<%@page import="java.sql.SQLException"%>
<%@page import="com.model.LandMarkModel"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.constants.WebConstants"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.webmodel.MasterRegionModel"%>
<%@page import="com.webmodel.MasterCity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<jsp:include page="header.jsp" />
<%!ArrayList<MasterRegionModel> listRegion;
	LandMarkModel editmodel = null;
	String editId = null;
	Connection con = null;
	public void getRegion() {
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
		}finally {
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
		try {
			con = DBConnectionProvider.getCon();
			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM dalmia_land_mark_project where Id=" + editId);
			ResultSet rs = ps.executeQuery();
			editmodel = new LandMarkModel();
			if (rs.next()) {
				editmodel.setDescription(rs.getString("P_Desc"));
				editmodel.setId(rs.getString("Id"));
				editmodel.setName(rs.getString("P_Name"));
				editmodel.setPlace(rs.getString("P_Place"));
				editmodel.setRegionId(rs.getString("Region_Id"));
				editmodel.setImage(rs.getString("P_Image"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			DBConnectionProvider.close(con);
		}
	} else {
		editmodel = null;
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
<script type='text/javascript'>
$(document).ready(
		function() {
			$(document).ajaxStop($.unblockUI);
            $(document).ajaxStart(function () { $.blockUI({ message: '<div class="load_1"><img src="images/loading.gif" /><p>Please Wait...</p></div>' }); });
		});

var base64Val=null;
var isOldImageAvl = "false";

function choose(){
	isOldImageAvl = "true";
}

function readURL(input) {
	if (input.files && input.files[0]) {
		base64Val=null;
		var reader = new FileReader();
		reader.onload = function (e) {
			base64Val=e.target.result
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
							var name = $('#name').val();
							var regionId = $('#region_id').val();
							var filePath = $('#file_name').val();
							var place = $('#place').val();
							var desc = $('#desc').val();
							if (idEdit === '') {
								$.ajax({
									type : 'POST',
									url : 'AjaxLandMark',
									data : 'name=' + name + '&RegionId='
											+ regionId + '&place=' + place
											+ '&desc=' + desc + '&Tag=AddNew'
											+'&Base64Data='+ base64Val 
											+ '&Path=' + filePath,
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
									url : 'AjaxLandMark',
									data : 'name=' + name + '&RegionId='
											+ regionId + '&place=' + place
											+ '&desc=' + desc 
											+ '&Tag=Edit'
											+'&Base64Data='+ base64Val
											+ '&isOldImgAvl=' + isOldImageAvl
											+ '&idEdit=' + idEdit + '&Path='
											+ filePath,
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
							if (editmodel != null && editmodel.getId() != null) {
						%>
						<h3>Edit Project Details</h3>
						<%
							} else {
						%>
						<h3>Add New Project</h3>
						<%}%>
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
						<label for="inputPassword">Name<span style="color: red;">*</span></label> <input type="text"
							<%if (editmodel != null && editmodel.getName() != null) {%>
							value="<%=editmodel.getName()%>" <%} else {%> value="" <%}%>
							id="name" class="login-inpt" placeholder="Enter the Name">
					</div>
					<div class="form-group">
						<label for="inputPassword">Place<span style="color: red;">*</span></label> <input type="text"
							<%if (editmodel != null && editmodel.getPlace() != null) {%>
							value="<%=editmodel.getPlace()%>" <%} else {%> value="" <%}%>
							id="place" class="login-inpt" placeholder="Enter the Place Name">
					</div>
					<div class="form-group">
						<label for="inputPassword">Description<span style="color: red;">*</span></label> <input type="text"
							<%if (editmodel != null && editmodel.getDescription() != null) {%>
							value="<%=editmodel.getDescription()%>" <%} else {%> value="" <%}%>
							id="desc" class="login-inpt" placeholder="Enter the Description">
					</div>
					<div class="form-group">
						<label for="exampleInputFile">Image<span style="color: red;">*</span></label> <input
							class="form-control-file" id="file_name"
							aria-describedby="fileHelp" type="file" accept=".jpg,.png" onchange="readURL(this);">
							<%
								if (editmodel != null && editmodel.getImage() != null) {
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

	<!-----footer--->

	<div class="admin_footer">Dalmia</div>

	<!-----end of footer--->
	</form>
	</div>
</body>

</html>