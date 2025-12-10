package com.neu.csye6220.qingmi.finalproject.service;

import com.neu.csye6220.qingmi.finalproject.dao.FlashcardDAO;
import com.neu.csye6220.qingmi.finalproject.dao.CardReviewDAO;
import com.neu.csye6220.qingmi.finalproject.dao.PageDAO;
import com.neu.csye6220.qingmi.finalproject.entity.Flashcard;
import com.neu.csye6220.qingmi.finalproject.entity.CardReview;
import com.neu.csye6220.qingmi.finalproject.entity.Difficulty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FlashcardServiceImpl implements FlashcardService {
    
    private final FlashcardDAO flashcardDAO;
    private final CardReviewDAO reviewDAO;
    private final PageDAO pageDAO;
    
    @Autowired
    public FlashcardServiceImpl(FlashcardDAO flashcardDAO, CardReviewDAO reviewDAO, PageDAO pageDAO) {
        this.flashcardDAO = flashcardDAO;
        this.reviewDAO = reviewDAO;
        this.pageDAO = pageDAO;
    }
    
    @Override
    public Flashcard createFlashcard(Long pageId, String front, String back, Long userId) {
        // Validate page exists and user owns it
        var page = pageDAO.findById(pageId);
        if (page == null) {
            throw new IllegalArgumentException("Page not found");
        }
        if (!page.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Access denied");
        }
        
        if (front == null || front.trim().isEmpty()) {
            throw new IllegalArgumentException("Front text is required");
        }
        if (back == null || back.trim().isEmpty()) {
            throw new IllegalArgumentException("Back text is required");
        }
        
        Flashcard flashcard = new Flashcard(pageId, front.trim(), back.trim(), userId);
        flashcardDAO.save(flashcard);
        return flashcard;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Flashcard getFlashcard(Long cardId, Long userId) {
        Flashcard flashcard = flashcardDAO.findById(cardId);
        if (flashcard == null) {
            throw new IllegalArgumentException("Flashcard not found");
        }
        // Check if user owns the page
        var page = pageDAO.findById(flashcard.getPageId());
        if (page == null || !page.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Access denied");
        }
        return flashcard;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Flashcard> getFlashcardsByPage(Long pageId, Long userId) {
        // Validate page access
        var page = pageDAO.findById(pageId);
        if (page == null || !page.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Access denied");
        }
        return flashcardDAO.findByPageId(pageId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Flashcard> getFlashcardsByUser(Long userId) {
        return flashcardDAO.findByUserId(userId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Flashcard> getDueFlashcards(Long userId) {
        LocalDate today = LocalDate.now();
        List<CardReview> dueReviews = reviewDAO.findDueCards(userId, today);
        
        // Get unique flashcards from reviews
        return dueReviews.stream()
            .map(review -> flashcardDAO.findById(review.getCardId()))
            .filter(card -> card != null)
            .distinct()
            .collect(Collectors.toList());
    }
    
    @Override
    public void updateFlashcard(Long cardId, Long userId, String front, String back) {
        Flashcard flashcard = getFlashcard(cardId, userId);
        
        if (front == null || front.trim().isEmpty()) {
            throw new IllegalArgumentException("Front text is required");
        }
        if (back == null || back.trim().isEmpty()) {
            throw new IllegalArgumentException("Back text is required");
        }
        
        flashcard.setFront(front.trim());
        flashcard.setBack(back.trim());
        flashcardDAO.update(flashcard);
    }
    
    @Override
    public void deleteFlashcard(Long cardId, Long userId) {
        Flashcard flashcard = getFlashcard(cardId, userId);
        flashcardDAO.delete(cardId);
    }
    
    @Override
    public CardReview reviewFlashcard(Long cardId, Long userId, Difficulty difficulty) {
        Flashcard flashcard = getFlashcard(cardId, userId);
        
        CardReview review = new CardReview(cardId, userId, difficulty);
        reviewDAO.save(review);
        
        return review;
    }
}

