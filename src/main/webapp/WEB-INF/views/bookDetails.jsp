<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Amazin - <c:out value="${book.title}" /></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <header>
        <h1 class="header">Amazin.com</h1>
        <h2 class="centered">Welcome to the <em>smallest</em> online shop in the world!!</h2>
    </header>

    <nav aria-label="Main navigation">
        <ul>
            <li><a href="${pageContext.request.contextPath}/home">Start</a></li>
            <li><a href="${pageContext.request.contextPath}/showBooks">Catalog</a></li>
            <li><a href="${pageContext.request.contextPath}/showSpecialOffer">Special offer</a></li>
            <li><a href="${pageContext.request.contextPath}/cart">View Cart</a></li>
            <li>
                <form action="${pageContext.request.contextPath}/logout" method="post" class="logout-form">
                    <button type="submit" class="logout-link">Logout</button>
                </form>
            </li>
        </ul>
    </nav>

    <section class="book-page" aria-label="Book details and reviews">
        <article class="book-details" aria-labelledby="book-title">
            <a href="${pageContext.request.contextPath}/showBooks">Back to catalog</a>
            <h2 id="book-title"><c:out value="${book.title}" /></h2>
            <p class="book-author">By <strong><c:out value="${book.author}" /></strong></p>
            <p class="book-description"><c:out value="${book.description}" /></p>
            <p class="book-price">
                Price (VAT included):
                <strong><fmt:formatNumber value="${book.price}" minFractionDigits="2" maxFractionDigits="2" /> &euro;</strong>
            </p>
        </article>

        <article class="book-reviews" aria-labelledby="reviews-title">
            <h2 id="reviews-title">Reader reviews (<c:out value="${bookReviews.size()}" />)</h2>
            <c:choose>
                <c:when test="${empty bookReviews}">
                    <p>No reviews yet. Be the first to review this book.</p>
                </c:when>
                <c:otherwise>
                    <ol class="review-list">
                        <c:forEach var="review" items="${bookReviews}">
                            <li class="review-card">
                                <div class="review-heading">
                                    <strong>User #<c:out value="${review.userId}" /></strong>
                                    <c:if test="${review.userId eq sessionScope.authenticatedUser.id}">
                                        <span class="own-review">Your review</span>
                                    </c:if>
                                    <span class="review-score"><c:out value="${review.score}" /> / 10</span>
                                </div>
                                <p class="review-text"><c:out value="${review.text}" /></p>
                            </li>
                        </c:forEach>
                    </ol>
                </c:otherwise>
            </c:choose>
        </article>

        <article class="review-section" aria-labelledby="add-review-title">
            <h2 id="add-review-title">Write a review</h2>
            <p>One review per user and book. Scores range from 0 to 10.</p>
            <c:url var="reviewAction" value="/books/${book.id}/reviews" />
            <form:form modelAttribute="reviewForm" action="${reviewAction}" method="post" cssClass="review-form">
                <spring:hasBindErrors name="reviewForm">
                    <div class="review-errors" role="alert">
                        <c:if test="${errors.hasFieldErrors('text')}">
                            <div>Write a review between 1 and 10,000 characters.</div>
                        </c:if>
                        <c:if test="${errors.hasFieldErrors('score')}">
                            <div>Score must be an integer between 0 and 10.</div>
                        </c:if>
                        <c:forEach var="error" items="${errors.globalErrors}">
                            <div><c:out value="${error.defaultMessage}" /></div>
                        </c:forEach>
                    </div>
                </spring:hasBindErrors>

                <label for="review-text">Your review</label>
                <form:textarea id="review-text" path="text" rows="5" maxlength="10000"
                               required="required" aria-describedby="review-text-help" htmlEscape="true" />
                <small id="review-text-help">Maximum 10,000 characters.</small>

                <label for="review-score">Score (0 to 10)</label>
                <form:input id="review-score" path="score" type="number" min="0" max="10" step="1"
                            required="required" htmlEscape="true" />

                <button type="submit" class="cart-button">Publish review</button>
            </form:form>
        </article>
    </section>

    <footer>
        <strong>Master in Web Engineering (miw.uniovi.es).</strong><br>
        <em>University of Oviedo</em>
    </footer>
</body>
</html>
