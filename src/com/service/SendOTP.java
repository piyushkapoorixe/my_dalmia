package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.URL;
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
import com.utils.Mailer;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class SendOTP
 */
public class SendOTP extends HttpServlet {
	
	
	
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	private String OTPIS;
	private String mobileNo=null;
	private String emailId=null;
	public SendOTP() {
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
					generateOTP();
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
	void generateOTP() 
	{
		generateOtpString();
		if(OTPIS!=null)
		{
			mCon=mDbUtils.generateConnection();	
			if(mCon==null)
			{
				ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
				return;
			}
			try{
				mobileNo=requestParam.optString("mobileNo");
				emailId=requestParam.optString("emailId");
				if(mobileNo!=null && mobileNo.trim().length()>0)
				{
					Statement stmt = mCon.createStatement();
					String sql="SELECT user_Id from users_details where mob_no='"+mobileNo+"'" ;
					ResultSet rs = stmt.executeQuery(sql);
					LoginModel model=new LoginModel();
					if (rs.next()) {
						String query="update users_details SET OTP_Forgot='"+OTPIS+"'"+"WHERE mob_no='"+mobileNo+"'";
						Statement stmt2 = mCon.createStatement();
						stmt2.executeUpdate(query);
						model.setMessage("Success");
						model.setStatus(MessageConstants.StatusSuccess);
						model.setEmail(emailId);
						model.setUID(rs.getString("user_Id"));
						model.setOTP(OTPIS);
						model.setMobileNo(mobileNo);
						ResponseUtil.setResponse(mWriter, model);
						sendOtp();		
					}
					else
					{
						ResponseUtil.seterror(mWriter, "Mobile number  not valid", MessageConstants.StatusFail);	
					}
				} 
				else if(emailId!=null && emailId.trim().length()>0)
				{
					Statement stmt = mCon.createStatement();
					String sql2="SELECT user_Id from users_details where email='"+emailId+"'" ;
					ResultSet rs2 = stmt.executeQuery(sql2);
					LoginModel model=new LoginModel();
					if (rs2.next()) {
						String query="update users_details SET OTP_Forgot='"+OTPIS+"'"+"WHERE email='"+emailId+"'";
						Statement stmt2 = mCon.createStatement();
						stmt2.executeUpdate(query);
						try {
							Mailer.send(emailId, "Otp", OTPIS);
							model.setMessage("We have sent the otp on "+emailId);
							model.setStatus(MessageConstants.StatusSuccess);
							model.setEmail(emailId);
							model.setUID(rs2.getString("user_Id"));
							model.setOTP(OTPIS);
							model.setMobileNo(mobileNo);
							ResponseUtil.setResponse(mWriter, model);
						} catch (Exception ex) {
							ex.printStackTrace();
							model.setMessage(ex.getLocalizedMessage());
							model.setStatus(MessageConstants.StatusFail);
							ResponseUtil.setResponse(mWriter, model);
						}
					}
					else
					{
						ResponseUtil.seterror(mWriter, "Email id  not valid", MessageConstants.StatusFail);	
					}	
				}
				else
				{
					ResponseUtil.seterror(mWriter, "Invalid parameter", MessageConstants.StatusFail);
				}
			}catch(Exception e)
			{
				e.printStackTrace();
				ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);	
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
			ResponseUtil.seterror(mWriter, "We are busy please try again after some time", MessageConstants.StatusFail);
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
		String address = MessageConstants.Smsurl+"?pid="+MessageConstants.Smsfeedid+"&uname="+MessageConstants.Smsuser+"&pwd="+MessageConstants.Smspass+"&mobileno="+mobileNo+"&smsmsg="+OTPIS;
		HitUrlUtils.callURL(address);
	}

}
