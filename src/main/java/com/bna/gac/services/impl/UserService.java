package com.bna.gac.services.impl;


import com.bna.gac.dto.RegisterRequestDTO;
import com.bna.gac.entities.User;
import java.util.List;

public interface UserService {

    String saveUser(RegisterRequestDTO request);

    List<User> getAllUsers();

    User getUserById(Long id);

    void deleteUser(Long id);

    User assignRoleToUser(String username, String roleName);

    User save(User user);
}