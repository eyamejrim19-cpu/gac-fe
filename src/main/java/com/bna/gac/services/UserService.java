package com.bna.gac.services;


import com.bna.gac.dto.UserRequestDTO;
import com.bna.gac.dto.UserResponseDTO;
import java.util.List;

public interface UserService {

    UserResponseDTO create(UserRequestDTO request);

    List<UserResponseDTO> getAll();

    UserResponseDTO getById(Long id);

    UserResponseDTO getByUsername(String username);

    UserResponseDTO update(Long id, UserRequestDTO request);

    void delete(Long id);

    UserResponseDTO assignRole(Long userId, Long roleId);

    UserResponseDTO toggleStatus(Long id);

    void changePassword(Long id, String currentPassword, String newPassword);

    UserResponseDTO register(com.bna.gac.dto.RegisterRequestDTO request);
}
