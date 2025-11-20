<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Profile - Document Platform</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <div class="container">
        <h2>Your Profile</h2>
        
        <div class="profile-info">
            <p><strong>Name:</strong> ${user.fullName}</p>
            <p><strong>Email:</strong> ${user.email}</p>
            <p><strong>Member Since:</strong> ${user.createdAt}</p>
        </div>
        
        <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">
            Back to Dashboard
        </a>
    </div>
</body>
</html>