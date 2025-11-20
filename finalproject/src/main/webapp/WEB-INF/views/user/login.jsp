<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Login - Document Platform</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <div class="container">
        <h2 class="text-center">Login</h2>
        
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/users/login" method="post">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" value="${loginDTO.email}" required>
            </div>
            
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>
            
            <button type="submit" style="width: 100%;">Login</button>
            
            <p class="text-center mt-20">
                Don't have an account? 
                <a href="${pageContext.request.contextPath}/users/register">Register here</a>
            </p>
        </form>
    </div>
</body>
</html>