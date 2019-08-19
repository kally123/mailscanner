<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false" %>
<html>

<!-- Global site tag (gtag.js) - Google Analytics -->
	<script async src="https://www.googletagmanager.com/gtag/js?id=UA-112439168-2"></script>
	<script>
	 window.dataLayer = window.dataLayer || [];
	 function gtag(){dataLayer.push(arguments);}
	 gtag('js', new Date());
	
	 gtag('config', 'UA-112439168-2');
	</script>

	<spring:url value="/resources/js/jquery-1.12.1.min.js"
	var="jqueryJs" />
		<script src="${jqueryJs}"></script>
	<spring:url value="/resources/js/jquery.dataTables.js"
	var="datatable" />
		<script src="${datatable}"></script>
	<spring:url value="/resources/css/jquery.dataTables.css" var="jquerydataTables" />
		<link href="${jquerydataTables}" rel="stylesheet" />
	<spring:url value="/resources/css/jquery.dataTables.min.css" var="jquerydataTablesMin" />
		<link href="${jquerydataTablesMin}" rel="stylesheet" />
	<spring:url value="/resources/css/common.css" var="common" />
		<link href="${common}" rel="stylesheet" />
		
		<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Spring Boot</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#about">About</a></li>
					<li><a href="/connectAccount">Account Status</a></li>
					<li><a href="/customers">Customers</a></li>
					<li><a href="/searchby">Search By</a></li>
					<li><a href="/mailscanner">Scan Mail</a></li>
				</ul>
			</div>
		</div>
	</nav>
