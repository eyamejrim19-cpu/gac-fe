package com.bna.gac.mapper;

import com.bna.gac.dto.AdminDTO;
import com.bna.gac.entities.Admin;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminDTO toDto(Admin entity);

    Admin toEntity(AdminDTO dto);

    List<AdminDTO> toDtoList(List<Admin> list);
}
