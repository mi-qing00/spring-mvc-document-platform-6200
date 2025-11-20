package com.neu.csye6220.qingmi.finalproject.controller;

import com.neu.csye6220.qingmi.finalproject.dto.LoginDTO;
import com.neu.csye6220.qingmi.finalproject.dto.UserRegistrationDTO;
import com.neu.csye6220.qingmi.finalproject.entity.User;
import com.neu.csye6220.qingmi.finalproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    // ========== REGISTRATION ==========
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserRegistrationDTO());
        return "user/register";
    }
    
    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("userDTO") @Valid UserRegistrationDTO userDTO,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        // Check for validation errors
        if (result.hasErrors()) {
            return "user/register";
        }
        
        // Check if passwords match
        if (!userDTO.passwordsMatch()) {
            model.addAttribute("error", "Passwords do not match");
            return "user/register";
        }
        
        try {
            // Register user
            userService.registerUser(
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getFullName()
            );
            
            // Success - redirect to login
            redirectAttributes.addFlashAttribute("success", 
                "Registration successful! Please login.");
            return "redirect:/users/login";
            
        } catch (IllegalArgumentException e) {
            // Business logic error (e.g., email already exists)
            model.addAttribute("error", e.getMessage());
            return "user/register";
        }
    }
    
    // ========== LOGIN ==========
    
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "user/login";
    }
    
    @PostMapping("/login")
    public String login(
            @ModelAttribute("loginDTO") @Valid LoginDTO loginDTO,
            BindingResult result,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        // Check for validation errors
        if (result.hasErrors()) {
            return "user/login";
        }
        
        // Authenticate user
        User user = userService.login(loginDTO.getEmail(), loginDTO.getPassword());
        
        if (user == null) {
            // Invalid credentials
            model.addAttribute("error", "Invalid email or password");
            return "user/login";
        }
        
        // Login successful - create session
        session.setAttribute("userId", user.getId());
        session.setAttribute("userEmail", user.getEmail());
        session.setAttribute("userName", user.getFullName());
        
        redirectAttributes.addFlashAttribute("success", 
            "Welcome back, " + user.getFullName() + "!");
        return "redirect:/dashboard";
    }
    
    // ========== LOGOUT ==========
    
    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "You have been logged out");
        return "redirect:/users/login";
    }
    
    // ========== PROFILE ==========
    
    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "user/profile";
    }
}