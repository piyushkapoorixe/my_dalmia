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
import com.model.MachineryModelList;
import com.model.ManPowerModelList;
import com.model.ProjectImageModel;
import com.responsemodel.CommonResponseModel;
import com.utils.GeneralRequestFormat;
import com.utils.Invoker;
import com.utils.ResponseUtil;

import sun.misc.BASE64Decoder;
/**
 * Servlet implementation class SaveUserProject
 */
public class SaveUserProject extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	private String UserId;
	private String UserName;
	private String ProjectName;
	private String Location;
	private String Area;
	private String Value;
	private ProjectImageModel ImagePathArray[]=null;
	private int ProjectId=0;
	private String CityId=null;
	public SaveUserProject() {
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

					addProject();
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
	void addProject() 
	{

		JSONArray arr=null;
		UserId=requestParam.optString("UserId");
		ProjectName=requestParam.optString("ProjectName");
		Location=requestParam.optString("Location");
		Value=requestParam.optString("Value");
		UserName=requestParam.optString("UserName");
		Area=requestParam.optString("Area");
		CityId=requestParam.optString("CityId");
		try {
			arr=new JSONArray(requestParam.optString("ImageData"));
			decodeString(arr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}



	public  void decodeString(JSONArray args) {
		ImagePathArray=new ProjectImageModel[args.length()];
		Exception writeImageException=null;
		for(int i=0;i<args.length();i++)
		{
			try {
				String ImageFolder=FolderAndDirectoryConstants.UsersProjectImages+"/U_"+UserId+UserName.replace(' ','_')+"/"+args.getJSONObject(i).getString("Name").replace(' ','_')+".png";
				String ProjectImagePath=FolderAndDirectoryConstants.ImagesDirectoryPath+ImageFolder;
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
				try{
					ProjectImageModel model=new ProjectImageModel();
					BufferedImage image = null;
					byte[] imageByte;
					BASE64Decoder decoder = new BASE64Decoder();
					imageByte = decoder.decodeBuffer(args.getJSONObject(i).getString("Image"));
					ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
					image = ImageIO.read(bis);
					bis.close();
					ImageIO.write(image, "png", file);
					model.setImage(FolderAndDirectoryConstants.ImagesBaseUrl+ImageFolder);
					model.setImageName(args.getJSONObject(i).getString("Name"));
					ImagePathArray[i]=model;
				}
				catch(Exception e)
				{
					writeImageException=e;
					e.printStackTrace();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		if(writeImageException!=null)
		{
			removeCurrentImages();
			CommonResponseModel model=new CommonResponseModel();
			model.setMessage(writeImageException.getLocalizedMessage());
			model.setStatus(MessageConstants.StatusFail);
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
			PreparedStatement preparedStmt=null;
			String insertQuery = "insert into users_project (U_Id,ProjectName,Price,Location,Area,City_Id)"+ " values (?, ?, ?,?,?,?)";
			preparedStmt = mCon.prepareStatement(insertQuery,Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setString (1, UserId);
			preparedStmt.setString (2, ProjectName);
			preparedStmt.setString (3, Value);
			preparedStmt.setString (4, Location);
			preparedStmt.setString (5, Area);
			preparedStmt.setString (6, CityId);
			preparedStmt.execute();
			ResultSet generatedKeys = preparedStmt.getGeneratedKeys();
			if(generatedKeys.next())
			{
				ProjectId=generatedKeys.getInt(1);
			}
			try {
				if(ProjectId!=0)
				{
					String insertQuery2 = "insert into users_project_image (Project_Id,Image_Path,Image_Name)"+ " values (?, ?, ?)";
					for(int i=0;i<ImagePathArray.length;i++)
					{
						ProjectImageModel modelImage=ImagePathArray[i];
						PreparedStatement preparedStmt2=null;
						preparedStmt2 = mCon.prepareStatement(insertQuery2);
						preparedStmt2.setInt (1, ProjectId);
						preparedStmt2.setString (2, modelImage.getImage());
						preparedStmt2.setString (3, modelImage.getImageName());
						preparedStmt2.execute();	
					}
					updateManpower();
					CommonResponseModel model=new CommonResponseModel();
					model.setMessage("Project Upload Successfully");
					model.setStatus(MessageConstants.StatusSuccess);
					ResponseUtil.setResponse(mWriter, model);
				}
				
				else
				{
					ResponseUtil.seterror(mWriter, "Insertin Error", MessageConstants.StatusFail);	
				}
			} catch (Exception e) {
				ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
			}
		} catch (SQLException e) {
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}
		}
	}
	void removeCurrentImages()
	{
		for(int i=0;i<ImagePathArray.length;i++)
		{
			try{
				File file = new File(ImagePathArray[i].getImageName());
				file.delete();
			}catch(Exception e){
				e.printStackTrace();
			}	
		}
		ImagePathArray=null;
	}
	void updateMachinary() 
	{
		JSONArray UsersMachineriesDetails=null;
		try{
			UsersMachineriesDetails=requestParam.getJSONArray("UsersMachineriesDetails");
			for(int i=0;i<UsersMachineriesDetails.length();i++)
			{
				String query = "insert into project_machineries (ProjectId,ManchineriesId,NoOfMachineries,CreatedDatet)"+ " values (?, ?, ?, ?)";
				PreparedStatement preparedStmt = mCon.prepareStatement(query);
				preparedStmt.setInt (1, ProjectId);
				preparedStmt.setString (2, UsersMachineriesDetails.getJSONObject(i).optString("MachineriesId"));
				preparedStmt.setString (3, UsersMachineriesDetails.getJSONObject(i).optString("NoOfMachineries"));
				preparedStmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
				preparedStmt.execute();	
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	void updateManpower() 
	{
		JSONArray UsersManpowerDetails=null;
		try{
			UsersManpowerDetails=requestParam.getJSONArray("UsersManpowerDetails");
			for(int i=0;i<UsersManpowerDetails.length();i++)
			{
				String query = "insert into project_manpower (ProjectId,ManpowerId,NoOfManpower,CreatedDate)"+ " values (?, ?, ?, ?)";
				PreparedStatement preparedStmt = mCon.prepareStatement(query);
				preparedStmt.setInt (1, ProjectId);
				preparedStmt.setString (2, UsersManpowerDetails.getJSONObject(i).optString("ManpowerId"));
				preparedStmt.setString (3, UsersManpowerDetails.getJSONObject(i).optString("NoOfManpower"));
				preparedStmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
				preparedStmt.execute();	
			}
			
		}catch(Exception e)
		{
		}
		updateMachinary();


	}
}
