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
import com.model.UserDetailModel;
import com.responsemodel.CommonResponseModel;
import com.utils.GeneralRequestFormat;
import com.utils.HitUrlUtils;
import com.utils.Invoker;
import com.utils.Mailer;
import com.utils.ResponseUtil;
import com.utils.WebProjectLIstUtils;

/**
 * Servlet implementation class UpdateUserBasicDetails
 */
public class UpdateUserBasicDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	public UpdateUserBasicDetails() {
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
					updateUser();
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

	void updateUser() 
	{
		String UserId=null;
		String FirmName=null;
		String Address=null;
		String StateId=null;
		String CityId=null;
		String QualificationId=null;
		String ExperienceYears=null;
		String ProjectHandled=null;
		String RegionId=null;
		String PinCodeId=null;
		String DistricId=null;
		String Websites=null;
		String Marrige_Anniversary=null;
		String DOB=null;

		try {
			UserId=requestParam.getString("UserId");
			FirmName=requestParam.getString("FirmName");
			Address=requestParam.getString("Address");
			StateId=requestParam.getString("StateId");
			CityId=requestParam.getString("CityId");
			PinCodeId=requestParam.getString("PinCodeId");
			DistricId=requestParam.getString("DistricId");
			QualificationId=requestParam.getString("QualificationId");
			ExperienceYears=requestParam.getString("ExperienceYears");
			ProjectHandled=requestParam.getString("ProjectHandled");
			RegionId=requestParam.getString("RegionId");
			Websites=requestParam.getString("Websites");
			Marrige_Anniversary=requestParam.getString("Marrige_Anniversary");
			DOB=requestParam.getString("DOB");

		} catch (Exception e) {
			e.printStackTrace();
		}
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		try {
			Statement stmt = mCon.createStatement();
			String sql="update users_details set "
					+ "FirmName = ?,"
					+ "Address = ?,"
					+ "StateId = ?,"
					+ "CityId = ?,"
					+ "PinCodeId = ?,"
					+ "QualificationId = ?,"
					+ "ExperienceYears = ?,"
					+ "ProjectHandled = ?,"
					+ "IsActive = ?,"
					+ "RegionId = ?,"
					+ "DistricId = ?,"
				+ "Marrige_Anniversary = ?,"
					+ "DOB = ?,"
					+ "Websites = ?"
					+ "WHERE user_Id = ?";
			PreparedStatement preparedStmt = mCon.prepareStatement(sql);
			preparedStmt.setString (1, FirmName);
			preparedStmt.setString (2, Address);
			preparedStmt.setString (3, StateId);
			preparedStmt.setString (4, CityId);
			preparedStmt.setString (5, PinCodeId);
			preparedStmt.setString (6, QualificationId);
			preparedStmt.setString (7, ExperienceYears);
			preparedStmt.setString (8, ProjectHandled);
			preparedStmt.setString (9, "0");
			preparedStmt.setString (10, RegionId);
			preparedStmt.setString (11, DistricId);
			preparedStmt.setString (12, Marrige_Anniversary);
			preparedStmt.setString (13, DOB);
			preparedStmt.setString (14, Websites);
			preparedStmt.setInt(15,Integer.parseInt(UserId));
			boolean f=preparedStmt.execute();

			Statement stmt2 = mCon.createStatement();
			String sql2="SELECT users_details.*, master_profession.Profession, master_qualification.Qualification, master_stateregion.Region, master_city.CityName, master_state.StateName ,master_pin_code.Pin,master_distric.name as dist_Name FROM users_details "
					+ "LEFT JOIN master_profession ON users_details.profession_Id=master_profession.profession_Id "
					+ "LEFT JOIN master_qualification ON users_details.QualificationId=master_qualification.Qualification_Id "
					+ "LEFT JOIN master_stateregion ON users_details.RegionId=master_stateregion.id "
					+ "LEFT JOIN master_city ON users_details.CityId=master_city.CityId "
					+ "LEFT JOIN master_state ON users_details.StateId=master_state.State_Id "
					+ "LEFT JOIN master_pin_code ON users_details.PinCodeId=master_pin_code.Id "
					+ "LEFT JOIN master_distric ON users_details.DistricId=master_distric.id "
					+ "where user_Id='"+UserId+"'" ;
			ResultSet rs = stmt2.executeQuery(sql2);
			if(rs.next())
			{
				UserDetailModel model=new UserDetailModel();
				model.setAddress(rs.getString("Address"));
				model.setCity(rs.getString("CityName"));
				model.setCityId(rs.getString("CityId"));
				model.setEmailId(rs.getString("email"));;
				model.setExperienceYears(rs.getString("ExperienceYears"));
				model.setFirmName(rs.getString("FirmName"));
				model.setMessage("Updated Successfully");
				model.setMobileNo(rs.getString("mob_no"));
				model.setProfession(rs.getString("Profession"));
				model.setProfessionId(rs.getString("profession_Id"));
				model.setProjectHandled(rs.getString("ProjectHandled"));
				model.setQualification(rs.getString("Qualification"));
				model.setQualificationId(rs.getString("QualificationId"));
				model.setRegion(rs.getString("Region"));
				model.setRegionId(rs.getString("RegionId"));
				model.setState(rs.getString("StateName"));
				model.setStateId(rs.getString("StateId"));
				model.setStatus(MessageConstants.StatusSuccess);
				model.setUserId(rs.getString("user_Id"));
				model.setUserName(rs.getString("user_Name"));
				model.setPinCode(rs.getString("Pin"));
				model.setPinCodeId(rs.getString("PinCodeId"));
				model.setDistric(rs.getString("dist_Name"));
				model.setDistricId(rs.getString("DistricId"));
				model.setDOB(rs.getString("DOB"));
				model.setWebsites(rs.getString("Websites"));
				model.setMarrige_Anniversary(rs.getString("Marrige_Anniversary"));
				ResponseUtil.setResponse(mWriter, model);
				UserDetailModel data=WebProjectLIstUtils.getUserdetailBasedonId(model.getUserId());
				String sms=null;
				sms=data.getProfession()+", "+data.getUserName()+" From "+data.getDistric()+" District, has been updated his profile";
				HitUrlUtils.sendInqtoRMAndTso(model.getUserId(),sms);
			}
			rs.close();
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
