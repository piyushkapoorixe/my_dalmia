<%@page import="com.google.gson.Gson"%>
<%@page import="com.model.DistricModel"%>
<%@page import="com.utils.WebProjectLIstUtils"%>
<%@page import="com.webmodel.MasterState"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.constants.WebConstants"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.webmodel.MasterCity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<jsp:include page="header.jsp"></jsp:include>
<%
CityId = PasswordEncryption.dycriptBase64(request.getParameter("id").getBytes());
getCity(); 
%>
<%!
   MasterCity item =null;
   String CityId=null;
	public void getCity() {
		Connection con = null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "Select master_distric.id as dist_id,master_city.CityId,master_city.CityName,master_city.Is_Active,master_state.state_id from master_city INNER JOIN master_distric ON master_distric.id=master_city.DiscticId INNER JOIN master_state ON master_state.State_Id=master_distric.stateId where master_city.CityId='"+CityId+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				item = new MasterCity();
				item.setCityId(rs.getString("CityId"));
				item.setIsActive(rs.getString("Is_Active"));	
				item.setDistricId(rs.getString("dist_id"));
				item.setStateId(rs.getString("state_id"));
				item.setCityName(rs.getString("CityName"));
				System.out.print(""+new Gson().toJson(item));
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
			$("#master_city_name_city_save").click(
					function() {
						var City = $('#master_city_name') .val();
						var Distric = $( "#master_city_distric_select") .val();
						var active = $( "#master_city_name_active").is( ":checked") ? "true" : "false";
						
						var dlt = $( "#master_city_name_delete").is( ":checked") ? "true" : "false";
						$.ajax({
							type : 'POST',
							url : 'AjaxController',
							data : 'Distric=' + Distric + '&City=' + City + '&deleteStatus=' + dlt + '&Tag=Edit' +'&CityId=' + <%=item.getCityId()%>+
									 '&Type=' +
<%=WebConstants.City%>
+ '&IsActive=' + active,
							success : function(data2) {
								$('#success').html(data2);
								$('#master_city_name_city').val('');
								alert(data2);
								if(data2.indexOf("Deleted")>0){
									window.location.href = "Master_City.jsp";
								}
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
				<h4>Edit City</h4>
			</td>
		</tr>
		</table>
		<table class="table table-striped table-bordered table-condensed">
			<tr>
				<td>State<span style="color: red;">*</span></td>
				<td><select name="ctl00$ContentPlaceHolder1$ddlState"
					id="select_master_city_name" id="ContentPlaceHolder1_ddlState" onchange="populateDistric(this.value)">
						<%
						
						    ArrayList<MasterState> listState=WebProjectLIstUtils.getStateList();
						    ArrayList<DistricModel> listDistric=WebProjectLIstUtils.getListOfDistricBasedOnState(item.getStateId());
							for (int i = 0; i < listState.size(); i++) {
						%>
						<option
							<%if (item.getStateId().equals(listState.get(i).getStateId())) {%>
							selected="" <%}%> value=<%=listState.get(i).getStateId()%>><%=listState.get(i).getStateName()%></option>
						<%
							}
						%>
				</select>
			</tr>
			<tr>
				<td>District<span style="color: red;">*</span></td>
				<td><select id="master_city_distric_select">
						<%
							for (int i = 0; i < listDistric.size(); i++) {
						%>
						<option
							<%if (item.getDistricId().equals(listDistric.get(i).getId())) {%>
							selected="" <%}%> value=<%=listDistric.get(i).getId()%>><%=listDistric.get(i).getName()%></option>
						<%
							}
						%>
				</select></td>
			</tr>

			<tr>
				<td>City<span style="color: red;">*</span></td>
				<td><input name="ctl00$ContentPlaceHolder1$txtCity" type="text"
					id="master_city_name" value="<%=item.getCityName()%>" /></td>
			</tr>
			<tr>
				<td>Status</td>
				<td><input id="master_city_name_active" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnActive"
					checked="checked" /><label for="master_city_name_active">Active</label>
					<input id="master_city_name_deactive" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDeactive"
					<%if (item.getIsActive().equals("Inactive")) {%> checked="checked" <%}%> /><label
					for="master_city_name_deactive">Deactive</label>
					<input id="master_city_name_delete" type="radio"
					name="ctl00$ContentPlaceHolder1$status" value="rdBtnDelete"/>
					<label for="master_city_name_delete">Delete</label>
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save"
					id="master_city_name_city_save" class="btn_inp_al"
					 /></td>
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