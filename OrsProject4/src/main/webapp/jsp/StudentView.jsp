<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.controller.StudentCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.sunrays.proj4.controller.StudentListCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.bean.StudentBean"%>
<%@ page import="in.co.sunrays.proj4.controller.StudentCtl"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@ page errorPage="ErrorView.jsp"%>
<html>
<head>
<title>Student View</title>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type="text/javascript">
	$(function() {
		$("#datepicker").datepicker({
			dateFormat : 'mm/dd/yy',
			defaultDate : "01/01/1982",
			changeMonth : true,
			changeYear : true,
			yearRange : '-35:-18'
		});
	});
</script>
<style type="text/css">
input[type=text] {
	width: 190px;
	padding: 3px;
}

input[type=submit] {
	width: 80px;
	padding-left: 2px;
}
select{
width:200px;
padding:3px;
}
</style>
</head>
<body>
	<center>
		<form action=" <%=ORSView.STUDENT_CTL%>" method="post">
			<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.StudentBean"
				scope="request"></jsp:useBean>
			<%@ include file="Header.jsp"%>
			<%
				if (bean.getId() > 0) {
			%>
			<h1>Update Student</h1>
			<%
				} else {
			%>
			<h1>Add Student</h1>
			<%
				}
			%>
			<h2>
				<font style="color: green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h2>
			<h2>
				<font style="color: red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h2>
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
					<th align="left">First Name<font style="color: red">*</font></th>
					<td><input type="text" name="firstName"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"  placeholder="Enter First Name">
						<font style="color: red"><%=ServletUtility.getErrorMessage("firstName", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="left">Last Name<font style="color: red">*</font></th>
					<td><input type="text" name="lastName"
						value="<%=DataUtility.getStringData(bean.getLastName())%>" placeholder="Enter last Name">
						<font style="color: red"><%=ServletUtility.getErrorMessage("lastName", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="left">Email<font style="color: red">*</font></th>
					<td><input type="text" name="email"
						value="<%=DataUtility.getStringData(bean.getEmail())%>"  placeholder="Enter  EmailId"> <font
						style="color: red"><%=ServletUtility.getErrorMessage("email", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="left">College Name<font style="color: red">*</font></th>
					<%
						List l = (List) request.getAttribute("collegeList");
					%>
					<td><%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), l)%><font
						style="color: red"> <%=ServletUtility.getErrorMessage("collegeId", request)%>
					</font></td>

				</tr>

				<tr>
					<th align="left">Mobile No<font style="color: red">*</font></th>
					<td><input type="text" name="mobileNo"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>" placeholder="Enter MobileNo">
						<font style="color: red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="left">Date Of Birth<font style="color: red">*</font></th>
					<td><input type="text" name="dob" id="datepicker"
						readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getDob())%>" placeholder="Enter Date Of Birth"> <font
						style="color: red"><%=ServletUtility.getErrorMessage("dob", request)%></font>
					</td>
				</tr>
				<tr>
					<th></th>
					<td>
					<%if(bean.getId()>0){ %>
					<input type="submit" name="operation" 
						value="<%=StudentCtl.OP_SAVE%>">&emsp;&emsp; <input
						type="submit" name="operation" value="<%=StudentCtl.OP_CANCEL%>">
						<%}else{ %>
						<input type="submit" name="operation" value="<%=StudentCtl.OP_SAVE%>">&emsp;&emsp;
						<input type="submit" name="operation" value="<%=StudentCtl.OP_RESET%>">
						<%} %>
						</td>
				</tr>
			</table>
		</form>
	</center>
	<%@ include file="Footer.jsp"%>
</body>
</html>