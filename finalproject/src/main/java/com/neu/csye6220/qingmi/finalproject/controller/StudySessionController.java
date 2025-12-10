package com.neu.csye6220.qingmi.finalproject.controller;

import com.neu.csye6220.qingmi.finalproject.entity.StudySession;
import com.neu.csye6220.qingmi.finalproject.service.StudySessionService;
import com.neu.csye6220.qingmi.finalproject.service.PageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/study/sessions")
public class StudySessionController {
    
    private final StudySessionService sessionService;
    private final PageService pageService;
    
    @Autowired
    public StudySessionController(StudySessionService sessionService, PageService pageService) {
        this.sessionService = sessionService;
        this.pageService = pageService;
    }
    
    @GetMapping
    public String listSessions(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        List<StudySession> sessions = sessionService.getSessionHistory(userId);
        model.addAttribute("sessions", sessions);
        
        return "study/sessions";
    }
    
    @GetMapping("/new")
    public String showCreateSession(
            @RequestParam(required = false) Long pageId,
            HttpSession session,
            Model model) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        if (pageId != null) {
            try {
                var page = pageService.getPage(pageId, userId);
                model.addAttribute("page", page);
                model.addAttribute("pageId", pageId);
            } catch (IllegalArgumentException e) {
                return "redirect:/dashboard";
            }
        }
        
        return "study/create-session";
    }
    
    @PostMapping("/create")
    public String createSession(
            @RequestParam Long pageId,
            @RequestParam(required = false) Integer durationMinutes,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            StudySession newSession = sessionService.createSession(pageId, userId, durationMinutes);
            redirectAttributes.addFlashAttribute("success", "Study session created successfully!");
            return "redirect:/study/sessions/" + newSession.getId();
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/study/sessions/new?pageId=" + pageId;
        }
    }
    
    @GetMapping("/{sessionId}")
    public String viewSession(
            @PathVariable Long sessionId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            StudySession studySession = sessionService.getSession(sessionId, userId);
            model.addAttribute("session", studySession);
            return "study/session-detail";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/study/sessions";
        }
    }
    
    @PostMapping("/{sessionId}/start")
    public String startSession(
            @PathVariable Long sessionId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            sessionService.startSession(sessionId, userId);
            redirectAttributes.addFlashAttribute("success", "Session started!");
            return "redirect:/study/sessions/" + sessionId;
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/study/sessions";
        }
    }
    
    @PostMapping("/{sessionId}/end")
    public String endSession(
            @PathVariable Long sessionId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            sessionService.endSession(sessionId, userId);
            redirectAttributes.addFlashAttribute("success", "Session completed!");
            return "redirect:/study/sessions/" + sessionId;
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/study/sessions";
        }
    }
    
    @PostMapping("/{sessionId}/delete")
    public String deleteSession(
            @PathVariable Long sessionId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            sessionService.deleteSession(sessionId, userId);
            redirectAttributes.addFlashAttribute("success", "Session deleted successfully!");
            return "redirect:/study/sessions";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/study/sessions";
        }
    }
}

