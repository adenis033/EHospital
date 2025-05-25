package com.ehospital.ehospital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Public resources
                        .requestMatchers("/", "/home", "/login", "/style.css", "/home_public").permitAll()

                        // Guard Doctor or Admin
                        .requestMatchers("/patients/register").hasAnyAuthority("GUARD_DOCTOR", "ADMIN")
                        .requestMatchers("/clinical-files/create").hasAnyAuthority("GUARD_DOCTOR", "ADMIN")
                        .requestMatchers("/clinical-files/reactivate").hasAnyAuthority("GUARD_DOCTOR", "ADMIN")

                        // Doctor or Admin
                        .requestMatchers("/treatment-plans/**").hasAnyAuthority("DOCTOR", "ADMIN")
                        .requestMatchers("/clinical-files/discharge").hasAnyAuthority("DOCTOR", "ADMIN")
                        .requestMatchers("/diagnostics/update").hasAnyAuthority("DOCTOR", "ADMIN") // future

                        // Nurse or Admin
                        .requestMatchers("/vitals/record").hasAnyAuthority("NURSE", "ADMIN")

                        // Doctor, Nurse, or Admin
                        .requestMatchers("/vitals/list").hasAnyAuthority("DOCTOR", "NURSE", "ADMIN")

                        // Doctor, Admin, Guard Doctor, or Nurse
                        .requestMatchers("/patients/all").hasAnyAuthority("DOCTOR", "ADMIN", "GUARD_DOCTOR", "NURSE")

                        // Admin-only endpoints (if any in the future)
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")

                        // All other requests require login
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }
}
