package com.AdminServiceAjax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.listener.DBConnectionProvider;

/**
 * Servlet implementation class AjaxFilterDistric
 */
public class AjaxFilterDistric extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpServletRequest mRequest;
	private PrintWriter mWriter;
	public AjaxFilterDistric() {
		super();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		mRequest=request;
		mWriter=response.getWriter();
		filterDistic(request,response);
		

	}
	void filterDistic(HttpServletRequest request, HttpServletResponse response)
	{
		int StateId=Integer.parseInt(request.getParameter("StateId"));
		Connection con=null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql="SELECT * from master_distric where isactive='1' AND stateId='"+StateId+"'" ;
			ResultSet rs = stmt.executeQuery(sql);
			StringBuilder html=new StringBuilder();
			while (rs.next()) {
				html.append("<option value="+rs.getString("id")+">");
				html.append(rs.getString("name"));
				html.append("</option>");
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
