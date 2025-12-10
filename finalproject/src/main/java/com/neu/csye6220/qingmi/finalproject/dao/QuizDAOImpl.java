package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.Quiz;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class QuizDAOImpl implements QuizDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void save(Quiz quiz) {
        entityManager.persist(quiz);
    }
    
    @Override
    public Quiz findById(Long id) {
        return entityManager.find(Quiz.class, id);
    }
    
    @Override
    public List<Quiz> findByPageId(Long pageId) {
        TypedQuery<Quiz> query = entityManager.createQuery(
            "SELECT q FROM Quiz q WHERE q.pageId = :pageId ORDER BY q.createdAt DESC",
            Quiz.class);
        query.setParameter("pageId", pageId);
        return query.getResultList();
    }
    
    @Override
    public List<Quiz> findByUserId(Long userId) {
        TypedQuery<Quiz> query = entityManager.createQuery(
            "SELECT q FROM Quiz q WHERE q.createdBy = :userId ORDER BY q.createdAt DESC",
            Quiz.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    @Override
    public void update(Quiz quiz) {
        entityManager.merge(quiz);
    }
    
    @Override
    public void delete(Long id) {
        Quiz quiz = findById(id);
        if (quiz != null) {
            entityManager.remove(quiz);
        }
    }
}

