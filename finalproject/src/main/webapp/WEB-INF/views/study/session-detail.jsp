<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Session Details - Document Platform</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <div class="container wide-container">
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        
        <h2>Study Session #${session.id}</h2>
        
        <div class="form-group">
            <label>Status:</label>
            <p><strong>${session.status}</strong></p>
        </div>
        
        <div class="form-group">
            <label>Started At:</label>
            <p><fmt:formatDate value="${session.startedAt}" pattern="MMM dd, yyyy HH:mm"/></p>
        </div>
        
        <c:if test="${not empty session.endedAt}">
            <div class="form-group">
                <label>Ended At:</label>
                <p><fmt:formatDate value="${session.endedAt}" pattern="MMM dd, yyyy HH:mm"/></p>
            </div>
        </c:if>
        
        <c:if test="${not empty session.durationMinutes}">
            <div class="form-group">
                <label>Duration:</label>
                <p>${session.durationMinutes} minutes</p>
            </div>
        </c:if>
        
        <c:if test="${not empty session.studyPlan}">
            <div class="form-group">
                <label>Study Plan:</label>
                <div style="white-space: pre-wrap; background: #f5f5f5; padding: 15px; border-radius: 4px;">
                    ${session.studyPlan}
                </div>
            </div>
        </c:if>
        
        <c:if test="${not empty session.summary}">
            <div class="form-group">
                <label>Summary:</label>
                <div style="white-space: pre-wrap; background: #f5f5f5; padding: 15px; border-radius: 4px;">
                    ${session.summary}
                </div>
            </div>
        </c:if>
        
        <div class="form-group">
            <c:if test="${session.status == 'PLANNED'}">
                <form action="${pageContext.request.contextPath}/study/sessions/${session.id}/start" method="post" style="display: inline;">
                    <button type="submit" class="btn">Start Session</button>
                </form>
            </c:if>
            <c:if test="${session.status == 'IN_PROGRESS'}">
                <form action="${pageContext.request.contextPath}/study/sessions/${session.id}/end" method="post" style="display: inline;">
                    <button type="submit" class="btn">End Session</button>
                </form>
            </c:if>
            <form action="${pageContext.request.contextPath}/study/sessions/${session.id}/delete" method="post" style="display: inline;" 
                  onsubmit="return confirm('Are you sure you want to delete this session?');">
                <button type="submit" class="btn btn-secondary">Delete</button>
            </form>
            <a href="${pageContext.request.contextPath}/study/sessions" class="btn btn-secondary">Back to Sessions</a>
        </div>
    </div>
</body>
</html>

