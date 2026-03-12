package com.bna.gac.controllers;

import com.bna.gac.services.impl.FactureService;
import com.bna.gac.services.impl.FactureServiceImpl;
import com.bna.gac.entities.Facture;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factures")
public class FactureController {

    private final FactureServiceImpl factureService;

    // Injection du service dans le controller
    public FactureController(FactureService factureService) {
        this.factureService = (FactureServiceImpl) factureService;
    }

    // GET : récupérer toutes les factures
    @GetMapping
    public List<Facture> getAllFactures() {
        return factureService.getAllFactures();
    }

    // GET : récupérer une facture par son id
    @GetMapping("/{id}")
    public Facture getFactureById(@PathVariable Long id) {
        return factureService.getFactureById(id);
    }

    // POST : créer une nouvelle facture
    @PostMapping
    public Facture createFacture(@RequestBody Facture facture) {
        return factureService.saveFacture(facture);
    }

    // PUT : modifier une facture existante
    @PutMapping("/{id}")
    public Facture updateFacture(@PathVariable Long id, @RequestBody Facture facture) {
        facture.setIdFacture(id);
        return factureService.saveFacture(facture);
    }

    // DELETE : supprimer une facture
    @DeleteMapping("/{id}")
    public void deleteFacture(@PathVariable Long id) {
        factureService.deleteFacture(id);
    }
}