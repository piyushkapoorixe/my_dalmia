<%@page import="java.io.PrintWriter"%>
<%@page import="com.utils.WebProjectLIstUtils"%>
<%@page import="com.webmodel.AdminListModel"%>
<%
	if (request.getParameter("name") != null && request.getParameter("pass") != null) {
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		Object object=WebProjectLIstUtils.validateUser(name, pass);
		if(object!=null)
		{
			if (object instanceof AdminListModel) {
				AdminListModel model=null;
				model=(AdminListModel)object;
				session.setAttribute("obj", model);
				response.sendRedirect("HomePage.jsp");
			}
			else 
			{
				   out.println("<script type=\"text/javascript\">");
				   out.println("alert('"+object.toString()+"');");
				   out.println("</script>");
			}	
		}
		else
		{
			   out.println("<script type=\"text/javascript\">");
			   out.println("alert('User or password incorrect');");
			   out.println("</script>");
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
<link type="text/css" href="css/login.css" rel="stylesheet" />
<link rel="stylesheet" href="css/bootstrap.min.css" />
</head>
<body style="background: url(images/dalnia_bg.jpg) no-repeat; background-size: cover; height:100%;">


	<form action="index.jsp" method="post">
		<div class="container">
			<div class="col-lg-4 col-md-4 col-sm-4">&nbsp;</div>
			<div class="col-lg-4 col-md-4 col-sm-4 login-wrap">
				<div class="login-innr">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td class="login-logo">
								<img src="images/logo1.png" alt=""/>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td><span id="lblError" style="color: Red;"></span> <input
								name="name" type="text" id="txtusername" class="login-inpt"
								placeholder="User Id" /></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td><input name="pass" type="password" id="txtpassword"
								class="login-inpt" placeholder="Password" /></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>
								<table width="100%">
									<tr>
										<td width="6%"><input id="chkRemember" type="checkbox"
											name="chkRemember" /></td>
										<td width="94%">&nbsp;Keep me signed in</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td><input type="submit" name="btnsubmit" value="Login"
								id="btnsubmit" class="logn-submt" /></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
</body>
</html>

