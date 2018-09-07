package com.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.constants.MessageConstants;
import com.google.gson.Gson;
import com.listener.DBConnectionProvider;
import com.model.MachinaryModel;
import com.model.ManPowerModel;
import com.model.ProfessionDetailItemModel;
import com.model.ProfessionDetailModel;
import com.model.ProjectImageModel;
import com.model.QualificationModel;
import com.model.RegionModel;
import com.model.RegionModelList;
import com.model.SpecializationModel;
import com.model.SpecializationModelList;
import com.model.UserDetailModel;
import com.webmodel.UsersProjectListItem;
import com.webmodel.UsersProjectModel;

public class GetProjectListUtils {

	public  UserDetailModel getList(String UserId)
	{
		Connection con=null;
		ArrayList<UserDetailModel> userDetail=null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql="SELECT users_details.*, master_profession.Profession, master_qualification.Qualification, master_stateregion.Region, master_city.CityName, master_state.StateName ,master_pin_code.Pin,master_distric.name as dist_Name FROM users_details "
						+ "LEFT JOIN master_profession ON users_details.profession_Id=master_profession.profession_Id "
						+ "LEFT JOIN master_qualification ON users_details.QualificationId=master_qualification.Qualification_Id "
						+ "LEFT JOIN master_stateregion ON users_details.RegionId=master_stateregion.id "
						+ "LEFT JOIN master_city ON users_details.CityId=master_city.CityId "
						+ "LEFT JOIN master_state ON users_details.StateId=master_state.State_Id "
						+ "LEFT JOIN master_pin_code ON users_details.PinCodeId=master_pin_code.Id "
						+ "LEFT JOIN master_distric ON users_details.DistricId=master_distric.id "
						+ "where user_Id='"+UserId+"'" ;
			ResultSet rs = stmt.executeQuery(sql);
			UserDetailModel model=null;
			if(rs.next())
			{
				model=new UserDetailModel();
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
				model.setUserId(rs.getString("user_Id"));
				model.setUserName(rs.getString("user_Name"));
                model.setImageUrl(rs.getString("ProfileImage"));
				model.setActivationStatus(rs.getString("IsActive"));
				model.setDateOfRegistration(rs.getString("createDate"));
				model.setDescription(rs.getString("Description"));
				model.setPinCode(rs.getString("Pin"));
				model.setPinCodeId(rs.getString("PinCodeId"));
				model.setDistric(rs.getString("dist_Name"));
				model.setDistricId(rs.getString("DistricId"));
				model.setMarrige_Anniversary(rs.getString("Marrige_Anniversary"));
				model.setWebsites(rs.getString("Websites"));
				
				ArrayList<UsersProjectListItem> projects=new ArrayList<>();
				try {
					String manPowerQuery="SELECT users_project.*, master_city.CityName,master_state.State_Id, master_state.StateName , master_distric.id as dist_Id, master_distric.name as dist_name FROM users_project Join master_city On master_city.CityId=users_project.City_Id Join master_distric On master_distric.id=master_city.DiscticId Join master_state On master_state.State_Id=master_distric.stateId WHERE users_project.U_Id='"+UserId+"'";
					Statement stmt2 = con.createStatement();
					ResultSet rs2 = stmt2.executeQuery(manPowerQuery);
					while(rs2.next())
					{

						String p_id=rs2.getString("Id");
						UsersProjectListItem project_item=new UsersProjectListItem();
						project_item.setProjectId(p_id);
						project_item.setProjectLocation(rs2.getString("Location"));
						project_item.setProjectName(rs2.getString("ProjectName"));
						project_item.setProjectPrice(rs2.getString("Price"));
						project_item.setArea(rs2.getString("Area"));
						project_item.setIs_Active(rs2.getString("Is_Active"));
						project_item.setStateId(rs2.getString("State_Id"));
						project_item.setCityId(rs2.getString("City_Id"));
						project_item.setStateName(rs2.getString("StateName"));
						project_item.setCityName(rs2.getString("CityName"));
						project_item.setDistric(rs2.getString("dist_name"));
						project_item.setDistricId(rs2.getString("dist_Id"));
						/***********************Machnine************************/	
						String machinaryQuery="SELECT project_machineries.ManchineriesId, project_machineries.NoOfMachineries,master_machineries.Machineries FROM project_machineries INNER JOIN master_machineries ON master_machineries.Machineries_Id=project_machineries.ManchineriesId WHERE project_machineries.ProjectId='"+p_id+"'";
						Statement stmt3 = con.createStatement();
						ResultSet rs3 = stmt3.executeQuery(machinaryQuery);
						ArrayList<MachinaryModel> machineList=new ArrayList<MachinaryModel>();
						while(rs3.next())
						{
							MachinaryModel machine=new MachinaryModel();
							machine.setMachineriesId(rs3.getString("ManchineriesId"));
							machine.setNoOfMachineries(rs3.getString("NoOfMachineries"));
							machine.setMachineriesType(rs3.getString("Machineries"));
							machineList.add(machine);
						}
						project_item.setMachinary(machineList);
						/***********************Machnine END************************/

						/***********************Manpower************************/	
						String manpowerQuery="SELECT project_manpower.ManpowerId, project_manpower.NoOfManpower, master_manpower.Manpower FROM project_manpower INNER JOIN master_manpower ON master_manpower.ManpowerId=project_manpower.ManpowerId WHERE project_manpower.ProjectId='"+p_id+"'";
						Statement stmt4 = con.createStatement();
						ResultSet rs4 = stmt4.executeQuery(manpowerQuery);
						ArrayList<ManPowerModel> manpowerList=new ArrayList<ManPowerModel>();
						while(rs4.next())
						{
							ManPowerModel manpower=new ManPowerModel();
							manpower.setManpowerId(rs4.getString("ManpowerId"));
							manpower.setNoOfManpower(rs4.getString("NoOfManpower"));
							manpower.setManpowerType(rs4.getString("Manpower"));
							manpowerList.add(manpower);
						}
						project_item.setManPower(manpowerList);
						/***********************Machnine END************************/	


						/***********************Images************************/	
						String imagesQuery="SELECT Image_Path from users_project_image WHERE Project_Id='"+p_id+"'";
						Statement stmt5 = con.createStatement();
						ResultSet rs5 = stmt5.executeQuery(imagesQuery);
						ArrayList<String> imageList=new ArrayList<String>();
						while(rs5.next())
						{
							imageList.add(rs5.getString("Image_Path"));
						}
						project_item.setImageData(imageList);
						projects.add(project_item);
						/***********************Images END************************/	
					}

					StringBuilder specializationString=new StringBuilder();
					String specializationQuery="SELECT Specialization FROM master_specialization INNER JOIN user_speciliazation ON user_speciliazation.SpeciliazationId=master_specialization.Specialization_Id WHERE user_speciliazation.UserId='"+UserId+"'";
					Statement stmtSpecialization = con.createStatement();
					ResultSet rsSpecialization = stmtSpecialization.executeQuery(specializationQuery);
					while(rsSpecialization.next())
					{
						specializationString.append(rsSpecialization.getString("Specialization")).append(",");

					}
					model.setSpecialization(specializationString.toString());
					model.setProjects(projects);
					return model;
				} catch (Exception e) {
					e.getLocalizedMessage();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}

		}
		return null;
	}



	public UsersProjectListItem getProjectDetail(String Pid)
	{
		Connection con=null;
		UsersProjectListItem data=new UsersProjectListItem();
		try {
			con = DBConnectionProvider.getCon();
			/***********************Manpower************************/	
			String manpowerQuery="SELECT project_manpower.ManpowerId, project_manpower.NoOfManpower, master_manpower.Manpower FROM project_manpower INNER JOIN master_manpower ON master_manpower.ManpowerId=project_manpower.ManpowerId WHERE project_manpower.ProjectId='"+Pid+"'";
			Statement stmt4 = con.createStatement();
			ResultSet rs4 = stmt4.executeQuery(manpowerQuery);
			ArrayList<ManPowerModel> manpowerList=new ArrayList<ManPowerModel>();
			while(rs4.next())
			{
				ManPowerModel manpower=new ManPowerModel();
				manpower.setManpowerId(rs4.getString("ManpowerId"));
				manpower.setNoOfManpower(rs4.getString("NoOfManpower"));
				manpower.setManpowerType(rs4.getString("Manpower"));
				manpowerList.add(manpower);
			}
			data.setManPower(manpowerList);
			/***********************Machnine END************************/	
			
			/***********************Machnine************************/	
			String machinaryQuery="SELECT project_machineries.ManchineriesId, project_machineries.NoOfMachineries,master_machineries.Machineries FROM project_machineries INNER JOIN master_machineries ON master_machineries.Machineries_Id=project_machineries.ManchineriesId WHERE project_machineries.ProjectId='"+Pid+"'";
			Statement stmt3 = con.createStatement();
			ResultSet rs3 = stmt3.executeQuery(machinaryQuery);
			ArrayList<MachinaryModel> machineList=new ArrayList<MachinaryModel>();
			while(rs3.next())
			{
				MachinaryModel machine=new MachinaryModel();
				machine.setMachineriesId(rs3.getString("ManchineriesId"));
				machine.setNoOfMachineries(rs3.getString("NoOfMachineries"));
				machine.setMachineriesType(rs3.getString("Machineries"));
				machineList.add(machine);
			}
			data.setMachinary(machineList);
			/***********************Machnine END************************/
			
			
			
			/***********************Detail************************/	
			String projectDetail="SELECT users_project.*, master_city.CityName , master_state.State_Id, master_state.StateName , master_distric.id as dist_id, master_distric.name as dist_name FROM users_project Join master_city On master_city.CityId=users_project.City_Id Join master_distric On master_distric.id=master_city.DiscticId Join master_state On master_distric.stateId=master_state.State_Id WHERE users_project.Id='"+Pid+"'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(projectDetail);
			if(rs.next())
			{
				data.setProjectLocation(rs.getString("Location"));
				data.setProjectName(rs.getString("ProjectName"));
				data.setProjectPrice(rs.getString("Price"));
				data.setArea(rs.getString("Area"));
				data.setCityName(rs.getString("CityName"));
				data.setStateName(rs.getString("StateName"));
				data.setStateId(rs.getString("State_Id"));
				data.setCityId(rs.getString("City_Id"));
				data.setDistric(rs.getString("dist_name"));
				data.setDistricId(rs.getString("dist_id"));
			}
			/***********************Detail END************************/
			
			return data;
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}
		}
		return null;
	}
	
	public UserDetailModel getPersonalDetail(String Uid)
	{
		Connection con=null;
		UserDetailModel model=new UserDetailModel();
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql="SELECT users_details.*, master_profession.Profession, master_qualification.Qualification, master_stateregion.Region, master_city.CityName, master_state.StateName ,master_pin_code.Pin,master_distric.name as dist_Name FROM users_details "
						+ "LEFT JOIN master_profession ON users_details.profession_Id=master_profession.profession_Id "
						+ "LEFT JOIN master_qualification ON users_details.QualificationId=master_qualification.Qualification_Id "
						+ "LEFT JOIN master_stateregion ON users_details.RegionId=master_stateregion.id "
						+ "LEFT JOIN master_city ON users_details.CityId=master_city.CityId "
						+ "LEFT JOIN master_state ON users_details.StateId=master_state.State_Id "
						+ "LEFT JOIN master_pin_code ON users_details.PinCodeId=master_pin_code.Id "
						+ "LEFT JOIN master_distric ON users_details.DistricId=master_distric.id "
						+ "where user_Id='"+Uid+"'" ;
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
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
				model.setUserId(rs.getString("user_Id"));
				model.setUserName(rs.getString("user_Name"));
				model.setActivationStatus(rs.getString("IsActive"));
				model.setDateOfRegistration(rs.getString("createDate"));
				model.setDescription(rs.getString("Description"));
				model.setPinCode(rs.getString("Pin"));
				model.setPinCodeId(rs.getString("PinCodeId"));
				model.setDistric(rs.getString("dist_Name"));
				model.setDistricId(rs.getString("DistricId"));
				model.setMarrige_Anniversary(rs.getString("Marrige_Anniversary"));
				model.setWebsites(rs.getString("Websites"));
			}
			return model;
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}
		}
		return null;
	}
	
	public static ArrayList<ProfessionDetailItemModel> getProfessionType()
	{
		Connection mCon=null;
		ArrayList<ProfessionDetailItemModel> listProfession=null;
		try {
			mCon = DBConnectionProvider.getCon();
			Statement stmt = mCon.createStatement();
			String sql="SELECT * from master_profession where is_Active='1'" ;
			ResultSet rs = stmt.executeQuery(sql);
			listProfession=new ArrayList<>();
			while (rs.next()) {
				ProfessionDetailItemModel item=new ProfessionDetailItemModel();
				item.setProfessionId(rs.getString("profession_Id"));
				item.setProfessionType(rs.getString("Profession"));
				listProfession.add(item);
			}
			return listProfession;
		} catch (Exception e) {
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}
		return null;
	}
	
	public static ArrayList<RegionModel> getRegion()
	{
		Connection mCon=null;
		ArrayList<RegionModel> listRegion=null;
		try {
			mCon = DBConnectionProvider.getCon();
			Statement stmt = mCon.createStatement();
			String sql="SELECT * from master_stateregion where IsActive='1' AND id!='4' Order by Region Asc" ;
			ResultSet rs = stmt.executeQuery(sql);
			listRegion=new ArrayList<>();
			while (rs.next()) {
				RegionModel item=new RegionModel();
				item.setRegionId(rs.getString("id"));
				item.setRegionType(rs.getString("Region"));
				listRegion.add(item);
			}
			return listRegion;
		} catch (Exception e) {
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}
		return null;
	}
	
	public static ArrayList<SpecializationModel> getSpecialization()
	{
		Connection mCon=null;
		ArrayList<SpecializationModel>  listSpecialization=null;
		try {
			mCon = DBConnectionProvider.getCon();
			Statement stmt = mCon.createStatement();
			String sql="SELECT * from master_specialization where Is_Active='1'" ;
			ResultSet rs = stmt.executeQuery(sql);
			listSpecialization=new ArrayList<>();
			while (rs.next()) {
				SpecializationModel item=new SpecializationModel();
				item.setSpecializationId(rs.getString("Specialization_Id"));
				item.setSpecializationType(rs.getString("Specialization"));
				listSpecialization.add(item);
			}
			return listSpecialization;

		} catch (Exception e) {
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}
		
		return null;
	}
	
	
	
	
	public static ArrayList<QualificationModel> getQulification()
	{
		Connection mCon=null;
		ArrayList<QualificationModel>  listQualification=null;
		try {
			mCon = DBConnectionProvider.getCon();
			Statement stmt = mCon.createStatement();
			String sql="SELECT * from master_qualification where Is_Active='1'" ;
			ResultSet rs = stmt.executeQuery(sql);
			listQualification=new ArrayList<>();
			while (rs.next()) {
				QualificationModel item=new QualificationModel();
				item.setQualificationId(rs.getString("Qualification_Id"));
				item.setQualificationType(rs.getString("Qualification"));
				listQualification.add(item);
			}
			return listQualification;

		} catch (Exception e) {
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}
		
		return null;
	}
	
	
	public static ArrayList<String> userSpecialization(String User_id)
	{Connection mCon=null;
	ArrayList<String>  specilizationList=null;
	try {
		mCon = DBConnectionProvider.getCon();
		Statement stmt = mCon.createStatement();
		String sql="SELECT 	SpeciliazationId from user_speciliazation WHERE UserId='"+User_id+"'" ;
		ResultSet rs = stmt.executeQuery(sql);
		specilizationList=new ArrayList<>();
		while (rs.next()) {
			specilizationList.add(String.valueOf(rs.getInt("SpeciliazationId")));
		}
		return specilizationList;

	} catch (Exception e) {
	}finally {
		try {
			DBConnectionProvider.close(mCon);
		} catch (SQLException e) {
		}

	}
	
	return null;	
	}
}
