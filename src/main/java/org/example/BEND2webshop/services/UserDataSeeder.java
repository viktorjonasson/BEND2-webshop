package org.example.BEND2webshop.services;

import org.example.BEND2webshop.models.User;
import org.example.BEND2webshop.models.UserRole;
import org.example.BEND2webshop.repositories.UserRepository;
import org.example.BEND2webshop.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDataSeeder {

    @Autowired
    UserRepository userRepository;
    UserRoleRepository roleRepository;

    public UserDataSeeder(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = userRoleRepository;
    }

    public void Seed() {
        if (roleRepository.findByName("admin") == null) {
            roleRepository.save(UserRole.builder().name("admin").build());
        }

        if (roleRepository.findByName("customer") == null) {
            roleRepository.save(UserRole.builder().name("customer").build());
        }

        if (userRepository.findUserByUsername("adminUser") == null) {
            userRepository.save(
                    User.builder()
                            .username("adminUser")
                            .roles(List.of(roleRepository.findByName("admin")))
                            .password("password")
                            .enabled(true)
                            .build()
            );
        }

        if (userRepository.findUserByUsername("customerUser") == null) {
            userRepository.save(
                    User.builder()
                            .username("customerUser")
                            .roles(List.of(roleRepository.findByName("customer")))
                            .password("password")
                            .enabled(true)
                            .build()
            );
        }

        userRepository.findAll().forEach(System.out::println);
        roleRepository.findAll().forEach(System.out::println);
    }
}
