<%@page import="com.webmodel.AdminListModel"%>
<%@page import="com.google.gson.Gson"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
AdminListModel model = null;
	if (session.getAttribute("obj") != null) {
		model = (AdminListModel) session.getAttribute("obj");
	} else {%>
		<jsp:forward page="index.jsp" >
		<jsp:param name="name" value="" /> 
		 </jsp:forward> 
		
	<% }
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">

<link rel="shortcut icon" href="/image/favicon.ico" type="image/x-icon" />
<link rel="icon" href="/image/favicon.ico" type="image/ico" />
<!-- Bootstrap framework -->
<link type="text/css" href="css/admin-style.css" rel="stylesheet" />
<link rel="stylesheet" href="css/bootstrap.min.css" />
</head>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript">
	function gotoHome() {
		 window.location.href = "HomePage.jsp";
	}
</script>
<body>
	<div id="maincontainer" class="clearfix">
		<header>
		<div class="container">
			<div class="admin_header">
				<div class="admin_logo">
				<img src="images/logo2.png" width="200" height="auto"/>
				</div>
				<div class="admin_version">
					<div class="admin_left welcome_admin">
					<div class="col-lg-5"><h1>Welcome to</h1>
					<span id="lblusername">
					<img src="images/user.png" width="30" height="auto"/> <%=model.getEmailId()%></span></div>
					<div class="col-lg-3"><h2>Last Login </h2>
					<span><img src="images/time.png" width="30" height="auto"/><%=model.getLastLoginTime()%></span></div>
					<div class="col-lg-3"><h2>Role(<%=model.getRole()%>) </h2> <span><%=model.getName()%></span></div>
					<div class="col-lg-1" style="margin-top:10px;">
	<form action="logout.jsp" method="post">
	 <input type="submit" value="Logout" class="btn btn-danger pull-right">
	 </form>
					<%  if(model!=null){%></div>
						
					</div><%} %>
				</div>
			</div></div>
			<div class="clearfix"></div>
			<div class="admin_menu">
			<div class="container">
				<ul>

					<li class="dropdown" onclick="gotoHome()"><a href="#">Back to DashBoard</a></li>
				</ul>

			</div></div>


		</header>
	</div>
	