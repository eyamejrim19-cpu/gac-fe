package com.bna.gac.controllers;

import com.bna.gac.dto.UserRequestDTO;
import com.bna.gac.dto.UserResponseDTO;
import com.bna.gac.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/chargedossiers")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserResponseDTO>> getChargeDossiers() {
        List<UserResponseDTO> users = userService.getAll().stream()
                .filter(u -> u.getRoles() != null && u.getRoles().contains("CHARGEDOSSIER"))
                .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDTO> getMe(
            org.springframework.security.core.Authentication authentication) {
        UserResponseDTO user = userService.getByUsername(authentication.getName());
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDTO> updateMe(
            org.springframework.security.core.Authentication authentication,
            @RequestBody UserRequestDTO request) {
        UserResponseDTO user = userService.getByUsername(authentication.getName());
        UserResponseDTO updated = userService.update(user.getId(), request);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/me/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changeMyPassword(
            org.springframework.security.core.Authentication authentication,
            @RequestBody java.util.Map<String, String> body) {
        UserResponseDTO user = userService.getByUsername(authentication.getName());
        userService.changePassword(user.getId(), body.get("currentPassword"), body.get("newPassword"));
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO user = userService.getById(id);
        return ResponseEntity.ok(user);
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO user = userService.create(request);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO request) {
        UserResponseDTO user = userService.update(id, request);
        return ResponseEntity.ok(user);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{userId}/roles/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> assignRole(@PathVariable Long userId, @PathVariable Long roleId) {
        UserResponseDTO user = userService.assignRole(userId, roleId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/toggle-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> toggleStatus(@PathVariable Long id) {
        UserResponseDTO user = userService.toggleStatus(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{id}/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @RequestBody java.util.Map<String, String> body) {
        userService.changePassword(id, body.get("currentPassword"), body.get("newPassword"));
        return ResponseEntity.ok().build();
    }
}
