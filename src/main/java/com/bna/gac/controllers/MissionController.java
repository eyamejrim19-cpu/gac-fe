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

    @PostMapping
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE')")
    public MissionDTO createMission(@RequestBody MissionDTO dto) {
        return missionService.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHARGEDOSSIER', 'RESPONSABLE')")
    public MissionDTO updateMission(@PathVariable Long id, @RequestBody MissionDTO dto) {
        return missionService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('RESPONSABLE')")
    public void deleteMission(@PathVariable Long id) {
        missionService.delete(id);
    }
}
