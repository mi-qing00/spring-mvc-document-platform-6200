package com.neu.csye6220.qingmi.finalproject.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flashcards")
public class Flashcard {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "page_id", nullable = false)
    private Long pageId;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String front;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String back;
    
    @Column(name = "created_by", nullable = false)
    private Long createdBy;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id", insertable = false, updatable = false)
    private Page page;
    
    @OneToMany(mappedBy = "flashcard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardReview> reviews = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Constructors
    public Flashcard() {
    }
    
    public Flashcard(Long pageId, String front, String back, Long createdBy) {
        this.pageId = pageId;
        this.front = front;
        this.back = back;
        this.createdBy = createdBy;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getPageId() {
        return pageId;
    }
    
    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }
    
    public String getFront() {
        return front;
    }
    
    public void setFront(String front) {
        this.front = front;
    }
    
    public String getBack() {
        return back;
    }
    
    public void setBack(String back) {
        this.back = back;
    }
    
    public Long getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Page getPage() {
        return page;
    }
    
    public void setPage(Page page) {
        this.page = page;
    }
    
    public List<CardReview> getReviews() {
        return reviews;
    }
    
    public void setReviews(List<CardReview> reviews) {
        this.reviews = reviews;
    }
    
    @Override
    public String toString() {
        return "Flashcard{" +
                "id=" + id +
                ", pageId=" + pageId +
                ", front='" + front + '\'' +
                ", createdBy=" + createdBy +
                '}';
    }
}

