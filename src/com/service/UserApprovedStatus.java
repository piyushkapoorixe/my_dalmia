package com.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.constants.MessageConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.model.DistricModel;
import com.model.DistricModelList;
import com.model.UserStatusModel;
import com.utils.GeneralRequestFormat;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class UserApprovedStatus
 */
public class UserApprovedStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	
	
    public UserApprovedStatus() {
        super();
        // TODO Auto-generated constructor stub
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
					getStatus();
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
	
	void getStatus()
	{
		String User_id=null;
		try {
			User_id=requestParam.getString("user_Id");
		} catch (Exception e) {

		}
		if(User_id!=null)
		{
			mCon=mDbUtils.generateConnection();	
			if(mCon==null)
			{
				ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
				return;
			}
			try {
				Statement stmt = mCon.createStatement();
				String sql="SELECT 	IsActive , user_Id from users_details where user_Id='"+User_id+"'" ;
				ResultSet rs = stmt.executeQuery(sql);
				UserStatusModel model=new UserStatusModel();
				if (rs.next()) {
					/*item.setId(rs.getString("id"));
					item.setName(rs.getString("name"));
					list.add(item);*/
					
					model.setMessage("Success");
					model.setStatus(MessageConstants.StatusSuccess);
					model.setActive_status(rs.getString("IsActive"));
					model.setUser_id(rs.getString("user_Id"));
					rs.close();
					ResponseUtil.setResponse(mWriter, model);
					
				}
				else
				{
					model.setMessage("No data found");
					model.setStatus(MessageConstants.StatusFail);
					rs.close();
					ResponseUtil.setResponse(mWriter, model);
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
		else
		{
			ResponseUtil.seterror(mWriter, "No state id", MessageConstants.StatusFail);
		}
	}
	
}
