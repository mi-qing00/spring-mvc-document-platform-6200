package com.neu.csye6220.qingmi.finalproject.controller;

import com.neu.csye6220.qingmi.finalproject.entity.Quiz;
import com.neu.csye6220.qingmi.finalproject.entity.Question;
import com.neu.csye6220.qingmi.finalproject.entity.QuizAttempt;
import com.neu.csye6220.qingmi.finalproject.entity.QuestionType;
import com.neu.csye6220.qingmi.finalproject.service.QuizService;
import com.neu.csye6220.qingmi.finalproject.service.PageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/quizzes")
public class QuizController {
    
    private final QuizService quizService;
    private final PageService pageService;
    
    @Autowired
    public QuizController(QuizService quizService, PageService pageService) {
        this.quizService = quizService;
        this.pageService = pageService;
    }
    
    @GetMapping
    public String listQuizzes(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        List<Quiz> quizzes = quizService.getQuizzesByUser(userId);
        model.addAttribute("quizzes", quizzes);
        
        return "quizzes/list";
    }
    
    @GetMapping("/page/{pageId}")
    public String quizzesByPage(
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
            List<Quiz> quizzes = quizService.getQuizzesByPage(pageId, userId);
            
            model.addAttribute("page", page);
            model.addAttribute("quizzes", quizzes);
            
            return "quizzes/list";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/dashboard";
        }
    }
    
    @GetMapping("/new")
    public String showCreateQuiz(
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
            
            return "quizzes/create";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/dashboard";
        }
    }
    
    @PostMapping("/create")
    public String createQuiz(
            @RequestParam Long pageId,
            @RequestParam String title,
            @RequestParam(required = false) Integer timeLimitMinutes,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            Quiz quiz = quizService.createQuiz(title, pageId, userId, timeLimitMinutes);
            redirectAttributes.addFlashAttribute("success", "Quiz created! Now add questions.");
            return "redirect:/quizzes/" + quiz.getId() + "/questions";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/quizzes/new?pageId=" + pageId;
        }
    }
    
    @GetMapping("/{quizId}")
    public String viewQuiz(
            @PathVariable Long quizId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            Quiz quiz = quizService.getQuiz(quizId, userId);
            model.addAttribute("quiz", quiz);
            
            return "quizzes/view";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/quizzes";
        }
    }
    
    @GetMapping("/{quizId}/questions")
    public String manageQuestions(
            @PathVariable Long quizId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            Quiz quiz = quizService.getQuiz(quizId, userId);
            model.addAttribute("quiz", quiz);
            model.addAttribute("questionTypes", QuestionType.values());
            
            return "quizzes/manage-questions";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/quizzes";
        }
    }
    
    @PostMapping("/{quizId}/questions/add")
    public String addQuestion(
            @PathVariable Long quizId,
            @RequestParam String questionText,
            @RequestParam String questionType,
            @RequestParam String correctAnswer,
            @RequestParam(required = false) String optionA,
            @RequestParam(required = false) String optionB,
            @RequestParam(required = false) String optionC,
            @RequestParam(required = false) String optionD,
            @RequestParam(required = false, defaultValue = "1") Integer points,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            Question question = new Question();
            question.setQuestionText(questionText);
            question.setQuestionType(QuestionType.valueOf(questionType));
            question.setCorrectAnswer(correctAnswer);
            question.setOptionA(optionA);
            question.setOptionB(optionB);
            question.setOptionC(optionC);
            question.setOptionD(optionD);
            question.setPoints(points);
            
            quizService.addQuestion(quizId, userId, question);
            redirectAttributes.addFlashAttribute("success", "Question added!");
            return "redirect:/quizzes/" + quizId + "/questions";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/quizzes/" + quizId + "/questions";
        }
    }
    
    @GetMapping("/{quizId}/take")
    public String takeQuiz(
            @PathVariable Long quizId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            Quiz quiz = quizService.getQuiz(quizId, userId);
            QuizAttempt attempt = quizService.startQuizAttempt(quizId, userId);
            
            model.addAttribute("quiz", quiz);
            model.addAttribute("attempt", attempt);
            
            return "quizzes/take";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/quizzes";
        }
    }
    
    @PostMapping("/{quizId}/submit")
    public String submitQuiz(
            @PathVariable Long quizId,
            @RequestParam Long attemptId,
            @RequestParam Map<String, String> allParams,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            // Extract answers from request parameters
            Map<Long, String> answers = new HashMap<>();
            for (Map.Entry<String, String> entry : allParams.entrySet()) {
                if (entry.getKey().startsWith("answer_")) {
                    Long questionId = Long.parseLong(entry.getKey().substring(7));
                    answers.put(questionId, entry.getValue());
                }
            }
            
            QuizAttempt attempt = quizService.submitQuizAttempt(attemptId, userId, answers);
            redirectAttributes.addFlashAttribute("success", "Quiz submitted! Score: " + attempt.getScore() + "%");
            return "redirect:/quizzes/" + quizId + "/results/" + attempt.getId();
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/quizzes/" + quizId + "/take";
        }
    }
    
    @GetMapping("/{quizId}/results/{attemptId}")
    public String viewResults(
            @PathVariable Long quizId,
            @PathVariable Long attemptId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            QuizAttempt attempt = quizService.getQuizAttempt(attemptId, userId);
            Quiz quiz = quizService.getQuiz(quizId, userId);
            
            model.addAttribute("quiz", quiz);
            model.addAttribute("attempt", attempt);
            
            return "quizzes/results";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/quizzes";
        }
    }
    
    @PostMapping("/{quizId}/delete")
    public String deleteQuiz(
            @PathVariable Long quizId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/users/login";
        }
        
        try {
            quizService.deleteQuiz(quizId, userId);
            redirectAttributes.addFlashAttribute("success", "Quiz deleted successfully!");
            return "redirect:/quizzes";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/quizzes";
        }
    }
}

