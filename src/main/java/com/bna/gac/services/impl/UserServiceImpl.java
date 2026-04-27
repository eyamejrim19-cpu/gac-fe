package com.bna.gac.services.impl;

import com.bna.gac.dto.RegisterRequestDTO;
import com.bna.gac.entities.Role;
import com.bna.gac.entities.User;
import com.bna.gac.exceptions.BadRequestException;
import com.bna.gac.exceptions.ResourceConflictException;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.repositories.RoleRepository;
import com.bna.gac.repositories.UserRepository;
import com.bna.gac.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String saveUser(RegisterRequestDTO request) {
        if (request.getUsername() == null || request.getPassword() == null) {
            throw new BadRequestException("Username and password required");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResourceConflictException("Username already exists");
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("ROLE_USER");
                    return roleRepository.save(newRole);
                });

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully";
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(getUserById(id));
    }

    @Override
    public User assignRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        user.getRoles().add(role);
        return userRepository.save(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
