<%@page import="java.sql.SQLException"%>
<%@page import="com.model.CallHistoryModel"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.webmodel.MasterCity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<jsp:include page="header.jsp"></jsp:include>
<%
	if (request.getParameter("Id").equals("Engineer")) {
		ProfesionId = "1";
	} else if (request.getParameter("Id").equals("Contractor")) {
		ProfesionId = "3";
	} else if (request.getParameter("Id").equals("Architect")) {
		ProfesionId = "2";
	} else if (request.getParameter("Id").equals("5")) {
		ProfesionId = "5";
	}
%>
<%!String ProfesionId = null;
	ArrayList<CallHistoryModel> listPerson;
	Connection con;

	public void getUserData() {
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "SELECT fud.user_Id, fud.user_Name as Caller, fud.Address as CallerAddress, toud.user_Name as Called, history.CallTime from user_call_history as history INNER JOIN users_details as fud on history.FromCall=fud.user_Id INNER JOIN users_details as toud on history.ToCall=toud.user_Id where fud.profession_Id='"
					+ ProfesionId + "' Order By history.CallTime Desc";
			ResultSet rs = stmt.executeQuery(sql);
			listPerson = new ArrayList<CallHistoryModel>();
			while (rs.next()) {
				CallHistoryModel item = new CallHistoryModel();
				item.setCalled(rs.getString("Called"));
				item.setCaller(rs.getString("Caller"));
				item.setCalledTime(rs.getString("CallTime"));
				item.setCallerAddress(rs.getString("CallerAddress"));
				item.setId(rs.getString("user_Id"));
				listPerson.add(item);
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
	<div class="container">
		<table width=100%>
			<tr>
				<td><input type="submit"
					onclick="window.history.go(-1); return false;" value="Back"
					class="btn_inp_al" /></td>
				<td style="width: 130px;" align="right">
					<form action="ExcelDownload" method="post">
						<input type="hidden" value="<%=ProfesionId%>" name="val"></input>
						<button type="submit" value="Call_Hostory" name="Type"
							class="btn btn-info btn-lg">Download Report</button>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<%
						if (ProfesionId.equals("1")) {
					%>
						<H3>Engineers</H3> <%
 					} else if (ProfesionId.equals("2")) {
 					%>
						<H3>Architect</H3> <%
 					} else if (ProfesionId.equals("3")) {
 					%>
						<H3>Contractor</H3> <%
 					} else if (ProfesionId.equals("5")) {
 					%>
						<H3>Consumer</H3> <%
 					}
 					%>
				</td>
			</tr>
		</table>
		<p></p>
		<div class="details">
			<table id="example" class="display table table-bordered">
				<thead>
					<tr>
						<th>S.N.</th>
						<th>Caller Name</th>
						<th>Person Location</th>
						<th>Contact Person</th>
						<th>Date of Call</th>
					</tr>
				</thead>
				<%
					getUserData();
					for (int i = 0; i < listPerson.size(); i++) {
				%>
				<tr>
					<td><%=i + 1%></td>
					<td><%=listPerson.get(i).getCaller()%></td>
					<td><%=listPerson.get(i).getCallerAddress()%></td>
					<td><%=listPerson.get(i).getCalled()%></td>
					<td><%=listPerson.get(i).getCalledTime()%></td>
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