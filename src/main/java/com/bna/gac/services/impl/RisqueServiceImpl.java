package com.bna.gac.services.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.bna.gac.repositories.RisqueRepository;
import com.bna.gac.mapper.RisqueMapper;
import com.bna.gac.dto.RisqueDTO;
import com.bna.gac.entities.Risque;
import com.bna.gac.services.impl.RisqueService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RisqueServiceImpl implements RisqueService {

    private final RisqueRepository repository;
    private final RisqueMapper mapper;

    @Override
    public RisqueDTO save(RisqueDTO dto) {
        Risque entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public List<RisqueDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }

    @Override
    public RisqueDTO findById(Long id) {
        Risque entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Risque not found"));
        return mapper.toDTO(entity);
    }

    @Override
    public RisqueDTO update(Long id, RisqueDTO dto) {
        Risque existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Risque not found"));

        existing.setMontantPrincipal(dto.getMontantPrincipal());
        existing.setMontantInteret(dto.getMontantInteret());
        existing.setMontantTotal(dto.getMontantTotale());
        existing.setTauxInteret(dto.getTauxInteret());
        existing.setPeriode(Integer.valueOf(dto.getPeriode()));
        existing.setDateContrat(dto.getDateContrat().atStartOfDay());
        existing.setDateDebloquage(dto.getDateDebloquage());
        existing.setDateEcheance(dto.getDateEcheance().atStartOfDay());

        return mapper.toDTO(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}