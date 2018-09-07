package com.AdminServiceAjax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.listener.DBConnectionProvider;
import com.utils.PasswordEncryption;
import com.webmodel.PersonModel;

/**
 * Servlet implementation class FilterController
 */
public class FilterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpServletRequest mRequest;
	private PrintWriter mWriter;
    public FilterController() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		mRequest=request;
		mWriter=response.getWriter();
		getFIlterBasedOnStatus(request,response);
	}
	void getFIlterBasedOnStatus(HttpServletRequest request, HttpServletResponse response)
	{
		int ProfessionId=Integer.parseInt(request.getParameter("ProfessionId"));
		int status=Integer.parseInt(request.getParameter("BasedOn"));
		Connection con=null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql=null;
			if(status==3)
			{
				sql = "Select users_details.user_Id,users_details.user_Name,users_details.createDate,users_details.IsActive,users_details.Address,master_stateregion.Region "
						+ "from users_details Join master_stateregion on master_stateregion.id=users_details.RegionId where profession_Id='"+ProfessionId+"' Order by createDate Desc";	
			}
			else
			{
				sql = "Select users_details.user_Id,users_details.user_Name,users_details.createDate,users_details.IsActive,users_details.Address,master_stateregion.Region "
						+ "from users_details Join master_stateregion on master_stateregion.id=users_details.RegionId where users_details.profession_Id='"+ProfessionId+"'"+" AND users_details.IsActive='"+status+"' Order by createDate Desc";	
			}
			ResultSet rs = stmt.executeQuery(sql);
			StringBuilder html=new StringBuilder();
			int i=0;
			while (rs.next()) {
				i++;
				String status_1;
				if (rs.getString("IsActive").equals("1")) {
					status_1="<td align='center' class='text-success'>Approved</td>";
				} else if (rs.getString("IsActive").equals("0")) {
					status_1="<td align='center' class='text-danger'>DisApproved</td>";
				} else {
					status_1="<td align='center' class='text-primary'>Pending</td>";
				}
				html.append("<tr>");
				html.append("<td>"+i+"</td>");
				html.append("<td>"+rs.getString("user_Name")+"</td>");
				html.append("<td>"+rs.getString("Address")+"</td>");
				html.append("<td align='center'>"+rs.getString("createDate")+"</td>");
				html.append(status_1);
				html.append("<td align='center'>"+rs.getString("Region")+"</td>");
				html.append("<td align='center'><form action='UserProjectAndProfile.jsp' method='get'><input type='hidden' value='"+PasswordEncryption.encryptBase64(rs.getString("user_Id"))+"' name='Uid'> <input type=" + "'submit' value='View' class='btn btn-success'></form></td>");
				html.append("<td align='center'><form action='ResetPassword.jsp' method='get'><input type='hidden' value='"+PasswordEncryption.encryptBase64(rs.getString("user_Id"))+"' name='Uid'> <input type=" + "'submit' value='Reset Password' class='btn btn-success'></form></td>");
				html.append("</tr>");
			}
			mWriter.println(html);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}

		}
	}
}
