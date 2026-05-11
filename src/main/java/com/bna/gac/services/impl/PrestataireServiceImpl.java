package com.bna.gac.services.impl;

import com.bna.gac.dto.PrestataireDTO;
import com.bna.gac.entities.Prestataire;
import com.bna.gac.entities.TypePrestataire;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.PrestataireMapper;
import com.bna.gac.repositories.PrestataireRepository;
import com.bna.gac.services.PrestataireService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrestataireServiceImpl implements PrestataireService {

    private final PrestataireRepository prestataireRepository;
    private final PrestataireMapper prestataireMapper;

    @Override
    public PrestataireDTO create(PrestataireDTO prestataire) {
        Prestataire entity = prestataireMapper.toEntity(prestataire);
        return prestataireMapper.toDTO(prestataireRepository.save(entity));
    }

    @Override
    public List<PrestataireDTO> getAll() {
        return prestataireMapper.toDTOList(prestataireRepository.findAll());
    }

    @Override
    public PrestataireDTO getById(Long id) {
        return prestataireMapper.toDTO(findPrestataire(id));
    }

    @Override
    public List<PrestataireDTO> getByType(TypePrestataire type) {
        return prestataireMapper.toDTOList(prestataireRepository.findByTypePrestataire(type));
    }

    @Override
    public PrestataireDTO update(Long id, PrestataireDTO prestataire) {
        Prestataire existing = findPrestataire(id);
        existing.setNom(prestataire.getNom());
        existing.setTelephone(prestataire.getTelephone());
        existing.setEmail(prestataire.getEmail());
        existing.setAdresse(prestataire.getAdresse());
        existing.setTypePrestataire(prestataireMapper.map(prestataire.getType()));
        existing.setRib(prestataire.getRib());
        return prestataireMapper.toDTO(prestataireRepository.save(existing));
    }

    @Override
    public PrestataireDTO updateStatus(Long id, boolean actif) {
        Prestataire prestataire = findPrestataire(id);
        prestataire.setActif(actif);
        return prestataireMapper.toDTO(prestataireRepository.save(prestataire));
    }

    @Override
    public void delete(Long id) {
        prestataireRepository.delete(findPrestataire(id));
    }

    private Prestataire findPrestataire(Long id) {
        return prestataireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prestataire not found"));
    }
}


