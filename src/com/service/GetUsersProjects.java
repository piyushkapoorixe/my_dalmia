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
import com.model.MachinaryModel;
import com.model.ManPowerModel;
import com.model.UserDetailModel;
import com.responsemodel.CommonResponseModel;
import com.utils.GeneralRequestFormat;
import com.utils.ResponseUtil;
import com.webmodel.UsersProjectListItem;

/**
 * Servlet implementation class GetUsersProjects
 */
public class GetUsersProjects extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;

	private String OtherProjects=null; // if 0->will my project detail 1-other project detail
	private String URL=this.getClass().getSimpleName();
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
					OtherProjects=requestParam.getString("OtherProjects");
					getList(requestParam.getString("UserId"));
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
	public void  getList(String UserId)
	{
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		try {
			Statement stmt = mCon.createStatement();
			UserDetailModel model=null;

			ArrayList<UsersProjectListItem> projects=new ArrayList<>();
			try {
				model=new UserDetailModel();
				String manPowerQuery="SELECT users_project.*, master_city.CityName,master_state.State_Id, master_state.StateName , master_distric.id as dist_Id, master_distric.name as dist_name FROM users_project Join master_city On master_city.CityId=users_project.City_Id Join master_distric On master_distric.id=master_city.DiscticId Join master_state On master_state.State_Id=master_distric.stateId WHERE users_project.U_Id="+UserId+" AND users_project.Is_Active=1";
				Statement stmt2 = mCon.createStatement();
				ResultSet rs2 = stmt2.executeQuery(manPowerQuery);
				UsersProjectListItem project_item=null;
				while(rs2.next())
				{
					String p_id=rs2.getString("Id");
					project_item=new UsersProjectListItem();
					project_item.setProjectId(p_id);
					project_item.setProjectLocation(rs2.getString("Location"));
					project_item.setProjectName(rs2.getString("ProjectName"));
					project_item.setProjectPrice(rs2.getString("Price"));
					project_item.setArea(rs2.getString("Area"));
					project_item.setStateId(rs2.getString("State_Id"));
					project_item.setCityId(rs2.getString("City_Id"));
					project_item.setStateName(rs2.getString("StateName"));
					project_item.setCityName(rs2.getString("CityName"));
					project_item.setDistric(rs2.getString("dist_name"));
					project_item.setDistricId(rs2.getString("dist_Id"));
					
					/***********************Machnine************************/	
					String machinaryQuery="SELECT project_machineries.ManchineriesId, project_machineries.NoOfMachineries,master_machineries.Machineries FROM project_machineries INNER JOIN master_machineries ON master_machineries.Machineries_Id=project_machineries.ManchineriesId WHERE project_machineries.ProjectId='"+p_id+"'";
					Statement stmt3 = mCon.createStatement();
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
					Statement stmt4 = mCon.createStatement();
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
					Statement stmt5 = mCon.createStatement();
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
				if(projects.size()>0)
				{
					model.setMessage("Success");
					model.setStatus(MessageConstants.StatusSuccess);
					model.setProjects(projects);
					ResponseUtil.setResponse(mWriter, model);	
				}
				else
				{
					if(OtherProjects.equals("0"))
						ResponseUtil.seterror(mWriter, "Your Projects Approval Is Pending ", MessageConstants.StatusFail);
					else if(OtherProjects.equals("1"))
						ResponseUtil.seterror(mWriter, "No Project Found", MessageConstants.StatusFail);
				}
			} catch (Exception e) {
				ResponseUtil.seterror(mWriter, e.getLocalizedMessage(),MessageConstants.StatusFail );
			}
		} catch (Exception e) {
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(),MessageConstants.StatusFail );
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}
		}
	}
}
