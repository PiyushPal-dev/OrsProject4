<%@page import="in.co.sunrays.proj4.bean.CourseBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.sunrays.proj4.model.CourseModel"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.controller.CourseListCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page errorPage="ErrorView.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CourseList View</title>
<style type="text/css">
input[type=text] {
	width: 190px;
	padding: 3px;
}
input[type=submit]{
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
			if (en != undefined & en.indexOf("chk_1") != -1) {
				document.forms[0].elements[i].checked = document.frm.chk_all.checked;
			}
		}
	}
	function check(frm) {
		var en = document.forms[0].elements[5].name;
		if (en != undefined & en.indexOf("chk_") != -1) {
			document.forms[0].elements[5].checked = document.frm.chk_all.unchecked;
		}
	}
</script>
</head>
<body>
	<%@include file="Header.jsp"%>
	<!-- <div style="min-height: 450px"> -->
	<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.CourseBean"
		scope="request"></jsp:useBean>

	<%
		List l = (List) request.getAttribute("courseList");
	%>

	<center>
		<h1>Course List </h1>
		<h2>
			<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
		
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
		</h2>

		<form action="<%=ORSView.COURSE_LIST_CTL%>" name="frm" method="POST">
			<%
				List list = ServletUtility.getList(request);
			%>
			<%
				if (list.size() != 0) {
			%>
			<table width="100%">
				<tr>
					<td align="center">
						 <label><b>Course Name :</b></label> <%=HTMLUtility.getList("courseId", String.valueOf(bean.getId()),l)%>
						&emsp;  <label><b> Name :</b></label> <input type="text"
						name="name" placeholder="Enter Course Name"
						value="<%=ServletUtility.getParameter("name", request)%>">
						&emsp; <label><b>Duration :</b> </label>
						<%
						HashMap map=new HashMap();
						map.put("2 years", "2 years");
						map.put("3 years", "3 years");
						map.put("4 years", "4 years");
				

								String htmlList = HTMLUtility.getList("duration", bean.getDuration(), map);
						%> <%=htmlList%>&emsp; <input type="submit" name="operation" value="<%=CourseListCtl.OP_SEARCH%>">&emsp;
						<input type="submit" name="operation" value="<%=CourseListCtl.OP_RESET%>">
					</td>
				</tr>
			</table>
			<br>
			<table border="1" width="100%">
				<tr>
					<th align="left" style="width: 100px"><input type="checkbox"
						name="chk_all" onclick="checkedAll()" />Select All</th>
					<th>S.No</th>
					<th>Name</th>
					<th>Description</th>
					<th>Duration</th>
					<th>Edit</th>
				</tr>
				<%
					CourseModel model = new CourseModel();
						int pageNo = ServletUtility.getPageNo(request);
						int pageSize = ServletUtility.getPageSize(request);
						int index = ((pageNo - 1) * pageSize) + 1;

						Iterator<CourseBean> it = list.iterator();

						while (it.hasNext()) {

							bean = it.next();
				%>
				<tr align="center">
					<td align="left"><input type="checkbox"
						id="sid" name="chk_1" onchange="check()" value="<%=bean.getId()%>"></td>
					<td><%=index++%></td>
					<td><%=bean.getName()%></td>
					<td><%=bean.getDescription()%></td>
					<td><%=bean.getDuration()%></td>
					<%
						if (userBean.getRoleId() == 1) {
					%>
					<td><a href="CourseCtl?id=<%=bean.getId()%>">Edit</a></td>
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
					<td>
						<%if(pageNo>1){ %> <input type="submit" name="operation"
						value="<%=CourseListCtl.OP_PREVIOUS%>"> <%}else{ %><input
						type="submit" name="operation" disabled="disabled"
						value="<%=CourseListCtl.OP_PREVIOUS%>"> <%} %>
					</td>
					<td><input type="submit" name="operation"
						value="<%=CourseListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation"
						value="<%=CourseListCtl.OP_DELETE %>"></td>
					<td align="right">
						<%if(pageSize>list.size() || model.nextPK()-1 == bean.getId()){ %> <input
						type="submit" name="operation" disabled="disabled"
						value="<%=CourseListCtl.OP_NEXT%>"> <%}else{ %> <input
						type="submit" name="operation" value="<%=CourseListCtl.OP_NEXT%>">
						<%} %>
					</td>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
			<%}else{ %>
			<input type="submit" value="<%=CourseListCtl.OP_BACK %>">
			<% }%>
		</form>

	</center>
<br><br><br><br>
	<%@include file="Footer.jsp"%>
</body>

</html>