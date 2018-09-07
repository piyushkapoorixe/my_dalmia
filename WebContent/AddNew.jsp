<%@page import="com.utils.WebProjectLIstUtils"%>
<%@page import="com.model.StateModel"%>
<%@page import="com.model.DistricModel"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.webmodel.MasterQueryFeedback"%>
<%@page import="com.webmodel.UserQueryFeedback"%>
<%@page import="com.model.QueryModel"%>
<%@page import="com.webmodel.MasterRegionModel"%>
<%@page import="com.webmodel.MasterState"%>
<%@page import="com.constants.WebConstants"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.model.LoginModel"%>
<%@page import="com.webmodel.MasterCity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<jsp:include page="header.jsp"></jsp:include>
<%!Connection con;
	int Type;%>
<%
	Type = Integer.parseInt(PasswordEncryption.dycriptBase64(request.getParameter("id").getBytes()));
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
<script type='text/javascript' src="js/jquery.min.js"></script>
<script type='text/javascript' src="js/jquery.blockUI.js"></script>
<script type='text/javascript'>
$(document).ready(
		function() {
			$(document).ajaxStop($.unblockUI);
            $(document).ajaxStart(function () { $.blockUI({ message: '<div class="load_1"><img src="images/loading.gif" /><p>Please Wait...</p></div>' }); });
		});
		
		
function populateDistric(values) {
	$.ajax({
		type : 'POST',
		url : 'AjaxFilterDistric',
		data : 'StateId=' + values,
		success : function(data2) {
			$('#success').html(data2);
			$("#master_city_distric_select").html(data2);
		},
		error : function(data) {
			alert('fail');
		}
	});
}

$(document).ready(
		function() {
			$("#name_query_feedback_save").click(
					function() {
						var FeebckAndQuery = $('#name_query_feedback')
								.val();
						var FeebckAndQueryType = $(
								"#name_select_query_feedback option:selected")
								.text();
						var active = $(
								"#name_query_feedback_active").is(
								":checked") ? "true" : "false";

						$.ajax({
							type : 'POST',
							url : 'AjaxController',
							data : 'FeebckAndQueryType=' + FeebckAndQueryType + '&FeebckAndQuery=' + FeebckAndQuery
									+ '&Type=' +
<%=WebConstants.QueryFeedback%>
+ '&IsActive=' + active,
							success : function(data2) {
								$('#success').html(data2);
								$('#name_query_feedback').val('');
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
			$("#name_specialization_save")
					.click(
							function() {
								var Specialization = $('#name_specialization').val();
								var active = $("#name_specialization_active").is(
										":checked") ? "true" : "false";
								$.ajax({
									type : 'POST',
									url : 'AjaxController',
									data : {Specialization: Specialization, 
										Type: <%=WebConstants.Specialization%>, 
										IsActive: active},
									success : function(data2) {
										$('#success').html(data2);
										$('#name_specialization').val('');
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
			$("#user_region_submit")
					.click(
							function() {
								var UserRegion = $('#name_user_region').val();
								var state = $('#name_select_state').val();
								var active = $("#name_user_region_active").is( ":checked") ? "true" : "false";
								$.ajax({
									type : 'POST',
									url : 'AjaxController',
									data : 'UserRegion=' + UserRegion 
									+'&state=' + state 
									+ '&Type=' + <%=WebConstants.UserRegion%> + '&IsActive=' + active,
									success : function(data2) {
										$('#success').html(data2);
										$('#name_user_region').val('');
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
			$("#name_Qualification_save")
					.click(
							function() {
								var Qualification = $('#name_Qualification').val();
								var active = $("#name_Qualification_active").is(
										":checked") ? "true" : "false";
								$.ajax({
									type : 'POST',
									url : 'AjaxController',
									data : 'Qualification=' + Qualification + '&Type='
											+
<%=WebConstants.Qualification%>
+ '&IsActive=' + active,
									success : function(data2) {
										$('#success').html(data2);
										$('#name_Qualification').val('');
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
			$("#master_city_name_city_save").click(
					function() {
						var City = $('#master_city_name_city') .val();
						var Distric = $( "#master_city_distric_select") .val();
						var active = $( "#master_city_name_city_active").is( ":checked") ? "true" : "false";
						$.ajax({
							type : 'POST',
							url : 'AjaxController',
							data : 'Distric=' + Distric + '&City=' + City
									+ '&Type=' +
<%=WebConstants.City%>
+ '&IsActive=' + active,
							success : function(data2) {
								$('#success').html(data2);
								$('#master_city_name_city').val('');
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
				$("#name_state_for_select_region_save").click(
						function() {
							var State = $('#name_state_for_select_region')
									.val();
							var Region = $(
									"#name_select_region option:selected")
									.text();
							var active = $(
									"#name_state_for_select_region_active").is(
									":checked") ? "true" : "false";

							$.ajax({
								type : 'POST',
								url : 'AjaxController',
								data : 'State=' + State + '&Region=' + Region
										+ '&Type=' +
<%=WebConstants.State%>
	+ '&IsActive=' + active,
								success : function(data2) {
									$('#success').html(data2);
									$('#name_state_for_select_region').val('');
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
				$("#save_name_region").click(
						function() {
							var name_region = $('#name_region').val();
							var name_region_contact = $('#name_region_contact')
									.val();
							var active = $("#name_region_active")
									.is(":checked") ? "true" : "false";
							$.ajax({
								type : 'POST',
								url : 'AjaxController',
								data : 'Contact=' + name_region_contact
										+ '&Region=' + name_region + '&Type='
										+
<%=WebConstants.Region%>
	+ '&IsActive=' + active,
								success : function(data2) {
									$('#success').html(data2);
									$('#name_region').val('');
									$('#name_region_contact').val('');
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
				$("#save_profession")
						.click(
								function() {
									var profession = $('#name_profession')
											.val();
									var active = $("#profession_active").is(
											":checked") ? "true" : "false";
									$.ajax({
										type : 'POST',
										url : 'AjaxController',
										data : 'Value=' + profession + '&Type='
												+
<%=WebConstants.Profession%>
	+ '&IsActive=' + active,
										success : function(data2) {
											$('#success').html(data2);
											$('#name_profession').val('');
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
				$("#save_machine")
						.click(
								function() {
									var machine = $('#name_machine').val();
									var active = $("#machine_active").is(
											":checked") ? "true" : "false";
									$.ajax({
										type : 'POST',
										url : 'AjaxController',
										data : 'Value=' + machine + '&Type='
												+
<%=WebConstants.Machinary%>
	+ '&IsActive=' + active,
										success : function(data2) {
											$('#success').html(data2);
											$('#name_machine').val('');
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
				$("#save_manpower")
						.click(
								function() {
									var manpower = $('#name_manpower').val();
									var active = $("#manpower_active").is(
											":checked") ? "true" : "false";
									$.ajax({
										type : 'POST',
										url : 'AjaxController',
										data : 'Value=' + manpower + '&Type='
												+
<%=WebConstants.ManPower%>
	+ '&IsActive=' + active,
										success : function(data2) {
											$('#success').html(data2);
											$('#name_manpower').val('');
											alert(data2);
										},
										error : function(data) {
											alert('fail');
										}
									});
								});
			});
</script>
</head>
<body>
	<%
		switch (Type) {
		case WebConstants.City:
			ArrayList<MasterState> listState=WebProjectLIstUtils.getStateList();
	%>
	<div class="container">
		<table>
			<tr>
				<td><input type="submit"
					onclick="window.history.go(-1); return false;" value="Back"
					class="btn_inp_al" />

					<p></p></td>
			</tr>
		</table>
		<table>
			<tr>
			<td>
				<h4>Add City</h4>
			</td>
		</tr>
		</table>
		<table class="table table-striped table-bordered table-condensed">
		
		<tr>
				<td>State<span style="color: red;">*</span></td>
				<td><select id="master_city_state_select"  onchange="populateDistric(this.value)">
						<%
				for(int i=0;i<listState.size();i++)
				{
					%>
					<option value=<%=listState.get(i).getStateId() %>>
					<%=listState.get(i).getStateName()%>
					</option>
						<%
				}
				%>
				</select></td>
			</tr>
			<tr>
				<td>District<span style="color: red;">*</span></td>
				<td><select id="master_city_distric_select">
				</select></td>
			</tr>
			<tr>
				<td>City<span style="color: red;">*</span></td>
				<td><input type="text" id="master_city_name_city" /></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="master_city_name_city_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
					checked="checked" /><label for="master_city_name_city_active">Active</label>
					<input id="master_city_name_city_deactive" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive" /><label
					for="master_city_name_city_deactive">Deactive</label></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save" id="master_city_name_city_save"
					class="btn_inp_al" /></td>
			</tr>
		</table>
	</div>

	<%
		break;
		case WebConstants.Machinary:
			
	%>

	<div class="container">
		<table>
			<tr>
				<td><input type="submit"
					onclick="window.history.go(-1); return false;" value="Back"
					class="btn_inp_al" />

					<p></p></td>
			</tr>
		</table>
		<table>
			<tr>
				<td>
					<h4>Add Machinary</h4>
				</td>
			</tr>
		</table>
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>Machinaries<span style="color: red;">*</span></td>
				<td><input type="text" required id="name_machine" /></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="machine_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" checked="checked" /><label
					for="machine_active">Active</label> <input id="machine_deactive"
					type="radio" name="ctl00$ContentPlaceHolder1$status" /><label
					for="machine_deactive">Deactive</label></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save"
					class="btn_inp_al" id="save_machine" /></td>
			</tr>
		</table>
	</div>
	<%
		break;
		case WebConstants.ManPower:
	%>
	<div class="container">
		<table>
			<tr>
				<td><input type="submit"
					onclick="window.history.go(-1); return false;" value="Back"
					class="btn_inp_al" />

					<p></p></td>
			</tr>
		</table>
		<table>
			<tr>
			<td>
				<h4>Add ManPower</h4>
			</td>
		</tr>
		</table>
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>ManPower<span style="color: red;">*</span></td>
				<td><input type="text" id="name_manpower" /></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="manpower_active" type="radio" checked="checked" /><label
					for="manpower_active">Active</label> <input id="manpower_deactive"
					type="radio" name="ctl00$ContentPlaceHolder1$status" /><label
					for="manpower_deactive">Deactive</label></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" id="save_manpower"
					value="Save" class="btn_inp_al" /></td>
			</tr>
		</table>
	</div>
	<%
		break;
		case WebConstants.Profession:
	%>
	<div class="container">
		<table>
			<tr>
				<td><input type="submit"
					onclick="window.history.go(-1); return false;" value="Back"
					class="btn_inp_al" />

					<p></p></td>
			</tr>
		</table>
		<table>
			<tr>
				<td>
					<h4>Add Profession</h4>
				</td>
			</tr>
		</table>
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>Profession<span style="color: red;">*</span></td>
				<td><input type="text" id=name_profession /></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="profession_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
					checked="checked" /><label for="profession_active">Active</label>
					<input id="profession_deactive" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive" /><label
					for="profession_deactive">Deactive</label></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save"
					class="btn_inp_al" id="save_profession" /></td>
			</tr>
		</table>
	</div>
	<%
		break;
		case WebConstants.Qualification:
	%>
	<div class="container">
		<table>
			<tr>
				<td><input type="submit"
					onclick="window.history.go(-1); return false;" value="Back"
					class="btn_inp_al" />

					<p></p></td>
			</tr>
		</table>
		<table>
			<tr>
				<td>
					<h4>Add Qualification</h4>
				</td>
			</tr>
		</table>
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>Qualification<span style="color: red;">*</span></td>
				<td><input type="text" id="name_Qualification" /></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="name_Qualification_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
					checked="checked" /><label for="name_Qualification_active">Active</label>
					<input id="name_Qualification_deactive" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive" /><label
					for="name_Qualification_deactive">Deactive</label></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save" id="name_Qualification_save"
					class="btn_inp_al" /></td>
			</tr>
		</table>
	</div>
	<%
		break;
		case WebConstants.QueryFeedback:
	%>

	<div class="container">
		<table>
			<tr>
				<td><input type="submit"
					onclick="window.history.go(-1); return false;" value="Back"
					class="btn_inp_al" />

					<p></p></td>
			</tr>
		</table>
		<table>
			<tr>
				<td>
					<h4>Add Query/Feedback</h4>
				</td>
			</tr>
		</table>
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>Type</td>
				<td><select id="name_select_query_feedback">
						<option value=1>Query</option>
						<option value=2>Feedback</option>
				</select></td>
			</tr>
			<tr>
				<td>Query/Feedback<span style="color: red;">*</span></td>
				<td>
				<textarea id="name_query_feedback" type="text" style="width:100%; min-height:70px;"></textarea>
				</td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="name_query_feedback_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
					checked="checked" /><label for="name_query_feedback_active">Active</label>
					<input id="ContentPlaceHolder1_rdBtnDeactive" type="radio"
					name="name_query_feedback_deactive" value="rdBtnDeactive" /><label
					for="name_query_feedback_deactive">Deactive</label></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save" id="name_query_feedback_save"
					class="btn_inp_al" /></td>
			</tr>
		</table>
	</div>
	<%
		break;
		case WebConstants.Region:
	%>
	<div class="container">
		<table>
			<tr>
				<td><input type="submit"
					onclick="window.history.go(-1); return false;" value="Back"
					class="btn_inp_al" />

					<p></p></td>
			</tr>
		</table>
		<table>
			<tr>
				<td>
					<h4>Add Zone</h4>
				</td>
			</tr>
		</table>
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>Zone Name<span style="color: red;">*</span></td>
				<td><input type="text" id="name_region" /></td>
			</tr>
			<tr>
				<td>Contact No<span style="color: red;">*</span></td>
				<td><input maxlength="12" id="name_region_contact" /></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="name_region_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
					checked="checked" /><label for="name_region_active">Active</label>
					<input id="name_region_deactive" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive" /><label
					for=""name_region_deactive"">Deactive</label></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save"
					class="btn_inp_al" id="save_name_region" /></td>
			</tr>
		</table>
	</div>
	<%
		break;
		case WebConstants.Specialization:
	%>
	<div class="container">
		<table>
			<tr>
				<td><input type="submit"
					onclick="window.history.go(-1); return false;" value="Back"
					class="btn_inp_al" />

					<p></p></td>
			</tr>
		</table>
		<table>
			<tr>
				<td>
					<h4>Add Specialization</h4>
				</td>
			</tr>
		</table>		
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>Specialization<span style="color: red;">*</span></td>
				<td><input type="text" id="name_specialization" /></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="name_specialization_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
					checked="checked" /><label for="name_specialization_active">Active</label>
					<input id="name_specialization_deactive" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive" /><label
					for="name_specialization_deactive">Deactive</label></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save" id="name_specialization_save"
					class="btn_inp_al" /></td>
			</tr>
		</table>
	</div>
	<%
		break;
		case WebConstants.State:
			con = DBConnectionProvider.getCon();
			ArrayList<MasterRegionModel> listRegion = null;
			try {
				Statement stmt = con.createStatement();
				String sql = "Select * from master_stateregion where IsActive='1'";
				ResultSet rs = stmt.executeQuery(sql);
				listRegion = new ArrayList<MasterRegionModel>();
				while (rs.next()) {
					MasterRegionModel item = new MasterRegionModel();
					item.setId(rs.getString("id"));
					item.setRegion(rs.getString("Region"));
					item.setContactNumber(rs.getString("ContactNo"));
					item.setIsActive(rs.getString("IsActive"));
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
	%>

	<div class="container">
		<table>
			<tr>
				<td><input type="submit"
					onclick="window.history.go(-1); return false;" value="Back"
					class="btn_inp_al" />

					<p></p></td>
			</tr>
		</table>
		<table>
			<tr>
				<td>
					<h4>Add State</h4>
				</td>
			</tr>
		</table>
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>State<span style="color: red;">*</span></td>
				<td><input type="text" id="name_state_for_select_region" /></td>

			</tr>
			<tr>
				<td>Zone<span style="color: red;">*</span></td>
				<td><select id="name_select_region">
						<%
							for (int i = 0; i < listRegion.size(); i++) {
						%>
						<option value=<%=i%>><%=listRegion.get(i).getRegion()%></option>
						<%
							}
						%>

				</select></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="name_state_for_select_region_active"
					type="radio" name="ctl00$ContentPlaceHolder1$status"
					value="rdBtnActive" checked="checked" /><label
					for="name_state_for_select_region_active">Active</label> <input
					id="name_state_for_select_region_deactive" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive" /><label
					for="name_state_for_select_region_deactive">Deactive</label></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save"
					class="btn_inp_al" id="name_state_for_select_region_save" /></td>
			</tr>
		</table>
	</div>
	<%
		break;
		case WebConstants.UserRegion:
			con = DBConnectionProvider.getCon();
			ArrayList<MasterState> listState2 = null;
			try {
				Statement stmt = con.createStatement();
				String sql = "Select * from master_state where Is_Active='1'";
				ResultSet rs = stmt.executeQuery(sql);
				listState2 = new ArrayList<MasterState>();
				while (rs.next()) {
					MasterState item = new MasterState();
					item.setStateId(rs.getString("State_Id"));
					item.setStateName(rs.getString("StateName"));
					listState2.add(item);
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
	%>
	<div class="container">
		<table>
			<tr>
				<td><input type="submit"
					onclick="window.history.go(-1); return false;" value="Back"
					class="btn_inp_al" />

					<p></p></td>
			</tr>
		</table>
		<table>
			<tr>
				<td>
					<h4>Add Region</h4>
				</td>
			</tr>
		</table>
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>State<span style="color: red;">*</span></td>
				<td><select id="name_select_state">
						<%
							for (int i = 0; i < listState2.size(); i++) {
						%>
						<option value=<%=listState2.get(i).getStateId()%>><%=listState2.get(i).getStateName()%></option>
						<%
							}
						%>
				</select></td>
			</tr>
			<tr>
				<td>Region<span style="color: red;">*</span></td>
				<td><input type="text" id="name_user_region" /></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="name_user_region_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
					checked="checked" /><label for="name_user_region_active">Active</label>
					<input id="name_user_region_deactive" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive" /><label
					for="name_user_region_deactive">Deactive</label></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save" id="user_region_submit"
					class="btn_inp_al" /></td>
			</tr>
		</table>
	</div>
	<%
		break;
		}
	%>
</body>
</html>