package com.service;

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

import org.json.JSONArray;
import org.json.JSONObject;

import com.constants.MessageConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.model.MachineryModelList;
import com.model.ManPowerModelList;
import com.utils.GeneralRequestFormat;
import com.utils.Invoker;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class UserManpowerSaveUpdate
 */
public class UserManpowerSaveUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();

	public UserManpowerSaveUpdate() {
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
					updateManpower();
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

	void updateManpower() 
	{
		String UserId=null;
		JSONArray UsersManpowerDetails=null;
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		try {
			UserId=requestParam.getString("UserId");
			UsersManpowerDetails=requestParam.getJSONArray("UsersManpowerDetails");
			if(UsersManpowerDetails!=null && UsersManpowerDetails.length()>0)
			{
				Statement stmt = mCon.createStatement();
				String deletePrev="DELETE FROM users_manpower WHERE UserId='"+UserId+"'";
				stmt.executeUpdate(deletePrev);	
				try{
					for(int i=0;i<UsersManpowerDetails.length();i++)
					{
						String query = "insert into users_manpower (UserId,ManpowerId,NoOfManpower,CreatedDate)"+ " values (?, ?, ?, ?)";
						PreparedStatement preparedStmt = mCon.prepareStatement(query);
						preparedStmt.setString (1, UserId);
						preparedStmt.setString (2, UsersManpowerDetails.getJSONObject(i).optString("ManpowerId"));
						preparedStmt.setString (3, UsersManpowerDetails.getJSONObject(i).optString("NoOfManpower"));
						preparedStmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
						preparedStmt.execute();	
					}
					ManPowerModelList list=new ManPowerModelList();
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
