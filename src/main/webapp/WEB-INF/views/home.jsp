<%@ page contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Amazin</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

	<header>
		<h1 class="header">El Amazin de Manu</h1>

		<h2 class="centered">
			Welcome to the <em>smallest</em> online shop in the world!!
		</h2>
	</header>

	<nav>
		<ul>
			<li>
				<a href="${pageContext.request.contextPath}/home">
					Start
				</a>
			</li>

			<li>
				<a href="${pageContext.request.contextPath}/cart">
					View Cart
				</a>
			</li>

			<li>
				<a href="http://miw.uniovi.es">About</a>
			</li>

			<li>
				<a href="mailto:dd@email.com">Contact</a>
			</li>

			<li>
				<form action="${pageContext.request.contextPath}/logout"
					method="post"
					class="logout-form">

					<button type="submit" class="logout-link">
						Logout
					</button>

				</form>
			</li>
		</ul>
	</nav>

	<section>
		<article id="a01">

			<label class="mytitle">
				Choose an option:
			</label>

			<br>

			<a href="${pageContext.request.contextPath}/showBooks">
				Show Catalog
			</a>

			<br>

			<a href="${pageContext.request.contextPath}/showSpecialOffer">
				Show Special Offers!
			</a>

			<c:if test="${sessionScope.authenticatedUser.role eq 'ADMIN'}">
				<br>
				<a href="${pageContext.request.contextPath}/adminInfo">Administration</a>
			</c:if>

		</article>

		<article class="recently-viewed" aria-labelledby="recently-viewed-title">
			<h2 id="recently-viewed-title">Recently viewed books</h2>
			<c:choose>
				<c:when test="${empty viewedBooks}">
					<p>You have not viewed any books yet. Open a book from the catalog to get started.</p>
				</c:when>
				<c:otherwise>
					<table>
						<caption>Most recent first</caption>
						<thead>
						<tr>
							<th>Title</th>
							<th>Author</th>
							<th>Price</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach var="book" items="${viewedBooks}">
							<tr>
								<td>
									<a class="book-details-link"
									   href="${pageContext.request.contextPath}/books/${book.id}">
										<c:out value="${book.title}"/>
									</a>
								</td>
								<td><c:out value="${book.author}"/></td>
								<td>
									<fmt:formatNumber value="${book.price}" minFractionDigits="2"
													  maxFractionDigits="2"/> &euro;
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</article>
	</section>

	<footer>
		<strong>
			Master in Web Engineering (miw.uniovi.es).
		</strong>
		<br>
		<em>University of Oviedo</em>
	</footer>

</body>
</html>