package com.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
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

import org.json.JSONArray;
import org.json.JSONObject;

import com.constants.MessageConstants;
import com.db.DBUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.listener.DBConnectionProvider;
import com.model.MachinaryModel;
import com.model.MachineryModelList;
import com.model.SpecializationModelList;
import com.utils.GeneralRequestFormat;
import com.utils.Invoker;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class UserMachineriesSaveUpdate
 */
public class UserMachineriesSaveUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();

	public UserMachineriesSaveUpdate() {
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
					updateMachinary();
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

	void updateMachinary() 
	{
		String UserId=null;
		JSONArray UsersMachineriesDetails=null;
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		try {
			UserId=requestParam.getString("UserId");
			UsersMachineriesDetails=requestParam.getJSONArray("UsersMachineriesDetails");
			if(UsersMachineriesDetails!=null && UsersMachineriesDetails.length()>0)
			{
				Statement stmt = mCon.createStatement();
				String deletePrev="DELETE FROM users_machineries WHERE UserId='"+UserId+"'";
				stmt.executeUpdate(deletePrev);	
				try{
					for(int i=0;i<UsersMachineriesDetails.length();i++)
					{
						String query = "insert into users_machineries (UserId,ManchineriesId,NoOfMachineries,CreatedDatet)"+ " values (?, ?, ?, ?)";
						PreparedStatement preparedStmt = mCon.prepareStatement(query);
						preparedStmt.setString (1, UserId);
						preparedStmt.setString (2, UsersMachineriesDetails.getJSONObject(i).optString("MachineriesId"));
						preparedStmt.setString (3, UsersMachineriesDetails.getJSONObject(i).optString("NoOfMachineries"));
						preparedStmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
						preparedStmt.execute();	
					}
					MachineryModelList list=new MachineryModelList();
					list.setMessage("Data saved successfully");
					list.setStatus(MessageConstants.StatusSuccess);
					ResponseUtil.setResponse(mWriter, list);	
					
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				ResponseUtil.seterror(mWriter, "Nothing to updated", MessageConstants.StatusFail);	
			}

		} catch (Exception e) {
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}
	}

	
}
