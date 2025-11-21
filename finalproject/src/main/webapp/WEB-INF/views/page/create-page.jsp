<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Create Page - Document Platform</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <c:if test="${not empty breadcrumbs}">
        <div class="breadcrumb">
            <a href="${pageContext.request.contextPath}/dashboard">Home</a>
            <c:forEach items="${breadcrumbs}" var="crumb">
                <span class="breadcrumb-separator">></span>
                <a href="${pageContext.request.contextPath}/pages/${crumb.id}">${crumb.title}</a>
            </c:forEach>
        </div>
    </c:if>
    
    <div class="container wide-container">
        <c:if test="${not empty parent}">
            <div class="context-info">
                Creating page under: <strong>${parent.title}</strong>
            </div>
        </c:if>
        
        <h2>Create New Page</h2>
        
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/pages/create" method="post">
            <input type="hidden" name="parentId" value="${pageDTO.parentId}">
            
            <div class="form-group">
                <label for="title">Title</label>
                <input type="text" id="title" name="title" value="${pageDTO.title}" required maxlength="200">
            </div>
            
            <div class="form-group">
                <label for="content">Content</label>
                <textarea id="content" name="content" required>${pageDTO.content}</textarea>
            </div>
            
            <div class="form-actions">
                <button type="submit">Create Page</button>
                <a href="${not empty parent ? pageContext.request.contextPath.concat('/pages/').concat(parent.id) : pageContext.request.contextPath.concat('/dashboard')}" 
                   class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>