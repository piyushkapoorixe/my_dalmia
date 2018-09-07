package com.db;

import java.sql.Connection;

import com.listener.DBConnectionProvider;


public class DBUtils {
	
	public static Connection generateConnection() 
	{
		try {
			return DBConnectionProvider.getCon();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
