<%@page import="com.model.CementShopModel"%>
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
   CementShopModel shopModel=null;
	public void getState() {
		listState=WebProjectLIstUtils.getStateList();
	}%>

<%
	if (request.getParameter("id") != null && request.getParameter("id").trim().length() > 0 ) {
		shopModel=new CementShopModel();
		shopModel.setId(PasswordEncryption.dycriptBase64(request.getParameter("id").getBytes()));
		try {
			con = DBConnectionProvider.getCon();
			String q="Select dalmia_cement_shop.*, master_city.CityName,master_distric.name as dist_Name,master_state.StateName from dalmia_cement_shop Join master_city on master_city.CityId=dalmia_cement_shop.CityId Join master_distric on master_distric.id=dalmia_cement_shop.District_Id Join master_state on master_state.State_Id=dalmia_cement_shop.StateId where dalmia_cement_shop.Id='"+shopModel.getId()+"'";
			System.out.println(q);
			PreparedStatement ps = con .prepareStatement(q);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				shopModel.setId(rs.getString("Id"));
				shopModel.setDelearId(rs.getString("DelearId"));
				shopModel.setAdd(rs.getString("Address"));
				shopModel.setAdd2(rs.getString("Address1"));
				shopModel.setAdd3(rs.getString("Address2"));
				shopModel.setPinCode(rs.getString("PinCode"));
				shopModel.setCityId(rs.getString("CityId"));
				shopModel.setCity(rs.getString("CityName"));
				shopModel.setDistricId(rs.getString("District_Id"));
				shopModel.setDistrict(rs.getString("dist_Name"));
				shopModel.setStateId(rs.getString("StateId"));
				shopModel.setState(rs.getString("StateName"));
				shopModel.setIs_Active(rs.getString("Is_Active"));
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
		shopModel=null;

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
				$("#master_shop_save") .click(
								function() {
									var idEdit = $('#master_shop_save').attr('data-value');
									var StateName = $('#master_state_select').val();
									var DisticName = $('#master_distic_select').val();
									var City = $('#master_city_select').val();
									var PinCode = $('#master_pin_name').val();
									var DelearId = $('#master_delear_Id').val();
									var Add = $('#master_Add').val();
									var Add1 = $('#master_Add1').val();
									var Add2 = $('#master_Add2').val();
									
									
									if (StateName == null || StateName.length <= 0) {
										alert("Please Select State");
										return;
									}
									
									if (DisticName == null || DisticName.length <= 0) {
										alert("Please Select Distic");
										return;
									}
									if (City == null || City.length <= 0) {
										alert("Please Select City");
										return;
									}
									if (DelearId == null || DelearId.length <= 0) {
										alert("Please Enter Delear Id");
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
									
									if (Add == null || Add.length <= 0) {
										alert("Please Enter Address");
										return;
									}
									
									
									if (idEdit === '') {
										  $.ajax({
												type : 'POST',
												url : 'AjaxCementShop',
												data : 'StateId='+StateName+
												       '&DistricId='+DisticName+
												       '&CityId='+City+
												       '&PinCode='+PinCode+
												       '&DelearId='+DelearId+
												       '&Tag=AddNew'+
												       '&Add='+Add+
												       '&Add1='+Add1+
												       '&Add2='+Add2,
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
												url : 'AjaxCementShop',
												data : 'StateId='+StateName+
												       '&DistricId='+DisticName+
												       '&CityId='+City+
												       '&PinCode='+PinCode+
												       '&DelearId='+DelearId+
												       '&Tag=Edit'+
												       '&Add='+Add+
												       '&Add1='+Add1+
												       '&Add2='+Add2+
												       '&ShopId='+idEdit,
												success : function(data2) {
													$('#success').html(data2);
													$('#master_pin_name').val('');
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
		<input type="submit" onclick="window.history.go(-1); return false;" value="Back" class="btn_inp_al" /></td>
		<h4>Cement Shop</h4>
		<div class="details">
			<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>State<span style="color: red;">*</span> </td>
				<td><select id="master_state_select"
					onchange="populateDistric(this.value)">
						<%
						getState();
							for (int i = 0; i < listState.size(); i++) {
						%>
						<option
							<%if (shopModel!=null && shopModel.getStateId() != null && shopModel.getStateId().equals(listState.get(i).getStateId())) {%>
							selected="" <%}%> value=<%=listState.get(i).getStateId()%>><%=listState.get(i).getStateName()%></option>

						<%
							}
						%>
				</select></td>
			</tr>
				<tr>
				<td>District<span style="color: red;">*</span> </td>
				<td>
				<select id="master_distic_select" onchange="populateCity(this.value)">
				<%
				if(shopModel!=null && shopModel.getDistricId()!=null && shopModel.getDistricId().trim().length()>0 )
				{
			      ArrayList<DistricModel> listDistric=WebProjectLIstUtils.getListOfDistricBasedOnState(shopModel.getStateId());
				    for(int i=0;i<listDistric.size();i++)
				      {
					%>
					
					<option
							<%if (shopModel.getDistricId().equals(listDistric.get(i).getId())) {%>
							selected="" <%}%> value=<%=listDistric.get(i).getId()%>><%=listDistric.get(i).getName()%></option>
					
					<%
				}
				}
				%>
				</select>
				</td>
			</tr>
			<tr>
				<td>City<span style="color: red;">*</span> </td>
				<td>
					<select id="master_city_select">
					<%
				if(shopModel!=null && shopModel.getCityId()!=null && shopModel.getCityId().trim().length()>0 )
				{
			      ArrayList<CityModel> listCity=WebProjectLIstUtils.getListOfCityBasedOnDistric(shopModel.getDistricId());
				    for(int i=0;i<listCity.size();i++)
				      {
					%>
					
					<option
							<%if (shopModel.getCityId().equals(listCity.get(i).getCityId())) {%>
							selected="" <%}%> value=<%=listCity.get(i).getCityId()%>><%=listCity.get(i).getCityName()%></option>
					
					<%
				}
				}
				%>
					
				</select>
				</td>
			</tr>
			<tr>
				<td>Delear Id<span style="color: red;">*</span> </td>
				<td>
				
				<%
				if(shopModel!=null && shopModel.getDelearId()!=null && shopModel.getDelearId().trim().length()>0 )
				{
					%>
					<input type="number" id="master_delear_Id" value="<%= shopModel.getDelearId()%>" />
					<%
					
				}else
				{
				%>
				<input type="text" id="master_delear_Id"  />
				<%	
				}
				%>
				
				</td>
			</tr>
			<tr>
				<td>Pin Code<span style="color: red;">*</span>
				<td>
				
				<%
				if(shopModel!=null && shopModel.getPinCode()!=null && shopModel.getPinCode().trim().length()>0 )
				{
					%>
					<input type="number" id="master_pin_name" value="<%= shopModel.getPinCode()%>" />
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
				<td>Address<span style="color: red;">*</span> </td>
				<td>
				
				<%
				if(shopModel!=null && shopModel.getAdd()!=null && shopModel.getAdd().trim().length()>0 )
				{
					%>
					<textarea id="master_Add" type="text" style="width:100%; min-height:70px;"><%=shopModel.getAdd() %></textarea>
					<%
					
				}else
				{
				%>
				<textarea id="master_Add" type="text" style="width:100%; min-height:70px;"></textarea>
				<%	
				}
				%>
				
				</td>
			</tr>
			<tr>
				<td>Address 1</td>
				<td>
				
				<%
				if(shopModel!=null && shopModel.getAdd2()!=null && shopModel.getAdd2().trim().length()>0 )
				{
					%>
					<textarea id="master_Add1" type="text" style="width:100%; min-height:70px;"><%=shopModel.getAdd2() %></textarea>
					<%
					
				}else
				{
				%>
				<textarea id="master_Add1" type="text" style="width:100%; min-height:70px;"></textarea>
				<%	
				}
				%>
				
				</td>
			</tr>
			<tr>
				<td>Address2</td>
				<td>
				
				<%
				if(shopModel!=null && shopModel.getAdd3()!=null && shopModel.getAdd3().trim().length()>0 )
				{
					%>
					<textarea id="master_Add2" type="text" style="width:100%; min-height:70px;"><%=shopModel.getAdd3() %></textarea>
					<%
					
				}else
				{
				%>
				<textarea id="master_Add2" type="text" style="width:100%; min-height:70px;"></textarea>
				<%	
				}
				%>
				
				</td>
			</tr>
			<tr>
				<td colspan="2">
				<input type="submit" value="Save" id="master_shop_save" class="btn_inp_al" 
				<%
				if(shopModel!=null && shopModel.getId()!=null)
				{
				%>
				data-value="<%=shopModel.getId() %>" 
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
			</tr>
		</table>
		</div>
	</div>

	<!-----footer--->
	<div class="admin_footer">Dalmia</div>
	<!-----end of footer--->
</body>

</html>