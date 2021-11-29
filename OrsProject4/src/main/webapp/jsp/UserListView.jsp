<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.controller.UserCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.proj4.controller.UserListCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.model.UserModel"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ page errorPage="ErrorView.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UserList View</title>
<style type="text/css">
input[type=text]{
width: 150px;
padding: 3px;
}
input[type=submit] {
	width: 100px;
	padding-left: 2px;
}
select{
width: 150px;
padding: 3px;
}
</style>
<script type="text/javascript">
	function checkedAll() {
		var totalElement = document.forms[0].elements.length;
		//console.log(totalElement+".. its totle element")
		for (var i = 0; i < totalElement; i++) {
			var en = document.forms[0].elements[i].name;
		//	console.log(en+"  var en... "+i+"...I count")
			if (en != undefined & en.indexOf("chk_1") != -1) {
				//console.log(document.forms[0].elements[i].checked+"iiiii...............................")
				document.forms[0].elements[i].checked = document.frm.chk_all.checked;
			}
		}
	}
	function check(frm) {
		var en = document.forms[0].elements[5].name;
		if (en != undefined & en.indexOf("chk_") != -1) {
			//console.log("its check function..."+en+"...");
			//console.log("......"+document.forms[0].elements[4].checked)
			document.forms[0].elements[5].checked = document.frm.chk_all.unchecked;
		}
	}
</script>
</head>
<body>

	<%@include file="Header.jsp"%>

	<center>
		<h1>User List</h1>
		<h2>
			<font style="color: green"><%=ServletUtility.getSuccessMessage(request)%></font>
			<font style="color: red"><%=ServletUtility.getErrorMessage(request)%></font>
		</h2>
	
		<form action="<%=ORSView.USER_LIST_CTL%>" name="frm" method="post">
	<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.UserBean"
			scope="request"></jsp:useBean>
			<%
		UserBean	uBean=(UserBean)session.getAttribute("user");
				
	      	List rList=(List)request.getAttribute("rList"); 
	      	List list = ServletUtility.getList(request);
				if (list.size() > 0) {
			%>
			<table width="100%">
				<tr>
					<td align="center">
					<label><b>Role :</b></label>
			<%=HTMLUtility.getList("role", String.valueOf(bean.getRoleId()), rList) %>
					<label style="padding-left: 2%;"><b>FirstName:</b></label> 
							<input type="text" name="firstName"
						value="<%=ServletUtility.getParameter("firstName", request)%>"
						placeholder="Enter FirstName"> &emsp; 
						<label style="padding-left: 3%;"><b>LoginId:</b></label>
						 <input type="text" name="login" value="<%=ServletUtility.getParameter("login", request)%>"
						    placeholder="Enter LastName"> &emsp; 
						
						<input type="submit" name="operation" value="<%=UserListCtl.OP_SEARCH%>">&emsp;&emsp;
						<input type="submit" name="operation" value="<%=UserListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<table border="1" width="100%" >
				<tr>
					<th align="left" style="width: 100px"><input type="checkbox"
						name="chk_all" onclick="checkedAll(this)">Select All</th>
					<th>S.No.</th>
					<th>FirstName</th>
					<th>LastName</th>
					<th>Role</th>
					<th>LoginId</th>
					<th>Gender</th>
					<th>DOB</th>
					
					<th >Edit</th>
				</tr>

				<%
					int pageNo = ServletUtility.getPageNo(request);
						int pageSize = ServletUtility.getPageSize(request);
						int index = ((pageNo - 1) * pageSize) + 1;
						UserModel model = new UserModel();

						Iterator<UserBean> it = list.iterator();
						while (it.hasNext()) {
							bean = it.next();
				%>
				<tr align="center">
					<td align="left"><input type="checkbox" id="ids" name="chk_1"
						onchange="check()" value="<%=bean.getId()%>"  <%if(bean.getRoleId()==RoleBean.ADMIN){ %> <%="disabled" %><%} %>></td>
					<td><%=index++%></td>
					<td><%=bean.getFirstName()%></td>
					<td><%=bean.getLastName()%></td>
					<td><%=bean.getRoleName()%></td>
					<td><%=bean.getLogin()%></td>
					<td><%=bean.getGender()%></td>
					<td><%=bean.getDob()%></td>
					
					<td> <a href="UserCtl?id=<%=bean.getId()%>" <%if(bean.getRoleId()==RoleBean.ADMIN) {%>onclick="return false;"<%} %>>Edit</a></td>
			
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
						value="<%=UserListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation" disabled="disabled"
						value="<%=UserListCtl.OP_PREVIOUS%>"></td>
					<%
						}
					%>
					<td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEW%>"></td>
					<td style="padding-left: 10%;"><input type="submit" name="operation" value="<%=UserListCtl.OP_DELETE%>"></td>
					<%
						if (pageSize > list.size() || model.nextPK() - 1 == bean.getId()) {
					%>
					<td align="right"><input type="submit" name="operation"
						disabled="disabled" value="<%=UserListCtl.OP_NEXT%>"></td>
					<%
						} else {
					%>
					<td align="right"><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEXT%>"></td>
					<%
						}
					%>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
			<%
				} else {
			%>
			<input type="submit" name="operation"
				value="<%=UserListCtl.OP_BACK%>">
			<%
				}
			%>
		</form>
	</center><br><br><br>
	<%@include file="Footer.jsp"%>
</body>
</html>