<%@page import="com.model.QualificationModel"%>
<%@page import="com.model.QualificationModelList"%>
<%@page import="com.model.PinModel"%>
<%@page import="com.model.CityModel"%>
<%@page import="com.model.DistricModel"%>
<%@page import="com.model.SpecializationModel"%>
<%@page import="com.model.RegionModel"%>
<%@page import="com.utils.WebProjectLIstUtils"%>
<%@page import="com.webmodel.MasterState"%>
<%@page import="com.model.ProfessionDetailItemModel"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.model.UserDetailModel"%>
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
<%!UserDetailModel userDetail = null;
	StringBuilder specialization = null;
	ArrayList<ProfessionDetailItemModel> listProfession;
	ArrayList<QualificationModel> listQualification;

	ArrayList<MasterState> listState = null;
	ArrayList<RegionModel> listRegion = null;
	
	ArrayList<SpecializationModel> listSpecialization = null;
	ArrayList<CityModel> listCity = null;
	ArrayList<PinModel> listPinCode = null;
	ArrayList<DistricModel> listDistric = null;
	ArrayList<String> user_specilaiztion = null;


	%>
<%
	String Uid = PasswordEncryption.dycriptBase64(request.getParameter("Uid").getBytes());
	userDetail = new GetProjectListUtils().getPersonalDetail(Uid);
	listState = WebProjectLIstUtils.getStateList();
	
	if (userDetail.getDistricId() != null) {
		listDistric = WebProjectLIstUtils.getListOfDistricBasedOnState(userDetail.getStateId());
	}
	if (userDetail.getStateId() != null) {
		listCity = WebProjectLIstUtils.getListOfCityBasedOnDistric(userDetail.getDistricId());
	}
	if (userDetail.getPinCodeId() != null) {
		listPinCode = WebProjectLIstUtils.getListOfPinBasedOnCity(userDetail.getCityId());
	}
	
	listRegion = GetProjectListUtils.getRegion();
	listProfession = GetProjectListUtils.getProfessionType();
	listSpecialization = GetProjectListUtils.getSpecialization();
	listQualification=GetProjectListUtils.getQulification();
	user_specilaiztion=GetProjectListUtils.userSpecialization(userDetail.getUserId());
	//listUserSpecialization = WebProjectLIstUtils.getSpecialization(userDetail.getUserId());
	specialization = new StringBuilder();
	 for (int j = 0; j < listSpecialization.size(); j++) {
		 for(int i=0;i<user_specilaiztion.size();i++)
		 if(listSpecialization.get(j).getSpecializationId().equals(user_specilaiztion.get(i)))
		 {
		specialization.append(listSpecialization.get(j).getSpecializationType()).append(",");	
		
		 } 
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
var valueSelectedSpecialization = null;
var valueSelectedSpecializationId=null;
var arraySpecialization=null;


function populateDistric(values) {
	$("#ListDistric").html('');
	$("#user_pincode").val('');
	$("#ListCity").html('');
	$.ajax({
		type : 'POST',
		url : 'AjaxFilterDistric',
		data : 'StateId=' + values,
		success : function(data2) {
			$('#success').html(data2);
			$("#ListDistric").html(data2);
		},
		error : function(data) {
			alert('fail');
		}
	});
}

function populateCity(values) {
	$("#user_pincode").val('');
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

function populatePin(values) {
	$("#user_pincode").val('');
	$.ajax({
		type : 'POST',
		url : 'AjazFilterPinCode',
		data : 'CityId=' + values,
		success : function(data2) {
			$('#success').html(data2);
			$("#user_pincode").html(data2);
		},
		error : function(data) {
			alert('fail');
		}
	});
}

	function populateState(values) {
		$.ajax({
			type : 'POST',
			url : 'AjaxFilterState',
			data : 'RegionId=' + values,
			success : function(data2) {
				$('#success').html(data2);
				$("#ListState").html(data2);
				$("#ListCity").html('');
			},
			error : function(data) {
				alert('fail');
			}
		});
	}
	
	
	
	$(document) .ready(
			function() {
				selectID();
				$("#submit_specialization") .click(
						function() {
						selectID();
								
			});
			});
	
	function selectID() {
		valueSelectedSpecialization=null;
		var sizeSpecialization = $( '#sizeSpecialization').val();
	arraySpecialization = new Array();
	
for (var i = 0; i < sizeSpecialization; i++) {
				if(($('#' + i + 'specialization').is(":checked"))) {
					var Type = $( '#' + i + 'specialization') .val();
					var id = $( '#' + i + 'idSpecialization') .val();
					var obj = new Object();
					obj.SpecializationId = id;
					obj.SpecializationType = Type;
						arraySpecialization.push(JSON .stringify(obj));
				if (valueSelectedSpecialization == null) {
						valueSelectedSpecialization = '[' + Type + '],';
					} else {
						valueSelectedSpecialization = valueSelectedSpecialization +'[' + Type + '],';
					}
				} 
		}
		$("#exist_specialization").val(valueSelectedSpecialization);
	}
	$(document).ready(
			function() {
				$("#update").click(
						function() {
							var user_profession = $('#user_profession').val();
							var user_qualification = $('#user_qualification').val();

							var user_name = $('#user_name').val();
							var user_firm = $('#user_firm').val();
							var user_email = $('#user_email').val();
							var user_mobile = $('#user_mobile').val();
							var user_address = $('#user_address').val();
							var user_desc = $('#user_desc').val();
							var user_region = $('#user_region').val();
							var ListState = $('#ListState').val();
							var ListDistric = $('#ListDistric').val();
							var ListCity = $('#ListCity').val();
							var user_pincode = $('#user_pincode').val();
							var user_project_handled = $('#user_project_handled').val();
							var user_exp = $('#user_exp').val();
							var p_specialization = JSON.stringify(arraySpecialization);
							
							if (user_profession.length <= 0) {
								alert("Please Select the profession type");
								return;
							}
							if (user_qualification.length <= 0) {
								alert("Please Select the qualification");
								return;
							}
							
							if (user_name.length <= 0) {
								alert("Please Enter the user name");
								return;
							}
							/* if (user_firm.length <= 0) {
								alert("Please Enter the firm name");
								return;
							} */
							
							if (user_email.length <= 0) {
								alert("Please enter the email id");
								return;
							}
							if (user_mobile.length <= 0 || user_mobile.length > 10) {
								alert("Please Enter the valid mobile no.");
								return;
							}
							
							if (user_address.length <= 0) {
								alert("Please enter the address");
								return;
							}
							/* if (user_desc.length <= 0) {
								alert("Please enter the description");
								return;
							} */
							
							if (user_region == null || user_region.length <= 0) {
								alert("Please Select the region");
								return;
							}
							if (ListState == null ||ListState.length <= 0) {
								alert("Please select the state");
								return;
							}
							if (ListDistric == null ||ListDistric.length <= 0) {
								alert("Please select the District");
								return;
							}
							
							if (ListCity == null || ListCity.length <= 0) {
								alert("Please select the city");
								return;
							}
							if (user_pincode.length <= 0) {
								alert("Please enter the pincode");
								return;
							}
						  if (user_project_handled.length <= 0) {
							  user_project_handled=0;
							  /* alert("Please Enter handled projects");
								return; */
							}  
							  /* if (user_exp.length <= 0) {
								alert("Please select the experience");
								return;
							}    */
							console.log(p_specialization);
							if (p_specialization == "null" || p_specialization.length <= 2) {
/* 								if(user_specilaiztion==null)
 */								alert("Please select one the specialization");
								/* else */
									
								return;
							}
							$.ajax({
								type : 'POST',
								url : 'AjaxEditUser',
								data : 'profession=' + user_profession + 
								'&Uid=' + "<%=userDetail.getUserId()%>" + 
								'&name=' + user_name + 
								'&qualification=' + user_qualification + 
								'&firm=' + user_firm + 
								'&email=' + user_email + 
								'&mobile=' + user_mobile + 
								'&address=' + user_address + 
								'&region=' + user_region + 
								'&state=' + ListState + 
								'&desc=' + user_desc + 
								'&city=' + ListCity + 
								'&ListDistric=' + ListDistric + 
								'&pin=' + user_pincode + 
								'&proj_handle=' + user_project_handled + 
								'&exp=' + user_exp + 
								'&specialization=' + p_specialization + 
								'&Type=UserDetail' ,
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
		
	<div class="col-lg-12" style="margin-bottom:10px;">
			<input type="submit" onclick="window.history.go(-1); return false;"
					value="Back" class="btn_inp_al" /></div>
	
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 admin_d col-sm-offset-2">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">
						<h3>Edit Detail</h3>
					</div>
				</div>

				<div class="panel-body">
					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">Profession</label> <select
								id="user_profession" class="form-control">
								<%
									for (int i = 0; i < listProfession.size(); i++) {
								%>
								<option
									<%if (userDetail.getProfessionId().equals(listProfession.get(i).getProfessionId())) {%>
									selected="" <%}%>
									value=<%=listProfession.get(i).getProfessionId()%>><%=listProfession.get(i).getProfessionType()%></option>
								<%
									}
								%>
							</select>
						</div>
					</div>
					
						<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">Qualification</label> <select
								id="user_qualification" class="form-control">
								<%
									for (int i = 0; i < listQualification.size(); i++) {
								%>
								<% if(userDetail.getQualificationId()!=null){ %>
								<option
									<%if (userDetail.getQualificationId().equals(listQualification.get(i).getQualificationId())) {%>
									selected="" <%}%>
									value=<%=listQualification.get(i).getQualificationId()%>><%=listQualification.get(i).getQualificationType()%></option>
								<%
									}else{
								%>
								<option selected="" value=<%=listQualification.get(i).getQualificationId()%>><%=listQualification.get(i).getQualificationType()%></option>
							<%
							}
							}%>
							
							</select>
						</div>
					</div>
					
					
					
					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">User Name<span style="color: red;">*</span> </label>

							<%
								if (userDetail.getUserName() != null) {
							%>
							<input type="text" id="user_name" id="title" class="login-inpt"
								value="<%=userDetail.getUserName()%>">
							<%
								} else {
							%>
							<input type="text" id="user_name" id="title" class="login-inpt"
								value="">
							<%
								}
							%>



						</div>
					</div>
					
					
					
					
					
					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">Firm Name </label>

							<%
								if (userDetail.getFirmName() != null) {
							%>
							<input type="text" id="user_firm" id="title" class="login-inpt"
								value="<%=userDetail.getFirmName()%>">
							<%
								} else {
							%>
							<input type="text" id="user_firm" id="title" class="login-inpt"
								value="">
							<%
								}
							%>


						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">Email<span style="color: red;">*</span> </label>
							<%
								if (userDetail.getEmailId() != null) {
							%>
							<input type="text" id="user_email" id="title" class="login-inpt"
								value="<%=userDetail.getEmailId()%>">
							<%
								} else {
							%>
							<input type="text" id="user_email" id="title" class="login-inpt"
								value="">
							<%
								}
							%>


						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">Mobile<span style="color: red;">*</span> </label>
							<%
								if (userDetail.getMobileNo() != null) {
							%>
							<input type="number" id="user_mobile" id="title"
								class="login-inpt" value="<%=userDetail.getMobileNo()%>">
							<%
								} else {
							%>
							<input type="number" id="user_mobile" id="title"
								class="login-inpt" value="">
							<%
								}
							%>

						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">Address<span style="color: red;">*</span> </label>

							<%
								if (userDetail.getAddress() != null) {
							%>
							<input type="text" id="user_address" 
								class="login-inpt" value="<%=userDetail.getAddress()%>">
							<%
								} else {
							%>
							<input type="text" id="user_address" 
								class="login-inpt" value="">
							<%
								}
							%>

						</div>
					</div>
					
					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">Description </label>

							<%
								if (userDetail.getDescription() != null) {
							%>
							<input type="text" id="user_desc" 
								class="login-inpt" value="<%=userDetail.getDescription()%>">
							<%
								} else {
							%>
							<input type="text" id="user_desc" 
								class="login-inpt" value="">
							<%
								}
							%>

						</div>
					</div>

					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">Region<span style="color: red;">*</span> </label> <select
								id="user_region" onchange="populateState(this.value)"
								class="form-control">
								<%
									for (int i = 0; i < listRegion.size(); i++) {
								%>

								<%
									if (userDetail.getRegionId() != null) {
								%>
								<option
									<%if (userDetail.getRegionId().equals(listRegion.get(i).getRegionId())) {%>
									selected="" <%}%> value=<%=listRegion.get(i).getRegionId()%>><%=listRegion.get(i).getRegionType()%></option>

								<%
									} else {
								%>
								<option value=<%=listRegion.get(i).getRegionId()%>><%=listRegion.get(i).getRegionType()%></option>

								<%
									}
								%>



								<%
									}
								%>
							</select>

						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">State<span style="color: red;">*</span> </label> <select id="ListState"
								class="form-control" onchange="populateDistric(this.value)">
								<%
									for (int i = 0; i < listState.size(); i++) {
								%>
								<%
									if (userDetail.getStateId() != null) {
								%>
								<option
									<%if (userDetail.getStateId().equals(listState.get(i).getStateId())) {%>
									selected="" <%}%> value=<%=listState.get(i).getStateId()%>><%=listState.get(i).getStateName()%></option>
								<%
									} else {
								%>
								<option value=<%=listState.get(i).getStateId()%>><%=listState.get(i).getStateName()%></option>
								<%
									}
								%>

								<%
									}
								%>
							</select>
						</div>
					</div>
					
<!-- 			************************************ District ****************************	
 -->			
 		<div class="form-group">
 		
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">District<span style="color: red;">*</span> </label> <select id="ListDistric"
								class="form-control" onchange="populateCity(this.value)">
								<%
									for (int i = 0; i < listDistric.size(); i++) {
								%>
								<%
									if (userDetail.getDistricId() != null) {
								%>
								<option
									<%if (userDetail.getDistricId().equals(listDistric.get(i).getId())) {%>
									selected="" <%}%> value=<%=listDistric.get(i).getId()%>><%=listDistric.get(i).getName()%></option>
								<%
									} else {
								%>
								<option value=<%=listDistric.get(i).getId()%>><%=listDistric.get(i).getName()%></option>
								<%
									}
								%>

								<%
									}
								%>
							</select>
						</div>
					</div>
					
					
<!-- 					************************************ City ******************************
 -->				
 	           <div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">City<span style="color: red;">*</span> </label> <select
								class="form-control" id="ListCity" onchange="populatePin(this.value)" >
								<%
									if (listCity != null) {
								%>

								<%
									for (int i = 0; i < listCity.size(); i++) {
								%>
								<%
									if (userDetail.getCityId() != null) {
								%>
								<option
									<%if (userDetail.getCityId().equals(listCity.get(i).getCityId())) {%>
									selected="" <%}%> value=<%=listCity.get(i).getCityId()%>><%=listCity.get(i).getCityName()%></option>

								<%
									} else {
								%>
								<option value=<%=listCity.get(i).getCityId()%>><%=listCity.get(i).getCityName()%></option>
								<%
									}
								%>
								<%
									}
								%>
								<%
									} else {
								%>
								<%
									}
								%>
							</select>

						</div>
					</div>

					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">Pincode<span style="color: red;">*</span> </label>
							<select
								class="form-control" id="user_pincode" >
								<%
								if(userDetail.getPinCode() != null)
								{
								for(int i=0;i<listPinCode.size();i++)
								{
									%>
								<option
									<%if (userDetail.getPinCodeId().equals(listPinCode.get(i).getId())) {%>
									selected="" <%}%> value=<%=listPinCode.get(i).getId()%>><%=listPinCode.get(i).getPinCode()%></option>	
							<%		
								}
								}
								%>
								
								</select>
						

						</div>
					</div>

					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">Project Handled </label>

							<%
								if (userDetail.getProjectHandled() != null) {
							%>
							<input id="user_project_handled" type="number" id="title"
								class="login-inpt" value="<%=userDetail.getProjectHandled()%>">
							<%
								} else {
							%>
							<input id="user_project_handled" type="number" id="title"
								class="login-inpt" value="">
							<%
								}
							%>


						</div>
					</div>


					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">Experience in Years </label>
							<%
								if (userDetail.getExperienceYears() != null) {
							%>
							<input id="user_exp" type="number" id="title" class="login-inpt"
								value="<%=userDetail.getExperienceYears().replaceAll("Year", "")%>">
							<%
								} else {
							%>
							<input id="user_exp" type="number" id="title" class="login-inpt"
								value="">
							<%
								}
							%>

						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-12 margin_dsp">
							<label for="inputPassword">Specialization<span style="color: red;">*</span> </label>
						</div>
						<div class="col-lg-12 margin_dsp">

							<%
								if (specialization != null) {
							%>
							<input type="text" class="login-inpt" readonly="" id="exist_specialization"
								data-toggle="modal" data-target="#myModal"
								value=<%=specialization%>>
							<%
								} else {
							%>
							<input type="text" class="login-inpt" readonly="" id="exist_specialization"
								data-toggle="modal" data-target="#myModal" value="">
							<%
								}
							%>

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
									<h4 class="modal-title" id="myModalLabel">Specialization</h4>
								</div>
								<div class="modal-body">
								
									<%
								
										for (int i = 0; i < listSpecialization.size(); i++) {
									%>
									<div class="bdmd margin_dsp">
										<div class="checkbox">
											<label> 
											<input type="hidden" id="sizeSpecialization" value="<%=listSpecialization.size()%>">
											<input type="hidden" id="<%= i+"idSpecialization"%>" value="<%=listSpecialization.get(i).getSpecializationId()%>">
											<input type="checkbox" value="<%=listSpecialization.get(i).getSpecializationType()%>" id="<%= i+"specialization"%>" 
											<%
											for(int j=0;j<user_specilaiztion.size();j++)
											{
												if(user_specilaiztion.get(j).equals(listSpecialization.get(i).getSpecializationId()))
										{
											%>
											
											checked
											<%
											
											}
										}
											%>
											><%=listSpecialization.get(i).getSpecializationType()%>
											
											</label>	
											
										</div>
									</div>
									<%
										}
									%>
								</div>
								<div class="modal-footer">
									<div class="bsdf">
										<a id="submit_specialization" data-dismiss="modal"class="btn btn-primary">OK</a>
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