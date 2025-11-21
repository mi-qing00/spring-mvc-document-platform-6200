<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Search Results - Document Platform</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <div class="container wide-container">
        <div class="search-bar">
            <form action="${pageContext.request.contextPath}/pages/search" method="get">
                <input type="text" name="q" placeholder="Search pages..." value="${query}" required>
                <button type="submit">Search</button>
            </form>
        </div>
        
        <h2>Search Results</h2>
        
        <c:choose>
            <c:when test="${not empty results}">
                <p style="color: var(--beige); margin-bottom: 20px;">
                    Found ${results.size()} page${results.size() != 1 ? 's' : ''}
                </p>
                
                <ul class="search-results-list">
                    <c:forEach items="${results}" var="page">
                        <li class="search-result-item">
                            <div class="search-result-title">
                                <a href="${pageContext.request.contextPath}/pages/${page.id}">${page.title}</a>
                            </div>
                            <div class="search-result-meta">
                                Created ${page.getFormattedDate()}
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <p>No pages found matching "${query}"</p>
                    <a href="${pageContext.request.contextPath}/dashboard" class="btn">Back to Dashboard</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>