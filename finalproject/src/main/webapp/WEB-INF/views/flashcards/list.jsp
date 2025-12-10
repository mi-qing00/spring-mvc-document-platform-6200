<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Flashcards - ${page.title}</title>
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
            <h2>Flashcards for: ${page.title}</h2>
            <a href="${pageContext.request.contextPath}/flashcards/new?pageId=${page.id}" class="btn" style="margin-top: 15px;">
                Create Flashcard
            </a>
            <a href="${pageContext.request.contextPath}/pages/${page.id}" class="btn btn-secondary" style="margin-top: 15px;">
                Back to Page
            </a>
        </div>
        
        <c:choose>
            <c:when test="${not empty flashcards}">
                <div class="page-cards">
                    <c:forEach items="${flashcards}" var="card">
                        <div class="page-card">
                            <div class="page-card-header">
                                <h3 class="page-card-title">${card.front}</h3>
                            </div>
                            <div class="page-card-divider"></div>
                            <div class="page-card-preview">
                                ${card.back}
                            </div>
                            <div class="page-card-actions">
                                <a href="${pageContext.request.contextPath}/flashcards/${card.id}/edit" class="btn">Edit</a>
                                <form action="${pageContext.request.contextPath}/flashcards/${card.id}/delete" method="post" 
                                      style="display: inline;" onsubmit="return confirm('Delete this flashcard?');">
                                    <button type="submit" class="btn btn-secondary">Delete</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <p>No flashcards for this page yet.</p>
                    <a href="${pageContext.request.contextPath}/flashcards/new?pageId=${page.id}" class="btn">
                        Create Your First Flashcard
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>

