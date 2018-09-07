<%@page import="com.model.DistricModel"%>
<%@page import="com.model.RHModel"%>
<%@page import="com.model.THModel"%>
<%@page import="java.sql.SQLException"%>
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


<%!ArrayList<DistricModel> listDistric;
	public void getListOfDistric() {
		Connection con = null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "SELECT  master_distric.*,master_state.StateName,master_user_region.Region from master_distric Join master_state On master_state.State_Id=master_distric.stateId Join master_user_region On master_user_region.Region_id=master_distric.RegionId";
			ResultSet rs = stmt.executeQuery(sql);
			
			listDistric = new ArrayList<DistricModel>();
			while (rs.next()) {
				DistricModel item = new DistricModel();
				item.setId(rs.getString("id"));
				item.setIsactive(rs.getString("isactive"));
				item.setName(rs.getString("name"));
				item.setState(rs.getString("StateName"));
				item.setRegion(rs.getString("Region"));
				item.setRegionId(rs.getString("RegionId"));
				listDistric.add(item);
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
<link type="text/css" href="css/login.css" rel="stylesheet" />
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script>
	
</script>
</head>
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
	$(document).ready(function() {
		$(".del").click(function() {
			var del = $(this).children().attr('data-string');
			if (confirm("Are you sure, want to Delete this ?")) {
				$.ajax({
					type : 'POST',
					url : 'AjaxFaqsController',
					data : 'Id=' + del + '&Tag=Del',
					success : function(data2) {
						$('#success').html(data2);
						alert(data2);
						location.reload();
					},
					error : function(data) {
						alert('fail');
					}
				});
			}
			return false;
		});
	});
</script>

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
			title : 'District_List'
		} ]
	}).container().appendTo($('#ExportToExcel'));
});
	
</script>

<body>
	<div class="container">
		<table class="col-lg-12">
			<tr>
				<td><a href="AddNewDistric.jsp">
						<button type="button" class="btn btn-info btn-lg">Add New</button>
				</a></td>
				<td style="width: 130px;" align="right">
					<div id="ExportToExcel"></div>
				</td>
			</tr>
			<tr>
				<td>
					<h3>District List</h3>
				</td>
			</tr>
		</table>
		<p></p>
		<div class="details">
			<table id="example" class="display table table-bordered">
				<thead>
					<tr>
						<th>S.N.</th>
						<th>District Name</th>
						<th style="text-align: center">Region</th>
						<th style="text-align: center">State Name</th>
						<th style="text-align: center">Status</th>
						<th style="text-align: center">Action</th>
					</tr>
				</thead>
				<tbody id="data_come">
					<%
						getListOfDistric();
						for (int i = 0; i < listDistric.size(); i++) {
					%>
					<tr>
						<td><%=i + 1%></td>
						<td align="center"><%=listDistric.get(i).getName()%></td>
						<td align="center"><%=listDistric.get(i).getRegion()%></td>
						<td align="center"><%=listDistric.get(i).getState()%></td>
						<td align="center">
							<%
								if (listDistric.get(i).getIsactive().equals("1")) {
							%> Active <%
								} else {
							%> Inactive <%
								}
							%>
						</td>
						<td align="center"><a
							href="AddNewDistric.jsp?id=<%=PasswordEncryption.encryptBase64(listDistric.get(i).getId())%>
								&rid=<%=PasswordEncryption.encryptBase64(listDistric.get(i).getRegionId())%>
								&status=<%=listDistric.get(i).getIsactive()%>">Edit</a>
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