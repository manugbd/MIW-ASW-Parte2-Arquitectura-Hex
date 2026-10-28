<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Amazin - Add a book</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <header>
        <h1 class="header">El Amazin de Manu</h1>
        <h2 class="centered">Welcome to the <em>smallest</em> online shop in the world!!</h2>
    </header>

    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/home">Start</a></li>
            <li><a href="${pageContext.request.contextPath}/showBooks">Show Catalog</a></li>
            <li><a href="${pageContext.request.contextPath}/adminInfo">Administration</a></li>
            <li>
                <form action="${pageContext.request.contextPath}/logout" method="post" class="logout-form">
                    <button type="submit" class="logout-link">Logout</button>
                </form>
            </li>
        </ul>
    </nav>

    <section class="book-admin-page">
        <article class="book-admin-panel">
            <h2>Add a new book</h2>
            <div class="book-admin-intro">Enter the book details and select its VAT group.</div>

            <c:if test="${not empty createdBook}">
                <div class="book-admin-success" role="status">
                    Book added successfully:
                    <a href="${pageContext.request.contextPath}/books/${createdBook.id}">
                        <c:out value="${createdBook.title}" />
                    </a>
                </div>
            </c:if>

            <c:if test="${empty vats}">
                <div class="review-errors" role="alert">
                    No VAT groups are available. A VAT group must exist before a book can be added.
                </div>
            </c:if>

            <form:form modelAttribute="bookForm" method="post"
                       action="${pageContext.request.contextPath}/admin/books" cssClass="book-admin-form">
                <spring:hasBindErrors name="bookForm">
                    <div class="review-errors" role="alert">
                        <c:if test="${errors.hasFieldErrors('title')}">
                            <div>Title must contain between 1 and 255 characters.</div>
                        </c:if>
                        <c:if test="${errors.hasFieldErrors('author')}">
                            <div>Author must contain between 1 and 255 characters.</div>
                        </c:if>
                        <c:if test="${errors.hasFieldErrors('description')}">
                            <div>Description must contain between 1 and 255 characters.</div>
                        </c:if>
                        <c:if test="${errors.hasFieldErrors('basePrice')}">
                            <div>Enter a finite, non-negative base price.</div>
                        </c:if>
                        <c:if test="${errors.hasFieldErrors('vatId')}">
                            <div>Select an existing VAT group.</div>
                        </c:if>
                        <c:forEach var="error" items="${errors.globalErrors}">
                            <div><c:out value="${error.defaultMessage}" /></div>
                        </c:forEach>
                    </div>
                </spring:hasBindErrors>

                <label for="book-title">Title</label>
                <form:input path="title" id="book-title" maxlength="255" required="required" htmlEscape="true" />

                <label for="book-author">Author</label>
                <form:input path="author" id="book-author" maxlength="255" required="required" htmlEscape="true" />

                <label for="book-description">Description</label>
                <form:textarea path="description" id="book-description" rows="4" maxlength="255"
                               required="required" htmlEscape="true" />

                <label for="book-price">Base price (&euro;, excluding VAT)</label>
                <form:input path="basePrice" id="book-price" type="number" min="0" step="0.01"
                            required="required" htmlEscape="true" />

                <label for="book-vat">VAT group</label>
                <form:select path="vatId" id="book-vat" required="required">
                    <form:option value="" label="Select a VAT group" />
                    <c:forEach var="vat" items="${vats}">
                        <form:option value="${vat.id}">
                            Group <c:out value="${vat.taxGroup}" /> —
                            <fmt:formatNumber value="${vat.value * 100}" maxFractionDigits="2" />%
                        </form:option>
                    </c:forEach>
                </form:select>

                <c:choose>
                    <c:when test="${empty vats}">
                        <button type="submit" class="cart-button" disabled>Add book</button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" class="cart-button">Add book</button>
                    </c:otherwise>
                </c:choose>
            </form:form>
        </article>
    </section>

    <footer>
        <strong>Master in Web Engineering (miw.uniovi.es).</strong><br>
        <em>University of Oviedo</em>
    </footer>
</body>
</html>
