package com.bna.gac.services.impl;

import com.bna.gac.dto.AffaireDTO;
import com.bna.gac.entities.Affaire;
import com.bna.gac.entities.DossierContentieux;
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
        return mapper.toDto(affaireRepository.save(affaire));
    }

    @Override
    public AffaireDTO update(Long id, AffaireDTO dto) {
        Affaire affaire = findAffaire(id);
        affaire.setNumeroProcedure(dto.getNumeroProcedure());
        affaire.setStatut(dto.getStatut());
        affaire.setTribunal(dto.getTribunal());
        affaire.setJugement(dto.getJugement());
        affaire.setDateDebut(dto.getDateDebut() == null ? null : dto.getDateDebut().atStartOfDay());
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
    public AffaireDTO getById(Long id) {
        return mapper.toDto(findAffaire(id));
    }

    @Override
    public void delete(Long id) {
        affaireRepository.delete(findAffaire(id));
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
