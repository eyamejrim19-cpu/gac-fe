package com.bna.gac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bna.gac.entities.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByTel(String tel);
    Optional<Client> findByEmail(String email);
    Optional<Client> findByCin(String cin);
    Optional<Client> findByRne(String rne);
}
