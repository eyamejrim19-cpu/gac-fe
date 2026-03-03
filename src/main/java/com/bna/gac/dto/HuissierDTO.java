package com.bna.gac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HuissierDTO {

    private Long id;
    private String username;
    private String email;
    private Boolean enabled;
    private String cabinet;
}
