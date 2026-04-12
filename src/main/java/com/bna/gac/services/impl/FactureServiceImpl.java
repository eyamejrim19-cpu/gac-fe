package com.bna.gac.services.impl;

import com.bna.gac.dto.FactureDTO;
import com.bna.gac.entities.Facture;
import com.bna.gac.repositories.FactureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FactureServiceImpl implements FactureService {

    private final FactureRepository factureRepository;

    // ENTITY -> DTO
    private FactureDTO toDTO(Facture f) {
        FactureDTO dto = new FactureDTO();
        dto.setIdFacture(f.getIdFacture());
        dto.setNumero(f.getNumero());
        dto.setMontant(f.getMontant());
        dto.setDateEmission(LocalDate.from(f.getDateEmission()));
        dto.setTypeFacture(f.getTypeFacture());
        dto.setStatut(f.getStatut());
        return dto;
    }

    // DTO -> ENTITY
    private Facture toEntity(FactureDTO dto) {
        Facture f = new Facture();
        f.setIdFacture(dto.getIdFacture());
        f.setNumero(dto.getNumero());
        f.setMontant(dto.getMontant());
        f.setDateEmission(dto.getDateEmission().atStartOfDay());
        f.setTypeFacture(dto.getTypeFacture());
        f.setStatut(dto.getStatut());
        return f;
    }

    @Override
    public FactureDTO create(FactureDTO dto) {
        Facture saved = factureRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    @Override
    public FactureDTO update(Long id, FactureDTO dto) {
        Facture existing = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture not found"));

        existing.setNumero(dto.getNumero());
        existing.setMontant(dto.getMontant());
        existing.setDateEmission(dto.getDateEmission().atStartOfDay());
        existing.setTypeFacture(dto.getTypeFacture());
        existing.setStatut(dto.getStatut());

        return toDTO(factureRepository.save(existing));
    }

    @Override
    public FactureDTO getById(Long id) {
        Facture f = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture not found"));

        return toDTO(f);
    }

    @Override
    public List<FactureDTO> getAll() {
        return factureRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        factureRepository.deleteById(id);
    }

    @Override
    public Facture getFactureById(Long id) {
        return null;
    }

    public Facture saveFacture(Facture facture) {
        return facture;
    }

    public void deleteFacture(Long id) {
    }
}
