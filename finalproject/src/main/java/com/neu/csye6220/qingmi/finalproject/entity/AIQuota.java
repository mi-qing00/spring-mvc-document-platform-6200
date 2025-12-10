package com.neu.csye6220.qingmi.finalproject.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ai_quota")
public class AIQuota {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "quota_date", nullable = false)
    private LocalDate quotaDate;
    
    @Column(name = "count_used")
    private Integer countUsed = 0;
    
    @Column(name = "daily_limit")
    private Integer dailyLimit = 5;
    
    // Constructors
    public AIQuota() {
        this.countUsed = 0;
        this.dailyLimit = 5;
    }
    
    public AIQuota(Long userId, LocalDate quotaDate) {
        this.userId = userId;
        this.quotaDate = quotaDate;
        this.countUsed = 0;
        this.dailyLimit = 5;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public LocalDate getQuotaDate() {
        return quotaDate;
    }
    
    public void setQuotaDate(LocalDate quotaDate) {
        this.quotaDate = quotaDate;
    }
    
    public Integer getCountUsed() {
        return countUsed;
    }
    
    public void setCountUsed(Integer countUsed) {
        this.countUsed = countUsed;
    }
    
    public Integer getDailyLimit() {
        return dailyLimit;
    }
    
    public void setDailyLimit(Integer dailyLimit) {
        this.dailyLimit = dailyLimit;
    }
    
    // Helper methods
    public boolean hasQuotaAvailable() {
        return countUsed < dailyLimit;
    }
    
    public int getRemainingQuota() {
        return Math.max(0, dailyLimit - countUsed);
    }
    
    public void incrementUsage() {
        if (hasQuotaAvailable()) {
            countUsed++;
        }
    }
    
    @Override
    public String toString() {
        return "AIQuota{" +
                "id=" + id +
                ", userId=" + userId +
                ", quotaDate=" + quotaDate +
                ", countUsed=" + countUsed +
                ", dailyLimit=" + dailyLimit +
                '}';
    }
}

