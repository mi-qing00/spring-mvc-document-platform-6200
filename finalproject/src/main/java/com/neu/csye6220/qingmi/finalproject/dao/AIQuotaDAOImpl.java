package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.AIQuota;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AIQuotaDAOImpl implements AIQuotaDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void save(AIQuota quota) {
        entityManager.persist(quota);
    }
    
    @Override
    public AIQuota findById(Long id) {
        return entityManager.find(AIQuota.class, id);
    }
    
    @Override
    public Optional<AIQuota> findByUserIdAndDate(Long userId, LocalDate date) {
        TypedQuery<AIQuota> query = entityManager.createQuery(
            "SELECT q FROM AIQuota q WHERE q.userId = :userId AND q.quotaDate = :date",
            AIQuota.class);
        query.setParameter("userId", userId);
        query.setParameter("date", date);
        List<AIQuota> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    @Override
    public void update(AIQuota quota) {
        entityManager.merge(quota);
    }
    
    @Override
    public void delete(Long id) {
        AIQuota quota = findById(id);
        if (quota != null) {
            entityManager.remove(quota);
        }
    }
}

