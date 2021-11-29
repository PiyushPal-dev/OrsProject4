<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.proj4.controller.MarksheetMeritListCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.bean.MarksheetBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ page errorPage="ErrorView.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MarksheetMeritList View</title>
<style type="text/css">
input[type=submit]{
width: 100px;
padding: 2px;
}
</style>
</head>
<body>
 <%@include file="Header.jsp"%>
    <center>
        <h1>Marksheet Merit List</h1>
        <h2><font style="color:red"><%=ServletUtility.getErrorMessage(request) %></font></h2>
            <%List list = ServletUtility.getList(request);
                  if(list.size()!=0){
            %>
        <form action="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>" method="POST">
            <br>
            <table border="1" width="100%">
                <tr>
                    <th>S.No.</th>
                    <th>Roll No</th>
                    <th>Name</th>
                    <th>Physics</th>
                    <th>Chemistry</th>
                    <th>Maths</th>
                </tr>
                <%
                    int pageNo =ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request); 
                    int index = ((pageNo - 1) * pageSize) + 1;
                    
                    list = ServletUtility.getList(request);
                    Iterator<MarksheetBean> it = list.iterator();
                    while (it.hasNext()) {
                  MarksheetBean bean = it.next();
                %>
                <tr align="center">
                    <td><%=index++%></td> 
                    <td><%=bean.getRollNo()%></td>
                    <td><%=bean.getName()%></td>
                    <td><%=bean.getPhysics()%></td>
                    <td><%=bean.getChemistry()%></td>
                    <td><%=bean.getMaths()%></td>
                </tr>
                <%
                    }
                %>
            </table>
            <table>
                <tr>
                    <td align="right"><input type="submit" name="operation"
                        value="<%=MarksheetMeritListCtl.OP_BACK%>"></td>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>">  <input
                type="hidden" name="pageSize" value="<%=pageSize%>"> 
        </form>
               <%}else{ %>
                <table>
                <tr>
                    <td align="right"><input type="submit" name="operation"
                        value="<%=MarksheetMeritListCtl.OP_BACK%>"></td>
                </tr>
            </table>
               <%} %>
    </center>
    <br><br><br>
    <%@include file="Footer.jsp"%>
</body>
</html>