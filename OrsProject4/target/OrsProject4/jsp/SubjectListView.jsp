<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.model.SubjectModel"%>
<%@page import="in.co.sunrays.proj4.bean.SubjectBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.controller.SubjectListCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page errorPage="ErrorView.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SubjectList View</title>
<style type="text/css">
input[type=text] {
	width: 190px;
	padding: 3px;
}

input[type=submit] {
	width: 100px;
	padding: 2px;
}

select {
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
		var en = document.forms[0].elements[6].name;
		if (en != undefined & en.indexOf("chk_1") != -1) {
			document.forms[0].elements[5].checked = document.frm.chk_all.unchecked;
		}
	}
</script>
</head>
<body>
	<%@include file="Header.jsp"%>
	<center>
		<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.SubjectBean"
			scope="request"></jsp:useBean>
		<jsp:useBean id="cbean" class="in.co.sunrays.proj4.bean.CourseBean"
			scope="request"></jsp:useBean>
		<h1>Subject List</h1>
		<h2>
			<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
		</h2>
		<%
			List l = (List) request.getAttribute("subjectList");
		%>
		<form action="<%=ORSView.SUBJECT_LIST_CTL%>" name="frm" method="post">

			<%
				List list = ServletUtility.getList(request);
			%>
			<%
				if (list.size() != 0) {
			%>
			<%
				List cList = (List) request.getAttribute("courseList");
			    List sList = (List) request.getAttribute("subjectList");
			%>
			<table width="100%">
				<tr>
					<td align="center"><label><b>Course :</b> </label><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), cList)%>&emsp;
						<label><b>Subject :</b> </label>
						<%=HTMLUtility.getList("subjectName", String.valueOf(bean.getId()), sList) %>
						<label><b>Subject Name :</b></label> <input type="text"
						name="name" placeholder="Enter Subject Name"
						value="<%=ServletUtility.getParameter("name", request)%>">
						&emsp; <input  type="submit"
						name="operation" value="<%=SubjectListCtl.OP_SEARCH%>">
						&emsp;<input type="submit"
						name="operation" value="<%=SubjectListCtl.OP_RESET%>"></td>

				</tr>
			</table>
			<br>
			<table border="2" width="100%">
				<tr>
					<th align="left" style="width: 100px"><input type="checkbox"
						name="chk_all" onclick="checkedAll()" />Select All</th>
					<th>S.No.</th>
					<th>Course Name</th>
					<th>Subject Name</th>
					<th>Description</th>
					<th>Edit</th>
				</tr>
				<%
					SubjectModel model = new SubjectModel();
						int pageNo = ServletUtility.getPageNo(request);
						int pageSize = ServletUtility.getPageSize(request);
						int index = ((pageNo - 1) * pageSize) + 1;

						Iterator<SubjectBean> it = list.iterator();

						while (it.hasNext()) {

							bean = it.next();
				%>
				<tr align="center">
					<td align="left"><input type="checkbox" id="sid" name="chk_1"
						onchange="check()" value="<%=bean.getId()%>"></td>
					<td><%=index++%></td>
					<td><%=bean.getCourseName()%></td>
					<td><%=bean.getName()%></td>
					<td><%=bean.getDescription()%></td>
					<%
						if (userBean.getRoleId() == RoleBean.ADMIN) {
					%>
					<td><a href="SubjectCtl?id=<%=bean.getId()%>">Edit</a></td>
					<%
						} else {
					%>
					<td><a>----</a></td>
					<%
						}
					%>
				</tr>
				<%
					}
				%>
			</table>
			<br>
			<table width="100%">
				<tr>
					<td><input  type="submit" name="operation" value="<%=SubjectListCtl.OP_PREVIOUS%>"
						<%=(pageNo <= 1) ? "disabled" : ""%>></td>

					<td><input  type="submit" name="operation" value="<%=SubjectListCtl.OP_NEW%>"
						<%=(userBean.getRoleId() == 1) ? "" : "disabled"%>></td>
					<%
						if (userBean.getRoleId() == 1) {
					%>
					<td style="padding-left: 13%"><input type="submit" name="operation"
						value="<%=SubjectListCtl.OP_DELETE%>" <%=(list.size() == 0) ? "disabled" : ""%>></td>
					<%
						}
 
							List subjectList = (List) request.getAttribute("subjectList");

							if (pageSize > list.size() || model.nextPK() - 1 == bean.getId()) {
					%>

					<td align="right">
					<input type="submit" name="operation" disabled="disabled" value="<%=SubjectListCtl.OP_NEXT%>"></td>
					<%
						} else {
					%>
					<td align="right">
					<input type="submit" name="operation" value="<%=SubjectListCtl.OP_NEXT%>"></td>
					<%
						}
					%>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>">
			<input	type="hidden" name="pageSize" value="<%=pageSize%>">
			<%
				} else {
			%>
			<input type="submit" name="operation" value="<%=SubjectListCtl.OP_BACK%>">
			<%
				}
			%>
		</form>
	</center>
	<br>
	<br>
	<br>
	<%@include file="Footer.jsp"%>
</body>
</html>