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

import com.constants.FolderAndDirectoryConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;

/**
 * Servlet implementation class AjaxFilterDistric
 */
public class AjaxFilterUserRegion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpServletRequest mRequest;
	private PrintWriter mWriter;
	public AjaxFilterUserRegion() {
		super();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		mRequest=request;
		mWriter=response.getWriter();
		if(request.getParameter("Tag")!=null && request.getParameter("Tag").trim().length()>0)
		{
			if(request.getParameter("Tag").equals("AddNew"))
			{
				addDistric(request, response);	
			}
			else if(request.getParameter("Tag").equals("Edit"))
			{
				editDistric(request, response);
			}


		}else
		{
			filterDistic(request,response);
		}

	}
	void filterDistic(HttpServletRequest request, HttpServletResponse response)
	{
		int StateId=Integer.parseInt(request.getParameter("StateId"));
		Connection con=null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql="SELECT * from master_user_region where Is_Active='1' AND StateId='"+StateId+"'" ;
			ResultSet rs = stmt.executeQuery(sql);
			StringBuilder html=new StringBuilder();
			while (rs.next()) {
				html.append("<option value="+rs.getString("Region_id")+">");
				html.append(rs.getString("Region"));
				html.append("</option>");
			}
			mWriter.println(html);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}

		}
	}
	void addDistric(HttpServletRequest request, HttpServletResponse response)
	{
		String Name=(String)request.getParameter("DisticName");
		int StateId=Integer.parseInt(request.getParameter("StateId"));
		int RegionId=Integer.parseInt(request.getParameter("RegionId"));
		int isActive=0;
		if(request.getParameter("IsActive").equals("true"))
		{
			isActive=Integer.parseInt("1");	
		}
		Connection con=null;
		try {
			con = DBConnectionProvider.getCon();
			String insertQuery="INSERT INTO master_distric(name,stateId, RegionId,isactive)" + " VALUES ('"+Name+"',"+StateId+","+RegionId+","+isActive+")";
			PreparedStatement preparedStmt = con.prepareStatement(insertQuery);
			preparedStmt.execute();
			mWriter.println(Name+" Inserted succefully");	
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

	void editDistric(HttpServletRequest request, HttpServletResponse response)
	{
		String Name=(String)request.getParameter("DisticName");
		String StateId=request.getParameter("StateId");
		String RegionId=request.getParameter("RegionId");
		String DistricId=request.getParameter("DistricId");
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
				String deleteSQL = "DELETE FROM master_distric WHERE id=?";
				PreparedStatement preparedStatement = mCon.prepareStatement(deleteSQL);
				preparedStatement.setInt(1, Integer.parseInt(DistricId));
				preparedStatement.executeUpdate();
				mWriter.println(Name+" Deleted Successfully");

			}catch(Exception e){
				mWriter.println(e.getLocalizedMessage());
			}
		}
		else{
			try {
				Connection mCon=DBConnectionProvider.getCon();
				String region="UPDATE  master_distric SET name= ?,stateId = ?,RegionId = ?,isactive = ? where id=?" ;
				PreparedStatement preparedStmt = mCon.prepareStatement(region);
				preparedStmt.setString   (1, Name);
				preparedStmt.setString   (2, StateId);
				preparedStmt.setString   (3, RegionId);
				preparedStmt.setInt      (4, isActive);
				preparedStmt.setString   (5, DistricId);
				preparedStmt.executeUpdate();
				mWriter.println("Updated succefully");	
			} catch (Exception e) {
				mWriter.println(e.getLocalizedMessage());
			}
		}
	}


}
