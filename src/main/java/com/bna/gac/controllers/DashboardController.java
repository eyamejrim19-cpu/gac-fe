package com.bna.gac.controllers;

import com.bna.gac.dto.DashboardStatsDTO;
import com.bna.gac.services.DossierContentieuxService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DossierContentieuxService service;

    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('RESPONSABLE', 'ADMIN')")
    public DashboardStatsDTO getStats() {
        return service.getStats();
    }
}

