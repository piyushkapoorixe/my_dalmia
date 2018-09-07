<%@page import="com.utils.RandomIdGenerator"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.webmodel.UserQueryFeedback"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<jsp:include page="header.jsp"></jsp:include>
<%!ArrayList<UserQueryFeedback> listData;

	public void getData() {
		Connection con=null;
		try {
			 con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "Select users_details.user_Id,users_query.QueryId, master_queryfeedback.Query,users_query.id, users_query.Type, users_query.Comment, users_query.Query_Ref_Id,users_query.CreatedDate,users_query.State,users_query.City,users_details.user_Name, users_details.email, users_details.mob_no, master_profession.Profession from users_details INNER join users_query ON users_query.UserId= users_details.user_Id INNER join master_profession ON users_details.profession_Id= master_profession.profession_Id INNER join master_queryfeedback ON master_queryfeedback.id =users_query.QueryId  Order By users_query.CreatedDate Desc";
			ResultSet rs = stmt.executeQuery(sql);
			listData = new ArrayList<UserQueryFeedback>();
			while (rs.next()) {
				UserQueryFeedback item = new UserQueryFeedback();
				item.setComment(rs.getString("Comment"));
				item.setCreated_Date(rs.getString("CreatedDate"));
				item.setEmail(rs.getString("email"));
				item.setMobile(rs.getString("mob_no"));
				item.setName(rs.getString("user_Name"));
				item.setProfession(rs.getString("Profession"));
				item.setQuery(rs.getString("Query"));
				item.setType(rs.getString("Type"));
				item.setUid(rs.getString("user_Id"));
				item.setRefId(rs.getString("Query_Ref_Id"));
				item.setState(rs.getString("State"));
				item.setCity(rs.getString("City"));
				listData.add(item);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
<script type='text/javascript' src="js/jquery.min.js"></script>
<script type='text/javascript'></script>
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
						<button type="submit" value="Users_Query" name="Type"
							class="btn btn-info btn-lg">Download Report</button>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<h3>Feedback List</h3>
				</td>
			</tr>
		</table>
		<p></p>
		<div class="details">
			<table id="example" class="display table table-bordered">
				<thead>
					<tr>
						<th scope="col">S.N.</th>
						<th scope="col">Ref. Id</th>
						<th scope="col">Name</th>
						<th scope="col">Profession</th>
						<th scope="col">Email</th>
						<th scope="col">Mobile</th>
						<th scope="col">Type</th>
						<th scope="col">Comment</th>
						<th scope="col">City</th>
						<th scope="col">State</th>
						<th scope="col">Created Date</th>

					</tr>
				</thead>
				<%
					getData();

					if (listData != null && listData.size() > 0) {
						for (int i = 0; i < listData.size(); i++) {
				%>
				<tr>
					<td><%=i + 1%></td>
					<td><%=listData.get(i).getRefId()%></td>
					<td><%=listData.get(i).getName()%></td>
					<td><span id="ContentPlaceHolder1_GVManpower_lblStatus_0"><%=listData.get(i).getProfession()%></span>
					</td>
					<td><span id="ContentPlaceHolder1_GVManpower_lblStatus_0"><%=listData.get(i).getEmail()%></span>
					</td>
					<td><%=listData.get(i).getMobile()%></td>
					<td><%=listData.get(i).getQuery()%></td>
					<td><span id="ContentPlaceHolder1_GVManpower_lblStatus_0"><%=listData.get(i).getComment()%></span>
					</td>
					<td><%=listData.get(i).getCity()%></td>
					<td><%=listData.get(i).getState()%></td>
					<td><span id="ContentPlaceHolder1_GVManpower_lblStatus_0"><%=listData.get(i).getCreated_Date()%></span>
					</td>
				</tr>
				<%
					}
					}
				%>

			</table>
		</div>
	</div>
	<!-----end of middle--->



	<!-----footer--->

	<div class="admin_footer">Dalmia</div>

	<!-----end of footer--->


	</form>
</body>
</html>








