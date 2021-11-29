<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.proj4.controller.LoginCtl"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page errorPage="ErrorView.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login  View</title>
<style type="text/css">
input[type=text]{
width: 200px;
padding: 3px;
}
input[type=password]{
width: 200px;
padding: 3px;
}
</style>
</head>
<body>
<form action="<%=ORSView.LOGIN_CTL%>" method="post" >
        <%@ include file="Header.jsp"%>

        <jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.UserBean"  scope="request"></jsp:useBean>

        <center>
            <h1>Login form</h1>

            <H2>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
 <font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
            </H2>
              
              <input type="hidden" name="id" value="<%=bean.getId()%>">
              <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
              <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
              <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
              <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

            <table align="center"  style="margin-left: 37%;">
                <tr>
                    <th>LoginId<font color="red">*</font></th>
                    <td>
                      <input type="text" name="login"  placeholder="Enter Your Email Id"
                        value="<%=DataUtility.getStringData(bean.getLogin())%>" >
                      <font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font>
                 </td>
                </tr>
                <tr>
                    <th>Password<font color="red">*</font></th>
                    <td><input type="password" name="password"  placeholder="Enter Your Password"
                        value="<%=DataUtility.getStringData(bean.getPassword())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
                </tr>
                <tr>
                    <th></th>
                    <td colspan="2">
                    <input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_IN %>"  style="width: 93px; padding-left: 2%;"> 
                    &emsp; 
                    <input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_UP %>"   style="width: 93px; padding-left: 2%;">
                     &nbsp;
                     </td>
                </tr>
                <tr><th></th>
                <td><a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b>Forget my password</b></a></td>
            </tr>
            </table>
           <input type="hidden" name="uri" value="<%=request.getAttribute("URI")%>">
    </center>
    </form>
    <br><br><br><br>
    <%@ include file="Footer.jsp"%>
</body>
</html>