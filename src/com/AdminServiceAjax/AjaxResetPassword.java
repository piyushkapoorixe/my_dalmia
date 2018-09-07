package com.AdminServiceAjax;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.constants.FolderAndDirectoryConstants;
import com.db.DBUtils;
import com.listener.DBConnectionProvider;
import com.utils.PasswordEncryption;

/**
 * Servlet implementation class AjaxResetPassword
 */
public class AjaxResetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection mCon=null;
	private PrintWriter mWriter;
	private  DBUtils mDbUtils;
	private String UserId=null;
	private String Pass=null;
	private String ConfirmPass=null;
	public AjaxResetPassword() {
		super();
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain");
		mWriter=resp.getWriter();
		validateData(req, resp);
	}
	void validateData(HttpServletRequest req, HttpServletResponse response)
	{
		try{
			mWriter=response.getWriter();
			mDbUtils=new DBUtils();
			UserId=req.getParameter("Id");
			ConfirmPass=req.getParameter("Confirm");
			Pass=req.getParameter("Password");
			if(Pass.trim().length()>0)
			{
				if(ConfirmPass.trim().length()>0)
				{
					if(Pass.equals(ConfirmPass))
					{
						if(Pass.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$")){
							if(UserId.contains("Admin"))
							{
								//edit admin password
								resetAdminNow();
								
							}
							else
							{
								//edit user password
								resetUserNow();		
							}
						}
						else{
							mWriter.println("* Must contain more than 6 characters\n* Atleast one special character\n* Atleast one number\n* Atleast one upper case Alphabet, &\n* Atleast one lower case Alphabet");	
						}
					}
					else
					{
						mWriter.println("Password Mismatched");	
					}

				}
				else
				{
					mWriter.println("Please enter the Confirm Password");	
				}
			}
			else
			{
				mWriter.println("Please enter the Password");	
			}
		}
		catch(Exception e)
		{
			mWriter.println(e.getLocalizedMessage());
		}
	}

	void resetAdminNow()
	{
		mCon=mDbUtils.generateConnection();
		try {
			String region="UPDATE  tbl_adminlogin_details SET Password= ? where ID=?" ;
			PreparedStatement preparedStmt = mCon.prepareStatement(region);
			String enc_pass=PasswordEncryption.encryptPasword(Pass);
			preparedStmt.setString   (1,enc_pass);
			preparedStmt.setString   (2, PasswordEncryption.dycriptBase64(UserId.substring(5).getBytes()));//remove the text 'Admin' from id
			preparedStmt.executeUpdate();
			mWriter.println("Password Reset Successfully ");	
		} catch (Exception e) {
			mWriter.println(e.getLocalizedMessage());
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}
		
	}
	void resetUserNow()
	{
		mCon=mDbUtils.generateConnection();
		try {
			String region="UPDATE  users_details SET password= ? where user_Id=?" ;
			PreparedStatement preparedStmt = mCon.prepareStatement(region);
			String enc_pass=PasswordEncryption.encryptPasword(Pass);
			preparedStmt.setString   (1,enc_pass);
			preparedStmt.setString   (2, PasswordEncryption.dycriptBase64(UserId.getBytes()));
			preparedStmt.executeUpdate();
			mWriter.println("Password Reset Successfully ");	
		} catch (Exception e) {
			mWriter.println(e.getLocalizedMessage());
		}finally {
			try {
				DBConnectionProvider.close(mCon);
			} catch (SQLException e) {
			}

		}
	}
}
