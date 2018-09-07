package com.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
import com.utils.Invoker;
import com.utils.PasswordEncryption;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class ChangePassword
 */
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String passNew=null;
	private String userId=null;
	private String URL=this.getClass().getSimpleName();
	public ChangePassword() {
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
					changePasword();
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

	void changePasword() 
	{
		ResultSet rs=null;
		String passOld=PasswordEncryption.encryptPasword(requestParam.optString("CurrentPassword"));
		passNew=PasswordEncryption.encryptPasword(requestParam.optString("NewPassword"));
		userId=requestParam.optString("UserId");
		if(passOld!=null && passNew!=null)
		{
			mCon=mDbUtils.generateConnection();	
			if(mCon==null)
			{
				ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
				return;
			}
			try {
				Statement stmt = mCon.createStatement();
				String sql="SELECT password from users_details where user_Id='"+userId+"'" ;
				rs = stmt.executeQuery(sql);
				if(rs.next())
				{
					String dbPassword=rs.getString("password");
					if(dbPassword.equals(passOld))
					{
						enterNewPasswordNow();
					}
					else
					{
						ResponseUtil.seterror(mWriter, "You entered invalid password", MessageConstants.StatusFail);
					}
				}
				rs.close();	
			} catch (Exception e) {
			}
			finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}
		}
		else
		{
			ResponseUtil.seterror(mWriter, "Invalid Password Format", MessageConstants.StatusFail);
		}

	}

	void enterNewPasswordNow()
	{
		try{
			Statement stmt = mCon.createStatement();
			String sql="UPDATE users_details SET password='"+passNew+"' where user_Id='"+userId+"'" ;
			int i=stmt.executeUpdate(sql);
			if(i>0)
			{
				LoginModel model=new LoginModel();
				model.setMessage("Password changed successfully");
				model.setStatus(MessageConstants.StatusSuccess);
				ResponseUtil.setResponse(mWriter, model);
			}
			else
			{
				ResponseUtil.seterror(mWriter, "Some problem occur please try again", MessageConstants.StatusFail);
			}

		}catch(Exception e)
		{
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
		}

	}
	
}
