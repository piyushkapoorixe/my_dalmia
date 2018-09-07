<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.model.FAQModel"%>
<%@page import="com.constants.WebConstants"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.webmodel.MasterCity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<jsp:include page="header.jsp" />
<%!String QueId = null;
	String Que = null;
	String Ans = null;
	Connection con = null;%>
<%
	if (request.getParameter("id") != null && request.getParameter("id").trim().length() > 0 ) {
		QueId = PasswordEncryption.dycriptBase64(request.getParameter("id").getBytes());
		try {
			con = DBConnectionProvider.getCon();
			PreparedStatement ps = con
					.prepareStatement("SELECT Question,Answer FROM master_faq where Id=" + QueId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Que = rs.getString("Question");
				Ans = rs.getString("Answer");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}

		}
	} else {
		Que = null;
		Ans = null;
		QueId=null;
	}
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
<title>Dalmia</title>
<link rel="shortcut icon" href="/image/favicon.ico" type="image/x-icon" />
<link rel="icon" href="/image/favicon.ico" type="image/ico" />
<!-- Bootstrap framework -->
<link type="text/css" href="css/admin-style.css" rel="stylesheet" />
<link rel="stylesheet" href="css/bootstrap.min.css" />

</head>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
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
				$("#add_new_faq").click(
						function() {
							var idEdit = $('#add_new_faq').attr('data-value');
							var ans = $("#ans").val();
							var ques = $("#que").val();
							 if (idEdit === '') {
								$.ajax({
									type : 'POST',
									url : 'AjaxFaqsController',
									data : '&Que=' + ques + '&Tag=AddNew'
											+ '&ans=' + ans,
									success : function(data2) {
										$('#success').html(data2);
										$("#que").val('');
										$("#ans").val('');
										alert(data2);
									},
									error : function(data) {
										alert('fail');
									}
								});
							} else {
								$.ajax({
									type : 'POST',
									url : 'AjaxFaqsController',
									data : '&Que=' + ques + '&Tag=Edit'
											+ '&idEdit=' + idEdit + '&ans='
											+ ans,
									success : function(data2) {
										$('#success').html(data2);
										$("#que").val('');
										$("#ans").val('');
										alert(data2);
									},
									error : function(data) {
										alert('fail');
									}
								});
							} 
						});
			});
</script>
<body>
	<div class="container">
		<table>
			<tr>
				<td><input type="submit"
					onclick="window.history.go(-1); return false;" value="Back"
					class="btn_inp_al" />

					<p></p></td>
			</tr>
		</table>
		<div id="loginbox"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 admin_d col-sm-offset-2">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">
						<h3>FAQ's</h3>
					</div>
				</div>
				<div style="padding: 10px 30px 0px 30px" class="panel-body">
					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>
					<div class="form-group">
						<label for="inputEmail">Question</label>
						<%
							if (Que != null && Que.trim().length() > 0) {
						%>
						<textarea class="form-control" rows="3" id="que"><%=Que%> </textarea>
						<%
							} else {
						%>
						<textarea class="form-control" rows="3" id="que"> </textarea>
						<%
							}
						%>

					</div>
					<div class="form-group">
						<label for="inputPassword">Answer</label>
						<%
							if (Ans != null && Ans.trim().length() > 0) {
						%>
						<textarea class="form-control" rows="4" id="ans"><%=Ans%></textarea>
						<%
							} else {
						%>
						<textarea class="form-control" rows="4" id="ans"></textarea>
						<%
							}
						%>
					</div>

					<div class="form-group">
						<div class="controls" >
							<a id="add_new_faq" class="btn btn-xm btn-primary"
								<%if (QueId != null) {%> data-value=<%=QueId%> <%} else {%>
								data-value="" <%}%>>Submit</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-----footer--->
	<div class="admin_footer">Dalmia</div>
	<!-----end of footer--->
</body>

</html>