package com.bna.gac.services.impl;

import com.bna.gac.dto.PrestataireDTO;
import com.bna.gac.entities.Prestataire;
import com.bna.gac.entities.TypePrestataire;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.PrestataireMapper;
import com.bna.gac.repositories.PrestataireRepository;
import com.bna.gac.services.PrestataireService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PrestataireServiceImpl implements PrestataireService {

    private final PrestataireRepository prestataireRepository;
    private final PrestataireMapper     prestataireMapper;

    @Override
    public PrestataireDTO create(PrestataireDTO dto) {
        Prestataire entity = prestataireMapper.toEntity(dto);
        return prestataireMapper.toDTO(prestataireRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrestataireDTO> getAll() {
        return prestataireMapper.toDTOList(prestataireRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public PrestataireDTO getById(Long id) {
        return prestataireMapper.toDTO(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrestataireDTO> getByType(TypePrestataire type) {
        return prestataireMapper.toDTOList(
                prestataireRepository.findByTypePrestataire(type));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PrestataireDTO> getPaginated(int page, int size, String search,
                                             TypePrestataire type, Boolean actif) {

        PageRequest pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "idPrestataire"));

        // Normalise search — null means "no filter"
        String searchTerm = (search == null || search.isBlank()) ? null : search.trim();

        // Boolean flags tell the repository whether to apply each optional filter.
        // This avoids the unreliable ":param IS NULL" pattern with enum columns.
        boolean filterByType  = (type  != null);
        boolean filterByActif = (actif != null);

        // Safe defaults for the actual param values when the filter is disabled
        TypePrestataire safeType  = filterByType  ? type  : TypePrestataire.AVOCAT;
        boolean         safeActif = filterByActif ? actif : true;

        return prestataireRepository
                .findPaginated(filterByType, safeType, filterByActif, safeActif, searchTerm, pageable)
                .map(prestataireMapper::toDTO);
    }

    @Override
    public PrestataireDTO update(Long id, PrestataireDTO dto) {
        Prestataire existing = findOrThrow(id);
        prestataireMapper.updateEntityFromDTO(dto, existing);
        existing.setIdPrestataire(id);   // never let DTO overwrite the PK
        return prestataireMapper.toDTO(prestataireRepository.save(existing));
    }

    @Override
    public PrestataireDTO updateStatus(Long id, boolean actif) {
        Prestataire prestataire = findOrThrow(id);
        prestataire.setActif(actif);
        return prestataireMapper.toDTO(prestataireRepository.save(prestataire));
    }

    @Override
    public void delete(Long id) {
        prestataireRepository.delete(findOrThrow(id));
    }

    // ── helpers ────────────────────────────────────────────────────────────────
    private Prestataire findOrThrow(Long id) {
        return prestataireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Prestataire introuvable : id=" + id));
    }
}
