<%@page import="com.model.CityModel"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.model.PinModel"%>
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
   Connection con = null;
   PinModel pinModel=null;
	public void getState() {
		listState=WebProjectLIstUtils.getStateList();
	}%>

<%
	if (request.getParameter("id") != null && request.getParameter("id").trim().length() > 0 ) {
		pinModel=new PinModel();
		pinModel.setId(PasswordEncryption.dycriptBase64(request.getParameter("id").getBytes()));
		try {
			con = DBConnectionProvider.getCon();
			String q="SELECT master_pin_code.*, master_distric.id as dist_Id,master_state.State_Id from master_pin_code JOIN master_city on master_city.CityId=master_pin_code.CItyId JOIN master_distric on master_distric.id=master_city.DiscticId JOIN master_state on master_distric.stateId=master_state.State_Id where master_pin_code.Id='"+pinModel.getId()+"'";
			System.out.println(q);
			PreparedStatement ps = con .prepareStatement(q);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				pinModel.setDistricId(rs.getString("dist_Id"));
				pinModel.setStateId(rs.getString("State_Id"));
				pinModel.setCityId(rs.getString("CItyId"));
				pinModel.setPinCode(rs.getString("Pin"));
				pinModel.setIsactive(rs.getString("isactive"));
				System.out.println(new Gson().toJson(pinModel));
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
		pinModel=null;

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
		$("#master_distic_select").html('');
		$("#master_city_select").html('');
		$.ajax({
			type : 'POST',
			url : 'AjaxFilterDistric',
			data : 'StateId=' + values,
			success : function(data2) {
				$('#success').html(data2);
				$("#master_distic_select").html(data2);
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
				$("#master_city_select").html(data2);
			},
			error : function(data) {
				alert('fail');
			}
		});
	}
	
	
	$(document).ready(
			function() {
				$("#master_pin_save") .click(
								function() {
									var idEdit = $('#master_pin_save').attr('data-value');
									var DisticName = $('#master_distic_select').val();
									var City = $('#master_city_select').val();
									var PinCode = $('#master_pin_name').val();
									
									if (DisticName == null || DisticName.length <= 0) {
										alert("Please Select Distic");
										return;
									}
									if (City == null || City.length <= 0) {
										alert("Please Select City");
										return;
									}
									if (PinCode == null || PinCode.length <= 0) {
										alert("Please Enter the PinCode ");
										return;
									}
									if (PinCode.length != 6) {
										alert("Please Enter the valid PinCode ");
										return;
									}
									var active = $("#master_pin_active").is(
											":checked") ? "true" : "false";
									var dlt = $("#master_pin_delete").is(
									":checked") ? "true" : "false";
									
									if (idEdit === '') {
										  $.ajax({
												type : 'POST',
												url : 'AjaxEditOrAddPinCode',
												data : 
												       'DistricId='+DisticName+
												       '&CityId='+City+
												       '&PinCode='+PinCode+
												       '&Tag=AddNew'+
												       '&IsActive=' + active,
												success : function(data2) {
													$('#success').html(data2);
													$('#master_pin_name').val('');
													alert(data2);
												},
												error : function(data) {
													alert('fail');
												}
											}); 
									} else {
										  $.ajax({
												type : 'POST',
												url : 'AjaxEditOrAddPinCode',
												data : 
													   'DistricId='+DisticName+
												       '&CityId='+City+
												       '&PinCode='+PinCode+
												       '&deleteStatus='+dlt+
												       '&PinId='+idEdit+
												       '&Tag=Edit'+
												       '&IsActive=' + active,
												success : function(data2) {
													$('#success').html(data2);
													$('#master_pin_name').val('');
													alert(data2);
													if(data2.indexOf("Deleted")>0){
														window.location.href = "PinMaster.jsp";
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
					if(pinModel!=null && pinModel.getPinCode()!=null && pinModel.getPinCode().trim().length()>0 )
					{
						%>
						<h4>Edit Pin Code</h4>
						<%
						
					}else
					{
						%>
						<h4>Add Pin Code</h4>
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
							<%if (pinModel!=null && pinModel.getStateId() != null && pinModel.getStateId().equals(listState.get(i).getStateId())) {%>
							selected="" <%}%> value=<%=listState.get(i).getStateId()%>><%=listState.get(i).getStateName()%></option>

						<%
							}
						%>
				</select></td>
			</tr>
				<tr>
				<td>Distric<span style="color: red;">*</span></td>
				<td>
				<select id="master_distic_select" onchange="populateCity(this.value)">
				<%
				if(pinModel!=null && pinModel.getDistricId()!=null && pinModel.getDistricId().trim().length()>0 )
				{
			      ArrayList<DistricModel> listDistric=WebProjectLIstUtils.getListOfDistricBasedOnState(pinModel.getStateId());
				    for(int i=0;i<listDistric.size();i++)
				      {
					%>
					
					<option
							<%if (pinModel.getDistricId().equals(listDistric.get(i).getId())) {%>
							selected="" <%}%> value=<%=listDistric.get(i).getId()%>><%=listDistric.get(i).getName()%></option>
					
					<%
				}
				}
				%>
				</select>
				</td>
			</tr>
			<tr>
				<td>City<span style="color: red;">*</span></td>
				<td>
					<select id="master_city_select">
					<%
				if(pinModel!=null && pinModel.getCityId()!=null && pinModel.getCityId().trim().length()>0 )
				{
			      ArrayList<CityModel> listCity=WebProjectLIstUtils.getListOfCityBasedOnDistric(pinModel.getDistricId());
				    for(int i=0;i<listCity.size();i++)
				      {
					%>
					
					<option
							<%if (pinModel.getCityId().equals(listCity.get(i).getCityId())) {%>
							selected="" <%}%> value=<%=listCity.get(i).getCityId()%>><%=listCity.get(i).getCityName()%></option>
					
					<%
				}
				}
				%>
					
				</select>
				</td>
			</tr>
			<tr>
				<td>Pin Code<span style="color: red;">*</span></td>
				<td>
				
				<%
				if(pinModel!=null && pinModel.getPinCode()!=null && pinModel.getPinCode().trim().length()>0 )
				{
					%>
					<input type="number" id="master_pin_name" value="<%= pinModel.getPinCode()%>" />
					<%
					
				}else
				{
				%>
				<input type="number" id="master_pin_name"  />
				<%	
				}
				%>
				
				</td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="master_pin_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
					checked="checked" /><label for="master_pin_active">Active</label>
					<input id="master_distic_deactive" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive"
					<%if (pinModel!=null && pinModel.getIsactive().equals("0")) {%>
					checked="checked" <%}%> /><label
					for="master_distic_deactive">Deactive</label>
					<%
					if(pinModel!=null && pinModel.getPinCode()!=null && pinModel.getPinCode().trim().length()>0 )
					{
						%>
						<input id="master_pin_delete" type="radio"
						name="ctl00$ContentPlaceHolder1$status" value="rdBtnDelete"/>
						<label for="master_pin_delete">Delete</label>
						<%
					}
					%>
				</td>
			</tr>
			<tr>
				<td colspan="2">
				<input type="submit" value="Save" id="master_pin_save" class="btn_inp_al" 
				<%
				if(pinModel!=null && pinModel.getId()!=null)
				{
				%>
				data-value="<%=pinModel.getId() %>" 
				<%	
				}
				else
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