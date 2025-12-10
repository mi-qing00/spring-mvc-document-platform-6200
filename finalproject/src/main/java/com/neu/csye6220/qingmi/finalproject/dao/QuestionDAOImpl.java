package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.Question;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class QuestionDAOImpl implements QuestionDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void save(Question question) {
        entityManager.persist(question);
    }
    
    @Override
    public Question findById(Long id) {
        return entityManager.find(Question.class, id);
    }
    
    @Override
    public List<Question> findByQuizId(Long quizId) {
        TypedQuery<Question> query = entityManager.createQuery(
            "SELECT q FROM Question q WHERE q.quizId = :quizId ORDER BY q.id ASC",
            Question.class);
        query.setParameter("quizId", quizId);
        return query.getResultList();
    }
    
    @Override
    public void update(Question question) {
        entityManager.merge(question);
    }
    
    @Override
    public void delete(Long id) {
        Question question = findById(id);
        if (question != null) {
            entityManager.remove(question);
        }
    }
}

