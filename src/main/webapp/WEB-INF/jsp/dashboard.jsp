<html lang="en">
<head>
<jsp:include page="includes/header.jsp" />
<script type="text/javascript"> 
window.onload = function() { 
$("#customersData").CanvasJSChart({ 
	title: { 
		text: "Customer Data",
		fontSize: 24
	}, 
	axisY: { 
		title: "Customers Data" 
	}, 
	legend :{ 
		verticalAlign: "center", 
		horizontalAlign: "right" 
	}, 
	data: [ 
	{ 
		type: "pie", 
		showInLegend: false, 
		toolTipContent: "{label} -<br/> {y}", 
		indexLabel: "{y}", 
		dataPoints: [ 
			{ label: "Total Customers",  y: ${totalcustomers}, legendText: "Total Customers"}, 
			{ label: "Repeated Customers",    y: ${repeatedcustomers.size()}, legendText: "Repeated Customers"  }
		] 
	} 
	] 
});

$("#ordersData").CanvasJSChart({ 
	title: { 
		text: "Food Order Data",
		fontSize: 24
	}, 
	axisY: { 
		title: "Food Order Data" 
	}, 
	legend :{ 
		verticalAlign: "center", 
		horizontalAlign: "right" 
	}, 
	data: [ 
	{ 
		type: "pie", 
		showInLegend: false, 
		toolTipContent: "{label} -<br/> {y}", 
		indexLabel: "{y}", 
		dataPoints: [ 
			{ label: "Total Orders",  y: ${totalorders}, legendText: "Total Orders"}
		] 
	} 
	] 
});

$("#ordersData1").CanvasJSChart({ 
	title: { 
		text: "Today vs Last week Orders",
		fontSize: 24
	}, 
	axisY: { 
		title: "Today vs Last week Orders" 
	}, 
	legend :{ 
		verticalAlign: "center", 
		horizontalAlign: "right" 
	}, 
	data: [ 
		{ 
		type: "pie", 
		showInLegend: false, 
		toolTipContent: "{label} -<br/> {y}", 
		indexLabel: "{y}", 
		dataPoints: [ 
			{ label: "Todays Orders",  y: ${todaysorders}, legendText: "Todays Orders"},
			{ label: "Last Weekday Orders[${lastWeekDate}]",  y: ${lastweekorders}, legendText: "Last Weekday Orders[${lastWeekDate}]"}
			] 
		} 
	] 
});
	
var data = eval('${repeatedcustomersStr}');
var table = $('#repeatedcustomersTable').DataTable( {
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
	    { "mData": "count"},
	    { "mData": "total"}
		 ],
		  "paging":true,
          "pageLength":100,
          "ordering":true,
          "order":[1,"desc"]
	});
}
</script>

</head>
<body>
<div class="col-md-12">
    <div class="row">
      <div class="col-md-4">
        <div id="customersData" style="height: 370px; width: 100%;"></div>
     </div>
     <div class="col-md-4">
       <div id="ordersData" style="height: 370px; width: 100%;"></div>
     </div>
           <div class="col-md-4">
       <div id="ordersData1" style="height: 370px; width: 100%;"></div>
     </div>
   </div>
    <br>
 </div>
<div>
	<h2 align="center">Repeated Customers Data</h2>
	<div align="center">
		<form action="/exportDataToExcel">
			<b>Export Data To </b><input type="image"
				src="/resources/images/ExcelIcon.jpg" width="58" height="48"
				alt="Export Data to Excel">
		</form>
	</div>
</div>

<table id="repeatedcustomersTable" class="display" style="overflow-x: auto"  width="100%">
	<thead>
		<tr>
			<th>Name</th>
			<th>Total Orders</th>
			<th>Total Orders Value</th>
		</tr>
	</thead>
</table>

</body>
</html>
