package com.bna.gac.services.impl;

import com.bna.gac.dto.FactureDTO;
import com.bna.gac.entities.Facture;
import com.bna.gac.entities.Mission;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.FactureMapper;
import com.bna.gac.repositories.FactureRepository;
import com.bna.gac.repositories.MissionRepository;
import com.bna.gac.services.FactureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FactureServiceImpl implements FactureService {

    private final FactureRepository factureRepository;
    private final MissionRepository missionRepository;
    private final FactureMapper factureMapper;

    @Override
    public FactureDTO create(FactureDTO dto) {
        Facture facture = factureMapper.toEntity(dto);
        facture.setMission(getMission(dto.getMissionId()));
        return factureMapper.toDto(factureRepository.save(facture));
    }

    @Override
    public FactureDTO update(Long id, FactureDTO dto) {
        Facture existing = findFacture(id);
        existing.setNumero(dto.getNumero());
        if (dto.getDateEmission() != null) {
            existing.setDateEmission(dto.getDateEmission().atStartOfDay());
        }
        existing.setMontant(dto.getMontant());
        existing.setStatut(dto.getStatut());
        existing.setTypeFacture(dto.getTypeFacture());
        existing.setMission(getMission(dto.getMissionId()));
        return factureMapper.toDto(factureRepository.save(existing));
    }

    @Override
    public FactureDTO getById(Long id) {
        return factureMapper.toDto(findFacture(id));
    }

    @Override
    public List<FactureDTO> getAll() {
        return factureRepository.findAll()
                .stream()
                .map(factureMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        factureRepository.delete(findFacture(id));
    }

    @Override
    public FactureDTO validate(Long id) {
        Facture facture = findFacture(id);
        if ("VALIDEE".equals(facture.getStatut()) || "PAYEE".equals(facture.getStatut())) {
            throw new com.bna.gac.exceptions.BadRequestException("La facture est déjà validée ou payée");
        }
        facture.setStatut("VALIDEE");
        return factureMapper.toDto(factureRepository.save(facture));
    }

    @Override
    public FactureDTO reject(Long id, String commentaireRejet) {
        Facture facture = findFacture(id);
        if ("VALIDEE".equals(facture.getStatut()) || "PAYEE".equals(facture.getStatut())) {
            throw new com.bna.gac.exceptions.BadRequestException("La facture est déjà validée ou payée");
        }
        facture.setStatut("REJETEE");
        facture.setCommentaireRejet(commentaireRejet);
        return factureMapper.toDto(factureRepository.save(facture));
    }

    private Facture findFacture(Long id) {
        return factureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facture not found"));
    }

    private Mission getMission(Long missionId) {
        if (missionId == null) {
            throw new com.bna.gac.exceptions.BadRequestException("Une facture doit être liée à une mission");
        }
        return missionRepository.findById(missionId)
                .orElseThrow(() -> new ResourceNotFoundException("Mission not found"));
    }
}

