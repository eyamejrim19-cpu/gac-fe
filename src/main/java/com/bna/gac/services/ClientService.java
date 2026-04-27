package com.bna.gac.services;

import com.bna.gac.dto.ClientDTO;
import java.util.List;

public interface ClientService {

    ClientDTO create(ClientDTO dto);

    ClientDTO update(Long id, ClientDTO dto);

    ClientDTO getById(Long id);

    List<ClientDTO> getAll();

    void delete(Long id);
}
