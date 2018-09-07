<%@page import="com.model.VideoModel"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.utils.WebProjectLIstUtils"%>
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
<%!
ArrayList<MasterRegionModel> listRegion=null;
String editId=null;
VideoModel eventData=null; 


 ArrayList<MasterRegionModel> getMainRegionList()
{
		Connection con=null;
		ArrayList<MasterRegionModel> listRegion=null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql="SELECT * from master_stateregion where IsActive='1' Order By Region Asc" ;
			ResultSet rs = stmt.executeQuery(sql);
			listRegion=new ArrayList<>();
			while (rs.next()) {
				MasterRegionModel data=new MasterRegionModel();
				data.setId(rs.getString("id"));
				data.setRegion(rs.getString("Region"));
				listRegion.add(data);
			}
			rs.close();
			return listRegion;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}

		}
		return null;
	
}
%>

<%



listRegion=getMainRegionList();
Connection con=null;
if (request.getParameter("id") != null && request.getParameter("id").trim().length() > 0
		&& (!request.getParameter("id").equals("null"))) {
	editId = PasswordEncryption.dycriptBase64(request.getParameter("id").getBytes());
	try {
		con = DBConnectionProvider.getCon();
		PreparedStatement ps = con.prepareStatement("SELECT master_tvc_videos.*,master_stateregion.Region from master_tvc_videos Join master_stateregion On master_stateregion.id=master_tvc_videos.Region_Id where master_tvc_videos.Id="+ editId);
		ResultSet rs = ps.executeQuery();
		eventData = new VideoModel();
		if (rs.next()) {
			eventData = new VideoModel();
			eventData.setId(rs.getString("Id"));
			eventData.setRegionId(rs.getString("Region_Id"));
			eventData.setRegion(rs.getString("Region"));
			eventData.setPath(rs.getString("Video_Path"));
			eventData.setTitle(rs.getString("Title"));
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		DBConnectionProvider.close(con);
	}
} else {
	eventData = null;
	editId=null;
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
<script>
</script>
</head>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0-alpha1/jquery.js"></script>
<script type='text/javascript' src="js/jquery.min.js"></script>
<script type='text/javascript' src="js/jquery.blockUI.js"></script>
<script type='text/javascript'>
$(document).ready(
		function() {
			$(document).ajaxStop($.unblockUI);
            $(document).ajaxStart(function () { $.blockUI({ message: '<div class="load_1"><img src="images/loading.gif" /><p>Please Wait...</p></div>' }); });
		});

	$(document).ready(function() {
		$("#upload").click(function() {
			var idEdit = $('#upload').attr('data-value');
			var filePath = $('#video_url').val();
			var regionId = $('#region_id').val();
			var title = $('#title').val();
			
			if (regionId == null || regionId.length <= 0) {
				alert("Please Select Region");
				return;
			}
			if (title == null || title.length <= 0) {
				alert("Please Enter the video Title");
				return;
			}
			if (filePath == null || filePath.length <= 0) {
				alert("Please Enter the Url ");
				return;
			}
			
			if (idEdit === '') {
				 $.ajax({
						type : 'POST',
						url : 'AjaxEditVideo',
						data :'Title=' + title +
						      '&RegionId='+ regionId +
						      '&Tag=AddNew'+
						      '&Path='+filePath,
						success : function(data2) {
							$('#success').html(data2);
							alert(data2);
						},
						error : function(data) {
							alert('fail');
						}
					});
			}else
				{
				 $.ajax({
						type : 'POST',
						url : 'AjaxEditVideo',
						data :'Title=' + title +
						      '&RegionId='+ regionId +
						      '&Tag=Edit'+
						      '&id='+idEdit +
						      '&Path='+filePath,
						success : function(data2) {
							$('#success').html(data2);
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
	<div class="col-lg-12">
			<input type="submit" onclick="window.history.go(-1); return false;"
					value="Back" class="btn_inp_al" />
		</div>
		<div id="loginbox"
			class="mainbox col-md-4 col-md-offset-4 col-sm-8 admin_d col-sm-offset-2">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">
						<%
							if (eventData != null && eventData.getId() != null) {
						%>
						<h3>Edit Video Details</h3>
						<%
							} else {
						%>
						<h3>Upload Video</h3>
						<%
							}
						%>

					</div>
				</div>

				<div style="padding: 10px 30px 0px 30px" class="panel-body">
					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>
					<div class="form-group">
						<label for="inputPassword">Region</label> <select
							class="form-control input-sm" id="region_id">
							<%
								for (int i = 0; i < listRegion.size(); i++) {
							%>
							
							<option
								<%if (eventData != null && eventData.getRegionId().equals(listRegion.get(i).getId())) {%>
								selected="" <%}%> value=<%=listRegion.get(i).getId()%>><%=listRegion.get(i).getRegion()%></option>
							<%
								}
							%>
						</select>
					</div>

					<div class="form-group">
						<label for="inputPassword">Title</label> <input type="text"
							id="title" class="login-inpt" placeholder="Enter the Title"
							<%
							if(eventData!=null && eventData.getTitle()!=null)
							{
							%>
							value="<%=eventData.getTitle() %>" <%}else
							{
							%>
							value="" <%	
							}
							%>>
					</div>

					<div class="form-group">
						<label for="inputPassword">Link</label> <input type="text"
							id="video_url" class="login-inpt"
							placeholder="Enter Youtube Video Url"
							<%
							if(eventData!=null && eventData.getPath()!=null)
							{
							%>
							value=<%=eventData.getPath()%>
							<%}else
							{
							%> value="" <%	
							}
							%>>
					</div>
					<div class="form-group">
						<div class="controls">
							<a id="upload" class="btn btn-xm btn-primary"
								<%
					if(eventData!=null && eventData.getId()!=null)
					{%>
								data-value=<%=eventData.getId()%>>Apply Changes<%}else
					{
					%>
								data-value="" >Upload<%	
					}
					%>
					
					</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-----footer--->

	<div class="admin_footer">Dalmia</div>

	<!-----end of footer--->
	</form>
	</div>
</body>

</html>