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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.constants.FolderAndDirectoryConstants;
import com.constants.MessageConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.model.ProjectImageModel;
import com.responsemodel.CommonResponseModel;
import com.utils.GeneralRequestFormat;
import com.utils.ResponseUtil;

import sun.misc.BASE64Decoder;

/**
 * Servlet implementation class UploadProjectImage
 */
public class UploadProjectImage extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	private String UserId;
	private String UserName;
	private String ProjectId;
	private String ImageName;
	private String Extension;
	private String ImageData;
	private String ProjectImagePath; 
	ProjectImageModel model=null;

	public UploadProjectImage() {
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
					
					addImage();
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
	void addImage() 
	{
		UserId=requestParam.optString("UserId");
		UserName=requestParam.optString("UserName");
		ProjectId=requestParam.optString("ProjectId");
		ImageName=requestParam.optString("ImageName");
		Extension=requestParam.optString("Extension");
		ImageData=requestParam.optString("Data");
		decodeString();
	}
	public  void decodeString() {
		Exception writeImageException=null;
		String ImagesPath=FolderAndDirectoryConstants.UsersProjectImages+"/" +"U_"+UserId+UserName.replace(' ','_')+"/"+ImageName.replace(' ','_')+"."+Extension;
		ProjectImagePath=FolderAndDirectoryConstants.ImagesDirectoryPath+ImagesPath;
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
			model.setImage(FolderAndDirectoryConstants.ImagesBaseUrl+ImagesPath);
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
			String insertQuery2 = "insert into users_project_image (Project_Id,Image_Path,Image_Name)"+ " values (?, ?, ?)";
			PreparedStatement preparedStmt2=null;
			preparedStmt2 = mCon.prepareStatement(insertQuery2);
			preparedStmt2.setString (1, ProjectId);
			preparedStmt2.setString (2, model.getImage());
			preparedStmt2.setString (3, model.getImageName());
			preparedStmt2.execute();
			CommonResponseModel modelResponse=new CommonResponseModel();
			modelResponse.setMessage("Image Upload Successfully");
			modelResponse.setStatus(MessageConstants.StatusSuccess);
			ResponseUtil.setResponse(mWriter, modelResponse);
		} catch (SQLException e) {
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}
		}
	}
}



