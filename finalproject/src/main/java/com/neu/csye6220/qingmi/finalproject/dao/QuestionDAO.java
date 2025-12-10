package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.Question;
import java.util.List;

public interface QuestionDAO {
    
    void save(Question question);
    
    Question findById(Long id);
    
    List<Question> findByQuizId(Long quizId);
    
    void update(Question question);
    
    void delete(Long id);
}

