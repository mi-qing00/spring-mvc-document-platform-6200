package com.neu.csye6220.qingmi.finalproject.service;

import com.neu.csye6220.qingmi.finalproject.dao.UserDAO;
import com.neu.csye6220.qingmi.finalproject.entity.User;
import com.neu.csye6220.qingmi.finalproject.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    private final UserDAO userDAO;
    
    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    @Override
    public User registerUser(String email, String plainPassword, String fullName) {
        // Validate email is unique
        User existingUser = userDAO.findByEmail(email);
        if (existingUser != null) {
            throw new IllegalArgumentException("Email already registered");
        }
        
        // Validate password strength
        if (plainPassword == null || plainPassword.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        
        // Validate full name
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name is required");
        }
        
        // Hash password
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);
        
        // Create user
        User user = new User();
        user.setEmail(email.trim().toLowerCase());
        user.setPasswordHash(hashedPassword);
        user.setFullName(fullName.trim());
        
        // Save to database
        userDAO.save(user);
        
        return user;
    }
    
    @Override
    public User login(String email, String plainPassword) {
        // Find user by email
        User user = userDAO.findByEmail(email);
        
        if (user == null) {
            return null;  // User not found
        }
        
        // Check password
        boolean passwordMatches = PasswordUtil.checkPassword(plainPassword, user.getPasswordHash());
        
        if (!passwordMatches) {
            return null;  // Wrong password
        }
        
        return user;  // Login successful
    }
    
    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userDAO.findById(userId);
    }
    
    @Override
    public void updateProfile(Long userId, String fullName, String email) {
        // Get existing user
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        
        // Check if new email is already taken by another user
        if (email != null && !email.equals(user.getEmail())) {
            User existingUser = userDAO.findByEmail(email);
            if (existingUser != null && !existingUser.getId().equals(userId)) {
                throw new IllegalArgumentException("Email already taken");
            }
            user.setEmail(email.trim().toLowerCase());
        }
        
        // Update full name
        if (fullName != null && !fullName.trim().isEmpty()) {
            user.setFullName(fullName.trim());
        }
        
        // Save changes
        userDAO.update(user);
    }
    
    @Override
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        // Get user
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        
        // Verify current password
        boolean currentPasswordMatches = PasswordUtil.checkPassword(currentPassword, user.getPasswordHash());
        if (!currentPasswordMatches) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        
        // Validate new password
        if (newPassword == null || newPassword.length() < 8) {
            throw new IllegalArgumentException("New password must be at least 8 characters");
        }
        
        // Hash and save new password
        String hashedPassword = PasswordUtil.hashPassword(newPassword);
        user.setPasswordHash(hashedPassword);
        
        userDAO.update(user);
    }
    
    @Override
    public void deleteUser(Long userId) {
        userDAO.delete(userId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<User> searchUsers(String name) {
        return userDAO.searchByName(name);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }
}