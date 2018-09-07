package com.AdminServiceAjax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.constants.MessageConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.model.StateModel;
import com.model.StateModelList;
import com.utils.HitUrlUtils;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class ApproveDisapprove
 */
public class ApproveDisapprove extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	private Statement stmt;
	private HttpServletRequest request;
	private int UserId=0;
	private int ProjectId=0;
	private int Status=0;
	private String StatusString=null;
	public ApproveDisapprove() {
		super();
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

		request=req;
		mWriter=response.getWriter();
		Status=Integer.parseInt(req.getParameter("status"));
		
		if(Status==0)
		{
			StatusString="Disapproved";	
		}else
		{
			StatusString="Approved";		
		}
		if(req.getParameter("PID")!=null)
		{
			ProjectId=Integer.parseInt(req.getParameter("PID"));	
			changeProjectStatus(req, response);
			
		}
		else
		{
			UserId=Integer.parseInt(req.getParameter("Uid"));	
			changeUserStatus(req, response);
		}
		
		
		
		
	}
	void changeUserStatus(HttpServletRequest req, HttpServletResponse response)
	{
		try{
			mWriter=response.getWriter();
			mDbUtils=new DBUtils();
			mCon=mDbUtils.generateConnection();		
			if(mCon!=null)
			{
				stmt = mCon.createStatement();
						try {
							String region="UPDATE  users_details SET IsActive= ? where user_Id=?" ;
							PreparedStatement preparedStmt = mCon.prepareStatement(region);
						      preparedStmt.setInt(1, Status);
						      preparedStmt.setInt (2, UserId);
						      preparedStmt.executeUpdate();
						      SenConfirmationToMobileNo(""+UserId, "Your Registration  has  "+StatusString);
							  mWriter.println(" Updated succefully");	
							
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
	void changeProjectStatus(HttpServletRequest req, HttpServletResponse response)
	{
		try{
			mWriter=response.getWriter();
			mDbUtils=new DBUtils();
			mCon=mDbUtils.generateConnection();		
			if(mCon!=null)
			{
				stmt = mCon.createStatement();
						try {
							String region="UPDATE  users_project SET Is_Active= ? where Id=?" ;
							PreparedStatement preparedStmt = mCon.prepareStatement(region);
						      preparedStmt.setInt(1, Status);
						      preparedStmt.setInt (2, ProjectId);
						      preparedStmt.executeUpdate();
							  mWriter.println(" Project Updated succefully");
							  SenConfirmationToMobileNo(""+UserId, "Your Project  has  "+StatusString);
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
	
	void SenConfirmationToMobileNo(String Id,String msg) 
	{
		
		try {
			Statement stmt = mCon.createStatement();
			String sql="SELECT mob_no from users_details where user_Id='"+Id+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				String mob=rs.getString("mob_no");
				String address = MessageConstants.Smsurl+"?pid="+MessageConstants.Smsfeedid+"&uname="+MessageConstants.Smsuser+"&pwd="+MessageConstants.Smspass+"&mobileno="+mob+"&smsmsg="+msg;
				HitUrlUtils.callURL(address);
			}
		} catch (Exception e) {
		}
		
	}
}
