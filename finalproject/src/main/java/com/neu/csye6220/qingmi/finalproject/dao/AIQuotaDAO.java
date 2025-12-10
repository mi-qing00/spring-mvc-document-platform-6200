package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.AIQuota;
import java.time.LocalDate;
import java.util.Optional;

public interface AIQuotaDAO {
    
    void save(AIQuota quota);
    
    AIQuota findById(Long id);
    
    Optional<AIQuota> findByUserIdAndDate(Long userId, LocalDate date);
    
    void update(AIQuota quota);
    
    void delete(Long id);
}

