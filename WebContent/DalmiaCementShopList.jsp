<%@page import="com.model.CementShopModel"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.constants.WebConstants"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.webmodel.ManPowerModel"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<jsp:include page="header.jsp"></jsp:include>
<%!
ArrayList<CementShopModel> listShops;
public void getList() {
	Connection con=null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "Select dalmia_cement_shop.*, master_city.CityName,master_distric.name as dist_Name,master_state.StateName from dalmia_cement_shop Join master_city on master_city.CityId=dalmia_cement_shop.CityId Join master_distric on master_distric.id=dalmia_cement_shop.District_Id Join master_state on master_state.State_Id=dalmia_cement_shop.StateId";
			ResultSet rs = stmt.executeQuery(sql);
			listShops = new ArrayList<CementShopModel>();
			while (rs.next()) {
				CementShopModel item = new CementShopModel();
				item.setId(rs.getString("Id"));
				item.setDelearId(rs.getString("DelearId"));
				item.setAdd(rs.getString("Address"));
				item.setAdd2(rs.getString("Address1"));
				item.setAdd3(rs.getString("Address2"));
				item.setPinCode(rs.getString("PinCode"));
				item.setCityId(rs.getString("CityId"));
				item.setCity(rs.getString("CityName"));
				item.setDistricId(rs.getString("District_Id"));
				item.setDistrict(rs.getString("dist_Name"));
				item.setStateId(rs.getString("StateId"));
				item.setState(rs.getString("StateName"));
				listShops.add(item);
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
			title : 'Cement_Shop_List'
		} ]
	}).container().appendTo($('#ExportToExcel'));
});
			
			
			
			$(document).ready(function() {
				$(".del").click(function() {
					var del = $(this).children().attr('data-string');
					if (confirm("Are you sure, want to Delete this ?")) {
						$.ajax({
							type : 'POST',
							url : 'AjaxCementShop',
							data : 'DelId=' + del+
							'&Tag=Del',
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
</head>
<body>




	<!-----middle--->

	<div class="container">
		<table class="col-lg-12">
			<tr>
				<td><a href="DalmiaCementShop.jsp"><button type="button"
						class="btn btn-info btn-lg">Add New</button></a></td>
				<td style="width: 130px;" align="right">
					<div id="ExportToExcel"></div>
				</td>
			</tr>
			<tr>
				<td>
					<h3>Shop List</h3>
				</td>
			</tr>
		</table>
		<p></p>
		<div class="details">
			<table id="example" class="display table table-bordered">
						<thead>
							<tr>
							<th>S.N.</th>
								<th>Delear Id</th>
								<th>Address</th>
								<th>Address1</th>
								<th>Address2</th>
								<th>PinCode</th>
								<th>City</th>
								<th>District</th>
								<th>State</th>
								<th>Action</th>
							</tr>
						</thead>
						<%
						getList();
							for (int i = 0; i < listShops.size(); i++) {
						%>
						<tr>
						<td><%=i+1 %></td>
							<td style="padding-top: 20px;"><%=listShops.get(i).getDelearId()%></td>
							
							<td style="padding-top: 20px;"><%=listShops.get(i).getAdd()%></td>
							
							<td style="padding-top: 20px;"><%=listShops.get(i).getAdd2()%></td>
							
							<td style="padding-top: 20px;"><%=listShops.get(i).getAdd3()%></td>
							
							<td style="padding-top: 20px;"><%=listShops.get(i).getPinCode()%></td>
							
							<td style="padding-top: 20px;"><%=listShops.get(i).getCity()%></td>
							
							<td style="padding-top: 20px;"><%=listShops.get(i).getDistrict()%></td>
							
							<td style="padding-top: 20px;"><%=listShops.get(i).getState()%></td>
							
							<td style="padding-top: 20px;">
							<a href="DalmiaCementShop.jsp?id=<%=PasswordEncryption.encryptBase64(listShops.get(i).getId())%>">
									<img src="images/edit.png" height="15" width="15"
									data-string=<%=listShops.get(i).getId()%>>
							</a> 
							<a class="del"> <img src="images/delete-button.png" height="15" width="15"
									data-string=<%=listShops.get(i).getId()%>>
							</a></td>
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








