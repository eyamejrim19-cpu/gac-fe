package com.bna.gac.controllers;

import com.bna.gac.dto.MissionDTO;
import com.bna.gac.services.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @GetMapping
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public List<MissionDTO> getAllMissions() {
        return missionService.getAll();
    }

    @GetMapping("/affaire/{affaireId}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public List<MissionDTO> getByAffaire(@PathVariable Long affaireId) {
        return missionService.getByAffaireId(affaireId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE', 'ADMIN')")
    public MissionDTO getMissionById(@PathVariable Long id) {
        return missionService.getById(id);
    }

    // Only ChargeDossier creates and manages missions
    @PostMapping
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER')")
    public MissionDTO createMission(@RequestBody MissionDTO dto) {
        return missionService.create(dto);
    }

    // ChargeDossier updates mission content; Responsable validates via /validate endpoint
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER')")
    public MissionDTO updateMission(@PathVariable Long id, @RequestBody MissionDTO dto) {
        return missionService.update(id, dto);
    }

    // Responsable validates a mission (sets status to VALIDEE)
    @PutMapping("/{id}/validate")
    @PreAuthorize("hasAnyRole('RESPONSABLE')")
    public MissionDTO validateMission(@PathVariable Long id) {
        return missionService.validate(id);
    }

    // Responsable rejects a mission (sets status back to EN_COURS)
    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('RESPONSABLE')")
    public MissionDTO rejectMission(@PathVariable Long id) {
        return missionService.reject(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER')")
    public void deleteMission(@PathVariable Long id) {
        missionService.delete(id);
    }
}
