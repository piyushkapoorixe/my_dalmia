<%@page import="com.webmodel.AdminListModel"%>
<%@page import="com.webmodel.AdminRole"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.model.RHModel"%>
<%@page import="com.model.THModel"%>
<%@page import="com.webmodel.MasterRegionModel"%>
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
     AdminListModel itemRh = null;
	 ArrayList<MasterState> listState;
	%>
<%
    listState = WebProjectLIstUtils.getStateList();
	Connection con=null;
	if (request.getParameter("id") != null && request.getParameter("id").trim().length() > 0
			&& (!request.getParameter("id").equals("null"))) {
		editId = PasswordEncryption.dycriptBase64(request.getParameter("id").getBytes());
		try {
			con = DBConnectionProvider.getCon();
			PreparedStatement ps = con.prepareStatement( "SELECT * FROM tbl_adminlogin_details WHERE ID='"+ editId+"'");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				itemRh= new AdminListModel();
				itemRh.setEmailId(rs.getString("Email_ID"));
				itemRh.setId(rs.getString("ID"));
				itemRh.setLoginId(rs.getString("LoginID"));
				itemRh.setMobile(rs.getString("Mobile_No"));
				itemRh.setName(rs.getString("UserName"));
				itemRh.setState_RegionId(rs.getString("SubRegion"));
				itemRh.setStateId(rs.getString("StateId"));
				itemRh.setRoleId(rs.getString("RoleType"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionProvider.close(con);
		}
	} else {
		itemRh = null;
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

function populateRegion(values) {
	$.ajax({
		type : 'POST',
		url : 'AjaxFilterUserRegion',
		data : 'StateId=' + values,
		success : function(data2) {
			$('#success').html(data2);
			$("#region_id").html(data2);
		},
		error : function(data) {
			alert('fail');
		}
	});
}

	$(document).ready(
			function() {
				$("#save_rh").click(
						function() {
							var idEdit = $('#save_rh').attr('data-value');
							var login_Id= $('#login_Id').val();
							var State = $('#stateId').val();
							var Region = $('#region_id').val();
							var name = $('#name').val();
							var email = $('#email').val();
							var mobile = $('#mobile').val();
							
							
							if (State == null || State.length <= 0) {
								alert("Please Select State");
								return;
							}
							
							if (Region == null || Region.length <= 0) {
								alert("Please Select Region");
								return;
							}
							if (login_Id == null || login_Id.length <= 0) {
								alert("Please Enter the Login Id");
								return;
							}
							
							if (name == null || name.length <= 0) {
								alert("Please Enter Name");
								return;
							}
							if (email == null || email.length <= 0) {
								alert("Please Enter the Email ");
								return;
							}
							if (mobile == null || mobile.length != 10) {
								alert("Please Enter the valid mobile ");
								return;
							}
							
								$.ajax({
									type : 'POST',
									url : 'AjaxAdminController',
									data : 'NAME=' + name +
									   '&State='+ State +
								       '&subRegion='+ Region +
								       '&AdminId='+ idEdit +
								       '&UID='+ login_Id +
								       '&Tag=Edit' +
								       '&EMAIL='+ email +
								       '&role='+ "<%=itemRh.getRoleId()%>" +
								       '&MOBILE=' + mobile,
									success : function(data2) {
										$('#success').html(data2);
										alert(data2);
									},
									error : function(data) {
										alert('fail');
									}
								});
							

						});
			});
</script>


<body>
	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-4 col-md-offset-4 col-sm-8 admin_d col-sm-offset-2">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">
						<h3>Regional Head</h3>
					</div>
				</div>

				<div style="padding: 10px 30px 0px 30px" class="panel-body">
					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>
						<div class="form-group">
						<label for="inputPassword">State</label> <select
							class="form-control input-sm" id="stateId" onchange="populateRegion(this.value)" >
							<%
							for(int i=0;i<listState.size();i++)
							{
								%>
								<option
								<%if ( itemRh!= null && itemRh.getStateId().equals(listState.get(i).getStateId())) {%>
								selected="" <%}%> value=<%=listState.get(i).getStateId()%>><%=listState.get(i).getStateName()%></option>
						   <%	}
							%>
						</select>
					</div>
					
					
					<div class="form-group">
						<label for="inputPassword">Region</label> 
						<select class="form-control input-sm" id="region_id">
						<%
						if(itemRh != null && itemRh.getState_RegionId() != null)
						{
							ArrayList<MasterRegionModel> listRegion= WebProjectLIstUtils.getRegionList(itemRh.getStateId());
							for(int i=0;i<listRegion.size();i++)
							{
								%>
							<option
								<%if ( itemRh!= null && itemRh.getState_RegionId().equals(listRegion.get(i).getId())) {%>
								selected="" <%}%> value=<%=listRegion.get(i).getId()%>><%=listRegion.get(i).getRegion()%></option>	
								
								<%
							}
						}
						%>
						
						</select>
					</div>
					
					<div class="form-group">
						<label for="inputPassword">Login Id</label> <input type="text"
							<%if (itemRh != null && itemRh.getLoginId() != null) {%>
							value=<%=itemRh.getLoginId()%> <%} else {%> value="" <%}%>
							id="login_Id" class="login-inpt" placeholder="Enter the Login Id">
					</div>

					<div class="form-group">
						<label for="inputPassword">Name</label> <input type="text"
							<%if (itemRh != null && itemRh.getName() != null) {%>
							value=<%=itemRh.getName()%> <%} else {%> value="" <%}%>
							id="name" class="login-inpt" placeholder="Enter the Name">
					</div>
					<div class="form-group">
						<label for="inputPassword">Email</label> <input type="text"
							<%if (itemRh != null && itemRh.getEmailId() != null) {%>
							value=<%=itemRh.getEmailId()%> <%} else {%> value="" <%}%>
							id="email" class="login-inpt" placeholder="Enter the Email">
					</div>

					<div class="form-group">
						<label for="inputPassword">Mobile</label> <input type="number"
							<%if (itemRh != null && itemRh.getMobile() != null) {%>
							value=<%=itemRh.getMobile()%> <%} else {%> value="" <%}%>
							id="mobile" class="login-inpt" placeholder="Enter the Mobile">
					</div>
					
					<div class="form-group">
					<input type="submit"  value="Save" class="btn btn-primary" id="save_rh"
					<%if (itemRh != null) {%> data-value=<%=itemRh.getId()%> <%} else {%>
								data-value="" <%}%>
					/>
					</div>
					<div class="form-group">
					<input type="submit" onclick="window.history.go(-1); return false;"
					value="Back" class="btn btn-primary" />
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