<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Study Sessions - Document Platform</title>
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
        
        <div class="dashboard-welcome">
            <h2>Study Sessions</h2>
            <a href="${pageContext.request.contextPath}/study/sessions/new" class="btn" style="margin-top: 15px;">
                Create New Session
            </a>
        </div>
        
        <c:choose>
            <c:when test="${not empty sessions}">
                <div class="page-cards">
                    <c:forEach items="${sessions}" var="session">
                        <div class="page-card">
                            <div class="page-card-header">
                                <h3 class="page-card-title">Session #${session.id}</h3>
                                <span class="page-card-date">
                                    <fmt:formatDate value="${session.startedAt}" pattern="MMM dd, yyyy HH:mm"/>
                                </span>
                            </div>
                            
                            <div class="page-card-divider"></div>
                            
                            <div class="page-card-meta">
                                Status: <strong>${session.status}</strong><br>
                                <c:if test="${not empty session.durationMinutes}">
                                    Duration: ${session.durationMinutes} minutes
                                </c:if>
                            </div>
                            
                            <div class="page-card-actions">
                                <a href="${pageContext.request.contextPath}/study/sessions/${session.id}" class="btn">View</a>
                                <c:if test="${session.status == 'PLANNED'}">
                                    <form action="${pageContext.request.contextPath}/study/sessions/${session.id}/start" method="post" style="display: inline;">
                                        <button type="submit" class="btn btn-secondary">Start</button>
                                    </form>
                                </c:if>
                                <c:if test="${session.status == 'IN_PROGRESS'}">
                                    <form action="${pageContext.request.contextPath}/study/sessions/${session.id}/end" method="post" style="display: inline;">
                                        <button type="submit" class="btn btn-secondary">End</button>
                                    </form>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <p>You haven't created any study sessions yet.</p>
                    <a href="${pageContext.request.contextPath}/study/sessions/new" class="btn">
                        Create Your First Session
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>

