<%@page import="java.sql.SQLException"%>
<%@page import="com.constants.WebConstants"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.webmodel.MasterSpecialization"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<jsp:include page="header.jsp"></jsp:include>
<%!



ArrayList<MasterSpecialization> listCity;
public void getCity() {
	Connection con=null;
		try {
			 con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "Select * from master_specialization";
			ResultSet rs = stmt.executeQuery(sql);
			listCity = new ArrayList<MasterSpecialization>();
			while (rs.next()) {
				MasterSpecialization item = new MasterSpecialization();
				item.setId(rs.getString("Specialization_Id"));
				item.setSpecializationName(rs.getString("Specialization"));
				item.setIsActive(rs.getString("Is_Active"));
				listCity.add(item);
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
				title : 'Specialization_List'
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
				<td><a
					href="AddNew.jsp?id=<%=PasswordEncryption.encryptBase64("" + WebConstants.Specialization)%>">
						<button type="button" class="btn btn-info btn-lg">Add New</button>
				</a></td>
				<td style="width: 130px;" align="right">
					<div id="ExportToExcel"></div>
				</td>
			</tr>
			<tr>
				<td>
					<h3>Specialization List</h3>
				</td>
			</tr>
		</table>
		<p></p>
		<div class="details">
			<table id="example" class="display table table-bordered">
				<thead>
					<tr>
						<th scope="col">S.N.</th>
						<th scope="col">Specialization</th>
						<th scope="col">Status</th>
						<th scope="col">Action</th>
					</tr>
				</thead>
				<%
					getCity();
					if (listCity != null && listCity.size() > 0) {
						for (int i = 0; i < listCity.size(); i++) {
				%>
				<tr>
					<td><%=i + 1%></td>
					<td><%=listCity.get(i).getSpecializationName()%></td>
					<td><span id="ContentPlaceHolder1_GVManpower_lblStatus_0"><%=listCity.get(i).getIsActive()%></span>
					</td>
					<td><a
						href="EditFile.jsp?id=<%=PasswordEncryption.encryptBase64(listCity.get(i).getId())%>&Type=<%=PasswordEncryption.encryptBase64("" + WebConstants.Specialization)%>">Edit</a></td>
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








