<%@page import="com.google.gson.Gson"%>
<%@page import="com.model.CityModel"%>
<%@page import="com.model.DistricModel"%>
<%@page import="com.model.MachinaryModel"%>
<%@page import="com.model.ManPowerModel"%>
<%@page import="com.utils.WebProjectLIstUtils"%>
<%@page import="com.webmodel.MasterState"%>
<%@page import="com.utils.GetProjectListUtils"%>
<%@page import="com.webmodel.UsersProjectListItem"%>
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
<%!StringBuilder machine = null;
	StringBuilder manpower = null;
	UsersProjectListItem projectDetail = null;
	ArrayList<MasterState> listState = null;
	ArrayList<DistricModel> listDistric = null;
	ArrayList<CityModel> listCity = null;
	ArrayList<ManPowerModel> listManpower = null;
	ArrayList<MachinaryModel> listMachinary = null;
	%>
<%
	String Pid = PasswordEncryption.dycriptBase64(request.getParameter("proj_id").getBytes());
	projectDetail = new GetProjectListUtils().getProjectDetail(Pid);
	listState = WebProjectLIstUtils.getStateList();
	listDistric=WebProjectLIstUtils.getListOfDistricBasedOnState(projectDetail.getStateId());
	listCity = WebProjectLIstUtils.getListOfCityBasedOnDistric(projectDetail.getDistricId());
	listManpower = WebProjectLIstUtils.getManpower();
	listMachinary = WebProjectLIstUtils.getMachaniry();
	machine = new StringBuilder();
	manpower = new StringBuilder();
	/* for (int j = 0; j < projectDetail.getMachinary().size(); j++) {
		machine.append(projectDetail.getMachinary().get(j).getMachineriesType() + "["
				+ projectDetail.getMachinary().get(j).getNoOfMachineries() + "]").append(",");
	}
	for (int j = 0; j < projectDetail.getManPower().size(); j++) {
		manpower.append(projectDetail.getManPower().get(j).getManpowerType() + "["
				+ projectDetail.getManPower().get(j).getNoOfManpower() + "]").append(",");
	} */
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
	var arrayManPower = null;
	var arrayMachine = null;

	var valueSelectedManpower = null;
	var valueSelectedMachine = null;
	function populateDistric(values) {
		$("#p_dist").html('');
		$("#ListCity").html('');
		$.ajax({
			type : 'POST',
			url : 'AjaxFilterDistric',
			data : 'StateId=' + values,
			success : function(data2) {
				$('#success').html(data2);
				$("#p_dist").html(data2);
			},
			error : function(data) {
				alert('fail');
			}
		});
	}

	function populateCity(values) {
		$.ajax({
			type : 'POST',
			url : 'AjaxFilterCity',
			data : 'DistId=' + values,
			success : function(data2) {
				$('#success').html(data2);
				$("#ListCity").html(data2);
			},
			error : function(data) {
				alert('fail');
			}
		});
	}
	

	$(document)
			.ready(
					function() {
						$("#machin_model")
								.click(
										function() {
											valueSelectedMachine=null;
											var sizeMachinary = $(
													'#sizeMachinary').val();
											arrayMachine = new Array();
											for (var i = 0; i < sizeMachinary; i++) {
												var value = $(
														'#'
																+ i
																+ 'valuemachine')
														.val();
												var id = $(
														'#' + i + 'idMachine')
														.val();
												if (value != null
														&& value.length > 0) {
													var obj = new Object();
													obj.MachineriesId = id;
													obj.NoOfMachineries = value;
													arrayMachine.push(JSON
															.stringify(obj));
													var value = $(
															'#'
																	+ i
																	+ 'label_machine')
															.text()
													if (valueSelectedMachine == null) {
														valueSelectedMachine = value
																+ '['
																+ obj.NoOfMachineries
																+ '],';
													} else {
														valueSelectedMachine = valueSelectedMachine
																+ value
																+ '['
																+ obj.NoOfMachineries
																+ '],';
													}
												}
											}

											
											console.log(valueSelectedMachine);
											$("#exist_machine").val(
													valueSelectedMachine);
										});
					});

	$(document)
			.ready(
					function() {
						$("#menpower_model")
								.click(
										function() {
											valueSelectedManpower=null;
											var sizeManpower = $(
													'#sizeManpower').val();
											arrayManPower = new Array();
											for (var i = 0; i < sizeManpower; i++) {
												var value = $(
														'#'
																+ i
																+ 'valuemanpower')
														.val();
												var id = $(
														'#' + i + 'idManpower')
														.val();
												if (value != null
														&& value.length > 0) {
													var obj = new Object();
													obj.ManpowerId = id;
													obj.NoOfManpower = value;
													arrayManPower.push(JSON
															.stringify(obj));
													var value = $(
															'#'
																	+ i
																	+ 'label_manpower')
															.text()
													if (valueSelectedManpower == null) {
														valueSelectedManpower = value
																+ '['
																+ obj.NoOfManpower
																+ '],';
													} else {
														valueSelectedManpower = valueSelectedManpower
																+ value
																+ '['
																+ obj.NoOfManpower
																+ '],';
													}
												}
											}
											$("#exist_manpower").val(
													valueSelectedManpower);
											console.log(valueSelectedManpower);
										});
					});

	$(document).ready(function() {
		$("#update").click(function() {
			var p_name = $('#p_name').val();
			var p_value = $('#p_value').val();
			var p_value_select_type = $('#p_value_select_type').val();
			var p_area = $('#p_area').val();
			var p_address = $('#p_address').val();
			var p_state = $('#p_state').val();
			var p_dist = $('#p_dist').val();
			var p_city = $('#ListCity').val();
			var p_manpower ;
		if(arrayManPower==null)
		{	p_manpower="";}
		else
		{	 p_manpower = JSON.stringify(arrayManPower);}
		
		var p_machine;
		if(arrayMachine== null)
			{p_machine="";}
		else{
			p_machine = JSON.stringify(arrayMachine);}
			if (p_name.length <= 0) {
				alert("Please enter the Project Name");
				return;
			}
			if (p_value.length <= 0) {
				alert("Please enter the Project Value");
				return;
			}
			if (p_value_select_type.length <= 0) {
				alert("Please enter the type of Unit");
				return;
			}
			if (p_area.length <= 0) {
				alert("Please enter the Area");
				return;
			}
			if (p_address.length <= 0) {
				alert("Please enter the Project Address");
				return;
			}
			if (p_state == null || p_state.length <= 0) {
				alert("Please Select the State");
				return;
			}
			if (p_dist == null || p_dist.length <= 0) {
				alert("Please Select the District");
				return;
			}
			
			if ( p_city == null || p_city.length <= 0) {
				alert("Please Select the City");
				return;
			}

		/* 	if (p_manpower == "null" || p_manpower.length <= 2) {
				alert("Please select one Manpower Detail");
				return;
			}
			if (p_machine == "null" || p_machine.length <= 2) {
				alert("Please select one Machine Detail");
				return;
			} */
			$.ajax({
				type : 'POST',
				url : 'AjaxEditUser',
				data : 
				'p_name=' + p_name + 
				'&Pid=' + "<%=Pid%>" + 
				'&p_value=' + p_value + 
				'&p_value_select_type=' + p_value_select_type + 
				'&p_area=' + p_area + 
				'&p_address=' + p_address + 
				'&p_state=' + p_state + 
				'&p_dist=' + p_dist + 
				'&p_city=' + p_city + 
				'&p_manpower=' + p_manpower + 
				'&p_machine=' + p_machine + 
				'&Type=ProjectDetail' ,
				success : function(data2) {
					$('#success').html(data2);
					alert('Project Successfull update.');
				},
				error : function(data) {
					alert('fail');
				}
			});
			/* alert(p_name + "\n2" + p_value + "\n3"
					+ p_value_select_type + "\n4" + p_area
					+ "\n5" + p_address + "\n6" + p_state
					+ "\n7" + p_city + "\n8" + p_manpower
					+ "\n9" + p_machine + ""); */
		});
	});
</script>

<body>
	<div class="container">
	
		<div class="col-lg-12" style="margin-bottom:10px;">
			<input type="submit" onclick="window.history.go(-1); return false;"
					value="Back" class="btn_inp_al" /></div>
					
					
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 admin_d col-sm-offset-2">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">
						<h3>Edit Project</h3>
					</div>
				</div>

				<div class="panel-body">
					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">Project Name<span style="color: red;">*</span> </label> <input
								type="text" id="p_name" class="login-inpt"
								value="<%=projectDetail.getProjectName()%>">
						</div>
					</div>

					<div class="form-group margin_dsp">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">Value of Project<span style="color: red;">*</span> </label>
						</div>
						<div class="col-lg-6 margin_dsp">
							<input type="number" id="p_value" class="login-inpt"
								value="<%=projectDetail.getProjectPrice().split(" ")[0]%>">
						</div>
						<div class="col-lg-6 margin_dsp">
							<select class="form-control" id="p_value_select_type">
								<option
									<%if (projectDetail.getProjectPrice().split(" ")[1].equals("Thousand")) {%>
									selected="" <%}%> value="Thousand">Thousand</option>
								<option
									<%if (projectDetail.getProjectPrice().split(" ")[1].equals("Lakh")) {%>
									selected="" <%}%> value="Lakh">Lakh</option>
								<option
									<%if (projectDetail.getProjectPrice().split(" ")[1].equals("Crore")) {%>
									selected="" <%}%> value="Crore">Crore</option>
							</select>
						</div>

					</div>


					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">Area<span style="color: red;">*</span> </label> <input type="number"
								id="p_area" class="login-inpt"
								value="<%=projectDetail.getArea()%>">
						</div>
					</div>

					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">Address<span style="color: red;">*</span> </label> <input type="text"
								id="p_address" class="login-inpt"
								value="<%=projectDetail.getProjectLocation()%>">
						</div>
					</div>




					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">State<span style="color: red;">*</span> </label> <select id="p_state"
								class="form-control" onchange="populateDistric(this.value)">
								<%
									for (int i = 0; i < listState.size(); i++) {
								%>
								<option
									<%if (projectDetail.getStateId().equals(listState.get(i).getStateId())) {%>
									selected="" <%}%> value=<%=listState.get(i).getStateId()%>><%=listState.get(i).getStateName()%></option>
								<%
									}
								%>
							</select>

						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">District<span style="color: red;">*</span> </label> <select id="p_dist"
								class="form-control" onchange="populateCity(this.value)">
								<%
									for (int i = 0; i < listDistric.size(); i++) {
								%>
								<option
									<%if (projectDetail.getDistricId().equals(listDistric.get(i).getId())) {%>
									selected="" <%}%> value=<%=listDistric.get(i).getId()%>><%=listDistric.get(i).getName()%></option>
								<%
									}
								%>
							</select>

						</div>
					</div>
					
					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">City<span style="color: red;">*</span> </label> <select
								class="form-control" id="ListCity">
								<%
									for (int i = 0; i < listCity.size(); i++) {
								%>
								<option
									<%if (projectDetail.getCityId().equals(listCity.get(i).getCityId())) {%>
									selected="" <%}%> value=<%=listCity.get(i).getCityId()%>><%=listCity.get(i).getCityName()%></option>
								<%
									}
								%>
							</select>
						</div>
					</div>

					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">Manpower</label>
						</div>
						<div class="col-lg-12 margin_dsp">
							<input type="text" class="login-inpt" readonly=""
								id="exist_manpower" value="<%=manpower%>" data-toggle="modal"
								data-target="#myModal">
						</div>

					</div>

					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">Machinery</label>
						</div>
						<div class="col-lg-12 margin_dsp">
							<input type="text" readonly="" class="login-inpt"
								value="<%=machine%>" id="exist_machine" data-toggle="modal"
								data-target="#machinery">
						</div>

					</div>


					<!-- Modal -->
					<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="myModalLabel">Manpower</h4>
								</div>
								<div class="modal-body">
									<%
										for (int i = 0; i < listManpower.size(); i++) {
									%>
									<div class="bdmd margin_dsp">
										<div class="col-lg-4 mss">
											<input type="hidden" id="sizeManpower"
												value="<%=listManpower.size()%>"> <label
												for="inputPassword" id=<%=i + "label_manpower"%>><%=listManpower.get(i).getManpowerType()%></label>
										</div>
										<div class="col-lg-8">
											<input type="number" id="<%=i + "valuemanpower"%>"
												class="login-inpt" value=""> <input type="hidden"
												id=<%=i + "idManpower"%>
												value="<%=listManpower.get(i).getManpowerId()%>">
										</div>

									</div>
									<%
										}
									%>


								</div>
								<div class="modal-footer">

									<div class="bsdf">
										<a id="menpower_model" class="btn btn-primary"
											data-dismiss="modal">OK</a>
									</div>
									<div class="bsdfs">
										<a class="btn btn-danger" data-dismiss="modal"
											aria-label="Close">Cancel</a>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal fade" id="machinery" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="myModalLabel">Machinery</h4>
								</div>
								<div class="modal-body">
									<%
										for (int i = 0; i < listMachinary.size(); i++) {
									%>
									<div class="bdmd margin_dsp">
										<div class="col-lg-4 mss">
											<input type="hidden" id="sizeMachinary"
												value="<%=listMachinary.size()%>"> <label
												id=<%=i + "label_machine"%> for="inputPassword"><%=listMachinary.get(i).getMachineriesType()%></label>
										</div>
										<div class="col-lg-8 ">
											<input type="number" id=<%=i + "valuemachine"%>
												class="login-inpt" value=""> <input type="hidden"
												id=<%=i + "idMachine"%>
												value="<%=listMachinary.get(i).getMachineriesId()%>">
										</div>
									</div>
									<%
										}
									%>
								</div>
								<div class="modal-footer">

									<div class="bsdf">
										<a id="machin_model" class="btn btn-primary"
											data-dismiss="modal">OK</a>
									</div>

									<div class="bsdfs">
										<a class="btn btn-danger" data-dismiss="modal"
											aria-label="Close">Cancel</a>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-12">
							<div class="controls">
								<a id="update" class="btn btn-xm btn-primary">Update Detail</a>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
	<!-----footer--->
	<div class="admin_footer">Dalmia</div>

	<!-----end of footer--->

</body>

</html>