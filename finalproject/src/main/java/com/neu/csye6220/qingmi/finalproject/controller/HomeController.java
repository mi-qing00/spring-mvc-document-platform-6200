package com.neu.csye6220.qingmi.finalproject.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(HttpSession session) {
        // If user is logged in, redirect to dashboard
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
        
        return "page/dashboard";
    }
}