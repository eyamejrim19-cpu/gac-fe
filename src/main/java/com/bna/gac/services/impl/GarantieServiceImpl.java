package com.bna.gac.services.impl;

import com.bna.gac.dto.GarantieDTO;
import com.bna.gac.entities.Garantie;
import com.bna.gac.entities.Risque;
import com.bna.gac.exceptions.BadRequestException;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.GarantieMapper;
import com.bna.gac.repositories.GarantieRepository;
import com.bna.gac.repositories.RisqueRepository;
import com.bna.gac.services.GarantieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GarantieServiceImpl implements GarantieService {

    private final GarantieRepository repository;
    private final RisqueRepository risqueRepository;
    private final GarantieMapper mapper;

    @Override
    public GarantieDTO create(GarantieDTO dto) {
        if (dto.getRisqueId() == null) {
            throw new BadRequestException("Garantie must be linked to a Risque");
        }
        Garantie garantie = mapper.toEntity(dto);
        garantie.setRisque(findRisque(dto.getRisqueId()));
        return mapper.toDTO(repository.save(garantie));
    }

    @Override
    public GarantieDTO update(Long id, GarantieDTO dto) {
        Garantie garantie = findGarantie(id);
        garantie.setTypeGarantie(dto.getTypeGarantie());
        garantie.setDescription(dto.getDescription());
        garantie.setValeur(dto.getValeur());
        garantie.setStatut(dto.getStatut());
        if (dto.getRisqueId() != null) {
            garantie.setRisque(findRisque(dto.getRisqueId()));
        }
        return mapper.toDTO(repository.save(garantie));
    }

    @Override
    public GarantieDTO getById(Long id) {
        return mapper.toDTO(findGarantie(id));
    }

    @Override
    public List<GarantieDTO> getAll() {
        return mapper.toDTOList(repository.findAll());
    }

    @Override
    public void delete(Long id) {
        repository.delete(findGarantie(id));
    }

    private Garantie findGarantie(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Garantie not found"));
    }

    private Risque findRisque(Long id) {
        return risqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Risque not found"));
    }
}
