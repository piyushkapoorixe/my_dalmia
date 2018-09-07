package com.AdminServiceAjax;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.constants.FolderAndDirectoryConstants;
import com.listener.DBConnectionProvider;

/**
 * Servlet implementation class AjaxEditVideo
 */
public class AjaxEditVideo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpServletRequest mRequest;
	private PrintWriter mWriter;
	public AjaxEditVideo() {
		super();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		mRequest=request;
		mWriter=response.getWriter();
		if(request.getParameter("Tag").equals("AddNew"))
		{
			addVideo(request, response);	
		}
		else if(request.getParameter("Tag").equals("Edit"))
		{
			editVideo(request, response);
		}
		else if(request.getParameter("Tag").equals("Del"))
		{
			delVideo(request, response);
		}

	}
	
	void addVideo(HttpServletRequest request, HttpServletResponse response)
	{
		String Title=request.getParameter("Title");
		String RegionId=request.getParameter("RegionId");
		String url=request.getParameter("Path");
		//String Path=FolderAndDirectoryConstants.ImagesBaseUrl+FolderAndDirectoryConstants.DalmiaRegionalVideo+"/"+url;
			Connection con=null;
			try {
				con = DBConnectionProvider.getCon();
				String insertQuery="INSERT INTO master_tvc_videos(Title,Video_Path,Region_Id)" + " VALUES ('"+Title+"','"+url+"','"+RegionId+"')";
				PreparedStatement preparedStmt = con.prepareStatement(insertQuery);
				preparedStmt.execute();
				mWriter.println("Video Added succefully");	
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

	void editVideo(HttpServletRequest request, HttpServletResponse response)
	{
		String vId=request.getParameter("id");
		String Title=request.getParameter("Title");
		String RegionId=request.getParameter("RegionId");
		String url=request.getParameter("Path");
		//String Path=FolderAndDirectoryConstants.ImagesBaseUrl+FolderAndDirectoryConstants.DalmiaRegionalVideo+"/"+url;
			Connection con=null;
			try {
				con = DBConnectionProvider.getCon();
				String region="UPDATE  master_tvc_videos SET Title= ?,Region_Id= ?,Video_Path= ? where Id=?" ;
				PreparedStatement preparedStmt = con.prepareStatement(region);
				preparedStmt.setString   (1, Title);
				preparedStmt.setString   (2, RegionId);
				preparedStmt.setString   (3, url);
				preparedStmt.setString   (4, vId);
				preparedStmt.executeUpdate();
				mWriter.println("Updated succefully");	
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
	
	void delVideo(HttpServletRequest request, HttpServletResponse response)
	{
		String id=request.getParameter("Id");
		
		try {
			Connection mCon=DBConnectionProvider.getCon();
			String sql="Delete from master_tvc_videos where Id = ?" ;
			PreparedStatement preparedStmt = mCon.prepareStatement(sql);
			preparedStmt.setString(1, id);
			preparedStmt.executeUpdate();
			mWriter.println("Deleted  succefully");	
		} catch (Exception e) {
			mWriter.println(e.getLocalizedMessage());
		}

	}
}
