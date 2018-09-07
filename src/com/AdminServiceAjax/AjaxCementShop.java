package com.AdminServiceAjax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.listener.DBConnectionProvider;

/**
 * Servlet implementation class AjaxCementShop
 */
public class AjaxCementShop extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpServletRequest mRequest;
	private PrintWriter mWriter;

	private String ShopId=null;
	private String DelearId=null;
	private String City=null;
	private String Pincode=null;
	private String District=null;
	private String State=null;
	private String Add=null;
	private String Add1=null;
	private String Add2=null;
	private String DelId=null;

	public AjaxCementShop() {
		super();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		mRequest=request;
		mWriter=response.getWriter();
		getParam(request);
		if(request.getParameter("Tag").equals("AddNew"))
		{
			addShop(request, response);	
		}
		else if(request.getParameter("Tag").equals("Edit"))
		{
			editShop(request, response);
		}
		else if(request.getParameter("Tag").equals("Del"))
		{
			delShop(request, response);
		}

	}

	void addShop(HttpServletRequest request, HttpServletResponse response)
	{
		Connection con=null;
		try {
			con = DBConnectionProvider.getCon();
			String insertQuery="INSERT INTO dalmia_cement_shop(DelearId,Address,Address1,Address2,PinCode,CityId,District_Id,StateId)" + 
			                                        " VALUES ('"+DelearId+"','"+Add+"','"+Add1+"','"+Add2+"','"+Pincode+"','"+City+"','"+District+"','"+State+"')";
			PreparedStatement preparedStmt = con.prepareStatement(insertQuery);
			preparedStmt.execute();
			mWriter.println(DelearId+" Inserted succefully");	
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

	void editShop(HttpServletRequest request, HttpServletResponse response)
	{

		try {
			Connection mCon=DBConnectionProvider.getCon();
			String region="UPDATE  dalmia_cement_shop SET DelearId= ?,Address= ?,Address1= ?,Address2= ?,PinCode= ?,CityId= ?,District_Id= ?,StateId= ? where Id=?" ;
			PreparedStatement preparedStmt = mCon.prepareStatement(region);
			preparedStmt.setString   (1, DelearId);
			preparedStmt.setString   (2, Add);
			preparedStmt.setString   (3, Add1);
			preparedStmt.setString   (4, Add2);
			preparedStmt.setString   (5, Pincode);
			preparedStmt.setString   (6, City);
			preparedStmt.setString   (7, District);
			preparedStmt.setString   (8, State);
			preparedStmt.setString   (9, ShopId);
			preparedStmt.executeUpdate();
			mWriter.println("Updated succefully");	
		} catch (Exception e) {
			mWriter.println(e.getLocalizedMessage());
		}

	}

	void getParam(HttpServletRequest req)
	{

		ShopId=req.getParameter("ShopId");
		DelearId=req.getParameter("DelearId");
		City=req.getParameter("CityId");
		Pincode=req.getParameter("PinCode");
		District=req.getParameter("DistricId");
		State=req.getParameter("StateId");
		Add=req.getParameter("Add");
		Add1=req.getParameter("Add1");
		Add2=req.getParameter("Add2");
		DelId=req.getParameter("DelId");
		
	}
	
	void delShop(HttpServletRequest request, HttpServletResponse response)
	{
		Connection con=null;
		try {
			con = DBConnectionProvider.getCon();
			String sql="Delete from dalmia_cement_shop where Id = ?" ;
			PreparedStatement preparedStmt = con.prepareStatement(sql);
			preparedStmt.setString(1, DelId);
			preparedStmt.executeUpdate();
			mWriter.println("Deleted  succefully");		
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
}
