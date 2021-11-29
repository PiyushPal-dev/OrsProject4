<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.proj4.controller.ForgetPasswordCtl"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page errorPage="ErrorView.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ForgetPassword View</title>
</head>
<body>
   <form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="post">

        <%@ include file="Header.jsp"%>

        <jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.UserBean"
            scope="request"></jsp:useBean>

        <center>
        <h1>Forgot your password?</h1>
            <H2>
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>
        
            <input type="hidden" name="id" value="<%=bean.getId()%>">
             <h3><lable>Submit your email address and we'll send you password.</lable></h3>
              <H2>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>
            <table align="center" style="margin-left: 37%;">
            <tr><th align="left">Email Id<font style="color: red">*</font></th>
                <td>&emsp;
                <input type="text" name="login" placeholder="Enter ID Here"
                    value="<%=DataUtility.getStringData(bean.getLogin())%>">&emsp;
                    <input type="submit" name="operation" value="<%=ForgetPasswordCtl.OP_GO%>">&emsp;
                <font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font>
                  </td>
                  </tr>
            </table>
            
    </form>
    </center>
    <%@ include file="Footer.jsp"%>

</body>
</html>