package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.ProficiencyScore;
import java.util.List;
import java.util.Optional;

public interface ProficiencyScoreDAO {
    
    void save(ProficiencyScore score);
    
    ProficiencyScore findById(Long id);
    
    List<ProficiencyScore> findByUserId(Long userId);
    
    Optional<ProficiencyScore> findByUserIdAndTopic(Long userId, String topic);
    
    void update(ProficiencyScore score);
    
    void delete(Long id);
}

