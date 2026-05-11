package com.bna.gac.services.impl;

import com.bna.gac.dto.DashboardStatsDTO;
import com.bna.gac.dto.DossierContentieuxDTO;
import com.bna.gac.entities.ChargeDossier;
import com.bna.gac.entities.Client;
import com.bna.gac.entities.DossierContentieux;
import com.bna.gac.entities.enums.DossierStatus;
import com.bna.gac.exceptions.BadRequestException;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.DossierContentieuxMapper;
import com.bna.gac.repositories.*;
import com.bna.gac.services.DossierContentieuxService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DossierContentieuxServiceImpl implements DossierContentieuxService {

    private final DossierContentieuxRepository dossierRepository;
    private final ClientRepository clientRepository;
    private final ChargeDossierRepository chargeDossierRepository;
    private final MissionRepository missionRepository;
    private final FactureRepository factureRepository;
    private final PrestataireRepository prestataireRepository;
    private final DossierContentieuxMapper mapper;

    @Override
    @Transactional
    public DossierContentieuxDTO create(DossierContentieuxDTO dto) {
        DossierContentieux dossier = mapper.toEntity(dto);
        dossier.setClient(findClient(dto.getClientId()));
        dossier.setChargeDossier(findChargeDossier(dto.getChargeDossierId()));
        
        if (dossier.getStatut() == null) {
            dossier.setStatut(DossierStatus.OUVERT);
        }
        
        return mapper.toDto(dossierRepository.save(dossier));
    }

    @Override
    @Transactional
    public DossierContentieuxDTO update(Long id, DossierContentieuxDTO dto) {
        DossierContentieux dossier = findDossier(id);
        
        DossierStatus newStatus = dto.getStatut() != null ? DossierStatus.valueOf(dto.getStatut()) : null;
        if (newStatus != null && !newStatus.equals(dossier.getStatut())) {
            validateStatusTransition(dossier.getStatut(), newStatus);
            dossier.setStatut(newStatus);
        }

        dossier.setReference(dto.getReference());
        dossier.setDateOuverture(dto.getDateOuverture() == null ? null : dto.getDateOuverture().atStartOfDay());
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

    private void validateStatusTransition(DossierStatus current, DossierStatus next) {
        if (current == DossierStatus.CLOTURE) {
            throw new BadRequestException("Cannot modify a closed dossier");
        }
    }

    @Override
    public List<DossierContentieuxDTO> getAll() {
        return mapper.toDtoList(dossierRepository.findAll());
    }

    @Override
    public List<DossierContentieuxDTO> findRecent(int limit) {
        return mapper.toDtoList(dossierRepository.findByOrderByIdDossierDesc(PageRequest.of(0, limit)));
    }

    @Override
    public DossierContentieuxDTO getById(Long id) {
        return mapper.toDto(findDossier(id));
    }

    @Override
    public void delete(Long id) {
        DossierContentieux dossier = findDossier(id);
        if (dossier.getStatut() == DossierStatus.CLOTURE) {
            throw new BadRequestException("Cannot delete a closed dossier");
        }
        dossierRepository.delete(dossier);
    }

    @Override
    public DossierContentieuxDTO validate(Long id) {
        DossierContentieux dossier = findDossier(id);
        if (dossier.getStatut() != DossierStatus.EN_ATTENTE_VALIDATION
                && dossier.getStatut() != DossierStatus.EN_COURS) {
            throw new BadRequestException("Le dossier doit être en cours ou en attente de validation pour être validé");
        }
        dossier.setStatut(DossierStatus.VALIDE);
        return mapper.toDto(dossierRepository.save(dossier));
    }

    @Override
    public DossierContentieuxDTO reject(Long id) {
        DossierContentieux dossier = findDossier(id);
        if (dossier.getStatut() != DossierStatus.EN_ATTENTE_VALIDATION
                && dossier.getStatut() != DossierStatus.EN_COURS) {
            throw new BadRequestException("Le dossier doit être en cours ou en attente de validation pour être rejeté");
        }
        dossier.setStatut(DossierStatus.EN_COURS);
        return mapper.toDto(dossierRepository.save(dossier));
    }

    @Override
    public DashboardStatsDTO getStats() {
        DashboardStatsDTO dto = new DashboardStatsDTO();
        
        long total = dossierRepository.count();
        long actifs = dossierRepository.countByStatut(DossierStatus.EN_COURS);
        long clotures = dossierRepository.countByStatut(DossierStatus.CLOTURE);

        dto.setTotalDossiers(total);
        dto.setDossiersActifs(actifs);
        dto.setDossiersClotures(clotures);

        Double recupere = dossierRepository.sumMontantRecupere();
        Double impaye = dossierRepository.sumMontantImpaye();
        Double totalInitial = dossierRepository.sumMontantInitial();
        
        dto.setMontantTotalRecupere(recupere != null ? recupere : 0.0);
        dto.setMontantTotalImpaye(impaye != null ? impaye : 0.0);
        
        // Correct Financial Recovery Rate calculation
        dto.setTauxRecouvrement((totalInitial == null || totalInitial == 0) ? 0 : (dto.getMontantTotalRecupere() * 100.0 / totalInitial));

        dto.setMissionsEnCours(missionRepository.countByStatut("EN_COURS"));
        dto.setMissionsTerminees(missionRepository.countByStatut("TERMINEE"));
        
        dto.setFacturesEnAttente(factureRepository.countByStatut("EN_ATTENTE_VALIDATION"));
        dto.setFacturesPayees(factureRepository.countByStatut("PAYEE"));

        dto.setPrestatairesActifs(prestataireRepository.countByActif(true));
        dto.setClientsActifs(clientRepository.count());

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
