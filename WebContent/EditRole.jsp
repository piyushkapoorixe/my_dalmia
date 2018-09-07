<%@page import="com.webmodel.AdminRole"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.model.DistricModel"%>
<%@page import="com.utils.WebProjectLIstUtils"%>
<%@page import="com.webmodel.MasterState"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.constants.WebConstants"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.webmodel.MasterCity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<jsp:include page="header.jsp"></jsp:include>

<%!  AdminRole item=null;
	String RoleId = null;%>
<%
	if (request.getParameter("id") != null && request.getParameter("id").trim().length() > 0) {
		RoleId = PasswordEncryption.dycriptBase64(request.getParameter("id").getBytes());
		Connection con = null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "Select * from master_admin_role where Id='" + RoleId + "'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				item = new AdminRole();
				item.setId(rs.getString("Id"));
				item.setType(rs.getString("RoleType"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}

		}

	} else {
		item = null;
		RoleId = null;
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
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script type='text/javascript' src="js/jquery.min.js"></script>
<script type='text/javascript' src="js/jquery.blockUI.js"></script>
<script type='text/javascript'>
	$(document)
			.ready(
					function() {
						$(document).ajaxStop($.unblockUI);
						$(document)
								.ajaxStart(
										function() {
											$
													.blockUI({
														message : '<div class="load_1"><img src="images/loading.gif" /><p>Please Wait...</p></div>'
													});
										});
					});

	$(document).ready(
			function() {
				$("#master_role_save").click(
						function() {
							var Role = $('#master_role_name').val();
							var idEdit = $('#master_role_save').attr(
									'data-value');
							if (idEdit === '') {
								$.ajax({
									type : 'POST',
									url : 'AjaxEditRole',
									data : 'Role=' + Role + '&Tag=AddNew',
									success : function(data2) {
										$('#success').html(data2);
										$('#master_role_name').val('');
										alert(data2);
									},
									error : function(data) {
										alert('fail');
									}
								});
							} else {
								$.ajax({
									type : 'POST',
									url : 'AjaxEditRole',
									data : 'Role=' + Role + '&Tag=Edit' + '&Id=' + idEdit ,
									success : function(data2) {
										$('#success').html(data2);
										$('#master_role_name').val('');
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
</head>

<body>
	<div class="container">
		<div class="col-lg-12">
			<input type="submit" onclick="window.history.go(-1); return false;"
					value="Back" class="btn_inp_al" />
					<p></p>
		</div>
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>Role<span style="color: red;">*</span></td>

				<%
					if (item != null && item.getType() != null) {
				%>
				<td><input name="ctl00$ContentPlaceHolder1$txtCity" type="text"
					id="master_role_name" value="<%=item.getType()%>" /></td>
				<%
					} else {
				%>
				<td><input name="ctl00$ContentPlaceHolder1$txtCity" type="text"
					id="master_role_name" value="" /></td>

				<%
					}
				%>



			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save"
					id="master_role_save" class="btn_inp_al" 
					
					
					<%
					if(item!=null && item.getId()!=null)
					{%>
					data-value=<%=item.getId()%>
					<%}else
					{
					%>
					data-value=""
					<%	
					}
				
					%>
					
					 /> </td>
			</tr>
		</table>
	</div>



	<!-----footer--->

	<div class="admin_footer">Dalmia</div>

	<!-----end of footer--->
	</form>
	</div>
</body>

</html>