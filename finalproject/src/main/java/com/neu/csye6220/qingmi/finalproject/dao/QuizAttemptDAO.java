package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.QuizAttempt;
import java.util.List;

public interface QuizAttemptDAO {
    
    void save(QuizAttempt attempt);
    
    QuizAttempt findById(Long id);
    
    List<QuizAttempt> findByQuizId(Long quizId);
    
    List<QuizAttempt> findByUserId(Long userId);
    
    List<QuizAttempt> findByQuizIdAndUserId(Long quizId, Long userId);
    
    void update(QuizAttempt attempt);
    
    void delete(Long id);
}

