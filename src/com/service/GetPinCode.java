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
import com.model.CiityModelList;
import com.model.CityModel;
import com.model.PinModel;
import com.model.PinModelLIst;
import com.utils.GeneralRequestFormat;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class GetPinCode
 */
public class GetPinCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();

	public GetPinCode() {
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
					getCityList();
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

	void getCityList() 
	{
		String cityId=null;
		try {
			cityId=requestParam.getString("CityId");
		} catch (Exception e) {

		}
		if(cityId!=null)
		{
			mCon=mDbUtils.generateConnection();	
			if(mCon==null)
			{
				ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
				return;
			}
			try {
				Statement stmt = mCon.createStatement();
				String sql="SELECT * from master_pin_code where isactive='1' AND CItyId='"+cityId+"'" ;
				ResultSet rs = stmt.executeQuery(sql);
				PinModelLIst model=new PinModelLIst();
				ArrayList<PinModel> list=new ArrayList<>();
				while (rs.next()) {
					PinModel item=new PinModel();
					item.setId(rs.getString("Id"));
					item.setPinCode(rs.getString("Pin"));
					list.add(item);
				}
				if(list.size()>0)
				{
					model.setMessage("Success");
					model.setStatus(MessageConstants.StatusSuccess);
					model.setListPin(list);
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
		else
		{
			ResponseUtil.seterror(mWriter, "No City id", MessageConstants.StatusFail);
		}


	}
}
