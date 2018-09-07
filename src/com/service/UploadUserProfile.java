package com.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.constants.FolderAndDirectoryConstants;
import com.constants.MessageConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.model.ProjectImageModel;
import com.model.UserProfileModel;
import com.responsemodel.CommonResponseModel;
import com.utils.GeneralRequestFormat;
import com.utils.ResponseUtil;
import sun.misc.BASE64Decoder;
/**
 * Servlet implementation class UploadUserProfile
 */
public class UploadUserProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	private String UserId;
	private String UserName;
	private String ImageName;
	private String Extension;
	private String ImageData;
	private String ProjectImagePath; 
	private ProjectImageModel model=null;
	public UploadUserProfile() {
		super();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initializeInitialRequirements(request, response);
	}
	void initializeInitialRequirements(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
	{
		mWriter=response.getWriter();
		requestParam=GeneralRequestFormat.getParam(request, response,URL);
		if(requestParam!=null)
		{
			try {
				if(requestParam.getString("TokenNo").equals(MessageConstants.TokenValues))
				{
					mDbUtils=new DBUtils();
					
					addProfileImage();
				}
				else
				{
					ResponseUtil.seterror(mWriter, "Invalid Authentication", MessageConstants.StatusFail);
				}

			} catch (Exception e) {
				ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
			}
		}
		else
		{
			ResponseUtil.seterror(mWriter, "Invalid Parameter", MessageConstants.StatusFail);
		}
	}
	void addProfileImage() 
	{
		UserId=requestParam.optString("UserId");
		UserName=requestParam.optString("UserName");
		ImageName=requestParam.optString("ImageName");
		Extension=requestParam.optString("Extension");
		ImageData=requestParam.optString("Data");
		decodeString();
	}
	public  void decodeString() {
		Exception writeImageException=null;
		
		String profileImage=FolderAndDirectoryConstants.UsersProfile+"/" +"U_"+UserId+UserName.replace(' ','_')+"/"+ImageName.replace(' ','_')+"."+Extension;
		ProjectImagePath=FolderAndDirectoryConstants.ImagesDirectoryPath+profileImage;
		File file = new File(ProjectImagePath);
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
		
				model=new ProjectImageModel();
				try{
					BufferedImage image = null;
					byte[] imageByte;
					BASE64Decoder decoder = new BASE64Decoder();
					imageByte = decoder.decodeBuffer(ImageData);
					ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
					image = ImageIO.read(bis);
					bis.close();
					ImageIO.write(image, Extension, file);
					model.setImage(FolderAndDirectoryConstants.ImagesBaseUrl+profileImage);
					model.setImageName(ImageName);
				}
				catch(Exception e)
				{
					writeImageException=e;
					e.printStackTrace();
				}
				if(writeImageException!=null)
				{
					try{
						File fileDel = new File(model.getImage());
						fileDel.delete();
					}catch(Exception e){
						e.printStackTrace();
					}
					CommonResponseModel modelResponse=new CommonResponseModel();
					modelResponse.setMessage(writeImageException.getLocalizedMessage());
					modelResponse.setStatus(MessageConstants.StatusFail);
					ResponseUtil.setResponse(mWriter, model);
				}
				else
				{
					saveDetailInDatabase();
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
			preparedStmt.setString (1, model.getImage());
			preparedStmt.setString (2, UserId);
			preparedStmt.executeUpdate();
			
			Statement stmt = mCon.createStatement();
			String imgQuery="SELECT ProfileImage from users_details where user_Id='"+UserId+"'";
			ResultSet rs = stmt.executeQuery(imgQuery);
			if(rs.next())
			{
				UserProfileModel model=new UserProfileModel();
				model.setImage(rs.getString("ProfileImage"));
				model.setMessage("Profile Upload Successfully");
				model.setStatus(MessageConstants.StatusSuccess);
				ResponseUtil.setResponse(mWriter, model);
			}
			/*
			CommonResponseModel modelResponse=new CommonResponseModel();
			modelResponse.setMessage("Profile Upload Successfully");
			modelResponse.setStatus(MessageConstants.StatusSuccess);
			ResponseUtil.setResponse(mWriter, modelResponse);*/
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
