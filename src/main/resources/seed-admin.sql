-- ============================================================
-- BNA GAC — Admin user seed script
-- Run this once against gac_db if no admin user exists
-- Password: admin123  (BCrypt encoded)
-- ============================================================

-- 1. Ensure ROLE_ADMIN exists
INSERT IGNORE INTO role (name, description)
VALUES ('ROLE_ADMIN', 'Administrateur système');

-- 2. Ensure admin user exists
INSERT IGNORE INTO users (username, password, nom, prenom, email, telephone)
VALUES (
  'admin',
  '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPVKkgv/y',
  'Admin',
  'Super',
  'admin@bna.tn',
  '00000000'
);

-- 3. Link admin user to ROLE_ADMIN
INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT u.id, r.idRole
FROM   users u, role r
WHERE  u.username = 'admin'
AND    r.name     = 'ROLE_ADMIN';
