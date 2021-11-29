<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.controller.TimeTableCtl"%>
<%@page import="in.co.sunrays.proj4.bean.TimeTableBean"%>
<%@page import="in.co.sunrays.proj4.controller.ErrorCtl"%>
<%@page errorPage="ErrorView.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TimeTable View</title>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
 function noSunday(date){
  return [date.getDay() != 0, false];
                      
 }; 
	$(function() {
		$("#datepicker").datepicker({
			dateFormat : 'mm/dd/yy',
			defaultDate : "01/01/2019",
			changeMonth : true,
			changeYear : true,
			yearRange : '0:+1',
			minDate:'+1',
			beforeShowDay:noSunday
		});
	});
</script>
<style type="text/css">
input[type=text] {
	width: 190px;
	padding: 3px;
}
select {
	width: 200px;
	padding: 2px;
}

input[type=submit] {
	width: 82px;
	padding: 2px;
}
</style>
</head>
<body>
	<div><%@include file="Header.jsp"%></div>
	<div align="center">
		<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.TimeTableBean"
			scope="request"></jsp:useBean>
		<%
			if (bean.getId() > 0) {
		%>
		<h1>Update TimeTable</h1>
		<%
			} else {
		%>
		<h1>Add TimeTable</h1>
		<%
			}
		%>
		<h2>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
		</h2>

		<form action="<%=ORSView.TIME_TABLE_CTL%>" method="post">

			<%
				List coursel = (List) request.getAttribute("courseList");
				List subjectl = (List) request.getAttribute("subjectList");
			%>
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDateTime" value="<%=bean.getCreatedDatetime()%>">
			<input type="hidden" name="modifiedDateTime"
				value="<%=bean.getModifiedDatetime()%>">


			<table align="center" style="margin-left: 35%;">
				<tr>
					<th align="left">Course Name<font color="red">*</font></th>
					<td><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()),coursel) %>
						<font color="red"><%=ServletUtility.getErrorMessage("courseId",request) %></font>
					</td>
				</tr>
				<tr>
					<th align="left">Subject Name<font color=" red">*</font></th>
					<td><%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubjectId()), subjectl) %>
						<font color="red"><%=ServletUtility.getErrorMessage("subjectId",request) %></font>
					</td>
				</tr>
				<tr>
					<th align="left">Semester<font color="red">*</font></th>
					<td>
						<%
					HashMap map=new HashMap();
					map.put("I", "I");
					map.put("II", "II");
					map.put("III", "III");
					map.put("IV", "IV");
					map.put("V", "V");
					map.put("VI", "VI");
					map.put("VII", "VII");
					map.put("VIII", "VIII");
				String	htmlList=HTMLUtility.getSemList("semester", bean.getSemester(), map);
				DataUtility.getStringData("semester");
					%>  <%=htmlList %><font
						color="red"><%=ServletUtility.getErrorMessage("semester",request) %></font>
					</td>
				</tr>
				<tr>
					<th align="left">Exam Date<font color="red">*</font></th>
					<td><input type="text" name="examDate" id="datepicker"
						placeholder="Enter Exam Date(Click Here)" readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getExamDate())%>"><font
						color="red"><%=ServletUtility.getErrorMessage("examDate",request) %></font></td>
				</tr>
				<tr>
					<th align="left">Time<font color="red">*</font></th>
					<td>
					<%
					HashMap map1=new HashMap();
					map1.put("08:00 am to 11:00 am", "08:00 am to 11:00 am");
					map1.put("10:00 am to 01:00 pm", "10:00 am to 01:00 pm");
					map1.put("01:00 pm to 04:00 pm", "01:00 pm to 04:00 pm");
					String htmlList1=HTMLUtility.getSemList("time", bean.getTime(), map1);
					%><%=htmlList1 %><font color="red"><%=ServletUtility.getErrorMessage("time",request) %></font></td>
				</tr>
				<tr>
					<th></th>
					<td>
						<%
							if (bean.getId() > 0) {
						%> <input type="submit" name="operation"
						value="<%=TimeTableCtl.OP_SAVE%>">&emsp;&emsp; <input
						type="submit" name="operation" value="<%=TimeTableCtl.OP_CANCEL%>">
						<%
							} else {
						%> <input type="submit" name="operation"
						value="<%=TimeTableCtl.OP_SAVE%>">&emsp;&emsp; <input
						type="submit"  name="operation"  value="<%=TimeTableCtl.OP_RESET%>">
						<%
							}
						%>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<%@include file="Footer.jsp"%>
</body>
</html>