package com.bna.gac.controllers;

import com.bna.gac.entities.Mission;

import com.bna.gac.services.impl.MissionService;
import com.bna.gac.services.impl.MissionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/missions")
public class MissionController {

    private final MissionServiceImpl missionService;

    public MissionController(MissionService missionService) {
        this.missionService = (MissionServiceImpl) missionService;
    }

    // Get all
    @GetMapping
    public List<Mission> getAllMissions() {
        return missionService.getAllMissions();
    }
 /*
    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<Mission> getMissionById(@PathVariable Long id) {
        return missionService.getMissionById(id)
                .map(ResponseEntity.ok().toString());}
*/
    // Get conventionnées
    @GetMapping("/conventionnees")
    public List<Mission> getConventionnees() {
        return missionService.getMissionsByConvention(true);
    }

    // Get non conventionnées
    @GetMapping("/non-conventionnees")
    public List<Mission> getNonConventionnees() {
        return missionService.getMissionsByConvention(false);
    }
}
