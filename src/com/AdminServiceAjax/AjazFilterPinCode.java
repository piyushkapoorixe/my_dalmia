package com.AdminServiceAjax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.listener.DBConnectionProvider;

/**
 * Servlet implementation class AjazFilterPinCode
 */
public class AjazFilterPinCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpServletRequest mRequest;
	private PrintWriter mWriter;
    public AjazFilterPinCode() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		mRequest=request;
		mWriter=response.getWriter();
		filterPin(request,response);
	}
	void filterPin(HttpServletRequest request, HttpServletResponse response)
	{
		int cityId=Integer.parseInt(request.getParameter("CityId"));
		Connection con=null;
		try {
			con = DBConnectionProvider.getCon();
			Statement stmt = con.createStatement();
			String sql="SELECT * from master_pin_code where isactive='1' AND CItyId='"+cityId+"'" ;
			ResultSet rs = stmt.executeQuery(sql);
			StringBuilder html=new StringBuilder();
			while (rs.next()) {
				html.append("<option value="+rs.getString("Id")+">");
				html.append(rs.getString("Pin"));
				html.append("</option>");
			}
			mWriter.println(html);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DBConnectionProvider.close(con);
			} catch (SQLException e) {
			}

		}
	}

}
