package com.AdminServiceAjax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.sun.org.apache.regexp.internal.recompile;

/**
 * Servlet implementation class AjaxAdminController
 */
public class AjaxAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private Statement stmt;
	String EditAdminId=null;
	private String AdminId=null;
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	public AjaxAdminController() {
		super();
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain");
		mWriter=resp.getWriter();
		validateData(req, resp);
		
	}
	void validateData(HttpServletRequest req, HttpServletResponse response)
	{
		String RegionId=req.getParameter("Region");
		AdminId=req.getParameter("AdminId");
		String UID=req.getParameter("UID");
		String NAME=req.getParameter("NAME");
		String EMAIL=req.getParameter("EMAIL");
		String MOBILE=req.getParameter("MOBILE");
		String State=req.getParameter("State");
		String Distric=req.getParameter("Distric");
		String subRegion=req.getParameter("subRegion");
		String Role=req.getParameter("role");
		String Tag=req.getParameter("Tag");
		mDbUtils=new DBUtils();
		if(!checkMobileAndEmail(EMAIL, MOBILE,UID))
			{
			try {
				mCon = DBConnectionProvider.getCon();
				if(Tag.equals("Edit"))
				{
					if(validate(EMAIL))
					{
						if(Role.equals("1"))
						{
							
							updateDataAdmin( UID, NAME, EMAIL, MOBILE,Role);
						}else if(Role.equals("2"))
						{
							updateDataTSO(UID, NAME, EMAIL, MOBILE,State,Distric,Role);
						}else if(Role.equals("3"))
						{
							updateDataRh(UID, NAME, EMAIL, MOBILE,State,subRegion,Role);
						}
						else if(Role.equals("4"))
						{
							updateDataTh(RegionId, UID, NAME, EMAIL, MOBILE,Role);	
						}
					}
				}else if(Tag.equals("AddNew")) 
				{
					if(validate(EMAIL))
					{
						if(!checkUID(UID))
						{
								if(Role.equals("1"))
								{
									insertDataAdmin(UID, NAME, EMAIL, MOBILE,Role);		
								}else if(Role.equals("2"))
								{
									insertDataTSO(UID, NAME, EMAIL, MOBILE,State,Distric,Role);
								}else if(Role.equals("3"))
								{
									insertDataRh(UID, NAME, EMAIL, MOBILE,State,subRegion,Role);
								}else if(Role.equals("4"))
								{
									insertDataTh(RegionId, UID, NAME, EMAIL, MOBILE,Role);
								}
						}
					}		
				}
			} catch (Exception e) {
				mWriter.println(e.getLocalizedMessage());
			}
		}
	}
	void insertDataTh(String RegionId,String UID,String NAME,String EMAIL,String MOBILE,String Role)
	{
		mCon=mDbUtils.generateConnection();
		try {
			stmt = mCon.createStatement();
			String query = "insert into tbl_adminlogin_details (UserName,LoginID,Email_ID,Mobile_No,RegionId,RoleType)"+ " values (?, ?, ?, ?, ?,?)";
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			preparedStmt.setString (1, NAME);
			preparedStmt.setString (2, UID);
			preparedStmt.setString (3, EMAIL);
			preparedStmt.setString (4, MOBILE);
			preparedStmt.setString(5, RegionId);
			preparedStmt.setString(6, Role);
			preparedStmt.execute();
			mWriter.println("Admin Created Successfully");
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
	
	void insertDataAdmin(String UID,String NAME,String EMAIL,String MOBILE,String Role)
	{
		mCon=mDbUtils.generateConnection();
		try {
			stmt = mCon.createStatement();
			String query = "insert into tbl_adminlogin_details (UserName,LoginID,Email_ID,Mobile_No,RoleType)"+ " values (?, ?, ?, ?,?)";
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			preparedStmt.setString (1, NAME);
			preparedStmt.setString (2, UID);
			preparedStmt.setString (3, EMAIL);
			preparedStmt.setString (4, MOBILE);
			preparedStmt.setString(5, Role);
			preparedStmt.execute();
			mWriter.println("Admin Created Successfully");
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
	void insertDataTSO(String UID,String NAME,String EMAIL,String MOBILE,String state,String distric,String Role)
	{
		mCon=mDbUtils.generateConnection();
		try {
			stmt = mCon.createStatement();
			String query = "insert into tbl_adminlogin_details (UserName,LoginID,Email_ID,Mobile_No,StateId,DistricId,RoleType)"+ " values (?, ?, ?, ?, ?,?,?)";
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			preparedStmt.setString (1, NAME);
			preparedStmt.setString (2, UID);
			preparedStmt.setString (3, EMAIL);
			preparedStmt.setString (4, MOBILE);
			preparedStmt.setString(5,state);
			preparedStmt.setString (6, distric);
			preparedStmt.setString(7,Role );
			preparedStmt.execute();
			mWriter.println("Admin Created Successfully");	
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
	void insertDataRh(String UID,String NAME,String EMAIL,String MOBILE,String State,String subRegion,String Role)
	{
		mCon=mDbUtils.generateConnection();
		try {
			stmt = mCon.createStatement();
			String query = "insert into tbl_adminlogin_details (UserName,LoginID,Email_ID,Mobile_No,StateId,SubRegion,RoleType)"+ " values (?, ?, ?, ?, ?,?,?)";
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			preparedStmt.setString (1, NAME);
			preparedStmt.setString (2, UID);
			preparedStmt.setString (3, EMAIL);
			preparedStmt.setString (4, MOBILE);
			preparedStmt.setString(5,State);
			preparedStmt.setString (6, subRegion);
			preparedStmt.setString(7,Role );
			preparedStmt.execute();
			mWriter.println("Admin Created Successfully");
		} catch (Exception e) {
			mWriter.println(e.getLocalizedMessage());	
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}
	}
	void updateDataTh(String RegionId,String UID,String NAME,String EMAIL,String MOBILE,String Role)
	{
		mCon=mDbUtils.generateConnection();
		try {
			stmt = mCon.createStatement();
			String query = "UPDATE  tbl_adminlogin_details SET UserName= ?,LoginID = ?,Email_ID=?,Mobile_No= ?,RegionId = ? where ID=?";
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			preparedStmt.setString (1, NAME);
			preparedStmt.setString (2, UID);
			preparedStmt.setString (3, EMAIL);
			preparedStmt.setString (4, MOBILE);
			preparedStmt.setString(5,RegionId );
			preparedStmt.setString(6,AdminId );
			preparedStmt.execute();
			mWriter.println("Admin Update Successfully");
		} catch (Exception e) {
			mWriter.println(e.getLocalizedMessage());	
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}
		}
	}
	
	void updateDataAdmin(String UID,String NAME,String EMAIL,String MOBILE,String Role)
	{
		mCon=mDbUtils.generateConnection();
		try {
			stmt = mCon.createStatement();
			String query = "UPDATE  tbl_adminlogin_details SET UserName= ?,LoginID = ?,Email_ID=?,Mobile_No= ? where ID=?";
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			preparedStmt.setString (1, NAME);
			preparedStmt.setString (2, UID);
			preparedStmt.setString (3, EMAIL);
			preparedStmt.setString (4, MOBILE);
			preparedStmt.setString(5,AdminId );
			preparedStmt.execute();
			mWriter.println("Admin Update Successfully");
		} catch (Exception e) {
			mWriter.println(e.getLocalizedMessage());	
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}
		}
	}
	void updateDataTSO(String UID,String NAME,String EMAIL,String MOBILE,String state,String distric,String Role)
	{
		mCon=mDbUtils.generateConnection();
		try {
			stmt = mCon.createStatement();
			String query = "UPDATE  tbl_adminlogin_details SET UserName= ?,LoginID = ?,Email_ID=?,Mobile_No= ?,StateId = ?,DistricId=? where ID=?";
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			preparedStmt.setString (1, NAME);
			preparedStmt.setString (2, UID);
			preparedStmt.setString (3, EMAIL);
			preparedStmt.setString (4, MOBILE);
			preparedStmt.setString(5,state);
			preparedStmt.setString (6, distric);
			preparedStmt.setString(7,AdminId );
			preparedStmt.execute();
			mWriter.println("Admin Update Successfully");
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
	void updateDataRh(String UID,String NAME,String EMAIL,String MOBILE,String State,String subRegion,String Role){
		mCon=mDbUtils.generateConnection();
		try {
			stmt = mCon.createStatement();
			String query = "UPDATE  tbl_adminlogin_details SET UserName= ?,LoginID = ?,Email_ID=?,Mobile_No= ?,StateId = ?,SubRegion=? where ID=?";
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			preparedStmt.setString (1, NAME);
			preparedStmt.setString (2, UID);
			preparedStmt.setString (3, EMAIL);
			preparedStmt.setString (4, MOBILE);
			preparedStmt.setString(5,State);
			preparedStmt.setString (6, subRegion);
			preparedStmt.setString(7,AdminId );
			preparedStmt.execute();
			mWriter.println("Admin Update Successfully");
		} catch (Exception e) {
			mWriter.println(e.getLocalizedMessage());	
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}
		}
	}

	boolean checkUID(String Id )
	{
		if(Id.trim().length()>0)
		{
			try {
				mCon=mDbUtils.generateConnection();
				stmt = mCon.createStatement();
				String sql="SELECT LoginID from tbl_adminlogin_details where LoginID='"+Id+"'" ;
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next())
				{
					mWriter.println(Id+" Already Exists");	
					return true;
				}
			} catch (Exception e) {
				mWriter.println(e.getLocalizedMessage());	
			}
			finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}
			}
		}else
		{
			mWriter.println(" Please enter user id");
			return true;
		}
		return false;
	}
	boolean checkEmail(String email)
	{
		if(validate(email))
		{
			try {
				mCon=mDbUtils.generateConnection();
				stmt = mCon.createStatement();
				String sql="SELECT Email_ID from tbl_adminlogin_details where Email_ID='"+email+"'" ;
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next())
				{
					mWriter.println(email+" Already Exists");	
					return true;
				}
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
			mWriter.println("Please Enter Valid Email Id");	
			return true;
		}
		return false;
	}
	boolean checkMobile(String mobile )
	{
		if(mobile.trim().length()!=10 )
		{
			mWriter.println("Please Enter Valid Mobile Number");	
			return true;
		}
		try {
			mCon=mDbUtils.generateConnection();
			stmt = mCon.createStatement();
			Long.parseLong(mobile);
			try {
				String sql="SELECT Mobile_No from tbl_adminlogin_details where Mobile_No='"+mobile+"'" ;
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next())
				{
					mWriter.println(mobile+" Already Exists");	
					return true;
				}
			} catch (Exception e) {
				mWriter.println(e.getLocalizedMessage());	
			}
		} catch (Exception e) {
			mWriter.println("Please Enter Valid Mobile Number");
			return true;
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}
		return false;
	}
	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		return matcher.find();
	}
	
	
	boolean checkMobileAndEmail(String Email,String Mob,String loginId)
	{
		mCon=mDbUtils.generateConnection();
		try {
			String emailCheck="Select email from tbl_adminlogin_details WHERE Email_ID='"+Email+"' And ID !='"+AdminId+"'" ;
			String mobileCheck="Select mob_no from tbl_adminlogin_details WHERE Mobile_No='"+Mob+"' And ID !='"+AdminId+"'" ;
			String loginIdCheck="Select mob_no from tbl_adminlogin_details WHERE LoginID='"+loginId+"' And ID !='"+AdminId+"'" ;
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
			
			ResultSet idSet = stmt.executeQuery(loginIdCheck);
			if(idSet.next())
			{
				mWriter.println(loginId+" Already Registered with us");
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
