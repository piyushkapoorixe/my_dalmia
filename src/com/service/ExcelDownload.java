package com.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import com.db.DBUtils;

/**
 * Servlet implementation class ExcelDownload
 */
public class ExcelDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBUtils mDbUtils;
	private Connection mCon=null;
	private XSSFSheet sheet=null;
	private String Type;
	public ExcelDownload() {
		super();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Type=request.getParameter("Type");
		response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename="+Type+".xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		sheet = workbook.createSheet();
		getDBConnection(request);
		workbook.write(response.getOutputStream());
	}
	void getDBConnection(HttpServletRequest request)
	{
		mDbUtils=new DBUtils();
		try {
			mCon=mDbUtils.generateConnection();
			if(mCon!=null)
			{
				getFields(request);	

			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	void getFields(HttpServletRequest request)
	{
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setFontHeightInPoints((short) 14);
		font.setBoldweight((short)10);
		cellStyle.setFont(font);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
		Row row = sheet.createRow(0);
		Cell cellTitle = null;

		if(Type.equals("Users_Query"))
		{
			//download user's query/feedback  data 
			try {
				cellTitle=row.createCell(0);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Query_Ref_Id");

				cellTitle=row.createCell(1);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Name");

				cellTitle=row.createCell(2);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Profession");

				cellTitle=row.createCell(3);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Email");

				cellTitle=row.createCell(4);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Mobile");

				cellTitle=row.createCell(5);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Query");

				cellTitle=row.createCell(6);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Comment");

				cellTitle=row.createCell(7);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Date/Time");
				
				
				cellTitle=row.createCell(8);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("City");

				cellTitle=row.createCell(9);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("State");

				cellTitle=row.createCell(10);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Type");
				
				getUsersFedbackAndQuery();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(Type.equals("Service_Request")){
			//download Service Request  data 
			try {
				cellTitle=row.createCell(0);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Firm Name");

				cellTitle=row.createCell(1);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Name");

				cellTitle=row.createCell(2);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Email");

				cellTitle=row.createCell(3);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Mobile");

				cellTitle=row.createCell(4);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Adress");

				cellTitle=row.createCell(5);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Date of Call");
				
				getServiceRequestData();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if(Type.equals("Call_Hostory")){
			//download Service Request  data 
			try {
				cellTitle=row.createCell(0);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Caller Name");

				cellTitle=row.createCell(1);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Person Location");

				cellTitle=row.createCell(2);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Contact Person");

				cellTitle=row.createCell(3);
				cellTitle.setCellStyle(cellStyle);
				cellTitle.setCellValue("Date of Call");
				
				getCallHistory(request.getParameter("val"));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	void getUsersFedbackAndQuery()
	{
		int rowNos=1;
		Statement stmt;
		try {
			stmt = mCon.createStatement();
			String sql="Select users_query.Query_Ref_Id, users_details.user_Name, master_profession.Profession, users_details.email, users_details.mob_no, master_queryfeedback.Query, users_query.Comment, users_query.CreatedDate , users_query.City, users_query.State , users_query.Type from users_details INNER join users_query ON users_query.UserId= users_details.user_Id INNER join master_profession ON users_details.profession_Id= master_profession.profession_Id INNER join master_queryfeedback ON master_queryfeedback.id =users_query.QueryId" ;
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsMetaData = rs.getMetaData();
			CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
			Font font = sheet.getWorkbook().createFont();
			font.setFontHeightInPoints((short) 12);
			cellStyle.setFont(font);
			while (rs.next()) {
				Row row = sheet.createRow(rowNos);
				for(int i=0;i<rsMetaData.getColumnCount();i++)
				{
					Cell cellTitle = row.createCell(i);
					cellTitle.setCellStyle(cellStyle);
					cellTitle.setCellValue(rs.getString(i+1));	
				}
				rowNos++;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void getServiceRequestData()
	{
		int rowNos=1;
		Statement stmt;
		try {
			stmt = mCon.createStatement();
			String sql="SELECT FirmName, user_Name, email,mob_no,Address ,service_request.Call_time from users_details INNER JOIN service_request on users_details.user_Id=service_request.Uid" ;
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsMetaData = rs.getMetaData();
			CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
			Font font = sheet.getWorkbook().createFont();
			font.setFontHeightInPoints((short) 12);
			cellStyle.setFont(font);
			while (rs.next()) {
				Row row = sheet.createRow(rowNos);
				for(int i=0;i<rsMetaData.getColumnCount();i++)
				{
					Cell cellTitle = row.createCell(i);
					cellTitle.setCellStyle(cellStyle);
					cellTitle.setCellValue(rs.getString(i+1));	
				}
				rowNos++;
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	void getCallHistory(String Id)
	{
		int rowNos=1;
		Statement stmt;
		try {
			stmt = mCon.createStatement();
			String sql="SELECT fud.user_Name as Caller, fud.Address as CallerAddress, toud.user_Name as Called, history.CallTime from user_call_history as history INNER JOIN users_details as fud on history.FromCall=fud.user_Id INNER JOIN users_details as toud on history.ToCall=toud.user_Id where fud.profession_Id='"
					+ Id + "'";
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsMetaData = rs.getMetaData();
			CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
			Font font = sheet.getWorkbook().createFont();
			font.setFontHeightInPoints((short) 12);
			cellStyle.setFont(font);
			while (rs.next()) {
				Row row = sheet.createRow(rowNos);
				for(int i=0;i<rsMetaData.getColumnCount();i++)
				{
					Cell cellTitle = row.createCell(i);
					cellTitle.setCellStyle(cellStyle);
					cellTitle.setCellValue(rs.getString(i+1));	
				}
				rowNos++;
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
