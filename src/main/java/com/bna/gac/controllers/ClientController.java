package com.bna.gac.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.bna.gac.dto.ClientDTO;
import com.bna.gac.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
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
}