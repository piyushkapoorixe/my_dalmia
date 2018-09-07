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

import com.constants.MessageConstants;
import com.constants.WebConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class AjaxControllerForEdition
 */
public class AjaxControllerForEdition extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	private Statement stmt;
	private HttpServletRequest request;
	int regType;
	public AjaxControllerForEdition() {
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
				editCity();
				break;
			case WebConstants.Machinary:
				editMachinary();
				break;
			case WebConstants.ManPower:
				editManPower();
				break;
			case WebConstants.Profession:
				editProfession();
				break;
			case WebConstants.Qualification:
				editQualification();
				break;
			case WebConstants.QueryFeedback:
				editQueryFeedback();
				break;
			case WebConstants.Region:
				editRegion();
				break;
			case WebConstants.Specialization:
				editSpecialization();
				break;
			case WebConstants.State:
				editState();
				break;
			case WebConstants.UserRegion:
				editUserRegion();
				break;
			}		

		}catch(Exception e)
		{
			mWriter.println(e.getLocalizedMessage());
		}

	}


	private void editMachinary()
	{
		String Machine=request.getParameter("Machine");
		String MachineId=request.getParameter("MachineId");
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(request.getParameter("deleteStatus").equals("true")){
			mCon=mDbUtils.generateConnection();	
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try
			{
				String deleteSQL = "DELETE FROM master_machineries WHERE Machineries_Id=?";
				PreparedStatement preparedStatement = mCon.prepareStatement(deleteSQL);
				preparedStatement.setInt(1, Integer.parseInt(MachineId));
				preparedStatement.executeUpdate();
				mWriter.println(Machine+" Deleted Successfully");

			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}
		}
		else{
			if(Machine.trim().length()>0)
			{
				mCon=mDbUtils.generateConnection();	
				if(mCon==null)
				{
					mWriter.println("Database problem occur");
					return;
				}
				try {
					String region="UPDATE  master_machineries SET Machineries= ?,Is_Active = ? where Machineries_Id=?" ;
					PreparedStatement preparedStmt = mCon.prepareStatement(region);
					preparedStmt.setString   (1, Machine);
					preparedStmt.setInt(2, isActive);
					preparedStmt.setInt (3, Integer.parseInt(MachineId));
					preparedStmt.executeUpdate();

					mWriter.println(Machine+" Updated succefully");	
				} catch (Exception e) {
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
				mWriter.println("Please enter the Machinary");	
			}
		}
	}
	private void editManPower()
	{
		String Manpower=request.getParameter("Manpower");
		String ManpowerId=request.getParameter("ManpowerId");
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(request.getParameter("deleteStatus").equals("true")){
			mCon=mDbUtils.generateConnection();	
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try
			{
				String deleteSQL = "DELETE FROM master_manpower WHERE ManpowerId=?";
				PreparedStatement preparedStatement = mCon.prepareStatement(deleteSQL);
				preparedStatement.setInt(1, Integer.parseInt(ManpowerId));
				preparedStatement.executeUpdate();
				mWriter.println(Manpower+" Deleted Successfully");

			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}
		}
		else{
			if(Manpower.trim().length()>0)
			{
				mCon=mDbUtils.generateConnection();	
				if(mCon==null)
				{
					mWriter.println("Database problem occur");
					return;
				}
				try {
					String region="UPDATE  master_manpower SET Manpower= ?,Is_Active = ? where ManpowerId=?" ;
					PreparedStatement preparedStmt = mCon.prepareStatement(region);
					preparedStmt.setString   (1, Manpower);
					preparedStmt.setInt(2, isActive);
					preparedStmt.setInt (3, Integer.parseInt(ManpowerId));
					preparedStmt.executeUpdate();

					mWriter.println(Manpower+" Updated succefully");	
				} catch (Exception e) {
					mWriter.println(e.getLocalizedMessage());
				}

			}
			else
			{
				mWriter.println("Please enter the Manpower");	
			}
		}
	}
	private void editProfession()
	{
		String Profession=request.getParameter("Profession");
		String ProfessionId=request.getParameter("ProfessionId");
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(request.getParameter("deleteStatus").equals("true")){
			mCon=mDbUtils.generateConnection();	
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try
			{
				String deleteSQL = "DELETE FROM master_profession WHERE profession_Id=?";
				PreparedStatement preparedStatement = mCon.prepareStatement(deleteSQL);
				preparedStatement.setInt(1, Integer.parseInt(ProfessionId));
				preparedStatement.executeUpdate();
				mWriter.println(Profession+" Deleted Successfully");

			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}
		}
		else{
			if(Profession.trim().length()>0)
			{
				mCon=mDbUtils.generateConnection();	
				if(mCon==null)
				{
					mWriter.println("Database problem occur");
					return;
				}
				try {
					String region="UPDATE  master_profession SET Profession= ?,is_Active = ? where profession_Id=?" ;
					PreparedStatement preparedStmt = mCon.prepareStatement(region);
					preparedStmt.setString   (1, Profession);
					preparedStmt.setInt(2, isActive);
					preparedStmt.setInt (3, Integer.parseInt(ProfessionId));
					preparedStmt.executeUpdate();

					mWriter.println(Profession+" Updated succefully");	
				} catch (Exception e) {
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
				mWriter.println("Please enter the Profession");	
			}
		}
	}


	private void editRegion()
	{
		String Region=request.getParameter("Region");
		String RegionId=request.getParameter("RegionId");
		String Contact=request.getParameter("Conatct");
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(request.getParameter("deleteStatus").equals("true")){
			mCon=mDbUtils.generateConnection();	
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try
			{
				String deleteSQL = "DELETE FROM master_stateregion WHERE id=?";
				PreparedStatement preparedStatement = mCon.prepareStatement(deleteSQL);
				preparedStatement.setInt(1, Integer.parseInt(RegionId));
				preparedStatement.executeUpdate();
				mWriter.println(Region+" Deleted Successfully");

			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}
		}
		else{
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
					try{
						long nu=Long.parseLong(Contact);
						try
						{
							String region="UPDATE master_stateregion SET Region= ?, ContactNo = ?, IsActive = ? where id=?" ;
							PreparedStatement preparedStmt = mCon.prepareStatement(region);
							preparedStmt.setString   (1, Region);
							preparedStmt.setString(2, Contact);
							preparedStmt.setInt (3, isActive);
							preparedStmt.setInt(4, Integer.parseInt(RegionId));
							preparedStmt.executeUpdate();
							mWriter.println(Region+" Updated Successfully");

						}catch(Exception e){
							mWriter.println(e.getLocalizedMessage());
						}finally {
							try {
								DBConnectionProvider.close(mCon);
							} catch (SQLException e) {
							}

						}
					}
					catch(Exception e){
						mWriter.println("You have entered Wrong Contact Number");
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

	}


	private void editState()
	{
		String State=request.getParameter("StateName");
		String Region=request.getParameter("RegionName");
		String StateId=request.getParameter("StateNameId");
		int isActive=0;
		String RegionId=null;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(request.getParameter("deleteStatus").equals("true")){
			mCon=mDbUtils.generateConnection();	
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try
			{
				String deleteSQL = "DELETE FROM master_state WHERE State_Id=?";
				PreparedStatement preparedStatement = mCon.prepareStatement(deleteSQL);
				preparedStatement.setInt(1, Integer.parseInt(StateId));
				preparedStatement.executeUpdate();
				mWriter.println(State+" Deleted Successfully");

			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}
		}
		else{
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
					stmt=mCon.createStatement();
					String qr="Select id from master_stateregion where Region='"+Region+"'" ;
					ResultSet rs = stmt.executeQuery(qr);
					if(rs.next())
					{
						RegionId=rs.getString("id");
					}
				}catch(Exception e){
					mWriter.println(e.getLocalizedMessage());
				}

				try
				{
					String region="UPDATE master_state SET StateName= ?, Region_Id = ?, Is_Active = ? where State_Id=?" ;
					PreparedStatement preparedStmt = mCon.prepareStatement(region);
					preparedStmt.setString   (1, State);
					preparedStmt.setInt(2, Integer.parseInt(RegionId));
					preparedStmt.setInt (3, isActive);
					preparedStmt.setInt(4, Integer.parseInt(StateId));
					preparedStmt.executeUpdate();
					mWriter.println(State +" Updated Successfully");

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
	}

	private void editCity()
	{


		String CityName=request.getParameter("CityName");
		String CityId=request.getParameter("CityId");
		String StateName=request.getParameter("StateName");
		String StateId=null;
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			mWriter.println("Database problem occur");
			return;
		}



		try
		{
			stmt=mCon.createStatement();
			String qr="Select State_Id from master_state where StateName='"+StateName+"'" ;
			ResultSet rs = stmt.executeQuery(qr);
			if(rs.next())
			{
				StateId=rs.getString("State_Id");

			}
			if(StateId!=null && StateId.trim().length()>0)
			{
				try
				{
					String region="UPDATE master_city SET StateId= ?, CityName = ?, Is_Active = ? where CityId=?" ;
					PreparedStatement preparedStmt = mCon.prepareStatement(region);
					preparedStmt.setInt   (1, Integer.parseInt(StateId));
					preparedStmt.setString(2, CityName);
					preparedStmt.setInt (3, isActive);
					preparedStmt.setInt(4, Integer.parseInt(CityId));
					preparedStmt.executeUpdate();
					mWriter.println(CityName +" Updated Successfully");

				}catch(Exception e){
					mWriter.println(e.getLocalizedMessage());
				}
			}
			else
			{
				mWriter.println("No State Found");
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

	private void editQualification()
	{
		String Qualification=request.getParameter("Qualification");
		String QualificationId=request.getParameter("QualificationId");
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(request.getParameter("deleteStatus").equals("true")){
			mCon=mDbUtils.generateConnection();	
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try
			{
				String deleteSQL = "DELETE FROM master_qualification WHERE Qualification_Id=?";
				PreparedStatement preparedStatement = mCon.prepareStatement(deleteSQL);
				preparedStatement.setInt(1, Integer.parseInt(QualificationId));
				preparedStatement.executeUpdate();
				mWriter.println(Qualification+" Deleted Successfully");

			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}
		}
		else{
			if(Qualification.trim().length()>0){
				mCon=mDbUtils.generateConnection();	
				if(mCon==null)
				{
					mWriter.println("Database problem occur");
					return;
				}
				try {
					String region="UPDATE  master_qualification SET Qualification= ?,Is_Active = ? where Qualification_Id=?" ;
					PreparedStatement preparedStmt = mCon.prepareStatement(region);
					preparedStmt.setString   (1, Qualification);
					preparedStmt.setInt(2, isActive);
					preparedStmt.setInt (3, Integer.parseInt(QualificationId));
					preparedStmt.executeUpdate();
		
					mWriter.println(Qualification+" Updated succefully");	
				} catch (Exception e) {
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
	}

	private void editUserRegion()
	{
		String UserRegionId=request.getParameter("UserRegionId");
		String UserRegion=request.getParameter("UserRegion");
		String state=request.getParameter("state");
		

		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(request.getParameter("deleteStatus").equals("true")){
			mCon=mDbUtils.generateConnection();	
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try
			{
				String deleteSQL = "DELETE FROM master_user_region WHERE Region_id=?";
				PreparedStatement preparedStatement = mCon.prepareStatement(deleteSQL);
				preparedStatement.setInt(1, Integer.parseInt(UserRegionId));
				preparedStatement.executeUpdate();
				mWriter.println(UserRegion+" Deleted Successfully");

			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}
		}
		else{
			if(UserRegion.trim().length()>0){
				mCon=mDbUtils.generateConnection();
				if(mCon==null)
				{
					mWriter.println("Database problem occur");
					return;
				}
				try {
					String region="UPDATE  master_user_region SET Region= ?,Is_Active = ?,StateId = ? where Region_id=?" ;
					PreparedStatement preparedStmt = mCon.prepareStatement(region);
					preparedStmt.setString   (1, UserRegion);
					preparedStmt.setInt(2, isActive);
					preparedStmt.setString(3, state);
					preparedStmt.setInt (4, Integer.parseInt(UserRegionId));
					preparedStmt.executeUpdate();
		
					mWriter.println(UserRegion+" Updated succefully");	
				} catch (Exception e) {
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
	}
	private void editSpecialization()
	{

		String Specialization=request.getParameter("Specialization");
		String SpecializationId=request.getParameter("SpecializationId");

		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(request.getParameter("deleteStatus").equals("true")){
			mCon=mDbUtils.generateConnection();	
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try
			{
				String deleteSQL = "DELETE FROM master_specialization WHERE Specialization_Id=?";
				PreparedStatement preparedStatement = mCon.prepareStatement(deleteSQL);
				preparedStatement.setInt(1, Integer.parseInt(SpecializationId));
				preparedStatement.executeUpdate();
				mWriter.println(Specialization+" Deleted Successfully");

			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}
		}
		else{
			if(Specialization.trim().length()>0){
				mCon=mDbUtils.generateConnection();	
				if(mCon==null)
				{
					mWriter.println("Database problem occur");
					return;
				}
				try {
					String region="UPDATE  master_specialization SET Specialization= ?,Is_Active = ? where Specialization_Id=?" ;
					PreparedStatement preparedStmt = mCon.prepareStatement(region);
					preparedStmt.setString   (1, Specialization);
					preparedStmt.setInt(2, isActive);
					preparedStmt.setInt (3, Integer.parseInt(SpecializationId));
					preparedStmt.executeUpdate();
		
					mWriter.println(Specialization+" Updated succefully");	
				} catch (Exception e) {
					mWriter.println(e.getLocalizedMessage());
				}	finally {
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
	}


	private void editQueryFeedback()
	{
		String QueryType=null;
		String FeebckAndQueryType=request.getParameter("QTYPE");
		String QId=request.getParameter("QueryFeedbackId");
		
		if(request.getParameter("deleteStatus").equals("true")){
			mCon=mDbUtils.generateConnection();	
			if(mCon==null)
			{
				mWriter.println("Database problem occur");
				return;
			}
			try
			{
				String deleteSQL = "DELETE FROM master_queryfeedback WHERE id=?";
				PreparedStatement preparedStatement = mCon.prepareStatement(deleteSQL);
				preparedStatement.setInt(1, Integer.parseInt(QId));
				preparedStatement.executeUpdate();
				mWriter.println(FeebckAndQueryType+" Deleted Successfully");

			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}
		}
		else{
			if(FeebckAndQueryType.equals("Query"))
			{
				QueryType="Q";	
			}
			else if(FeebckAndQueryType.equals("Feedback"))
			{
				QueryType="F";
			}
			String FeebckAndQuery=request.getParameter("QueryFeedback");
			int isActive=0;
			if(request.getParameter("IsActive").equals("true"))
			{
				isActive=Integer.parseInt("1");	
			}
			if(FeebckAndQuery.trim().length()>0 )
			{
				/*try
				{
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
				}*/
				mCon=mDbUtils.generateConnection();	
				if(mCon==null)
				{
					mWriter.println("Database problem occur");
					return;
				}
				try {
					String region="UPDATE  master_queryfeedback SET Query= ?,Type=?,IsActive = ? where id=?" ;
					PreparedStatement preparedStmt = mCon.prepareStatement(region);
					preparedStmt.setString   (1, FeebckAndQuery);
					preparedStmt.setString(2, QueryType);
					preparedStmt.setInt(3, isActive);
					preparedStmt.setInt (4, Integer.parseInt(QId));
					preparedStmt.executeUpdate();
					mWriter.println(FeebckAndQuery+" Updated succefully");	
				} catch (Exception e) {
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

}
