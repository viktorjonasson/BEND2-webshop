package org.example.BEND2webshop.repositories;

import org.example.BEND2webshop.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    public UserRole findByName(String name);
}
