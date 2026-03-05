package com.bna.gac.services.impl;

import com.bna.gac.dto.ClientDTO;

import java.util.List;

public interface ClientService {

    ClientDTO createClient(ClientDTO dto);

    List<ClientDTO> getAllClients();

    ClientDTO getClientById(Long id);

    ClientDTO updateClient(Long id, ClientDTO dto);

    void deleteClient(Long id);
}