package com.bna.gac.mapper;

import com.bna.gac.dto.UserRequestDTO;
import com.bna.gac.dto.UserResponseDTO;
import com.bna.gac.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    UserResponseDTO toResponseDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nom", ignore = true)
    @Mapping(target = "prenom", ignore = true)
    @Mapping(target = "telephone", ignore = true)
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserRequestDTO dto);
}
