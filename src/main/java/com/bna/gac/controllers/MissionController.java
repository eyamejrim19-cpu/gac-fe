package com.bna.gac.controllers;

import com.bna.gac.dto.MissionDTO;
import com.bna.gac.services.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @GetMapping
    public List<MissionDTO> getAllMissions() {
        return missionService.getAll();
    }

    @GetMapping("/{id}")
    public MissionDTO getMissionById(@PathVariable Long id) {
        return missionService.getById(id);
    }

    @PostMapping
    public MissionDTO createMission(@RequestBody MissionDTO dto) {
        return missionService.create(dto);
    }

    @PutMapping("/{id}")
    public MissionDTO updateMission(@PathVariable Long id, @RequestBody MissionDTO dto) {
        return missionService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteMission(@PathVariable Long id) {
        missionService.delete(id);
    }
}

