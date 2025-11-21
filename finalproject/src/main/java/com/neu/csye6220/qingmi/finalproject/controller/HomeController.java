package com.neu.csye6220.qingmi.finalproject.controller;

import com.neu.csye6220.qingmi.finalproject.entity.Page;
import com.neu.csye6220.qingmi.finalproject.service.PageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    
    private final PageService pageService;
    
    @Autowired
    public HomeController(PageService pageService) {
        this.pageService = pageService;
    }
    
    @GetMapping("/")
    public String home(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            return "redirect:/dashboard";
        }
        return "home/index";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        String userName = (String) session.getAttribute("userName");
        model.addAttribute("userName", userName);
        
        // Load root pages
        List<Page> rootPages = pageService.getRootPages(userId);
        model.addAttribute("rootPages", rootPages);
        
        return "page/dashboard";
    }
}