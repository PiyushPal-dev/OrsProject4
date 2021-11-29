<%@page import="in.co.sunrays.proj4.util.DataValidator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.proj4.controller.MarksheetCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@ page errorPage="ErrorView.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet View</title>
<style type="text/css">
input[type=submit] {
	width: 80px;
	padding: 2px;
}

input[type=text] {
	width: 190px;
	padding: 3px;
}

select {
	width: 200px;
	padding: 3px;
}
</style>
</head>
<body >
	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.MarksheetBean"
		scope="request"></jsp:useBean>

	<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">


		<%
			List l = (List) request.getAttribute("studentList");
		%>

		<center>
			<%
				if (bean.getId() > 0) {
			%>
			<h1>Update Marksheet</h1>
			<%
				} else {
			%>
			<h1>Add Marksheet</h1>
			<%
				}
			%>
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>
			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>


			<input type="hidden" name="id" value="<%=bean.getId()%>"> 
			<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"value="<%=bean.getModifiedBy()%>">
			 <input type="hidden"	name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
				
			<table align="center" style="margin-left: 36%;">
				<tr>
					<th align="left">RollNo<font style="color: red">*</font></th>
					<td><input type="text" name="rollNo"
					value="<%=DataUtility.getStringData(bean.getRollNo())%>"
					<%=(bean.getId() > 0) ? "readonly" : ""%> placeholder="Enter RollNo"> 
				 <font color="red"> <%=ServletUtility.getErrorMessage("rollNo", request)%></font>
						</td>
				</tr>
				<tr>
					<th align="left">Name<font style="color: red">*</font></th>
					<td><%=HTMLUtility.getList("studentId", String.valueOf(bean.getStudentId()), l)%><font
						style="color: red"><%=ServletUtility.getErrorMessage("studentId", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Physics<font style="color: red">*</font></th>
					<td><input type="text" name="physics"
						value="<%=DataUtility.getStringData(bean.getPhysics())%>" placeholder="Enter Physics Marks"><font
						color="red"> <%=ServletUtility.getErrorMessage("physics", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Chemistry<font style="color: red">*</font></th>
					<td><input type="text" name="chemistry"
						value="<%= DataUtility.getStringData(bean.getChemistry())%>" placeholder="Enter Chemistry Marks"><font
						color="red"> <%=ServletUtility.getErrorMessage("chemistry", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Maths<font style="color: red">*</font></th>
					<td><input type="text" name="maths"
						value="<%= DataUtility.getStringData(bean.getMaths())%>"  placeholder="Enter Maths Marks"><font
						color="red"> <%=ServletUtility.getErrorMessage("maths", request)%></font></td>

				</tr>
				<tr>
					<th></th>
					<td colspan="2">
						<%
							if (bean.getId() > 0) {
						%> <input type="submit" name="operation"
						value="<%=MarksheetCtl.OP_SAVE%>">&emsp; &emsp;<input type="submit"
						name="operation" value="<%=MarksheetCtl.OP_CANCEL%>"> <%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=MarksheetCtl.OP_SAVE%>">&emsp; &emsp;<input type="submit"
						name="operation" value="<%=MarksheetCtl.OP_RESET%>"> <%
 	}
 %>
					</td>
				</tr>
			</table>
		</center>
	</form>

	<%@ include file="Footer.jsp"%>
</body>
</html>