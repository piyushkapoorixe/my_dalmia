package com.AdminServiceAjax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.constants.MessageConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.model.MachinaryModel;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class AjaxDeleteUser
 */
public class AjaxDeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
    private ServletContext context;
	private Statement stmt;
	private String Tag=null;
	private String delId=null;
	
	
    public AjaxDeleteUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		mWriter=response.getWriter();
		  context = getServletContext( );
		  delId=request.getParameter("Id");
			deleteUser(request, response);
			
			
		
		
	}
	
	void deleteUser(HttpServletRequest req, HttpServletResponse response)
	{
		try{
			mWriter=response.getWriter();
			mDbUtils=new DBUtils();
			mCon=mDbUtils.generateConnection();		
			if(mCon!=null)
			{
				stmt = mCon.createStatement();
				try {
					String sql="DELETE FROM users_details WHERE user_Id = ?" ;
					
					context.log(sql);
					PreparedStatement preparedStmt = mCon.prepareStatement(sql);
					preparedStmt.setString(1, delId);
					
					preparedStmt.executeUpdate();
					findProjectId(delId);
					deleteprojects(delId);
					mWriter.println("Deleted  succefully");	
				} catch (Exception e) {
					mWriter.println(e.getLocalizedMessage());
				}
			}else
			{
				mWriter.println("Database Problem Ocur");
			}
		}catch(Exception e)
		{
			mWriter.println(e.getLocalizedMessage());
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
				
			}
		}

	}


	private void findProjectId(String id) {
		// TODO Auto-generated method stub
		try {
			ArrayList<String> proj_ids=new ArrayList<>();
			Statement stmt = mCon.createStatement();
			String sql="SELECT id from users_project where U_Id = "+id;
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				proj_ids.add(rs.getString("id"));
			}
			if(proj_ids.size()>0)
			deleteProjectImages(proj_ids);
			
		} catch (Exception e) {
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
		}
		
	}
	
	void deleteprojects(String id)
	{
		try {
			String query="Delete  FROM  users_project WHERE U_Id ='"+id+"'" ;
			System.out.println(query);
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			mWriter.println(e.getLocalizedMessage());
		}	
		
	}


	private void deleteProjectImages(ArrayList<String> values) {
		//StringBuffer ids=new StringBuffer();
		StringBuilder ids=new StringBuilder();
		
		for(int i=0;i<values.size();i++)
		{
			ids.append(values.get(i)).append(",");	
		}
		
		String finalIds=ids.toString().substring(0,ids.toString().length()-1);
		
		
		
		// TODO Auto-generated method stub
		try {
			String query="Delete  FROM  users_project_image WHERE Project_Id IN  ("+finalIds+")" ;
			
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			
			preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			mWriter.println(e.getLocalizedMessage());
		}
		
	}


}
