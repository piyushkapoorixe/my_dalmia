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
import com.utils.GeneralRequestFormat;
import com.utils.HitUrlUtils;
import com.utils.Invoker;
import com.utils.PasswordEncryption;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	private String OTPIS;
	public Login() {
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


	void checkCredential() 
	{
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		try{
			String pass=PasswordEncryption.encryptPasword(requestParam.optString("Password"));
			if(pass!=null)
			{
				String mobile=requestParam.optString("MobileNo");
				Statement stmt = mCon.createStatement();
				String sql="SELECT users_details.* , master_profession.Profession FROM users_details INNER JOIN master_profession ON users_details.profession_Id=master_profession.profession_Id where password='"+pass+"' AND mob_no='"+mobile+"'" ;
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next())
				{
					if(rs.getString("OTP_Signup_Auth").equals("1")){
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
						model.setImageUrl(rs.getString("ProfileImage"));	

						rs.close();
						ResponseUtil.setResponse(mWriter, model);
					}
					else
					{
						String mob=rs.getString("mob_no");
						generateOtpString();
						String query="update users_details SET OTP_Signup='"+OTPIS+"'"+"WHERE mob_no='"+mob+"'";
						Statement stmt2 = mCon.createStatement();
						stmt2.executeUpdate(query);
						LoginModel model=new LoginModel();
						model.setMessage("We have send Otp Successfully.");
						model.setStatus(MessageConstants.StatusAuthError);
						model.setUID(rs.getString("user_Id"));
						model.setOTP(OTPIS);
						rs.close();
						sendOtp(mob);
						ResponseUtil.setResponse(mWriter, model);
					}
				}
				else
				{
					ResponseUtil.seterror(mWriter, "Invalid Credential", MessageConstants.StatusFail);
				}	
			}
			else
			{
				ResponseUtil.seterror(mWriter, "Invalid Password Format", MessageConstants.StatusFail);
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
	void generateOtpString()
	{
		java.util.Random generator = new java.util.Random();
		generator.setSeed(System.currentTimeMillis());
		int i = generator.nextInt(10000) % 10000;
		java.text.DecimalFormat f = new java.text.DecimalFormat("0000");
		OTPIS=""+f.format(i);
	}
	
	void sendOtp(String mob)
	{
		String msg="Kindly use One Time Password -OTP "+OTPIS+" for Dalmia HomeExpert";
		String address = MessageConstants.Smsurl+"?pid="+MessageConstants.Smsfeedid+"&uname="+MessageConstants.Smsuser+"&pwd="+MessageConstants.Smspass+"&mobileno="+mob+"&smsmsg="+msg;
		HitUrlUtils.callURL(address);
	}

	
}
