package com.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.constants.MessageConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.utils.GeneralRequestFormat;
import com.utils.Invoker;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class GetProductCatalouge
 */
public class GetProductCatalouge extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	public GetProductCatalouge() {
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
					getList();
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

	void getList() 
	{
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		JSONObject mainObj=null;
		try {
			Statement stmt = mCon.createStatement();
			String sql="SELECT * from dalmia_data where Is_Active='1'" ;
			ResultSet rs = stmt.executeQuery(sql);
			mainObj=new JSONObject();
			JSONArray arr=new JSONArray();
			while (rs.next()) {
				JSONObject dataObj=new JSONObject();
				dataObj.put("Category", rs.getString("category"));
				dataObj.put("Data", rs.getString("data"));
				dataObj.put("Id", rs.getString("id"));
				dataObj.put("ImagePath", rs.getString("img_path"));
				dataObj.put("Is_Active", rs.getString("Is_Active"));
				arr.put(dataObj);
			}
			if(arr.length()>0)
			{
				mainObj.put("Message", "Success");
				mainObj.put("Status", MessageConstants.StatusSuccess);
				mainObj.put("Result",arr);
				rs.close();
				mWriter.print(mainObj.toString());
			}
			else
			{
				mainObj.put("Message", "No data found");
				mainObj.put("Status", MessageConstants.StatusFail);
				rs.close();
				mWriter.print(mainObj.toString());
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
