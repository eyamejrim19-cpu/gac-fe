package com.bna.gac.mapper;


import com.bna.gac.dto.ClientDTO;
import com.bna.gac.entities.Client;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDTO toDto(Client client);

    Client toEntity(ClientDTO dto);

    List<ClientDTO> toDtoList(List<Client> clients);
}