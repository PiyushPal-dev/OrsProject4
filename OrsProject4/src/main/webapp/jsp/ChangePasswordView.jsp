<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.proj4.controller.ChangePasswordCtl"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page errorPage="ErrorView.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ChangePassword View</title>
<style type="text/css">
input[type=password]{
width: 190px;
padding: 3px;
}
</style>
</head>
<body>
	<form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.UserBean"
			scope="request"></jsp:useBean>

		<center>
			<h1>Change Password</h1>
          <H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
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

			<table align="center" style="margin-left: 33%;">
				<tr>
					<th align="left">Old Password <font style="color: red">*</font></th>
					<td><input type="password" name="oldPassword"
					 placeholder="Old Password"><font
						color="red"> <%=ServletUtility.getErrorMessage("oldPassword", request)%></font></td>
				</tr>

				<tr>
					<th align="left">New Password<font style="color: red">*</font></th>
					<td><input type="password" name="newPassword"
					 placeholder="New Password"><font
						color="red"> <%=ServletUtility.getErrorMessage("newPassword", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Confirm Password<font style="color: red">*</font></th>
					<td><input type="password" name="confirmPassword"
					 placeholder="ConfirmPassword"><font
						color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
				</tr>

				<tr>
					<th></th>
					<td colspan="2"><input type="submit" name="operation"
						value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE%>">
						&nbsp; &emsp;<input type="submit" name="operation"
						value="<%=ChangePasswordCtl.OP_SAVE%>"></td>
				</tr>

			</table>
			
	</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>