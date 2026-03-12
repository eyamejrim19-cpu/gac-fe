package com.bna.gac.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.bna.gac.dto.DossierContentieuxDTO;
import com.bna.gac.services.DossierContentieuxService;

import java.util.List;

@RestController
@RequestMapping("/api/dossiers")
@RequiredArgsConstructor
public class DossierController {

    private final DossierContentieuxService service;

    @PostMapping
    public DossierContentieuxDTO create(@RequestBody DossierContentieuxDTO dto) {
        return service.save(dto);
    }

    @GetMapping
    public List<DossierContentieuxDTO> getAll() {
        return service.findAll();
    }
}