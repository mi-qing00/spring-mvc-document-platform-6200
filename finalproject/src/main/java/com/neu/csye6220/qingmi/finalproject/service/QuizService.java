package com.neu.csye6220.qingmi.finalproject.service;

import com.neu.csye6220.qingmi.finalproject.entity.Quiz;
import com.neu.csye6220.qingmi.finalproject.entity.Question;
import com.neu.csye6220.qingmi.finalproject.entity.QuizAttempt;
import com.neu.csye6220.qingmi.finalproject.entity.UserAnswer;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface QuizService {
    
    Quiz createQuiz(String title, Long pageId, Long userId, Integer timeLimitMinutes);
    
    Quiz getQuiz(Long quizId, Long userId);
    
    List<Quiz> getQuizzesByPage(Long pageId, Long userId);
    
    List<Quiz> getQuizzesByUser(Long userId);
    
    void addQuestion(Long quizId, Long userId, Question question);
    
    void updateQuestion(Long questionId, Long userId, Question question);
    
    void deleteQuestion(Long questionId, Long userId);
    
    void deleteQuiz(Long quizId, Long userId);
    
    QuizAttempt startQuizAttempt(Long quizId, Long userId);
    
    QuizAttempt submitQuizAttempt(Long attemptId, Long userId, Map<Long, String> answers);
    
    BigDecimal scoreQuizAttempt(QuizAttempt attempt);
    
    List<QuizAttempt> getQuizAttempts(Long quizId, Long userId);
    
    QuizAttempt getQuizAttempt(Long attemptId, Long userId);
}

