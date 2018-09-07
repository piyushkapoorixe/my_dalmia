package com.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.constants.MessageConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.model.TollFreeModel;
import com.model.TvcVideoList;
import com.model.TvcVideoModel;
import com.utils.GeneralRequestFormat;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class GetTvcVideos
 */
public class GetTvcVideos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	public GetTvcVideos() {
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
					getTollFree();
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
	

	void getTollFree() 
	{
		
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		try{
			String stateName=requestParam.optString("StateName");
			if(stateName!=null)
			{
				Statement stmt = mCon.createStatement();
				String sql="SELECT a.* FROM master_tvc_videos a LEFT join master_state b on a.Region_Id=b.Region_id where b.StateName='"+stateName+"' OR a.Region_Id=4";			
				ResultSet rs = stmt.executeQuery(sql);
				TvcVideoList list=new TvcVideoList();
				ArrayList<TvcVideoModel> tvcVideosDataList=new ArrayList<>();
				while(rs.next())
				{
					TvcVideoModel model=new TvcVideoModel();
					model.setVideoId(rs.getString("Id"));
					model.setRegionId(rs.getString("Region_Id"));
					model.setVideoPath(rs.getString("Video_Path"));
					model.setVideoTitle(rs.getString("Title"));
					tvcVideosDataList.add(model);
				}
				
				rs.close();
				if(tvcVideosDataList.size()>0)
				{
					list.setTvcVideos(tvcVideosDataList);
					list.setMessage("Success");
					list.setStatus(MessageConstants.StatusSuccess);
					ResponseUtil.setResponse(mWriter, list);
				}
				else
				{
					list.setMessage("No video found for this region");
					list.setStatus(MessageConstants.StatusFail);
					ResponseUtil.setResponse(mWriter, list);
				}
			}
			else
			{
				ResponseUtil.seterror(mWriter, "Invalid State Name", MessageConstants.StatusFail);
			}


		}catch(Exception e)
		{
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);	
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}
	}

	
}
