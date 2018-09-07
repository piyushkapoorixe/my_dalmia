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
import com.model.LandMarkModel;
import com.model.LandMarkModelList;
import com.model.RegionalBannerModel;
import com.model.RegionalBannerModelList;
import com.utils.GeneralRequestFormat;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class GetDalmiaLandMarkProject
 */
public class GetDalmiaLandMarkProject extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private  String StateName;
	private String URL=this.getClass().getSimpleName();
	public GetDalmiaLandMarkProject() {
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
					getLandMarkProject();
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

	void getLandMarkProject() 
	{
		StateName=requestParam.optString("StateName");
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
			try {
				Statement stmt = mCon.createStatement();
				String sql="SELECT a.* FROM dalmia_land_mark_project a LEFT join master_state b on a.Region_Id=b.Region_id where b.StateName='"+StateName+"' OR a.Region_Id=4" ;
				ResultSet rs = stmt.executeQuery(sql);
				LandMarkModelList model=new LandMarkModelList();
				ArrayList<LandMarkModel> list=new ArrayList<LandMarkModel>();
				while (rs.next()) {
					LandMarkModel item=new LandMarkModel();
					item.setId(rs.getString("Id"));
					item.setDescription(rs.getString("P_Desc"));
					item.setImage(rs.getString("P_Image"));
					item.setName(rs.getString("P_Name"));
					item.setPlace(rs.getString("P_Place"));
					item.setRegionId(rs.getString("Region_Id"));
					list.add(item);
				}
				if(list.size()>0) {
					model.setMessage("Success");
					model.setStatus(MessageConstants.StatusSuccess);
					model.setListLandMarkProjects(list);
					rs.close();
					ResponseUtil.setResponse(mWriter, model);
				}
				else {
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
