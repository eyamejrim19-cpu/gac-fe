package com.bna.gac.services;

import com.bna.gac.dto.ClientDTO;
import com.bna.gac.dto.StatusUpdateDTO;
import org.springframework.data.domain.Page;

public interface ClientService {

    Page<ClientDTO> findAll(int page, int size, String search, String type, Boolean active);

    ClientDTO create(ClientDTO dto);

    ClientDTO update(Long id, ClientDTO dto);

    /**
     * Updates ONLY the active field. No other field is read or written.
     * This is the safe path for the toggle-status feature.
     */
    ClientDTO updateStatus(Long id, StatusUpdateDTO dto);

    ClientDTO getById(Long id);

    void delete(Long id);

    ClientDTO deactivate(Long id);

    ClientDTO reactivate(Long id);
}