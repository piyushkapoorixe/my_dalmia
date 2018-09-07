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
import com.model.UserDetailModel;
import com.utils.GeneralRequestFormat;
import com.utils.HitUrlUtils;
import com.utils.ResponseUtil;
import com.utils.WebProjectLIstUtils;

/**
 * Servlet implementation class AuthSignupOTP
 */
public class AuthSignupOTP extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	public AuthSignupOTP() {
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
			Statement stmt2 = mCon.createStatement();
			String sql2="SELECT OTP_Signup from users_details  where user_Id='"+UserId+"'";
			ResultSet rs2 = stmt2.executeQuery(sql2);
			if(rs2.next())
			{
				if(rs2.getString("OTP_Signup").equals(OTP))
				{
					String query="update users_details SET OTP_Signup_Auth='1'"+"WHERE user_Id='"+UserId+"'";
					Statement stm = mCon.createStatement();
					stm.executeUpdate(query);
					String sql="SELECT users_details.* , master_profession.Profession FROM users_details INNER JOIN master_profession ON users_details.profession_Id=master_profession.profession_Id where user_Id="+UserId+"";
					Statement stmt = mCon.createStatement();
					ResultSet rs = stmt.executeQuery(sql);
					if(rs.next())
					{
						LoginModel model=new LoginModel();
						model.setMessage("You have registered successfully");
						model.setStatus(MessageConstants.StatusSuccess);
						model.setUID(rs.getString("user_Id"));
						model.setEmail(rs.getString("email"));
						model.setLastLoginTime(rs.getString("Last_LoginTime"));
						model.setMobileNo(rs.getString("mob_no"));
						model.setName(rs.getString("user_Name"));
						model.setProfession(rs.getString("Profession"));
						model.setProfessionId(rs.getString("profession_Id"));
						model.setOTP(rs.getString("OTP_Signup"));
						model.setDistricId(rs.getString("DistricId"));
						rs.close();
						ResponseUtil.setResponse(mWriter, model);
						UserDetailModel data=WebProjectLIstUtils.getUserdetailBasedonId(UserId);
						String sms=null;
						sms="A New "+data.getProfession()+", "+data.getUserName()+" From "+data.getDistric()+" District, has applied for a new Registration, and mobile no is "+data.getMobileNo();
						HitUrlUtils.sendInqtoRMAndTso(model.getUID(),sms);
					}
					else
						ResponseUtil.seterror(mWriter, "Invalid Credential", MessageConstants.StatusFail);
				}
				else
				{
					ResponseUtil.seterror(mWriter, "Invalid OTP ", MessageConstants.StatusFail);
				}
			}
		} catch (Exception e) {
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

}
