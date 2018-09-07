<%@page import="java.sql.SQLException"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.model.UserDetailModel"%>
<%@page import="com.constants.WebConstants"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.model.CallHistoryModel"%>
<%@page import="com.webmodel.MasterCity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<jsp:include page="header.jsp" />

<%!ArrayList<UserDetailModel> requestList = null;
	public void getUserData() {
		Connection con = null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "SELECT user_Name, email,mob_no,FirmName,Address ,service_request.Call_time from users_details INNER JOIN service_request on users_details.user_Id=service_request.Uid Order By service_request.Call_time Desc";
			ResultSet rs = stmt.executeQuery(sql);
			requestList = new ArrayList<UserDetailModel>();
			while (rs.next()) {
				UserDetailModel item = new UserDetailModel();
				item.setUserName(rs.getString("user_Name"));
				item.setEmailId(rs.getString("email"));
				item.setMobileNo(rs.getString("mob_no"));
				item.setFirmName(rs.getString("FirmName"));
				item.setAddress(rs.getString("Address"));
				item.setCallTime(rs.getString("Call_time"));
				requestList.add(item);
			}
			//System.out.print("%%%%%%%%%%%%%%%%"+new Gson().toJson(requestList));
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}

		}
	}%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
<title>Dalmia</title>
<link rel="shortcut icon" href="/image/favicon.ico" type="image/x-icon" />
<link rel="icon" href="/image/favicon.ico" type="image/ico" />
<!-- Bootstrap framework -->
<link type="text/css" href="css/admin-style.css" rel="stylesheet" />
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/r/bs-3.3.5/jq-2.1.4,dt-1.10.8/datatables.min.css" />
<script type="text/javascript"
	src="https://cdn.datatables.net/r/bs-3.3.5/jqc-1.11.3,dt-1.10.8/datatables.min.js"></script>
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
	$('#example').DataTable();
} );
</script>
</head>
<body>


	<!-----middle--->

	<div class="container">
		<table class="col-lg-12">
			<tr>
				<td style="width: 130px;" align="right">
					<form action="ExcelDownload" method="post">
						<button type="submit" value="Service_Request" name="Type"
							class="btn btn-info btn-lg">Download Report</button>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<h3>Request List</h3>
				</td>
			</tr>
		</table>
		<p></p>
		<div class="details">
			<table id="example" class="display table table-bordered">
				<thead>
					<tr>
						<th>S.N.</th>
						<th>Firm Name</th>
						<th>Name</th>
						<th>Email</th>
						<th>Mobile</th>
						<th>Address</th>
						<th>Date of Call</th>
					</tr>
				</thead>
				<%
					getUserData();
					for (int i = 0; i < requestList.size(); i++) {
				%>
				<tr>
					<td><%=i + 1%></td>
					<td><%=requestList.get(i).getFirmName()%></td>
					<td><%=requestList.get(i).getUserName()%></td>
					<td><%=requestList.get(i).getEmailId()%></td>
					<td><%=requestList.get(i).getMobileNo()%></td>
					<td><%=requestList.get(i).getAddress()%></td>
					<td><%=requestList.get(i).getCallTime()%></td>
				</tr>
				<%
					}
				%>
			</table>
		</div>
	</div>

	<!-----end of middle--->

	<!-----footer--->

	<div class="admin_footer">Dalmia</div>

	<!-----end of footer--->
</body>

</html>