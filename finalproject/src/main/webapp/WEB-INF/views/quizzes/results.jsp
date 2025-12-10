<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Quiz Results - ${quiz.title}</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <div class="container wide-container">
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        
        <h2>Quiz Results: ${quiz.title}</h2>
        
        <div class="form-group">
            <h3 style="color: var(--dark-blue);">Your Score: ${attempt.score}%</h3>
            <c:if test="${not empty attempt.timeTakenSeconds}">
                <p>Time Taken: ${attempt.timeTakenSeconds} seconds</p>
            </c:if>
            <p>Completed: <fmt:formatDate value="${attempt.completedAt}" pattern="MMM dd, yyyy HH:mm"/></p>
        </div>
        
        <c:if test="${not empty quiz.questions}">
            <h3 style="color: var(--dark-blue); margin-top: 30px;">Question Review</h3>
            <c:forEach items="${quiz.questions}" var="question" varStatus="status">
                <div class="page-card" style="margin-bottom: 20px;">
                    <div class="page-card-header">
                        <h3 class="page-card-title">Question ${status.index + 1}</h3>
                    </div>
                    <div class="page-card-divider"></div>
                    <div class="page-card-preview">
                        <p><strong>${question.questionText}</strong></p>
                        <p style="margin-top: 10px;">
                            <strong>Correct Answer:</strong> ${question.correctAnswer}
                        </p>
                        <!-- Note: User answers would need to be loaded separately -->
                    </div>
                </div>
            </c:forEach>
        </c:if>
        
        <div class="form-group">
            <a href="${pageContext.request.contextPath}/quizzes/${quiz.id}" class="btn">Back to Quiz</a>
            <a href="${pageContext.request.contextPath}/quizzes" class="btn btn-secondary">All Quizzes</a>
        </div>
    </div>
</body>
</html>

