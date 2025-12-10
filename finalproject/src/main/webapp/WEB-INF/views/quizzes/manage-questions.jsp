<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Manage Questions - ${quiz.title}</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    
    <div class="container wide-container">
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        
        <h2>Manage Questions: ${quiz.title}</h2>
        
        <c:if test="${not empty quiz.questions}">
            <h3 style="color: var(--dark-blue); margin-top: 30px;">Existing Questions</h3>
            <c:forEach items="${quiz.questions}" var="question" varStatus="status">
                <div class="page-card" style="margin-bottom: 20px;">
                    <div class="page-card-header">
                        <h3 class="page-card-title">Question ${status.index + 1}: ${question.questionType}</h3>
                    </div>
                    <div class="page-card-divider"></div>
                    <div class="page-card-preview">
                        <p><strong>${question.questionText}</strong></p>
                        <p>Correct Answer: ${question.correctAnswer}</p>
                    </div>
                </div>
            </c:forEach>
        </c:if>
        
        <h3 style="color: var(--dark-blue); margin-top: 30px;">Add New Question</h3>
        <form action="${pageContext.request.contextPath}/quizzes/${quiz.id}/questions/add" method="post">
            <div class="form-group">
                <label for="questionText">Question Text:</label>
                <textarea id="questionText" name="questionText" rows="3" required class="form-control"></textarea>
            </div>
            
            <div class="form-group">
                <label for="questionType">Question Type:</label>
                <select id="questionType" name="questionType" required class="form-control">
                    <c:forEach items="${questionTypes}" var="type">
                        <option value="${type}">${type}</option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="form-group">
                <label for="correctAnswer">Correct Answer:</label>
                <input type="text" id="correctAnswer" name="correctAnswer" required class="form-control">
            </div>
            
            <div class="form-group" id="optionsGroup">
                <label>Options (for Multiple Choice):</label>
                <input type="text" name="optionA" placeholder="Option A" class="form-control" style="margin-bottom: 10px;">
                <input type="text" name="optionB" placeholder="Option B" class="form-control" style="margin-bottom: 10px;">
                <input type="text" name="optionC" placeholder="Option C" class="form-control" style="margin-bottom: 10px;">
                <input type="text" name="optionD" placeholder="Option D" class="form-control">
            </div>
            
            <div class="form-group">
                <label for="points">Points:</label>
                <input type="number" id="points" name="points" value="1" min="1" class="form-control">
            </div>
            
            <div class="form-group">
                <button type="submit" class="btn">Add Question</button>
                <a href="${pageContext.request.contextPath}/quizzes/${quiz.id}" class="btn btn-secondary">Back to Quiz</a>
            </div>
        </form>
    </div>
</body>
</html>

