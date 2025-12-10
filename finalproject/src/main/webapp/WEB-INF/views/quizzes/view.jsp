<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>${quiz.title} - Document Platform</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <div class="container wide-container">
        <h2>${quiz.title}</h2>
        
        <div class="form-group">
            <c:if test="${quiz.aiGenerated}">
                <p><strong>🤖 AI Generated</strong></p>
            </c:if>
            <c:if test="${not empty quiz.timeLimitMinutes}">
                <p>Time Limit: ${quiz.timeLimitMinutes} minutes</p>
            </c:if>
        </div>
        
        <c:if test="${not empty quiz.questions}">
            <h3 style="color: var(--dark-blue); margin-top: 30px;">Questions (${quiz.questions.size()})</h3>
            <c:forEach items="${quiz.questions}" var="question" varStatus="status">
                <div class="page-card" style="margin-bottom: 20px;">
                    <div class="page-card-header">
                        <h3 class="page-card-title">Question ${status.index + 1}: ${question.questionType}</h3>
                    </div>
                    <div class="page-card-divider"></div>
                    <div class="page-card-preview">
                        <p><strong>${question.questionText}</strong></p>
                        <c:if test="${question.questionType == 'MULTIPLE_CHOICE'}">
                            <p>A) ${question.optionA}</p>
                            <p>B) ${question.optionB}</p>
                            <p>C) ${question.optionC}</p>
                            <p>D) ${question.optionD}</p>
                        </c:if>
                        <p style="margin-top: 10px; color: var(--light-blue);">
                            Correct Answer: ${question.correctAnswer}
                        </p>
                    </div>
                </div>
            </c:forEach>
        </c:if>
        
        <div class="form-group">
            <a href="${pageContext.request.contextPath}/quizzes/${quiz.id}/questions" class="btn">Manage Questions</a>
            <a href="${pageContext.request.contextPath}/quizzes/${quiz.id}/take" class="btn">Take Quiz</a>
            <form action="${pageContext.request.contextPath}/quizzes/${quiz.id}/delete" method="post" 
                  style="display: inline;" onsubmit="return confirm('Delete this quiz?');">
                <button type="submit" class="btn btn-secondary">Delete</button>
            </form>
            <a href="${pageContext.request.contextPath}/quizzes/page/${quiz.pageId}" class="btn btn-secondary">Back</a>
        </div>
    </div>
</body>
</html>

