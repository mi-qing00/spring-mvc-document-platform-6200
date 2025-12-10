<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Edit Flashcard - Document Platform</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <div class="container">
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        
        <h2>Edit Flashcard</h2>
        
        <form action="${pageContext.request.contextPath}/flashcards/${flashcard.id}/update" method="post">
            <div class="form-group">
                <label for="front">Front (Question):</label>
                <textarea id="front" name="front" rows="3" required class="form-control">${flashcard.front}</textarea>
            </div>
            
            <div class="form-group">
                <label for="back">Back (Answer):</label>
                <textarea id="back" name="back" rows="5" required class="form-control">${flashcard.back}</textarea>
            </div>
            
            <div class="form-group">
                <button type="submit" class="btn">Update Flashcard</button>
                <a href="${pageContext.request.contextPath}/flashcards/page/${flashcard.pageId}" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>

