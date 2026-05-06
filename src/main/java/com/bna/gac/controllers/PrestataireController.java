package com.bna.gac.controllers;

import com.bna.gac.dto.PrestataireDTO;
import com.bna.gac.entities.TypePrestataire;
import com.bna.gac.exceptions.BadRequestException;
import com.bna.gac.services.PrestataireService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestataires")
@RequiredArgsConstructor
public class PrestataireController {

    private final PrestataireService prestataireService;

    // ── GET /api/prestataires ──────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<PrestataireDTO>> getAll() {
        return ResponseEntity.ok(prestataireService.getAll());
    }

    // ── GET /api/prestataires/paginated ───────────────────────────────────────
    // NOTE: This mapping MUST be declared before /{id} so Spring resolves the
    // literal path "paginated" before attempting Long conversion on {id}.
    // The /{id:\\d+} regex constraint below is the belt-and-suspenders fix.
    @GetMapping("/paginated")
    public ResponseEntity<Page<PrestataireDTO>> getPaginated(
            @RequestParam(defaultValue = "0")  int     page,
            @RequestParam(defaultValue = "10") int     size,
            @RequestParam(required = false)    String  search,
            @RequestParam(required = false)    String  type,
            @RequestParam(required = false)    Boolean actif) {

        int safePage = Math.max(0, page);
        int safeSize = Math.min(Math.max(1, size), 100);

        TypePrestataire parsedType = parseType(type);

        return ResponseEntity.ok(
                prestataireService.getPaginated(safePage, safeSize, search, parsedType, actif));
    }

    // ── GET /api/prestataires/type/{type} ─────────────────────────────────────
    // Also declared before /{id} to avoid ambiguity.
    @GetMapping("/type/{type}")
    public ResponseEntity<List<PrestataireDTO>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(prestataireService.getByType(parseTypeRequired(type)));
    }

    // ── GET /api/prestataires/{id} ────────────────────────────────────────────
    // Regex \\d+ ensures only numeric segments match — "paginated" never reaches here.
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<PrestataireDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(prestataireService.getById(id));
    }

    // ── POST /api/prestataires ─────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<PrestataireDTO> create(@RequestBody PrestataireDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(prestataireService.create(dto));
    }

    // ── PUT /api/prestataires/{id} ─────────────────────────────────────────────
    @PutMapping("/{id:\\d+}")
    public ResponseEntity<PrestataireDTO> update(
            @PathVariable Long id,
            @RequestBody PrestataireDTO dto) {
        return ResponseEntity.ok(prestataireService.update(id, dto));
    }

    // ── PUT /api/prestataires/{id}/status?actif=true ───────────────────────────
    @PutMapping("/{id:\\d+}/status")
    public ResponseEntity<PrestataireDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam boolean actif) {
        return ResponseEntity.ok(prestataireService.updateStatus(id, actif));
    }

    // ── DELETE /api/prestataires/{id} ─────────────────────────────────────────
    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        prestataireService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /**
     * Parses an optional type string.
     * Returns null if blank — means "no type filter, return all".
     * Returns 400 for unrecognised values instead of crashing with 500.
     */
    private TypePrestataire parseType(String type) {
        if (type == null || type.isBlank()) return null;
        try {
            return TypePrestataire.valueOf(type.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(
                    "Type de prestataire invalide : '" + type +
                    "'. Valeurs acceptées : AVOCAT, HUISSIER, EXPERT");
        }
    }

    /**
     * Parses a required type string.
     * Throws BadRequestException if blank or invalid.
     */
    private TypePrestataire parseTypeRequired(String type) {
        if (type == null || type.isBlank())
            throw new BadRequestException("Le type de prestataire est requis.");
        return parseType(type);
    }
}
