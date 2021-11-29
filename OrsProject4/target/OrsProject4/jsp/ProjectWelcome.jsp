<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@ page errorPage="ErrorView.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome View</title>
</head>
<body>
 <%@ include file="Header.jsp"%>
        <form action="<%=ORSView.WELCOME_CTL%>">
                
        </form>
        
        <%@ include file="Footer.jsp"%>
</body>
</html>