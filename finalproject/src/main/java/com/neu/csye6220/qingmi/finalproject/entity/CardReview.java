package com.neu.csye6220.qingmi.finalproject.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "card_reviews")
public class CardReview {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "card_id", nullable = false)
    private Long cardId;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Difficulty difficulty;
    
    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;
    
    @Column(name = "next_review_date", nullable = false)
    private LocalDate nextReviewDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", insertable = false, updatable = false)
    private Flashcard flashcard;
    
    @PrePersist
    protected void onCreate() {
        reviewedAt = LocalDateTime.now();
        if (nextReviewDate == null && difficulty != null) {
            nextReviewDate = calculateNextReview(difficulty, LocalDate.now());
        }
    }
    
    // Constructors
    public CardReview() {
    }
    
    public CardReview(Long cardId, Long userId, Difficulty difficulty) {
        this.cardId = cardId;
        this.userId = userId;
        this.difficulty = difficulty;
        this.reviewedAt = LocalDateTime.now();
        this.nextReviewDate = calculateNextReview(difficulty, LocalDate.now());
    }
    
    // Helper method for spaced repetition
    public static LocalDate calculateNextReview(Difficulty difficulty, LocalDate today) {
        return switch (difficulty) {
            case EASY -> today.plusDays(7);
            case MEDIUM -> today.plusDays(3);
            case HARD -> today.plusDays(1);
        };
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getCardId() {
        return cardId;
    }
    
    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Difficulty getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        if (difficulty != null) {
            this.nextReviewDate = calculateNextReview(difficulty, LocalDate.now());
        }
    }
    
    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }
    
    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
    
    public LocalDate getNextReviewDate() {
        return nextReviewDate;
    }
    
    public void setNextReviewDate(LocalDate nextReviewDate) {
        this.nextReviewDate = nextReviewDate;
    }
    
    public Flashcard getFlashcard() {
        return flashcard;
    }
    
    public void setFlashcard(Flashcard flashcard) {
        this.flashcard = flashcard;
    }
    
    @Override
    public String toString() {
        return "CardReview{" +
                "id=" + id +
                ", cardId=" + cardId +
                ", userId=" + userId +
                ", difficulty=" + difficulty +
                ", nextReviewDate=" + nextReviewDate +
                '}';
    }
}

