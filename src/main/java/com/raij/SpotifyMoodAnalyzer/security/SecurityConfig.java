package com.raij.SpotifyMoodAnalyzer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // No direct dependency on CustomUserDetailsService here

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/callback", "/home").permitAll()  // Allow access to the login page
                        .anyRequest().authenticated()            // All other requests require authentication
                )
                .formLogin(form -> form
                        .loginPage("/login")                    // Specify the custom login page
                        .permitAll()                            // Allow everyone to see the login page
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")     // Redirect to the login page on logout
                        .permitAll()                            // Allow everyone to logout
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, CustomUserDetailsService userDetailsService) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService) // Register your UserDetailsService
                .passwordEncoder(passwordEncoder());    // Register your PasswordEncoder
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }

}


