package com.bna.gac.controllers;

import com.bna.gac.dto.FactureDTO;
import com.bna.gac.entities.Facture;
import com.bna.gac.services.impl.FactureServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/factures")
@RequiredArgsConstructor
public class FactureController {

    private final FactureServiceImpl factureService;

    // GET : récupérer toutes les factures
    @GetMapping
    public List<FactureDTO> getAllFactures() {
        return factureService.getAll();
    }

    // GET : récupérer facture par ID
    @GetMapping("/{id}")
    public Facture getFactureById(@PathVariable Long id) {
        return factureService.getFactureById(id);
    }

    // POST : ajouter facture
    @PostMapping
    public Facture createFacture(@RequestBody Facture facture) {
        return factureService.saveFacture(facture);
    }

    // PUT : modifier facture
    @PutMapping("/{id}")
    public Facture updateFacture(@PathVariable Long id, @RequestBody Facture facture) {
        facture.setIdFacture(id);
        return factureService.saveFacture(facture);
    }

    // DELETE : supprimer facture
    @DeleteMapping("/{id}")
    public void deleteFacture(@PathVariable Long id) {
        factureService.deleteFacture(id);
    }
}