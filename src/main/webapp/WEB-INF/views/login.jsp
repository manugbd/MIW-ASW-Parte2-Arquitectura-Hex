<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

		<!DOCTYPE html>
		<html lang="en">

		<head>
			<meta charset="UTF-8">
			<title>Amazin - Login</title>
			<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
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
					<li><a href="http://miw.uniovi.es">About</a></li>
					<li><a href="mailto:dd@email.com">Contact</a></li>
				</ul>
			</nav>

			<section>
				<article id="a01">

					<label class="mytitle">Login</label>

					<form class="login-form" action="${pageContext.request.contextPath}/login" method="post">

						<label for="username">Username:</label> <input id="username" name="username" type="text"> <label
							for="password">Password:</label>
						<input id="password" name="password" type="password">

						<button type="submit">Login</button>

					</form>

					<c:if test="${error}">
						<div class="login-error">Incorrect username or password.</div>
					</c:if>

				</article>
			</section>

			<footer>
				<strong>Master in Web Engineering (miw.uniovi.es).</strong><br> <em>University
					of Oviedo</em>
			</footer>

		</body>

		</html>