package com.AdminServiceAjax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.DBUtils;
import com.listener.DBConnectionProvider;

/**
 * Servlet implementation class AjaxConstructionTipsController
 */
public class AjaxConstructionTipsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private Statement stmt;
	private String delId=null;
	private String Tag=null;
	private String addTipsHeading=null;
	private String addTips=null;
	private String idEdit=null;
	public AjaxConstructionTipsController() {
		super();
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		mWriter=response.getWriter();
		Tag=req.getParameter("Tag");
		if(Tag.equals("AddNew"))
		{
			// requested from AddNewConstruction.jsp  page to Add  a  new row 
			if(validateField(req, response))
			{
				AddNewTips(req, response);	
			}

		}
		else if(Tag.equals("Del"))
		{
			// requested from ConstructionTips.jsp  page to delete the row 
			delId=req.getParameter("Id");
			if(delId!=null && delId.trim().length()>0)
				deleteTips(req, response);
			else
				mWriter.println("Error while deleting");
		}
		else if(Tag.equals("Edit"))
		{
			idEdit=req.getParameter("idEdit");
			if(validateField(req, response))
			{
				editTips(req, response);	
			}
		}

	}
	void deleteTips(HttpServletRequest req, HttpServletResponse response)
	{
		try{
			mWriter=response.getWriter();
			mDbUtils=new DBUtils();
			mCon=mDbUtils.generateConnection();		
			if(mCon!=null)
			{
				stmt = mCon.createStatement();
				try {
					String sql="Delete from master_cunstruction where Id = ?" ;
					PreparedStatement preparedStmt = mCon.prepareStatement(sql);
					preparedStmt.setString(1, delId);
					preparedStmt.executeUpdate();
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

	void AddNewTips(HttpServletRequest req, HttpServletResponse response)
	{
		try{
			mWriter=response.getWriter();
			mDbUtils=new DBUtils();
			mCon=mDbUtils.generateConnection();		
			if(mCon!=null)
			{
				stmt = mCon.createStatement();
				try {
					String query = "insert into master_cunstruction (Heading,Tips)"+ " values (?, ?)";
					PreparedStatement preparedStmt = mCon.prepareStatement(query);
					preparedStmt.setString (1, addTipsHeading);
					preparedStmt.setString (2, addTips);
					preparedStmt.execute();
					mWriter.println("Record Added Successfully");
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
	void editTips(HttpServletRequest req, HttpServletResponse response)
	{
		try{
			mWriter=response.getWriter();
			mDbUtils=new DBUtils();
			mCon=mDbUtils.generateConnection();		
			if(mCon!=null)
			{
				stmt = mCon.createStatement();
				try {
					String region="UPDATE  master_cunstruction SET Heading= ?,Tips = ? where Id=?" ;
					PreparedStatement preparedStmt = mCon.prepareStatement(region);
					preparedStmt.setString   (1,addTipsHeading );
					preparedStmt.setString   (2, addTips);
					preparedStmt.setString   (3, idEdit);
					preparedStmt.executeUpdate();
					mWriter.println("Updated succefully");	
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
	boolean validateField(HttpServletRequest req, HttpServletResponse response)
	{
		addTipsHeading=req.getParameter("heading");
		addTips=req.getParameter("tips");

		if(addTipsHeading!=null && addTipsHeading.trim().length()>0)
		{
			if(addTips!=null && addTips.trim().length()>0)
			{
				return true;
			}
			else{
				mWriter.println("Please enter the Heading for Tips");
				return false;	
			}
		}
		else{
			mWriter.println("Please enter the Tips text");
			return false;	
		}

	}
}
