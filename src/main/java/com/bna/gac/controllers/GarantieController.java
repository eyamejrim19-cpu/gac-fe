package com.bna.gac.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.bna.gac.dto.GarantieDTO;
import com.bna.gac.services.GarantieService;

import java.util.List;

@RestController
@RequestMapping("/api/garanties")
@RequiredArgsConstructor
public class GarantieController {

    private final GarantieService service;

    @PostMapping
    public GarantieDTO create(@RequestBody GarantieDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public GarantieDTO update(@PathVariable Long id, @RequestBody GarantieDTO dto) {
        return service.update(id, dto);
    }

    @GetMapping
    public List<GarantieDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GarantieDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
