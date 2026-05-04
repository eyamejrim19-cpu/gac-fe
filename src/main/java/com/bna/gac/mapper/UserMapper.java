package com.bna.gac.mapper;

import com.bna.gac.dto.UserRequestDTO;
import com.bna.gac.dto.UserResponseDTO;
import com.bna.gac.entities.Role;
import com.bna.gac.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "nom", source = "nom")
    @Mapping(target = "prenom", source = "prenom")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToNames")
    UserResponseDTO toResponseDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nom", source = "nom")
    @Mapping(target = "prenom", source = "prenom")
    @Mapping(target = "telephone", ignore = true)
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserRequestDTO dto);

    @Named("rolesToNames")
    default List<String> rolesToNames(Set<Role> roles) {
        if (roles == null) return List.of();
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
