package com.bna.gac.controllers;

import com.bna.gac.entities.Prestataire;
import com.bna.gac.entities.TypePrestataire;
import com.bna.gac.services.impl.PrestataireServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/prestataires")
@RequiredArgsConstructor
public class PrestataireController {

    private final PrestataireServiceImpl prestataireService;

    // ajout prestataire
    @PostMapping
    public Prestataire addPrestataire(@RequestBody Prestataire prestataire){
        return prestataireService.addPrestataire(prestataire);
    }

    // get all
    @GetMapping
    public List<Prestataire> getAllPrestataires(){
        return prestataireService.getAllPrestataires();
    }

    // get by id
    @GetMapping("/{id}")
    public Prestataire getPrestataireById(@PathVariable Long id){
        return prestataireService.getPrestataireById(id);
    }

    // get by type
    @GetMapping("/type/{type}")
    public List<Prestataire> getPrestatairesByType(@PathVariable String type){
        return prestataireService.getPrestatairesByType(TypePrestataire.valueOf(type));
    }

    // update
    @PutMapping("/{id}")
    public Prestataire updatePrestataire(@PathVariable Long id,
                                         @RequestBody Prestataire prestataire){
        return prestataireService.updatePrestataire(id, prestataire);
    }

    // update status
    @PutMapping("/{id}/status")
    public Prestataire updateStatus(@PathVariable Long id,
                                    @RequestParam boolean actif){
        return prestataireService.updateStatus(id, actif);
    }

    // delete
    @DeleteMapping("/{id}")
    public void deletePrestataire(@PathVariable Long id){
        prestataireService.deletePrestataire(id);
    }
}
