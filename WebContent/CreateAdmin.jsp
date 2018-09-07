<%@page import="com.webmodel.MasterState"%>
<%@page import="com.webmodel.AdminRole"%>
<%@page import="com.utils.WebProjectLIstUtils"%>
<%@page import="java.sql.SQLException"%>
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
<%!ArrayList<AdminRole> listRole;
	ArrayList<MasterRegionModel> listRegion;
	ArrayList<MasterState> listState;
	Connection con;
	public void getRegion() {
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "Select * from master_stateregion where IsActive='1' AND Region!='ALL'";
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
	listRole = WebProjectLIstUtils.getListOfAdminRole();
    listState = WebProjectLIstUtils.getStateList();
%>

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
<script>
	
</script>
</head>
<script type='text/javascript' src="js/jquery.min.js"></script>
<script type='text/javascript' src="js/jquery.blockUI.js"></script>
<script type='text/javascript'>
	function populateMainLayout(values) {
		var roleType = values;
		 var admin_layout = $("#admin_layout");
		 var th_layout = $("#th_layout");
		 var rh_layout = $("#rh_layout");
		 var tso_layout = $("#tso_layout");
		 
		
		if (roleType == 1 ) {
			/* ADMIN Layout */
			 admin_layout.show();
			 th_layout.hide();
			 tso_layout.hide();
			 rh_layout.hide();
			
		} else if (roleType == 2) {
			/* TSO Layout */
			th_layout.hide();
			 admin_layout.hide();
			 tso_layout.show();
			 rh_layout.hide();
		} else if (roleType == 3) {
			/* RH Layout */
			th_layout.hide();
			 admin_layout.hide();
			 tso_layout.hide();
			 rh_layout.show();
		} 
		else if (roleType == 4) {
			/* TH Layout */
			  th_layout.show();
			  admin_layout.hide();
			 tso_layout.hide();
			 rh_layout.hide();
		} 
	}

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
	
	
	$(document).ready(
			function() {
				$("#create_admin").click(
						function() {
							var Role_Id = $("#Role_Id").val();
							var UID = $('#admin_uid').val();
							var NAME = $('#admin_name').val();
							var EMAIL = $('#admin_email').val();
							var MOBILE = $('#admin_mobile').val();
							if (UID == null || UID.length <= 0) {
								alert("Please Enter the id");
								return;
							}
							
							if (NAME == null || NAME.length <= 0) {
								alert("Please Enter Name");
								return;
							}
							if (EMAIL == null || EMAIL.length <= 0) {
								alert("Please Enter the email id");
								return;
							}
							
							if (MOBILE == null || MOBILE.length != 10) {
								alert("Please Enter The Valid Mobile N0.");
								return;
							}
							 $.ajax({
								type : 'POST',
								url : 'AjaxAdminController',
								contentType: 'application/json; charset=utf-8',
								data : 'UID=' + UID 
								        +'&NAME=' + NAME
										+ '&EMAIL=' + EMAIL 
										+ '&MOBILE='+ MOBILE
										+ '&Tag=AddNew'
										+'&role='+ Role_Id,
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
	
	
	$(document).ready(
			function() {
				$("#create_th").click(
						function() {
							var Role_Id = $("#Role_Id").val();
							var UID = $('#uid').val();
							var NAME = $('#name').val();
							var EMAIL = $('#email').val();
							var MOBILE = $('#mobile').val();
							var Region = $("#region").val();
							if (Region == null || Region.length <= 0) {
								alert("Please Select Region");
								return;
							}
							if (UID == null || UID.length <= 0) {
								alert("Please Enter the id");
								return;
							}
							
							if (NAME == null || NAME.length <= 0) {
								alert("Please Enter Name");
								return;
							}
							if (EMAIL == null || EMAIL.length <= 0) {
								alert("Please Enter the email id");
								return;
							}
							
							if (MOBILE == null || MOBILE.length != 10) {
								alert("Please Enter The Valid Mobile N0.");
								return;
							}
							 $.ajax({
								type : 'POST',
								url : 'AjaxAdminController',
								data : 'UID=' + UID 
								        +'&NAME=' + NAME
										+ '&EMAIL=' + EMAIL 
										+ '&MOBILE='+ MOBILE
										+'&role='+ Role_Id   
										+ '&Tag=AddNew'
										+ '&Region=' + Region,
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
	
	$(document).ready(
			function() {
				$("#create_admin_tso").click(
						function() {
							var Role_Id = $("#Role_Id").val();
							var State = $('#tso_stateId').val();
							var Distric = $('#tso_distric_id').val();
							var UID = $('#tso_uid').val();
							var NAME = $('#tso_name').val();
							var EMAIL = $('#tso_email').val();
							var MOBILE = $('#tso_mobile').val();
							if (State == null || State.length <= 0) {
								alert("Please Select State");
								return;
							}
							if (Distric == null || Distric.length <= 0) {
								alert("Please Select District");
								return;
							}
							if (UID == null || UID.length <= 0) {
								alert("Please Enter the id");
								return;
							}
							
							if (NAME == null || NAME.length <= 0) {
								alert("Please Enter Name");
								return;
							}
							if (EMAIL == null || EMAIL.length <= 0) {
								alert("Please Enter the email id");
								return;
							}
							
							if (MOBILE == null || MOBILE.length != 10) {
								alert("Please Enter The Valid Mobile N0.");
								return;
							}
							$.ajax({
								type : 'POST',
								url : 'AjaxAdminController',
								data : 'NAME=' + NAME +
								       '&State='+ State +
								       '&UID='+ UID +
								       '&Distric='+ Distric +
								       '&EMAIL='+ EMAIL +
								       '&role='+ Role_Id +
								       '&Tag=AddNew'+
								       '&MOBILE=' + MOBILE,
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
	$(document).ready(
			function() {
				$("#create_admin_rh").click(
						function() {
							var Role_Id = $("#Role_Id").val();
							var UID = $('#rh_uid').val();
							var NAME = $('#rh_name').val();
							var EMAIL = $('#rh_email').val();
							var MOBILE = $('#rh_mobile').val();
							var State = $('#rh_stateId').val();
							var Region = $('#rh_region_id').val();
							if (Region == null || Region.length <= 0) {
								alert("Please Select Region");
								return;
							}
							if (State == null || State.length <= 0) {
								alert("Please Select Region");
								return;
							}
							if (UID == null || State.length <= 0) {
								alert("Please Enter the id");
								return;
							}
							
							if (NAME == null || NAME.length <= 0) {
								alert("Please Enter Name");
								return;
							}
							if (EMAIL == null || EMAIL.length <= 0) {
								alert("Please Enter the email id");
								return;
							}
							
							if (MOBILE == null || MOBILE.length != 10) {
								alert("Please Enter The Valid Mobile N0.");
								return;
							}
							$.ajax({
								type : 'POST',
								url : 'AjaxAdminController',
								data : 'NAME=' + NAME +
								       '&State='+ State +
								       '&subRegion='+ Region +
								       '&EMAIL='+ EMAIL +
								       '&role='+ Role_Id +
								       '&UID='+ UID +
								       '&Tag=AddNew'+
								       '&MOBILE=' + MOBILE,
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
	
	function populateDistric(values) {
		$.ajax({
			type : 'POST',
			url : 'AjaxFilterDistric',
			data : 'StateId=' + values,
			success : function(data2) {
				$('#success').html(data2);
				$("#tso_distric_id").html(data2);
			},
			error : function(data) {
				alert('fail');
			}
		});
	}
	
	function populateStateRegion(values) {
		$.ajax({
			type : 'POST',
			url : 'AjaxFilterUserRegion',
			data : 'StateId=' + values,
			success : function(data2) {
				$('#success').html(data2);
				$("#rh_region_id").html(data2);
			},
			error : function(data) {
				alert('fail');
			}
		});
	}
	
	
</script>


<body>
	<div class="container">
		<div id="loginbox"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 admin_d col-sm-offset-2">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">
						<h3>Admin</h3>
					</div>
				</div>

				<div class="panel-body">
					<div class="form-group">
						<label for="inputPassword">Role<span style="color: red;">*</span></label> <select
							class="form-control input-sm" id="Role_Id"
							onchange="populateMainLayout(this.value)">
							<%
								for (int i = 0; i < listRole.size(); i++) {
							%>
							<option value=<%=listRole.get(i).getId()%>><%=listRole.get(i).getType()%></option>
							<%
								}
							%>
						</select>
					</div>
				</div>
				
				
				<!-- Will Visible for Admin Role  -->
				<div  class="panel-body" id="admin_layout">
					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>

					<div class="form-group">
						<label for="inputEmail">Login ID<span style="color: red;">*</span></label> <input type="email"
							class="form-control" id="admin_uid" placeholder="Login ID">
					</div>

					<div class="form-group">
						<label for="inputPassword">Name<span style="color: red;">*</span></label> <input required
							class="form-control" id="admin_name" placeholder="Name">
					</div>

					<div class="form-group">
						<label for="inputPassword">Email ID<span style="color: red;">*</span></label> <input type="email"
							class="form-control" id="admin_email" placeholder="email">
					</div>

					<div class="form-group">
						<label for="inputPassword">Mobile No.<span style="color: red;">*</span></label> <input type="number"
							class="form-control" id="admin_mobile" maxlength="10"
							placeholder="xxxxxxxxxx">
					</div>
					<div class="form-group">
						<div class="col-sm-12 controls">
							<a id="create_admin" class="btn btn-primary">Create Admin</a>
						</div>
					</div>
				</div>
				<!-- ********************************END************************************* -->
				

				<!-- Will Visible for TH Role  -->
				<div style="display: none" class="panel-body" id="th_layout">
					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>
					<div class="form-group">
						<label for="inputPassword">Region</label> <select
							class="form-control input-sm" id="region">
							<%
								getRegion();
								for (int i = 0; i < listRegion.size(); i++) {
							%>
							<option value=<%=listRegion.get(i).getId()%>><%=listRegion.get(i).getRegion()%></option>
							<%
								}
							%>
						</select>
					</div>

					<div class="form-group">
						<label for="inputEmail">Login ID</label> <input type="email"
							class="form-control" id="uid" placeholder="Login ID">
					</div>

					<div class="form-group">
						<label for="inputPassword">Name</label> <input required
							class="form-control" id="name" placeholder="Name">
					</div>

					<div class="form-group">
						<label for="inputPassword">Email ID</label> <input type="email"
							class="form-control" id="email" placeholder="email">
					</div>

					<div class="form-group">
						<label for="inputPassword">Mobile No.</label> <input type="number"
							class="form-control" id="mobile" maxlength="10"
							placeholder="xxxxxxxxxx">
					</div>
					<div class="form-group">
						<div class="col-sm-12 controls">
							<a id="create_th" class="btn btn-primary">Create Admin</a>
						</div>
					</div>
				</div>
				<!-- ********************************END************************************* -->





				<!-- Will Visible for TSO Role  -->
				<div style="display: none" class="panel-body" id="tso_layout">
					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>
					<div class="form-group">
						<label for="inputPassword">State</label> <select
							class="form-control input-sm" id="tso_stateId" onchange="populateDistric(this.value)" >
							<%
							for(int i=0;i<listState.size();i++)
							{
								%>
								<option value=<%=listState.get(i).getStateId()%>><%=listState.get(i).getStateName()%></option>
						   <%	}
							%>
						</select>
					</div>
					<div class="form-group">
						<label for="inputPassword">District</label> <select
							class="form-control input-sm" id="tso_distric_id">
							<option></option>
						</select>
					</div>
					<div class="form-group">
						<label for="inputEmail">Login ID</label> <input type="email"
							class="form-control" id="tso_uid" placeholder="Login ID">
					</div>

					<div class="form-group">
						<label for="inputPassword">Name</label> <input required
							class="form-control" id="tso_name" placeholder="Name">
					</div>

					<div class="form-group">
						<label for="inputPassword">Email ID</label> <input type="email"
							class="form-control" id="tso_email" placeholder="email">
					</div>

					<div class="form-group">
						<label for="inputPassword">Mobile No.</label> <input type="number"
							class="form-control" id="tso_mobile" maxlength="10"
							placeholder="xxxxxxxxxx">
					</div>
					<div class="form-group">
						<div class="col-sm-12 controls">
							<a id="create_admin_tso" class="btn btn-primary">Create Admin</a>
						</div>
					</div>
				</div>

				<!-- ********************************END************************************* -->


				<!-- Will Visible for RH Role  -->
				<div style="display: none" class="panel-body" id="rh_layout">
					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>
					<div class="form-group">
						<label for="inputPassword">State</label> <select
							class="form-control input-sm" id="rh_stateId" onchange="populateStateRegion(this.value)" >
							<%
							for(int i=0;i<listState.size();i++)
							{
								%>
								<option value=<%=listState.get(i).getStateId()%>><%=listState.get(i).getStateName()%></option>
						   <%	}
							%>
						</select>
					</div>

					<div class="form-group">
						<label for="inputPassword">Region</label> <select
							class="form-control input-sm" id="rh_region_id">
						</select>
					</div>
					<div class="form-group">
						<label for="inputEmail">Login ID</label> <input type="email"
							class="form-control" id="rh_uid" placeholder="Login ID">
					</div>

					<div class="form-group">
						<label for="inputPassword">Name</label> <input required
							class="form-control" id="rh_name" placeholder="Name">
					</div>

					<div class="form-group">
						<label for="inputPassword">Email ID</label> <input type="email"
							class="form-control" id="rh_email" placeholder="email">
					</div>

					<div class="form-group">
						<label for="inputPassword">Mobile No.</label> <input type="number"
							class="form-control" id="rh_mobile" maxlength="10"
							placeholder="xxxxxxxxxx">
					</div>
					<div class="form-group">
						<div class="col-sm-12 controls">
							<a id="create_admin_rh" class="btn btn-primary">Create Admin</a>
						</div>
					</div>
				</div>

				<!-- ********************************END************************************* -->

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