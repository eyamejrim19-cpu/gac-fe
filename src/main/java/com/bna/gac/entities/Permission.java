package com.bna.gac.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPermission;

    private String code;
    private String description;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;
}