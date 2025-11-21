package com.neu.csye6220.qingmi.finalproject.service;

import com.neu.csye6220.qingmi.finalproject.entity.Page;
import java.util.List;

public interface PageService {
    
    /**
     * Create a new page
     * Validates parent access if parentId is provided
     * 
     * @param title Page title
     * @param content Page content
     * @param userId Owner ID
     * @param parentId Parent page ID (null for root page)
     * @return Created page
     * @throws IllegalArgumentException if parent doesn't exist or user doesn't own it
     */
    Page createPage(String title, String content, Long userId, Long parentId);
    
    /**
     * Get page by ID
     * Checks if user has access (ownership)
     * 
     * @param pageId Page ID
     * @param userId User requesting access
     * @return Page
     * @throws IllegalArgumentException if page not found or access denied
     */
    Page getPage(Long pageId, Long userId);
    
    /**
     * Update page content
     * Checks ownership before updating
     * 
     * @param pageId Page ID
     * @param title New title
     * @param content New content
     * @param userId User making the update
     * @throws IllegalArgumentException if not owner
     */
    void updatePage(Long pageId, String title, String content, Long userId);
    
    /**
     * Delete page and all its children
     * Checks ownership before deleting
     * 
     * @param pageId Page ID
     * @param userId User requesting deletion
     * @throws IllegalArgumentException if not owner
     */
    void deletePage(Long pageId, Long userId);
    
    /**
     * Get all root pages for a user
     */
    List<Page> getRootPages(Long userId);
    
    /**
     * Get child pages of a parent
     */
    List<Page> getChildPages(Long parentId, Long userId);
    
    /**
     * Get breadcrumb trail for navigation
     * Returns list from root to current page
     * Example: [CS Course, Week 1, Lecture Notes]
     */
    List<Page> getBreadcrumbs(Long pageId);
    
    /**
     * Search pages by title
     */
    List<Page> searchPages(String query, Long userId);
    
    /**
     * Get all pages for a user (for overview)
     */
    List<Page> getUserPages(Long userId);
}