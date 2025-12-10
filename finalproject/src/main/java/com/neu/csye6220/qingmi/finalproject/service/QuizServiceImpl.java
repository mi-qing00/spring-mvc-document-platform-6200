package com.neu.csye6220.qingmi.finalproject.service;

import com.neu.csye6220.qingmi.finalproject.dao.QuizDAO;
import com.neu.csye6220.qingmi.finalproject.dao.QuestionDAO;
import com.neu.csye6220.qingmi.finalproject.dao.QuizAttemptDAO;
import com.neu.csye6220.qingmi.finalproject.dao.UserAnswerDAO;
import com.neu.csye6220.qingmi.finalproject.dao.PageDAO;
import com.neu.csye6220.qingmi.finalproject.entity.Quiz;
import com.neu.csye6220.qingmi.finalproject.entity.Question;
import com.neu.csye6220.qingmi.finalproject.entity.QuizAttempt;
import com.neu.csye6220.qingmi.finalproject.entity.UserAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {
    
    private final QuizDAO quizDAO;
    private final QuestionDAO questionDAO;
    private final QuizAttemptDAO attemptDAO;
    private final UserAnswerDAO answerDAO;
    private final PageDAO pageDAO;
    
    @Autowired
    public QuizServiceImpl(QuizDAO quizDAO, QuestionDAO questionDAO, 
                          QuizAttemptDAO attemptDAO, UserAnswerDAO answerDAO,
                          PageDAO pageDAO) {
        this.quizDAO = quizDAO;
        this.questionDAO = questionDAO;
        this.attemptDAO = attemptDAO;
        this.answerDAO = answerDAO;
        this.pageDAO = pageDAO;
    }
    
    @Override
    public Quiz createQuiz(String title, Long pageId, Long userId, Integer timeLimitMinutes) {
        // Validate page exists and user owns it
        var page = pageDAO.findById(pageId);
        if (page == null) {
            throw new IllegalArgumentException("Page not found");
        }
        if (!page.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Access denied");
        }
        
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        
        Quiz quiz = new Quiz(title.trim(), pageId, userId);
        quiz.setTimeLimitMinutes(timeLimitMinutes);
        quizDAO.save(quiz);
        return quiz;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Quiz getQuiz(Long quizId, Long userId) {
        Quiz quiz = quizDAO.findById(quizId);
        if (quiz == null) {
            throw new IllegalArgumentException("Quiz not found");
        }
        // Check if user owns the page
        var page = pageDAO.findById(quiz.getPageId());
        if (page == null || !page.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Access denied");
        }
        return quiz;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Quiz> getQuizzesByPage(Long pageId, Long userId) {
        // Validate page access
        var page = pageDAO.findById(pageId);
        if (page == null || !page.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Access denied");
        }
        return quizDAO.findByPageId(pageId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Quiz> getQuizzesByUser(Long userId) {
        return quizDAO.findByUserId(userId);
    }
    
    @Override
    public void addQuestion(Long quizId, Long userId, Question question) {
        Quiz quiz = getQuiz(quizId, userId);
        
        if (question.getQuestionText() == null || question.getQuestionText().trim().isEmpty()) {
            throw new IllegalArgumentException("Question text is required");
        }
        if (question.getCorrectAnswer() == null || question.getCorrectAnswer().trim().isEmpty()) {
            throw new IllegalArgumentException("Correct answer is required");
        }
        
        question.setQuizId(quizId);
        questionDAO.save(question);
    }
    
    @Override
    public void updateQuestion(Long questionId, Long userId, Question question) {
        Question existing = questionDAO.findById(questionId);
        if (existing == null) {
            throw new IllegalArgumentException("Question not found");
        }
        
        // Verify user owns the quiz
        getQuiz(existing.getQuizId(), userId);
        
        existing.setQuestionText(question.getQuestionText());
        existing.setQuestionType(question.getQuestionType());
        existing.setCorrectAnswer(question.getCorrectAnswer());
        existing.setOptionA(question.getOptionA());
        existing.setOptionB(question.getOptionB());
        existing.setOptionC(question.getOptionC());
        existing.setOptionD(question.getOptionD());
        existing.setPoints(question.getPoints());
        
        questionDAO.update(existing);
    }
    
    @Override
    public void deleteQuestion(Long questionId, Long userId) {
        Question question = questionDAO.findById(questionId);
        if (question == null) {
            throw new IllegalArgumentException("Question not found");
        }
        
        // Verify user owns the quiz
        getQuiz(question.getQuizId(), userId);
        
        questionDAO.delete(questionId);
    }
    
    @Override
    public void deleteQuiz(Long quizId, Long userId) {
        Quiz quiz = getQuiz(quizId, userId);
        quizDAO.delete(quizId);
    }
    
    @Override
    public QuizAttempt startQuizAttempt(Long quizId, Long userId) {
        Quiz quiz = quizDAO.findById(quizId);
        if (quiz == null) {
            throw new IllegalArgumentException("Quiz not found");
        }
        
        QuizAttempt attempt = new QuizAttempt(quizId, userId);
        attemptDAO.save(attempt);
        return attempt;
    }
    
    @Override
    public QuizAttempt submitQuizAttempt(Long attemptId, Long userId, Map<Long, String> answers) {
        QuizAttempt attempt = attemptDAO.findById(attemptId);
        if (attempt == null) {
            throw new IllegalArgumentException("Attempt not found");
        }
        if (!attempt.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Access denied");
        }
        
        // Get all questions for the quiz
        List<Question> questions = questionDAO.findByQuizId(attempt.getQuizId());
        
        // Create user answers and check correctness
        for (Question question : questions) {
            String userAnswer = answers.getOrDefault(question.getId(), "");
            boolean isCorrect = checkAnswer(question, userAnswer);
            
            UserAnswer userAnswerEntity = new UserAnswer(
                attemptId, 
                question.getId(), 
                userAnswer, 
                isCorrect
            );
            answerDAO.save(userAnswerEntity);
        }
        
        // Calculate score
        BigDecimal score = scoreQuizAttempt(attempt);
        attempt.setScore(score);
        attemptDAO.update(attempt);
        
        return attempt;
    }
    
    @Override
    @Transactional(readOnly = true)
    public BigDecimal scoreQuizAttempt(QuizAttempt attempt) {
        List<UserAnswer> answers = answerDAO.findByAttemptId(attempt.getId());
        
        if (answers.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        long correctCount = answers.stream()
            .filter(UserAnswer::getIsCorrect)
            .count();
        
        int totalQuestions = answers.size();
        
        return BigDecimal.valueOf((double) correctCount / totalQuestions * 100)
            .setScale(2, RoundingMode.HALF_UP);
    }
    
    private boolean checkAnswer(Question question, String userAnswer) {
        if (userAnswer == null) {
            return false;
        }
        
        String correctAnswer = question.getCorrectAnswer().trim();
        String userAnswerTrimmed = userAnswer.trim();
        
        // Case-insensitive comparison
        return correctAnswer.equalsIgnoreCase(userAnswerTrimmed);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<QuizAttempt> getQuizAttempts(Long quizId, Long userId) {
        Quiz quiz = getQuiz(quizId, userId);
        return attemptDAO.findByQuizIdAndUserId(quizId, userId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public QuizAttempt getQuizAttempt(Long attemptId, Long userId) {
        QuizAttempt attempt = attemptDAO.findById(attemptId);
        if (attempt == null) {
            throw new IllegalArgumentException("Attempt not found");
        }
        if (!attempt.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Access denied");
        }
        return attempt;
    }
}

