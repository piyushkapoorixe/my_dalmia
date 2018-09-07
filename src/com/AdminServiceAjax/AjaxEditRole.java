package com.AdminServiceAjax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.listener.DBConnectionProvider;

/**
 * Servlet implementation class AjaxEditRole
 */
public class AjaxEditRole extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpServletRequest mRequest;
	private PrintWriter mWriter;
	public AjaxEditRole() {
		super();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		mRequest=request;
		mWriter=response.getWriter();
		if(request.getParameter("Tag").equals("AddNew"))
		{
			addRole(request, response);	
		}
		else if(request.getParameter("Tag").equals("Edit"))
		{
			editRole(request, response);
		}

	}
	
	void addRole(HttpServletRequest request, HttpServletResponse response)
	{
		String Role=request.getParameter("Role");
		Connection con=null;
		try {
			con = DBConnectionProvider.getCon();
			String insertQuery="INSERT INTO master_admin_role(RoleType)" + " VALUES ('"+Role+"')";
			PreparedStatement preparedStmt = con.prepareStatement(insertQuery);
			preparedStmt.execute();
			mWriter.println(Role+" Inserted succefully");	
		} catch (Exception e) {
			mWriter.println(e.getLocalizedMessage());
		}
		finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}

		}
	}

	void editRole(HttpServletRequest request, HttpServletResponse response)
	{
		String id=request.getParameter("Id");
		String Role=request.getParameter("Role");
		
		try {
			Connection mCon=DBConnectionProvider.getCon();
			String region="UPDATE  master_admin_role SET RoleType= ? where Id=?" ;
			PreparedStatement preparedStmt = mCon.prepareStatement(region);
			preparedStmt.setString   (1, Role);
			preparedStmt.setString   (2, id);
			preparedStmt.executeUpdate();
			mWriter.println("Updated succefully");	
		} catch (Exception e) {
			mWriter.println(e.getLocalizedMessage());
		}

	}


}
