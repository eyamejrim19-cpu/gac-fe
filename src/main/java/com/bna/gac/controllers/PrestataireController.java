package com.bna.gac.controllers;


import com.bna.gac.entities.Prestataire;
import com.bna.gac.services.impl.PrestataireServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestataires")
@RequiredArgsConstructor

public class PrestataireController {

    private final PrestataireServiceImpl prestataireService;

    // ajout prestataire (avocat / expert / huissier)
    @PostMapping
    public Prestataire addPrestataire(@RequestBody Prestataire prestataire){
        return prestataireService.addPrestataire(prestataire);
    }

    // get all prestataires
    @GetMapping
    public List<Prestataire> getAllPrestataires(){
        return prestataireService.getAllPrestataires();
    }

    // get prestataire by id
    @GetMapping("/{id}")
    public Prestataire getPrestataireById(@PathVariable Long id){
        return prestataireService.getPrestataireById(id);
    }

    // modifier prestataire
    @PutMapping("/{id}")
    public Prestataire updatePrestataire(@PathVariable Long id,
                                         @RequestBody Prestataire prestataire){
        return prestataireService.updatePrestataire(id, prestataire);
    }

    // supprimer prestataire
    @DeleteMapping("/{id}")
    public void deletePrestataire(@PathVariable Long id){
        prestataireService.deletePrestataire(id);
    }
}
