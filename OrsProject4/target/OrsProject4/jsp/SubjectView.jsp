<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<%@page import="in.co.sunrays.proj4.controller.SubjectCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page errorPage="ErrorView.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Subject View</title>
<link rel="icon" type="image/Ico" href="img/logo.ico" />
<style>
input[type=text], input[type=password] {
	width: 190px;
	padding: 3px
}
input[type=submit]{
width:100px;
padding: 2px;}

select {
	width: 200px;
	padding: 3px
}

textarea {
	width: 195px;
	padding: 2px;
}

</style>

</head>
<body>
	<div>
		<%@ include file="Header.jsp"%></div>
	<form action="<%=ORSView.SUBJECT_CTL%>" method="post">

		<script type="text/javascript"></script>


		<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.SubjectBean"
			scope="request"></jsp:useBean>
<center>
		<%
			List l = (List) request.getAttribute("courseList");
		%>


		<%
			if (bean.getId() > 0) {
		%>
		<h1 align="center">Update Subject</h1>
		<%
			} else {
		%>
		<h1>Add Subject</h1>
		<%
			}
		%>

		
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>
		</center>




		<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
			type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy"
			value="<%=bean.getModifiedBy()%>"> <input type="hidden"
			name="createdDatetime"
			value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
		<input type="hidden" name="modifiedDatetime"
			value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">



		<table align="center" style="margin-left: 35%">
			<tr>
				<th align="left">Course Name<font color="red">*</font></th>
				<td><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), l)%><font
					color="red"> <%=ServletUtility.getErrorMessage("courseId", request)%></font></td>
			</tr>

			<tr>
				<th align="left">Subject Name<font color="red">*</font></th>
				<td><input type="text" name="name"
					placeholder="Enter Subject Name"
					value="<%=DataUtility.getStringData(bean.getName())%>"><font
					color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
			</tr>

			<tr>
				<th align="left">Description<font color="red">*</font></th>
				<td><textarea rows="2" cols="21" name="description"
						style="resize: both;" placeholder="Description Of Course"><%=DataUtility.getStringData(bean.getDescription())%></textarea><font
					color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>

			</tr>

			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>

			<tr>
				<td></td>
				<td>
					<%
						if (bean.getId() > 0) {
					%> <input type="submit" name="operation"
					value="<%=SubjectCtl.OP_SAVE%>"> <input type="submit"
					name="operation" value="<%=SubjectCtl.OP_CANCEL%>"> <%
 	} else {
 %>
					<input type="submit" name="operation"
					value="<%=SubjectCtl.OP_SAVE%>">&emsp; <input type="submit"
					name="operation" value="<%=SubjectCtl.OP_RESET%>"> <%
 	}
 %>
				</td>
			</tr>


		</table>


		</div>
	</form>


	<div><%@ include file="Footer.jsp"%></div>
</body>
</html>