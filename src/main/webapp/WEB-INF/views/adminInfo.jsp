<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Amazin - Administration</title>

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

			<li><a href="${pageContext.request.contextPath}/home"> Start
			</a></li>

			<li><a href="${pageContext.request.contextPath}/cart"> View Cart </a></li>

			<li><a href="http://miw.uniovi.es"> About </a></li>

			<li><a href="mailto:dd@email.com"> Contact </a></li>

			<li>
				<form action="${pageContext.request.contextPath}/logout"
					method="post" class="logout-form">

					<button type="submit" class="logout-link">Logout</button>

				</form>
			</li>

		</ul>
	</nav>

	<section>

		<article id="a01">

			<label class="mytitle"> Administration </label>

			<p>This page is only available to administrators.</p>

		</article>

	</section>

	<footer>
		<strong> Master in Web Engineering (miw.uniovi.es). </strong> <br>
		<em>University of Oviedo</em>
	</footer>

</body>

</html>