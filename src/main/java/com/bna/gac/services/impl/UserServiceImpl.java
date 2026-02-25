package com.bna.gac.services.impl;

import com.bna.gac.entities.User;
import com.bna.gac.repositories.UserRepository;
import com.bna.gac.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}