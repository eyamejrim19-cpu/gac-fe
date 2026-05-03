package com.bna.gac.services;

import com.bna.gac.dto.AdminDTO;

import java.util.List;

public interface AdminService {

    AdminDTO create(AdminDTO dto);

    AdminDTO update(Long id, AdminDTO dto);

    List<AdminDTO> getAll();

    AdminDTO getById(Long id);

    void delete(Long id);
}


