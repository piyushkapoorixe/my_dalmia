<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	session.invalidate();

	//session = request.getSession();
	response.sendRedirect("index.jsp");
%>