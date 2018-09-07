package com.AdminServiceAjax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.constants.MessageConstants;
import com.constants.WebConstants;
import com.db.DBUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.listener.DBConnectionProvider;
import com.utils.GeneralRequestFormat;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class AjaxController
 */
public class AjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	private Statement stmt;
	private HttpServletRequest request;
	int regType;
	public AjaxController() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain");
		request=req;
		mWriter=resp.getWriter();
		regType=Integer.parseInt(req.getParameter("Type"));
		startInsertion(req, resp);
	}
	void startInsertion(HttpServletRequest req, HttpServletResponse response)
	{
		try{
			mWriter=response.getWriter();
			mDbUtils=new DBUtils();
			switch (regType) {
			case WebConstants.City:
				insertCity();
				break;
			case WebConstants.Machinary:
				insertMachinary();
				break;
			case WebConstants.ManPower:
				insertManPower();
				break;
			case WebConstants.Profession:
				insertProfession();
				break;
			case WebConstants.Qualification:
				insertQualification();
				break;
			case WebConstants.QueryFeedback:
				insertQueryFeedback();
				break;
			case WebConstants.Region:
				insertRegion();
				break;
			case WebConstants.Specialization:
				insertSpecialization();
				break;
			case WebConstants.State:
				insertState();
				break;
			case WebConstants.UserRegion:
				insertUserRegion();
				break;
			}		

		}catch(Exception e)
		{
			mWriter.println(e.getLocalizedMessage());
		}

	}


	private void insertMachinary()
	{
		String Value=request.getParameter("Value");
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(Value.trim().length()>0)
		{
			mCon=mDbUtils.generateConnection();
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try
			{
				stmt = mCon.createStatement();
				String sql="SELECT Machineries from master_machineries where Machineries='"+Value+"'" ;
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next())
				{
					mWriter.println(Value+" Already Exists");	
				}
				else
				{
					try {
						String insertQuery="INSERT INTO master_machineries(Machineries, Is_Active)"
								+ " VALUES ('"+Value+"',"+isActive+")";
						PreparedStatement preparedStmt = mCon.prepareStatement(insertQuery);
						preparedStmt.execute();
						mWriter.println(Value+" Inserted succefully");	
					} catch (Exception e) {
						mWriter.println(e.getLocalizedMessage());
					}

				}
			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}	
		}
		else
		{
			mWriter.println("Please enter the text");	
		}

	}
	private void insertManPower()
	{
		String Value=request.getParameter("Value");
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(Value.trim().length()>0)
		{
			mCon=mDbUtils.generateConnection();
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try
			{
				stmt = mCon.createStatement();
				String sql="SELECT Manpower from master_manpower where Manpower='"+Value+"'" ;
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next())
				{
					mWriter.println(Value+" Already Exists");	
				}
				else
				{
					try {
						String insertQuery="INSERT INTO master_manpower(Manpower, Is_Active)"
								+ " VALUES ('"+Value+"',"+isActive+")";
						PreparedStatement preparedStmt = mCon.prepareStatement(insertQuery);
						preparedStmt.execute();
						mWriter.println(Value+" Inserted succefully");	
					} catch (Exception e) {
						mWriter.println(e.getLocalizedMessage());
					}

				}
			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}	
		}
		else
		{
			mWriter.println("Please enter the text");	
		}


	}
	private void insertProfession()
	{
		String Value=request.getParameter("Value");
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(Value.trim().length()>0)
		{
			mCon=mDbUtils.generateConnection();
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try
			{
				stmt = mCon.createStatement();
				String sql="SELECT Profession from master_profession where Profession='"+Value+"'" ;
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next())
				{
					mWriter.println(Value+" Already Exists");	
				}
				else
				{
					try {
						String insertQuery="INSERT INTO master_profession(Profession, is_Active)"
								+ " VALUES ('"+Value+"',"+isActive+")";
						PreparedStatement preparedStmt = mCon.prepareStatement(insertQuery);
						preparedStmt.execute();
						mWriter.println(Value+" Inserted succefully");	
					} catch (Exception e) {
						mWriter.println(e.getLocalizedMessage());
					}

				}
			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}	
		}
		else
		{
			mWriter.println("Please enter the text");	
		}

	}


	private void insertRegion()
	{
		String Region=request.getParameter("Region");
		String Contact=request.getParameter("Contact");
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(Region.trim().length()>0)
		{
			if(Contact.trim().length()>0 && Contact.trim().length()>=10)
			{
				mCon=mDbUtils.generateConnection();
				if(mCon==null)
				{
					mWriter.println("Database problem occur");
					return;
				}
				try
				{
					stmt = mCon.createStatement();
					long nu=Long.parseLong(Contact);
					try
					{
						String region="SELECT Region from master_stateregion where Region='"+Region+"'" ;
						ResultSet rsregion = stmt.executeQuery(region);
						if(rsregion.next())
						{
							mWriter.println(Region+" Already Exists");
							rsregion.close();
						}
						else
						{
							String contactQr="SELECT ContactNo from master_stateregion where ContactNo='"+Contact+"'" ;
							ResultSet rscontact = stmt.executeQuery(contactQr);
							if(rscontact.next())
							{
								mWriter.println(Contact+" Already Exists");	
								rscontact.close();
							}
							else
							{
								try {
									String insertQuery="INSERT INTO master_stateregion(Region,ContactNo, IsActive)"
											+ " VALUES ('"+Region+"','"+Contact+"',"+isActive+")";
									PreparedStatement preparedStmt = mCon.prepareStatement(insertQuery);
									preparedStmt.execute();
									mWriter.println(Region+" Inserted succefully");	
								} catch (Exception e) {
									mWriter.println(e.getLocalizedMessage());
								}


							}

						}
					}catch(Exception e){
						mWriter.println(e.getLocalizedMessage());
					}
				}
				catch(Exception e){
					mWriter.println("You have entered Wrong Contact Number");
				}finally {
					try {
						DBConnectionProvider.close(mCon);
					} catch (SQLException e) {
					}

				}

			}
			else
			{
				mWriter.println("Please enter the valid Contact Number");	
			}

		}
		else
		{
			mWriter.println("Please enter the Region");	
		}

	}


	private void insertState()
	{
		String State=request.getParameter("State");
		String Region=request.getParameter("Region");
		String RegionId=null;
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(State.trim().length()>0 )
		{
			mCon=mDbUtils.generateConnection();
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try
			{
				stmt = mCon.createStatement();
				String qry="SELECT id from master_stateregion where Region='"+Region+"'" ;
				ResultSet rsId = stmt.executeQuery(qry);
				ResultSet rsCheckState =null;
				if(rsId.next())
				{
					RegionId=rsId.getString("id");
					String qryState="SELECT StateName from master_state where StateName='"+State+"'" ;
					rsCheckState = stmt.executeQuery(qryState);
					if(rsCheckState.next())
					{
						mWriter.println(State+" Already exist");
					}
					else
					{
						String qryInsertState="INSERT INTO master_state(StateName,Region_Id, Is_Active)" + " VALUES ('"+State+"','"+RegionId+"',"+isActive+")" ;
						PreparedStatement preparedStmt = mCon.prepareStatement(qryInsertState);
						preparedStmt.execute();
						mWriter.println(State+" Inserted Successfully");

					}
				}
				else
				{
					mWriter.println("No Region Found");
				}
				rsId.close();
				rsCheckState.close();
			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}

		}
		else
		{
			mWriter.println("Please enter the State Name");	
		}
	}

	private void insertCity()
	{
		String Distric=request.getParameter("Distric");
		String City=request.getParameter("City");
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(Distric!=null && Distric.trim().length()>0 && !(Distric.trim().equals("null")))
		{
			if(City.trim().length()>0 )
			{
				mCon=mDbUtils.generateConnection();
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try
			{
				
				if(request.getParameter("Tag")!=null )
				{
					//request for edit the city
					String CityId=request.getParameter("CityId");
					if(request.getParameter("deleteStatus").equals("true")){
						String deleteSQL = "DELETE FROM master_city WHERE CityId=?";
						PreparedStatement preparedStatement = mCon.prepareStatement(deleteSQL);
						preparedStatement.setInt(1, Integer.parseInt(CityId));
						preparedStatement.executeUpdate();
						mWriter.println(City+" Deleted Successfully");
					}
					else{
						String sql="update master_city set "
								+ "CityName = ?,"
								+ "DiscticId = ?,"
								+ "Is_Active = ? "
								+ "WHERE CityId = ?";
						PreparedStatement preparedStmt = mCon.prepareStatement(sql);
						preparedStmt.setString (1, City);
						preparedStmt.setString (2, Distric);
						preparedStmt.setInt (3, isActive);
						preparedStmt.setString (4, CityId);
						preparedStmt.executeUpdate();
						mWriter.println(City+" Updated Successfully");
					}

				}else{
					stmt = mCon.createStatement();
					String qry="SELECT * from  master_city where CityName='"+City.trim()+"'" ;
					ResultSet rsId = stmt.executeQuery(qry);
					if(rsId.next())
					{
						mWriter.println(""+City+" Already Exists");	
					}
					String qryInsertCity="INSERT INTO master_city(CityName,DiscticId, Is_Active)" + " VALUES ('"+City+"','"+Distric+"',"+isActive+")" ;
					PreparedStatement preparedStmt = mCon.prepareStatement(qryInsertCity);
					preparedStmt.execute();
					mWriter.println(City+" Inserted Successfully");
					rsId.close();
				}
				
			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}
			finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}
			}
			}
			else
			{
				mWriter.println("Please enter the City Name");	
			}	
		}
		else
		{
			mWriter.println("Please select the Distric");		
		}
		
		

	}


	private void insertQualification()
	{
		String Qualification=request.getParameter("Qualification");
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(Qualification.trim().length()>0)
		{
			mCon=mDbUtils.generateConnection();
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try
			{
				stmt = mCon.createStatement();
				String sql="SELECT Qualification from master_qualification where Qualification='"+Qualification+"'" ;
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next())
				{
					mWriter.println(Qualification+" Already Exists");	
				}
				else
				{
					try {
						String insertQuery="INSERT INTO master_qualification(Qualification, Is_Active)"
								+ " VALUES ('"+Qualification+"',"+isActive+")";
						PreparedStatement preparedStmt = mCon.prepareStatement(insertQuery);
						preparedStmt.execute();
						mWriter.println(Qualification+" Inserted succefully");	
					} catch (Exception e) {
						mWriter.println(e.getLocalizedMessage());
					}

				}
			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}	
		}
		else
		{
			mWriter.println("Please enter the Qualification");	
		}
	}

	private void insertUserRegion()
	{
		String UserRegion=request.getParameter("UserRegion");
		String state=request.getParameter("state");
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(UserRegion.trim().length()>0)
		{
			mCon=mDbUtils.generateConnection();
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try
			{
				stmt = mCon.createStatement();
				String sql="SELECT Region from master_user_region where Region='"+UserRegion+"'" ;
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next())
				{
					mWriter.println(UserRegion+" Already Exists");	
				}
				else
				{
					try {
						String insertQuery="INSERT INTO master_user_region(Region,StateId, Is_Active)" + " VALUES ('"+UserRegion+"',"+state+","+isActive+")";
						PreparedStatement preparedStmt = mCon.prepareStatement(insertQuery);
						preparedStmt.execute();
						mWriter.println(UserRegion+" Inserted succefully");	
					} catch (Exception e) {
						mWriter.println(e.getLocalizedMessage());
					}

				}
			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}	
		}
		else
		{
			mWriter.println("Please enter the Region");	
		}
	}
	private void insertSpecialization()
	{

		String Specialization=request.getParameter("Specialization");
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(Specialization.trim().length()>0)
		{
			
			mCon=mDbUtils.generateConnection();
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try
			{
				stmt = mCon.createStatement();
				String sql="SELECT Specialization from master_specialization where Specialization='"+Specialization+"'" ;
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next())
				{
					mWriter.println(Specialization+" Already Exists");	
				}
				else
				{
					try {
						String insertQuery="INSERT INTO master_specialization(Specialization, Is_Active)"
								+ " VALUES ('"+Specialization+"',"+isActive+")";
						PreparedStatement preparedStmt = mCon.prepareStatement(insertQuery);
						preparedStmt.execute();
						mWriter.println(Specialization+" Inserted succefully");	
					} catch (Exception e) {
						mWriter.println(e.getLocalizedMessage());
					}

				}
			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}	
		}
		else
		{
			mWriter.println("Please enter the Specialization");	
		}
	}


	private void insertQueryFeedback()
	{

		String QueryType=null;
		String FeebckAndQueryType=request.getParameter("FeebckAndQueryType");
		if(FeebckAndQueryType.equals("Query"))
		{
			QueryType="Q";	
		}
		else if(FeebckAndQueryType.equals("Feedback"))
		{
			QueryType="F";
		}
		String FeebckAndQuery=request.getParameter("FeebckAndQuery");
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(FeebckAndQuery.trim().length()>0 )
		{
			mCon=mDbUtils.generateConnection();
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try
			{
				stmt = mCon.createStatement();
				String qry="SELECT Query from master_queryfeedback where Query='"+FeebckAndQuery+"'" ;
				ResultSet rsQuery = stmt.executeQuery(qry);
				if(rsQuery.next())
				{
					mWriter.println(FeebckAndQuery+" Already Exists");
				}
				else
				{
					String qryInsertQuery="INSERT INTO master_queryfeedback(Query,Type, IsActive)" + " VALUES ('"+FeebckAndQuery+"','"+QueryType+"',"+isActive+")" ;
					PreparedStatement preparedStmt = mCon.prepareStatement(qryInsertQuery);
					preparedStmt.execute();
					mWriter.println(FeebckAndQuery+" Inserted Successfully");

				}
			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}

		}
		else
		{
			mWriter.println("Please enter the Query/feedback ");	
		}

	}
}
