package com.constants;

import java.io.PrintWriter;
import java.util.Collection;

public class MessageConstants {
	/*-------------------- Api Response Result Status------------------------*/
	public static final String TokenValues = "abcHkl7900@8Uyhkj";
	public static final String StatusFail="false";
	public static final String StatusSuccess="true";
	public static final String StatusAuthError="error";
	
	
	/*-------------------- END------------------------*/
	

	/*-------------------- SMS Configuration------------------------*/
/*	public static String Smsurl = "http://boancomm.net/boansms/boansmsinterface.aspx";
	public static String Smsuser = "hrdalmia";
	public static String Smspass = "hr14dalmia";
	public static String Smsfeedid = "420";*/
	
	
	public static String Smsurl = "";
	public static String Smsuser = "";
	public static String Smspass = "";
	public static String Smsfeedid = "";
	/*-------------------- END------------------------*/

	
	/*-------------------- DB CONNECTION------------------------*/
	public static String DRIVER="com.mysql.jdbc.Driver"; 
	
	
	//Local Server
public static String CONNECTION_URL="jdbc:mysql://localhost:3306/dalmiya";  
	public static String USERNAME="rootuser";  
	public static String PASSWORD="abcd1234";

/*	//Live Server
public static String CONNECTION_URL="jdbc:mysql://ndc-mobdev-003.dalmiabharat.com:3306/dalmiya";  
	public static String USERNAME="root";  
	public static String PASSWORD="root123";*/
	/*-------------------- END------------------------*/


	/*-------------------- Images base url------------------------*/
	
	   //Local Server
  //public static final String ImagesBaseUrl="http://172.16.0.13:8080/DalmiaMobileAppData/";
  public static final String ImagesBaseUrl="http://localhost:8484/DalmiaMobileAppData/";

	  
	  //Live Server
// public static final String ImagesBaseUrl="https://buildexpert.dalmiabharat.com/DalmiaMobileAppData/";
	  
	/*-------------------- END------------------------*/

	   
	/*-------------------- Images Save Folder------------------------*/
//	public static final String ImagesDirectoryPath="C:\\wildfly\\welcome-content\\DalmiaMobileAppData\\";
	public static final String ImagesDirectoryPath="C:\\wildfly\\welcome-content\\DalmiaMobileAppData\\";

	/*-------------------- END------------------------*/

	/*---------------------Response Message--------------------------*/
	public static final String ErrorMessage = "Failed";
	public static final String SuccessMessage = "Success";
	public static final String ErrorNoData = "No Data Available";
	public static final String invalidTSO = "Invalid TSO Credential";

	
	/*---------------------END--------------------------*/
	
	public static final String ADMIN = "ADMIN";
	public static final String TSO = "TSO";
	public static final String REGIONALHEAD = "RegionalHead";
	public static final String SCO = "SCO";
	public static final String TECHNICALHEAD = "TechnicalHead";
	
	public static final String InvalidToken = "Invalid Token";
	public static final String InvalidVisitType = "Invalid VisitType";
	public static final String InvaliRequestData = "Invalid RequestData";

	public static final String DAILYVISIT = "DailyVisit";
	public static final String CRMACTIVITY = "CRMActivity";
	public static final String CST = "CST";
	public static final String TECHEXPERT = "TechExpert";
	public static final String ISVEREIFICATION = "IsVerification";
	
	
	public static final String ENGINEERVIST = "Engineer";
	public static final String HEADMASONVISIT = "HeadMason";
	public static final String CONTRACTORVISIT = "Contractor";
	public static final String DEALERVISIT = "Dealer";
	public static final String OTHERVISIT = "Other";
	public static final String PROJECTVISIT = "NewProjects";


	



}
