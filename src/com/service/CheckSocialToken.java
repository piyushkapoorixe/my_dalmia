package com.service;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.utils.GeneralRequestFormat;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class CheckSocialToken
 */
public class CheckSocialToken extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	
	public CheckSocialToken() {
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
					checkCredential();
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
	private void  checkCredential() {
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		try{
			String token=requestParam.optString("SocialToken");
			if(token!=null)
			{
				Statement stmt = mCon.createStatement();
				String sql="SELECT users_details.* , master_profession.Profession FROM users_details INNER JOIN master_profession ON users_details.profession_Id=master_profession.profession_Id where Gmail_Token='"+token+"' OR FB_Token='"+token+"'";
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next())
				{
					LoginModel model=new LoginModel();
					model.setMessage("Success");
					model.setStatus(MessageConstants.StatusSuccess);
					model.setUID(rs.getString("user_Id"));
					model.setEmail(rs.getString("email"));
					model.setLastLoginTime(rs.getString("Last_LoginTime"));
					model.setMobileNo(rs.getString("mob_no"));
					model.setName(rs.getString("user_Name"));
					model.setProfessionId(rs.getString("profession_Id"));
					model.setProfession(rs.getString("Profession"));	
					rs.close();
					ResponseUtil.setResponse(mWriter, model);
				}
				else
				{
					ResponseUtil.seterror(mWriter, "Invalid Credential", MessageConstants.StatusFail);
				}	
			}
			else
			{
				ResponseUtil.seterror(mWriter, "Token not found", MessageConstants.StatusFail);
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
}
