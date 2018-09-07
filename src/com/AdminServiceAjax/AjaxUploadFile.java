package com.AdminServiceAjax;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import com.db.DBUtils;
import com.listener.DBConnectionProvider;

/**
 * Servlet implementation class AjaxUploadFile
 */
public class AjaxUploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	String Title=null;
	String RegionId=null;
	String fromPath=null;
	String fromFileName=null;
	private File videoFile;
	public AjaxUploadFile() {
		super();
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain");
		mWriter=resp.getWriter();
		validateData(req, resp);
	}
	void validateData(HttpServletRequest req, HttpServletResponse response)
	{
		try{
			mWriter=response.getWriter();
			mDbUtils=new DBUtils();
			Title=req.getParameter("Title");
			RegionId=req.getParameter("RegionId");
			fromPath=req.getParameter("Path");
			fromFileName=fromPath.substring(fromPath.lastIndexOf("\\")+1);
			if(Title.trim().length()>0)
			{
				if(fromPath.trim().length()>0)
				{
					createMFile();
				}
				else
				{
					mWriter.println("Please Select a file to upload");	
				}
			}
			else
			{
				mWriter.println("Please enter the Title");	
			}
		}
		catch(Exception e)
		{
			mWriter.println(e.getLocalizedMessage());
		}
	}

	void createMFile()
	{
		String videoPath=getServletContext().getRealPath("").substring(0, getServletContext().getRealPath("").lastIndexOf("\\")+1)
				+FolderAndDirectoryConstants.DalmiaRegionalVideo+"/"+"Region_"+RegionId;
		File file = new File(videoPath);
		if(!file.exists())
		{
			try {
				file.mkdirs();
				createNewFile(file);	
			} catch (Exception e) {
				mWriter.println(e.getLocalizedMessage());	
			}
		}
		else
		{
			createNewFile(file);	
		}
	}
	void createNewFile(File file)
	{
		videoFile=new File(file,Title+"_"+System.currentTimeMillis()+fromFileName);
		try {
			videoFile.createNewFile();
			saveVideo();
		} catch (Exception e) {
			mWriter.println(e.getLocalizedMessage());	
		}
	}
	void saveVideo()
	{
		try
		{
			File file = new File(fromPath);
			FileInputStream fin = new FileInputStream(file);
			byte b[] = new byte[(int)file.length()];
			fin.read(b);
			File nf = new File(videoFile.toString());
			FileOutputStream fw = new FileOutputStream(nf);
			fw.write(b);
			fw.flush();
			fw.close();
			writeDb();
		}
		catch(Exception e)
		{
			videoFile.delete();
			mWriter.println(e.getLocalizedMessage());	
		}
	}
	void writeDb()
	{
		mCon=mDbUtils.generateConnection();
		try {
			PreparedStatement preparedStmt=null;
			String insertQuery = "insert into master_tvc_videos (Title,Video_Path,Region_Id)"+ " values (?, ?, ?)";
			preparedStmt = mCon.prepareStatement(insertQuery);
			preparedStmt.setString (1, Title);
			preparedStmt.setString (2, videoFile.toString());
			preparedStmt.setString (3, RegionId);
			preparedStmt.execute();
			mWriter.println("File Uploaded Successfully");
		} catch (Exception e) {
			mWriter.println(e.getLocalizedMessage());	
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}
	}
}
