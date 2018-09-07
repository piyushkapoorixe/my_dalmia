<%@page import="com.model.ConstructionTipsModel"%>
<%@page import="java.sql.SQLException"%>
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


<%!ArrayList<ConstructionTipsModel> listTips;
	public void getTips() {
		Connection con = null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "SELECT * from master_cunstruction";
			ResultSet rs = stmt.executeQuery(sql);
			listTips = new ArrayList<ConstructionTipsModel>();
			while (rs.next()) {
				ConstructionTipsModel item = new ConstructionTipsModel();
				item.setHeading(rs.getString("Heading"));
				item.setId(rs.getString("Id"));
				item.setTips(rs.getString("Tips"));
				item.setCreatedOn(rs.getString("CreatedOn"));
				listTips.add(item);
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
<link type="text/css" href="css/login.css" rel="stylesheet" />
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script>
	
</script>
</head>
<script type='text/javascript' src="js/jquery.min.js"></script>
<script type='text/javascript' src="js/jquery.blockUI.js"></script>
<script type='text/javascript'>
$(document).ready(
		function() {
			$(document).ajaxStop($.unblockUI);
            $(document).ajaxStart(function () { $.blockUI({ message: '<div class="load_1"><img src="images/loading.gif" /><p>Please Wait...</p></div>' }); });
		});
	$(document).ready(function() {
		$(".del").click(function() {
			var del = $(this).children().attr('data-string');
			if (confirm("Are you sure, want to Delete this ?")) {
				$.ajax({
					type : 'POST',
					url : 'AjaxConstructionTipsController',
					data : 'Id=' + del + '&Tag=Del',
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
			return false;
		});
	});
</script>
<body>
	<div class="container">
		<div class="col-lg-12">
			<a href="AddNewConstruction.jsp" class="btn btn-info btn-lg">Add New</a>
		</div>
		<div class="admin_d" style="margin: 10px 0px 50px 0px">
			<%
				getTips();
				for (int i = 0; i < listTips.size(); i++) {
			%>
			<div class="col-lg-12" style="border-bottom: solid 1px #e2e2e2;">
				<div class="faq">
					<div class="lefts">
						<h4><%="Que." + (i + 1)%></h4>
					</div>
					<div class="middle">
						<h4><%=listTips.get(i).getHeading()%></h4>
						<p><%=listTips.get(i).getTips()%></p>
					</div>
					<div class="faq_r">
						<div class="edit">
							<a
								href="AddNewConstruction.jsp?id=<%=PasswordEncryption.encryptBase64(listTips.get(i).getId())%>" class="edit">
								<img src="images/edit.png" width="16" height="16" data-string=<%=listTips.get(i).getId()%> />
								</a>
						</div>
						<div class="edit">
							<a class="del"><img src="images/delete-button.png" width="16"
								height="16" data-string=<%=listTips.get(i).getId()%> /></a>
						</div>
					</div>
				</div>
			</div>
			<%
				}
			%>
		</div>
	</div>


	<!-----footer--->
	<div class="admin_footer">Dalmia</div>
	<!-----end of footer--->
	</form>
	</div>

</body>

</html>