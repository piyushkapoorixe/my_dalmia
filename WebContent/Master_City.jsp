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
<%!ArrayList<MasterCity> listCity;
	public void getCity() {
		Connection con = null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "Select master_distric.name as dist_name,master_distric.id as dist_id,master_city.CityName,master_city.CityId,master_city.Is_Active,master_state.StateName from master_city INNER JOIN master_distric ON master_distric.id=master_city.DiscticId INNER JOIN master_state ON master_state.State_Id=master_distric.stateId";
			ResultSet rs = stmt.executeQuery(sql);
			listCity = new ArrayList<MasterCity>();
			while (rs.next()) {
				MasterCity item = new MasterCity();
				item.setCityId(rs.getString("CityId"));
				item.setCityName(rs.getString("CityName"));
				item.setDistricName(rs.getString("dist_name"));
				item.setStateName(rs.getString("StateName"));
				item.setIsActive(rs.getString("Is_Active"));
				item.setDistricId(rs.getString("dist_id"));
				listCity.add(item);
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
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

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
				title : 'City_List'
			} ]
		}).container().appendTo($('#ExportToExcel'));
	});
</script>
</head>

<body>
	<div class="container">
		<table class="col-lg-12">
			<tr>
				<td><a
					href="AddNew.jsp?id=<%=PasswordEncryption.encryptBase64("" + WebConstants.City)%>">
						<button type="button" class="btn btn-info btn-lg">Add New</button>
				</a></td>
				<td style="width: 130px;" align="right">
					<div id="ExportToExcel"></div>
				</td>
			</tr>
			<tr>
				<td>
					<h3>City List</h3>
				</td>
			</tr>
		</table>
		<p></p>
		<div class="details">
			<table id="example" class="display table table-bordered">
			<thead>
				<tr>
					<th scope="col">S.N.</th>
					<th scope="col">City</th>
					<th scope="col">District</th>
					<th scope="col">State</th>
					<th scope="col">Status</th>
					<th scope="col">Action</th>
				</tr>
			</thead>

			<tbody>

				<%
					getCity();
					if (listCity != null && listCity.size() > 0) {
						for (int i = 0; i < listCity.size(); i++) {
				%>
				<tr>
					<td><%=i + 1%></td>
					<td><span id="ContentPlaceHolder1_GVManpower_lblStatus_0"><%=listCity.get(i).getCityName()%></span>
					</td>
					<td><%=listCity.get(i).getDistricName()%></td>
					<td><%=listCity.get(i).getStateName()%></td>
					<td><span id="ContentPlaceHolder1_GVManpower_lblStatus_0"><%=listCity.get(i).getIsActive()%></span>
					</td>
					<td><a
						href="EditCity.jsp?id=<%=PasswordEncryption.encryptBase64(listCity.get(i).getCityId())%>">Edit</a></td>
				</tr>
				<%
					}
					}
				%>
			</tbody>
		</table>
		</div>
	</div>
	<!-----end of middle--->



	<!-----footer--->

	<div class="admin_footer">Dalmia</div>

	<!-----end of footer--->
</body>

</html>