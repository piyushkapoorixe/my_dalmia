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
import com.model.QualificationModel;
import com.model.QualificationModelList;
import com.utils.GeneralRequestFormat;
import com.utils.Invoker;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class GetQualification
 */
public class GetQualification extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	public GetQualification() {
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
					getQualificationList();
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

	void getQualificationList() 
	{
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		
			try {
				Statement stmt = mCon.createStatement();
				String sql="SELECT * from master_qualification where Is_Active='1'" ;
				ResultSet rs = stmt.executeQuery(sql);
				QualificationModelList model=new QualificationModelList();
				ArrayList<QualificationModel> list=new ArrayList<>();
				while (rs.next()) {
					QualificationModel item=new QualificationModel();
					item.setQualificationId(rs.getString("Qualification_Id"));
					item.setQualificationType(rs.getString("Qualification"));
					list.add(item);
				}
				if(list.size()>0)
				{
					model.setMessage("Success");
					model.setStatus(MessageConstants.StatusSuccess);
					model.setQualificationDetails(list);
					rs.close();
					ResponseUtil.setResponse(mWriter, model);
				}
				else
				{
					model.setMessage("No data found");
					model.setStatus(MessageConstants.StatusFail);
					rs.close();
					ResponseUtil.setResponse(mWriter, model);
				}

			} catch (Exception e) {
				ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
			}finally {
				try {
					DBConnectionProvider.close(mCon);
				} catch (SQLException e) {
				}

			}
		


	}
	

}
