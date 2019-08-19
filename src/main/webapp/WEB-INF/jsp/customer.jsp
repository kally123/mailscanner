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

		<table border = "0" >
			<tr><td><h1 align="center">Customer Details Page</h1></td></tr>
			<tr><td><h2>Customer Name: </h2></td><td>${customer.name}</td></tr>
			<tr><td><h2>Customer Mobile Number: </h2></td><td>${customer.mobNumber}</td></tr>
			<tr><td><h2>Customer Address: </h2></td><td>${customer.address}</td></tr>
			<tr><td><h2>Customer Order Details :</h2> </td><td>${customer.orderDetails.size()} Orders delivered to this Customer.</td></tr>
			<c:forEach items="${customer.orderDetails}" var="orderDetail">
				<tr><td colspan="2">==============================================================</td></tr>
    			<tr><td colspan="2">Customer Order Id :${orderDetail.orderId}</td></tr>
    			<tr><td colspan="2">Customer Order Date :${orderDetail.orderDate}</td></tr>
    			<tr><td colspan="2">Customer Order Details :${orderDetail.orderSummary}</td></tr>
    			<tr><td colspan="2">Customer Order Bill :${orderDetail.bill}</td></tr>
				<c:if test="${not empty orderDetail.restaurantPromo}">
    			<tr><td colspan="2">Restaurant Promo :${orderDetail.restaurantPromo}</td></tr>
				</c:if>
				<c:if test="${not empty orderDetail.zomatoPromo}">
    			<tr><td colspan="2">Zomato Promo :${orderDetail.zomatoPromo}</td></tr>
				</c:if>
    			<tr><td colspan="2">Packaging Charges :${orderDetail.packagingCharge}</td></tr>
    			<tr><td colspan="2">Payment Mode :${orderDetail.paymentMode}</td></tr>
			</c:forEach>
		</table>

	</div>

	<script type="text/javascript"
		src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
<jsp:include page="includes/footer.jsp" />
</html>
