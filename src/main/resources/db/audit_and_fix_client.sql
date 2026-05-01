-- =============================================================================
-- FILE   : audit_and_fix_client.sql
-- DB     : gac_db
-- TABLE  : client
-- AUTHOR : Senior DBA
-- PURPOSE: Audit data integrity issues and safely fix misplaced field values.
--
-- FIELD RULES (from Spring Boot entity + validation layer):
--   tel       : ^[24579][0-9]{7}$   — exactly 8 digits, starts with 2/4/5/7/9
--   cin       : ^[0-9]{8}$          — exactly 8 digits
--   rne       : ^[0-9]{7}$          — exactly 7 digits
--   email     : must contain '@' and a '.' after '@'
--   active    : TINYINT(1) — 0 or 1
--
-- EXECUTION ORDER:
--   STEP 1 — BACKUP (manual, outside this script)
--   STEP 2 — AUDIT   (SELECT only, safe to run anytime)
--   STEP 3 — FIX     (wrapped in TRANSACTION with ROLLBACK point)
--   STEP 4 — VERIFY  (SELECT only, run after STEP 3)
--   STEP 5 — CONSTRAINTS (run once after data is clean)
-- =============================================================================


-- =============================================================================
-- STEP 1 — BACKUP REMINDER
-- =============================================================================
-- Run this BEFORE executing anything else:
--
--   mysqldump -u root gac_db client > client_backup_$(date +%Y%m%d_%H%M%S).sql
--
-- Or from MySQL Workbench: Server → Data Export → select gac_db → client table.
-- =============================================================================


-- =============================================================================
-- STEP 2 — AUDIT (read-only, no data modified)
-- =============================================================================

USE gac_db;

-- ----------------------------------------------------------------------------
-- 2A. Full snapshot — see everything before touching anything
-- ----------------------------------------------------------------------------
SELECT
    id_client,
    nom,
    prenom,
    tel,
    email,
    cin,
    rne,
    adresse,
    type_client,
    active
FROM client
ORDER BY id_client;

-- ----------------------------------------------------------------------------
-- 2B. tel column issues
-- ----------------------------------------------------------------------------

-- tel that does NOT match the Tunisian phone pattern (should be 8 digits, starts 2/4/5/7/9)
SELECT id_client, nom, tel,
       'tel: invalid phone format' AS issue
FROM client
WHERE tel IS NOT NULL
  AND tel NOT REGEXP '^[24579][0-9]{7}$';

-- tel that looks like a 7-digit RNE
SELECT id_client, nom, tel,
       'tel: looks like RNE (7 digits)' AS issue
FROM client
WHERE tel REGEXP '^[0-9]{7}$';

-- tel that looks like an 8-digit CIN
SELECT id_client, nom, tel,
       'tel: looks like CIN (8 digits, wrong prefix)' AS issue
FROM client
WHERE tel REGEXP '^[0-9]{8}$'
  AND tel NOT REGEXP '^[24579][0-9]{7}$';

-- tel that looks like an email
SELECT id_client, nom, tel,
       'tel: looks like email' AS issue
FROM client
WHERE tel LIKE '%@%';

-- ----------------------------------------------------------------------------
-- 2C. rne column issues
-- ----------------------------------------------------------------------------

-- rne that does NOT match 7-digit rule
SELECT id_client, nom, rne,
       'rne: invalid format (not 7 digits)' AS issue
FROM client
WHERE rne IS NOT NULL
  AND rne NOT REGEXP '^[0-9]{7}$';

-- rne that looks like a phone number (8 digits, valid prefix)
SELECT id_client, nom, rne, tel,
       'rne: looks like phone number' AS issue
FROM client
WHERE rne REGEXP '^[24579][0-9]{7}$';

-- rne that looks like a CIN (8 digits)
SELECT id_client, nom, rne,
       'rne: looks like CIN (8 digits)' AS issue
FROM client
WHERE rne REGEXP '^[0-9]{8}$';

-- ----------------------------------------------------------------------------
-- 2D. cin column issues
-- ----------------------------------------------------------------------------

-- cin that does NOT match 8-digit rule
SELECT id_client, nom, cin,
       'cin: invalid format (not 8 digits)' AS issue
FROM client
WHERE cin IS NOT NULL
  AND cin NOT REGEXP '^[0-9]{8}$';

-- cin that looks like a phone number
SELECT id_client, nom, cin, tel,
       'cin: looks like phone number' AS issue
FROM client
WHERE cin REGEXP '^[24579][0-9]{7}$';

-- cin that looks like an RNE (7 digits)
SELECT id_client, nom, cin,
       'cin: looks like RNE (7 digits)' AS issue
FROM client
WHERE cin REGEXP '^[0-9]{7}$';

-- ----------------------------------------------------------------------------
-- 2E. email column issues
-- ----------------------------------------------------------------------------

-- email that does not contain '@'
SELECT id_client, nom, email,
       'email: missing @ sign' AS issue
FROM client
WHERE email IS NOT NULL
  AND email NOT LIKE '%@%';

-- email that does not have a dot after '@'
SELECT id_client, nom, email,
       'email: no dot in domain' AS issue
FROM client
WHERE email IS NOT NULL
  AND email LIKE '%@%'
  AND SUBSTRING_INDEX(email, '@', -1) NOT LIKE '%.%';

-- email that contains spaces
SELECT id_client, nom, email,
       'email: contains spaces' AS issue
FROM client
WHERE email LIKE '% %';

-- ----------------------------------------------------------------------------
-- 2F. Cross-column: phone stored in rne, rne stored in tel (the reported bug)
-- ----------------------------------------------------------------------------
SELECT
    id_client,
    nom,
    tel,
    rne,
    cin,
    CASE
        WHEN rne REGEXP '^[24579][0-9]{7}$' AND (tel IS NULL OR tel = '')
            THEN 'SWAP NEEDED: phone is in rne, tel is empty'
        WHEN rne REGEXP '^[24579][0-9]{7}$' AND tel REGEXP '^[24579][0-9]{7}$'
            THEN 'CONFLICT: both tel and rne look like phone numbers'
        WHEN tel REGEXP '^[0-9]{7}$' AND (rne IS NULL OR rne = '')
            THEN 'SWAP NEEDED: rne is in tel, rne is empty'
        WHEN tel REGEXP '^[0-9]{7}$' AND rne REGEXP '^[0-9]{7}$'
            THEN 'CONFLICT: both tel and rne look like 7-digit values'
        ELSE 'OK'
    END AS diagnosis
FROM client
WHERE
    rne REGEXP '^[24579][0-9]{7}$'   -- phone in rne
    OR tel REGEXP '^[0-9]{7}$'        -- rne in tel
ORDER BY id_client;

-- ----------------------------------------------------------------------------
-- 2G. Summary counts per issue type
-- ----------------------------------------------------------------------------
SELECT 'tel invalid format'          AS issue, COUNT(*) AS affected FROM client WHERE tel IS NOT NULL AND tel NOT REGEXP '^[24579][0-9]{7}$'
UNION ALL
SELECT 'rne invalid format'          AS issue, COUNT(*) AS affected FROM client WHERE rne IS NOT NULL AND rne NOT REGEXP '^[0-9]{7}$'
UNION ALL
SELECT 'cin invalid format'          AS issue, COUNT(*) AS affected FROM client WHERE cin IS NOT NULL AND cin NOT REGEXP '^[0-9]{8}$'
UNION ALL
SELECT 'email missing @'             AS issue, COUNT(*) AS affected FROM client WHERE email IS NOT NULL AND email NOT LIKE '%@%'
UNION ALL
SELECT 'phone stored in rne'         AS issue, COUNT(*) AS affected FROM client WHERE rne REGEXP '^[24579][0-9]{7}$'
UNION ALL
SELECT 'rne stored in tel'           AS issue, COUNT(*) AS affected FROM client WHERE tel REGEXP '^[0-9]{7}$'
UNION ALL
SELECT 'cin stored in rne'           AS issue, COUNT(*) AS affected FROM client WHERE rne REGEXP '^[0-9]{8}$';


-- =============================================================================
-- STEP 3 — SAFE DATA FIXES  (TRANSACTION — review STEP 2 output first)
-- =============================================================================
-- IMPORTANT: Read every AUDIT result before running this section.
-- Each fix only runs when there is HIGH CONFIDENCE (pattern match + empty target).
-- Conflicts (both fields filled) are logged but NOT auto-fixed.
-- =============================================================================

START TRANSACTION;

SAVEPOINT before_fixes;

-- ----------------------------------------------------------------------------
-- FIX 1: Phone number stored in rne, tel is empty → move to tel
-- Condition: rne matches phone pattern AND tel is NULL or empty
-- ----------------------------------------------------------------------------
UPDATE client
SET
    tel = rne,
    rne = NULL
WHERE rne REGEXP '^[24579][0-9]{7}$'
  AND (tel IS NULL OR tel = '');

SELECT ROW_COUNT() AS 'FIX 1: rows fixed (phone moved from rne to tel)';

-- ----------------------------------------------------------------------------
-- FIX 2: RNE stored in tel, rne is empty → move to rne
-- Condition: tel matches 7-digit pattern AND rne is NULL or empty
-- ----------------------------------------------------------------------------
UPDATE client
SET
    rne = tel,
    tel = NULL
WHERE tel REGEXP '^[0-9]{7}$'
  AND (rne IS NULL OR rne = '');

SELECT ROW_COUNT() AS 'FIX 2: rows fixed (rne moved from tel to rne)';

-- ----------------------------------------------------------------------------
-- FIX 3: CIN stored in rne, cin is empty → move to cin
-- Condition: rne matches 8-digit pattern AND cin is NULL or empty
-- ----------------------------------------------------------------------------
UPDATE client
SET
    cin = rne,
    rne = NULL
WHERE rne REGEXP '^[0-9]{8}$'
  AND (cin IS NULL OR cin = '');

SELECT ROW_COUNT() AS 'FIX 3: rows fixed (cin moved from rne to cin)';

-- ----------------------------------------------------------------------------
-- FIX 4: CIN stored in tel (8 digits, wrong prefix), cin is empty → move to cin
-- Condition: tel is 8 digits but NOT a valid phone prefix AND cin is empty
-- ----------------------------------------------------------------------------
UPDATE client
SET
    cin = tel,
    tel = NULL
WHERE tel REGEXP '^[0-9]{8}$'
  AND tel NOT REGEXP '^[24579][0-9]{7}$'
  AND (cin IS NULL OR cin = '');

SELECT ROW_COUNT() AS 'FIX 4: rows fixed (cin moved from tel to cin)';

-- ----------------------------------------------------------------------------
-- FIX 5: Trim whitespace from email, tel, cin, rne
-- Safe — never loses data, only removes leading/trailing spaces
-- ----------------------------------------------------------------------------
UPDATE client
SET
    email = TRIM(email),
    tel   = TRIM(tel),
    cin   = TRIM(cin),
    rne   = TRIM(rne);

SELECT ROW_COUNT() AS 'FIX 5: rows trimmed';

-- ----------------------------------------------------------------------------
-- CONFLICT LOG: rows where both fields are filled and both look valid
-- These are NOT auto-fixed — review manually
-- ----------------------------------------------------------------------------
SELECT
    id_client,
    nom,
    tel,
    rne,
    cin,
    'MANUAL REVIEW NEEDED: conflict detected' AS action_required
FROM client
WHERE
    (rne REGEXP '^[24579][0-9]{7}$' AND tel REGEXP '^[24579][0-9]{7}$')
    OR (tel REGEXP '^[0-9]{7}$'      AND rne REGEXP '^[0-9]{7}$')
    OR (rne REGEXP '^[0-9]{8}$'      AND cin REGEXP '^[0-9]{8}$');

-- ----------------------------------------------------------------------------
-- Review the changes before committing.
-- If anything looks wrong: ROLLBACK TO SAVEPOINT before_fixes;
-- If everything looks correct: COMMIT;
-- ----------------------------------------------------------------------------

-- ROLLBACK TO SAVEPOINT before_fixes;  -- ← uncomment to undo all fixes above
-- ROLLBACK;                             -- ← uncomment to undo everything

COMMIT;  -- ← comment this out if you want to review first


-- =============================================================================
-- STEP 4 — VERIFICATION (run after COMMIT)
-- =============================================================================

-- Full clean view
SELECT
    id_client,
    nom,
    tel,
    email,
    cin,
    rne,
    type_client,
    active
FROM client
ORDER BY id_client;

-- Confirm no remaining violations
SELECT
    'tel still invalid'   AS check_name, COUNT(*) AS remaining_issues FROM client WHERE tel IS NOT NULL AND tel NOT REGEXP '^[24579][0-9]{7}$'
UNION ALL
SELECT
    'rne still invalid'   AS check_name, COUNT(*) AS remaining_issues FROM client WHERE rne IS NOT NULL AND rne NOT REGEXP '^[0-9]{7}$'
UNION ALL
SELECT
    'cin still invalid'   AS check_name, COUNT(*) AS remaining_issues FROM client WHERE cin IS NOT NULL AND cin NOT REGEXP '^[0-9]{8}$'
UNION ALL
SELECT
    'email still invalid' AS check_name, COUNT(*) AS remaining_issues FROM client WHERE email IS NOT NULL AND email NOT LIKE '%@%.%';


-- =============================================================================
-- STEP 5 — DATABASE CONSTRAINTS (run once, after data is clean)
-- These prevent bad data from ever entering the database again.
-- =============================================================================

-- NOTE: MySQL CHECK constraints require MySQL 8.0.16+.
-- If you are on MySQL 5.7, skip the CHECK constraints and rely on backend validation.

ALTER TABLE client
    -- tel: required, must be valid Tunisian phone
    MODIFY COLUMN tel VARCHAR(20) NOT NULL,
    ADD CONSTRAINT chk_tel
        CHECK (tel REGEXP '^[24579][0-9]{7}$'),

    -- email: required, must contain @
    MODIFY COLUMN email VARCHAR(255) NOT NULL,
    ADD CONSTRAINT chk_email
        CHECK (email LIKE '%@%.%'),

    -- cin: optional, but if present must be exactly 8 digits
    ADD CONSTRAINT chk_cin
        CHECK (cin IS NULL OR cin REGEXP '^[0-9]{8}$'),

    -- rne: optional, but if present must be exactly 7 digits
    ADD CONSTRAINT chk_rne
        CHECK (rne IS NULL OR rne REGEXP '^[0-9]{7}$'),

    -- active: must be 0 or 1
    ADD CONSTRAINT chk_active
        CHECK (active IN (0, 1));

-- Verify constraints were added
SELECT
    CONSTRAINT_NAME,
    CHECK_CLAUSE
FROM information_schema.CHECK_CONSTRAINTS
WHERE CONSTRAINT_SCHEMA = 'gac_db'
  AND TABLE_NAME = 'client';
