package com.bna.gac.controllers;

import com.bna.gac.dto.ClientDTO;
import com.bna.gac.dto.StatusUpdateDTO;
import com.bna.gac.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

    private final ClientService service;

    @PostMapping
    public ClientDTO create(@RequestBody ClientDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<ClientDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ClientDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public ClientDTO update(@PathVariable Long id, @RequestBody ClientDTO dto) {
        return service.update(id, dto);
    }

    /**
     * PUT /api/clients/{id}/status
     * Change uniquement le champ active du client.
     * Body attendu : { "active": true } ou { "active": false }
     * Retourne 200 + ClientDTO mis à jour, ou 404 si le client n'existe pas.
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<ClientDTO> updateStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateDTO dto) {
        ClientDTO updated = service.updateStatus(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
