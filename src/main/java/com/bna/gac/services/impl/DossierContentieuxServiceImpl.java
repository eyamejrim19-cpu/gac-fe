package com.bna.gac.services.impl;

import com.bna.gac.dto.DashboardStatsDTO;
import com.bna.gac.dto.DossierContentieuxDTO;
import com.bna.gac.entities.ChargeDossier;
import com.bna.gac.entities.Client;
import com.bna.gac.entities.DossierContentieux;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.DossierContentieuxMapper;
import com.bna.gac.repositories.ChargeDossierRepository;
import com.bna.gac.repositories.ClientRepository;
import com.bna.gac.repositories.DossierContentieuxRepository;
import com.bna.gac.services.DossierContentieuxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DossierContentieuxServiceImpl implements DossierContentieuxService {

    private final DossierContentieuxRepository dossierRepository;
    private final ClientRepository clientRepository;
    private final ChargeDossierRepository chargeDossierRepository;
    private final DossierContentieuxMapper mapper;

    @Override
    public DossierContentieuxDTO create(DossierContentieuxDTO dto) {
        DossierContentieux dossier = mapper.toEntity(dto);
        dossier.setClient(findClient(dto.getClientId()));
        dossier.setChargeDossier(findChargeDossier(dto.getChargeDossierId()));
        return mapper.toDto(dossierRepository.save(dossier));
    }

    @Override
    public DossierContentieuxDTO update(Long id, DossierContentieuxDTO dto) {
        DossierContentieux dossier = findDossier(id);
        dossier.setReference(dto.getReference());
        dossier.setDateOuverture(dto.getDateOuverture() == null ? null : dto.getDateOuverture().atStartOfDay());
        dossier.setStatut(dto.getStatut());
        dossier.setNiveauRisque(dto.getNiveauRisque());
        dossier.setDateCloture(dto.getDateCloture() == null ? null : dto.getDateCloture().atStartOfDay());
        dossier.setMontantInitial(dto.getMontantInitial());
        dossier.setMontantRecupere(dto.getMontantRecupere());
        if (dto.getClientId() != null) {
            dossier.setClient(findClient(dto.getClientId()));
        }
        dossier.setChargeDossier(findChargeDossier(dto.getChargeDossierId()));
        return mapper.toDto(dossierRepository.save(dossier));
    }

    @Override
    public List<DossierContentieuxDTO> getAll() {
        return mapper.toDtoList(dossierRepository.findAll());
    }

    @Override
    public List<DossierContentieuxDTO> findRecent(int limit) {
        return mapper.toDtoList(dossierRepository.findTop5ByOrderByIdDossierDesc());
    }

    @Override
    public DossierContentieuxDTO getById(Long id) {
        return mapper.toDto(findDossier(id));
    }

    @Override
    public void delete(Long id) {
        dossierRepository.delete(findDossier(id));
    }

    @Override
    public DashboardStatsDTO getStats() {
        DashboardStatsDTO dto = new DashboardStatsDTO();
        long total = dossierRepository.count();
        long actifs = dossierRepository.countByStatut("EN_COURS");
        long clotures = dossierRepository.countByStatut("CLOTURE");

        dto.setTotalDossiers(total);
        dto.setDossiersActifs(actifs);
        dto.setDossiersClotures(clotures);
        dto.setTauxRecouvrement(total == 0 ? 0 : (clotures * 100.0 / total));

        return dto;
    }

    private DossierContentieux findDossier(Long id) {
        return dossierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dossier not found"));
    }

    private Client findClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    private ChargeDossier findChargeDossier(Long id) {
        if (id == null) {
            return null;
        }
        return chargeDossierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Charge dossier not found"));
    }
}
