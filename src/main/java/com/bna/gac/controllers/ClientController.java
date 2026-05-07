package com.bna.gac.controllers;

import com.bna.gac.dto.ClientDTO;
import com.bna.gac.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

    private final ClientService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public ClientDTO create(@RequestBody ClientDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public List<ClientDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public ClientDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public ClientDTO update(@PathVariable Long id, @RequestBody ClientDTO dto) {
        return service.update(id, dto);
    }

    
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public ClientDTO updateStatus(@PathVariable Long id, @RequestBody com.bna.gac.dto.StatusUpdateDTO dto) {
        return service.updateStatus(id, dto);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('RESPONSABLE', 'ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

