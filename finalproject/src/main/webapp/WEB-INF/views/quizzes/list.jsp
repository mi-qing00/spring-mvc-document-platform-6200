<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Quizzes - Document Platform</title>
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
            <h2>Quizzes</h2>
            <c:if test="${not empty page}">
                <h3 style="color: var(--dark-blue); margin-top: 15px;">For: ${page.title}</h3>
                <a href="${pageContext.request.contextPath}/quizzes/new?pageId=${page.id}" class="btn" style="margin-top: 15px;">
                    Create New Quiz
                </a>
                <a href="${pageContext.request.contextPath}/pages/${page.id}" class="btn btn-secondary" style="margin-top: 15px;">
                    Back to Page
                </a>
            </c:if>
        </div>
        
        <c:choose>
            <c:when test="${not empty quizzes}">
                <div class="page-cards">
                    <c:forEach items="${quizzes}" var="quiz">
                        <div class="page-card">
                            <div class="page-card-header">
                                <h3 class="page-card-title">${quiz.title}</h3>
                                <span class="page-card-date">
                                    <fmt:formatDate value="${quiz.createdAt}" pattern="MMM dd, yyyy"/>
                                </span>
                            </div>
                            
                            <div class="page-card-divider"></div>
                            
                            <div class="page-card-meta">
                                <c:if test="${quiz.aiGenerated}">
                                    🤖 AI Generated
                                </c:if>
                                <c:if test="${not empty quiz.timeLimitMinutes}">
                                    ⏱️ ${quiz.timeLimitMinutes} minutes
                                </c:if>
                            </div>
                            
                            <div class="page-card-actions">
                                <a href="${pageContext.request.contextPath}/quizzes/${quiz.id}" class="btn">View</a>
                                <a href="${pageContext.request.contextPath}/quizzes/${quiz.id}/take" class="btn btn-secondary">Take Quiz</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <p>No quizzes yet.</p>
                    <c:if test="${not empty page}">
                        <a href="${pageContext.request.contextPath}/quizzes/new?pageId=${page.id}" class="btn">
                            Create Your First Quiz
                        </a>
                    </c:if>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>

