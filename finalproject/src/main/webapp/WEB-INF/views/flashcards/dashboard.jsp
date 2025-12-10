<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Flashcards Dashboard - Document Platform</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <div class="container wide-container">
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        
        <div class="dashboard-welcome">
            <h2>Flashcards Dashboard</h2>
            <c:if test="${not empty dueCards and dueCards.size() > 0}">
                <a href="${pageContext.request.contextPath}/flashcards/study" class="btn" style="margin-top: 15px;">
                    Study Now (${dueCards.size()} due)
                </a>
            </c:if>
        </div>
        
        <c:if test="${not empty dueCards and dueCards.size() > 0}">
            <h3 style="color: var(--dark-blue); margin-top: 30px;">Cards Due for Review</h3>
            <div class="page-cards">
                <c:forEach items="${dueCards}" var="card">
                    <div class="page-card">
                        <div class="page-card-header">
                            <h3 class="page-card-title">${card.front}</h3>
                        </div>
                        <div class="page-card-divider"></div>
                        <div class="page-card-preview">
                            ${card.back.length() > 100 ? card.back.substring(0, 100).concat('...') : card.back}
                        </div>
                        <div class="page-card-actions">
                            <a href="${pageContext.request.contextPath}/flashcards/${card.id}/edit" class="btn">Edit</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
        
        <h3 style="color: var(--dark-blue); margin-top: 30px;">All Flashcards</h3>
        <c:choose>
            <c:when test="${not empty allCards}">
                <div class="page-cards">
                    <c:forEach items="${allCards}" var="card">
                        <div class="page-card">
                            <div class="page-card-header">
                                <h3 class="page-card-title">${card.front}</h3>
                            </div>
                            <div class="page-card-divider"></div>
                            <div class="page-card-preview">
                                ${card.back.length() > 100 ? card.back.substring(0, 100).concat('...') : card.back}
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
                    <p>You haven't created any flashcards yet.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>

