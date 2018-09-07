<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.utils.GetProjectListUtils"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.model.UserDetailModel"%>
<jsp:include page="header.jsp" />
<%!StringBuilder machine = null;
	StringBuilder manpower = null;
	UserDetailModel userDetail=null;
	String UserId = null;%>
<%
	UserId = PasswordEncryption.dycriptBase64(request.getParameter("Uid").getBytes());
	userDetail = new GetProjectListUtils().getList(UserId);
	
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
<script type='text/javascript' src="js/jquery.blockUI.js"></script>
<script type='text/javascript'>
$(document).ready(
		function() {
			$(document).ajaxStop($.unblockUI);
            $(document).ajaxStart(function () { $.blockUI({ message: '<div class="load_1"><img src="images/loading.gif" /><p>Please Wait...</p></div>' }); });
		});
var base64Val = null;
	$(document).ready(function() {
		$("#approve").click(function() {
			$.ajax({
				type : 'POST',
				url : 'ApproveDisapprove',
				data : 'Uid=' +
<%=UserId%>
	+ '&status=1',
				success : function(data2) {
					$('#success').html(data2);
					alert(data2);
					location.reload();
				},
				error : function(data) {
					alert('fail');
				}
			});
		});
	});
	$(document).ready(function() {
		$("#dis_approve").click(function() {
			$.ajax({
				type : 'POST',
				url : 'ApproveDisapprove',
				data : 'Uid=' +
<%=UserId%>
	+ '&status=0',
				success : function(data2) {
					$('#success').html(data2);
					alert(data2);
					location.reload();
				},
				error : function(data) {
					alert('fail');
				}
			});
		});
	});
	$(document).ready(function() {
		$("#p_approve").click(function() {
			$.ajax({
				type : 'POST',
				url : 'ApproveDisapprove',
				data : 'Uid=' +
<%=UserId%>
	+ '&status=1',
				success : function(data2) {
					$('#success').html(data2);
					alert(data2);
					location.reload();
				},
				error : function(data) {
					alert('fail');
				}
			});
		});
	});
	$(document).ready(function() {
		$("#p_dis_approve").click(function() {
			$.ajax({
				type : 'POST',
				url : 'ApproveDisapprove',
				data : 'Uid=' + <%=UserId%> + '&status=0',
				success : function(data2) {
					$('#success').html(data2);
					alert(data2);
					location.reload();
				},
				error : function(data) {
					alert('fail');
				}
			});
		});
	});
	function changeStatus(id,status) {
		
		if (status == 11)
			{
			$.ajax({
				type : 'POST',
				url : 'ApproveDisapprove',
				data : 'PID='+id + '&status=1' + '&Uid=' + <%=UserId%>,
				success : function(data2) {
					$('#success').html(data2);
					alert(data2);
					location.reload();
				},
				error : function(data) {
					alert('fail');
				}
			});
			}
		else
			if(status == 12) {
				$.ajax({
					type : 'POST',
					url : 'ApproveDisapprove',
					data : 'PID='+id + '&status=0' + '&Uid=' + <%=UserId%>,
					success : function(data2) {
						$('#success').html(data2);
						alert(data2);
						location.reload();
					},
					error : function(data) {
						alert('fail');
					}
				});
				}
	}
	function readURL(input) {
		if (input.files && input.files[0]) {
			base64Val = null;
			var reader = new FileReader();
			reader.onload = function(e) {
				base64Val = e.target.result
				var filePath = $('#file_name').val();
				$.ajax({
					type : 'POST',
					url : 'AjaxUploadProfilePic',
					data : 'UID=' + "<%=UserId%>" + 
					       '&UName=' + "<%=userDetail.getUserName()%>"+ 
					       '&Base64Data=' + base64Val+ 
					       '&Path=' + filePath,
					success : function(data2) {
						$('#success').html(data2);
						alert(data2);
					},
					error : function(data) {
						alert('fail');
					}
				});
			};
			reader.readAsDataURL(input.files[0]);
		}
	}
</script>
</head>
<body>
	<div class="container">
	<input type="submit" onclick="window.history.go(-1); return false;"
					value="Back" class="btn_inp_al" />
		<div class="table-responsive">
			<div class="profile">
				<div class="profile_in">
					<div class="chat_d">
						<div class="pending_img">
							<%
								if (userDetail.getImageUrl() != null) {
							%>
							<img src=<%=userDetail.getImageUrl() %> style="width: 140px; height: 140px; border-radius: 70px;">
							<%
								} else {
							%>
							<img src="images/user_placeholder.png" style="width: 140px; height: 140px; border-radius: 70px;">
							<%
								}
							%>



						</div>
						<div class="pending_img_edit">
						<input class="uplod_img2" id="file_name" aria-describedby="fileHelp" type="file" accept=".jpeg,.jpg,.png" onchange="readURL(this);">
						</div>



						<div style="position: absolute; right: 100px; top: 0px;">

							
								<div class="book_now">
						<%
							if (userDetail.getActivationStatus().equals("0")) {
						%>
						<input class="btn btn-success pull-right" id="approve" value="Approve User"
							type="submit">
						<%
							} else if (userDetail.getActivationStatus().equals("1")) {
						%>
						<input class="btn btn-danger pull-right" id="dis_approve"
							value="Disapprove User" type="submit">
						<%
							} else if (userDetail.getActivationStatus().equals("2")) {
						%>
						<input class="btn btn-success pull-right" id="p_approve" value="Approve User"
							type="submit"> <input class="btn btn-danger"
							id="p_dis_approve" value="Disapprove User" type="submit">
						<%
							}
						%>
					</div>
							
							


							<div style="position: absolute; right: -84px; top: 0px;"btnbtn-primary">
								<a
									href="EditUserDetail.jsp?Uid=<%=PasswordEncryption.encryptBase64(userDetail.getUserId())%>">
									<span style="width: 100px; padding: 0 0 0 30px;"><img
										alt="" src="images/edit.png"><br>
										<p class="text-center">Edit Profile</p> </span>
								</a>
							</div>



						</div>
						<div class="questionnary_list">
							<h2>Personal Details</h2>
							<h4>Status : 
							 <%
								if (userDetail.getActivationStatus().equals("0")) {
							%>
							<span style="color: red;">DisApproved</span>
							<%
								} else if (userDetail.getActivationStatus().equals("1")) {
							%>
							<span style="color: green;">Approved</span>
							<%
								} else if (userDetail.getActivationStatus().equals("2")) {
							%>
							<span style="color: blue;">Pending</span>
							<%
								}
							%> 
							
							</h4>
							<ul>
								<li><b>User Name :</b><span><%=userDetail.getUserName()%></span></li>
								<li><b>Email - ID :</b><span><%=userDetail.getEmailId()%></span></li>
								<li><b>Profession :</b><span><%=userDetail.getProfession()%></span></li>
								<li><b>Mobile No :</b><span><%=userDetail.getMobileNo()%></span></li>
								<li><b>Region :</b><span><%=userDetail.getRegion()%></span></li>
								<li><b>State :</b><span><%=userDetail.getState()%></span></li>
								<li><b>District :</b><span><%=userDetail.getDistric()%></span></li>
								<li><b>City :</b><span><%=userDetail.getCity()%></span></li>
								<li><b>Address :</b><span><%=userDetail.getAddress()%></span></li>
								<li><b>Pincode :</b><span><%=userDetail.getPinCode()%></span></li>
								<li><b>Firm Name :</b><span><%=userDetail.getFirmName()%></span></li>
								<li><b>Project handle :</b><span><%=userDetail.getProjectHandled()%></span></li>
								<li><b>Qualification :</b><span><%=userDetail.getQualification()%></span></li>
								<li><b>Experience :</b><span><%=userDetail.getExperienceYears()%></span></li>
								<li><b>Specialization :</b><span><%=userDetail.getSpecialization()%></span></li>
								<li><b>Date of Registration :</b><span><%=userDetail.getDateOfRegistration()%></span></li>
							    <li><b>Description :</b><span><%=userDetail.getDescription()%></span></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="job_photo">
					<%
						for (int i = 0; i < userDetail.getProjects().size(); i++) {
							machine = new StringBuilder();
							manpower = new StringBuilder();
							for (int j = 0; j < userDetail.getProjects().get(i).getMachinary().size(); j++) {
								machine.append(userDetail.getProjects().get(i).getMachinary().get(j).getMachineriesType() + ":"
										+ userDetail.getProjects().get(i).getMachinary().get(j).getNoOfMachineries() + "\n");
							}
							for (int j = 0; j < userDetail.getProjects().get(i).getManPower().size(); j++) {
								manpower.append(userDetail.getProjects().get(i).getManPower().get(j).getManpowerType() + ":"
										+ userDetail.getProjects().get(i).getManPower().get(j).getNoOfManpower() + "\n");
							}
					%>

					<%
						if (userDetail.getProjects() != null && userDetail.getProjects().size() > 0) {
					%>

					<h1>Project</h1>
					<div class="edit">
						<div class="row">
							<div class="col-lg-12">
								<div class="project_name">
									<h2 class="text-danger"><%=userDetail.getProjects().get(i).getProjectName()%></h2>
								</div>

								<%
									for (int j = 0; j < userDetail.getProjects().get(i).getImageData().size(); j++) {
								%>
								<div class="col-lg-3">
									<img class="border_s" style="width: 100%" height="auto"
										src=<%=userDetail.getProjects().get(i).getImageData().get(j)%>
										alt="" />
								</div>
								<%
									}
								%>
							</div>

							<div class="col-lg-12">
								<div style="position: absolute; right: 10px; top: -35px;">
									<a
										href="EditProject.jsp?proj_id=<%=PasswordEncryption.encryptBase64(userDetail.getProjects().get(i).getProjectId())%>">
										<span style="width: 100px; padding: 0 0 0 30px;"><img
											alt="" src="images/edit.png"><br>
											<p class="text-center">Edit Project</p> </span>
									</a>
								</div>
								<h2>Description</h2>
								<ol>
									<li><b>Values of Project</b><span><%=userDetail.getProjects().get(i).getProjectPrice()%>
									</span></li>
									<li><b>Area</b><span><%=userDetail.getProjects().get(i).getArea()%></span></li>
									<li><b>State</b><span><%=userDetail.getProjects().get(i).getStateName()%></span></li>
									<li><b>District</b><span><%=userDetail.getProjects().get(i).getDistric()%></span></li>
									<li><b>City</b><span><%=userDetail.getProjects().get(i).getCityName()%></span></li>
									<li><b>Address</b><span><%=userDetail.getProjects().get(i).getProjectLocation()%></span></li>
									<li><b>Manpower</b><span><%=manpower%></span></li>
									<li><b>Machinary</b><span><%=machine%></span></li>
								</ol>
							</div>
						</div>
						<%
							if (userDetail.getProjects().get(i).getIs_Active().equals("0")) {
						%>
						<input class="btn btn-success"
							onclick="changeStatus(<%=userDetail.getProjects().get(i).getProjectId()%>,11)"
							value="Approve" type="submit">
						<%
							} else if (userDetail.getProjects().get(i).getIs_Active().equals("1")) {
						%>
						<input class="btn btn-danger"
							onclick="changeStatus(<%=userDetail.getProjects().get(i).getProjectId()%>,12)"
							value="Disapprove" type="submit">
						<%
							} else if (userDetail.getProjects().get(i).getIs_Active().equals("2")) {
						%>
						<input class="btn btn-success"
							onclick="changeStatus(<%=userDetail.getProjects().get(i).getProjectId()%>,11)"
							value="Approve" type="submit"> <input
							class="btn btn-danger"
							onclick="changeStatus(<%=userDetail.getProjects().get(i).getProjectId()%>,12)"
							value="Disapprove" type="submit">
						<%
							}
						%>
					</div>
					<%
						}
					%>
					<%
						}
					%>
			
				</div>
			</div>
		</div>
	</div>


	<div class="admin_footer">Dalmia</div>
</body>

</html>