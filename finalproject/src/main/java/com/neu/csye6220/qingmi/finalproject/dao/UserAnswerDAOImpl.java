package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.UserAnswer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserAnswerDAOImpl implements UserAnswerDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void save(UserAnswer answer) {
        entityManager.persist(answer);
    }
    
    @Override
    public UserAnswer findById(Long id) {
        return entityManager.find(UserAnswer.class, id);
    }
    
    @Override
    public List<UserAnswer> findByAttemptId(Long attemptId) {
        TypedQuery<UserAnswer> query = entityManager.createQuery(
            "SELECT a FROM UserAnswer a WHERE a.attemptId = :attemptId ORDER BY a.questionId ASC",
            UserAnswer.class);
        query.setParameter("attemptId", attemptId);
        return query.getResultList();
    }
    
    @Override
    public void update(UserAnswer answer) {
        entityManager.merge(answer);
    }
    
    @Override
    public void delete(Long id) {
        UserAnswer answer = findById(id);
        if (answer != null) {
            entityManager.remove(answer);
        }
    }
}

