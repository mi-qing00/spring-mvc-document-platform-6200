package com.neu.csye6220.qingmi.finalproject.dao;

import com.neu.csye6220.qingmi.finalproject.entity.Flashcard;
import java.util.List;

public interface FlashcardDAO {
    
    void save(Flashcard flashcard);
    
    Flashcard findById(Long id);
    
    List<Flashcard> findByPageId(Long pageId);
    
    List<Flashcard> findByUserId(Long userId);
    
    void update(Flashcard flashcard);
    
    void delete(Long id);
}

