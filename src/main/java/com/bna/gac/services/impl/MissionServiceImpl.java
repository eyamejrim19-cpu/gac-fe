package com.bna.gac.services.impl;

import com.bna.gac.dto.MissionDTO;
import com.bna.gac.entities.Affaire;
import com.bna.gac.entities.Mission;
import com.bna.gac.entities.Prestataire;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.MissionMapper;
import com.bna.gac.repositories.AffaireRepository;
import com.bna.gac.repositories.MissionRepository;
import com.bna.gac.repositories.PrestataireRepository;
import com.bna.gac.services.MissionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MissionServiceImpl implements MissionService {

    private final MissionRepository missionRepository;
    private final PrestataireRepository prestataireRepository;
    private final AffaireRepository affaireRepository;
    private final MissionMapper missionMapper;

    @Override
    public MissionDTO create(MissionDTO dto) {
        Mission mission = missionMapper.toEntity(dto);
        mission.setPrestataire(findPrestataire(dto.getPrestataireId()));
        mission.setAffaire(findAffaire(dto.getAffaireId()));
        return missionMapper.toDto(missionRepository.save(mission));
    }

    @Override
    public MissionDTO update(Long id, MissionDTO dto) {
        Mission mission = findMission(id);
        mission.setTypeMission(dto.getTypeMission());
        mission.setDateDebut(dto.getDateDebut().atStartOfDay());
        mission.setDateFin(dto.getDateFin() != null ? dto.getDateFin().atStartOfDay() : null);
        mission.setStatut(dto.getStatut());
        mission.setResultat(dto.getResultat());
        mission.setCommentaire(dto.getCommentaire());

        if (dto.getPrestataireId() != null) {
            mission.setPrestataire(findPrestataire(dto.getPrestataireId()));
        }
        if (dto.getAffaireId() != null) {
            mission.setAffaire(findAffaire(dto.getAffaireId()));
        }

        return missionMapper.toDto(missionRepository.save(mission));
    }

    @Override
    public MissionDTO getById(Long id) {
        return missionMapper.toDto(findMission(id));
    }

    @Override
    public List<MissionDTO> getAll() {
        return missionMapper.toDtoList(missionRepository.findAll());
    }

    @Override
    public List<MissionDTO> getByAffaireId(Long affaireId) {
        return missionMapper.toDtoList(missionRepository.findByAffaire_IdAffaire(affaireId));
    }

    @Override
    public void delete(Long id) {
        missionRepository.delete(findMission(id));
    }

    private Mission findMission(Long id) {
        return missionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mission not found"));
    }

    private Prestataire findPrestataire(Long prestataireId) {
        return prestataireRepository.findById(prestataireId)
                .orElseThrow(() -> new ResourceNotFoundException("Prestataire not found"));
    }

    private Affaire findAffaire(Long affaireId) {
        if (affaireId == null) return null;
        return affaireRepository.findById(affaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Affaire not found"));
    }
}




