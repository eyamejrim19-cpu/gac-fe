package com.bna.gac.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PermissionDTO {

    private Long idPermission;
    @NotBlank
    private String code;
    private String description;
}
