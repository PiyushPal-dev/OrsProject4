<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.controller.CourseCtl"%>
<%@page errorPage="ErrorView.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Course View</title>
<style type="text/css">
input[type=text] {
	width: 190px;
	padding: 3px;
}

input[type=submit] {
	width: 85px;
	padding: 2px;
}

select {
	width: 200px;
	padding: 3px;
}

textarea {
	width: 195px;
	padding: 2px;
}
</style>
</head>
<body>
	<%@include file="Header.jsp"%>
	<center>
		<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.CourseBean"
			scope="request"></jsp:useBean>
		<form action="<%=ORSView.COURSE_CTL%>" method="post">
 <%bean.getId();%>
			<%
				if (bean.getId() > 0) {
			%>
			<h1>Update Course</h1>
			<%
				} else {
			%>
			<h1>Add Course</h1>
			<%
				}
			%>
			<h2>
				<font style="color: red"><%=ServletUtility.getErrorMessage(request)%></font>
				<font style="color: green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h2>





			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">


			<table align="center" style="margin-left: 36%;">
				<tr>
					<th align="left">Name<font color="red">*</font></th>
					<td><input type="text" name="name"
						placeholder="Enter Course Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>

				</tr>
				<tr>
					<th align="left">Description<font color="red">*</font></th>
					<td><textarea rows="2" cols="22" name="description"
							style="resize: both;" placeholder="Description Of Course"
							value="<%=DataUtility.getStringData(bean.getDescription())%>"><%=DataUtility.getStringData(bean.getDescription())%></textarea><font
						color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Duration<font color="red">*</font></th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("2 years", "2 years");
							map.put("3 years", "3 years");
							map.put("4 years", "4 years");

							String htmlList = HTMLUtility.getList("duration", bean.getDuration(), map);
							DataUtility.getStringData("duration");
						%> <%=htmlList%> <font color="red"><%=ServletUtility.getErrorMessage("duration", request)%></font>

					</td>
				</tr>

				<tr>
					<th></th>
					<td>
						<%
							if (bean.getId() > 0) {
						%> <input type="submit" name="operation"
						value="<%=CourseCtl.OP_SAVE%>"> &emsp;&emsp;<input
						type="submit" name="operation" value="<%=CourseCtl.OP_CANCEL%>">
						<%
							} else {
						%> <input type="submit" name="operation" value="<%=CourseCtl.OP_SAVE%>">&emsp;
						<input type="submit" name="operation"
						value="<%=CourseCtl.OP_RESET%>"> <%
 	}
 %>
					</td>
				</tr>
			</table>
		</form>
	</center>
	<%@include file="Footer.jsp"%>
</body>
</html>