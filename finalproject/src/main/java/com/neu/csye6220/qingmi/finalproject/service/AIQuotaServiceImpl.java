package com.neu.csye6220.qingmi.finalproject.service;

import com.neu.csye6220.qingmi.finalproject.dao.AIQuotaDAO;
import com.neu.csye6220.qingmi.finalproject.entity.AIQuota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class AIQuotaServiceImpl implements AIQuotaService {
    
    private static final int FREE_DAILY_LIMIT = 5;
    
    private final AIQuotaDAO quotaDAO;
    
    @Autowired
    public AIQuotaServiceImpl(AIQuotaDAO quotaDAO) {
        this.quotaDAO = quotaDAO;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean hasQuotaAvailable(Long userId) {
        AIQuota quota = getOrCreateTodayQuota(userId);
        return quota.hasQuotaAvailable();
    }
    
    @Override
    public void incrementUsage(Long userId) {
        AIQuota quota = getOrCreateTodayQuota(userId);
        if (quota.hasQuotaAvailable()) {
            quota.incrementUsage();
            quotaDAO.update(quota);
        } else {
            throw new IllegalStateException("Daily AI quota limit reached");
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public int getRemainingQuota(Long userId) {
        AIQuota quota = getOrCreateTodayQuota(userId);
        return quota.getRemainingQuota();
    }
    
    @Override
    public AIQuota getOrCreateTodayQuota(Long userId) {
        LocalDate today = LocalDate.now();
        Optional<AIQuota> existing = quotaDAO.findByUserIdAndDate(userId, today);
        
        if (existing.isPresent()) {
            return existing.get();
        } else {
            AIQuota newQuota = new AIQuota(userId, today);
            newQuota.setDailyLimit(FREE_DAILY_LIMIT);
            quotaDAO.save(newQuota);
            return newQuota;
        }
    }
}

