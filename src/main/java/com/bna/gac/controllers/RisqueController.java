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
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public RisqueDTO update(@PathVariable Long id, @RequestBody RisqueDTO dto) {
        return service.update(id, dto);
    }

    @GetMapping
    public List<RisqueDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public RisqueDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
