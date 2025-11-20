package com.neu.csye6220.qingmi.finalproject.service;

import com.neu.csye6220.qingmi.finalproject.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional  // Rollback after each test
class UserServiceTest {
    
    @Autowired
    private UserService userService;
    
    @Test
    void testRegisterUser() {
        // Register a new user
        User user = userService.registerUser(
            "test@example.com",
            "password123",
            "Test User"
        );
        
        // Verify user was created
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Test User", user.getFullName());
        
        System.out.println("✓ User registered successfully!");
        System.out.println("  ID: " + user.getId());
        System.out.println("  Email: " + user.getEmail());
        System.out.println("  Name: " + user.getFullName());
    }
    
    @Test
    void testLogin() {
        // Register user
        userService.registerUser("login@example.com", "password123", "Login User");
        
        // Test successful login
        User user = userService.login("login@example.com", "password123");
        assertNotNull(user);
        assertEquals("login@example.com", user.getEmail());
        
        // Test failed login (wrong password)
        User failed = userService.login("login@example.com", "wrongpassword");
        assertNull(failed);
        
        System.out.println("✓ Login works correctly!");
    }
    
    @Test
    void testDuplicateEmail() {
        // Register first user
        userService.registerUser("duplicate@example.com", "password123", "User One");
        
        // Try to register with same email
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser("duplicate@example.com", "password456", "User Two");
        });
        
        assertEquals("Email already registered", exception.getMessage());
        System.out.println("✓ Duplicate email validation works!");
    }
}