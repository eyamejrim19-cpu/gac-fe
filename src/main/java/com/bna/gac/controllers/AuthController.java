package com.bna.gac.controllers;

import com.bna.gac.dtos.UserRequestDTO;
import com.bna.gac.dtos.UserResponseDTO;
import com.bna.gac.entities.User;
import com.bna.gac.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody UserRequestDTO request) {

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = userService.save(user);

        return UserResponseDTO.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .build();
    }

    @GetMapping("/test")
    public String test() {
        return "SECURED OK";
    }
}