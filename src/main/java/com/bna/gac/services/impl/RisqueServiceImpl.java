package com.bna.gac.services.impl;

import com.bna.gac.dto.RisqueDTO;
import com.bna.gac.entities.DossierContentieux;
import com.bna.gac.entities.Risque;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.RisqueMapper;
import com.bna.gac.repositories.DossierContentieuxRepository;
import com.bna.gac.repositories.RisqueRepository;
import com.bna.gac.services.RisqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RisqueServiceImpl implements RisqueService {

    private final RisqueRepository repository;
    private final DossierContentieuxRepository dossierRepository;
    private final RisqueMapper mapper;

    @Override
    public RisqueDTO create(RisqueDTO dto) {
        Risque risque = mapper.toEntity(dto);
        risque.setDossier(findDossier(dto.getDossierId()));
        return mapper.toDTO(repository.save(risque));
    }

    @Override
    public RisqueDTO update(Long id, RisqueDTO dto) {
        Risque risque = findRisque(id);
        risque.setMontantPrincipal(dto.getMontantPrincipal());
        risque.setMontantInteret(dto.getMontantInteret());
        risque.setMontantTotal(dto.getMontantTotal());
        risque.setTauxInteret(dto.getTauxInteret());
        risque.setPeriode(dto.getPeriode() == null || dto.getPeriode().isBlank() ? null : Integer.valueOf(dto.getPeriode()));
        risque.setDateContrat(dto.getDateContrat() == null ? null : dto.getDateContrat().atStartOfDay());
        risque.setDateDeblocage(dto.getDateDeblocage() == null ? null : dto.getDateDeblocage().atStartOfDay());
        risque.setDateEcheance(dto.getDateEcheance() == null ? null : dto.getDateEcheance().atStartOfDay());
        risque.setDossier(findDossier(dto.getDossierId()));
        return mapper.toDTO(repository.save(risque));
    }

    @Override
    public RisqueDTO getById(Long id) {
        return mapper.toDTO(findRisque(id));
    }

    @Override
    public List<RisqueDTO> getAll() {
        return mapper.toDTOList(repository.findAll());
    }

    @Override
    public void delete(Long id) {
        repository.delete(findRisque(id));
    }

    private Risque findRisque(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Risque not found"));
    }

    private DossierContentieux findDossier(Long id) {
        if (id == null) {
            return null;
        }
        return dossierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dossier not found"));
    }
}
