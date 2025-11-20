package com.neu.csye6220.qingmi.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to Document Collaboration Platform!");
        return "home/index";
    }
    
    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("status", "Spring Boot + JSP is working!");
        return "home/test";
    }
}