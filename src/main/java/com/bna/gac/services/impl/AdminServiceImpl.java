package com.bna.gac.services.impl;

import com.bna.gac.dto.AdminDTO;
import com.bna.gac.entities.Admin;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.AdminMapper;
import com.bna.gac.repositories.AdminRepository;
import com.bna.gac.services.AdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository repository;
    private final AdminMapper mapper;

    @Override
    public AdminDTO create(AdminDTO dto) {
        Admin admin = mapper.toEntity(dto);
        return mapper.toDto(repository.save(admin));
    }

    @Override
    public AdminDTO update(Long id, AdminDTO dto) {
        Admin admin = findAdmin(id);
        admin.setUsername(dto.getUsername());
        admin.setEmail(dto.getEmail());
        return mapper.toDto(repository.save(admin));
    }

    @Override
    public List<AdminDTO> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public AdminDTO getById(Long id) {
        return mapper.toDto(findAdmin(id));
    }

    @Override
    public void delete(Long id) {
        repository.delete(findAdmin(id));
    }

    private Admin findAdmin(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));
    }
}
