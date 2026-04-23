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
        return service.save(dto);
    }

    @GetMapping
    public List<ClientDTO> getAll() {
        return service.findAll();
    }

    @PutMapping("/{id}")
    public ClientDTO update(@PathVariable Long id, @RequestBody ClientDTO dto) {
        dto.setId(id);
        return service.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}