package com.bna.gac.services.impl;

import com.bna.gac.dto.AudienceDTO;
import com.bna.gac.entities.Affaire;
import com.bna.gac.entities.Audience;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.AudienceMapper;
import com.bna.gac.repositories.AffaireRepository;
import com.bna.gac.repositories.AudienceRepository;
import com.bna.gac.services.AudienceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AudienceServiceImpl implements AudienceService {

    private final AudienceRepository audienceRepository;
    private final AffaireRepository affaireRepository;
    private final AudienceMapper mapper;

    @Override
    public AudienceDTO create(AudienceDTO dto) {
        Audience audience = mapper.toEntity(dto);
        audience.setAffaire(findAffaire(dto.getAffaireId()));
        return mapper.toDto(audienceRepository.save(audience));
    }

    @Override
    public AudienceDTO update(Long id, AudienceDTO dto) {
        Audience audience = findAudience(id);
        audience.setDateAudience(dto.getDateAudience() == null ? null : dto.getDateAudience().atStartOfDay());
        audience.setTypeAudience(dto.getTypeAudience());
        audience.setDecision(dto.getDecision());
        audience.setObservation(dto.getObservation());
        audience.setCommentaire(dto.getCommentaire());
        if (dto.getAffaireId() != null) {
            audience.setAffaire(findAffaire(dto.getAffaireId()));
        }
        return mapper.toDto(audienceRepository.save(audience));
    }

    @Override
    public List<AudienceDTO> getAll() {
        return mapper.toDtoList(audienceRepository.findAll());
    }

    @Override
    public AudienceDTO getById(Long id) {
        return mapper.toDto(findAudience(id));
    }

    @Override
    public List<AudienceDTO> getByAffaireId(Long affaireId) {
        return mapper.toDtoList(audienceRepository.findByAffaire_IdAffaire(affaireId));
    }

    @Override
    public void delete(Long id) {
        audienceRepository.delete(findAudience(id));
    }

    private Audience findAudience(Long id) {
        return audienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audience not found"));
    }

    private Affaire findAffaire(Long id) {
        return affaireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Affaire not found"));
    }
}

