<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<header>
    <div class="nav">
        <h1>Document Platform</h1>
        <nav>
            <c:choose>
                <c:when test="${not empty sessionScope.userId}">
                    <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
                    <a href="${pageContext.request.contextPath}/pages/search">Search</a>
                    <a href="${pageContext.request.contextPath}/users/profile">Profile</a>
                    <form action="${pageContext.request.contextPath}/users/logout" method="post" style="display: inline;">
                        <button type="submit" style="background: none; border: none; color: #ffffff; cursor: pointer; font-size: 1rem; padding: 8px 16px; margin-left: 20px;">Logout</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/users/login">Login</a>
                    <a href="${pageContext.request.contextPath}/users/register">Register</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </div>
</header>