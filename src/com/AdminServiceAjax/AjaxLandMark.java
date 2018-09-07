package com.AdminServiceAjax;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.constants.FolderAndDirectoryConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;

import sun.misc.BASE64Decoder;

/**
 * Servlet implementation class AjaxLandMark
 */
public class AjaxLandMark extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private Statement stmt;
	private String Tag=null;
	private String Name=null;
	private String RegionId=null;
	private String Place=null;
	private String Description=null;
	private String FilePath=null;
	private String delId=null;
	private String editId=null;
	private String landMarkFile=null;
	private String base64ImageData=null;
	private String OldImg = null;
	String fromFileName=null;
	public AjaxLandMark() {
		super();
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		mWriter=response.getWriter();
		Tag=req.getParameter("Tag");
		if(Tag.equals("AddNew"))
		{

			// requested from AddNewLandmark.jsp  page to Add  a  new row 
			if(validateAllFiels(req, response))
				AddNewLandmark(req, response);	
		}
		else if(Tag.equals("Del"))
		{
			delId=req.getParameter("Id");
			deleteLandMark(req, response);
		}
		else if(Tag.equals("Edit"))
		{

			editId=req.getParameter("idEdit");
			if(validateAllFiels(req, response))
				editLandMark(req, response);
		}

	}
	void deleteLandMark(HttpServletRequest req, HttpServletResponse response)
	{
		try{
			mWriter=response.getWriter();
			mDbUtils=new DBUtils();
			mCon=mDbUtils.generateConnection();		
			if(mCon!=null)
			{
				stmt = mCon.createStatement();
				try {
					String sql="Delete from dalmia_land_mark_project where Id = ?" ;
					PreparedStatement preparedStmt = mCon.prepareStatement(sql);
					preparedStmt.setString(1, delId);
					preparedStmt.executeUpdate();
					mWriter.println("Deleted  succefully");	
				} catch (Exception e) {
					mWriter.println(e.getLocalizedMessage());
				}
			}else
			{
				mWriter.println("Database Problem Ocur");
			}
		}catch(Exception e)
		{
			mWriter.println(e.getLocalizedMessage());
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}

	}

	void AddNewLandmark(HttpServletRequest req, HttpServletResponse response)
	{

		createMFile();
		try{
			mWriter=response.getWriter();
			mDbUtils=new DBUtils();
			mCon=mDbUtils.generateConnection();		
			if(mCon!=null)
			{
				stmt = mCon.createStatement();
				try {
					String query = "insert into dalmia_land_mark_project (P_Name,P_Place,P_Image,P_Desc,Region_Id)"+ " values (?,?,?,?,?)";
					PreparedStatement preparedStmt = mCon.prepareStatement(query);
					preparedStmt.setString (1, Name);
					preparedStmt.setString (2, Place);
					preparedStmt.setString (3, FolderAndDirectoryConstants.ImagesBaseUrl+landMarkFile);
					preparedStmt.setString (4, Description);
					preparedStmt.setString (5, RegionId);
					preparedStmt.execute();
					mWriter.println("Record Added Successfully");
				} catch (Exception e) {
					mWriter.println(e.getLocalizedMessage());
				}
			}else
			{
				mWriter.println("Database Problem Ocur");
			}
		}catch(Exception e)
		{
			mWriter.println(e.getLocalizedMessage());
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}

	}

	void editLandMark(HttpServletRequest req, HttpServletResponse response)
	{
		try{
			mWriter=response.getWriter();
			mDbUtils=new DBUtils();
			mCon=mDbUtils.generateConnection();		
			if(mCon!=null)
			{
				stmt = mCon.createStatement();
				try {
					if(OldImg.matches("true") && FilePath.matches("")){
						String region="UPDATE  dalmia_land_mark_project SET P_Name= ?,P_Place = ?,P_Desc = ?,Region_Id = ? where Id=?" ;
						PreparedStatement preparedStmt = mCon.prepareStatement(region);
						preparedStmt.setString   (1, Name);
						preparedStmt.setString   (2, Place);
						preparedStmt.setString   (3, Description);
						preparedStmt.setString   (4, RegionId);
						preparedStmt.setString   (5, editId);
						preparedStmt.executeUpdate();
						mWriter.println("Updated succefully");
					}
					else{
						createMFile();
						String region="UPDATE  dalmia_land_mark_project SET P_Name= ?,P_Place = ?,P_Image = ?,P_Desc = ?,Region_Id = ? where Id=?" ;
						PreparedStatement preparedStmt = mCon.prepareStatement(region);
						preparedStmt.setString   (1, Name);
						preparedStmt.setString   (2, Place);
						preparedStmt.setString   (3, FolderAndDirectoryConstants.ImagesBaseUrl+landMarkFile);
						preparedStmt.setString   (4, Description);
						preparedStmt.setString   (5, RegionId);
						preparedStmt.setString   (6, editId);
						preparedStmt.executeUpdate();
						mWriter.println("Updated succefully");
					}	
				} catch (Exception e) {
					mWriter.println(e.getLocalizedMessage());
				}
			}else
			{
				mWriter.println("Database Problem Ocur");
			}
		}catch(Exception e)
		{
			mWriter.println(e.getLocalizedMessage());
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}

	}

	boolean validateAllFiels(HttpServletRequest req, HttpServletResponse response)
	{
		Name=req.getParameter("name");
		RegionId=req.getParameter("RegionId");
		Place=req.getParameter("place");
		Description=req.getParameter("desc");
		FilePath=req.getParameter("Path");
		OldImg=req.getParameter("isOldImgAvl");
		if(RegionId!=null && RegionId.trim().length()>0)
		{
			if(Name!=null && Name.trim().length()>0)
			{
				if(Place!=null && Place.trim().length()>0)
				{
					if(Description!=null && Description.trim().length()>0)
					{
						if(OldImg.matches("true") && FilePath.matches("")){
							base64ImageData=null;
							return true;
						}
						else{
							if(FilePath!=null && FilePath.trim().length()>0)
							{
								base64ImageData=null;
								base64ImageData=req.getParameter("Base64Data").replace(' ', '+');
								return true;

							}
							else{
								mWriter.println("Please select a image file");
								return false;
							}
						}
					}
					else{
						mWriter.println("Please enter the Description of Project");
						return false;
					}
				}
				else{
					mWriter.println("Please enter the place of Project");
					return false;
				}
			}
			else{
				mWriter.println("Please enter the name of project");
				return false;
			}	
		}
		else{
			mWriter.println("Please Select the Region");
			return false;
		}

	}
	void createMFile()
	{
		fromFileName=FilePath.substring(FilePath.lastIndexOf("\\")+1).replace(' ','_');
		landMarkFile=FolderAndDirectoryConstants.DalmiaLandMarkProject+"/"+"Region_"+RegionId+"/"+Name.replace(' ','_')+"_"+System.currentTimeMillis()+fromFileName;
		String videoPath=FolderAndDirectoryConstants.ImagesDirectoryPath+landMarkFile;
		File file = new File(videoPath);
		try {
			if(!file.getParentFile().exists())
			{
				try {
					file.mkdirs();
					
				} catch (Exception e) {
					mWriter.println(e.getLocalizedMessage());	
				}
			}
			try {
				file.createNewFile();
			} catch (Exception e) {
				mWriter.println(e.getLocalizedMessage());	
			}
			saveBanner(videoPath);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	void saveBanner(String file)
	{
		try
		{
			BufferedImage image = null;
			byte[] imageByte;
			BASE64Decoder decoder = new BASE64Decoder();
			imageByte = decoder.decodeBuffer(base64ImageData.split(",")[1]);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			bis.close();
			File outputfile = new File(file);
			ImageIO.write(image, "png", outputfile);
		}
		catch(Exception e)
		{
			new File(file).delete();
			mWriter.println(e.getLocalizedMessage());	
		}
	}
}
