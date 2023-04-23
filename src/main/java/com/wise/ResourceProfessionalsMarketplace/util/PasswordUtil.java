package com.wise.ResourceProfessionalsMarketplace.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    /**
     * Hashes a given input password using BCrypt
     *
     * @param plaintextPassword the plaintext password to hash
     * @return the BCrypt password hash
     */
    public String hashPassword(String plaintextPassword) {
        return encoder.encode(plaintextPassword);
    }

    /**
     * Authenticates a plaintext password against a hashed password
     *
     * @param plaintextPassword the plaintext password
     * @param hashedPassword the hashed password
     * @return true if the password matches the hash, false otherwise
     */
    public boolean authenticate(String plaintextPassword, String hashedPassword) {
        return encoder.matches(plaintextPassword, hashedPassword);
    }
}
