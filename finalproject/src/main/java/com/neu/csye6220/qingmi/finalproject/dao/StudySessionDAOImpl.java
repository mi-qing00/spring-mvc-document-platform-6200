package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.StudySession;
import com.neu.csye6220.qingmi.finalproject.entity.SessionStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class StudySessionDAOImpl implements StudySessionDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void save(StudySession session) {
        entityManager.persist(session);
    }
    
    @Override
    public StudySession findById(Long id) {
        return entityManager.find(StudySession.class, id);
    }
    
    @Override
    public List<StudySession> findByUserId(Long userId) {
        TypedQuery<StudySession> query = entityManager.createQuery(
            "SELECT s FROM StudySession s WHERE s.userId = :userId ORDER BY s.startedAt DESC",
            StudySession.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    @Override
    public List<StudySession> findByUserIdAndStatus(Long userId, SessionStatus status) {
        TypedQuery<StudySession> query = entityManager.createQuery(
            "SELECT s FROM StudySession s WHERE s.userId = :userId AND s.status = :status ORDER BY s.startedAt DESC",
            StudySession.class);
        query.setParameter("userId", userId);
        query.setParameter("status", status);
        return query.getResultList();
    }
    
    @Override
    public List<StudySession> findByPageId(Long pageId) {
        TypedQuery<StudySession> query = entityManager.createQuery(
            "SELECT s FROM StudySession s WHERE s.pageId = :pageId ORDER BY s.startedAt DESC",
            StudySession.class);
        query.setParameter("pageId", pageId);
        return query.getResultList();
    }
    
    @Override
    public void update(StudySession session) {
        entityManager.merge(session);
    }
    
    @Override
    public void delete(Long id) {
        StudySession session = findById(id);
        if (session != null) {
            entityManager.remove(session);
        }
    }
}

