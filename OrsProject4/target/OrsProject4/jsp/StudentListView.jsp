<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.controller.StudentCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.proj4.controller.StudentListCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.bean.StudentBean"%>
<%@page import="in.co.sunrays.proj4.model.StudentModel"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ page errorPage="ErrorView.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>StudentList View</title>
<style type="text/css">
input[type=submit]{
width: 100px;
padding: 2px;
}
input[type=text]{
width:190px;
padding:3px;
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
	<center>
		<h1>Student List</h1>
		<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.StudentBean"
			scope="request"></jsp:useBean>
		<form action="<%=ORSView.STUDENT_LIST_CTL%>" name="frm" method="post">
		<h2><font style="color:green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>
			<h2>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h2>
			<%UserBean uBean=(UserBean)session.getAttribute("user"); %>
			<%List list = ServletUtility.getList(request);
		List	slist=(List)request.getAttribute("sList");
			if(list!=null && list.size()!=0){
			%>
			<table width="100%"  style="margin-left: 5%;">
				<tr>
					<td>
					<label><b>Student :</b></label>
			<%=HTMLUtility.getList("sName", String.valueOf(bean.getId()), slist) %>
					
					<label style="padding-left: 2%;"><b> First_Name :</b></label> 
					<input 	type="text" name="firstName" placeholder="Enter FirstName" 
					                value="<%=ServletUtility.getParameter("firstName", request)%>" >
					
						<label style="padding-left: 3%;" ><b>Email_Id:</b></label> 
							<input type="text" name="email" placeholder="Enter EmailId"
						value="<%=ServletUtility.getParameter("email", request)%>" > &emsp;&emsp;
						<input type="submit" name="operation"  
						value="<%=StudentListCtl.OP_SEARCH%>" >&emsp;&emsp;
						<input type="submit"  name="operation" value="<%=StudentListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>
			<table border="1" width="100%">
				<tr>
					<th align="left" style="width: 100px"><input type="checkbox"
						name="chk_all" onclick="checkedAll(this)">Select All</th>
					<th>S.No.</th>
					<th>First Name.</th>
					<th>Last Name.</th>
					<th>Date Of Birth.</th>
					<th>Mobil No.</th>
					<th>Email ID.</th>
					<th>Edit</th>
				</tr>
				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;
					StudentModel model = new StudentModel();
					
					Iterator<StudentBean> it = list.iterator();

					while (it.hasNext()) {

						bean = it.next();
				%>
				<tr align="center">
					<td align="left"><input type="checkbox" id="ids" name="chk_1"
						onchange="check()" value="<%=bean.getId()%>" <%-- <%if(uBean.getRoleId()!=RoleBean.ADMIN){ %> <%="disabled" %><%} %> --%>></td>
					<td><%=index++%></td>
					<td><%=bean.getFirstName()%></td>
					<td><%=bean.getLastName()%></td>
					<td><%=bean.getDob()%></td>
					<td><%=bean.getMobileNo()%></td>
					<td><%=bean.getEmail()%></td>
					<td><a href="StudentCtl?id=<%=bean.getId()%>">Edit</a></td>
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
						value="<%=StudentListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation" disabled="disabled"
						value="<%=StudentListCtl.OP_PREVIOUS%>"></td>
						<%
						}
					%>
					<td ><input type="submit"  name="operation"  value="<%=StudentCtl.OP_NEW%>"></td>
					<td style="padding-left: 10%;"><input type="submit" name="operation"
						value="<%=StudentListCtl.OP_DELETE%>"></td>
					<%
						if (pageSize > list.size() || model.nextPK() - 1 == bean.getId()) {
					%>
					<td align="right"><input type="submit" name="operation"
						disabled="disabled" value="<%=StudentListCtl.OP_NEXT%>"></td>
					<%
						} else {
					%>
					<td align="Right"><input type="submit" name="operation"
						value="<%=StudentListCtl.OP_NEXT%>"></td>
					<%
						}
					%>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"><input
				type="hidden" name="pageSize" value="<%=pageSize%>">

<%}else{ %>
<input  align="center"  type="submit"  name="operation"  value="<%=StudentListCtl.OP_BACK%>">
<%} %>
		</form>
	</center>
		<br><br><br>
	<%@include file="Footer.jsp" %>
</body>
</html>