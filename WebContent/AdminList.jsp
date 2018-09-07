<%@page import="com.webmodel.AdminListModel"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.webmodel.PersonModel"%>
<%@page import="com.constants.WebConstants"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.webmodel.MasterCity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<jsp:include page="header.jsp"></jsp:include>
<%!ArrayList<AdminListModel> listPerson = null;
	private Connection con;
	public void getUserData() {
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "SELECT tbl_adminlogin_details.*, master_stateregion.Region as MainRegion, master_user_region.Region as subRegionName, master_state.StateName as stateName, master_distric.name as distName, master_admin_role.RoleType as RoleName from tbl_adminlogin_details Left Join master_stateregion On master_stateregion.id=tbl_adminlogin_details.RegionId Left Join master_user_region On master_user_region.Region_id=tbl_adminlogin_details.SubRegion Left Join master_state On master_state.State_Id=tbl_adminlogin_details.StateId Left Join master_admin_role On master_admin_role.Id=tbl_adminlogin_details.RoleType Left Join master_distric On master_distric.id=tbl_adminlogin_details.DistricId WHERE tbl_adminlogin_details.Status=1";
			ResultSet rs = stmt.executeQuery(sql);
			listPerson = new ArrayList<AdminListModel>();
			while (rs.next()) {
				AdminListModel item = new AdminListModel();
				item.setId(rs.getString("ID"));
				item.setEmailId(rs.getString("Email_ID"));
				item.setLoginId(rs.getString("LoginID"));
				item.setMobile(rs.getString("Mobile_No"));
				item.setName(rs.getString("UserName"));
				item.setRoleId(rs.getString("RoleType"));
				if(rs.getString("MainRegion")!=null)
				{
					item.setRegion(rs.getString("MainRegion"));	
				}else
				{
					item.setRegion("N/A");	
				}
				if(rs.getString("stateName")!=null)
				{
					item.setState(rs.getString("stateName"));
				}else
				{
					item.setState("N/A");
				}
				if(rs.getString("distName")!=null)
				{
					item.setDistric(rs.getString("distName"));
				}else
				{
					item.setDistric("N/A");
				}
				if(rs.getString("subRegionName")!=null)
				{
					item.setState_Region(rs.getString("subRegionName"));
				}else
				{
					item.setState_Region("N/A");
				}
				item.setRole(rs.getString("RoleName"));
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
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.colVis.min.js"></script>
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
	var table = $('#example').DataTable( {
        "scrollX": true
    } );

	new $.fn.dataTable.Buttons(table, {
		buttons : [ {
			extend : 'excelHtml5',
			text : 'Excel Download',
			className : 'btn btn-info btn-lg',
			title : 'Admin_List',
			exportOptions: {
                columns: [0,1,2,3,4,5,6,7,8,9]
            }
		} ]
	}).container().appendTo($('#ExportToExcel'));
	
	new $.fn.dataTable.Buttons(table, {
		buttons : [ {
			extend : 'columnsToggle'
		} ]
	}).container().appendTo($('#ColumnVisibility'));
});
</script>
</head>
<body>
	<div class="container">
		<table class="col-lg-12">
			<tr>
				<td><a href="CreateAdmin.jsp"><button type="button"
							class="btn btn-info btn-lg">Add New</button></a></td>
				<td style="width: 130px;" align="right">
					<div id="ExportToExcel"></div>
				</td>
			</tr>
			<tr>
				<td>
					<h3>Admin List</h3>
				</td>
				<td style="width: 1000px;" align="right">
					<div id="ColumnVisibility" style="margin: 10px 0px;"></div>
				</td>
			</tr>
		</table>

		<div class="details">
			<table id="example" class="display table table-bordered">
				<thead>
					<tr>
						<th>S.N.</th>
						<th>Name</th>
						<th>LoginId</th>
						<th style="text-align: center">Email Id</th>
						<th style="text-align: center">Mobile No.</th>
						<th style="text-align: center">Zone</th>
						<th style="text-align: center">State</th>
						<th style="text-align: center">Region</th>
						<th style="text-align: center">District</th>
						<th style="text-align: center">Role</th>
						<th style="text-align: center">Edit</th>
						<th style="text-align: center">Action</th>
					</tr>
				</thead>
				<tbody id="data_come">
					<%
						getUserData();
						for (int i = 0; i < listPerson.size(); i++) {
					%>
					<tr>
						<td><%=i + 1%></td>
						<td><%=listPerson.get(i).getName()%></td>
						<td><%=listPerson.get(i).getLoginId()%></td>
						<td align="center"><%=listPerson.get(i).getEmailId()%></td>
						<td align="center"><%=listPerson.get(i).getMobile()%></td>
						<td align="center"><%=listPerson.get(i).getRegion()%></td>
						<td align="center"><%=listPerson.get(i).getState()%></td>
						<td align="center"><%=listPerson.get(i).getState_Region()%></td>
						<td align="center"><%=listPerson.get(i).getDistric()%></td>
						<td align="center"><%=listPerson.get(i).getRole()%></td>
						<!-- action="EditAdmin.jsp" -->
						<td align="center">
							<%
								if (listPerson.get(i).getRoleId().equals("1")) {
										//Admin and Technical Head
							%> <a
							href="EditAdmin.jsp?id=<%=PasswordEncryption.encryptBase64(listPerson.get(i).getId())%>"
							class="btn btn-success">Edit</a> <%
 	} else if (listPerson.get(i).getRoleId().equals("2")) { //Admin and TSO
 %> <a
							href="EditTso.jsp?id=<%=PasswordEncryption.encryptBase64(listPerson.get(i).getId())%>"
							class="btn btn-success">Edit</a> <%
 	} else if (listPerson.get(i).getRoleId().equals("3")) { //Admin and Regional
 %> <a
							href="EditRegionalHead.jsp?id=<%=PasswordEncryption.encryptBase64(listPerson.get(i).getId())%>"
							class="btn btn-success">Edit</a> <%
 	} else if (listPerson.get(i).getRoleId().equals("4")) { //Admin and Regional
 %> <a
							href="EditTechnicalHead.jsp?id=<%=PasswordEncryption.encryptBase64(listPerson.get(i).getId())%>"
							class="btn btn-success">Edit</a> <%
 	}
 %>
						</td>
						<td align="center">
							<form action="ResetPassword.jsp" method="get">
								<input type="hidden"
									value=<%="Admin" + PasswordEncryption.encryptBase64(listPerson.get(i).getId())%>
									name="Uid"> <input type="submit" value="Reset Password"
									class="btn btn-success">
							</form>
						</td>

					</tr>
					<%
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