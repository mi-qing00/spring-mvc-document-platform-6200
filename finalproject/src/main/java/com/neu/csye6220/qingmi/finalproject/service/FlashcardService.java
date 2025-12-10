package com.neu.csye6220.qingmi.finalproject.service;

import com.neu.csye6220.qingmi.finalproject.entity.Flashcard;
import com.neu.csye6220.qingmi.finalproject.entity.CardReview;
import com.neu.csye6220.qingmi.finalproject.entity.Difficulty;
import java.util.List;

public interface FlashcardService {
    
    Flashcard createFlashcard(Long pageId, String front, String back, Long userId);
    
    Flashcard getFlashcard(Long cardId, Long userId);
    
    List<Flashcard> getFlashcardsByPage(Long pageId, Long userId);
    
    List<Flashcard> getFlashcardsByUser(Long userId);
    
    List<Flashcard> getDueFlashcards(Long userId);
    
    void updateFlashcard(Long cardId, Long userId, String front, String back);
    
    void deleteFlashcard(Long cardId, Long userId);
    
    CardReview reviewFlashcard(Long cardId, Long userId, Difficulty difficulty);
}

