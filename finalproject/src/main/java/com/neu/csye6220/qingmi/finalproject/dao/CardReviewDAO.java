package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.CardReview;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CardReviewDAO {
    
    void save(CardReview review);
    
    CardReview findById(Long id);
    
    List<CardReview> findByCardId(Long cardId);
    
    List<CardReview> findByUserId(Long userId);
    
    List<CardReview> findDueCards(Long userId, LocalDate date);
    
    Optional<CardReview> findLatestReview(Long cardId, Long userId);
    
    void update(CardReview review);
    
    void delete(Long id);
}

