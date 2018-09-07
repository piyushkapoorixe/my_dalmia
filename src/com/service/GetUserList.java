package com.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
import com.listener.DBConnectionProvider;
import com.model.CiityModelList;
import com.model.CityModel;
import com.model.LoginModel;
import com.model.SearchUserDetailList;
import com.model.UserDetailModel;
import com.utils.GeneralRequestFormat;
import com.utils.Invoker;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class GetUserList
 */
public class GetUserList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	public GetUserList() {
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
					getUsers();
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
	void getUsers() 
	{
		String ProfessionId=null;
		String CityName=null;

		/******Manually search permater request****/
		String CityId=null;
		String DistrictId=null;
		String StateId=null;
		String StateName=null;
		/**********/
		try {
			ProfessionId=requestParam.getString("ProfessionId");
			CityName=requestParam.optString("CityName");
			CityId=requestParam.optString("CityId");
			DistrictId=requestParam.optString("DistrictId");
			StateId=requestParam.optString("StateId");
			StateName=requestParam.optString("StateName");

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
			String sql=null;

			if(CityName!=null && CityName.trim().length()>0)
			{
				//deault search only
				if(ProfessionId.equals("3"))
				{
					// 3 mean contactor
					 sql="SELECT users_details.*, master_profession.Profession, master_qualification.Qualification, master_stateregion.Region, master_city.CityName, master_state.StateName ,master_pin_code.Pin,master_distric.name as dist_Name FROM users_details "
							+ "LEFT JOIN master_profession ON users_details.profession_Id=master_profession.profession_Id "
							+ "LEFT JOIN master_qualification ON users_details.QualificationId=master_qualification.Qualification_Id "
							+ "LEFT JOIN master_stateregion ON users_details.RegionId=master_stateregion.id "
							+ "LEFT JOIN master_city ON users_details.CityId=master_city.CityId "
							+ "LEFT JOIN master_state ON users_details.StateId=master_state.State_Id "
							+ "LEFT JOIN master_pin_code ON users_details.PinCodeId=master_pin_code.Id "
							+ "LEFT JOIN master_distric ON users_details.DistricId=master_distric.id "
							+ "where  master_city.CityName='"+CityName+"' AND users_details.profession_Id="+ProfessionId+" ORDER BY users_details.user_Name ASC" ;	
				
						/*users_details.IsActive='1' AND*/

				}
				else
				{
					// 1 and 2 mean Architect and Engineer
					sql="SELECT users_details.*, master_profession.Profession, master_qualification.Qualification, master_stateregion.Region, master_city.CityName, master_state.StateName ,master_pin_code.Pin,master_distric.name as dist_Name FROM users_details "
							+ "LEFT JOIN master_profession ON users_details.profession_Id=master_profession.profession_Id "
							+ "LEFT JOIN master_qualification ON users_details.QualificationId=master_qualification.Qualification_Id "
							+ "LEFT JOIN master_stateregion ON users_details.RegionId=master_stateregion.id "
							+ "LEFT JOIN master_city ON users_details.CityId=master_city.CityId "
							+ "LEFT JOIN master_state ON users_details.StateId=master_state.State_Id "
							+ "LEFT JOIN master_pin_code ON users_details.PinCodeId=master_pin_code.Id "
							+ "LEFT JOIN master_distric ON users_details.DistricId=master_distric.id "
							+ "where master_city.CityName='"+CityName+"' AND users_details.profession_Id in (1,2) ORDER BY users_details.user_Name ASC" ;	
				
					/*users_details.IsActive='1' AND*/

				}

			}
			else
			{
				//manually search only
				if(CityId!=null && CityId.trim().length()>0)
				{
					//city based Searching
					if(ProfessionId.equals("3"))
					{
						// 3 mean contactor
						sql="SELECT users_details.*, master_profession.Profession, master_qualification.Qualification, master_stateregion.Region, master_city.CityName, master_state.StateName ,master_pin_code.Pin,master_distric.name as dist_Name FROM users_details "
								+ "LEFT JOIN master_profession ON users_details.profession_Id=master_profession.profession_Id "
								+ "LEFT JOIN master_qualification ON users_details.QualificationId=master_qualification.Qualification_Id "
								+ "LEFT JOIN master_stateregion ON users_details.RegionId=master_stateregion.id "
								+ "LEFT JOIN master_city ON users_details.CityId=master_city.CityId "
								+ "LEFT JOIN master_state ON users_details.StateId=master_state.State_Id "
								+ "LEFT JOIN master_pin_code ON users_details.PinCodeId=master_pin_code.Id "
								+ "LEFT JOIN master_distric ON users_details.DistricId=master_distric.id "
								+ "where master_city.CityId='"+CityId+"' AND users_details.profession_Id="+ProfessionId+" ORDER BY users_details.user_Name ASC" ;	
						/*users_details.IsActive='1' AND*/

					}
					else
					{
						// 1 and 2 mean Architect and Engineer
						sql="SELECT users_details.*, master_profession.Profession, master_qualification.Qualification, master_stateregion.Region, master_city.CityName, master_state.StateName ,master_pin_code.Pin,master_distric.name as dist_Name FROM users_details "
								+ "LEFT JOIN master_profession ON users_details.profession_Id=master_profession.profession_Id "
								+ "LEFT JOIN master_qualification ON users_details.QualificationId=master_qualification.Qualification_Id "
								+ "LEFT JOIN master_stateregion ON users_details.RegionId=master_stateregion.id "
								+ "LEFT JOIN master_city ON users_details.CityId=master_city.CityId "
								+ "LEFT JOIN master_state ON users_details.StateId=master_state.State_Id "
								+ "LEFT JOIN master_pin_code ON users_details.PinCodeId=master_pin_code.Id "
								+ "LEFT JOIN master_distric ON users_details.DistricId=master_distric.id "
								+ "where master_city.CityId='"+CityId+"' AND users_details.profession_Id in (1,2) ORDER BY users_details.user_Name ASC" ;	
				
						/*where users_details.IsActive='1' AND master_city.CityId='"+CityId+"*/
					}

				}else if(DistrictId!=null && DistrictId.trim().length()>0)
				{
					//District based Searching
					if(ProfessionId.equals("3"))
					{
						// 3 mean contactor
						sql="SELECT users_details.*, master_profession.Profession, master_qualification.Qualification, master_stateregion.Region, master_city.CityName, master_state.StateName ,master_pin_code.Pin,master_distric.name as dist_Name FROM users_details "
								+ "LEFT JOIN master_profession ON users_details.profession_Id=master_profession.profession_Id "
								+ "LEFT JOIN master_qualification ON users_details.QualificationId=master_qualification.Qualification_Id "
								+ "LEFT JOIN master_stateregion ON users_details.RegionId=master_stateregion.id "
								+ "LEFT JOIN master_city ON users_details.CityId=master_city.CityId "
								+ "LEFT JOIN master_state ON users_details.StateId=master_state.State_Id "
								+ "LEFT JOIN master_pin_code ON users_details.PinCodeId=master_pin_code.Id "
								+ "LEFT JOIN master_distric ON users_details.DistricId=master_distric.id "
								+ "where master_distric.id='"+DistrictId+"' AND users_details.profession_Id="+ProfessionId+" ORDER BY users_details.user_Name ASC" ;	
						/*users_details.IsActive='1' AND*/
					}
					else
					{
						// 1 and 2 mean Architect and Engineer
						sql="SELECT users_details.*, master_profession.Profession, master_qualification.Qualification, master_stateregion.Region, master_city.CityName, master_state.StateName ,master_pin_code.Pin,master_distric.name as dist_Name FROM users_details "
								+ "LEFT JOIN master_profession ON users_details.profession_Id=master_profession.profession_Id "
								+ "LEFT JOIN master_qualification ON users_details.QualificationId=master_qualification.Qualification_Id "
								+ "LEFT JOIN master_stateregion ON users_details.RegionId=master_stateregion.id "
								+ "LEFT JOIN master_city ON users_details.CityId=master_city.CityId "
								+ "LEFT JOIN master_state ON users_details.StateId=master_state.State_Id "
								+ "LEFT JOIN master_pin_code ON users_details.PinCodeId=master_pin_code.Id "
								+ "LEFT JOIN master_distric ON users_details.DistricId=master_distric.id "
								+ "where master_distric.id='"+DistrictId+"' AND users_details.profession_Id in (1,2) ORDER BY users_details.user_Name ASC" ;	
					
						/*users_details.IsActive='1' AND*/
					}

				}
				else if(StateId!=null && StateId.trim().length()>0)
				{
					//State based Searching
					if(ProfessionId.equals("3"))
					{
						// 3 mean contactor
						sql="SELECT users_details.*, master_profession.Profession, master_qualification.Qualification, master_stateregion.Region, master_city.CityName, master_state.StateName ,master_pin_code.Pin,master_distric.name as dist_Name FROM users_details "
								+ "LEFT JOIN master_profession ON users_details.profession_Id=master_profession.profession_Id "
								+ "LEFT JOIN master_qualification ON users_details.QualificationId=master_qualification.Qualification_Id "
								+ "LEFT JOIN master_stateregion ON users_details.RegionId=master_stateregion.id "
								+ "LEFT JOIN master_city ON users_details.CityId=master_city.CityId "
								+ "LEFT JOIN master_state ON users_details.StateId=master_state.State_Id "
								+ "LEFT JOIN master_pin_code ON users_details.PinCodeId=master_pin_code.Id "
								+ "LEFT JOIN master_distric ON users_details.DistricId=master_distric.id "
								+ "where  master_state.State_Id='"+StateId+"' AND users_details.profession_Id="+ProfessionId+" ORDER BY users_details.user_Name ASC" ;	
					
						/*users_details.IsActive='1' AND*/
					
					}
					else
					{
						// 1 and 2 mean Architect and Engineer
						sql="SELECT users_details.*, master_profession.Profession, master_qualification.Qualification, master_stateregion.Region, master_city.CityName, master_state.StateName ,master_pin_code.Pin,master_distric.name as dist_Name FROM users_details "
								+ "LEFT JOIN master_profession ON users_details.profession_Id=master_profession.profession_Id "
								+ "LEFT JOIN master_qualification ON users_details.QualificationId=master_qualification.Qualification_Id "
								+ "LEFT JOIN master_stateregion ON users_details.RegionId=master_stateregion.id "
								+ "LEFT JOIN master_city ON users_details.CityId=master_city.CityId "
								+ "LEFT JOIN master_state ON users_details.StateId=master_state.State_Id "
								+ "LEFT JOIN master_pin_code ON users_details.PinCodeId=master_pin_code.Id "
								+ "LEFT JOIN master_distric ON users_details.DistricId=master_distric.id "
								+ "where master_state.State_Id='"+StateId+"' AND users_details.profession_Id in (1,2) ORDER BY users_details.user_Name ASC" ;	
						/*users_details.IsActive='1' AND*/
					
					}
				}
				
			else if(StateName!=null && StateName.trim().length()>0)
				{
					//State based Searching
					if(ProfessionId.equals("3"))
					{
						// 3 mean contactor
						sql="SELECT users_details.*, master_profession.Profession, master_qualification.Qualification, master_stateregion.Region, master_city.CityName, master_state.StateName ,master_pin_code.Pin,master_distric.name as dist_Name FROM users_details "
								+ "LEFT JOIN master_profession ON users_details.profession_Id=master_profession.profession_Id "
								+ "LEFT JOIN master_qualification ON users_details.QualificationId=master_qualification.Qualification_Id "
								+ "LEFT JOIN master_stateregion ON users_details.RegionId=master_stateregion.id "
								+ "LEFT JOIN master_city ON users_details.CityId=master_city.CityId "
								+ "LEFT JOIN master_state ON users_details.StateId=master_state.State_Id "
								+ "LEFT JOIN master_pin_code ON users_details.PinCodeId=master_pin_code.Id "
								+ "LEFT JOIN master_distric ON users_details.DistricId=master_distric.id "
								+ "where  master_state.StateName='"+StateName+"' AND users_details.profession_Id="+ProfessionId+" ORDER BY users_details.user_Name ASC" ;	
					
						/*users_details.IsActive='1' AND*/
					
					}
					else
					{
						// 1 and 2 mean Architect and Engineer
						sql="SELECT users_details.*, master_profession.Profession, master_qualification.Qualification, master_stateregion.Region, master_city.CityName, master_state.StateName ,master_pin_code.Pin,master_distric.name as dist_Name FROM users_details "
								+ "LEFT JOIN master_profession ON users_details.profession_Id=master_profession.profession_Id "
								+ "LEFT JOIN master_qualification ON users_details.QualificationId=master_qualification.Qualification_Id "
								+ "LEFT JOIN master_stateregion ON users_details.RegionId=master_stateregion.id "
								+ "LEFT JOIN master_city ON users_details.CityId=master_city.CityId "
								+ "LEFT JOIN master_state ON users_details.StateId=master_state.State_Id "
								+ "LEFT JOIN master_pin_code ON users_details.PinCodeId=master_pin_code.Id "
								+ "LEFT JOIN master_distric ON users_details.DistricId=master_distric.id "
								+ "where master_state.StateName='"+StateName+"' AND users_details.profession_Id in (1,2) ORDER BY users_details.user_Name ASC" ;	
						/*users_details.IsActive='1' AND*/
					
					}
				}
			}
			System.out.println("@@@@@@@@"+sql);
			ResultSet rs = stmt.executeQuery(sql);
			SearchUserDetailList data=new SearchUserDetailList();
			ArrayList<UserDetailModel> list=new ArrayList<>();
			while (rs.next()) {
				UserDetailModel model=new UserDetailModel();
				model.setAddress(rs.getString("Address"));
				model.setCity(rs.getString("CityName"));
				model.setCityId(rs.getString("CityId"));
				model.setEmailId(rs.getString("email"));
				model.setExperienceYears(rs.getString("ExperienceYears"));
				model.setFirmName(rs.getString("FirmName"));
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
				model.setUserId(rs.getString("user_Id"));
				model.setUserName(rs.getString("user_Name"));
				model.setDOB(rs.getString("DOB"));
				model.setImageUrl(rs.getString("ProfileImage"));
				model.setDescription(rs.getString("Description"));
				model.setProfileVisitCount(rs.getString("profile_visit_count"));
				model.setPinCode(rs.getString("Pin"));
				model.setPinCodeId(rs.getString("PinCodeId"));
				model.setDistric(rs.getString("dist_Name"));
				model.setDistricId(rs.getString("DistricId"));
				model.setMarrige_Anniversary(rs.getString("Marrige_Anniversary"));
				model.setWebsites(rs.getString("Websites"));
				model.setActiveStatus(rs.getString("IsActive"));
				list.add(model);
			}
			if(list.size()>0)
			{
				data.setMessage("Success");
				data.setStatus(MessageConstants.StatusSuccess);
				data.setUsersResult(list);
				rs.close();
				ResponseUtil.setResponse(mWriter, data);
			}
			else
			{
				data.setMessage("No data found");
				data.setStatus(MessageConstants.StatusFail);
				rs.close();
				ResponseUtil.setResponse(mWriter, data);
			}
		} catch (Exception e) {
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}
		}
	}
}
