package com.bna.gac.dtos;

import lombok.Data;

@Data
public class UserRequestDTO {

    private String username;
    private String email;
    private String password;
}