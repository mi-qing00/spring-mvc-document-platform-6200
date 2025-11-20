<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Document Platform</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <div class="container text-center">
        <h2>Document Collaboration Platform</h2>
        <p style="color: #456882; margin: 30px 0;">
            Create, organize, and share your documents in hierarchical structures.
        </p>
        
        <div style="margin-top: 40px;">
            <a href="${pageContext.request.contextPath}/users/register" class="btn" style="margin-right: 10px;">
                Get Started
            </a>
            <a href="${pageContext.request.contextPath}/users/login" class="btn btn-secondary">
                Login
            </a>
        </div>
    </div>
</body>
</html>