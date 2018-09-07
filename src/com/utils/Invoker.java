package com.utils;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Invoker {
	public static void  invoke()
	{
		try {
	        java.sql.Driver mySqlDriver = DriverManager.getDriver("jdbc:mysql://localhost:3306/dalmiya");
	        DriverManager.deregisterDriver(mySqlDriver);
	    } catch (SQLException ex) {
	    	ex.printStackTrace();
	    } 
	}

}
