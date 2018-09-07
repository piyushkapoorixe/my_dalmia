<%@page import="com.model.CityModel"%>
<%@page import="com.model.DistricModel"%>
<%@page import="com.model.EventModel"%>
<%@page import="com.utils.WebProjectLIstUtils"%>
<%@page import="com.webmodel.MasterState"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.webmodel.MasterCity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<jsp:include page="header.jsp" />
<%!String editId = null;
	EventModel eventData = null;
	ArrayList<MasterState> listState = null;
	%>

<%
	listState = WebProjectLIstUtils.getStateList();
	Connection con=null;
	if (request.getParameter("id") != null && request.getParameter("id").trim().length() > 0
			&& (!request.getParameter("id").equals("null"))) {
		editId = PasswordEncryption.dycriptBase64(request.getParameter("id").getBytes());
		try {
			con = DBConnectionProvider.getCon();
			PreparedStatement ps = con.prepareStatement( "SELECT master_event.*, master_state.StateName,master_city.CityName,master_distric.name as dist_name from master_event Join master_state On master_state.State_Id=master_event.State Join master_city On master_city.CityId=master_event.City Join master_distric On master_distric.id=master_event.Distric where master_event.Id="
							+ editId);
			ResultSet rs = ps.executeQuery();
			eventData = new EventModel();
			if (rs.next()) {
				eventData.setId(rs.getString("Id"));
				eventData.setDescription(rs.getString("Description"));
				eventData.setImage(rs.getString("Image"));
				eventData.setName(rs.getString("Name"));
				eventData.setPlace(rs.getString("Place"));
				eventData.setState(rs.getString("StateName"));
				eventData.setStateId(rs.getString("State"));
				eventData.setCity(rs.getString("CityName"));
				eventData.setCityId(rs.getString("City"));
				eventData.setDistricId(rs.getString("Distric"));
				eventData.setDistric(rs.getString("dist_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionProvider.close(con);
		}
	} else {
		eventData = null;
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
		
var isOldImageAvl = "false";

function choose(){
	isOldImageAvl = "true";
}

function populateDistric(values) {
	
	$("#city_id").html('');
	$.ajax({
		type : 'POST',
		url : 'AjaxFilterDistric',
		data : 'StateId=' + values,
		success : function(data2) {
			$('#success').html(data2);
			$("#dist_id").html(data2);
		},
		error : function(data) {
			alert('fail');
		}
	});
}
function populateCity(values) {
	$("#master_city_select").html('');
	$.ajax({
		type : 'POST',
		url : 'AjaxFilterCity',
		data : 'DistId=' + values,
		success : function(data2) {
			$('#success').html(data2);
			$("#city_id").html(data2);
		},
		error : function(data) {
			alert('fail');
		}
	});
}

	var base64Val = null;

	function readURL(input) {
		if (input.files && input.files[0]) {
			
			 var img=input.files[0].size;
		      if(img<=512000)
				{
				base64Val = null;
				var reader = new FileReader();
				reader.onload = function(e) {
					base64Val = e.target.result
					isOldImageAvl = "false";
				};
				reader.readAsDataURL(input.files[0]);
				}else{
				 alert('Image size must be less than or equals 512 kb.'+img); 
				 $('#file_name').val('');
			      //return false;
				}
		}
	}
	$(document).ready(
			function() {
				$("#upload").click(
						function() {
							var idEdit = $('#upload').attr('data-value');
							var name = $('#name').val();
							var StateId = $('#state_id').val();
							var CityId = $('#city_id').val();
							var filePath = $('#file_name').val();
							var DistId = $('#dist_id').val();
							
							var place = $('#place').val();
							var desc = $('#desc').val();
					
							if (idEdit === '') {
								$.ajax({
									type : 'POST',
									url : 'AjaxEvent',
									data : 'name=' + name +
									       '&StateId='+ StateId +
									       '&CityId='+ CityId +
									       '&DistId='+ DistId +
									       '&place=' + place+ 
									       '&desc=' + desc + 
									       '&isOldImgAvl=false' +
									       '&Tag=AddNew'+ 
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
									url : 'AjaxEvent',
									data : 'name=' + name +
									 '&StateId='+ StateId +
								     '&CityId='+ CityId +
									'&place=' + place+ 
									'&desc=' + desc + '&Tag=Edit'
											+ '&Base64Data=' + base64Val
											+'&DistId='+ DistId 
											+ '&idEdit=' + idEdit + 
											'&isOldImgAvl=' + isOldImageAvl + 
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
							if (eventData != null && eventData.getId() != null) {
						%>
						<h3>Edit Event Details</h3>
						<%
							} else {
						%>
						<h3>Add New Event</h3>
						<%}%>
					</div>
				</div>

				<div style="padding: 10px 30px 0px 30px" class="panel-body">
					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>


					<div class="form-group">
						<label for="inputPassword">Name<span style="color: red;">*</span></label> <input type="text"
							<%if (eventData != null && eventData.getName() != null) {%>
							value="<%=eventData.getName()%>" <%} else {%> value="" <%}%>
							id="name" class="login-inpt" placeholder="Enter the Name">
					</div>
					<div class="form-group">
						<label for="inputPassword">Place </label><span style="color: red;">*</span> <input type="text"
							<%if (eventData != null && eventData.getPlace() != null) {%>
							value="<%=eventData.getPlace()%>" <%} else {%> value="" <%}%>
							id="place" class="login-inpt" placeholder="Enter the Place Name">
					</div>

					<div class="form-group">
						<label for="inputPassword">State </label><span style="color: red;">*</span> <select
							class="form-control input-sm" id="state_id"  onchange="populateDistric(this.value)">
							<%
								for (int i = 0; i < listState.size(); i++) {
							%>
							<option
								<%if ( eventData!= null && eventData.getStateId().equals(listState.get(i).getStateId())) {%>
								selected="" <%}%> value=<%=listState.get(i).getStateId()%>><%=listState.get(i).getStateName()%></option>
							<%
								}
							%>
						</select>
					</div>
					
					
					
					<div class="form-group">
						<label for="inputPassword">District </label><span style="color: red;">*</span> <select
							class="form-control input-sm" id="dist_id"  onchange="populateCity(this.value)">
							<%
							if(eventData!=null && eventData.getDistricId()!=null && eventData.getDistricId().trim().length()>0)
							{
								ArrayList<DistricModel> listDistric=WebProjectLIstUtils.getListOfDistricBasedOnState(eventData.getStateId());
								for (int i = 0; i < listDistric.size(); i++) {
							%>
							<option
								<%if ( eventData!= null && eventData.getDistricId().equals(listDistric.get(i).getId())) {%>
								selected="" <%}%> value=<%=listDistric.get(i).getId()%>><%=listDistric.get(i).getName()%></option>
							<%
								}
								}
							%>
						</select>
					</div>

					<div class="form-group">
						<label for="inputPassword">City </label><span style="color: red;">*</span> <select
							class="form-control input-sm" id="city_id">
							<%
							
							if(eventData!=null && eventData.getCityId()!=null && eventData.getCityId().trim().length()>0)
							{
								ArrayList<CityModel> listCity=WebProjectLIstUtils.getListOfCityBasedOnDistric(eventData.getDistricId());
								for (int i = 0; i < listCity.size(); i++) {
							%>
							<option
								<%if ( eventData!= null && eventData.getCityId().equals(listCity.get(i).getCityId())) {%>
								selected="" <%}%> value=<%=listCity.get(i).getCityId()%>><%=listCity.get(i).getCityName()%></option>
							<%
								}
								}
							%>
						</select>
					</div>



					<div class="form-group">
						<label for="inputPassword">Description </label><span style="color: red;">*</span> <input type="text"
							<%if (eventData != null && eventData.getDescription() != null) {%>
							value="<%=eventData.getDescription()%>" <%} else {%> value="" <%}%>
							id="desc" class="login-inpt" placeholder="Enter the Description">
					</div>
					<div class="form-group">
						<label for="exampleInputFile">Image </label><span style="color: red;">*</span> <input
							class="form-control-file" id="file_name"
							aria-describedby="fileHelp" type="file" accept=".png"
							onchange="return readURL(this);">
							<%
						if(eventData!=null && eventData.getImage()!=null)
							
						{
							%>
							<img src=<%=eventData.getImage()%> onload="choose()" height="50" width="50">
						<%
						}
						%>
							<label style="font-size: 12px;color: red;">Image must be '.png' format and not more than '512' kb.</label>
					</div>
					<div class="form-group">
						<div class="controls">
							<a id="upload" class="btn btn-xm btn-primary"
							<%if (eventData != null && eventData.getId() != null) {%>
								data-value=<%=eventData.getId()%>>Apply Changes <%
								} else {
							%>
								data-value="">Upload <%
								}
							%></a>
						</div>
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