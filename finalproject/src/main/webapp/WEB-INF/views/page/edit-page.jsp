<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Edit Page - Document Platform</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <div class="container wide-container">
        <h2>Edit Page</h2>
        
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/pages/${page.id}/update" method="post">
            <div class="form-group">
                <label for="title">Title</label>
                <input type="text" id="title" name="title" value="${pageDTO.title}" required maxlength="200">
            </div>
            
            <div class="form-group">
                <label for="content">Content</label>
                <textarea id="content" name="content" required>${pageDTO.content}</textarea>
            </div>
            
            <div class="form-actions">
                <button type="submit">Save Changes</button>
                <a href="${pageContext.request.contextPath}/pages/${page.id}" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>