<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.controller.CollegeCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.proj4.controller.CollegeListCtl"%>
<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.bean.CollegeBean"%>
<%@page import="in.co.sunrays.proj4.model.CollegeModel"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page errorPage="ErrorView.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CollegeList View</title>

<style type="text/css">
input[type=submit]{
width: 100px;
padding: 2px;
}

input[type=text]{
width: 190px;
padding: 3px;
}
select{
width: 180px;
padding: 3px;
}
</style>
<script type="text/javascript">
function checkedAll()
{
var totalElement=document.forms[0].elements.length;
for(var i=0; i<totalElement;i++){
	var en=document.forms[0].elements[i].name;
	if(en!=undefined & en.indexOf("chk_1") !=-1){
		document.forms[0].elements[i].checked=document.frm.chk_all.checked;
	}
  }
}
function check(frm) {
	var en=document.forms[0].elements[5].name;
	if(en!=undefined & en.indexOf("chk_") !=-1){
		document.forms[0].elements[5].checked=document.frm.chk_all.unchecked;
	}
}
</script>
</head>
<body>

	<%@include file="Header.jsp"%>
	<center>
	<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.CollegeBean"
			scope="request"></jsp:useBean>
		<h1>College List</h1>
			<h2>
				<font style="color:red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h2>
			<h2><font style="color:green" ><%=ServletUtility.getSuccessMessage(request) %></font></h2>
		<form action="<%=ORSView.COLLEGE_LIST_CTL%>" name="frm" method="POST">

			<%List list = ServletUtility.getList(request);
		List	clist=(List)request.getAttribute("cList");
			%>
			<% 
	   UserBean uBean=(UserBean)session.getAttribute("user");
                if(list.size()>0){
                       %>
			<table width="100%">
				<tr>
					<td align="center">
					<label><b>College_Name :</b></label>
			<%=HTMLUtility.getList("collagetName", String.valueOf(bean.getId()), clist) %>
					<label style="padding-left: 2%"><b> Name :</b></label> <input type="text"
						name="name"
						value="<%=ServletUtility.getParameter("name", request)%>" placeholder="Enter College Name">
						<label style="padding-left: 3%"><b>City :</b></label> <input type="text" name="city"
						value="<%=ServletUtility.getParameter("city", request)%>" placeholder="Enter College City">&emsp;&emsp;
						<input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_SEARCH%>">&emsp;&emsp;
						<input type="submit" name="operation"  value="<%=CollegeListCtl.OP_RESET%>">
						</td>
				</tr>
			</table>
			<br>
			<table border="1" width="100%">
				<tr>
					<th align="left" style="width: 100px;"><input type="checkbox"
						name="chk_all" onclick="checkedAll(this)">Select All</th>
					<th>S.No.</th>
					<th>Name.</th>
					<th>Address.</th>
					<th>State.</th>
					<th>City.</th>
					<th>PhoneNo.</th>
					<th>Edit</th>
				</tr>

				<%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;
                   CollegeModel model=new CollegeModel();
                   // List list = ServletUtility.getList(request);
                    Iterator<CollegeBean> it = list.iterator();

                    while (it.hasNext()) {

                         bean = it.next();
                %>
				<tr align="center">
					<td style="width: 100px;" align="left"><input type="checkbox" id="ids"
						name="chk_1" onchange="check()" value="<%=bean.getId() %>"></td>
					<td><%=index++%></td>
					<td><%=bean.getName()%></td>
					<td><%=bean.getAddress()%></td>
					<td><%=bean.getState()%></td>
					<td><%=bean.getCity()%></td>
					<td><%=bean.getPhoneNo()%></td>
					<td><a href="CollegeCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
                    }
                %>
			</table>
			<table width="100%">
				<tr>
					<td>
						<%if(pageNo>1){ %> <input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_PREVIOUS%>"> <%}else{ %><input
						type="submit" name="operation" disabled="disabled"
						value="<%=CollegeListCtl.OP_PREVIOUS%>"> <%} %>
					</td>
					<td><input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_DELETE %>"></td>
					<td align="right">
						<%if(pageSize>list.size() || model.nextPK()-1 == bean.getId()){ %> <input
						type="submit" name="operation" disabled="disabled"
						value="<%=CollegeListCtl.OP_NEXT%>"> <%}else{ %> <input
						type="submit" name="operation" value="<%=CollegeListCtl.OP_NEXT%>">
						<%} %>
					</td>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
			<%}else{ %>
			<input type="submit" value="<%=CollegeListCtl.OP_BACK %>">
			<% }%>
		</form>

	</center>
		<br><br><br>
	<%@include file="Footer.jsp"%>
</body>
</html>