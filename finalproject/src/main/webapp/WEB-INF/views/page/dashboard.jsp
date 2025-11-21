<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Dashboard - Document Platform</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <div class="container wide-container">
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        
        <div class="dashboard-welcome">
            <h2>Welcome, ${userName}!</h2>
            <p>Your document workspace</p>
            <a href="${pageContext.request.contextPath}/pages/new" class="btn" style="margin-top: 15px;">
                Create New Page
            </a>
        </div>
        
        <c:choose>
            <c:when test="${not empty rootPages}">
                <div class="page-cards">
                    <c:forEach items="${rootPages}" var="page">
                        <div class="page-card">
                            <div class="page-card-header">
                                <h3 class="page-card-title">${page.title}</h3>
                                <span class="page-card-date">
                                    ${page.getFormattedDate()}
                                </span>
                            </div>
                            
                            <div class="page-card-divider"></div>
                            
                            <div class="page-card-preview">
                                ${page.content.length() > 150 ? page.content.substring(0, 150).concat('...') : page.content}
                            </div>
                            
                            <div class="page-card-meta">
                                📄 ${page.children.size()} child page${page.children.size() != 1 ? 's' : ''}
                            </div>
                            
                            <div class="page-card-actions">
                                <a href="${pageContext.request.contextPath}/pages/${page.id}" class="btn">View</a>
                                <a href="${pageContext.request.contextPath}/pages/${page.id}/edit" class="btn btn-secondary">Edit</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <p>You haven't created any pages yet.</p>
                    <a href="${pageContext.request.contextPath}/pages/new" class="btn">
                        Create Your First Page
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>