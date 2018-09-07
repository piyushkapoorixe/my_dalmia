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
import com.model.StateModel;
import com.model.StateModelList;
import com.utils.GeneralRequestFormat;
import com.utils.Invoker;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class GetState
 */
public class GetState extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();
	public GetState() {
		super();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initializeInitialRequirements(request, response);
	}

	void initializeInitialRequirements(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
	{mWriter=response.getWriter();
	requestParam=GeneralRequestFormat.getParam(request, response,URL);
	if(requestParam!=null)
	{
		try {
			if(requestParam.getString("TokenNo").equals(MessageConstants.TokenValues))
			{
				mDbUtils=new DBUtils();
				getStateList();
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

	void getStateList() 
	{
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		try {
			Statement stmt = mCon.createStatement();
			String sql="SELECT master_stateregion.id,master_stateregion.Region ,master_state.State_Id,master_state.StateName from master_state INNER JOIN master_stateregion ON master_stateregion.id=master_state.Region_Id where master_state.Is_Active='1'";
			//String sql="SELECT * from master_state" ;
			ResultSet rs = stmt.executeQuery(sql);
			StateModelList model=new StateModelList();
			ArrayList<StateModel> list=new ArrayList<>();
			while (rs.next()) {
				StateModel item=new StateModel();
				item.setStateId(rs.getString("State_Id"));
				item.setStateName(rs.getString("StateName"));
				item.setRegionId(rs.getString("id"));
				item.setRegionName(rs.getString("Region"));
				list.add(item);
			}
			if(list.size()>0)
			{
				model.setMessage("Success");
				model.setStatus(MessageConstants.StatusSuccess);
				model.setStateDetails(list);
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
		}
		finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}
	}


}
