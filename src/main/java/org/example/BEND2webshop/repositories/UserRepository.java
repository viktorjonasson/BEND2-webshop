package org.example.BEND2webshop.repositories;

import org.example.BEND2webshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    public User findUserByUsername(String username);
    public Optional<User> findById(UUID id);
}
