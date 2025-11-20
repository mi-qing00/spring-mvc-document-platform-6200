package com.neu.csye6220.qingmi.finalproject.service;

import com.neu.csye6220.qingmi.finalproject.entity.User;
import java.util.List;

public interface UserService {
    
    /**
     * Register a new user
     * Validates email uniqueness and password strength
     * Hashes password before saving
     * 
     * @param email User's email
     * @param plainPassword Plain text password
     * @param fullName User's full name
     * @return Saved user
     * @throws IllegalArgumentException if email exists or password is weak
     */
    User registerUser(String email, String plainPassword, String fullName);
    
    /**
     * Authenticate user with email and password
     * 
     * @param email User's email
     * @param plainPassword Plain text password
     * @return User if credentials valid, null otherwise
     */
    User login(String email, String plainPassword);
    
    /**
     * Get user by ID
     * 
     * @param userId User ID
     * @return User or null if not found
     */
    User getUserById(Long userId);
    
    /**
     * Update user profile
     * 
     * @param userId User ID
     * @param fullName New full name
     * @param email New email (must be unique)
     * @throws IllegalArgumentException if email already taken by another user
     */
    void updateProfile(Long userId, String fullName, String email);
    
    /**
     * Change user password
     * 
     * @param userId User ID
     * @param currentPassword Current password (for verification)
     * @param newPassword New password
     * @throws IllegalArgumentException if current password is incorrect
     */
    void changePassword(Long userId, String currentPassword, String newPassword);
    
    /**
     * Delete user account
     * 
     * @param userId User ID
     */
    void deleteUser(Long userId);
    
    /**
     * Search users by name
     * 
     * @param name Name to search for (partial match)
     * @return List of matching users
     */
    List<User> searchUsers(String name);
    
    /**
     * Get all users (for admin purposes)
     * 
     * @return List of all users
     */
    List<User> getAllUsers();
}