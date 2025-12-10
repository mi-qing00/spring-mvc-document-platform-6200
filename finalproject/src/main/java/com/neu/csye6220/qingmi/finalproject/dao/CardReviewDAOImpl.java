package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.CardReview;
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
public class CardReviewDAOImpl implements CardReviewDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void save(CardReview review) {
        entityManager.persist(review);
    }
    
    @Override
    public CardReview findById(Long id) {
        return entityManager.find(CardReview.class, id);
    }
    
    @Override
    public List<CardReview> findByCardId(Long cardId) {
        TypedQuery<CardReview> query = entityManager.createQuery(
            "SELECT r FROM CardReview r WHERE r.cardId = :cardId ORDER BY r.reviewedAt DESC",
            CardReview.class);
        query.setParameter("cardId", cardId);
        return query.getResultList();
    }
    
    @Override
    public List<CardReview> findByUserId(Long userId) {
        TypedQuery<CardReview> query = entityManager.createQuery(
            "SELECT r FROM CardReview r WHERE r.userId = :userId ORDER BY r.reviewedAt DESC",
            CardReview.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    @Override
    public List<CardReview> findDueCards(Long userId, LocalDate date) {
        TypedQuery<CardReview> query = entityManager.createQuery(
            "SELECT r FROM CardReview r WHERE r.userId = :userId AND r.nextReviewDate <= :date ORDER BY r.nextReviewDate ASC",
            CardReview.class);
        query.setParameter("userId", userId);
        query.setParameter("date", date);
        return query.getResultList();
    }
    
    @Override
    public Optional<CardReview> findLatestReview(Long cardId, Long userId) {
        TypedQuery<CardReview> query = entityManager.createQuery(
            "SELECT r FROM CardReview r WHERE r.cardId = :cardId AND r.userId = :userId ORDER BY r.reviewedAt DESC",
            CardReview.class);
        query.setParameter("cardId", cardId);
        query.setParameter("userId", userId);
        query.setMaxResults(1);
        List<CardReview> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    @Override
    public void update(CardReview review) {
        entityManager.merge(review);
    }
    
    @Override
    public void delete(Long id) {
        CardReview review = findById(id);
        if (review != null) {
            entityManager.remove(review);
        }
    }
}

