package com.bna.gac.services;

import com.bna.gac.dto.ClientDTO;
import com.bna.gac.dto.StatusUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClientService {

    Page<ClientDTO> findAll(int page, int size, String search, String type, Boolean active);

    ClientDTO create(ClientDTO dto);

    ClientDTO update(Long id, ClientDTO dto);

    ClientDTO getById(Long id);

    List<ClientDTO> getAll();

    @Transactional
    ClientDTO updateStatus(Long id, StatusUpdateDTO dto);

    void delete(Long id);

    @Transactional
    ClientDTO deactivate(Long id);

    @Transactional
    ClientDTO reactivate(Long id);
}

