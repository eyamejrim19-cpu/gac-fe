package com.bna.gac.repositories;

import com.bna.gac.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.ScopedValue;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    <T> ScopedValue<T> findByUsername(String username);
}