<%@page import="java.sql.SQLException"%>
<%@page import="com.constants.WebConstants"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.webmodel.MasterState"%>
<jsp:include page="header.jsp"></jsp:include>
<%!



ArrayList<MasterState> listState;
public void getList() {
	Connection con=null;
		try {
			 con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "SELECT master_state.Is_Active,master_state.State_Id,master_state.StateName,master_stateregion.id, master_stateregion.Region,master_stateregion.id,master_stateregion.ContactNo from master_state INNER JOIN master_stateregion ON master_state.Region_Id=master_stateregion.id";
			ResultSet rs = stmt.executeQuery(sql);
			listState = new ArrayList<MasterState>();
			while (rs.next()) {
				MasterState item = new MasterState();
				item.setStateId(rs.getString("State_Id"));
				item.setStateName(rs.getString("StateName"));
				item.setRegion(rs.getString("Region"));
				item.setIsActive(rs.getString("Is_Active"));
				item.setContactNo(rs.getString("ContactNo"));
				item.setRegionId(rs.getString("id"));
				listState.add(item);
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
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/r/bs-3.3.5/jq-2.1.4,dt-1.10.8/datatables.min.css" />
<script type="text/javascript"
	src="https://cdn.datatables.net/r/bs-3.3.5/jqc-1.11.3,dt-1.10.8/datatables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.flash.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.html5.min.js"></script>
<script type="text/javascript" charset="utf-8">

	$(document).ready(function() {
		var table = $('#example').DataTable();

		new $.fn.dataTable.Buttons(table, {
			buttons : [ {
				extend : 'excelHtml5',
				text : 'Excel Download',
				className : 'btn btn-info btn-lg',
				title : 'State_List'
			} ]
		}).container().appendTo($('#ExportToExcel'));
	});
</script>

</head>
<body>




	<!-----middle--->

	<div class="container">
		<table class="col-lg-12">
			<tr>
				<td><a href="AddNew.jsp?id=<%=PasswordEncryption.encryptBase64("" + WebConstants.State)%>">
				<button type="button" class="btn btn-info btn-lg">Add New</button></a></td>
				<td style="width: 130px;" align="right">
					<div id="ExportToExcel"></div>
				</td>
			</tr>
			<tr>
				<td>
					<h3>State List</h3>
				</td>
			</tr>
		</table>
		<p></p>
		<div class="details">
			<table id="example" class="display table table-bordered">
				<thead>
					<tr>
						<th scope="col">S.N.</th>
						<th scope="col">State</th>
						<th scope="col">Zone</th>
						<th scope="col">Contact No</th>
						<th scope="col">Status</th>
						<th scope="col">Action</th>
					</tr>
				</thead>
				<%
					getList();

					if (listState != null && listState.size() > 0) {
						for (int i = 0; i < listState.size(); i++) {
				%>
				<tr>
					<td><%=i + 1%></td>
					<td><%=listState.get(i).getStateName()%></td>
					<td><span id="ContentPlaceHolder1_GVManpower_lblStatus_0"><%=listState.get(i).getRegion()%></span>
					</td>
					<td><span id="ContentPlaceHolder1_GVManpower_lblStatus_0"><%=listState.get(i).getContactNo()%></span>
					</td>
					<td><span id="ContentPlaceHolder1_GVManpower_lblStatus_0"><%=listState.get(i).getIsActive()%></span>
					</td>
					<td><a
						href="EditFile.jsp?id=<%=PasswordEncryption.encryptBase64(listState.get(i).getStateId())%>&Type=<%=PasswordEncryption.encryptBase64("" + WebConstants.State)%>&RegionId=<%=PasswordEncryption.encryptBase64(listState.get(i).getRegionId())%>">Edit</a></td>
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
</body>
</html>








