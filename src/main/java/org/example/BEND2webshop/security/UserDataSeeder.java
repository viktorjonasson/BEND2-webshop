package org.example.BEND2webshop.security;

import org.example.BEND2webshop.models.UserRole;
import org.example.BEND2webshop.repositories.UserRepository;
import org.example.BEND2webshop.repositories.UserRoleRepository;
import org.example.BEND2webshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class UserDataSeeder {

    @Autowired
    UserRepository userRepository;
    UserRoleRepository roleRepository;
    UserService userService;

    public UserDataSeeder(UserRepository userRepository, UserRoleRepository userRoleRepository, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = userRoleRepository;
        this.userService = userService;
    }

    public void Seed() {
        userRepository.deleteAll();
//        roleRepository.deleteAll();

        if (roleRepository.findByName("admin") == null) {
            roleRepository.save(UserRole.builder().name("admin").build());
        }

        if (roleRepository.findByName("customer") == null) {
            roleRepository.save(UserRole.builder().name("customer").build());
        }

        if (userRepository.findUserByUsername("Admin") == null) {
            userService.saveUser("Admin", Set.of("admin"), "password");
        }

        if (userRepository.findUserByUsername("Customer") == null) {
            userService.saveUser("Customer", Set.of("customer"), "password");
        }
    }
}
