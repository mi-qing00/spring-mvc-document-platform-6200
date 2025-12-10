package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.UserAnswer;
import java.util.List;

public interface UserAnswerDAO {
    
    void save(UserAnswer answer);
    
    UserAnswer findById(Long id);
    
    List<UserAnswer> findByAttemptId(Long attemptId);
    
    void update(UserAnswer answer);
    
    void delete(Long id);
}

