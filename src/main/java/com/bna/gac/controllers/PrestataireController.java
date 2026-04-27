package com.bna.gac.controllers;

import com.bna.gac.entities.TypePrestataire;
import com.bna.gac.dto.PrestataireDTO;
import com.bna.gac.services.PrestataireService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/prestataires")
@RequiredArgsConstructor
public class PrestataireController {

    private final PrestataireService prestataireService;

    @PostMapping
    public PrestataireDTO create(@RequestBody PrestataireDTO prestataire){
        return prestataireService.create(prestataire);
    }

    @GetMapping
    public List<PrestataireDTO> getAll(){
        return prestataireService.getAll();
    }

    @GetMapping("/{id}")
    public PrestataireDTO getById(@PathVariable Long id){
        return prestataireService.getById(id);
    }

    @GetMapping("/type/{type}")
    public List<PrestataireDTO> getByType(@PathVariable String type){
        return prestataireService.getByType(TypePrestataire.valueOf(type));
    }

    @PutMapping("/{id}")
    public PrestataireDTO update(@PathVariable Long id,
                                 @RequestBody PrestataireDTO prestataire){
        return prestataireService.update(id, prestataire);
    }

    @PutMapping("/{id}/status")
    public PrestataireDTO updateStatus(@PathVariable Long id,
                                       @RequestParam boolean actif){
        return prestataireService.updateStatus(id, actif);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        prestataireService.delete(id);
    }
}
