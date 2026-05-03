package com.bna.gac.services.impl;

import com.bna.gac.dto.RegisterRequestDTO;
import com.bna.gac.dto.UserRequestDTO;
import com.bna.gac.dto.UserResponseDTO;
import com.bna.gac.entities.Role;
import com.bna.gac.entities.User;
import com.bna.gac.exceptions.BadRequestException;
import com.bna.gac.exceptions.ResourceConflictException;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.UserMapper;
import com.bna.gac.repositories.RoleRepository;
import com.bna.gac.repositories.UserRepository;
import com.bna.gac.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO create(UserRequestDTO request) {
        if (request.getUsername() == null || request.getPassword() == null) {
            throw new BadRequestException("Username and password required");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResourceConflictException("Username already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role defaultRole = roleRepository.findByName("RESPONSABLE")
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);
        user.setRoles(roles);

        User saved = userRepository.save(user);
        return userMapper.toResponseDto(saved);
    }

    @Override
    public List<UserResponseDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toResponseDto(user);
    }

    @Override
    public UserResponseDTO update(Long id, UserRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User saved = userRepository.save(user);
        return userMapper.toResponseDto(saved);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public UserResponseDTO assignRole(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        user.getRoles().add(role);
        User saved = userRepository.save(user);
        return userMapper.toResponseDto(saved);
    }

    @Override
    public UserResponseDTO register(RegisterRequestDTO request) {
        if (request.getUsername() == null || request.getPassword() == null) {
            throw new BadRequestException("Username and password required");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResourceConflictException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role defaultRole = roleRepository.findByName("RESPONSABLE")
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);
        user.setRoles(roles);

        User saved = userRepository.save(user);
        return userMapper.toResponseDto(saved);
    }
}
