package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.ProficiencyScore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ProficiencyScoreDAOImpl implements ProficiencyScoreDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void save(ProficiencyScore score) {
        entityManager.persist(score);
    }
    
    @Override
    public ProficiencyScore findById(Long id) {
        return entityManager.find(ProficiencyScore.class, id);
    }
    
    @Override
    public List<ProficiencyScore> findByUserId(Long userId) {
        TypedQuery<ProficiencyScore> query = entityManager.createQuery(
            "SELECT p FROM ProficiencyScore p WHERE p.userId = :userId ORDER BY p.score ASC",
            ProficiencyScore.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    @Override
    public Optional<ProficiencyScore> findByUserIdAndTopic(Long userId, String topic) {
        TypedQuery<ProficiencyScore> query = entityManager.createQuery(
            "SELECT p FROM ProficiencyScore p WHERE p.userId = :userId AND p.topic = :topic",
            ProficiencyScore.class);
        query.setParameter("userId", userId);
        query.setParameter("topic", topic);
        List<ProficiencyScore> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    @Override
    public void update(ProficiencyScore score) {
        entityManager.merge(score);
    }
    
    @Override
    public void delete(Long id) {
        ProficiencyScore score = findById(id);
        if (score != null) {
            entityManager.remove(score);
        }
    }
}

