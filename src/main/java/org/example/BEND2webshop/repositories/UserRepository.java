package org.example.BEND2webshop.repositories;

import org.example.BEND2webshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
