package com.bna.gac.controllers;

import com.bna.gac.dto.DashboardStatsDTO;
import com.bna.gac.repositories.DossierContentieuxRepository;
import com.bna.gac.services.DossierContentieuxService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DossierContentieuxService service;
    private final DossierContentieuxRepository dossierRepository;

    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('RESPONSABLE', 'ADMIN')")
    public DashboardStatsDTO getStats() {
        return service.getStats();
    }

    @GetMapping("/monthly-recovery")
    @PreAuthorize("hasAnyRole('RESPONSABLE', 'ADMIN')")
    public Map<String, Double> getMonthlyRecovery() {
        List<Object[]> rows = dossierRepository.findMonthlyRecovery(6);
        String[] monthNames = {"", "Jan", "Fév", "Mar", "Avr", "Mai", "Juin",
                                   "Juil", "Août", "Sep", "Oct", "Nov", "Déc"};
        Map<String, Double> result = new LinkedHashMap<>();
        for (Object[] row : rows) {
            int month = ((Number) row[0]).intValue();
            double total = ((Number) row[1]).doubleValue();
            result.put(monthNames[month], total);
        }
        return result;
    }
}

