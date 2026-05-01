package com.bna.gac.controllers;

import com.bna.gac.dto.ClientDTO;
import com.bna.gac.dto.StatusUpdateDTO;
import com.bna.gac.services.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.OPTIONS
})
@Slf4j
public class ClientController {

    private final ClientService service;

    @GetMapping
    public Page<ClientDTO> getAll(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Boolean active
    ) {
        log.info("GET /api/clients page={} size={} search='{}' type='{}' active={}", page, size, search, type, active);
        return service.findAll(page, size, search, type, active);
    }

    @PostMapping
    public ClientDTO create(@RequestBody ClientDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ClientDTO update(@PathVariable Long id, @RequestBody ClientDTO dto) {
        log.info("PUT /api/clients/{} payload={}", id, dto.getNom());
        return service.update(id, dto);
    }

    /**
     * Dedicated status toggle endpoint.
     * Accepts ONLY { "active": true/false } — nothing else is read or written.
     * This is the safe path called by the Angular toggleStatus feature.
     */
    @PatchMapping("/{id}/status")
    public ClientDTO updateStatus(@PathVariable Long id, @RequestBody StatusUpdateDTO dto) {
        log.info("PATCH /api/clients/{}/status active={}", id, dto.getActive());
        return service.updateStatus(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PatchMapping("/{id}/deactivate")
    public ClientDTO deactivate(@PathVariable Long id) {
        log.info("PATCH /api/clients/{}/deactivate", id);
        return service.deactivate(id);
    }

    @PatchMapping("/{id}/reactivate")
    public ClientDTO reactivate(@PathVariable Long id) {
        log.info("PATCH /api/clients/{}/reactivate", id);
        return service.reactivate(id);
    }
}