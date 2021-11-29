<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.proj4.controller.MarksheetListCtl"%>
<%@page import="in.co.sunrays.proj4.controller.BaseCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.bean.MarksheetBean"%>
<%@page import="in.co.sunrays.proj4.model.MarksheetModel"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ page errorPage="ErrorView.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MarksheetList View</title>
<style type="text/css">
input[type=submit]{
width: 100px;
padding: 2px;
}
input[type=text]{
width:190px;
padding:3px;
}
select{
width:160px;
padding:2px;
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
		
		<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.MarksheetBean" scope="request"></jsp:useBean>
		<form action="<%=ORSView.MARKSHEET_LIST_CTL%>" name="frm" method="POST">
		<h1>Marksheet List</h1>
			<h2>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			   <font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
			</h2>
			<%UserBean uBean=(UserBean)session.getAttribute("user"); %>
			<%List sList=(List)request.getAttribute("sList"); %>
			<%List list=ServletUtility.getList(request); %>	
			<%if(list.size()!=0){ %>	
			<table>
			<tr>
			<td><label><b>Student Name :</b></label>
			<%=HTMLUtility.getList("studentName", String.valueOf(bean.getStudentId()), sList) %>
					<label> <b>Name :</b></label>
					 <input type="text"name="name" value="<%=ServletUtility.getParameter("name", request)%>"  placeholder="Enter Name">
						&emsp; 
						<label><b>RollNo :</b></label> 
						<input type="text" name="rollNo" value="<%=ServletUtility.getParameter("rollNo", request)%>"  placeholder="Enter RollNo">&emsp;&emsp;
					<input type="submit" name="operation" value="<%=MarksheetListCtl.OP_SEARCH%>">&emsp;&emsp;
					<input type="submit" name="operation" value="<%=MarksheetListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>
			<table border="1" width="100%">
				<tr>
				<%if(uBean.getRoleId()==RoleBean.ADMIN) {%>
					<th align="left" style="width: 100px;"><input type="checkbox"
						name="chk_all" onclick="checkedAll(this)"> Select All</th>
						<%} %>
					<th>S.No.</th>
					<th>RollNo</th>
					<th>Name</th>
					<th>Physics</th>
					<th>Chemistry</th>
					<th>Maths</th>
					<%if(uBean.getRoleId()==RoleBean.ADMIN) {%>
					<th>Edit</th>
					<%} %>
				</tr>

				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize)+1;
					MarksheetModel model = new MarksheetModel();
					
					Iterator<MarksheetBean> it = list.iterator();
                    while (it.hasNext()) {
                               bean = it.next();
                    	 %>
				<tr align="center">
				<%if(uBean.getRoleId()==RoleBean.ADMIN) {%>
					<td align="left"><input  type="checkbox" name="chk_1" id="ids"
						onchange="check()"  value="<%=bean.getId()%>" ></td>
						<%} %>
					<td><%=index++%></td>
					<td><%=bean.getRollNo()%></td>
					<td><%=bean.getName()%></td>
					<td><%=bean.getPhysics()%></td>
					<td><%=bean.getChemistry()%></td>
					<td><%=bean.getMaths()%></td>
					<%if(uBean.getRoleId()==RoleBean.ADMIN) {%>
					<td><a href="MarksheetCtl?id=<%=bean.getId()%>">Edit</a></td>
					<%} %>
									</tr>

				<%
					}
				%>
			</table><br>
			<table width="100%">
				<tr>
					<%
						if (pageNo > 1) {
					%>
					<td><input type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation" disabled="disabled"
						value="<%=MarksheetListCtl.OP_PREVIOUS%>"></td>

					<%
						}
					%>
					<td><input type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_NEW%>"></td>
					<td align="center"><input type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_DELETE%>"></td>
					<%
						if (pageSize > list.size() || model.nextPK() - 1 == bean.getId()) {
					%>
					<td align="right"><input type="submit" name="operation"
						disabled="disabled" value="<%=MarksheetListCtl.OP_NEXT%>"></td>
					<%
						} else {
					%>
					<td align="right"><input type="submit" name="operation"
						value="<%=MarksheetListCtl.OP_NEXT%>"></td>
					<%
						}
					%>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
				<%}else{ %>
				<input type="submit" name="operation"  value="<%=MarksheetListCtl.OP_BACK%>">
				<%} %>
		</form>
	</center>
	<br><br><br><br>
	<%@include file="Footer.jsp"%>
</body>
</html>