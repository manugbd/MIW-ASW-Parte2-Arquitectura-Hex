<%@ page contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Amazin - Shopping Cart</title>

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
				<a href="${pageContext.request.contextPath}/home">Start</a>
			</li>

			<li>
				<a href="${pageContext.request.contextPath}/showBooks">Catalog</a>
			</li>

			<li>
				<a href="${pageContext.request.contextPath}/cart">View Cart</a>
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

			<label class="mytitle">Shopping Cart</label>

			<c:choose>
				<c:when test="${empty cart.items}">
					<p>Your shopping cart is empty.</p>
				</c:when>

				<c:otherwise>
					<table class="cart-table">
						<thead>
							<tr>
								<th>Book</th>
								<th>Price</th>
								<th>Quantity</th>
								<th>Subtotal</th>
								<th>Actions</th>
							</tr>
						</thead>

						<tbody>
							<c:forEach var="item" items="${cart.items}">
								<tr>
									<td>${item.title}</td>
									<td>
										<fmt:formatNumber value="${item.unitPrice}"
											minFractionDigits="2"
											maxFractionDigits="2" />
										&euro;
									</td>
									<td>${item.quantity}</td>
									<td>
										<fmt:formatNumber value="${item.subtotal}"
											minFractionDigits="2"
											maxFractionDigits="2" />
										&euro;
									</td>
									<td class="cart-actions">
										<form
											action="${pageContext.request.contextPath}/cart/items/${item.bookId}/decrease"
											method="post"
											class="inline-form">
											<button type="submit" class="cart-button">
												Remove one
											</button>
										</form>

										<form
											action="${pageContext.request.contextPath}/cart/items/${item.bookId}/remove"
											method="post"
											class="inline-form">
											<button type="submit" class="cart-button">
												Remove
											</button>
										</form>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<p class="cart-total">
						Total:
						<strong>
							<fmt:formatNumber value="${cart.totalPrice}"
								minFractionDigits="2"
								maxFractionDigits="2" />
							&euro;
						</strong>
					</p>
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
