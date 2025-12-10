package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.StudySession;
import com.neu.csye6220.qingmi.finalproject.entity.SessionStatus;
import java.util.List;

public interface StudySessionDAO {
    
    void save(StudySession session);
    
    StudySession findById(Long id);
    
    List<StudySession> findByUserId(Long userId);
    
    List<StudySession> findByUserIdAndStatus(Long userId, SessionStatus status);
    
    List<StudySession> findByPageId(Long pageId);
    
    void update(StudySession session);
    
    void delete(Long id);
}

