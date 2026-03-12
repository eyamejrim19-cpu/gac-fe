package com.bna.gac.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.bna.gac.dto.RisqueDTO;
import com.bna.gac.services.RisqueService;

import java.util.List;

@RestController
@RequestMapping("/api/risques")
@RequiredArgsConstructor
public class RisqueController {

    private final RisqueService service;

    @PostMapping
    public RisqueDTO create(@RequestBody RisqueDTO dto) {
        return service.save(dto);
    }

    @GetMapping
    public List<RisqueDTO> getAll() {
        return service.findAll();
    }
}