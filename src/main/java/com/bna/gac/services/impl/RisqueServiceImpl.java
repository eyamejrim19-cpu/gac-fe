package com.bna.gac.services.impl;

import com.bna.gac.dto.RisqueDTO;
import com.bna.gac.entities.DossierContentieux;
import com.bna.gac.entities.Risque;
import com.bna.gac.exceptions.BadRequestException;
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
        validateRisque(dto);
        Risque risque = mapper.toEntity(dto);
        risque.setDossier(findDossier(dto.getDossierId()));
        return mapper.toDto(repository.save(risque));
    }

    @Override
    public RisqueDTO update(Long id, RisqueDTO dto) {
        validateRisque(dto);
        Risque risque = findRisque(id);
        risque.setMontantPrincipal(dto.getMontantPrincipal());
        risque.setMontantInteret(dto.getMontantInteret());
        risque.setMontantTotal(dto.getMontantTotal());
        risque.setTauxInteret(dto.getTauxInteret());
        risque.setPeriode(dto.getPeriode());
        risque.setDateContrat(dto.getDateContrat() == null ? null : dto.getDateContrat().atStartOfDay());
        risque.setDateDeblocage(dto.getDateDeblocage() == null ? null : dto.getDateDeblocage().atStartOfDay());
        risque.setDateEcheance(dto.getDateEcheance() == null ? null : dto.getDateEcheance().atStartOfDay());
        risque.setDossier(findDossier(dto.getDossierId()));
        return mapper.toDto(repository.save(risque));
    }

    private void validateRisque(RisqueDTO dto) {
        if (dto.getMontantPrincipal() == null || dto.getMontantInteret() == null || dto.getMontantTotal() == null) {
            throw new BadRequestException("All financial amounts (principal, interest, total) are required");
        }
        if (Math.abs(dto.getMontantPrincipal() + dto.getMontantInteret() - dto.getMontantTotal()) > 0.01) {
            throw new BadRequestException("Total amount must equal Principal + Interest");
        }
    }

    @Override
    public RisqueDTO getById(Long id) {
        return mapper.toDto(findRisque(id));
    }

    @Override
    public List<RisqueDTO> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public List<RisqueDTO> getByDossierId(Long dossierId) {
        return mapper.toDtoList(repository.findByDossier_IdDossier(dossierId));
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
