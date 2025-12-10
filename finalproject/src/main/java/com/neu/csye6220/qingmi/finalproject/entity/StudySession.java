package com.neu.csye6220.qingmi.finalproject.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "study_sessions")
public class StudySession {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "page_id", nullable = false)
    private Long pageId;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "duration_minutes")
    private Integer durationMinutes;
    
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    
    @Column(name = "ended_at")
    private LocalDateTime endedAt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SessionStatus status;
    
    @Column(name = "study_plan", columnDefinition = "TEXT")
    private String studyPlan;
    
    @Column(columnDefinition = "TEXT")
    private String summary;
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id", insertable = false, updatable = false)
    private Page page;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    
    // Lifecycle
    @PrePersist
    protected void onCreate() {
        if (startedAt == null) {
            startedAt = LocalDateTime.now();
        }
        if (status == null) {
            status = SessionStatus.PLANNED;
        }
    }
    
    // Constructors
    public StudySession() {
    }
    
    public StudySession(Long pageId, Long userId, Integer durationMinutes) {
        this.pageId = pageId;
        this.userId = userId;
        this.durationMinutes = durationMinutes;
        this.status = SessionStatus.PLANNED;
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
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Integer getDurationMinutes() {
        return durationMinutes;
    }
    
    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
    
    public LocalDateTime getStartedAt() {
        return startedAt;
    }
    
    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }
    
    public LocalDateTime getEndedAt() {
        return endedAt;
    }
    
    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }
    
    public SessionStatus getStatus() {
        return status;
    }
    
    public void setStatus(SessionStatus status) {
        this.status = status;
    }
    
    public String getStudyPlan() {
        return studyPlan;
    }
    
    public void setStudyPlan(String studyPlan) {
        this.studyPlan = studyPlan;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public Page getPage() {
        return page;
    }
    
    public void setPage(Page page) {
        this.page = page;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    // Helper methods
    public void start() {
        this.status = SessionStatus.IN_PROGRESS;
        this.startedAt = LocalDateTime.now();
    }
    
    public void end() {
        this.status = SessionStatus.COMPLETED;
        this.endedAt = LocalDateTime.now();
        if (startedAt != null && endedAt != null) {
            long minutes = java.time.Duration.between(startedAt, endedAt).toMinutes();
            this.durationMinutes = (int) minutes;
        }
    }
    
    public void cancel() {
        this.status = SessionStatus.CANCELLED;
        this.endedAt = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "StudySession{" +
                "id=" + id +
                ", pageId=" + pageId +
                ", userId=" + userId +
                ", status=" + status +
                ", durationMinutes=" + durationMinutes +
                '}';
    }
}

