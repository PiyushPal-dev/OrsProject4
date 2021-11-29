<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.proj4.controller.RoleListCtl"%>
<%@page import="in.co.sunrays.proj4.controller.BaseCtl"%>
<%@page import="in.co.sunrays.proj4.bean.RoleBean"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.model.RoleModel" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ page errorPage="ErrorView.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RoleList View</title>
<style type="text/css">
input[type=submit]{
width:100px;
padding:2px;
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
	var en=document.forms[0].elements[4].name;
	if(en!=undefined & en.indexOf("chk_") !=-1){
		document.forms[0].elements[4].checked=document.frm.chk_all.unchecked;
	}
}
</script>
</head>
<body>

    <%@include file="Header.jsp"%>

    <center>
        <h1>Role List</h1>
<jsp:useBean id="bean"  class="in.co.sunrays.proj4.bean.RoleBean" scope="request"></jsp:useBean>
        <form action="<%=ORSView.ROLE_LIST_CTL%>"  name="frm"   method="post">
        <h2><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font></h2>
          <h2><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h2>
           <%List list = ServletUtility.getList(request); 
           List	rList=(List)request.getAttribute("rList");
           %>
           
           <% if(list.size()>0){ %>
            <table width="100%"  style="margin-left: 20%;">
                <tr>
                    <td>
                    <label><b>Role :</b></label>
			<%=HTMLUtility.getList("role", String.valueOf(bean.getId()), rList) %>
                    <label><b>Name :</b></label> <input type="text"
                        name="name"
                        value="<%=ServletUtility.getParameter("name", request)%>"  placeholder="Enter Name">&emsp;&emsp;
                        <input type="submit" name="operation"  value="<%=RoleListCtl.OP_SEARCH %>">&emsp;&emsp;&emsp;
                        <input type="submit" name="operation" value="<%=RoleListCtl.OP_RESET%>">
                    </td>
                </tr>
            </table>
            <table border="1" width="100%">
                <tr>
                <th align="left" style="width: 100px"><input type="checkbox"  name="chk_all" onclick="checkedAll(this)">Select All</th>
                    <th>S.No.</th>
                    <th>Name</th>
                    <th>Descriptiop</th>
                    <th>Edit</th>
                </tr>

                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;
                     RoleModel model=new RoleModel();
                    
                    Iterator<RoleBean> it = list.iterator();
                    while (it.hasNext()) {
                       bean = it.next();
                %>
                <tr align="center">
                    <td align="left"><input type="checkbox" id="ids" name="chk_1" onclick="check()" value="<%=bean.getId() %>"> </td>
                    <td><%=index++%></td>
                    <td><%=bean.getName()%></td>
                    <td><%=bean.getDescription()%></td>
                    <td><a href="RoleCtl?id=<%=bean.getId()%>">Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>
            <table width="100%">
                <tr>
                <%if(pageNo>1){ %>
                    <td><input type="submit" name="operation" 
                        value="<%=RoleListCtl.OP_PREVIOUS%>"></td>
                        <% }else{%>
                        <td><input type="submit" name="operation"   disabled="disabled" value="<%=RoleListCtl.OP_PREVIOUS%>"></td>
                        <%} %>
                        <td><input type="submit" name="operation" value="<%=RoleListCtl.OP_NEW%>"></td>
                    <td style="padding-left: 15%;"> <input type="submit"  name="operation" value="<%=RoleListCtl.OP_DELETE %>"> </td>
                    <%if(pageSize>list.size()  || model.nextPK()-1  ==  bean.getId()){ %>
                    <td align="right"><input type="submit" name="operation" disabled="disabled" 
                        value="<%=RoleListCtl.OP_NEXT%>"></td>
                        <%}else{ %>
                        <td><input type="submit" name="operation"  value="<%=RoleListCtl.OP_NEXT%>"></td>
                        <%} %>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
                type="hidden" name="pageSize" value="<%=pageSize%>">
<%}else{ %>
<input type="submit"  name="operation" value="<%=RoleListCtl.OP_BACK%>">
<%} %>
        </form>
    </center>
    <%@include file="Footer.jsp"%>
</body>
</html>