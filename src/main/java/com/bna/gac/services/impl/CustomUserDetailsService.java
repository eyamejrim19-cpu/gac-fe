package com.bna.gac.services.impl;

import com.bna.gac.entities.User;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> {
            // Add Role with ROLE_ prefix so Spring's hasAnyRole() works correctly
            String roleName = role.getName().startsWith("ROLE_") ? role.getName() : "ROLE_" + role.getName();
            authorities.add(new SimpleGrantedAuthority(roleName));
            // Add Permissions associated with the Role
            if (role.getPermissions() != null) {
                role.getPermissions().forEach(permission -> {
                    authorities.add(new SimpleGrantedAuthority(permission.getCode()));
                });
            }
        });

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true, true, true,
                authorities.stream().distinct().toList()
        );
    }
}

