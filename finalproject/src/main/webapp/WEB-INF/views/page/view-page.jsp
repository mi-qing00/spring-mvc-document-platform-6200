<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>${page.title} - Document Platform</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <div class="breadcrumb">
        <a href="${pageContext.request.contextPath}/dashboard">Home</a>
        <c:forEach items="${breadcrumbs}" var="crumb" varStatus="status">
            <span class="breadcrumb-separator">></span>
            <c:choose>
                <c:when test="${status.last}">
                    <span class="breadcrumb-current">${crumb.title}</span>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/pages/${crumb.id}">${crumb.title}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>
    
    <div class="container wide-container">
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        
        <div class="page-header">
            <h2>${page.title}</h2>
            <div class="page-actions">
                <a href="${pageContext.request.contextPath}/pages/new?parentId=${page.id}" class="btn">
                    Create Child
                </a>
                <a href="${pageContext.request.contextPath}/pages/${page.id}/edit" class="btn btn-secondary">
                    Edit
                </a>
                <form action="${pageContext.request.contextPath}/pages/${page.id}/delete" 
                      method="post" 
                      style="display: inline;"
                      onsubmit="return confirm('Delete this page and all its children?');">
                    <button type="submit" class="btn btn-danger">Delete</button>
                </form>
            </div>
        </div>
        
        <div class="page-content">
            ${page.content}
        </div>
        
        <c:if test="${not empty children}">
            <div class="children-section">
                <h3>Child Pages</h3>
                <ul class="child-list">
                    <c:forEach items="${children}" var="child">
                        <li class="child-item">
                            <a href="${pageContext.request.contextPath}/pages/${child.id}" class="child-item-title">
                                ${child.title}
                            </a>
                            <a href="${pageContext.request.contextPath}/pages/${child.id}" class="btn btn-secondary">
                                View
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
    </div>
</body>
</html>