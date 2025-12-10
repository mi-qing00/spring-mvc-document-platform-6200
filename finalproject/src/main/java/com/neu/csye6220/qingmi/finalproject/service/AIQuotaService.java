package com.neu.csye6220.qingmi.finalproject.service;

import com.neu.csye6220.qingmi.finalproject.entity.AIQuota;
import java.time.LocalDate;

public interface AIQuotaService {
    
    boolean hasQuotaAvailable(Long userId);
    
    void incrementUsage(Long userId);
    
    int getRemainingQuota(Long userId);
    
    AIQuota getOrCreateTodayQuota(Long userId);
}

