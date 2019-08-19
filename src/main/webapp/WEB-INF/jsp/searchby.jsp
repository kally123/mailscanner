<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
<c:url value="/css/main.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />
<jsp:include page="includes/header.jsp" />
</head>
<body>

	<div class="container">
		<H2 align="center">Search Api for Zomato Orders</H2>
		<div class="starter-template">
			<form action="/findByName" method="GET">
				Search By Name: <input type="text" name="name"> <input
					type="submit" value="Search">
			</form>
		</div>
		<div class="starter-template">
			<form action="/findByMobNumber" method="GET">
				Search By Mobile Number: <input type="text" name="mobNumber">
				<input type="submit" value="Search">
			</form>
		</div>
		<div class="starter-template">
			<form action="/findCustomerWithOrderId" method="GET">
				Search By Order Id: <input type="text" name="orderId"> <input
					type="submit" value="Search">
			</form>
		</div><br>
		<div class="starter-template">
			<form action="findCustomersMultiOrders" method="POST">
				<input type="submit" value="Fetch Customers With more Orders">
			</form>
		</div>


	</div>

	<script type="text/javascript"
		src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
<jsp:include page="includes/footer.jsp" />
</html>
