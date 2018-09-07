package com.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
import com.google.gson.Gson;
import com.listener.DBConnectionProvider;
import com.model.MachinaryModel;
import com.model.ManPowerModel;
import com.model.SpecializationModel;
import com.model.UserDetailModel;
import com.utils.GeneralRequestFormat;
import com.utils.Invoker;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class GetUsersDetails
 */
public class GetUsersDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	public GetUsersDetails() {
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
					getdetail();
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
	void getdetail() 
	{
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		try{
			String uid=requestParam.optString("UserId");
			if(uid!=null)
			{
				Statement stmt = mCon.createStatement();
				
				String sql="SELECT users_details.*, master_profession.Profession, master_qualification.Qualification, master_stateregion.Region, master_city.CityName, master_state.StateName ,master_pin_code.Pin,master_distric.name as dist_Name FROM users_details "
						+ "LEFT JOIN master_profession ON users_details.profession_Id=master_profession.profession_Id "
						+ "LEFT JOIN master_qualification ON users_details.QualificationId=master_qualification.Qualification_Id "
						+ "LEFT JOIN master_stateregion ON users_details.RegionId=master_stateregion.id "
						+ "LEFT JOIN master_city ON users_details.CityId=master_city.CityId "
						+ "LEFT JOIN master_state ON users_details.StateId=master_state.State_Id "
						+ "LEFT JOIN master_pin_code ON users_details.PinCodeId=master_pin_code.Id "
						+ "LEFT JOIN master_distric ON users_details.DistricId=master_distric.id "
						+ "where user_Id='"+uid+"'" ;
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next())
				{
					UserDetailModel model=new UserDetailModel();
					model.setAddress(rs.getString("Address"));
					model.setCity(rs.getString("CityName"));
					model.setCityId(rs.getString("CityId"));
					model.setEmailId(rs.getString("email"));;
					model.setExperienceYears(rs.getString("ExperienceYears"));
					model.setFirmName(rs.getString("FirmName"));
					model.setMessage("Success");
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
					model.setImageUrl(rs.getString("ProfileImage"));
					model.setDOB(rs.getString("DOB"));
					model.setDescription(rs.getString("Description"));
					model.setProfileVisitCount(rs.getString("profile_visit_count"));
					model.setPinCode(rs.getString("Pin"));
					model.setPinCodeId(rs.getString("PinCodeId"));
					model.setDistric(rs.getString("dist_Name"));
					model.setDistricId(rs.getString("DistricId"));
					model.setMarrige_Anniversary(rs.getString("Marrige_Anniversary"));
					model.setWebsites(rs.getString("Websites"));
					
					try {
						String specializationQuery="SELECT user_speciliazation.SpeciliazationId, master_specialization.Specialization FROM user_speciliazation INNER JOIN master_specialization ON user_speciliazation.SpeciliazationId=master_specialization.Specialization_Id WHERE UserId='"+uid+"'";
						Statement stmt4 = mCon.createStatement();
						ResultSet rs4 = stmt4.executeQuery(specializationQuery);
						ArrayList<SpecializationModel> list=new ArrayList<>();
						while(rs4.next())
						{
							SpecializationModel model2=new SpecializationModel();
							model2.setSpecializationId(rs4.getString("SpeciliazationId"));
							model2.setSpecializationType(rs4.getString("Specialization"));
							list.add(model2);
						}
						model.setUsersSpeciliazation(list);
					} catch (Exception e) {
						e.printStackTrace();
					}


					ResponseUtil.setResponse(mWriter, model);
				}
				else
				{
					ResponseUtil.seterror(mWriter, "No data found", MessageConstants.StatusFail);
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
	

	
}
