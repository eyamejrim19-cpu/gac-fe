package com.bna.gac.services.impl;

import com.bna.gac.dto.AudienceDTO;
import com.bna.gac.entities.Audience;
import com.bna.gac.entities.DossierContentieux;
import com.bna.gac.mapper.AudienceMapper;
import com.bna.gac.repositories.AudienceRepository;
import com.bna.gac.repositories.DossierContentieuxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AudienceServiceImpl implements AudienceService {

    private final AudienceRepository audienceRepository;
    private final DossierContentieuxRepository dossierRepository;
    private  AudienceMapper mapper;

    public AudienceServiceImpl(AudienceRepository audienceRepository, DossierContentieuxRepository dossierRepository) {
        this.audienceRepository = audienceRepository;
        this.dossierRepository = dossierRepository;
    }

    @Override
    public AudienceDTO create(AudienceDTO dto) {

        Audience audience = mapper.toEntity(dto);

        DossierContentieux dossier = dossierRepository.findById(dto.getDossierId())
                .orElseThrow(() -> new RuntimeException("Dossier not found"));

        audience.setDossier(dossier);

        Audience saved = audienceRepository.save(audience);

        return mapper.toDto(saved);
    }

    @Override
    public List<AudienceDTO> getAll() {
        return mapper.toDtoList(audienceRepository.findAll());
    }

    @Override
    public AudienceDTO getById(Long id) {
        Audience audience = audienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Audience not found"));
        return mapper.toDto(audience);
    }

    @Override
    public void delete(Long id) {
        audienceRepository.deleteById(id);
    }
}
