package com.bna.gac;

import com.bna.gac.entities.Role;
import com.bna.gac.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GacApplication {

	public static void main(String[] args) {
		SpringApplication.run(GacApplication.class, args);
	}

	@Bean
	CommandLineRunner initRoles(RoleRepository roleRepository) {
		return args -> {
			createRoleIfNotFound(roleRepository, "ADMIN");
			createRoleIfNotFound(roleRepository, "CHARGEDOSSIER");
			createRoleIfNotFound(roleRepository, "RESPONSABLE");
		};
	}

	private void createRoleIfNotFound(RoleRepository repo, String name) {
		repo.findByName(name).orElseGet(() -> {
			Role role = new Role();
			role.setName(name);
			role.setDescription(name + " role");
			return repo.save(role);
		});
	}
}