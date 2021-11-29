<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.sunrays.proj4.controller.RoleCtl"%>
<%@page import="in.co.sunrays.proj4.controller.BaseCtl"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page errorPage="ErrorView.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Role View</title>
<style type="text/css">
input[type=text] {
	width: 190px;
	padding: 3px;
}
input[type=submit]{
width:85px;
padding: 2px;
}
</style>
</head>
<body>
	<form action="<%=ORSView.ROLE_CTL%>" method="post">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.RoleBean"
			scope="request"></jsp:useBean>

		<center>
		<%if(bean.getId()>0) {%>
		<h1>Update Role</h1>
		<%}else{ %>
			<h1>Add Role</h1>
			<%} %>
			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">


			<table align="center" style="margin-left: 35%;">
				<tr>
					<th align="left">Name<font style="color: red">*</font></th>
					<td><input type="text" name="name"
						value="<%=DataUtility.getStringData(bean.getName())%>" placeholder="Enter Name"><font
						color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Description<font style="color: red">*</font></th>
					<td><input type="text" name="description"
						value="<%=DataUtility.getStringData(bean.getDescription())%>" placeholder="Enter Discription"><font
						color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<td colspan="2">
					<%if(bean.getId()>0){ %>
					<input type="submit" name="operation"
						value="<%=RoleCtl.OP_SAVE%>" >&emsp;&emsp;
						 <input type="submit" name="operation"
						value="<%=RoleCtl.OP_CANCEL%>" >
						<%}else{ %>
						<input type="submit" name="operation" value="<%=RoleCtl.OP_SAVE%>">&emsp;&emsp;
						<input type="submit" name="operation" value="<%=RoleCtl.OP_RESET%>">
						<%} %>
						</td>
				</tr>
			</table>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>