package com.neu.csye6220.qingmi.finalproject.controller;

import com.neu.csye6220.qingmi.finalproject.dto.PageDTO;
import com.neu.csye6220.qingmi.finalproject.entity.Page;
import com.neu.csye6220.qingmi.finalproject.service.PageService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/pages")
public class PageController {
    
    private final PageService pageService;
    
    @Autowired
    public PageController(PageService pageService) {
        this.pageService = pageService;
    }
    
    // ========== CREATE PAGE ==========
    
    @GetMapping("/new")
    public String showCreatePage(
            @RequestParam(required = false) Long parentId,
            HttpSession session,
            Model model) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        PageDTO pageDTO = new PageDTO();
        pageDTO.setParentId(parentId);
        
        model.addAttribute("pageDTO", pageDTO);
        
        // If creating nested page, load parent for breadcrumb
        if (parentId != null) {
            try {
                Page parent = pageService.getPage(parentId, userId);
                List<Page> breadcrumbs = pageService.getBreadcrumbs(parentId);
                model.addAttribute("parent", parent);
                model.addAttribute("breadcrumbs", breadcrumbs);
            } catch (IllegalArgumentException e) {
                return "redirect:/dashboard";
            }
        }
        
        return "page/create-page";
    }
    
    @PostMapping("/create")
    public String createPage(
            @ModelAttribute("pageDTO") @Valid PageDTO pageDTO,
            BindingResult result,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        if (result.hasErrors()) {
            return "page/create-page";
        }
        
        try {
            Page page = pageService.createPage(
                pageDTO.getTitle(),
                pageDTO.getContent(),
                userId,
                pageDTO.getParentId()
            );
            
            redirectAttributes.addFlashAttribute("success", "Page created successfully!");
            return "redirect:/pages/" + page.getId();
            
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "page/create-page";
        }
    }
    
    // ========== VIEW PAGE ==========
    
    @GetMapping("/{pageId}")
    public String viewPage(
            @PathVariable Long pageId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            Page page = pageService.getPage(pageId, userId);
            List<Page> breadcrumbs = pageService.getBreadcrumbs(pageId);
            List<Page> children = pageService.getChildPages(pageId, userId);
            
            model.addAttribute("page", page);
            model.addAttribute("breadcrumbs", breadcrumbs);
            model.addAttribute("children", children);
            
            return "page/view-page";
            
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/dashboard";
        }
    }
    
    // ========== EDIT PAGE ==========
    
    @GetMapping("/{pageId}/edit")
    public String showEditPage(
            @PathVariable Long pageId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            Page page = pageService.getPage(pageId, userId);
            
            PageDTO pageDTO = new PageDTO();
            pageDTO.setTitle(page.getTitle());
            pageDTO.setContent(page.getContent());
            
            model.addAttribute("pageDTO", pageDTO);
            model.addAttribute("page", page);
            
            return "page/edit-page";
            
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/dashboard";
        }
    }
    
    @PostMapping("/{pageId}/update")
    public String updatePage(
            @PathVariable Long pageId,
            @ModelAttribute("pageDTO") @Valid PageDTO pageDTO,
            BindingResult result,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        if (result.hasErrors()) {
            try {
                Page page = pageService.getPage(pageId, userId);
                model.addAttribute("page", page);
            } catch (IllegalArgumentException e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
                return "redirect:/dashboard";
            }
            return "page/edit-page";
        }
        
        try {
            pageService.updatePage(pageId, pageDTO.getTitle(), pageDTO.getContent(), userId);
            
            redirectAttributes.addFlashAttribute("success", "Page updated successfully!");
            return "redirect:/pages/" + pageId;
            
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "page/edit-page";
        }
    }
    
    // ========== DELETE PAGE ==========
    
    @PostMapping("/{pageId}/delete")
    public String deletePage(
            @PathVariable Long pageId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            pageService.deletePage(pageId, userId);
            
            redirectAttributes.addFlashAttribute("success", "Page deleted successfully!");
            return "redirect:/dashboard";
            
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/dashboard";
        }
    }
    
    // ========== SEARCH ==========
    
    @GetMapping("/search")
    public String searchPages(
            @RequestParam(required = false) String q,
            HttpSession session,
            Model model) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        List<Page> results = pageService.searchPages(q, userId);
        
        model.addAttribute("query", q);
        model.addAttribute("results", results);
        
        return "page/search-results";
    }
}