package com.daoImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import com.constants.MessageConstants;
import com.dao.MasterDAO;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.model.BrandModel;
import com.model.CiityModelList;
import com.model.CityModel;
import com.model.DistricModel;
import com.model.DistricModelList;
import com.model.EventModel;
import com.model.MachinaryModel;
import com.model.ManPowerModel;
import com.model.MeetingModel;
import com.model.ProductModel;
import com.model.QualificationModel;
import com.model.RegionModel;
import com.model.StateModel;
import com.utils.PasswordEncryption;
import com.utils.ResponseUtil;
import com.webmodel.AdminListModel;

@Stateless
public class MasterDAOImpl implements MasterDAO {

	private Connection con;
	
	@Override
	public List<StateModel> getStateList() {
		List<StateModel> stateList = new ArrayList<>();
		//String query = "SELECT * FROM master_state WHERE Is_Active = 1";
		String query ="SELECT master_stateregion.id,master_stateregion.Region ,master_state.State_Id,master_state.StateName from master_state INNER JOIN master_stateregion ON master_stateregion.id=master_state.Region_Id where master_state.Is_Active='1'";

		try {
			//if(con == null) {
				con = DBUtils.generateConnection();
			//}
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				StateModel item=new StateModel();
				item.setStateId(rs.getString("State_Id"));
				item.setStateName(rs.getString("StateName"));
				item.setRegionId(rs.getString("id"));
				item.setRegionName(rs.getString("Region"));
				stateList .add(item);
			}
			rs.close();
			con.close();
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return stateList;
	}

	@Override
	public List<DistricModel> getDistricList(int stateId) {
		List<DistricModel> list = new ArrayList<>();
		try {
			//if(con == null) {
				con = DBUtils.generateConnection();
			//}
			Statement stmt = con.createStatement();
			String sql="SELECT * from master_distric where isactive='1' AND stateId='"+stateId+"'" ;
			ResultSet rs = stmt.executeQuery(sql);
			DistricModelList model=new DistricModelList();
			while (rs.next()) {
				DistricModel item=new DistricModel();
				item.setId(rs.getString("id"));
				item.setName(rs.getString("name"));
				list.add(item);
			}
			rs.close();
		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			
		}finally {
			try {
				
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
				return null;
			}

		}
		
		return list;
	}

	@Override
	public List<CityModel> getCityList(int districId) {
		List<CityModel> list = new ArrayList<>();
		try {
			//if(con == null) {
				con = DBUtils.generateConnection();
			//}
				Statement stmt = con.createStatement();
				String sql="SELECT * from master_city where Is_Active='1' AND DiscticId='"+districId+"'" ;
				ResultSet rs = stmt.executeQuery(sql);
				CiityModelList model=new CiityModelList();
				
				while (rs.next()) {
					CityModel item=new CityModel();
					item.setCityId(rs.getString("CityId"));
					item.setCityName(rs.getString("CityName"));
					list.add(item);
				}
				rs.close();

			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					DBConnectionProvider.close(con);
				} catch (SQLException e) {
				}

		}
		
		return list;
	}

	@Override
	public List<QualificationModel> getQualificationList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RegionModel> getRegionList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MachinaryModel> getMachinaryList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManPowerModel> getManPowerList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EventModel> getEventList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BrandModel> getBrandList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductModel> getProductList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MeetingModel> getMeetingList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//@Override
	public Object getTSOLogin(String id, String mobileNo, String password){
		String error = null;
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
					//updateTime(time,model.getMobile());
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
	

	
}
