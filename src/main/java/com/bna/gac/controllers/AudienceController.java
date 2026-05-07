package com.bna.gac.controllers;

import com.bna.gac.dto.AudienceDTO;
import com.bna.gac.services.AudienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audiences")
@RequiredArgsConstructor
public class AudienceController {

    private final AudienceService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'ADMIN')")
    public AudienceDTO create(@RequestBody AudienceDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'ADMIN')")
    public AudienceDTO update(@PathVariable Long id, @RequestBody AudienceDTO dto) {
        return service.update(id, dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public List<AudienceDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public AudienceDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('RESPONSABLE', 'ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

