package org.example.BEND2webshop.services;

import org.example.BEND2webshop.exceptions.UsernameNotAvailableException;
import org.example.BEND2webshop.models.AppUser;
import org.example.BEND2webshop.repositories.UserRepository;
import org.example.BEND2webshop.repositories.UserRoleRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class UserService {
    public static final String USERNAME_UNAVAILABLE = "Username is not available, pick another.";

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public void saveUser(String username, Set<String> roles, String password) {
        if (userRepository.findByUsernameIgnoreCase(username) != null) {
            throw new UsernameNotAvailableException(USERNAME_UNAVAILABLE);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userRepository.save(
                AppUser.builder()
                        .username(username)
                        .password(encoder.encode(password))
                        .roles(
                                userRoleRepository.findAll().stream().filter(
                                        r -> roles.contains(r.getName())
                                ).toList()
                        )
                        .enabled(true)
                        .build()
        );
    }
}
