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
<%!
Connection con=null;
	String Id;
	String Is_Active = null;
	String CityId=null;
	int Type;%>
<%
	Id = PasswordEncryption.dycriptBase64(request.getParameter("id").getBytes());
	Type = Integer.parseInt(PasswordEncryption.dycriptBase64(request.getParameter("Type").getBytes()));
	
	if(request.getParameter("CityId")!=null)
	{
		CityId=PasswordEncryption.dycriptBase64(request.getParameter("CityId").getBytes());	
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
<link rel="stylesheet" href="css/bootstrap.min.css" />

</head>
<script type='text/javascript' src="js/jquery.min.js"></script>
<script type='text/javascript' src="js/jquery.blockUI.js"></script>
<script type='text/javascript'>
$(document).ready(
		function() {
			$(document).ajaxStop($.unblockUI);
            $(document).ajaxStart(function () { $.blockUI({ message: '<div class="load_1"><img src="images/loading.gif" /><p>Please Wait...</p></div>' }); });
		});

$(document).ready(
		function() {
			$("#master_city_name_save").click(
					function() {
						var CityName = $('#master_city_name').val();
						var StateName = $("#select_master_city_name option:selected")
						.text();
						var active = $("#master_city_name_active").is(":checked") ? "true" : "false";
						$.ajax({
							type : 'POST',
							url : 'AjaxControllerForEdition',
							data : 'StateName=' + StateName + 
							       '&IsActive=' + active +
							       '&CityName=' + CityName +
							       '&CityId=' + <%=CityId%> +
							      '&Type=' + <%=WebConstants.City%> ,
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
			$("#name_select_master_state_save").click(
					function() {
						var StateName = $('#name_select_master_state').val();
						var RegionName = $("#select_value_master_state option:selected")
						.text();
						var active = $("#name_select_master_state_active").is(":checked") ? "true" : "false";
						var dlt = $("#name_select_master_state_delete").is(":checked") ? "true" : "false";
						$.ajax({
							type : 'POST',
							url : 'AjaxControllerForEdition',
							data : 'StateName=' + StateName + 
							       '&IsActive=' + active +
							       '&RegionName=' + RegionName +
							       '&deleteStatus=' + dlt +
							       '&StateNameId=' + <%=Id%> +
							       '&Type=' + <%=WebConstants.State%> ,
							success : function(data2) {
								$('#success').html(data2);
								alert(data2);
								if(data2.indexOf("Deleted")>0){
									window.location.href = "Master_State.jsp";
								}
							},
							error : function(data) {
								alert('fail');
							}
						});
					});
		});

$(document).ready(
		function() {
			$("#name_query_feedback_save").click(
					function() {
						
						var QueryFeedback = $('#name_query_feedback')
								.val();
						var active = $(
								"#name_query_feedback_active").is(
								":checked") ? "true" : "false";
						var qType = $("#select_feedback_type option:selected").text();
						var dlt = $("#name_query_feedback_delete").is(":checked") ? "true" : "false";
						$.ajax({
							type : 'POST',
							url : 'AjaxControllerForEdition',
							data : {QueryFeedback: QueryFeedback, 
								IsActive: active, 
								deleteStatus: dlt, 
								QueryFeedbackId: <%=Id%>,
								QTYPE: qType, 
								Type: <%=WebConstants.QueryFeedback%>},
							success : function(data2) {
								$('#success').html(data2);
								alert(data2);
								if(data2.indexOf("Deleted")>0){
									window.location.href = "Master_QueryFeedback.jsp";
								}
							},
							error : function(data) {
								alert('fail');
							}
						});
					});
		});


$(document).ready(
		function() {
			$("#name_specialization_save").click(
					function() {
						
						var Specialization = $('#name_specialization')
								.val();
						var active = $(
								"#name_specialization_active").is(
								":checked") ? "true" : "false";
						
						var dlt = $(
						"#name_specialization_delete").is(
						":checked") ? "true" : "false";
						
						$.ajax({
							type : 'POST',
							url : 'AjaxControllerForEdition',
							data : {Specialization: Specialization, 
								IsActive : active, 
								deleteStatus: dlt, 
								SpecializationId: <%=Id%>, 
								Type: <%=WebConstants.Specialization%>},
							success : function(data2) {
								$('#success').html(data2);
								alert(data2);
								if(data2.indexOf("Deleted")>0){
									window.location.href = "MAster_Specialization.jsp";
								}
							},
							error : function(data) {
								alert('fail');
							}
						});
					});
		});


$(document).ready(
		function() {
			$("#name_user_region_save").click(
					function() {
						var UserRegion = $('#name_user_region').val();
						var state = $('#select_value_state').val();
						var active = $("#name_user_region_active").is(":checked") ? "true" : "false";
						
						var dlt = $(
						"#name_user_region_delete").is(
						":checked") ? "true" : "false";
						
						$.ajax({
							type : 'POST',
							url : 'AjaxControllerForEdition',
							data : 'UserRegion=' + UserRegion + 
							       '&IsActive=' + active +
							       '&state=' + state +
							       '&deleteStatus=' + dlt +
							       '&UserRegionId=' + <%=Id%> +
							       '&Type=' + <%=WebConstants.UserRegion%> ,
							success : function(data2) {
								$('#success').html(data2);
								alert(data2);
								if(data2.indexOf("Deleted")>0){
									window.location.href = "Master_UsersRegion.jsp";
								}
							},
							error : function(data) {
								alert('fail');
							}
						});
					});
		});
$(document).ready(
		function() {
			$("#name_qualification_save").click(
					function() {
						
						var Qualification = $('#name_qualification')
								.val();
						
						var active = $(
								"#name_qualification_active").is(
								":checked") ? "true" : "false";
						
						var dlt = $(
						"#name_qualification_delete").is(
						":checked") ? "true" : "false";
						
						$.ajax({
							type : 'POST',
							url : 'AjaxControllerForEdition',
							data : 'Qualification=' + Qualification + 
							       '&IsActive=' + active +
							       '&deleteStatus=' + dlt +
							       '&QualificationId=' + <%=Id%> +
							       '&Type=' + <%=WebConstants.Qualification%> ,
							success : function(data2) {
								$('#success').html(data2);
								alert(data2);
								if(data2.indexOf("Deleted")>0){
									window.location.href = "Master_Qualification.jsp";
								}
							},
							error : function(data) {
								alert('fail');
							}
						});
					});
		});


$(document).ready(
		function() {
			$("#name_profession_save").click(
					function() {
						var Profession = $('#name_profession')
								.val();
						var active = $(
								"#name_profession_active").is(
								":checked") ? "true" : "false";
						
						var dlt = $(
						"#name_profession_delete").is(
						":checked") ? "true" : "false";
						
						$.ajax({
							type : 'POST',
							url : 'AjaxControllerForEdition',
							data : 'Profession=' + Profession + 
							       '&IsActive=' + active +
							       '&deleteStatus=' + dlt +
							       '&ProfessionId=' + <%=Id%> +
							       '&Type=' + <%=WebConstants.Profession%> ,
							success : function(data2) {
								$('#success').html(data2);
								alert(data2);
								if(data2.indexOf("Deleted")>0){
									window.location.href = "Master_Profession.jsp";
								}
							},
							error : function(data) {
								alert('fail');
							}
						});
					});
		});


$(document).ready(
		function() {
			$("#name_manpower_save").click(
					function() {
						
						var Manpower = $('#name_manpower')
								.val();
						
						var active = $(
								"#name_manpower_active").is(
								":checked") ? "true" : "false";
						
						var dlt = $(
						"#name_manpower_delete").is(
						":checked") ? "true" : "false";
						
						$.ajax({
							type : 'POST',
							url : 'AjaxControllerForEdition',
							data : 'Manpower=' + Manpower + 
							       '&IsActive=' + active + 
							       '&deleteStatus=' + dlt +
							       '&ManpowerId=' + <%=Id%> +
							       '&Type=' + <%=WebConstants.ManPower%> ,
							success : function(data2) {
								$('#success').html(data2);
								alert(data2);
								if(data2.indexOf("Deleted")>0){
									window.location.href = "Master_Manpower.jsp";
								}
							},
							error : function(data) {
								alert('fail');
							}
						});
					});
		});



$(document).ready(
		function() {
			$("#name_machinary_save").click(
					function() {
						
						var Machine = $('#name_machinary')
								.val();
						
						var active = $(
								"#name_machinary_active").is(
								":checked") ? "true" : "false";
						
						var dlt = $(
								"#name_machinary_delete").is(
								":checked") ? "true" : "false";						
						
						$.ajax({
							type : 'POST',
							url : 'AjaxControllerForEdition',
							data : 'Machine=' + Machine + 
							       '&IsActive=' + active +
							       '&deleteStatus=' + dlt +
							       '&MachineId=' + <%=Id%> +
							       '&Type=' + <%=WebConstants.Machinary%> ,
							success : function(data2) {
								$('#success').html(data2);
								alert(data2);
								if(data2.indexOf("Deleted")>0){
									window.location.href = "Master_Machineries.jsp";
								}
							},
							error : function(data) {
								alert('fail');
							}
						});
					});
		});

$(document).ready(
		function() {
			$("#name_region_save").click(
					function() {
						
						var RegionId = $(this).attr('data-id');
						var Region = $('#name_region')
								.val();
						
						var Conatct = $('#name_contact')
						.val();
						var active = $(
								"#name_region_active").is(
								":checked") ? "true" : "false";
						
						var dlt = $(
						"#name_region_delete").is(
						":checked") ? "true" : "false";
						
						$.ajax({
							type : 'POST',
							url : 'AjaxControllerForEdition',
							data : 'Region=' + Region + 
							       '&Conatct=' + Conatct +
							       '&RegionId=' + RegionId +
							       '&deleteStatus=' + dlt +
							       '&Type=' + <%=WebConstants.Region%> +
							       '&IsActive=' + active,
							success : function(data2) {
								$('#success').html(data2);
								alert(data2);
								if(data2.indexOf("Deleted")>0){
									window.location.href = "Master_Region.jsp";
								}
							},
							error : function(data) {
								alert('fail');
							}
						});
					});
		});
</script>
<body>

	<%
		switch (Type) {
		case WebConstants.City:
			con = DBConnectionProvider.getCon();
			String CityId = PasswordEncryption.dycriptBase64(request.getParameter("CityId").getBytes());
			String CityName = PasswordEncryption.dycriptBase64(request.getParameter("CityName").getBytes());
			ArrayList<MasterState> stateList = null;
			try {
				PreparedStatement ps = con
						.prepareStatement("SELECT State_Id,StateName  FROM master_state where Is_Active='1'");
				ResultSet rs = ps.executeQuery();
				stateList = new ArrayList<MasterState>();
				while (rs.next()) {
					MasterState item = new MasterState();
					item.setStateId(rs.getString("State_Id"));
					item.setStateName(rs.getString("StateName"));
					stateList.add(item);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			try {
				PreparedStatement ps = con.prepareStatement("SELECT Is_Active FROM master_city where CityId=?");
				ps.setString(1, CityId);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					Is_Active = rs.getString("Is_Active");
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
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>State</td>
				<td><select name="ctl00$ContentPlaceHolder1$ddlState"
					id="select_master_city_name" id="ContentPlaceHolder1_ddlState">
						<%
							for (int i = 0; i < stateList.size(); i++) {
						%>
						<option <%if (Id.equals(stateList.get(i).getStateId())) {%>
							selected="" <%}%> value=<%=i%>><%=stateList.get(i).getStateName()%></option>
						<%
							}
						%>
				</select>
			</tr>
			<tr>
				<td>City</td>
				<td><input name="ctl00$ContentPlaceHolder1$txtCity" type="text"
					id="master_city_name" value="<%out.println(CityName);%>"
					 /></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="master_city_name_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
					checked="checked" /><label for="master_city_name_active">Active</label>
					<input id="master_city_name_deactive" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive"
					<%if (Is_Active.equals("0")) {%> checked="checked" <%}%> /><label
					for="master_city_name_deactive">Deactive</label></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save"
					id="master_city_name_save" class="btn_inp_al"
					onclick="addNewPage();" /> <input type="submit"
					onclick="window.history.go(-1); return false;" value="Back"
					class="btn_inp_al" /></td>
			</tr>
		</table>
	</div>

	<%
		break;
		case WebConstants.Machinary:
			String Machinary = null;
			con = DBConnectionProvider.getCon();
			try {
				PreparedStatement ps = con.prepareStatement(
						"SELECT Is_Active,Machineries FROM master_machineries  where Machineries_Id=?");
				ps.setString(1, Id);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					Machinary = rs.getString("Machineries");
					Is_Active = rs.getString("Is_Active");

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
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>Machinaries</td>
				<td><input id="name_machinary" type="text"
					value="<%=Machinary%>" /></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="name_machinary_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
					checked="checked" /><label for="name_machinary_active">Active</label>
					<input id="name_machinary_deactive" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive"
					<%if (Is_Active.equals("0")) {%> checked="checked" <%}%> /><label
					for="name_machinary_deactive">Deactive</label>
					<input id="name_machinary_delete" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDelete" /><label
					for="name_machinary_delete">Delete</label>
					</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save"
					id="name_machinary_save" class="btn_inp_al" onclick="addNewPage();" /></td>
			</tr>
		</table>

	</div>
	<%
		break;
		case WebConstants.ManPower:
			String Manpower = null;
			con = DBConnectionProvider.getCon();
			try {
				PreparedStatement ps = con
						.prepareStatement("SELECT Is_Active ,Manpower FROM master_manpower  where ManpowerId=?");
				ps.setString(1, Id);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					Manpower = rs.getString("Manpower");
					Is_Active = rs.getString("Is_Active");
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
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>ManPower</td>
				<td><input id="name_manpower" type="text" value="<%=Manpower%>" /></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="name_manpower_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
					checked="checked" /><label for="name_manpower_active">Active</label>
					<input id="name_manpower_deactive" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive"
					<%if (Is_Active.equals("0")) {%> checked="checked" <%}%> /> <label
					for="name_manpower_deactive">Deactive</label>
					<input id="name_manpower_delete" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDelete"/> <label
					for="name_manpower_delete">Delete</label>
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save"
					id="name_manpower_save" class="btn_inp_al" /> </td>
			</tr>
		</table>

	</div>
	<%
		break;
		case WebConstants.Profession:
			String Profession = null;
			con = DBConnectionProvider.getCon();
			try {
				PreparedStatement ps = con.prepareStatement(
						"SELECT is_Active,Profession FROM master_profession  where profession_Id=?");
				ps.setString(1, Id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					Profession = rs.getString("Profession");
					Is_Active = rs.getString("is_Active");
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
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>Profession</td>
				<td><input type="text" id="name_profession"
					value="<%=Profession%>" /></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="name_profession_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
					checked="checked" /><label for="name_profession_active">Active</label>
					<input id="name_profession_deactive" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive"
					<%if (Is_Active.equals("0")) {%> checked="checked" <%}%> /><label
					for="name_profession_deactive">Deactive</label>
					<input id="name_profession_delete" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDelete"/>
					<label for="name_profession_delete">Delete</label>
					</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save"
					id="name_profession_save" class="btn_inp_al" /></td>
			</tr>
		</table>

	</div>
	<%
		break;
		case WebConstants.Qualification:

			String Qualification = null;
			con = DBConnectionProvider.getCon();
			try {
				PreparedStatement ps = con.prepareStatement(
						"SELECT Is_Active,Qualification FROM master_qualification  where Qualification_Id=?");
				ps.setString(1, Id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					Qualification = rs.getString("Qualification");
					Is_Active = rs.getString("Is_Active");
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
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>Qualification</td>
				<td><input type="text" id="name_qualification"
					value="<%=Qualification%>" /></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="name_qualification_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
					checked="checked" /><label for="name_qualification_active">Active</label>
					<input id="name_qualification_deactive" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive"
					<%if (Is_Active.equals("0")) {%> checked="checked" <%}%> /><label
					for="name_qualification_deactive">Deactive</label>
					<input id="name_qualification_delete" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDelete"/>
					<label for="name_qualification_delete">Delete</label></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save"
					id="name_qualification_save" class="btn_inp_al" />
				</td>
			</tr>
		</table>

	</div>
	<%
		break;
		case WebConstants.QueryFeedback:

			String QueryFeedback = null;
			String QueryType = null;
			con = DBConnectionProvider.getCon();
			try {
				PreparedStatement ps = con
						.prepareStatement("SELECT IsActive,Query,Type FROM master_queryfeedback  where id=?");
				ps.setString(1, Id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					QueryFeedback = rs.getString("Query");
					QueryType = rs.getString("Type");
					Is_Active = rs.getString("IsActive");
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
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>Type</td>
				<td><select id="select_feedback_type">
						<option <%if (QueryType.equals("Q")) {%>
							selected<%}%> value=1>Query</option>
						<option <%if (QueryType.equals("F")) {%>
							selected<%}%> value=2>Feedback</option>
				</select></td>
			</tr>
			<tr>
				<td>Query/Feedback</td>
				<td>
				
				<textarea id="name_query_feedback"  id="ContentPlaceHolder1_txtCity" style="width:100%; min-height:70px;"><%=QueryFeedback%></textarea>
				
				</td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="name_query_feedback_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
					checked="checked" /><label for="name_query_feedback_active">Active</label>
					<input id="name_query_feedback_deactive" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive"
					<%if (Is_Active.equals("0")) {%> checked="checked" <%}%> /><label
					for="name_query_feedback_deactive">Deactive</label>
					<input id="name_query_feedback_delete" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDelete"/>
					<label for="name_query_feedback_delete">Delete</label>
					</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save"
					id="name_query_feedback_save" class="btn_inp_al" /></td>
			</tr>
		</table>
	</div>
	<%
		break;
		case WebConstants.Region:
			String RegionValue = null;
			String MobileValue = null;
			String RegionId = null;
			con = DBConnectionProvider.getCon();
			try {
				PreparedStatement ps = con.prepareStatement(
						"SELECT id,IsActive,Region,ContactNo FROM master_stateregion  where id=?");
				ps.setString(1, Id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					RegionValue = rs.getString("Region");
					MobileValue = rs.getString("ContactNo");
					Is_Active = rs.getString("isActive");
					RegionId = rs.getString("id");
				}

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
		<section>
			<div class="col-lg-12">
			<input type="submit" onclick="window.history.go(-1); return false;"
					value="Back" class="btn_inp_al" />
					<p></p>
				<div class="details">
					<h4>Edit Zone</h4>
					<table class="table table-striped table-bordered table-condensed">
						<tr>
							<td>Zone Name</td>
							<td><input id="name_region" type="text" value=<%=RegionValue%> /></td>
						</tr>
						<tr>
							<td>Contact No</td>
							<td><input type="text" id="name_contact" value=<%=MobileValue%>
								maxlength="12" /></td>
						</tr>
						<tr>
							<td>Status</td>
							<td><input id="name_region_active" type="radio"
								name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
								checked="checked" /> <label for="name_region_active">Active</label>
			
								<input id="name_region_deactive" type="radio"
								name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive"
								<%if (Is_Active.equals("0")) {%> checked="checked" <%}%> /><label
								for="name_region_deactive">Deactive</label>
								
								<input id="name_region_delete" type="radio"
								name="ctl00$ContentPlaceHolder1$status" value="rdBtnDelete"/>
								<label for="name_region_delete">Delete</label>
							</td>
						</tr>
						<tr>
							<td colspan="2"><input type="submit" value="Save"
								id="name_region_save" class="btn_inp_al" data-id="<%=RegionId%>" />
								</td>
						</tr>
					</table>
				</div>
			</div>
		</section>
	</div>
	<%
		break;
		case WebConstants.Specialization:
			String Specialization = null;
			con = DBConnectionProvider.getCon();
			try {
				PreparedStatement ps = con.prepareStatement(
						"SELECT Is_Active,Specialization FROM master_specialization  where Specialization_Id=?");
				ps.setString(1, Id);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					Specialization = rs.getString("Specialization");
					Is_Active = rs.getString("Is_Active");
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
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>Specialization</td>
				<td><input id="name_specialization" type="text"
					id="ContentPlaceHolder1_txtManpower" value="<%=Specialization%>" /></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="name_specialization_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
					checked="checked" /><label for="name_specialization_active">Active</label>
					<input id="name_specialization_deactive" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive"
					<%if (Is_Active.equals("0")) {%> checked="checked" <%}%> /><label
					for="name_specialization_deactive">Deactive</label>
					<input id="name_specialization_delete" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDelete"/>
					<label for="name_specialization_delete">Delete</label>
					</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save"
					id="name_specialization_save" class="btn_inp_al" />
					</td>
			</tr>
		</table>

	</div>
	<%
		break;
		case WebConstants.State:
			String R_Id = PasswordEncryption.dycriptBase64(request.getParameter("RegionId").getBytes());
			String StateName = null;
			con = DBConnectionProvider.getCon();
			ArrayList<MasterRegionModel> regionList = null;
			try {
				PreparedStatement ps = con
						.prepareStatement("SELECT Region,id  FROM master_stateregion where IsActive='1'");
				ResultSet rs = ps.executeQuery();
				regionList = new ArrayList<MasterRegionModel>();
				while (rs.next()) {
					MasterRegionModel item = new MasterRegionModel();
					item.setRegion(rs.getString("Region"));
					item.setId(rs.getString("id"));
					regionList.add(item);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				PreparedStatement ps = con.prepareStatement(
						"SELECT Is_Active,StateName  FROM master_state where State_Id='" + Id + "'");
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					StateName = rs.getString("StateName");
					Is_Active = rs.getString("Is_Active");
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
		<section>
			<div class="col-lg-12">
				<input type="submit" onclick="window.history.go(-1); return false;"
					value="Back" class="btn_inp_al" />
				<h4>Edit State</h4>
				<div class="details">
					<table class="table table-striped table-bordered table-condensed">
						<tr>
							<td>State<span style="color: red;">*</span></td>
							<td><input name="ctl00$ContentPlaceHolder1$txtCity"
								type="text" id="name_select_master_state" value="<%=StateName%>"
								id="ContentPlaceHolder1_txtCity" /></td>
						</tr>
						<tr>
							<td>Zone<span style="color: red;">*</span></td>
							<td><select name="ctl00$ContentPlaceHolder1$ddlState"
								id="select_value_master_state">
									<%
										for (int i = 0; i < regionList.size(); i++) {
									%>
									<option <%if (R_Id.equals(regionList.get(i).getId())) {%>
										selected="" <%}%> value=<%=i%>><%=regionList.get(i).getRegion()%></option>
									<%
										}
									%>
							</select></td>
						</tr>
						<tr>
							<td>Status</td>
							<td><input id="name_select_master_state_active" type="radio"
								name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
								checked="checked" /><label
								for="name_select_master_state_active">Active</label> <input
								id="name_select_master_state_deactive" type="radio"
								name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive"
								<%if (Is_Active.equals("0")) {%> checked="checked" <%}%> /><label
								for="name_select_master_state_deactive">Deactive</label>
								<input id="name_select_master_state_delete" type="radio"
								name="ctl00$ContentPlaceHolder1$status" value="rdBtnDelete"/><label
								for="name_select_master_state_delete">Delete</label></td>
						</tr>
						<tr>
							<td colspan="2"><input type="submit" value="Save"
								id="name_select_master_state_save" class="btn_inp_al" />
								</td>
						</tr>
					</table>
				</div>
			</div>
		</section>
	</div>
	<%
		break;
		case WebConstants.UserRegion:
			String Region = null;
			String StateId = null;
			con = DBConnectionProvider.getCon();
			try {
				PreparedStatement ps = con .prepareStatement("SELECT Is_Active,Region,StateId FROM master_user_region  where Region_id=?");
				ps.setString(1, Id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					Region = rs.getString("Region");
					StateId = rs.getString("StateId");
					Is_Active = rs.getString("Is_Active");
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ArrayList<MasterState> stateList2 = null;
			try {
				PreparedStatement ps = con .prepareStatement("Select * from master_state where Is_Active='1'");
				ResultSet rs = ps.executeQuery();
				stateList2 = new ArrayList<MasterState>();
				while (rs.next()) {
					MasterState item = new MasterState();
					item.setStateId(rs.getString("State_Id"));
					item.setStateName(rs.getString("StateName"));
					stateList2.add(item);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
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
			<tr>
				<td>
					<h4>Edit Region</h4>
				</td>
			</tr>
		</table>
		<table class="table table-striped table-bordered table-condensed">
		<tr>
				<td>State<span style="color: red;">*</span></td>
				<td>
				<select id="select_value_state">
						<%
							for (int i = 0; i < stateList2.size(); i++) {
						%>
						<option <%if (StateId.equals(stateList2.get(i).getStateId())) {%>
							selected="" <%}%> value=<%=stateList2.get(i).getStateId()%>><%=stateList2.get(i).getStateName()%></option>
						<%
							}
						%>
				</select>
				</td>
					
			</tr>
			<tr>
				<td>Region<span style="color: red;">*</span></td>
				<td><input type="text" id="name_user_region"
					value="<%=Region%>" /></td>
			</tr>
			
			<tr>
				<td>Status</td>
				<td><input id="name_user_region_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
					checked="checked" /><label for="name_user_region_active">Active</label>
					<input id="name_user_region_deactive" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive"
					<%if (Is_Active.equals("0")) {%> checked="checked" <%}%> /><label
					for="name_user_region_deactive">Deactive</label>
					<input id="name_user_region_delete" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDelete" />
					<label for="name_user_region_delete">Delete</label>
					</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save"
					id="name_user_region_save" class="btn_inp_al" /></td>
			</tr>
		</table>

	</div>
	<%
		break;
		}
	%>
	<div class="admin_footer">Dalmia</div>
	</form>
</body>
</html>

