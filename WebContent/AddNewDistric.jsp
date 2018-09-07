<%@page import="com.webmodel.MasterRegionModel"%>
<%@page import="com.webmodel.MasterState"%>
<%@page import="com.utils.WebProjectLIstUtils"%>
<%@page import="com.model.StateModel"%>
<%@page import="com.model.DistricModel"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.PreparedStatement"%>
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
<%!ArrayList<MasterState> listState;
   String DistId=null;
   String RegionId=null;
   String DistName=null;
   String Dist_State=null;
   Connection con = null;
   String Is_Active=null;

	public void getState() {
		listState=WebProjectLIstUtils.getStateList();
	}%>

<%
	if (request.getParameter("id") != null && request.getParameter("id").trim().length() > 0 ) {
		DistId = PasswordEncryption.dycriptBase64(request.getParameter("id").getBytes());
		RegionId= PasswordEncryption.dycriptBase64(request.getParameter("rid").getBytes());
		Is_Active= request.getParameter("status");
		try {
			con = DBConnectionProvider.getCon();
			PreparedStatement ps = con .prepareStatement("SELECT name,stateId FROM master_distric where id=" + DistId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				DistName = rs.getString("name");
				Dist_State = rs.getString("stateId");
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
	} else {
		DistName = null;
		Dist_State = null;
		DistId=null;
		RegionId=null;
		 String Is_Active=null;

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
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
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
			url : 'AjaxFilterUserRegion',
			data : 'StateId=' + values,
			success : function(data2) {
				$('#success').html(data2);
				$("#master_region_select").html(data2);
			},
			error : function(data) {
				alert('fail');
			}
		});
	}
	
	$(document).ready(
			function() {
				$("#master_distic_save") .click(
								function() {
									var idEdit = $('#master_distic_save').attr('data-value');
									var DisticName = $('#master_distic_name').val();
									var StateId = $('#master_state_select').val();
									var RegionId = $('#master_region_select').val();
									if (StateId == null || StateId.length <= 0) {
										alert("Please Select State");
										return;
									}
									if (RegionId == null || RegionId.length <= 0) {
										alert("Please Select Region");
										return;
									}
									if (DisticName == null || DisticName.length <= 0) {
										alert("Please Enter the District Name");
										return;
									}
									var active = $("#master_distic_active").is(
											":checked") ? "true" : "false";
									
									var dlt = $("#master_distic_delete").is(
									":checked") ? "true" : "false";
									
									if (idEdit === '') {
										 $.ajax({
												type : 'POST',
												url : 'AjaxFilterUserRegion',
												data : 'DisticName=' + DisticName +
												       '&StateId='+StateId+
												       '&RegionId='+RegionId+
												       '&Tag=AddNew'+
												       '&IsActive=' + active,
												success : function(data2) {
													$('#success').html(data2);
													$('#master_distic_name').val('');
													alert(data2);
												},
												error : function(data) {
													alert('fail');
												}
											}); 
									} else {
										 $.ajax({
												type : 'POST',
												url : 'AjaxFilterUserRegion',
												data : 'DisticName=' + DisticName +
												       '&DistricId='+<%=DistId%>+
												       '&StateId='+StateId+
												       '&RegionId='+RegionId+
												       '&Tag=Edit'+
												       '&deleteStatus=' + dlt +
												       '&IsActive=' + active,
												success : function(data2) {
													$('#success').html(data2);
													$('#master_distic_name').val('');
													alert(data2);
													if(data2.indexOf("Deleted")>0){
														window.location.href = "DistricMaster.jsp";
													}
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
		<table>
		<tr>
		<td>
		<input type="submit" onclick="window.history.go(-1); return false;" value="Back" class="btn_inp_al" />
		<p></p>
		</td>
		</tr>
			<tr>
				<td>
					<%
						if(DistId!=null && DistId.trim().length()>0)
							{
					%>
								<h4>Edit District</h4>
					<%	
							}else
							{
					%>
								<h4>Add District</h4>
					<%
							}
					%>
				</td>
			</tr>
		</table>
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>State<span style="color: red;">*</span></td>
				<td><select id="master_state_select"
					onchange="populateDistric(this.value)">
						<%
						getState();
							for (int i = 0; i < listState.size(); i++) {
						%>
						<option
							<%if (Dist_State != null && Dist_State.equals(listState.get(i).getStateId())) {%>
							selected="" <%}%> value=<%=listState.get(i).getStateId()%>><%=listState.get(i).getStateName()%></option>

						<%
							}
						%>
				</select></td>
			</tr>
			<tr>
				<td>Region<span style="color: red;">*</span></td>
				<td>
					<%
				if(RegionId!=null && RegionId.trim().length()>0)
				{
					ArrayList<MasterRegionModel> data=WebProjectLIstUtils.getRegionList(Dist_State);
					%> <select id="master_region_select">
						<%
							for (int i = 0; i < data.size(); i++) {
						%>
						<option
							<%if (RegionId != null && RegionId.equals(data.get(i).getId())) {%>
							selected="" <%}%> value=<%=data.get(i).getId()%>><%=data.get(i).getRegion()%></option>

						<%
							}
						%>
				</select> <%
					
				}
				else
				{
					%> <select id="master_region_select">
				</select> <%
				}
				%>
				</td>
			</tr>
			<tr>
				<td>Name of District<span style="color: red;">*</span></td>
				<td>
					<%
				if(DistName!=null && DistName.trim().length()>0)
				{
					%> <input type="text" id="master_distic_name" value="<%=DistName%>" />

					<%
					
				}else
				{%> <input type="text" id="master_distic_name" /> <%
				}
				%>
				</td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="master_distic_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
					checked="checked" /><label for="master_city_name_city_active">Active</label>
					<input id="master_distic_deactive" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive"
					<%if (Is_Active!=null && Is_Active.equals("0")) {%> checked="checked" <%}%> 
					 /><label
					for="master_city_name_city_deactive">Deactive</label>
					<%
					if(DistId!=null && DistId.trim().length()>0)
						{
					%>
							<input id="master_distic_delete" type="radio"
								name="ctl00$ContentPlaceHolder1$status" value="rdBtnDelete"/>
								<label for="master_distic_delete">Delete</label>
					<%	
						}
					%>
					</td>
			</tr>
			<tr>
				<td colspan="2">
				<input type="submit" value="Save" id="master_distic_save" class="btn_inp_al" 
				
				<%
				if(DistId!=null && DistId.trim().length()>0)
				{
					%>
					data-value="<%=DistId %>"
				<%	
				}else
				{
					%>
					data-value=""
					<%
				}
				%>
				
				 /> 
				</td>
			</tr>
		</table>
	</div>

	<!-----footer--->
	<div class="admin_footer">Dalmia</div>
	<!-----end of footer--->
	</form>
	</div>
</body>

</html>