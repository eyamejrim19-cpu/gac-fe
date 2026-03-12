package com.bna.gac.services;

import com.bna.gac.dto.ClientDTO;
import java.util.List;

public interface ClientService {

    ClientDTO save(ClientDTO dto);

    List<ClientDTO> findAll();

    ClientDTO findById(Long id);

    void delete(Long id);
}