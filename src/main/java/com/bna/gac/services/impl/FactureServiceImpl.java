package com.bna.gac.services.impl;

import com.bna.gac.dto.FactureDTO;
import com.bna.gac.entities.Facture;
import com.bna.gac.entities.Mission;
import com.bna.gac.entities.Prestataire;
import com.bna.gac.mapper.FactureMapper;
import com.bna.gac.repositories.FactureRepository;
import com.bna.gac.repositories.MissionRepository;
import com.bna.gac.repositories.PrestataireRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FactureServiceImpl implements FactureService {

    private final FactureRepository factureRepository;
    private final MissionRepository missionRepository;
    private final PrestataireRepository prestataireRepository;
    private final FactureMapper factureMapper;

    @Override
    public FactureDTO create(FactureDTO dto) {

        Mission mission = missionRepository.findById(dto.getMissionId())
                .orElseThrow(() -> new RuntimeException("Mission not found"));

        Prestataire prestataire = prestataireRepository.findById(dto.getPrestataireId())
                .orElseThrow(() -> new RuntimeException("Prestataire not found"));

        Facture facture = factureMapper.toEntity(dto);
        facture.setMission(mission);
        facture.setPrestataire(prestataire);

        return factureMapper.toDto(factureRepository.save(facture));
    }

    @Override
    public FactureDTO update(Long id, FactureDTO dto) {

        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture not found"));

        facture.setNumero(dto.getNumero());
        facture.setDateEmission(dto.getDateEmission().atStartOfDay());
        facture.setMontant(dto.getMontant());
        facture.setStatut(dto.getStatut());
        facture.setTypeFacture(dto.getTypeFacture());

        return factureMapper.toDto(factureRepository.save(facture));
    }

    @Override
    public FactureDTO getById(Long id) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture not found"));
        return factureMapper.toDto(facture);
    }

    @Override
    public List<FactureDTO> getAll() {
        return factureMapper.toDtoList(factureRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        factureRepository.deleteById(id);
    }

    public List<Facture> getAllFactures() {
        return List.of();
    }

    public Facture getFactureById(Long id) {
        return null;
    }

    public Facture saveFacture(Facture facture) {
        return facture;
    }

    public void deleteFacture(Long id) {
    }
}
