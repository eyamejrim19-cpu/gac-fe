package com.bna.gac.controllers;

import com.bna.gac.dto.ClientDTO;
import com.bna.gac.services.ClientService;
import lombok.RequiredArgsConstructor;
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
