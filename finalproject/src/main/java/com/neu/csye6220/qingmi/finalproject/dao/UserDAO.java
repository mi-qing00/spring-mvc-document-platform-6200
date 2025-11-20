package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.User;
import java.util.List;

public interface UserDAO {
    
    /**
     * Save a new user to the database
     */
    void save(User user);
    
    /**
     * Find user by ID
     * @return User or null if not found
     */
    User findById(Long id);
    
    /**
     * Find user by email
     * @return User or null if not found
     */
    User findByEmail(String email);
    
    /**
     * Get all users
     */
    List<User> findAll();
    
    /**
     * Update existing user
     */
    void update(User user);
    
    /**
     * Delete user by ID
     */
    void delete(Long id);
    
    /**
     * Search users by name (partial match)
     */
    List<User> searchByName(String name);
}