<%@page import="com.webmodel.AdminListModel"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.utils.PasswordEncryption"%>
<%@page import="com.listener.DBConnectionProvider"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<jsp:include page="header.jsp" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">

<title>Dalmia</title>

<link rel="shortcut icon" href="/image/favicon.ico" type="image/x-icon" />
<link rel="icon" href="/image/favicon.ico" type="image/ico" />
<!-- Bootstrap framework -->
<link type="text/css" href="css/admin-style.css" rel="stylesheet" />
<link rel="stylesheet" href="css/bootstrap.min.css" />
<body>
	<!-----middle--->
	<div class="container">
		<section>
			<div class="admin_right">
				<ul>
					<%
						if (session.getAttribute("obj") == null)
							return;
					   AdminListModel model = (AdminListModel) session.getAttribute("obj");
					   if(model.getRoleId().equals("1"))
					   {
						%>
						<li><a href="AdminList.jsp"> <img
							src="images/create_admin.png" alt="CREATE ADMIN" />
							<p>MANAGE ADMIN</p>
					</a></li>
					<li><a href="Master_RoleType.jsp"> <img
							src="images/admin_role.png" alt="STATE REGION MASTER" />
							<p>ADMIN ROLE</p>
					</a></li>
					<li><a href="Master_Region.jsp"> <img
							src="images/state_region_master.png" alt="STATE REGION MASTER" />
							<p>ZONE MASTER</p>
					</a></li>

					<li><a href="Master_State.jsp"> <img
							src="images/state_master.png" alt="STATE MASTER" />
							<p>STATE MASTER</p>
					</a></li>
					
					<li><a href="Master_UsersRegion.jsp"> <img
							src="images/user_region.png" alt="USERS REGION" />
							<p>REGION MASTER</p>
					</a></li>
					
					<li><a href="DistricMaster.jsp"> <img
							src="images/district_master.png" alt="DISTRIC MASTER" />
							<p>DISTRICT MASTER</p>
					</a></li>

					<li><a href="Master_City.jsp"> <img
							src="images/city_master.png" alt="CITY MASTER" />
							<p>CITY MASTER</p>
					</a></li>
					<li><a href="PinMaster.jsp"> <img
							src="images/pin_master.png" alt="PIN MASTER" />
							<p>PIN MASTER</p>
					</a></li>

					<li><a href="Master_Machineries.jsp"> <img
							src="images/machineries.png" alt="MACHINERIES" />
							<p>MACHINERIES</p>
					</a></li>

					<li><a href="Master_Manpower.jsp"> <img
							src="images/man-power.png" class="test" alt="MANPOWER" />
							<p>MANPOWER</p>
					</a></li>

					<li><a href="Master_Profession.jsp"> <img
							src="images/profession.png" alt="PROFESSION" />
							<p>PROFESSION</p>
					</a></li>

					<li><a href="Master_Qualification.jsp"> <img
							src="images/qualification.png" alt="QUALIFICATION" />
							<p>QUALIFICATION</p>
					</a></li>

					<li><a href="MAster_Specialization.jsp"> <img
							src="images/specialization.png" alt="SPECIALIZATION" />
							<p>SPECIALIZATION</p>
					</a></li>

					<li><a href="Master_QueryFeedback.jsp"> <img
							src="images/query.png" alt="QUERY / FEEDBACK" />
							<p>QUERY / FEEDBACK</p>
					</a></li>
					<li><a href="UsersQueryFeedback.jsp"> <img
							src="images/feedback.png" alt="USER'S QUERY / FEEDBACK" />
							<p>USER'S QUERY / FEEDBACK</p>
					</a></li>

					<li><a href="UserManagement.jsp"> <img
							src="images/management.png" alt="USER MANAGEMENT" />
							<p>USER MANAGEMENT</p>
					</a></li>
					<li><a href="CallHistory.jsp"> <img
							src="images/call_history.png" alt="CALL HISTORY" />
							<p>CALL HISTORY</p>
					</a></li>
					<li><a href="ServiceRequest.jsp"> <img
							src="images/service_request.png" alt="SERVICE REQUEST" />
							<p>SERVICE REQUEST</p>
					</a></li>
					<li><a href="VideoList.jsp"> <img
							src="images/videos.png" alt="Upload Videos" />
							<p>TVC's VIDEOS</p>
					</a></li>
					<li><a href="Faqs.jsp"> <img src="images/faq.png"
							alt="Faq's" />
							<p>Faq's</p>
					</a></li>
					<li><a href="LandmarkPage.jsp"> <img
							src="images/land_mark.png" alt="LandMark Projects" />
							<p>LANDMARK PROJECTS</p>
					</a></li>

					<li><a href="BannerList.jsp"> <img
							src="images/upload_banner.png" alt="Upload Banner" />
							<p>BANNERS</p>
					</a></li>

					<li><a href="Events.jsp"> <img src="images/events.png"
							alt="Events" />
							<p>EVENTS</p>
					</a></li>

					<li><a href="ConstructionsTips.jsp"> <img
							src="images/constructions_tips.png" alt="Constructions Tips" />
							<p>CONSTRUCTION TIPS</p>
					</a></li>
					<li><a href="DalmiaCementShopList.jsp"> <img
							src="images/shop.png" alt="Constructions Tips" />
							<p>CEMENT SHOPS</p>
					</a></li>
						<%   
					   }else
					   {
						%>   
						  <li><a href="UserManagement.jsp"> <img
							src="images/management.png" alt="USER MANAGEMENT" />
							<p>USER MANAGEMENT</p>
					</a></li> 
					 <%  }
						
					%>
					
				</ul>
			</div>
		</section>

	</div>
	<!-----end of middle--->
	<!-----footer--->
	<div class="admin_footer">Dalmia</div>

	<!-----end of footer--->


	</form>
</body>

</head>
</html>










