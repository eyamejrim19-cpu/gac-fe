package com.bna.gac.services.impl;

import com.bna.gac.dto.RisqueDTO;
import com.bna.gac.entities.DossierContentieux;
import com.bna.gac.entities.Risque;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.RisqueMapper;
import com.bna.gac.repositories.DossierContentieuxRepository;
import com.bna.gac.repositories.RisqueRepository;
import com.bna.gac.services.RisqueService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        // Auto-calculate montantTotal
        double principal = dto.getMontantPrincipal() != null ? dto.getMontantPrincipal() : 0;
        double interet   = dto.getMontantInteret()   != null ? dto.getMontantInteret()   : 0;
        risque.setMontantTotal(principal + interet);
        Risque saved = repository.save(risque);
        if (saved.getReference() == null || saved.getReference().isBlank()) {
            saved.setReference(generateReference(saved.getIdRisque()));
            saved = repository.save(saved);
        }
        return mapper.toDto(saved);
    }

    /** Runs once on startup — backfills references for any existing risque that has none. */
    @PostConstruct
    public void backfillReferences() {
        List<Risque> all = repository.findAll();
        for (Risque r : all) {
            if (r.getReference() == null || r.getReference().isBlank()) {
                r.setReference(generateReference(r.getIdRisque()));
                repository.save(r);
            }
        }
    }

    private String generateReference(Long id) {
        String year = String.valueOf(LocalDate.now().getYear());
        return "RSQ-" + year + "-" + String.format("%03d", id);
    }

    @Override
    public RisqueDTO update(Long id, RisqueDTO dto) {
        Risque risque = findRisque(id);
        risque.setMontantPrincipal(dto.getMontantPrincipal());
        risque.setMontantInteret(dto.getMontantInteret());
        // Auto-calculate montantTotal
        double principal = dto.getMontantPrincipal() != null ? dto.getMontantPrincipal() : 0;
        double interet   = dto.getMontantInteret()   != null ? dto.getMontantInteret()   : 0;
        risque.setMontantTotal(principal + interet);
        risque.setTauxInteret(dto.getTauxInteret());
        risque.setPeriode(dto.getPeriode());
        risque.setDateContrat(dto.getDateContrat() == null ? null : dto.getDateContrat().atStartOfDay());
        risque.setDateDeblocage(dto.getDateDeblocage() == null ? null : dto.getDateDeblocage().atStartOfDay());
        risque.setDateEcheance(dto.getDateEcheance() == null ? null : dto.getDateEcheance().atStartOfDay());
        risque.setDossier(findDossier(dto.getDossierId()));
        return mapper.toDto(repository.save(risque));
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
