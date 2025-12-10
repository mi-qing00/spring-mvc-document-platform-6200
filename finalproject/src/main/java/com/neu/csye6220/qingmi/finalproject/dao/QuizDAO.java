package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.Quiz;
import java.util.List;

public interface QuizDAO {
    
    void save(Quiz quiz);
    
    Quiz findById(Long id);
    
    List<Quiz> findByPageId(Long pageId);
    
    List<Quiz> findByUserId(Long userId);
    
    void update(Quiz quiz);
    
    void delete(Long id);
}

