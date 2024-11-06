package com.raij.SpotifyMoodAnalyzer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Example user retrieval logic
        if ("user".equals(username)) {
            return new org.springframework.security.core.userdetails.User(
                    "user",
                    passwordEncoder.encode("password"), // Store encoded password
                    new ArrayList<>()                    // Granted authorities (roles)
            );
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}


