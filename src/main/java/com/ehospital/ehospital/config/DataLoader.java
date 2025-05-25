package com.ehospital.ehospital.config;

import com.ehospital.ehospital.model.Role;
import com.ehospital.ehospital.model.User;
import com.ehospital.ehospital.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findAll().isEmpty()) {
            User admin = new User("admin", passwordEncoder.encode("pass"));
            admin.setRoles(Set.of(Role.ADMIN));

            User doctor = new User("doctor", passwordEncoder.encode("pass"));
            doctor.setRoles(Set.of(Role.DOCTOR));

            User nurse = new User("nurse", passwordEncoder.encode("pass"));
            nurse.setRoles(Set.of(Role.NURSE));

            User guard = new User("guard", passwordEncoder.encode("pass"));
            guard.setRoles(Set.of(Role.GUARD_DOCTOR));

            userRepository.save(admin);
            userRepository.save(doctor);
            userRepository.save(nurse);
            userRepository.save(guard);

            System.out.println("Users seeded.");
        }
    }
}
