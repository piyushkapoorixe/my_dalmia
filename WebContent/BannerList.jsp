<%@page import="com.model.BannerModel"%>
<%@page import="com.model.EventModel"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.model.LandMarkModel"%>
<%@page import="com.model.LandMarkModelList"%>
<%@page import="com.model.CallHistoryModel"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.webmodel.MasterCity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<jsp:include page="header.jsp"></jsp:include>
<%!ArrayList<BannerModel> listBanner;
	public void getBanner() {
		Connection con = null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "SELECT master_regional_banner.*,master_stateregion.Region from master_regional_banner Inner Join master_stateregion On master_stateregion.id=master_regional_banner.RegionId Order By master_stateregion.Region Asc";
			ResultSet rs = stmt.executeQuery(sql);
			listBanner = new ArrayList<BannerModel>();
			while (rs.next()) {
				BannerModel item = new BannerModel();
				item.setId(rs.getString("Id"));
				item.setImage(rs.getString("Path"));
				item.setRegion(rs.getString("Region"));
				item.setRegionId(rs.getString("RegionId"));
				item.setTitle(rs.getString("Title"));
				listBanner.add(item);
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
<script type='text/javascript' src="js/jquery.min.js"></script>
<script type='text/javascript' src="js/jquery.blockUI.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/r/bs-3.3.5/jq-2.1.4,dt-1.10.8/datatables.min.css" />
<script type="text/javascript"
	src="https://cdn.datatables.net/r/bs-3.3.5/jqc-1.11.3,dt-1.10.8/datatables.min.js"></script>
<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				$('#example').DataTable();
			} );
			
			
			$(document).ready(
					$(document).bind("ajaxSend", function(){
						   $("#loading").show();
						 }).bind("ajaxComplete", function(){
						   $("#loading").hide();
						 })
						 
					/* function() {
						$(document).ajaxStop($.unblockUI);
			            $(document).ajaxStart(function () { $.blockUI({ message: '<div class="load_1"><img src="images/loading.gif" /><p>Please Wait...</p></div>' }); });
					} */);
					
					
					
			 	$(document).ready(function() {
					$(".del").click(function() {
						var del = $(this).children().attr('data-string');
						if (confirm("Are you sure, want to Delete this ?")) {
							//alert('Thanks for confirming');
							$.ajax({
								type : 'POST',
								url : 'AjaxUploadBanner',
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
						else {
    						//alert('Why did you press cancel? You should have confirmed');
    						return false;
						}
					});
				}); 
			 	
	
</script>
</head>
<body>
	<div class="container">
		<table class="col-lg-12">
			<tr>
				<td><a href="UploadBanner.jsp"><button type="button"
							class="btn btn-info btn-lg">Add New</button></a></td>
			</tr>
			<tr>
				<td>
					<h3>Banners</h3>
				</td>
			</tr>
		</table>
		<p></p>
		<div class="details">
			<table id="example" class="display table table-bordered">
				<thead>
					<tr>
						<th>S.N.</th>
						<th>Image</th>
						<th>Title</th>
						<th>Region</th>
						<th>Action</th>
					</tr>
				</thead>
				<%
					getBanner();
					for (int i = 0; i < listBanner.size(); i++) {
				%>
				<tr>
						<td><%=i + 1%></td>
						<td><img src=<%=listBanner.get(i).getImage()%> height="50"
							width="50"></td>
						<td style="padding-top: 20px;"><%=listBanner.get(i).getTitle()%></td>
						<td style="padding-top: 20px;"><%=listBanner.get(i).getRegion()%></td>
						<td style="padding-top: 20px;"><a
							href="UploadBanner.jsp?id=<%=PasswordEncryption.encryptBase64(listBanner.get(i).getId())%>">
								<img src="images/edit.png" height="15" width="15"
								data-string=<%=listBanner.get(i).getId()%>>
						</a> <a class="del"> <img src="images/delete-button.png"
								height="15" width="15"
								data-string=<%=listBanner.get(i).getId()%>>
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