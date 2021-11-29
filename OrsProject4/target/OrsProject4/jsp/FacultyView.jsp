<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.proj4.controller.FacultyCtl"%>
<%@page errorPage="ErrorView.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Faculty View</title>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
	$(function() {
		$("#datepicker").datepicker({
			dateFormat : 'mm/dd/yy',
			defaultDate : "01/01/1975",
			changeMonth : true,
			changeYear : true,
			yearRange : '1975:-18'
		});
	});
</script>
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
</style>

</head>
<body>
	<%@include file="Header.jsp"%>
	<center>


		<form action="<%=ORSView.FACULTY_CTL%>" method="post">
			<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.FacultyBean"
				scope="request"></jsp:useBean>

			
			<%
				if (bean.getId() > 0) {
			%>
			<h1>Update Faculty</h1>
			<%
				} else {
			%>
			<h1>Add Faculty</h1>
			<%
				}
			%>

       <h2>   
                <font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
				<font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
			</h2>
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table align="center" style="margin-left: 34%;">
				<tr>
					<th align="left">First Name<font style="color: red">*</font></th>
					<td><input type="text" name="firstName"
						placeholder="Enter First Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"><font
						style="color: red"><%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Last Name<font style="color: red">*</font></th>
					<td><input type="text" name="lastName"
						placeholder="Enter Last Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"><font
						style="color: red"><%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Gender<font style="color: red">*</font></th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("M", "Male");
							map.put("F", "Female");
							String mfList = HTMLUtility.getList("gender", String.valueOf(bean.getGender()), map);
						%><%=mfList%> <font style="color: red"><%=ServletUtility.getErrorMessage("gender", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="left">College Name<font style="color: red">*</font></th>
					<%
						List l = (List) request.getAttribute("collegeList");
					%>

					<td><%=HTMLUtility.getList("collegeList", String.valueOf(bean.getCollegeId()), l)%>
						<font style="color: red"><%=ServletUtility.getErrorMessage("collegeId", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Course Name<font style="color: red">*</font></th>
					<%
						List ll = (List) request.getAttribute("courseList");
					%>
					<td><%=HTMLUtility.getList("courseList", String.valueOf(bean.getCourseId()), ll)%>
						<font style="color: red"><%=ServletUtility.getErrorMessage("courseId", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Subject Name<font style="color: red">*</font></th>
					<%
						List list = (List) request.getAttribute("subjectList");
					%>
					<td><%=HTMLUtility.getList("subjectList", String.valueOf(bean.getSubjectId()), list)%><font
						style="color: red"><%=ServletUtility.getErrorMessage("subjectId", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Qualification<font style="color: red">*</font></th>
					<td><input type="text" name="qualification"
						placeholder="Enter Qualifcation"
						value="<%=DataUtility.getStringData(bean.getQualification())%>"><font
						Style="color: red"><%=ServletUtility.getErrorMessage("qualification", request)%></font></td>
				</tr>
				<tr>
					<th align="left">EmailId<font style="color: red">*</font></th>
					<td><input type="text" name="emailId"
						placeholder="Enter Email Id"
						value="<%=DataUtility.getStringData(bean.getEmailId())%>"><font
						style="color: red"><%=ServletUtility.getErrorMessage("emailId", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Mobile No<font style="color: red">*</font></th>
					<td><input type="text" name="mobileNo"
						placeholder="Enter MobileNo"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"><font
						style="color: red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Date OF Birth<font style="color: red">*</font></th>
					<td><input type="text" name="dateOfBirth" id="datepicker"
						readonly="readonly" placeholder="mm/dd/yyyy"
						value="<%=DataUtility.getDateString(bean.getDob())%>"><font
						style="color: red"><%=ServletUtility.getErrorMessage("dateOfBirth", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<td>
						<%
							if (bean.getId() > 0) {
						%> <input type="submit" name="operation"
						value="<%=FacultyCtl.OP_SAVE%>">&emsp;&emsp; <input
						type="submit" name="operation" value="<%=FacultyCtl.OP_CANCEL%>">
						<%
							} else {
						%> <input type="submit" name="operation"
						value="<%=FacultyCtl.OP_SAVE%>">&emsp;&emsp; <input
						type="submit" name="operation" value="<%=FacultyCtl.OP_RESET%>">
						<%
							}
						%>
					</td>
				</tr>
			</table>
		</form>
	</center>
	<br><br>
	<%@include file="Footer.jsp"%>
</body>
</html>