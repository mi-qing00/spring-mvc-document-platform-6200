<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Create Quiz - Document Platform</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <div class="container">
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        
        <h2>Create Quiz</h2>
        
        <c:if test="${not empty page}">
            <div class="form-group">
                <label>Page:</label>
                <p><strong>${page.title}</strong></p>
            </div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/quizzes/create" method="post">
            <input type="hidden" name="pageId" value="${pageId}">
            
            <div class="form-group">
                <label for="title">Quiz Title:</label>
                <input type="text" id="title" name="title" required class="form-control">
            </div>
            
            <div class="form-group">
                <label for="timeLimitMinutes">Time Limit (minutes, optional):</label>
                <input type="number" id="timeLimitMinutes" name="timeLimitMinutes" 
                       min="1" max="300" class="form-control">
            </div>
            
            <div class="form-group">
                <button type="submit" class="btn">Create Quiz</button>
                <a href="${pageContext.request.contextPath}/quizzes/page/${pageId}" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>

