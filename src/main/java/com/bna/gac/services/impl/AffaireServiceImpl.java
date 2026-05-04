package com.bna.gac.services.impl;

import com.bna.gac.dto.AffaireDTO;
import com.bna.gac.entities.Affaire;
import com.bna.gac.entities.DossierContentieux;
import com.bna.gac.entities.enums.AffaireStatus;
import com.bna.gac.exceptions.BadRequestException;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.AffaireMapper;
import com.bna.gac.repositories.AffaireRepository;
import com.bna.gac.repositories.DossierContentieuxRepository;
import com.bna.gac.services.AffaireService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AffaireServiceImpl implements AffaireService {

    private final AffaireRepository affaireRepository;
    private final DossierContentieuxRepository dossierRepository;
    private final AffaireMapper mapper;

    @Override
    public AffaireDTO create(AffaireDTO dto) {
        Affaire affaire = mapper.toEntity(dto);
        affaire.setDossier(findDossier(dto.getDossierId()));

        if (affaire.getStatut() == null) {
            affaire.setStatut(AffaireStatus.INITIEE);
        }

        return mapper.toDto(affaireRepository.save(affaire));
    }

    @Override
    public AffaireDTO update(Long id, AffaireDTO dto) {
        Affaire affaire = findAffaire(id);

        AffaireStatus newStatus = null;
        if (dto.getStatut() != null) {
            newStatus = AffaireStatus.valueOf(dto.getStatut().toUpperCase());
        }

        if (newStatus != null && !newStatus.equals(affaire.getStatut())) {
            validateStatusTransition(affaire.getStatut(), newStatus);
            affaire.setStatut(newStatus);
        }

        affaire.setNumeroProcedure(dto.getNumeroProcedure());
        affaire.setTribunal(dto.getTribunal());
        affaire.setJugement(dto.getJugement());
        affaire.setDateDebut(dto.getDateDebut());

        if (dto.getDossierId() != null) {
            affaire.setDossier(findDossier(dto.getDossierId()));
        }

        return mapper.toDto(affaireRepository.save(affaire));
    }

    @Override
    public List<AffaireDTO> getAll() {
        return mapper.toDtoList(affaireRepository.findAll());
    }

    @Override
    public List<AffaireDTO> getByDossierId(Long dossierId) {
        return mapper.toDtoList(affaireRepository.findByDossier_IdDossier(dossierId));
    }

    @Override
    public AffaireDTO getById(Long id) {
        return mapper.toDto(findAffaire(id));
    }

    @Override
    public void delete(Long id) {
        Affaire affaire = findAffaire(id);

        if (affaire.getStatut() == AffaireStatus.TERMINEE) {
            throw new BadRequestException("Cannot delete a finished case (Affaire)");
        }

        affaireRepository.delete(affaire);
    }

    private void validateStatusTransition(AffaireStatus current, AffaireStatus next) {
        if (current == AffaireStatus.TERMINEE) {
            throw new BadRequestException("Cannot modify a finished case (Affaire)");
        }
    }

    private Affaire findAffaire(Long id) {
        return affaireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Affaire not found"));
    }

    private DossierContentieux findDossier(Long id) {
        return dossierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dossier not found"));
    }
}