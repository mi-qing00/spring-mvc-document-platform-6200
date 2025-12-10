package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.QuizAttempt;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class QuizAttemptDAOImpl implements QuizAttemptDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void save(QuizAttempt attempt) {
        entityManager.persist(attempt);
    }
    
    @Override
    public QuizAttempt findById(Long id) {
        return entityManager.find(QuizAttempt.class, id);
    }
    
    @Override
    public List<QuizAttempt> findByQuizId(Long quizId) {
        TypedQuery<QuizAttempt> query = entityManager.createQuery(
            "SELECT a FROM QuizAttempt a WHERE a.quizId = :quizId ORDER BY a.completedAt DESC",
            QuizAttempt.class);
        query.setParameter("quizId", quizId);
        return query.getResultList();
    }
    
    @Override
    public List<QuizAttempt> findByUserId(Long userId) {
        TypedQuery<QuizAttempt> query = entityManager.createQuery(
            "SELECT a FROM QuizAttempt a WHERE a.userId = :userId ORDER BY a.completedAt DESC",
            QuizAttempt.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    @Override
    public List<QuizAttempt> findByQuizIdAndUserId(Long quizId, Long userId) {
        TypedQuery<QuizAttempt> query = entityManager.createQuery(
            "SELECT a FROM QuizAttempt a WHERE a.quizId = :quizId AND a.userId = :userId ORDER BY a.completedAt DESC",
            QuizAttempt.class);
        query.setParameter("quizId", quizId);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    @Override
    public void update(QuizAttempt attempt) {
        entityManager.merge(attempt);
    }
    
    @Override
    public void delete(Long id) {
        QuizAttempt attempt = findById(id);
        if (attempt != null) {
            entityManager.remove(attempt);
        }
    }
}

