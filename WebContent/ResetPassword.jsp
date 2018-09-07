<%@page import="com.constants.WebConstants"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.webmodel.MasterRegionModel"%>
<%@page import="com.webmodel.MasterCity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<jsp:include page="header.jsp" />

<%!String UserId = null;%>
<%
	UserId = request.getParameter("Uid");
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
<title>Dalmia</title>
<link rel="shortcut icon" href="/image/favicon.ico" type="image/x-icon" />
<link rel="icon" href="/image/favicon.ico" type="image/ico" />
<!-- Bootstrap framework -->
<link type="text/css" href="css/admin-style.css" rel="stylesheet" />
<link type="text/css" href="css/login.css" rel="stylesheet" />
<link rel="stylesheet" href="css/bootstrap.min.css" />
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script>
	
</script>
</head>
<script type='text/javascript' src="js/jquery.min.js"></script>
<script type='text/javascript' src="js/jquery.blockUI.js"></script>
<script type='text/javascript'>
$(document).ready(
		function() {
			$(document).ajaxStop($.unblockUI);
            $(document).ajaxStart(function () { $.blockUI({ message: '<div class="load_1"><img src="images/loading.gif" /><p>Please Wait...</p></div>' }); });
		});
	$(document).ready(
			function() {
				$("#reset_now").click(
						function() {
							var Password = $('#pass').val();
							var ConfirmPassword = $('#c_pass').val();
							$.ajax({
								type : 'POST',
								url : 'AjaxResetPassword',
								data : '&Password=' + Password + 
								       '&Confirm='  + ConfirmPassword + 
								       '&Id=' + "<%=UserId%>" ,
								success : function(data2) {
									$('#success').html(data2);
									alert(data2);
									$('#pass').val('');
									$('#c_pass').val('');
								},
								error : function(data) {
									alert('fail');
								}
							});
						});
			});
</script>

<body>
	<div class="container">
		<div class="col-lg-12">
			<input type="submit" onclick="window.history.go(-1); return false;"
					value="Back" class="btn_inp_al" />
		</div>
		<div id="loginbox"
			class="mainbox col-md-4 col-md-offset-4 col-sm-8 admin_d col-sm-offset-2">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">
						<h3>Reset Password</h3>
					</div>
				</div>

				<div style="padding: 10px 30px 0px 30px" class="panel-body">
					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>
					<div class="form-group">
						<label>Enter New Password</label> <input type="password" id="pass"
							class="login-inpt" placeholder="Enter New Password">
					</div>

					<div class="form-group">
						<label>Confirm Password</label> <input type="password" id="c_pass"
							class="login-inpt" placeholder="Re-enter New Password">
					</div>
					<div class="form-group">
						<div class="controls">
							<a id="reset_now" class="btn btn-xm btn-primary">Reset
								Password</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-----footer--->

	<div class="admin_footer">Dalmia</div>

	<!-----end of footer--->
	</form>
	</div>
</body>

</html>