<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
        </div>
        
        <div style="text-align: center; padding: 40px;">
            <p style="color: #456882; margin-bottom: 20px;">
                Your pages will appear here once you create them.
            </p>
            <p style="color: #234C6A;">
                Phase 2 will add page management functionality.
            </p>
        </div>
    </div>
</body>
</html>