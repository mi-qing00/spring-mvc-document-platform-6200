package com.neu.csye6220.qingmi.finalproject.service;

import com.neu.csye6220.qingmi.finalproject.dao.PageDAO;
import com.neu.csye6220.qingmi.finalproject.entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class PageServiceImpl implements PageService {
    
    private final PageDAO pageDAO;
    
    @Autowired
    public PageServiceImpl(PageDAO pageDAO) {
        this.pageDAO = pageDAO;
    }
    
    @Override
    public Page createPage(String title, String content, Long userId, Long parentId) {
        // Validate inputs
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content is required");
        }
        
        // If parent specified, validate access
        if (parentId != null) {
            Page parent = pageDAO.findById(parentId);
            if (parent == null) {
                throw new IllegalArgumentException("Parent page not found");
            }
            if (!parent.getUserId().equals(userId)) {
                throw new IllegalArgumentException("You don't have access to the parent page");
            }
        }
        
        // Create page
        Page page = new Page();
        page.setTitle(title.trim());
        page.setContent(content.trim());
        page.setUserId(userId);
        page.setParentId(parentId);
        
        pageDAO.save(page);
        return page;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page getPage(Long pageId, Long userId) {
        Page page = pageDAO.findById(pageId);
        
        if (page == null) {
            throw new IllegalArgumentException("Page not found");
        }
        
        // Check ownership
        if (!page.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Access denied");
        }
        
        return page;
    }
    
    @Override
    public void updatePage(Long pageId, String title, String content, Long userId) {
        Page page = getPage(pageId, userId);  // This checks ownership
        
        // Validate inputs
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content is required");
        }
        
        // Update page
        page.setTitle(title.trim());
        page.setContent(content.trim());
        
        pageDAO.update(page);
    }
    
    @Override
    public void deletePage(Long pageId, Long userId) {
        Page page = getPage(pageId, userId);  // This checks ownership
        
        // Delete (cascade will handle children)
        pageDAO.delete(pageId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Page> getRootPages(Long userId) {
        return pageDAO.findRootPagesByUserId(userId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Page> getChildPages(Long parentId, Long userId) {
        // Verify user owns the parent
        Page parent = getPage(parentId, userId);
        
        return pageDAO.findChildPages(parentId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Page> getBreadcrumbs(Long pageId) {
        List<Page> breadcrumbs = new ArrayList<>();
        Page current = pageDAO.findById(pageId);
        
        // Build breadcrumb trail by traversing up the parent chain
        while (current != null) {
            breadcrumbs.add(current);
            if (current.getParentId() != null) {
                current = pageDAO.findById(current.getParentId());
            } else {
                current = null;  // Reached root
            }
        }
        
        // Reverse to get root → current order
        Collections.reverse(breadcrumbs);
        return breadcrumbs;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Page> searchPages(String query, Long userId) {
        if (query == null || query.trim().isEmpty()) {
            return getUserPages(userId);
        }
        return pageDAO.searchByTitle(query, userId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Page> getUserPages(Long userId) {
        return pageDAO.findByUserId(userId);
    }
}