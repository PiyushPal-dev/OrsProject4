<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.proj4.controller.GetMarksheetCtl"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page errorPage="ErrorView.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GetMarksheet View</title>
</head>
<body>
<form action="<%=ORSView.GET_MARKSHEET_CTL%>" method="post">
  <%@ include file="Header.jsp"%>

    <jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.MarksheetBean"
        scope="request"></jsp:useBean>

    <center>
            <h1>Get Marksheet</h1>
             <H2>
            <font color="red"> <%=ServletUtility.getErrorMessage(request)%> </font>
            </H2>
            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <table align="center" style="margin-left: 38%;">
            <tr>
             <th align="left">Rollno.<font style="color: red">*</font></th>
            <td>&nbsp;
             <input type="text" name="rollNo" value="<%=ServletUtility.getParameter("rollNo", request)%>" 
                 placeholder="Enter Student RollNo.">
                 &emsp;
             <input type="submit" name="operation" value="<%=GetMarksheetCtl.OP_GO%>">
             &emsp;
            <font color="red"> <%=ServletUtility.getErrorMessage("rollNo", request) %></font>
               </td>
                  </tr>
               </table>
                <br><br>
                <%
                    if (bean.getRollNo() != null && bean.getRollNo().trim().length() > 0) {
                %>
                 <table border="1"width="65%" >
                <tr>
                    <td ><b>Rollno :</b>
                    <%=DataUtility.getStringData(bean.getRollNo())%><br>
                             <b>Name :</b>
                    <%=DataUtility.getStringData(bean.getName())%>
                   </td>
                </tr>
                
                <tr>
                <th align="left">Subjects</th>
                <th  align="left">Marks</th>
                </tr>
                
                <tr>
                    <td>Physics :</td>
                    <td><%=bean.getPhysics()>32?bean.getPhysics():bean.getPhysics()+"<font color='red'>*</font>"%></td>
                </tr>
                <tr>
                    <td>Chemistry :</td>
                    <td><%=bean.getChemistry()>32?bean.getChemistry():bean.getChemistry()+"<font color='red'>*</font>"%></td>
                </tr>
                <tr>
                    <td>Maths :</td>
                    <td><%=bean.getMaths()>32?bean.getMaths():bean.getMaths()+"<font color='red'>*</font>"%></td>
                 </tr>
                <tr>
                    </td>
                </tr>
                <tr>
                <th align="left">Total Marks</th>
                <td><%=bean.getMaths()+bean.getChemistry()+bean.getPhysics() %>/300</td>
                </tr>
                <tr>
                <th align="left">Result</th>
                <td><%=bean.getMaths()>32&&bean.getChemistry()>32&&bean.getPhysics()>32?"PASSED":"FAILED" %></td>
                </tr>
            </table>
            <%
                }
            %>
       </center>
       </form>
        <%@ include file="Footer.jsp"%>
</body>
</html>