package com.bna.gac.repositories;

import com.bna.gac.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByTel(String tel);

    Optional<Client> findByEmail(String email);

    Optional<Client> findByCin(String cin);

    Optional<Client> findByRne(String rne);
}
