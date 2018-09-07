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

import com.listener.DBConnectionProvider;

/**
 * Servlet implementation class AjaxEditOrAddPinCode
 */
public class AjaxEditOrAddPinCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpServletRequest mRequest;
	private PrintWriter mWriter;
	public AjaxEditOrAddPinCode() {
		super();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		mRequest=request;
		mWriter=response.getWriter();
		if(request.getParameter("Tag").equals("AddNew"))
		{
			addPin(request, response);	
		}
		else if(request.getParameter("Tag").equals("Edit"))
		{
			editPin(request, response);
		}

	}
	
	void addPin(HttpServletRequest request, HttpServletResponse response)
	{
		//String Name=request.getParameter("DistricId");
		int CityId=Integer.parseInt(request.getParameter("CityId"));
		int PinCode=Integer.parseInt(request.getParameter("PinCode"));
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		Connection con=null;
		try {
			con = DBConnectionProvider.getCon();
			String insertQuery="INSERT INTO master_pin_code(Pin,CItyId, isactive)" + " VALUES ('"+PinCode+"',"+CityId+","+isActive+")";
			PreparedStatement preparedStmt = con.prepareStatement(insertQuery);
			preparedStmt.execute();
			mWriter.println(PinCode+" Inserted succefully");	
		} catch (Exception e) {
			mWriter.println(e.getLocalizedMessage());
		}
		finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}

		}
	}

	void editPin(HttpServletRequest request, HttpServletResponse response)
	{
		//String Name=request.getParameter("DistricId");
		String CityId=request.getParameter("CityId");
		String PinCode=request.getParameter("PinCode");
		String PinId=request.getParameter("PinId");
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		if(request.getParameter("deleteStatus").equals("true")){
			try
			{
				Connection mCon=DBConnectionProvider.getCon();
				if(mCon==null)
				{
					mWriter.println("Database problem occur");
					return;
				}
				String deleteSQL = "DELETE FROM master_pin_code WHERE Id=?";
				PreparedStatement preparedStatement = mCon.prepareStatement(deleteSQL);
				preparedStatement.setInt(1, Integer.parseInt(PinId));
				preparedStatement.executeUpdate();
				mWriter.println(PinCode+" Deleted Successfully");

			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}
		}
		else{
			try {
				Connection mCon=DBConnectionProvider.getCon();
				String region="UPDATE master_pin_code SET Pin= ?,CItyId = ?,isactive = ? where Id=?" ;
				PreparedStatement preparedStmt = mCon.prepareStatement(region);
				preparedStmt.setString   (1, PinCode);
				preparedStmt.setString   (2, CityId);
				preparedStmt.setInt      (3, isActive);
				preparedStmt.setString   (4, PinId);
				preparedStmt.executeUpdate();
				mWriter.println("Updated succefully");	
			} catch (Exception e) {
				mWriter.println(e.getLocalizedMessage());
			}
		}

	}


}
