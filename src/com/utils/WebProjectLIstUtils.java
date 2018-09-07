package com.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.constants.MessageConstants;
import com.google.gson.Gson;
import com.listener.DBConnectionProvider;
import com.model.CityModel;
import com.model.DistricModel;
import com.model.MachinaryModel;
import com.model.MachineryModelList;
import com.model.ManPowerModel;
import com.model.ManPowerModelList;
import com.model.PinModel;
import com.model.SpecializationModel;
import com.model.UserDetailModel;
import com.webmodel.AdminListModel;
import com.webmodel.AdminRole;
import com.webmodel.MasterCity;
import com.webmodel.MasterRegionModel;
import com.webmodel.MasterState;

public class WebProjectLIstUtils {




	public static ArrayList<MasterState> getStateList() {
		ArrayList<MasterState> listState=null;
		Connection con=null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "SELECT master_state.State_Id,master_state.StateName from master_state where Is_Active='1'";
			ResultSet rs = stmt.executeQuery(sql);
			listState = new ArrayList<MasterState>();
			while (rs.next()) {
				MasterState item = new MasterState();
				item.setStateId(rs.getString("State_Id"));
				item.setStateName(rs.getString("StateName"));
				listState.add(item);
			}
			rs.close();
			return listState;
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

	public static ArrayList<MasterCity> getCityList(String StateId) {
		Connection con = null;
		ArrayList<MasterCity> listCity=null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql="SELECT * from master_city where Is_Active='1' AND StateId='"+StateId+"'" ;
			ResultSet rs = stmt.executeQuery(sql);
			listCity = new ArrayList<MasterCity>();
			while (rs.next()) {
				MasterCity item = new MasterCity();
				item.setCityId(rs.getString("CityId"));
				item.setCityName(rs.getString("CityName"));
				listCity.add(item);
			}
			rs.close();
			return listCity;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}

		}
		return null;
	}
	
	
	
	public static ArrayList<ManPowerModel> getManpower() {
		Connection con = null;
		ArrayList<ManPowerModel> listManpower=null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql="SELECT * from master_manpower where Is_Active='1'" ;
			ResultSet rs = stmt.executeQuery(sql);
			listManpower=new ArrayList<>();
			while (rs.next()) {
				ManPowerModel item=new ManPowerModel();
				item.setManpowerId(rs.getString("ManpowerId"));
				item.setManpowerType(rs.getString("Manpower"));
				listManpower.add(item);
			}
			return listManpower; 
		} catch (Exception e) {
		}finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}

		}
		return null;
	}
	
	public static ArrayList<MachinaryModel> getMachaniry() {
		Connection con = null;
		ArrayList<MachinaryModel> listMachine=null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql="SELECT * from master_machineries where Is_Active='1'" ;
			ResultSet rs = stmt.executeQuery(sql);
			listMachine=new ArrayList<>();
			while (rs.next()) {
				MachinaryModel item=new MachinaryModel();
				item.setMachineriesId(rs.getString("Machineries_Id"));
				item.setMachineriesType(rs.getString("Machineries"));
				listMachine.add(item);
			}
			return listMachine; 
		} catch (Exception e) {
		}finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}

		}
		return null;
	}
	
	public static ArrayList<SpecializationModel> getSpecialization(String Uid) {
		Connection con = null;
		try {
			con = DBConnectionProvider.getCon();
			String specializationQuery="SELECT user_speciliazation.SpeciliazationId, master_specialization.Specialization FROM user_speciliazation INNER JOIN master_specialization ON user_speciliazation.SpeciliazationId=master_specialization.Specialization_Id WHERE UserId='"+Uid+"'";
			Statement stmt4 = con.createStatement();
			ResultSet rs4 = stmt4.executeQuery(specializationQuery);
			ArrayList<SpecializationModel> list=new ArrayList<>();
			while(rs4.next())
			{
				SpecializationModel model2=new SpecializationModel();
				model2.setSpecializationId(rs4.getString("SpeciliazationId"));
				model2.setSpecializationType(rs4.getString("Specialization"));
				list.add(model2);
			}
			return list;
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
	
	public static ArrayList<MasterRegionModel> getRegionList(String StateId)
	{
			Connection con=null;
			ArrayList<MasterRegionModel> listState=null;
			try {
				con = DBConnectionProvider.getCon();
				Statement stmt = con.createStatement();
				String sql="SELECT * from master_user_region where Is_Active='1' AND StateId='"+StateId+"'" ;
				ResultSet rs = stmt.executeQuery(sql);
				listState=new ArrayList<>();
				while (rs.next()) {
					MasterRegionModel data=new MasterRegionModel();
					data.setId(rs.getString("Region_id"));
					data.setRegion(rs.getString("Region"));
					listState.add(data);
				}
				rs.close();
				return listState;
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
	
	
	public static ArrayList<MasterRegionModel> getMainRegionList()
	{
			Connection con=null;
			ArrayList<MasterRegionModel> listRegion=null;
			try {
				con = DBConnectionProvider.getCon();
				Statement stmt = con.createStatement();
				String sql="SELECT * from master_stateregion where IsActive='1' AND id!='4' Order by Region Asc" ;
				ResultSet rs = stmt.executeQuery(sql);
				listRegion=new ArrayList<>();
				while (rs.next()) {
					MasterRegionModel data=new MasterRegionModel();
					data.setId(rs.getString("id"));
					data.setRegion(rs.getString("Region"));
					listRegion.add(data);
				}
				rs.close();
				return listRegion;
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
	
	public static ArrayList<DistricModel> getListOfDistricBasedOnState(String StateId) {
		Connection con = null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "SELECT id,name from master_distric where isactive=1 AND stateId='"+StateId+"'";
			ResultSet rs = stmt.executeQuery(sql);
			
			ArrayList<DistricModel> listDistric = new ArrayList<DistricModel>();
			while (rs.next()) {
				DistricModel item = new DistricModel();
				item.setId(rs.getString("id"));
				item.setName(rs.getString("name"));
				listDistric.add(item);
			}
			rs.close();
			return listDistric;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}

		}
		return null;
	}
	
	public static ArrayList<CityModel> getListOfCityBasedOnDistric(String DistId) {
		Connection con = null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "SELECT * from master_city where Is_Active=1 AND DiscticId='"+DistId+"'";
			ResultSet rs = stmt.executeQuery(sql);
			
			ArrayList<CityModel> listDistric = new ArrayList<CityModel>();
			while (rs.next()) {
				CityModel item = new CityModel();
				item.setCityId(rs.getString("CityId"));
				item.setCityName(rs.getString("CityName"));
				listDistric.add(item);
			}
			rs.close();
			return listDistric;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}

		}
		return null;
	}
	
	public static ArrayList<PinModel> getListOfPinBasedOnCity(String cityId) {
		Connection con = null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "SELECT * from master_pin_code where isactive=1 AND CItyId='"+cityId+"'";
			ResultSet rs = stmt.executeQuery(sql);
			
			ArrayList<PinModel> listPin = new ArrayList<PinModel>();
			while (rs.next()) {
				PinModel item = new PinModel();
				item.setPinCode(rs.getString("Pin"));
				item.setId(rs.getString("Id"));
				listPin.add(item);
			}
			rs.close();
			return listPin;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}

		}
		return null;
	}
	public static ArrayList<AdminRole> getListOfAdminRole() {
		Connection con = null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql = "SELECT * from master_admin_role";
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<AdminRole> listPin = new ArrayList<AdminRole>();
			while (rs.next()) {
				AdminRole item = new AdminRole();
				item.setType(rs.getString("RoleType"));
				item.setId(rs.getString("Id"));
				listPin.add(item);
			}
			rs.close();
			return listPin;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}

		}
		return null;
	}
	
	public static Object validateUser(String id, String password)
	{
		Connection con = null;
		String error=null;
		String pass = PasswordEncryption.encryptPasword(password);
		Statement stmt = null;
		try {
			con = DBConnectionProvider.getCon();
			if(con!=null)
			{
				stmt = con.createStatement();
				String sql = "SELECT tbl_adminlogin_details.*, master_stateregion.Region as MainRegion, master_user_region.Region as subRegionName, master_state.StateName as stateName, master_distric.name as distName, master_admin_role.RoleType as RoleName from tbl_adminlogin_details Left Join master_stateregion On master_stateregion.id=tbl_adminlogin_details.RegionId Left Join master_user_region On master_user_region.Region_id=tbl_adminlogin_details.SubRegion Left Join master_state On master_state.State_Id=tbl_adminlogin_details.StateId Left Join master_admin_role On master_admin_role.Id=tbl_adminlogin_details.RoleType Left Join master_distric On master_distric.id=tbl_adminlogin_details.DistricId  Where  tbl_adminlogin_details.Password='" + pass + "' AND tbl_adminlogin_details.LoginID='" + id + "'";
				ResultSet rs = stmt.executeQuery(sql);
				AdminListModel model = new AdminListModel();
				if (rs.next()) {
					model.setEmailId(rs.getString("Email_ID"));
					model.setMobile(rs.getString("Mobile_No"));
					model.setName(rs.getString("UserName"));
					model.setRegion(rs.getString("MainRegion"));
					model.setRegionId(rs.getString("RegionId"));
					model.setDistricId(rs.getString("DistricId"));
					model.setRoleId(rs.getString("RoleType"));
					model.setRole(rs.getString("RoleName"));
					model.setStateId(rs.getString("StateId"));
					model.setState_RegionId(rs.getString("SubRegion"));
					String time=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
					updateTime(time,model.getMobile());
					model.setLastLoginTime(time);
					rs.close();
					return model;
				}
			}
			else
			{
				error="Database Problem Occur!";
				return error;
			}
		} catch (Exception e) {
			e.printStackTrace();
			error=e.getLocalizedMessage();
		} finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}
		}
		return error;
	}
	
	private static void  updateTime(String time,String mob) {
		Connection con = null;
		Statement stmt = null;
		try {
			con = DBConnectionProvider.getCon();
			stmt = con.createStatement();
			String sql = "Update tbl_adminlogin_details  Set LoginTime='" + time + "' Where Mobile_No='"+mob+"'";
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}
		}
	}
	
	public static String getQueryForAdminRole(String RoleType,String Profession,String Distric,String SubRegion,String Region)
	{
		String sql=null;
		if(RoleType.equals("1"))
		{
			//Admin
//		   sql="Select users_details.user_Id,users_details.user_Name,users_details.createDate,users_details.IsActive,users_details.Address,master_stateregion.Region from users_details Join master_stateregion On master_stateregion.id=users_details.RegionId where users_details.profession_Id="+Profession+" Order by `createDate` Desc";	
		
			sql="Select users_details.user_Id, users_details.user_Name, users_details.createDate, users_details.IsActive, users_details.Address, master_stateregion.Region as zone, master_distric.name as dist_name, master_state.StateName as state, master_user_region.Region as region, master_city.CityName as city from users_details Join master_stateregion On master_stateregion.id=users_details.RegionId, master_distric, master_state, master_user_region, master_city where users_details.profession_Id="+Profession+" AND users_details.DistricId=master_distric.id AND users_details.StateId=master_state.State_Id AND master_distric.RegionId=master_user_region.Region_id AND users_details.CityId=master_city.CityId Order by `createDate` Desc";
		
		}else if(RoleType.equals("2")){
			//Tso
			sql="Select users_details.user_Id, users_details.user_Name, users_details.createDate, users_details.IsActive, users_details.Address, master_stateregion.Region as zone, master_distric.name as dist_name, master_state.StateName as state, master_user_region.Region as region, master_city.CityName as city from users_details Join master_stateregion On master_stateregion.id=users_details.RegionId, master_distric, master_state, master_user_region, master_city where users_details.profession_Id="+Profession+" And users_details.DistricId="+Distric+" AND users_details.DistricId=master_distric.id AND users_details.StateId=master_state.State_Id AND master_distric.RegionId=master_user_region.Region_id AND users_details.CityId=master_city.CityId Order by 'createDate' Desc";
		}
		else if(RoleType.equals("3")){
			//Rh
			sql="Select users_details.user_Id, users_details.user_Name, users_details.createDate, users_details.IsActive, users_details.Address, master_stateregion.Region as zone, master_distric.name as dist_name, master_state.StateName as state, master_user_region.Region as region, master_city.CityName as city from users_details Join master_stateregion on master_stateregion.id=users_details.RegionId Join master_distric on master_distric.id=users_details.DistricId, master_state, master_user_region, master_city where users_details.profession_Id="+Profession+" AND master_distric.RegionId="+SubRegion+" AND users_details.StateId=master_state.State_Id AND master_distric.RegionId=master_user_region.Region_id AND users_details.CityId=master_city.CityId Order by 'createDate' Desc";
		}
		else if(RoleType.equals("4")){
			//Th
			sql="Select users_details.user_Id, users_details.user_Name, users_details.createDate, users_details.IsActive, users_details.Address, master_stateregion.Region as zone, master_distric.name as dist_name, master_state.StateName as state, master_user_region.Region as region, master_city.CityName as city from users_details Join master_stateregion On master_stateregion.id=users_details.RegionId, master_distric, master_state, master_user_region, master_city where users_details.profession_Id="+Profession+" And users_details.RegionId="+Region+" AND users_details.DistricId=master_distric.id AND users_details.StateId=master_state.State_Id AND master_distric.RegionId=master_user_region.Region_id AND users_details.CityId=master_city.CityId Order by 'createDate' Desc";
		}
		return sql;
	}
	
	
	
	public static UserDetailModel getUserdetailBasedonId(String id) 
	{
		Connection con = null;
		Statement stmt = null;
		UserDetailModel model=null;
		String sql=null;
		try {
			con = DBConnectionProvider.getCon();
			stmt = con.createStatement();
		    sql="SELECT users_details.user_Name,users_details.mob_no, master_profession.Profession, master_distric.name as dist_Name FROM users_details LEFT JOIN master_profession ON users_details.profession_Id=master_profession.profession_Id LEFT JOIN master_distric ON users_details.DistricId=master_distric.id where user_Id='"+id+"'" ;
		    ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				model=new UserDetailModel();
				model.setMobileNo(rs.getString("mob_no"));
				model.setProfession(rs.getString("Profession"));
				model.setUserName(rs.getString("user_Name"));
				model.setDistric(rs.getString("dist_Name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}
		}
		return  model;
	}
}
