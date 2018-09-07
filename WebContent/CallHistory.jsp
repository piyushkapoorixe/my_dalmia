<%@page import="com.constants.WebConstants"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.webmodel.MasterCity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<jsp:include page="header.jsp"/>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
<title>Dalmia</title>
<link rel="shortcut icon" href="/image/favicon.ico" type="image/x-icon" />
<link rel="icon" href="/image/favicon.ico" type="image/ico" />
<!-- Bootstrap framework -->
<link type="text/css" href="css/admin-style.css" rel="stylesheet" />
<link rel="stylesheet" href="css/bootstrap.min.css" />
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script>
</script>
</head>
<body>
	<div class="container">
		<section>
			<div class="call_history">
				<h1 class="text-center margin-3">
					<img src="images/call_history.jpg" alt="" /> <br>Call History
				</h1>

				<div class="col-lg-6">
					<div class="history">
						<div class="col-lg-2 col-md-6 col-sm-12 col-xs-12">
							<img class="img-responsive" src="images/architect.png"
								alt="REGION" />
						</div>
						<div class="col-lg-5">
						
							<h3>ARCHITECT</h3>
						</div>
						<form action="Call_HistoryList.jsp" method="Get" >
						<input type="hidden" value="Architect" name="Id">
						<div class="col-lg-3 margin-4">
							<input type="Submit" class="btn btn-success" value="View Call Details">
						</div>
						</form>
					</div>
				</div>

				<div class="col-lg-6">
					<div class="history">
						<div class="col-lg-2 col-md-6 col-sm-12 col-xs-12">
							<img class="img-responsive" src="images/contractors.png"
								alt="REGION" />
						</div>
						<div class="col-lg-5">
							<h3>CONTRATOR</h3>
						</div>
					<form action="Call_HistoryList.jsp" method="Get" >
						<input type="hidden" value="Contractor" name="Id">
						<div class="col-lg-3 margin-4">
							<input type="Submit" class="btn btn-success" value="View Call Details">
						</div>
						</form>
					</div>
				</div>

				<div class="col-lg-6">
					<div class="history">
						<div class="col-lg-2 col-md-6 col-sm-12 col-xs-12">
							<img class="img-responsive" src="images/civil_engineers.png"
								alt="REGION" />
						</div>
						<div class="col-lg-5">
							<h3>ENGINEER</h3>
						</div>
						<form action="Call_HistoryList.jsp" method="GET" >
						<input type="hidden" value="Engineer" name="Id">
						<div class="col-lg-3 margin-4">
							<input type="Submit" class="btn btn-success" value="View Call Details">
						</div>
						</form>
					</div>
				</div>
				<div class="col-lg-6">
					<div class="history">
						<div class="col-lg-2 col-md-6 col-sm-12 col-xs-12">
							<img class="img-responsive" src="images/consumers.png"
								alt="REGION" />
						</div>
						<div class="col-lg-5">
							<h3>CONSUMER</h3>
						</div>
						<form action="Call_HistoryList.jsp" method="Post" >
						<input type="hidden" value="5" name="Id">
						<div class="col-lg-3 margin-4">
							<input type="Submit" class="btn btn-success" value="View Call Details">
						</div>
						</form>
					</div>

				</div>
			</div>
		</section>
	</div>

	<!-----footer--->

	<div class="admin_footer">Dalmia</div>

	<!-----end of footer--->
	</form>
	</div>
</body>

</html>