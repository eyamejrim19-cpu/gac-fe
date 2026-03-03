package com.bna.gac.services.impl;

import com.bna.gac.dto.MissionDTO;
import com.bna.gac.entities.Mission;
import com.bna.gac.entities.Prestataire;
import com.bna.gac.mapper.MissionMapper;
import com.bna.gac.repositories.MissionRepository;
import com.bna.gac.repositories.PrestataireRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionServiceImpl implements MissionService {

    private final MissionRepository missionRepository;
    private final PrestataireRepository prestataireRepository;
    private final MissionMapper missionMapper;

    @Override
    public MissionDTO create(MissionDTO dto) {

        Prestataire prestataire = prestataireRepository.findById(dto.getPrestataireId())
                .orElseThrow(() -> new RuntimeException("Prestataire not found"));

        Mission mission = missionMapper.toEntity(dto);
        mission.setPrestataire(prestataire);

        return missionMapper.toDto(missionRepository.save(mission));
    }

    @Override
    public MissionDTO update(Long id, MissionDTO dto) {

        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mission not found"));

        mission.setTypeMission(dto.getTypeMission());
        mission.setDateDebut(dto.getDateDebut().atStartOfDay());
        mission.setDateFin(dto.getDateFin().atStartOfDay());
        mission.setStatut(dto.getStatut());
        mission.setResultat(dto.getResultat());

        if (dto.getPrestataireId() != null) {
            Prestataire prestataire = prestataireRepository.findById(dto.getPrestataireId())
                    .orElseThrow(() -> new RuntimeException("Prestataire not found"));
            mission.setPrestataire(prestataire);
        }

        return missionMapper.toDto(missionRepository.save(mission));
    }

    @Override
    public MissionDTO getById(Long id) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mission not found"));
        return missionMapper.toDto(mission);
    }

    @Override
    public List<MissionDTO> getAll() {
        return missionMapper.toDtoList(missionRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        if (!missionRepository.existsById(id)) {
            throw new RuntimeException("Mission not found");
        }
        missionRepository.deleteById(id);
    }
}
