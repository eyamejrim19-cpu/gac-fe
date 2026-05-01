package com.bna.gac.mapper;

import com.bna.gac.dto.ClientDTO;
import com.bna.gac.entities.Client;
import org.springframework.stereotype.Component;

/**

 *  1. PHYSIQUE → only cin is stored; rne is forced to null
 *  2. MORALE   → only rne is stored; cin is forced to null
 *  This prevents cross-type contamination at the persistence layer.
 */
@Component
public class ClientMapper {

    // Number of trailing characters kept visible after masking
    private static final int CIN_VISIBLE_DIGITS = 2;   // CIN = 8 digits → "******78"
    private static final int RNE_VISIBLE_DIGITS = 2;   // RNE = 7 digits → "*****67"



    /**
     * Converts a Client entity to a ClientDTO safe for API responses.
     *
     * Strict field mapping:
     *   tel    → dto.tel       (direct, no transformation)
     *   cin    → dto.cin       (masked for PHYSIQUE, null for MORALE)
     *   rne    → dto.rne       (masked for MORALE,   null for PHYSIQUE)
     *   active → dto.active    (boolean, source of truth)
     *   active → dto.statut    (derived: "Actif" / "Inactif")
     */
    public ClientDTO toDto(Client c) {
        if (c == null) return null;

        ClientDTO dto = new ClientDTO();

        // ── Identity fields ──────────────────────────────────────────────
        dto.setId(c.getId());
        dto.setNom(c.getNom());
        dto.setPrenom(c.getPrenom());
        dto.setEmail(c.getEmail());
        dto.setAdresse(c.getAdresse());
        dto.setTypeClient(c.getTypeClient());
        dto.setDateCreation(c.getDateCreation());

        // ── tel → Téléphone column ───────────────────────────────────────
        // Direct mapping. tel is always the phone number — never cin or rne.
        dto.setTel(c.getTel());

        // ── active → Statut column ───────────────────────────────────────
        // active is the boolean source of truth.
        // statut is the derived human-readable label for the UI.
        dto.setActive(c.getActive());
        dto.setStatut(Boolean.TRUE.equals(c.getActive()) ? "Actif" : "Inactif");

        // ── Identifier masking — type-based, mutually exclusive ──────────
        if ("PHYSIQUE".equalsIgnoreCase(c.getTypeClient())) {
            // PHYSIQUE: cin → CIN column (masked), rne → null (never shown)
            dto.setCin(maskIdentifier(c.getCin(), CIN_VISIBLE_DIGITS));
            dto.setRne(null);

        } else if ("MORALE".equalsIgnoreCase(c.getTypeClient())) {
            // MORALE: rne → RNE column (masked), cin → null (never shown)
            dto.setRne(maskIdentifier(c.getRne(), RNE_VISIBLE_DIGITS));
            dto.setCin(null);

        } else {
            // Unknown type — suppress both identifiers as a safe default
            dto.setCin(null);
            dto.setRne(null);
        }

        return dto;
    }

    // -----------------------------------------------------------------------
    // toEntity — API request → entity
    // -----------------------------------------------------------------------

    /**
     * Converts an inbound ClientDTO (create/update request) to a Client entity.
     *
     * Enforces type-based field isolation:
     *   PHYSIQUE → cin stored, rne forced to null
     *   MORALE   → rne stored, cin forced to null
     */
    public Client toEntity(ClientDTO dto) {
        if (dto == null) return null;

        Client c = new Client();
        c.setNom(dto.getNom());
        c.setPrenom(dto.getPrenom());
        c.setEmail(dto.getEmail());
        c.setTel(dto.getTel());
        c.setAdresse(dto.getAdresse());
        c.setTypeClient(dto.getTypeClient());
        c.setActive(dto.getActive() != null ? dto.getActive() : true);

        // Enforce identifier isolation — only the applicable field is stored
        if ("PHYSIQUE".equalsIgnoreCase(dto.getTypeClient())) {
            c.setCin(dto.getCin());
            c.setRne(null);           // MORALE-only field — must never be set for PHYSIQUE

        } else if ("MORALE".equalsIgnoreCase(dto.getTypeClient())) {
            c.setRne(dto.getRne());
            c.setCin(null);           // PHYSIQUE-only field — must never be set for MORALE

        } else {
            c.setCin(null);
            c.setRne(null);
        }

        return c;
    }

    // -----------------------------------------------------------------------
    // Masking utility
    // -----------------------------------------------------------------------

    /**
     * Masks an identifier string, keeping only the last {@code visibleDigits}
     * characters visible and replacing the rest with asterisks.
     *
     * Examples:
     *   maskIdentifier("12345678", 2)  →  "******78"   (CIN, 8 digits)
     *   maskIdentifier("1234567",  2)  →  "*****67"    (RNE, 7 digits)
     *   maskIdentifier(null, 2)        →  null
     *   maskIdentifier("",   2)        →  null
     *
     * @param value         raw identifier value
     * @param visibleDigits number of trailing characters to keep visible
     * @return masked string, or null if the input is null or blank
     */
    static String maskIdentifier(String value, int visibleDigits) {
        if (value == null || value.isBlank()) return null;

        int len = value.length();
        if (len <= visibleDigits) {
            // Shorter than the visible window — show last char only
            return "*".repeat(Math.max(0, len - 1)) + value.charAt(len - 1);
        }

        int maskLen = len - visibleDigits;
        return "*".repeat(maskLen) + value.substring(maskLen);
    }
}