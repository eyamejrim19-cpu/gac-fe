package com.bna.gac.services.impl;

import com.bna.gac.dto.AdminDTO;
import com.bna.gac.entities.Admin;
import com.bna.gac.mapper.AdminMapper;
import com.bna.gac.repositories.AdminRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository repository;
    private  AdminMapper mapper;

    public AdminServiceImpl(AdminRepository repository) {
        this.repository = repository;
    }

    @Override
    public AdminDTO create(AdminDTO dto) {

        Admin admin = mapper.toEntity(dto);
        admin.setCreatedAt(LocalDateTime.now());
        admin.setEnabled(true);

        Admin saved = repository.save(admin);
        return mapper.toDto(saved);
    }

    @Override
    public List<AdminDTO> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public AdminDTO getById(Long id) {
        Admin admin = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        return mapper.toDto(admin);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
