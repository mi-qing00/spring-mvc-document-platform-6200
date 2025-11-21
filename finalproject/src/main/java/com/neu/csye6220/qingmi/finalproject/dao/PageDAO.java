package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.Page;
import java.util.List;

public interface PageDAO {
    
    /**
     * Save a new page
     */
    void save(Page page);
    
    /**
     * Find page by ID
     */
    Page findById(Long id);
    
    /**
     * Find all pages owned by a user
     */
    List<Page> findByUserId(Long userId);
    
    /**
     * Find root pages (no parent) for a user
     */
    List<Page> findRootPagesByUserId(Long userId);
    
    /**
     * Find child pages of a parent
     */
    List<Page> findChildPages(Long parentId);
    
    /**
     * Update existing page
     */
    void update(Page page);
    
    /**
     * Delete page by ID
     * Note: Cascade delete will remove all children
     */
    void delete(Long id);
    
    /**
     * Search pages by title for a user
     */
    List<Page> searchByTitle(String title, Long userId);
}