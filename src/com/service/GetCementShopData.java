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
import com.model.CementShopModel;
import com.model.CementShopModelList;
import com.utils.GeneralRequestFormat;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class GetCementShopData
 */
public class GetCementShopData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();

	public GetCementShopData() {
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
					getShopList();
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

	void getShopList() 
	{
		String CityId=null;
		String DistrictId=null;
		String StateId=null;
		String CityName=null;
		String StateName=null;
		String RecordCount=null;
		String sql=null;
			CityName=requestParam.optString("CityName");
			CityId=requestParam.optString("CityId");
			DistrictId=requestParam.optString("DistrictId");
			StateId=requestParam.optString("StateId");
			RecordCount=requestParam.optString("RecordCount");
			StateName=requestParam.optString("StateName");
			mCon=mDbUtils.generateConnection();	
			if(mCon==null)
			{
				ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
				return;
			}
			if(CityName!=null && CityName.length()>0)
			{
				sql="SELECT dalmia_cement_shop.*, master_city.CityName, master_state.StateName , master_distric.name as dist_Name FROM dalmia_cement_shop LEFT JOIN master_city ON dalmia_cement_shop.CityId=master_city.CityId LEFT JOIN master_state ON dalmia_cement_shop.StateId=master_state.State_Id LEFT JOIN master_distric ON dalmia_cement_shop.District_Id=master_distric.id where master_city.CityName='"+CityName+"' LIMIT "+RecordCount+",20";
			}
			else if(CityId!=null && CityId.length()>0)
			{
				sql="SELECT dalmia_cement_shop.*, master_city.CityName, master_state.StateName , master_distric.name as dist_Name FROM dalmia_cement_shop LEFT JOIN master_city ON dalmia_cement_shop.CityId=master_city.CityId LEFT JOIN master_state ON dalmia_cement_shop.StateId=master_state.State_Id LEFT JOIN master_distric ON dalmia_cement_shop.District_Id=master_distric.id where dalmia_cement_shop.CityId='"+CityId+"' LIMIT "+RecordCount+",20";
			}
			else if(DistrictId!=null && DistrictId.length()>0)
			{
				sql="SELECT dalmia_cement_shop.*, master_city.CityName, master_state.StateName , master_distric.name as dist_Name FROM dalmia_cement_shop LEFT JOIN master_city ON dalmia_cement_shop.CityId=master_city.CityId LEFT JOIN master_state ON dalmia_cement_shop.StateId=master_state.State_Id LEFT JOIN master_distric ON dalmia_cement_shop.District_Id=master_distric.id where dalmia_cement_shop.District_Id='"+DistrictId+"' LIMIT "+RecordCount+",20";
			}
			else if(StateId!=null && StateId.length()>0)
			{
				sql="SELECT dalmia_cement_shop.*, master_city.CityName, master_state.StateName , master_distric.name as dist_Name FROM dalmia_cement_shop LEFT JOIN master_city ON dalmia_cement_shop.CityId=master_city.CityId LEFT JOIN master_state ON dalmia_cement_shop.StateId=master_state.State_Id LEFT JOIN master_distric ON dalmia_cement_shop.District_Id=master_distric.id where dalmia_cement_shop.StateId='"+StateId+"' LIMIT "+RecordCount+",20";
			}
			
			
			else if(StateName!=null && StateName.length()>0)
			{
				sql="SELECT dalmia_cement_shop.*, master_city.CityName, master_state.StateName , master_distric.name as dist_Name FROM dalmia_cement_shop LEFT JOIN master_city ON dalmia_cement_shop.CityId=master_city.CityId LEFT JOIN master_state ON dalmia_cement_shop.StateId=master_state.State_Id LEFT JOIN master_distric ON dalmia_cement_shop.District_Id=master_distric.id where master_state.StateName='"+StateName+"' LIMIT "+RecordCount+",20";
			}
			try {
				Statement stmt = mCon.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				CementShopModelList model=new CementShopModelList();
				ArrayList<CementShopModel> list=new ArrayList<>();
				while (rs.next()) {
					CementShopModel item=new CementShopModel();
					item.setId(rs.getString("Id"));
					item.setDelearId(rs.getString("DelearId"));
					item.setAdd(rs.getString("Address"));
					item.setAdd2(rs.getString("Address1"));
					item.setAdd3(rs.getString("Address2"));
					item.setPinCode(rs.getString("PinCode"));
					item.setCityId(rs.getString("CityId"));
					item.setCity(rs.getString("CityName"));
					item.setDistricId(rs.getString("District_Id"));
					item.setDistrict(rs.getString("dist_Name"));
					item.setStateId(rs.getString("StateId"));
					item.setState(rs.getString("StateName"));
					list.add(item);
				}
				if(list.size()>0)
				{
					model.setMessage("Success");
					model.setStatus(MessageConstants.StatusSuccess);
					model.setShopList(list);
					rs.close();
					ResponseUtil.setResponse(mWriter, model);
				}
				else
				{
					model.setMessage("No data found");
					model.setStatus(MessageConstants.StatusFail);
					rs.close();
					ResponseUtil.setResponse(mWriter, model);
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
