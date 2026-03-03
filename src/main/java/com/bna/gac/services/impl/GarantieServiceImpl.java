package com.bna.gac.services.impl;

import com.bna.gac.dto.GarantieDto;
import com.bna.gac.entities.Garantie;
import com.bna.gac.entities.Risque;
import com.bna.gac.mapper.GarantieMapper;
import com.bna.gac.repositories.PrestataireRepository;
import com.bna.gac.repositories.RisqueRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GarantieServiceImpl implements GarantieService {

    private final PrestataireRepository.GarantieRepository garantieRepository;
    private final RisqueRepository risqueRepository;
    private final GarantieMapper garantieMapper;

    @Override
    public GarantieDto create(GarantieDto dto) {

        Garantie garantie = garantieMapper.toEntity(dto);

        if (dto.getRisqueIds() != null) {
            List<Risque> risques = risqueRepository.findAllById(dto.getRisqueIds());
            risques.forEach(r -> r.getGaranties(garantie));
            garantie.setRisques(risques);
        }

        return garantieMapper.toDto(garantieRepository.save(garantie));
    }

    @Override
    public GarantieDto update(Long id, GarantieDto dto) {

        Garantie garantie = garantieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Garantie not found"));

        garantie.setTypeGarantie(dto.getTypeGarantie());
        garantie.setDescription(dto.getDescription());
        garantie.setValeur(dto.getValeur());
        garantie.setStatut(dto.getStatut());

        if (dto.getRisqueIds() != null) {
            List<Risque> risques = risqueRepository.findAllById(dto.getRisqueIds());
            risques.forEach(r -> r.getGaranties(garantie));
            garantie.setRisques(risques);
        }

        return garantieMapper.toDto(garantieRepository.save(garantie));
    }

    @Override
    public GarantieDto getById(Long id) {
        Garantie garantie = garantieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Garantie not found"));
        return garantieMapper.toDto(garantie);
    }

    @Override
    public List<GarantieDto> getAll() {
        return garantieMapper.toDtoList(garantieRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        garantieRepository.deleteById(id);
    }
}
