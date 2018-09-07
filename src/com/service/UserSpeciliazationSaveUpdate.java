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
import com.model.SpecializationModelList;
import com.utils.GeneralRequestFormat;
import com.utils.Invoker;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class UserSpeciliazationSaveUpdate
 */
public class UserSpeciliazationSaveUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();

	public UserSpeciliazationSaveUpdate() {
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
					updateSpecialization();
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

	void updateSpecialization() 
	{
		String UserId=null;
		JSONArray UsersSpecializationDetails=null;
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		try {
			UserId=requestParam.getString("UserId");
			UsersSpecializationDetails=requestParam.getJSONArray("UsersSpecializationDetails");
			if(UsersSpecializationDetails!=null && UsersSpecializationDetails.length()>0)
			{
				Statement stmt = mCon.createStatement();
				String deletePrev="DELETE FROM user_speciliazation WHERE UserId='"+UserId+"'";
				stmt.executeUpdate(deletePrev);	
				try{
					for(int i=0;i<UsersSpecializationDetails.length();i++)
					{
						String query = "insert into user_speciliazation (UserId,SpeciliazationId,CreateDate)"+ " values (?, ?, ?)";
						PreparedStatement preparedStmt = mCon.prepareStatement(query);
						preparedStmt.setString (1, UserId);
						preparedStmt.setString (2, UsersSpecializationDetails.getJSONObject(i).optString("SpecializationId"));
						preparedStmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
						preparedStmt.execute();	
					}
					SpecializationModelList list=new SpecializationModelList();
					list.setMessage("Specialization has been updated");
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
