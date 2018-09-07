<%@page import="com.utils.WebProjectLIstUtils"%>
<%@page import="com.webmodel.AdminListModel"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.constants.WebConstants"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.webmodel.MasterCity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.webmodel.PersonModel"%>
<jsp:include page="header.jsp"></jsp:include>
<%!ArrayList<PersonModel> listPerson = null;
String sql =null;
	private Connection con;
	public void getUserData() {
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			listPerson = new ArrayList<PersonModel>();
			while (rs.next()) {
				PersonModel item = new PersonModel();
				item.setDateOfRegistration(rs.getString("createDate"));
				item.setId(rs.getString("user_Id"));
				item.setLocation(rs.getString("Address"));
				item.setName(rs.getString("user_Name"));
				if (rs.getString("IsActive").equals("1")) {
					item.setStatus("Approved");
				} else if (rs.getString("IsActive").equals("0")) {
					item.setStatus("DisApproved");
				} else {
					item.setStatus("Pending");
				}
				item.setZone(rs.getString("zone"));
				item.setDisTName(rs.getString("dist_name"));
				item.setState(rs.getString("state"));
				item.setRegion(rs.getString("region"));
				item.setCity(rs.getString("city"));
				listPerson.add(item);
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
	AdminListModel model = (AdminListModel) session.getAttribute("obj");
	sql=WebProjectLIstUtils.getQueryForAdminRole(model.getRoleId(), "5", model.getDistricId(), model.getState_RegionId(), model.getRegionId());
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

		$(document).bind("ajaxSend", function(){
			   $("#loading").show();
			 }).bind("ajaxComplete", function(){
			   $("#loading").hide();
			 })
		
		/* function() {
			$(document).ajaxStop($('html').unblock());
            $(document).ajaxStart(function () { $.blockUI({ message: '<div class="load_1"><img src="images/loading.gif" /><p>Please Wait...</p></div>' }); });
		} */);
	function populate(values) {
		$.ajax({
			type : 'POST',
			url : 'FilterController',
			data : '&ProfessionId=5' + '&BasedOn=' + values,
			success : function(data2) {
				$('#success').html(data2);
				$("#data_come").html(data2);
			},
			error : function(data) {
				alert('fail');
			}
		});

	}
	
 	function deleteFunction(str) {
		var del_var =  str;
		//alert(del_var);
		if (confirm("Are you sure, want to Delete this ?")) {
			//alert('data');
			 $.ajax({
				 
				   type: 'POST',
		            url: 'AjaxDeleteUser', 
		            data : 'Id=' + del_var,
		           
		        })
		    
		        .done(function(data){
		          //  $('#response').html(data);    
		        	  
		            $('#success').html(data);
					alert(data);
					location.reload();
		          
		        })
		        .fail(function() {
		            alert( "Posting failed." );

		        }); 
	}
	
 	}
	

	 /* $(document).ready(function() {
		$(".del").click(function() {
			var del = $(this).attr('data-string');
			//alert(del);
			if (confirm("Are you sure, want to Delete this ?")) {
				//alert('data');
				 $.ajax({
					 
					   type: 'POST',
			            url: 'AjaxDeleteUser', 
			            data : 'Id=' + del ,
			           
			        })
			    
			        .done(function(data){
			        	  $('#response').html(data); 
			        	  alert(data)
			        	  location.reload();
			        })
			        .fail(function() {
			            alert( "Posting failed." );

			        });
			}
			return false;
		});
	});  */
</script>
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/r/bs-3.3.5/jq-2.1.4,dt-1.10.8/datatables.min.css" />
<script type="text/javascript"
	src="https://cdn.datatables.net/r/bs-3.3.5/jqc-1.11.3,dt-1.10.8/datatables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.flash.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.html5.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.colVis.min.js"></script>
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
	var table = $('#example').DataTable({
        "scrollX": true
    });

	new $.fn.dataTable.Buttons(table, {
		buttons : [ {
			extend : 'excelHtml5',
			text : 'Excel Download',
			className : 'btn btn-info btn-lg',
			title : 'Consumers_List',
			exportOptions: {
                columns: [0,1,2,3,4,5,6]
            }
		} ]
	}).container().appendTo($('#ExportToExcel'));
	
	new $.fn.dataTable.Buttons(table, {
		buttons : [ {
			extend : 'columnsToggle'
		} ]
	}).container().appendTo($('#ColumnVisibility'));
});
</script>
<script>
	
</script>
</head>

<body>
	<div class="container">
		<table class="col-lg-12">
			<tr>
				<td><input type="submit"
					onclick="window.history.go(-1); return false;" value="Back"
					class="btn_inp_al" /></td>
				<td style="width: 130px;" align="right">
					<div id="ExportToExcel"></div>
				</td>
			</tr>
			<tr>
				<td>
					<h3>Consumers</h3>
				</td>
				<td style="width: 1000px;" align="right">
					<div id="ColumnVisibility" style="margin: 10px 0px;"></div>
				</td>
			</tr>
		</table>
		<p></p>
		<div class="details">
			<table id="example" class="display table table-bordered">
				<thead>
					<tr>
						<th>S.N.</th>
						<th>User Name</th>
						<th>Location</th>
						<th style="text-align: center">City</th>
						<th style="text-align: center">District</th>
						<th style="text-align: center">Region</th>
						<th style="text-align: center">State</th>
						<th style="text-align: center">Zone</th>
						<th style="text-align: center">Date of Registration</th>
						<th style="text-align: center">Status</th>
						<!-- <th style="text-align: center"><select
									onchange="populate(this.value)">
										<option value="3">All</option>
										<option value="0">Dis-Approved</option>
										<option value="1">Approved</option>
										<option value="2">Pending</option>
								</select></th> -->
						<th style="text-align: center">View Details</th>
						<th style="text-align: center">Action</th>
						<th style="text-align: center">Profile Delete</th>
					</tr>
				</thead>
				<tbody id="data_come">
					<%
						getUserData();
						for (int i = 0; i < listPerson.size(); i++) {
					%>
					<tr <%if (listPerson.get(i).getStatus().equals("Approved")) {%>
						class="text-black" <%} else {%> class="text-blurred" <%}%>>
						<td><%=i + 1%></td>
						<td><%=listPerson.get(i).getName()%></td>
						<td><%=listPerson.get(i).getLocation()%></td>
						<td align="center"><%=listPerson.get(i).getCity()%></td>
						<td align="center"><%=listPerson.get(i).getDisTName()%></td>
						<td align="center"><%=listPerson.get(i).getRegion()%></td>
						<td align="center"><%=listPerson.get(i).getState()%></td>
						<td align="center"><%=listPerson.get(i).getZone()%></td>
						<td align="center"><%=listPerson.get(i).getDateOfRegistration()%></td>
						<td align="center"
							<%if (listPerson.get(i).getStatus().equals("Approved")) {%>
							class="text-success"
							<%} else if (listPerson.get(i).getStatus().equals("DisApproved")) {%>
							class="text-danger" <%} else {%> class="text-primary" <%}%>><%=listPerson.get(i).getStatus()%></td>

						<td align="center">
							<form action="UserProjectAndProfile.jsp" method="get">
								<input type="hidden"
									value=<%=PasswordEncryption.encryptBase64(listPerson.get(i).getId())%>
									name="Uid"> <input type="submit" value="View"
									class="btn btn-success">
							</form>
						</td>
						<td align="center">
							<form action="ResetPassword.jsp" method="get">
								<input type="hidden" value=value=
									<%=PasswordEncryption.encryptBase64(listPerson.get(i).getId())%>
									name="Uid"> <input type="submit" value="Reset Password"
									class="btn btn-success">
							</form>
						</td>

						<td align="center">
						<input type="button" class="del"
							onclick="deleteFunction(<%=listPerson.get(i).getId()%>)"
							value="Delete User"/>

						</td>
					</tr>
					<%
						}
					%>
				</tbody>

			</table>
		</div>
	</div>
	<!-----end of middle--->



	<!-----footer--->

	<div class="admin_footer">Dalmia</div>

	<!-----end of footer--->
</body>

</html>