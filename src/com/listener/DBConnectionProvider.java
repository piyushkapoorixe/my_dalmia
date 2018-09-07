package com.listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.constants.MessageConstants;
public class DBConnectionProvider{
	public static void close(Connection conn) throws SQLException {
		if (conn == null) {
			return;
		}
		if (!conn.isClosed()) {
			conn.close();
		}
	}
	
	@SuppressWarnings("finally")
	public static Connection getCon() throws Exception
	{
		Connection con = null;
		try{
			Class.forName(MessageConstants.DRIVER);  
			con=DriverManager.getConnection(MessageConstants.CONNECTION_URL,MessageConstants.USERNAME,MessageConstants.PASSWORD);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			return con;
		}
	}
}

