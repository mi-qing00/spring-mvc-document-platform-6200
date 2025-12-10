<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp" %>
    <title>Study Flashcards - Document Platform</title>
    <style>
        .flashcard-container {
            max-width: 600px;
            margin: 50px auto;
            perspective: 1000px;
        }
        .flashcard {
            width: 100%;
            height: 300px;
            position: relative;
            transform-style: preserve-3d;
            transition: transform 0.6s;
        }
        .flashcard.flipped {
            transform: rotateY(180deg);
        }
        .flashcard-front, .flashcard-back {
            position: absolute;
            width: 100%;
            height: 100%;
            backface-visibility: hidden;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 30px;
            background: var(--white);
            color: var(--dark-blue);
            border: 2px solid var(--light-blue);
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }
        .flashcard-back {
            transform: rotateY(180deg);
        }
        .difficulty-buttons {
            margin-top: 30px;
            display: flex;
            gap: 10px;
            justify-content: center;
        }
        .difficulty-buttons button {
            padding: 10px 20px;
        }
    </style>
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
        
        <c:if test="${not empty message}">
            <div class="empty-state">
                <p>${message}</p>
                <a href="${pageContext.request.contextPath}/flashcards" class="btn">Back to Flashcards</a>
            </div>
        </c:if>
        
        <c:if test="${not empty cards and cards.size() > 0}">
            <h2 style="text-align: center; color: var(--dark-blue);">Study Mode</h2>
            <p style="text-align: center; color: var(--light-blue);">
                Card ${currentIndex + 1} of ${cards.size()}
            </p>
            
            <div class="flashcard-container">
                <c:forEach items="${cards}" var="card" varStatus="status">
                    <div id="card-${status.index}" class="flashcard" style="${status.index == 0 ? '' : 'display: none;'}">
                        <div class="flashcard-front">
                            <div style="text-align: center;">
                                <h3>${card.front}</h3>
                                <button onclick="flipCard(${status.index})" class="btn" style="margin-top: 20px;">
                                    Reveal Answer
                                </button>
                            </div>
                        </div>
                        <div class="flashcard-back">
                            <div style="text-align: center;">
                                <h3>${card.back}</h3>
                                <form action="${pageContext.request.contextPath}/flashcards/${card.id}/review" method="post" 
                                      style="margin-top: 20px;" id="review-form-${status.index}">
                                    <div class="difficulty-buttons">
                                        <button type="submit" name="difficulty" value="HARD" class="btn btn-secondary">Hard</button>
                                        <button type="submit" name="difficulty" value="MEDIUM" class="btn">Medium</button>
                                        <button type="submit" name="difficulty" value="EASY" class="btn">Easy</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
    
    <script>
        function flipCard(index) {
            const card = document.getElementById('card-' + index);
            card.classList.add('flipped');
        }
    </script>
</body>
</html>

