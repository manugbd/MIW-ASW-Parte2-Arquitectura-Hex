<%@ page contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

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
		<h1 class="header">Amazin.com</h1>

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
		<article>

			<table>

				<caption>Our catalog:</caption>

				<thead>
					<tr>
						<th>Title</th>
						<th>Author</th>
						<th>Description</th>
						<th>Price</th>
						<th>Cart</th>
					</tr>
				</thead>

				<tbody>

					<c:forEach var="book" items="${books}">

						<tr>
							<td>${book.title}</td>
							<td>${book.author}</td>
							<td>${book.description}</td>

							<td>
								<fmt:formatNumber
									value="${book.price}"
									minFractionDigits="2"
									maxFractionDigits="2" />
								&euro;
							</td>

							<td>
								<form
									action="${pageContext.request.contextPath}/cart/items"
									method="post"
									class="inline-form">

									<input type="hidden"
										name="bookId"
										value="${book.id}">

									<button type="submit" class="cart-button">
										Add to cart
									</button>
								</form>
							</td>
						</tr>

					</c:forEach>

				</tbody>
			</table>

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
