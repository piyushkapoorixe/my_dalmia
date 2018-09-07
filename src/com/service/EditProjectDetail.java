package com.service;

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

import org.json.JSONArray;
import org.json.JSONObject;

import com.constants.MessageConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.model.UserDetailModel;
import com.responsemodel.CommonResponseModel;
import com.utils.GeneralRequestFormat;
import com.utils.HitUrlUtils;
import com.utils.Mailer;
import com.utils.ResponseUtil;
import com.utils.WebProjectLIstUtils;

/**
 * Servlet implementation class EditProjectDetail
 */
public class EditProjectDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	private String ProjectId;
	private String ProjectName;
	private String Location;
	private String Area;
	private String Value;
	private String CityId=null;
	private String UserId=null;
	public EditProjectDetail() {
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
					editProject();
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
	void editProject() 
	{
		ProjectId=requestParam.optString("ProjectId");
		ProjectName=requestParam.optString("ProjectName");
		Location=requestParam.optString("Location");
		Value=requestParam.optString("Value");
		Area=requestParam.optString("Area");
		CityId=requestParam.optString("CityId");
		UserId=requestParam.optString("UserId");
		saveDetailInDatabase();
	}
	void saveDetailInDatabase()
	{
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		try {
			String query="UPDATE  users_project SET ProjectName= ?,Price = ?,Location = ?,City_Id = ?,Area=? ,Is_Active=? where Id=?" ;
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			preparedStmt.setString (1, ProjectName);
			preparedStmt.setString (2, Value);
			preparedStmt.setString (3, Location);
			preparedStmt.setString (4, CityId);
			preparedStmt.setString(5, Area);
			preparedStmt.setString(6, "0");//default inactive, in case of any updation 
			preparedStmt.setString(7, ProjectId);
			preparedStmt.executeUpdate();
			deleteAlreadyManpower();
		} catch (SQLException e) {
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}
		}
	}
	void updateManpower() 
	{
		JSONArray UsersManpowerDetails=null;
		try{
			UsersManpowerDetails=requestParam.getJSONArray("UsersManpowerDetails");
			for(int i=0;i<UsersManpowerDetails.length();i++)
			{
				String query = "insert into project_manpower (ProjectId,ManpowerId,NoOfManpower,CreatedDate)"+ " values (?, ?, ?, ?)";
				PreparedStatement preparedStmt = mCon.prepareStatement(query);
				preparedStmt.setString (1, ProjectId);
				preparedStmt.setString (2, UsersManpowerDetails.getJSONObject(i).optString("ManpowerId"));
				preparedStmt.setString (3, UsersManpowerDetails.getJSONObject(i).optString("NoOfManpower"));
				preparedStmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
				preparedStmt.execute();	
				
			}
			deleteAlreadyMachinaty();
			
		}catch(Exception e)
		{
			e.printStackTrace();
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
		}
	}
	void updateMachinary() 
	{
		JSONArray UsersMachineriesDetails=null;
		try{
			UsersMachineriesDetails=requestParam.getJSONArray("UsersMachineriesDetails");
			for(int i=0;i<UsersMachineriesDetails.length();i++)
			{
				String query = "insert into project_machineries (ProjectId,ManchineriesId,NoOfMachineries,CreatedDatet)"+ " values (?, ?, ?, ?)";
				PreparedStatement preparedStmt = mCon.prepareStatement(query);
				preparedStmt.setString (1, ProjectId);
				preparedStmt.setString (2, UsersMachineriesDetails.getJSONObject(i).optString("MachineriesId"));
				preparedStmt.setString (3, UsersMachineriesDetails.getJSONObject(i).optString("NoOfMachineries"));
				preparedStmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
				preparedStmt.execute();	
			}
			CommonResponseModel model=new CommonResponseModel();
			model.setMessage("Project Update Successfully");
			model.setStatus(MessageConstants.StatusSuccess);
			ResponseUtil.setResponse(mWriter, model);
			UserDetailModel data=WebProjectLIstUtils.getUserdetailBasedonId(UserId);
			String sms=null;
			sms=data.getUserName()+" From "+data.getDistric()+" District, has been updated their project. The  project name is "+ProjectName;
			HitUrlUtils.sendInqtoRMAndTso(UserId,sms);
		}catch(Exception e)
		{
			e.printStackTrace();
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);	
		}
}
	
	void deleteAlreadyMachinaty()
	{
		try {
			String query="delete from project_machineries where ProjectId='"+ProjectId+"'" ;
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			preparedStmt.executeUpdate();
			updateMachinary();
		} catch (SQLException e) {
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
		}	
	}
	void deleteAlreadyManpower()
	{
		try {
			String query="delete from project_manpower where ProjectId='"+ProjectId+"'" ;
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			preparedStmt.executeUpdate();
			updateManpower();
		} catch (SQLException e) {
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
		}		
	}
	
	
}