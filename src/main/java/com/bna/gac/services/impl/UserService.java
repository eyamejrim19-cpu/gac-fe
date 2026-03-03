package com.bna.gac.services;


import com.bna.gac.entities.User;
import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    void deleteUser(Long id);

    User assignRoleToUser(String username, String roleName);

    User save(User user);
}