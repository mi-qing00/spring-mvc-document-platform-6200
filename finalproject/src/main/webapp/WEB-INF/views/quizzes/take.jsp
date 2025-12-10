<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Take Quiz: ${quiz.title}</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <div class="container wide-container">
        <h2>${quiz.title}</h2>
        
        <c:if test="${not empty quiz.timeLimitMinutes}">
            <p style="color: var(--light-blue);">Time Limit: ${quiz.timeLimitMinutes} minutes</p>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/quizzes/${quiz.id}/submit" method="post">
            <input type="hidden" name="attemptId" value="${attempt.id}">
            
            <c:forEach items="${quiz.questions}" var="question" varStatus="status">
                <div class="page-card" style="margin-bottom: 30px;">
                    <div class="page-card-header">
                        <h3 class="page-card-title">Question ${status.index + 1}</h3>
                    </div>
                    <div class="page-card-divider"></div>
                    <div class="page-card-preview">
                        <p><strong>${question.questionText}</strong></p>
                        
                        <c:choose>
                            <c:when test="${question.questionType == 'MULTIPLE_CHOICE'}">
                                <div style="margin-top: 15px;">
                                    <label style="display: block; margin-bottom: 10px;">
                                        <input type="radio" name="answer_${question.id}" value="A" required>
                                        A) ${question.optionA}
                                    </label>
                                    <label style="display: block; margin-bottom: 10px;">
                                        <input type="radio" name="answer_${question.id}" value="B" required>
                                        B) ${question.optionB}
                                    </label>
                                    <label style="display: block; margin-bottom: 10px;">
                                        <input type="radio" name="answer_${question.id}" value="C" required>
                                        C) ${question.optionC}
                                    </label>
                                    <label style="display: block; margin-bottom: 10px;">
                                        <input type="radio" name="answer_${question.id}" value="D" required>
                                        D) ${question.optionD}
                                    </label>
                                </div>
                            </c:when>
                            <c:when test="${question.questionType == 'TRUE_FALSE'}">
                                <div style="margin-top: 15px;">
                                    <label style="display: block; margin-bottom: 10px;">
                                        <input type="radio" name="answer_${question.id}" value="True" required>
                                        True
                                    </label>
                                    <label style="display: block; margin-bottom: 10px;">
                                        <input type="radio" name="answer_${question.id}" value="False" required>
                                        False
                                    </label>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div style="margin-top: 15px;">
                                    <input type="text" name="answer_${question.id}" required 
                                           class="form-control" placeholder="Your answer">
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:forEach>
            
            <div class="form-group">
                <button type="submit" class="btn">Submit Quiz</button>
                <a href="${pageContext.request.contextPath}/quizzes/${quiz.id}" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>

