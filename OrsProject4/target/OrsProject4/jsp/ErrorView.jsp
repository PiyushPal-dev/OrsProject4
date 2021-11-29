<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@page import="in.co.sunrays.proj4.bean.UserBean"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page isErrorPage="true" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error view</title>
</head>
<body style="margin-top: 220px">
	<form action="">
		<center>
			<%
				UserBean bean = (UserBean) session.getAttribute("user");
			%>
			<h1 style="color: red">*****....OOPS!! Something Went Wrong....*****</h1>
			<%
				if (bean == null) {
			%>
			<a href="<%=ORSView.LOGIN_CTL%>">Back</a>
			<%
				} else {
			%>
			<a href="<%=ORSView.WELCOME_CTL%>">Back</a>
			<%
				}
			%>
		</center>
	</form>
</body>
</html>