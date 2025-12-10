package com.neu.csye6220.qingmi.finalproject.service;

import com.neu.csye6220.qingmi.finalproject.dao.StudySessionDAO;
import com.neu.csye6220.qingmi.finalproject.dao.PageDAO;
import com.neu.csye6220.qingmi.finalproject.entity.StudySession;
import com.neu.csye6220.qingmi.finalproject.entity.SessionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudySessionServiceImpl implements StudySessionService {
    
    private final StudySessionDAO sessionDAO;
    private final PageDAO pageDAO;
    
    @Autowired
    public StudySessionServiceImpl(StudySessionDAO sessionDAO, PageDAO pageDAO) {
        this.sessionDAO = sessionDAO;
        this.pageDAO = pageDAO;
    }
    
    @Override
    public StudySession createSession(Long pageId, Long userId, Integer durationMinutes) {
        // Validate page exists and user owns it
        var page = pageDAO.findById(pageId);
        if (page == null) {
            throw new IllegalArgumentException("Page not found");
        }
        if (!page.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Access denied");
        }
        
        StudySession session = new StudySession(pageId, userId, durationMinutes);
        sessionDAO.save(session);
        return session;
    }
    
    @Override
    public void startSession(Long sessionId, Long userId) {
        StudySession session = getSession(sessionId, userId);
        session.start();
        sessionDAO.update(session);
    }
    
    @Override
    public void endSession(Long sessionId, Long userId) {
        StudySession session = getSession(sessionId, userId);
        session.end();
        sessionDAO.update(session);
    }
    
    @Override
    public void cancelSession(Long sessionId, Long userId) {
        StudySession session = getSession(sessionId, userId);
        session.cancel();
        sessionDAO.update(session);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<StudySession> getActiveSessions(Long userId) {
        return sessionDAO.findByUserIdAndStatus(userId, SessionStatus.IN_PROGRESS);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<StudySession> getSessionHistory(Long userId) {
        return sessionDAO.findByUserId(userId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public StudySession getSession(Long sessionId, Long userId) {
        StudySession session = sessionDAO.findById(sessionId);
        if (session == null) {
            throw new IllegalArgumentException("Session not found");
        }
        if (!session.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Access denied");
        }
        return session;
    }
    
    @Override
    public void deleteSession(Long sessionId, Long userId) {
        StudySession session = getSession(sessionId, userId);
        sessionDAO.delete(sessionId);
    }
    
    @Override
    public void updateStudyPlan(Long sessionId, Long userId, String studyPlan) {
        StudySession session = getSession(sessionId, userId);
        session.setStudyPlan(studyPlan);
        sessionDAO.update(session);
    }
    
    @Override
    public void updateSummary(Long sessionId, Long userId, String summary) {
        StudySession session = getSession(sessionId, userId);
        session.setSummary(summary);
        sessionDAO.update(session);
    }
}

