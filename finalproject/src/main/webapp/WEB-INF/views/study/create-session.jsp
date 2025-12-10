<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Create Study Session - Document Platform</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <div class="container">
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        
        <h2>Create Study Session</h2>
        
        <c:if test="${not empty page}">
            <div class="form-group">
                <label>Page:</label>
                <p><strong>${page.title}</strong></p>
            </div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/study/sessions/create" method="post">
            <input type="hidden" name="pageId" value="${pageId}">
            
            <div class="form-group">
                <label for="durationMinutes">Duration (minutes):</label>
                <input type="number" id="durationMinutes" name="durationMinutes" 
                       min="5" max="300" value="60" class="form-control">
            </div>
            
            <div class="form-group">
                <button type="submit" class="btn">Create Session</button>
                <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>

