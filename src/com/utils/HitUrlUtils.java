package com.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.constants.MessageConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;

public class HitUrlUtils {
	public  static String callURL(String myURL) {
		String UrlIs=myURL.replaceAll(" ", "%20");
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(UrlIs);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(),Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}


	public static void sendInqtoRMAndTso(String Uid,String msg)
	{
		Connection  mCon=null;
		String  regionId=null;
		try {
			DBUtils mDbUtils=new DBUtils();
			mCon=mDbUtils.generateConnection();
			Statement stmt = mCon.createStatement();
			String sql="Select master_distric.RegionId from master_distric Join users_details On users_details.DistricId=master_distric.id where users_details.user_Id='"+Uid+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				regionId=rs.getString("RegionId");
			}
			if(regionId!=null)
			{
				Statement stmt2 = mCon.createStatement();
				String sql2="SELECT Email_ID,Mobile_No from tbl_adminlogin_details where SubRegion='"+regionId+"'";
				ResultSet rs2 = stmt2.executeQuery(sql2);	
				while(rs2.next())
				{
					//Sending to Regional Head
					String address2 = MessageConstants.Smsurl+"?pid="+MessageConstants.Smsfeedid+"&uname="+MessageConstants.Smsuser+"&pwd="+MessageConstants.Smspass+"&mobileno="+rs2.getString("Mobile_No")+"&smsmsg="+msg;
					HitUrlUtils.callURL(address2);
					Mailer.send(rs2.getString("Email_ID"), "Dalmia", msg);
				}
				sendInqtoTso(Uid, msg);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}
		}

	}

	static void sendInqtoTso(String Uid,String msg)
	{
		Connection  mCon=null;
		String  districId=null;
		try {
			DBUtils mDbUtils=new DBUtils();
			mCon=mDbUtils.generateConnection();
			Statement stmt = mCon.createStatement();
			String sql="Select users_details.DistricId from users_details where users_details.user_Id='"+Uid+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				districId=rs.getString("DistricId");
			}
			if(districId!=null)
			{
				Statement stmt2 = mCon.createStatement();
				String sql2="SELECT Email_ID,Mobile_No from tbl_adminlogin_details where DistricId='"+districId+"'";
				ResultSet rs2 = stmt2.executeQuery(sql2);	
				while(rs2.next())
				{
					//Sending to Tso
					String address2 = MessageConstants.Smsurl+"?pid="+MessageConstants.Smsfeedid+"&uname="+MessageConstants.Smsuser+"&pwd="+MessageConstants.Smspass+"&mobileno="+rs2.getString("Mobile_No")+"&smsmsg="+msg;
					HitUrlUtils.callURL(address2);
					Mailer.send(rs2.getString("Email_ID"), "Dalmia", msg);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}
		}

	}
}
