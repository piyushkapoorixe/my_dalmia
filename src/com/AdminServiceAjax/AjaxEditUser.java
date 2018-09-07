package com.AdminServiceAjax;

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

import org.json.JSONArray;

import com.constants.MessageConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.responsemodel.CommonResponseModel;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class AjaxEditUser
 */
public class AjaxEditUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private HttpServletRequest req;
	private String Uid=null;
	private String Pid=null;
	public AjaxEditUser() {
		super();
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain");
		mWriter=resp.getWriter();
		mDbUtils=new DBUtils();
		this.req=req;
		checkWhetherDetailOrProjectUpdate();
	}
	void checkWhetherDetailOrProjectUpdate()
	{
		if(req.getParameter("Type").equals("UserDetail"))
		{
			editUsersDetail();

		}else if(req.getParameter("Type").equals("ProjectDetail")) 
		{
			editUsersProject();	
		}
	}

	void editUsersDetail()
	{
		String profession,name,qualification,firm,email,mobile,address,region,state,city,pin,proj_handle,desc,dist,exp=null;
		Uid=req.getParameter("Uid");
		profession=req.getParameter("profession");
		name=req.getParameter("name");
		qualification=req.getParameter("qualification");
		firm=req.getParameter("firm");
		email=req.getParameter("email");
		mobile=req.getParameter("mobile");
		address=req.getParameter("address");
		region=req.getParameter("region");
		state=req.getParameter("state");
		city=req.getParameter("city");
		pin=req.getParameter("pin");
		desc=req.getParameter("desc");
		dist=req.getParameter("ListDistric");
		proj_handle=req.getParameter("proj_handle");
		exp=req.getParameter("exp");
		if(!checkMobileAndEmail(email, mobile))
		{
			mCon=mDbUtils.generateConnection();
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try {
				String sql="update users_details set "
						+ "FirmName = ?,"
						+ "Address = ?,"
						+ "StateId = ?,"
						+ "CityId = ?,"
						+ "PinCodeId = ?,"
						+ "ExperienceYears = ?,"
						+ "ProjectHandled = ?,"
						+ "RegionId = ?,"
						+ "profession_Id = ?,"
						+ "user_Name = ?,"
						+ "email = ?,"
						+ "Description = ?,"
						/*+ "IsActive = ?,"*/
						+ "mob_no = ?,"
						+ "QualificationId = ?,"
						+ "DistricId = ?"
						+ "WHERE user_Id = ?";
				PreparedStatement preparedStmt = mCon.prepareStatement(sql);
				preparedStmt.setString (1, firm);
				preparedStmt.setString (2, address);
				preparedStmt.setString (3, state);
				preparedStmt.setString (4, city);
				preparedStmt.setString (5, pin);
				preparedStmt.setString (6, exp+"Year");
				preparedStmt.setString (7, proj_handle);
				preparedStmt.setString (8, region);
				preparedStmt.setString (9, profession);
				preparedStmt.setString (10, name);
				preparedStmt.setString (11, email);
				preparedStmt.setString (12, desc);
				/*preparedStmt.setString (13, "0");*/
				preparedStmt.setString (13, mobile);
				preparedStmt.setString (14, qualification);
				preparedStmt.setString (15, dist);
				preparedStmt.setString (16, Uid);
				preparedStmt.executeUpdate();
				deleteSpecialization();
			

			} catch (Exception e) {
				e.printStackTrace();
				mWriter.println(e.getLocalizedMessage());
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}
			
		}
		
		
		
		
		
		

	}
	
	void deleteSpecialization()
	{
		try {
			String query="delete from user_speciliazation where UserId='"+Uid+"'" ;
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			preparedStmt.executeUpdate();
			updateSpecialization();	
		} catch (SQLException e) {
			mWriter.println(e.getLocalizedMessage());
		}
		
		
	}
	void updateSpecialization()
	{
		try{
			String one= req.getParameter("specialization").replace("\\","");
			String two= one.replace("\"{\"","{\"");
			String finalString= two.replace("\"}\"","\"}");
			JSONArray arr=new JSONArray(finalString);
			for(int i=0;i<arr.length();i++)
			{
				String query = "insert into user_speciliazation (UserId,SpeciliazationId)"+ " values (?, ?)";
				PreparedStatement preparedStmt = mCon.prepareStatement(query);
				preparedStmt.setInt (1, Integer.parseInt(Uid));
				preparedStmt.setString (2, arr.getJSONObject(i).optString("SpecializationId"));
				preparedStmt.execute();	
			}
			
			mWriter.println("Detail Updated Sucessfully");
		}catch(Exception e)
		{
			e.printStackTrace();
			mWriter.println(e.getLocalizedMessage());
		}
	}
	void editUsersProject()
	{
		String p_name,p_value,p_value_select_type,p_area,p_address,p_state,p_city;
		Pid=req.getParameter("Pid");
		p_name=req.getParameter("p_name");
		p_value=req.getParameter("p_value");
		p_value_select_type=req.getParameter("p_value_select_type");
		p_area=req.getParameter("p_area");
		p_address=req.getParameter("p_address");
		//p_state=req.getParameter("p_state");
		p_city=req.getParameter("p_city");
	
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			mWriter.println("Database problem occur");
			return;
		}
		try {
			String query="UPDATE  users_project SET ProjectName= ?,Price = ?,Location = ?,City_Id = ?,Area=? where Id=?" ;
	/*		,Is_Active=?*/
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			preparedStmt.setString (1, p_name);
			preparedStmt.setString (2, p_value+" "+p_value_select_type);
			preparedStmt.setString (3, p_address);
			preparedStmt.setString (4, p_city);
			preparedStmt.setString(5, p_area);
/*			preparedStmt.setString(6, "0");
*/			preparedStmt.setString(6, Pid);
			preparedStmt.executeUpdate();
			
			String man_power= req.getParameter("p_manpower");
			if(!man_power.equals(""))
			deleteAlreadyManpower();
		} catch (SQLException e) {
			mWriter.println(e.getLocalizedMessage());
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}
		}
		
	}
	void deleteAlreadyManpower()
	{
		try {
			String query="delete from project_manpower where ProjectId='"+Pid+"'" ;
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			preparedStmt.executeUpdate();
			updateManpower();
		} catch (SQLException e) {
			mWriter.println(e.getLocalizedMessage());
		}		
	}
	
	void updateManpower() 
	{
		try{
			String one= req.getParameter("p_manpower").replace("\\","");
			String two= one.replace("\"{\"","{\"");
			String finalString= two.replace("\"}\"","\"}");
			JSONArray arr=new JSONArray(finalString);
			for(int i=0;i<arr.length();i++)
			{
				String query = "insert into project_manpower (ProjectId,ManpowerId,NoOfManpower,CreatedDate)"+ " values (?, ?, ?, ?)";
				PreparedStatement preparedStmt = mCon.prepareStatement(query);
				preparedStmt.setString (1, Pid);
				preparedStmt.setString (2, arr.getJSONObject(i).optString("ManpowerId"));
				preparedStmt.setString (3, arr.getJSONObject(i).optString("NoOfManpower"));
				preparedStmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
				preparedStmt.execute();	
				
			}
			String mac= req.getParameter("p_machine");
			if(!mac.equals(""))
			deleteAlreadyMachinaty();
			
		}catch(Exception e)
		{
			e.printStackTrace();
			mWriter.println(e.getLocalizedMessage());
		}
	}
	
	void deleteAlreadyMachinaty()
	{
		try {
			String query="delete from project_machineries where ProjectId='"+Pid+"'" ;
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			preparedStmt.executeUpdate();
			updateMachinary();
		} catch (SQLException e) {
			mWriter.println(e.getLocalizedMessage());
		}	
	}
	void updateMachinary() 
	{
		
		try{
			String one= req.getParameter("p_machine").replace("\\","");
			String two= one.replace("\"{\"","{\"");
			String finalString= two.replace("\"}\"","\"}");
			JSONArray arr=new JSONArray(finalString);
			for(int i=0;i<arr.length();i++)
			{
				String query = "insert into project_machineries (ProjectId,ManchineriesId,NoOfMachineries,CreatedDatet)"+ " values (?, ?, ?, ?)";
				PreparedStatement preparedStmt = mCon.prepareStatement(query);
				preparedStmt.setString (1, Pid);
				preparedStmt.setString (2, arr.getJSONObject(i).optString("MachineriesId"));
				preparedStmt.setString (3, arr.getJSONObject(i).optString("NoOfMachineries"));
				preparedStmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
				preparedStmt.execute();	
			}
			mWriter.println("Project Updated Sucessfully");
		}catch(Exception e)
		{
			e.printStackTrace();
			mWriter.println(e.getLocalizedMessage());
		}
}
	
boolean checkMobileAndEmail(String Email,String Mob)
{
	mCon=mDbUtils.generateConnection();
	try {
		String emailCheck="Select email from users_details WHERE email='"+Email+"' And user_Id !='"+Uid+"'" ;
		String mobileCheck="Select mob_no from users_details WHERE mob_no='"+Mob+"' And user_Id !='"+Uid+"'" ;
		Statement stmt = mCon.createStatement();
		ResultSet emailSet = stmt.executeQuery(emailCheck);
		if(emailSet.next())
		{
			mWriter.println(Email+" Already Registered with us");
			return true;
		}
		ResultSet mobileSet = stmt.executeQuery(mobileCheck);
		if(mobileSet.next())
		{
			mWriter.println(Mob+" Already Registered with us");
			return true;
		}
	} catch (Exception e) {
	
	}finally {
		try {
			DBConnectionProvider.close(mCon);
		} catch (SQLException e) {
		}
	}
   return false;
}
	
	
}
