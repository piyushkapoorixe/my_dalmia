package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
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
import com.utils.GeneralRequestFormat;
import com.utils.HitUrlUtils;
import com.utils.Invoker;
import com.utils.PasswordEncryption;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class UsersRegistration
 */
public class UsersRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	private String MobileNo=null;
	private String PinCode=null;
	private String Distric=null;
	private String DOB=null;
	private String RegionId=null;
	private String Password=null;
	private String EmailID=null;
	private String UserName=null;
	private String ProfessionType=null;
	private String SocialToken=null;
	private String StateId=null;
	private String CityId=null;
	private String Marrige_Anniversary=null;
	private String Websites=null;
	private String OTPIS;
	public UsersRegistration() {
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
					saveUser();
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

	void saveUser() 
	{
		MobileNo=null;
		Password=null;
		EmailID=null;
		UserName=null;
		ProfessionType=null;
		SocialToken=null;
		RegionId=null;
		DOB=null;
		Distric=null;
		PinCode=null;
		Marrige_Anniversary=null;
		Websites=null;
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		try {
			PinCode=requestParam.getString("PinCode");
			Distric=requestParam.getString("Distric");
			MobileNo=requestParam.getString("MobileNo").trim();
			Password=requestParam.getString("Password");
			EmailID=requestParam.getString("EmailID").trim();
			UserName=requestParam.getString("UserName");
			ProfessionType=requestParam.getString("ProfessionType");
			StateId=requestParam.getString("StateId");
			CityId=requestParam.getString("CityId");
			RegionId=requestParam.getString("RegionId");
			DOB=requestParam.getString("DOB");
			Marrige_Anniversary=requestParam.getString("Marrige_Anniversary");
			Websites=requestParam.getString("Websites");
			
			String emailCheck="Select email from users_details WHERE email='"+EmailID+"'" ;
			String mobileCheck="Select mob_no from users_details WHERE mob_no='"+MobileNo+"'" ;
			Statement stmt = mCon.createStatement();
			ResultSet emailSet = stmt.executeQuery(emailCheck);
			if(emailSet.next())
			{
				ResponseUtil.seterror(mWriter, EmailID+" Already Registered with us", MessageConstants.StatusFail);
				return;
			}
			ResultSet mobileSet = stmt.executeQuery(mobileCheck);
			if(mobileSet.next())
			{
				ResponseUtil.seterror(mWriter, MobileNo+" Already Registered with us", MessageConstants.StatusFail);
				return;
			}
			try {
				SocialToken=requestParam.getString("SocialToken");
			} catch (Exception e) {
			}
			generateOtpString();
			String query =null;
			String pass=PasswordEncryption.encryptPasword(Password);
			PreparedStatement preparedStmt=null;
			if(SocialToken!=null)
			{
				if(SocialToken.contains("Fb"))
				{
					query = "insert into users_details (email,user_Name,profession_Id,mob_no,password,FB_Token,OTP_Signup,StateId,CityId,RegionId,DOB,PinCodeId,DistricId,Marrige_Anniversary,Websites)"+ " values (?, ?, ?, ?, ?, ?,?, ?,?,?,?,?,?,?,?)";	
					preparedStmt = mCon.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
					preparedStmt.setString (1, EmailID);
					preparedStmt.setString (2, UserName);
					preparedStmt.setString (3, ProfessionType);
					preparedStmt.setString (4, MobileNo);
					preparedStmt.setString(5, pass);
					preparedStmt.setString (6, SocialToken);
					preparedStmt.setString (7, OTPIS);
					preparedStmt.setString (8, StateId);
					preparedStmt.setString (9, CityId);
					preparedStmt.setString (10, RegionId);
					preparedStmt.setString (11, DOB);
					preparedStmt.setString (12, PinCode);
					preparedStmt.setString (13, Distric);
					preparedStmt.setString (14, Marrige_Anniversary);
					preparedStmt.setString (15, Websites);
				}
				else if(SocialToken.contains("Gmail"))
				{
					query = "insert into users_details (email,user_Name,profession_Id,mob_no,password,Gmail_Token,OTP_Signup,StateId,CityId,RegionId,DOB,PinCodeId,DistricId,Marrige_Anniversary,Websites)"+ " values (?, ?, ?, ?, ?,?,?, ?,?,?,?,?,?,?,?)";
					preparedStmt = mCon.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
					preparedStmt.setString (1, EmailID);
					preparedStmt.setString (2, UserName);
					preparedStmt.setString (3, ProfessionType);
					preparedStmt.setString (4, MobileNo);
					preparedStmt.setString(5, pass);
					preparedStmt.setString (6, SocialToken);
					preparedStmt.setString (7, OTPIS);
					preparedStmt.setString (8, StateId);
					preparedStmt.setString (9, CityId);
					preparedStmt.setString (10, RegionId);
					preparedStmt.setString (11, DOB);
					preparedStmt.setString (12, PinCode);
					preparedStmt.setString (13, Distric);
					preparedStmt.setString (14, Marrige_Anniversary);
					preparedStmt.setString (15, Websites);
				}
			}
			else
			{
				query = "insert into users_details (email,user_Name,profession_Id,mob_no,password,OTP_Signup,StateId,CityId,RegionId,DOB,PinCodeId,DistricId,Marrige_Anniversary,Websites)"+ " values (?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?)";	
				preparedStmt = mCon.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
				preparedStmt.setString (1, EmailID);
				preparedStmt.setString (2, UserName);
				preparedStmt.setString (3, ProfessionType);
				preparedStmt.setString (4, MobileNo);
				preparedStmt.setString(5, pass);
				preparedStmt.setString (6, OTPIS);
				preparedStmt.setString (7, StateId);
				preparedStmt.setString (8, CityId);
				preparedStmt.setString (9, RegionId);
				preparedStmt.setString (10, DOB);
				preparedStmt.setString (11, PinCode);
				preparedStmt.setString (12, Distric);
				preparedStmt.setString (13, Marrige_Anniversary);
				preparedStmt.setString (14, Websites);
			}
			preparedStmt.execute();
			LoginModel model=new LoginModel();
			model.setMessage("We have send Otp Successfully.");
			model.setStatus(MessageConstants.StatusSuccess);
			ResultSet generatedKeys = preparedStmt.getGeneratedKeys();
			if(generatedKeys.next())
			{
				model.setUID(""+generatedKeys.getInt(1));
			}
			model.setOTP(OTPIS);
			emailSet.close();
			mobileSet.close();
			ResponseUtil.setResponse(mWriter, model);
			System.out.println("otp ::::::::::::: "+OTPIS);
			//sendOtp();
				
		} catch (Exception e) {
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}
	}
	void generateOtpString()
	{
		java.util.Random generator = new java.util.Random();
		generator.setSeed(System.currentTimeMillis());
		int i = generator.nextInt(10000) % 10000;
		java.text.DecimalFormat f = new java.text.DecimalFormat("0000");
		OTPIS=""+f.format(i);
	}

	void sendOtp()
	{
		String msg="Kindly use One Time Password -OTP "+OTPIS+" for Dalmia HomeExpert";
		String address = MessageConstants.Smsurl+"?pid="+MessageConstants.Smsfeedid+"&uname="+MessageConstants.Smsuser+"&pwd="+MessageConstants.Smspass+"&mobileno="+MobileNo+"&smsmsg="+msg;
		HitUrlUtils.callURL(address);
	}
	
}
