<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.sunrays.proj4.bean.FacultyBean"%>
<%@page import="in.co.sunrays.proj4.model.FacultyModel"%>
<%@page import="in.co.sunrays.proj4.controller.FacultyListCtl"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page errorPage="ErrorView.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>FacultyList View</title>
<style type="text/css">
input[type=submit]{
width: 100px;
padding: 2px;
}
input[type=text]{
width:190px;
padding: 3px;
}
select{
width: 200px;
padding: 3px;
}

</style>
<script type="text/javascript">
	function checkedAll() {

		var totalElement = document.forms[0].elements.length;
		for (var i = 0; i < totalElement; i++) {
			var en = document.forms[0].elements[i].name;

			if (en != undefined & en.indexOf("chk_") != -1) {
				document.forms[0].elements[i].checked = document.frm.chk_all.checked;

			}
		}
	}
	function check() {
		var en = document.forms[0].elements[5].name;
		if (en != undefined & en.indexOf("chk_") != -1) {
			document.forms[0].elements[5].checked = document.frm.chk_all.unchecked;
		}
	}
</script>
</head>
<body>
	<%@include file="Header.jsp"%>
	<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.FacultyBean" scope="request"></jsp:useBean>
	<center>
		<h1>FacultyList</h1>
		<h2>
			<font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
			<font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
		</h2>

		<form action="<%=ORSView.FACULTY_LIST_CTL%>"  name="frm" method="post">

			<%
				List list = ServletUtility.getList(request);
			%>
			<%
				if (list.size() > 0) {
			%>
			<%
				    List facultyList = (List) request.getAttribute("facultyList");
					List courseList = (List) request.getAttribute("courseList");
			%>

			<table width="100%">
				<tr>
					<td align="center"><label><b>Course:</b></label> 
					<%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), courseList)%>
						&emsp; 
						<label><b>Faculty  :</b></label>
						<%=HTMLUtility.getList("facultyname", String.valueOf(bean.getId()), facultyList) %>
						<label><b>First Name :</b></label>
						 <input type="text" name="firstName" placeholder="Enter First Name" 
						 value="<%=ServletUtility.getParameter("firstName", request)%>">
						&emsp;
						 <input type="submit" name="operation" value="<%=FacultyListCtl.OP_SEARCH%>"> &emsp;
						<input type="submit" name="operation" value="<%=FacultyListCtl.OP_RESET%>">
						</td>

				</tr>
			</table>
			<br>
			<table border="1"  width="100%">
				<tr>
					<th align="left" style="width: 100px;"><input type="checkbox"
						name="chk_all" onclick="checkedAll(this)" />Select All</th>
					<th>S.No.</th>
					<th>FirstName</th>
					<th>LastName</th>
					<th>EmailId</th>
					<th>Qualification</th>
					<th>CollegeName</th>
					<th>CourseName</th>
					<th>SubjectName</th>
					<th>Date Of Join</th>
					<th>MOB</th>
					<th>Edit</th>
				</tr>


				<%
					FacultyModel model = new FacultyModel();
						int pageNo = ServletUtility.getPageNo(request);
						int pageSize = ServletUtility.getPageSize(request);
						int index = ((pageNo - 1) * pageSize) + 1;
						FacultyBean bean1 = null;
						Iterator<FacultyBean> it = list.iterator();
						while (it.hasNext()) {
							bean1 = it.next();
				%>
				<tr align="center">

					<td align="left"><input type="checkbox" id="sid" name="chk_1"
						onchange="check()" value="<%=bean1.getId()%>"></td>
					<td><%=index++%></td>
					<td><%=bean1.getFirstName()%></td>
					<td><%=bean1.getLastName()%></td>
					<td><%=bean1.getEmailId()%></td>
					<td><%=bean1.getQualification()%></td>
					<td><%=bean1.getCollegeName()%></td>
					<td><%=bean1.getCourseName()%></td>
					<td><%=bean1.getSubjectName()%></td>
					<td><%=DataUtility.getDateString(bean1.getCreatedDatetime())%></td>
					<td><%=bean1.getMobileNo()%></td>
					<td><a href="FacultyCtl?id=<%=bean1.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>

			<br>
			<table width="100%">
				<tr>
					<%
						if (pageNo > 1) {
					%>
					<td><input type="submit" name="operation"
						value="<%=FacultyListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation" disabled="disabled"
						value="<%=FacultyListCtl.OP_PREVIOUS%>"></td>
					<%
						}
					%>
					<td><input type="submit"   name="operation"  value="<%=FacultyListCtl.OP_NEW%>"></td>
					<td style="padding-left: 10%;"><input type="submit"
						name="operation" value="<%=FacultyListCtl.OP_DELETE%>"></td>
					<%
					if(pageSize>list.size() || model.nextPK()-1 == bean.getId()){ 
					%>
					<td align="right"><input type="submit" name="operation"
						disabled="disabled" value="<%=FacultyListCtl.OP_NEXT%>"></td>
					<%
						} else {
					%>
					<td align="Right"><input type="submit" name="operation"
						value="<%=FacultyListCtl.OP_NEXT%>"></td>
					<%
						}
					%>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"><input
				type="hidden" name="pageSize" value="<%=pageSize%>">
			<%
				} else {
			%>
			<input style="padding: 5px; width: 100px;" type="submit"
				name="operation" value="<%=FacultyListCtl.OP_BACK%>">
			<%
				}
			%>
		</form>
	</center>
	<br><br><br><br>
	<%@include file="Footer.jsp"%>
</body>
</html>