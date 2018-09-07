package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.sql.Connection;
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
import com.model.UserProfileModel;
import com.responsemodel.CommonResponseModel;
import com.utils.GeneralRequestFormat;
import com.utils.PasswordEncryption;
import com.utils.ResponseUtil;
import com.webmodel.UsersProjectListItem;
import com.webmodel.UsersProjectModel;

/**
 * Servlet implementation class GetUserProfileImage
 */
public class GetUserProfileImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	private String UserId=null;
	public GetUserProfileImage() {
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
					UserId=requestParam.getString("UserId");
					mDbUtils=new DBUtils();
					getProfileImage();
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


	void getProfileImage() 
	{

		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		try{
			Statement stmt = mCon.createStatement();
			String sql="SELECT ProfileImage from users_details where user_Id='"+UserId+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				UserProfileModel model=new UserProfileModel();
				model.setImage(rs.getString("ProfileImage"));
				model.setMessage("Success");
				model.setStatus(MessageConstants.StatusSuccess);
				ResponseUtil.setResponse(mWriter, model);
			}
			else
			{
				CommonResponseModel model=new CommonResponseModel();
				model.setMessage("No Image Found");
				model.setStatus(MessageConstants.StatusFail);
				ResponseUtil.setResponse(mWriter, model);
			}

		}catch(Exception e)
		{
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}
	}
	@Override
	public void destroy() {
		super.destroy();

	}

}
