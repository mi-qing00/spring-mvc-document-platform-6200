package com.neu.csye6220.qingmi.finalproject.service;

import com.neu.csye6220.qingmi.finalproject.entity.StudySession;
import com.neu.csye6220.qingmi.finalproject.entity.SessionStatus;
import java.util.List;

public interface StudySessionService {
    
    StudySession createSession(Long pageId, Long userId, Integer durationMinutes);
    
    void startSession(Long sessionId, Long userId);
    
    void endSession(Long sessionId, Long userId);
    
    void cancelSession(Long sessionId, Long userId);
    
    List<StudySession> getActiveSessions(Long userId);
    
    List<StudySession> getSessionHistory(Long userId);
    
    StudySession getSession(Long sessionId, Long userId);
    
    void deleteSession(Long sessionId, Long userId);
    
    void updateStudyPlan(Long sessionId, Long userId, String studyPlan);
    
    void updateSummary(Long sessionId, Long userId, String summary);
}

