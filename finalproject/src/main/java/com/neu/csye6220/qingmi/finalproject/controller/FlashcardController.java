package com.neu.csye6220.qingmi.finalproject.controller;

import com.neu.csye6220.qingmi.finalproject.entity.Flashcard;
import com.neu.csye6220.qingmi.finalproject.entity.CardReview;
import com.neu.csye6220.qingmi.finalproject.entity.Difficulty;
import com.neu.csye6220.qingmi.finalproject.service.FlashcardService;
import com.neu.csye6220.qingmi.finalproject.service.PageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/flashcards")
public class FlashcardController {
    
    private final FlashcardService flashcardService;
    private final PageService pageService;
    
    @Autowired
    public FlashcardController(FlashcardService flashcardService, PageService pageService) {
        this.flashcardService = flashcardService;
        this.pageService = pageService;
    }
    
    @GetMapping
    public String dashboard(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        List<Flashcard> dueCards = flashcardService.getDueFlashcards(userId);
        List<Flashcard> allCards = flashcardService.getFlashcardsByUser(userId);
        
        model.addAttribute("dueCards", dueCards);
        model.addAttribute("allCards", allCards);
        
        return "flashcards/dashboard";
    }
    
    @GetMapping("/page/{pageId}")
    public String flashcardsByPage(
            @PathVariable Long pageId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            var page = pageService.getPage(pageId, userId);
            List<Flashcard> flashcards = flashcardService.getFlashcardsByPage(pageId, userId);
            
            model.addAttribute("page", page);
            model.addAttribute("flashcards", flashcards);
            
            return "flashcards/list";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/dashboard";
        }
    }
    
    @GetMapping("/new")
    public String showCreateFlashcard(
            @RequestParam Long pageId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            var page = pageService.getPage(pageId, userId);
            model.addAttribute("page", page);
            model.addAttribute("pageId", pageId);
            
            return "flashcards/create";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/dashboard";
        }
    }
    
    @PostMapping("/create")
    public String createFlashcard(
            @RequestParam Long pageId,
            @RequestParam String front,
            @RequestParam String back,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            flashcardService.createFlashcard(pageId, front, back, userId);
            redirectAttributes.addFlashAttribute("success", "Flashcard created successfully!");
            return "redirect:/flashcards/page/" + pageId;
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/flashcards/new?pageId=" + pageId;
        }
    }
    
    @GetMapping("/{cardId}/edit")
    public String showEditFlashcard(
            @PathVariable Long cardId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            Flashcard flashcard = flashcardService.getFlashcard(cardId, userId);
            model.addAttribute("flashcard", flashcard);
            
            return "flashcards/edit";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/flashcards";
        }
    }
    
    @PostMapping("/{cardId}/update")
    public String updateFlashcard(
            @PathVariable Long cardId,
            @RequestParam String front,
            @RequestParam String back,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            Flashcard flashcard = flashcardService.getFlashcard(cardId, userId);
            flashcardService.updateFlashcard(cardId, userId, front, back);
            
            redirectAttributes.addFlashAttribute("success", "Flashcard updated successfully!");
            return "redirect:/flashcards/page/" + flashcard.getPageId();
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/flashcards";
        }
    }
    
    @PostMapping("/{cardId}/delete")
    public String deleteFlashcard(
            @PathVariable Long cardId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            Flashcard flashcard = flashcardService.getFlashcard(cardId, userId);
            Long pageId = flashcard.getPageId();
            
            flashcardService.deleteFlashcard(cardId, userId);
            redirectAttributes.addFlashAttribute("success", "Flashcard deleted successfully!");
            return "redirect:/flashcards/page/" + pageId;
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/flashcards";
        }
    }
    
    @GetMapping("/study")
    public String studyMode(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        List<Flashcard> dueCards = flashcardService.getDueFlashcards(userId);
        
        if (dueCards.isEmpty()) {
            model.addAttribute("message", "No flashcards due for review!");
            return "flashcards/study";
        }
        
        model.addAttribute("cards", dueCards);
        model.addAttribute("currentIndex", 0);
        
        return "flashcards/study";
    }
    
    @PostMapping("/{cardId}/review")
    public String reviewFlashcard(
            @PathVariable Long cardId,
            @RequestParam String difficulty,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            Difficulty difficultyEnum = Difficulty.valueOf(difficulty.toUpperCase());
            flashcardService.reviewFlashcard(cardId, userId, difficultyEnum);
            
            redirectAttributes.addFlashAttribute("success", "Review saved!");
            return "redirect:/flashcards/study";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/flashcards/study";
        }
    }
}

