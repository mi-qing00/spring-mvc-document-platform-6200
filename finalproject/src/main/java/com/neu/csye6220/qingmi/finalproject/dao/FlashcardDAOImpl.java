package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.Flashcard;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class FlashcardDAOImpl implements FlashcardDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void save(Flashcard flashcard) {
        entityManager.persist(flashcard);
    }
    
    @Override
    public Flashcard findById(Long id) {
        return entityManager.find(Flashcard.class, id);
    }
    
    @Override
    public List<Flashcard> findByPageId(Long pageId) {
        TypedQuery<Flashcard> query = entityManager.createQuery(
            "SELECT f FROM Flashcard f WHERE f.pageId = :pageId ORDER BY f.createdAt DESC",
            Flashcard.class);
        query.setParameter("pageId", pageId);
        return query.getResultList();
    }
    
    @Override
    public List<Flashcard> findByUserId(Long userId) {
        TypedQuery<Flashcard> query = entityManager.createQuery(
            "SELECT f FROM Flashcard f WHERE f.createdBy = :userId ORDER BY f.createdAt DESC",
            Flashcard.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    @Override
    public void update(Flashcard flashcard) {
        entityManager.merge(flashcard);
    }
    
    @Override
    public void delete(Long id) {
        Flashcard flashcard = findById(id);
        if (flashcard != null) {
            entityManager.remove(flashcard);
        }
    }
}

