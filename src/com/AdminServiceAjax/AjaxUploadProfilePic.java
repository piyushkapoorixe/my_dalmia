package com.AdminServiceAjax;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.constants.FolderAndDirectoryConstants;
import com.constants.MessageConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.utils.ResponseUtil;
import sun.misc.BASE64Decoder;

/**
 * Servlet implementation class AjaxUploadProfilePic
 */
public class AjaxUploadProfilePic extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	String fromPath=null;
	String fromFileName=null;
	private String UID=null;
	private String UName=null;
	private String base64ImageData=null; 
	private String profileImage=null;
	public AjaxUploadProfilePic() {
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
			fromPath=req.getParameter("Path");
			UID=req.getParameter("UID");
			UName=req.getParameter("UName");
			fromFileName=fromPath.substring(fromPath.lastIndexOf("\\")+1).replace(' ','_');
			if(fromPath.trim().length()>0)
			{
				base64ImageData=null;
				base64ImageData=req.getParameter("Base64Data").replace(' ', '+');
				createMFile();
			}
			else
			{
				mWriter.println("Please Select a file to upload");	
			}
		}
		catch(Exception e)
		{
			mWriter.println(e.getLocalizedMessage());
		}
	}

	void createMFile()
	{
		
		profileImage=FolderAndDirectoryConstants.UsersProfile+"/" +"U_"+UID+UName.replace(' ','_')+"/"+fromFileName;
		String videoPath=FolderAndDirectoryConstants.ImagesDirectoryPath+profileImage;
		File file = new File(videoPath);
		if(!file.getParentFile().exists())
		{
			try {
				file.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
				mWriter.println(e.getLocalizedMessage());	
			}
		}
		try {
			file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
			mWriter.println(e.getLocalizedMessage());	
		}
		try
		{
			BufferedImage image = null;
			byte[] imageByte;
			BASE64Decoder decoder = new BASE64Decoder();
			imageByte = decoder.decodeBuffer(base64ImageData.split(",")[1]);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			bis.close();
			ImageIO.write(image, "png", file);
			saveDetailInDatabase();
		}
		catch(Exception e)
		{
			file.delete();
			mWriter.println(e.getLocalizedMessage());	
		}
		
	}

	
	void saveDetailInDatabase()
	{
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		try {
			String query="UPDATE  users_details SET ProfileImage= ? where user_Id=?" ;
			PreparedStatement preparedStmt = mCon.prepareStatement(query);
			preparedStmt.setString (1, FolderAndDirectoryConstants.ImagesBaseUrl+profileImage);
			preparedStmt.setString (2, UID);
			preparedStmt.executeUpdate();
			mWriter.print("Profile Updated Successfully");
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
