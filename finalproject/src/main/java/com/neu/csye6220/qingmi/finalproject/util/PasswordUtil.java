package com.neu.csye6220.qingmi.finalproject.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    /**
     * Hash a plain text password using BCrypt
     * @param plainPassword The plain text password
     * @return Hashed password
     */
    public static String hashPassword(String plainPassword) {
        return encoder.encode(plainPassword);
    }
    
    /**
     * Check if plain password matches the hashed password
     * @param plainPassword The plain text password
     * @param hashedPassword The BCrypt hashed password
     * @return true if passwords match, false otherwise
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return encoder.matches(plainPassword, hashedPassword);
    }
}