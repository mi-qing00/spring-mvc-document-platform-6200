<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Create Flashcard - Document Platform</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <div class="container">
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        
        <h2>Create Flashcard</h2>
        
        <c:if test="${not empty page}">
            <div class="form-group">
                <label>Page:</label>
                <p><strong>${page.title}</strong></p>
            </div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/flashcards/create" method="post">
            <input type="hidden" name="pageId" value="${pageId}">
            
            <div class="form-group">
                <label for="front">Front (Question):</label>
                <textarea id="front" name="front" rows="3" required class="form-control"></textarea>
            </div>
            
            <div class="form-group">
                <label for="back">Back (Answer):</label>
                <textarea id="back" name="back" rows="5" required class="form-control"></textarea>
            </div>
            
            <div class="form-group">
                <button type="submit" class="btn">Create Flashcard</button>
                <a href="${pageContext.request.contextPath}/flashcards/page/${pageId}" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>

