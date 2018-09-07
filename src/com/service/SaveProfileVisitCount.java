package com.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.constants.MessageConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.responsemodel.CommonResponseModel;
import com.utils.GeneralRequestFormat;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class SaveProfileVisitCount
 */
public class SaveProfileVisitCount extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	
	private String user_id;
	private String profile_id;
	private String date;

	public SaveProfileVisitCount() {
		super();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initializeInitialRequirements(request, response);

	}
	void initializeInitialRequirements(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
	{
		mWriter=response.getWriter();
		requestParam=GeneralRequestFormat.getParam(request, response,URL);
		if(requestParam!=null)
		{
			try {
				if(requestParam.getString("TokenNo").equals(MessageConstants.TokenValues))
				{
					mDbUtils=new DBUtils();
					saveCount();
				}
				else
				{
					ResponseUtil.seterror(mWriter, "Invalid Authentication", MessageConstants.StatusFail);
				}

			} catch (Exception e) {
				ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
			}
		}
		else
		{
			ResponseUtil.seterror(mWriter, "Invalid Parameter", MessageConstants.StatusFail);
		}

	}
	
	
	
	
	void saveCount()
	{
		mCon=mDbUtils.generateConnection();	

		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		try {
			user_id=requestParam.getString("user_id");
			profile_id=requestParam.getString("profile_id");
	        date=getDate();	

			
			String checkCountOnDate="Select * from  profile_visit_count WHERE User_id='"+user_id+"' AND Profile_id='"+profile_id+"' AND	Date='"+date+"'" ;
			Statement stmt = mCon.createStatement();
			ResultSet checkCount = stmt.executeQuery(checkCountOnDate);
			if(checkCount.next())
			{
				ResponseUtil.seterror(mWriter, " Already Visited on this date", MessageConstants.StatusFail);
				return;
			}
			PreparedStatement preparedStmt=null;
			String query =null;

			query = "insert into profile_visit_count (User_id,Profile_id,Date)"+ " values (?, ?, ?)";	
			preparedStmt = mCon.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setString (1, user_id);
			preparedStmt.setString (2, profile_id);
			preparedStmt.setString (3, date);
			preparedStmt.execute();		
			
			String updateVisitCount = "UPDATE users_details SET profile_visit_count = profile_visit_count + 1 WHERE user_Id=?";
			PreparedStatement updateVisitCountpreparedStmt = mCon.prepareStatement(updateVisitCount);
			updateVisitCountpreparedStmt.setString (1, profile_id);
			updateVisitCountpreparedStmt.execute();
			
			CommonResponseModel model=new CommonResponseModel();
			model.setMessage("Success");
			model.setStatus(MessageConstants.StatusSuccess);
			ResponseUtil.setResponse(mWriter, model);
		}catch (Exception e) {
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {	}
		}
	}
	private String getDate() {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		
		return dateFormat.format(date);
	}
	
	
/*
	void saveCount() 
	{
		String UserId=null;
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		try {
			UserId=requestParam.getString("UserId");
			String query = "UPDATE users_details SET profile_visit_count = profile_visit_count + 1 WHERE user_Id=?";
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			preparedStmt.setString (1, UserId);
			preparedStmt.execute();
			CommonResponseModel model=new CommonResponseModel();
			model.setMessage("Success");
			model.setStatus(MessageConstants.StatusSuccess);
			ResponseUtil.setResponse(mWriter, model);
		} catch (Exception e) {
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}
	}*/
}
