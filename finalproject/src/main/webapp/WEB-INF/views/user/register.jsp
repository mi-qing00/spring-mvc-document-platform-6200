<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Register - Document Platform</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <div class="container">
        <h2 class="text-center">Create Account</h2>
        
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/users/register" method="post">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" value="${userDTO.email}" required>
            </div>
            
            <div class="form-group">
                <label for="fullName">Full Name</label>
                <input type="text" id="fullName" name="fullName" value="${userDTO.fullName}" required>
            </div>
            
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
                <small style="color: #456882;">Minimum 8 characters</small>
            </div>
            
            <div class="form-group">
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>
            
            <button type="submit" style="width: 100%;">Register</button>
            
            <p class="text-center mt-20">
                Already have an account? 
                <a href="${pageContext.request.contextPath}/users/login">Login here</a>
            </p>
        </form>
    </div>
</body>
</html>