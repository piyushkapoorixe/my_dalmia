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
import org.json.JSONObject;
import com.constants.MessageConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.model.TollFreeModel;
import com.utils.GeneralRequestFormat;
import com.utils.Invoker;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class GetTollFreeNo
 */
public class GetTollFreeNo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	public GetTollFreeNo() {
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
				String sql="SELECT a.Region,a.ContactNo from master_stateregion a inner join master_state b on a.id=b.Region_Id where a.IsActive='1' AND StateName='"+stateName+"'";			
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next())
				{
					TollFreeModel model=new TollFreeModel();
					model.setMessage("Success");
					model.setStatus(MessageConstants.StatusSuccess);
					model.setRegion(rs.getString("Region"));
					model.setTollFreeNo(rs.getString("ContactNo"));
					rs.close();
					ResponseUtil.setResponse(mWriter, model);
				}
				else
				{
					ResponseUtil.seterror(mWriter, "We are not available here", MessageConstants.StatusFail);
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
