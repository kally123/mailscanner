<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page errorPage = "ShowError.jsp" %>
<html lang="en">
<jsp:include page="includes/header.jsp" />
<head>
<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
<c:url value="/css/main.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />
<script type="text/javascript">
$(document).ready(function(){
	var data =eval('${customers}');
	var table = $('#customerTable').DataTable( {
		  "aaData": data,
		  "aoColumns": [
			{ 
	         "data": "name",
	         "render": function(data, type, row, meta){
	            if(type === 'display'){
	                data = '<a href="/findByName?name=' + data + '" target="popup">' + data + '</a>';
	            }
	            return data;
	            }
			 },
	         { 
	         "data": "mobNumber",
	         "render": function(data, type, row, meta){
	            if(type === 'display'){
	                data = '<a href="/findByMobNumber?mobNumber=' + data + '" target="popup">' + data + '</a>';
	            }
	            return data;
		        }
			},
		    { "mData": "address"},
		    { "mData": "orderDetails.length"},
		    { "mData": "concatenatedOrderIds"}
		  ],
		  "paging":true,
          "pageLength":100,
          "ordering":true,
          "order":[3,"desc"]
		});
});
</script>

</head>
<body>

<div>
<h2 align="center">Total No of Customers : ${totalCustomers}</h2> <form action="/exportDataToExcel"><input type="submit" value="Export Data to Excel"></form>
</div>


	<table id="customerTable" class="display" style="overflow-x: auto"  width="100%">
		<thead>
			<tr>
				<th>Name</th>
				<th>Mobile Number</th>
				<th>Address</th>
				<th>No of Orders</th>
				<th>Order Ids</th>
			</tr>
		</thead>
	</table>
</body>
<jsp:include page="includes/footer.jsp" />
</html>

