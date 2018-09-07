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
 * Servlet implementation class AjaxDeleteFaqs
 */
public class AjaxFaqsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private Statement stmt;
	private String delId=null;
	private String Tag=null;
	private String addQuestion=null;
	private String addAnswer=null;
	private String idEdit=null;
	public AjaxFaqsController() {
		super();
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		mWriter=response.getWriter();
		Tag=req.getParameter("Tag");
		if(Tag.equals("AddNew"))
		{
			// requested from AddNewFaqs.jsp  page to Add  a  new row 
			if(validateField(req, response))
			{
				AddNewFaqs(req, response);	
			}
			
		}
		else if(Tag.equals("Del"))
		{
			// requested from faqs.jsp  page to delete the row 
			delId=req.getParameter("Id");
			if(delId!=null && delId.trim().length()>0)
				deleteFaqs(req, response);
			else
				mWriter.println("Error while deleting");
		}
		else if(Tag.equals("Edit"))
		{
			idEdit=req.getParameter("idEdit");
			if(validateField(req, response))
			{
				editFaqs(req, response);	
			}
		}
		
	}
	void deleteFaqs(HttpServletRequest req, HttpServletResponse response)
	{
		try{
			mWriter=response.getWriter();
			mDbUtils=new DBUtils();
			mCon=mDbUtils.generateConnection();		
			if(mCon!=null)
			{
				stmt = mCon.createStatement();
				try {
					String sql="Delete from master_faq where Id = ?" ;
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

	void AddNewFaqs(HttpServletRequest req, HttpServletResponse response)
	{
		try{
			mWriter=response.getWriter();
			mDbUtils=new DBUtils();
			mCon=mDbUtils.generateConnection();		
			if(mCon!=null)
			{
				stmt = mCon.createStatement();
				try {
					String query = "insert into master_faq (Question,Answer)"+ " values (?, ?)";
					PreparedStatement preparedStmt = mCon.prepareStatement(query);
					preparedStmt.setString (1, addQuestion);
					preparedStmt.setString (2, addAnswer);
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
	void editFaqs(HttpServletRequest req, HttpServletResponse response)
	{
		try{
			mWriter=response.getWriter();
			mDbUtils=new DBUtils();
			mCon=mDbUtils.generateConnection();		
			if(mCon!=null)
			{
				stmt = mCon.createStatement();
				try {
					String region="UPDATE  master_faq SET Question= ?,Answer = ? where Id=?" ;
					PreparedStatement preparedStmt = mCon.prepareStatement(region);
					preparedStmt.setString   (1,addQuestion );
					preparedStmt.setString   (2, addAnswer);
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
		addQuestion=req.getParameter("Que");
		addAnswer=req.getParameter("ans");

		if(addQuestion!=null && addQuestion.trim().length()>0)
		{
			if(addAnswer!=null && addAnswer.trim().length()>0)
			{
				return true;
			}
			else{
				mWriter.println("Please enter the answer text");
				return false;	
			}
		}
		else{
			mWriter.println("Please enter the question text");
			return false;	
		}
		
	}

}
