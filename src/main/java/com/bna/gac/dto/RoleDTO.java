package com.bna.gac.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data

public class RoleDTO {

    private Long idRole;
    @NotBlank
    private String name;
    private String description;
    private Set<Long> permissionIds;
}
