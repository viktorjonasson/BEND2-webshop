package org.example.BEND2webshop.repositories;

import org.example.BEND2webshop.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, UUID> {
    AppUser findByUsernameIgnoreCase(String username);
    public Optional<AppUser> findById(UUID id);
}
