package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.constants.MessageConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.responsemodel.CommonResponseModel;
import com.utils.GeneralRequestFormat;
import com.utils.HitUrlUtils;
import com.utils.Invoker;
import com.utils.RandomIdGenerator;
import com.utils.ResponseUtil;

/**
 * Servlet implementation class SaveUsersQuery
 */
public class SaveUsersQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private JSONObject requestParam=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String MobileNo;
	private String URL=this.getClass().getSimpleName();
	private String randomRefId=null;
	public SaveUsersQuery() {
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
					saveQuery();
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

	void saveQuery() 
	{
		String QueryId=null;
		String QueryType=null;
		String UserId=null;
		String Comment=null;
		String CityName=null;
		String StateName=null;
		mCon=mDbUtils.generateConnection();	
		if(mCon==null)
		{
			ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
			return;
		}
		try {
			QueryId=requestParam.getString("QueryId");
			QueryType=requestParam.getString("QueryType");
			UserId=requestParam.getString("UserId");
			Comment=requestParam.getString("Comment");
			CityName=requestParam.getString("CityName");
			StateName=requestParam.getString("StateName");
			MobileNo=requestParam.getString("MobileNo");
			String query = "insert into users_query (QueryId,Type,UserId,Comment,Query_Ref_Id,State,City)"+ " values (?, ?, ?, ?, ?,?,?)";
			PreparedStatement preparedStmt = mCon.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setString (1, QueryId);
			preparedStmt.setString (2, QueryType);
			preparedStmt.setString (3, UserId);
			preparedStmt.setString (4, Comment);
			preparedStmt.setString (5, "0");
			preparedStmt.setString (6, StateName);
			preparedStmt.setString (7, CityName);
			boolean f=preparedStmt.execute();
			ResultSet generatedKeys = preparedStmt.getGeneratedKeys();
			if(generatedKeys.next())
			{
				randomRefId=""+generatedKeys.getInt(1);
			}
			String insertRefId = "UPDATE  users_query SET Query_Ref_Id= ? where id=?";
			PreparedStatement insertRefIdpreparedStmt = mCon.prepareStatement(insertRefId);
			insertRefIdpreparedStmt.setString (1, RandomIdGenerator.getRefIDBasedOnUserId(randomRefId));
			insertRefIdpreparedStmt.setString (2, randomRefId);
			insertRefIdpreparedStmt.execute();
			CommonResponseModel model=new CommonResponseModel();
			String msg;
			if(QueryType.equals("Q"))
			{
				msg="Your query submitted Successffuly. Kindly note the Ref. id "+RandomIdGenerator.getRefIDBasedOnUserId(randomRefId)+" for reference";
			}
			else
			{
				msg="Your feedback submitted Successffuly. Kindly note the Ref. id "+RandomIdGenerator.getRefIDBasedOnUserId(randomRefId)+" for reference";	
			}
			sendMessage(); 
			model.setMessage(msg);
			model.setStatus(MessageConstants.StatusSuccess);
			ResponseUtil.setResponse(mWriter, model);
		} catch (Exception e) {
			ResponseUtil.seterror(mWriter, e.getLocalizedMessage(), MessageConstants.StatusFail);
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}
	}
	void sendMessage()
	{
		String sms="We%20have%20Recieved%20Your%20Feedback/Query%20Kindly%20note%20the%20Ref.%20id%20"+randomRefId+"%20for%20reference";
		String address = MessageConstants.Smsurl+"?pid="+MessageConstants.Smsfeedid+"&uname="+MessageConstants.Smsuser+"&pwd="+MessageConstants.Smspass+"&mobileno="+MobileNo+"&smsmsg="+sms;
		HitUrlUtils.callURL(address);
	}
	
	
}
