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
import com.model.ConstructionTipsModel;
import com.model.ConstructionTipsModelList;
import com.utils.GeneralRequestFormat;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class GetConstructionTips
 */
public class GetConstructionTips extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String URL=this.getClass().getSimpleName();

	public GetConstructionTips() {
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
					getTips();
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

	void getTips() 
	{
			mCon=mDbUtils.generateConnection();	
			if(mCon==null)
			{
				ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
				return;
			}
			try {
				Statement stmt = mCon.createStatement();
				String sql="SELECT * from master_cunstruction" ;
				ResultSet rs = stmt.executeQuery(sql);
				ConstructionTipsModelList model=new ConstructionTipsModelList();
				ArrayList<ConstructionTipsModel> list=new ArrayList<>();
				while (rs.next()) {
					ConstructionTipsModel item=new ConstructionTipsModel();
					item.setCreatedOn(rs.getString("CreatedOn"));
					item.setHeading(rs.getString("Heading"));
					item.setTips(rs.getString("Tips"));
					item.setId(rs.getString("Id"));
					list.add(item);
				}
				if(list.size()>0)
				{
					model.setMessage("Success");
					model.setStatus(MessageConstants.StatusSuccess);
					model.setListConst(list);
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
