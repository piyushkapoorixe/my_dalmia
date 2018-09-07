<%@page import="com.google.gson.Gson"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.model.BannerModel"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.webmodel.MasterRegionModel"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<jsp:include page="header.jsp" />
<%!BannerModel editmodel = null;
	String editId = null;

	ArrayList<MasterRegionModel> listRegion;
	public void getRegion() {
		Connection con = null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "Select * from master_stateregion where IsActive='1' Order by Region Asc";
			ResultSet rs = stmt.executeQuery(sql);
			listRegion = new ArrayList<MasterRegionModel>();
			while (rs.next()) {
				MasterRegionModel item = new MasterRegionModel();
				item.setId(rs.getString("id"));
				item.setRegion(rs.getString("Region"));
				listRegion.add(item);
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


<%
	if (request.getParameter("id") != null && request.getParameter("id").trim().length() > 0
			&& (!request.getParameter("id").equals("null"))) {
		editId = PasswordEncryption.dycriptBase64(request.getParameter("id").getBytes());
		Connection con=null;
		try {
			con = DBConnectionProvider.getCon();
			PreparedStatement ps = con
					.prepareStatement("SELECT master_regional_banner.*,master_stateregion.Region from master_regional_banner Inner Join master_stateregion On master_stateregion.id=master_regional_banner.RegionId where master_regional_banner.Id=" + editId);
			ResultSet rs = ps.executeQuery();
			editmodel = new BannerModel();
			if (rs.next()) {
				editmodel.setId(rs.getString("Id"));
				editmodel.setImage(rs.getString("Path"));
				editmodel.setRegion(rs.getString("Region"));
				editmodel.setRegionId(rs.getString("RegionId"));
				editmodel.setTitle(rs.getString("Title"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DBConnectionProvider.close(con);
		}
	} else {
		editmodel = null;
		editId=null;
	}
%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
<title>Dalmia</title>
<link rel="shortcut icon" href="/image/favicon.ico" type="image/x-icon" />
<link rel="icon" href="/image/favicon.ico" type="image/ico" />
<!-- Bootstrap framework -->
<link type="text/css" href="css/admin-style.css" rel="stylesheet" />
<link type="text/css" href="css/login.css" rel="stylesheet" />
<link rel="stylesheet" href="css/bootstrap.min.css" />
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script>
	
</script>
</head>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0-alpha1/jquery.js"></script>
<script type='text/javascript' src="js/jquery.min.js"></script>
<script type='text/javascript' src="js/jquery.blockUI.js"></script>
<script type='text/javascript' src="js/jquery.blockUI.js"></script>
<script type='text/javascript'>
$(document).ready(
		function() {
			$(document).ajaxStop($.unblockUI);
            $(document).ajaxStart(function () { $.blockUI({ message: '<div class="load_1"><img src="images/loading.gif" /><p>Please Wait...</p></div>' }); });
		});
	var base64Val = null;
	var isOldImageAvl = "false";
	
	function choose(){
		isOldImageAvl = "true";
	}

	function readURL(input) {
		
		if (input.files && input.files[0]) {
			base64Val = null;
			var reader = new FileReader();
			reader.onload = function(e) {
				base64Val = e.target.result
				isOldImageAvl = "false";
			};
			reader.readAsDataURL(input.files[0]);
		}
	}
	
	$(document).ready(
			function() {
				$("#upload").click(
						function() {
							var idEdit = $('#upload').attr('data-value');
							var filePath = $('#file_name').val();
							var regionId = $('#region_id').val();
							var title = $('#title').val();
							if (idEdit === '') {
								$.ajax({
									type : 'POST',
									url : 'AjaxUploadBanner',
									data : 'name=' + title +
									       '&Tag=AddNew'+ 
									       '&Region=' + regionId +
									       '&isOldImgAvl=false' +
									       '&Base64Data=' + base64Val+ 
									       '&Path=' + filePath,
									success : function(data2) {
										$('#success').html(data2);
										alert(data2);
									},
									error : function(data) {
										alert('fail');
									}
								});
							} else {
								$.ajax({
									type : 'POST',
									url : 'AjaxUploadBanner',
									data : 'name=' + title +
								       '&Tag=Edit'+ 
								       '&Region=' + regionId + 
								       '&idEdit=' + idEdit+
								       '&isOldImgAvl=' + isOldImageAvl+
								       '&Base64Data=' + base64Val+ 
								       '&Path=' + filePath,
									success : function(data2) {
										$('#success').html(data2);
										alert(data2);
									},
									error : function(data) {
										alert('fail');
									}
								});
							}

						});
			});
	
	
</script>
<body>
	<div class="container">
		<div class="col-lg-12">
			<input type="submit" onclick="window.history.go(-1); return false;"
					value="Back" class="btn_inp_al" />
		</div>
		<div id="loginbox"
			class="mainbox col-md-4 col-md-offset-4 col-sm-8 admin_d col-sm-offset-2">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">
						<%
						if(editmodel!=null && editmodel.getTitle()!=null)
							
						{
						%>
							<h3>Edit Banner Details</h3>
						<%
						}else{
						%>
							<h3>Upload Banner</h3>
						<%	
							
						}
						%>
					</div>
				</div>

				<div style="padding: 10px 30px 0px 30px" class="panel-body">
					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>
					<div class="form-group">
						<label for="inputPassword">Region</label> <select
							class="form-control input-sm" id="region_id">
							<%
								getRegion();
								for (int i = 0; i < listRegion.size(); i++) {
							%>
							<option
								<%if (editmodel != null && editmodel.getRegionId().equals(listRegion.get(i).getId())) {%>
								selected="" <%}%> value=<%=listRegion.get(i).getId()%>><%=listRegion.get(i).getRegion()%></option>
							
							<%
								}
							%>
						</select>
					</div>

					<div class="form-group">
						<label for="inputPassword">Title<span style="color: red;">*</span></label>

						<%
						if(editmodel!=null && editmodel.getTitle()!=null)
							
						{
							%>
						<input type="text" id="title" class="login-inpt"
							value="<%=editmodel.getTitle()%>">
						<%
						}else{
							%>
							<input type="text" id="title" class="login-inpt"
							placeholder="Enter the Title">
						<%	
							
						}
						%>
					</div>
					<div class="form-group">
						<label for="exampleInputFile">File<span style="color: red;">*</span></label> <input
							class="form-control-file" id="file_name"
							aria-describedby="fileHelp" type="file" accept=".jpg,.png"
							onchange="readURL(this);">
							
							<%
						if(editmodel!=null && editmodel.getImage()!=null)
							
						{
							%>
							<img src=<%=editmodel.getImage()%> onload="choose()" height="50" width="50">
						<%
						}
						%>
					</div>
					<div class="form-group">
						<div class="controls">
						<a id="upload" class="btn btn-xm btn-primary"
								<%if (editmodel != null && editmodel.getId() != null) {%>
								data-value=<%=editmodel.getId()%>>Apply Changes <%} else {%> data-value="">Upload
								<%}%></a>
						
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-----footer--->

	<div class="admin_footer">Dalmia</div>

	<!-----end of footer--->
</body>

</html>