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

import org.json.JSONObject;

import com.constants.MessageConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.model.LoginModel;
import com.model.TollFreeModel;
import com.responsemodel.CommonResponseModel;
import com.utils.GeneralRequestFormat;
import com.utils.Invoker;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class AuthOTP
 */
public class AuthOTP extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();

	public AuthOTP() {
		super();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initializeInitialRequirements(request, response);

	}


	void initializeInitialRequirements(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
	{mWriter=response.getWriter();
	requestParam=GeneralRequestFormat.getParam(request, response,URL);
	if(requestParam!=null)
	{
		try {
			if(requestParam.getString("TokenNo").equals(MessageConstants.TokenValues))
			{

				mDbUtils=new DBUtils();
				authentication();
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

	void authentication() 
	{
		String UserId=null;
		String OTP=null;
		try {
			UserId=requestParam.getString("UserId");
			OTP=requestParam.getString("OTP");
		} catch (Exception e) {
		}
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		try {
			Statement stmt = mCon.createStatement();
			String sql="SELECT OTP_Forgot from users_details  where user_Id='"+UserId+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				if(rs.getString("OTP_Forgot").equals(OTP))
				{
					LoginModel model=new LoginModel();
					model.setMessage("OTP verified Successfully");
					model.setStatus(MessageConstants.StatusSuccess);
					model.setUID(UserId);
					ResponseUtil.setResponse(mWriter, model);
				}
				else
				{
					ResponseUtil.seterror(mWriter, "Invalid OTP ", MessageConstants.StatusFail);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}
	}



}
