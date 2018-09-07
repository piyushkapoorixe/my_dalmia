package com.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.constants.MessageConstants;
import com.db.DBUtils;

public class BasicUtils {
	private static JSONObject requestParam=null;
	private static PrintWriter mWriter;
	private static Connection mCon=null;
	public static boolean chekBasicConnection(HttpServletRequest request, HttpServletResponse response, String Url)
	{
		requestParam=GeneralRequestFormat.getParam(request, response,Url);
		if(requestParam!=null)
		{
			try {
				if(requestParam.getString("TokenNo").equals(MessageConstants.TokenValues))
				{
					mWriter=response.getWriter();
					DBUtils mDbUtils=new DBUtils();
					mCon=mDbUtils.generateConnection();		
					if(mCon!=null)
					{

					}else
					{
						ResponseUtil.seterror(mWriter, "Database problem occur", MessageConstants.StatusFail);
					}
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

		return false;		
	}


}
