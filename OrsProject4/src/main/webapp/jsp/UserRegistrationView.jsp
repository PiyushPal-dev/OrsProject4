<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.proj4.controller.UserRegistrationCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page errorPage="ErrorView.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UserRegistrastion View</title>
  <link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script type="text/javascript">
	$(function() {
		$("#datepicker").datepicker({
			dateFormat : 'mm/dd/yy',
			defaultDate : "01/01/1982",
			changeMonth : true,
			changeYear : true,
			yearRange : '-35:-18' 
		});
	});
</script>
 <style type="text/css">
input[type=text]{
width: 190px;
padding: 3px;
}
input[type=password]{
width: 190px;
padding: 3px;

}
select{
width: 200px;
padding: 3px;
}
input[type=submit] {
	width:85px;
	padding:2px
}
</style>
</head>
<body>

 <form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post">
          <%@ include file="Header.jsp"%>
        <script type="text/javascript" src="./js/calendar.js"></script>
        <jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.UserBean" scope="request"></jsp:useBean>
      <center>
            <h1>User Registration</h1>
            <H2>
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%> </font>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
            </H2>

            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
            

            <table align="center"  style="margin-left: 32%;">

                <tr>
                    <th align="left">First Name<font style="color: red">*</font></th>
                    <td><input type="text" name="firstName" value="<%=DataUtility.getStringData(bean.getFirstName())%>" 
                        placeholder="Enter First Name">
                        <font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
                </tr>
                <tr>
                    <th align="left">Last Name<font style="color: red">*</font></th>
                    <td>
                    <input type="text" name="lastName"  value="<%=DataUtility.getStringData(bean.getLastName())%>" placeholder="Enter Last Name">
                        <font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
                </tr>
                <tr>
                    <th align="left">LoginId<font style="color: red">*</font></th>
                    <td><input type="text" name="login"
                        placeholder="Must be Email ID"
                        value="<%=DataUtility.getStringData(bean.getLogin())%>" placeholder="Enter LoginId">
                        <font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
                </tr>
                <tr>
                    <th align="left">Password<font style="color: red">*</font></th>
                    <td><input type="password" name="password"
                        value="<%=DataUtility.getStringData(bean.getPassword())%>" placeholder="Enter Password">
                        <font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
                </tr>
                <tr>
                    <th align="left">Confirm Password<font style="color: red">*</font></th>
                    <td><input type="password" name="confirmPassword"
                        value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>" placeholder="Enter ConfirmPassword">
                        <font color="red"> <%=ServletUtility .getErrorMessage("confirmPassword", request)%></font></td>
                </tr>
                <tr>
                    <th align="left">MobileNo<font style="color: red">*</font></th>
                    <td>
                    <input type="text" name="mobileNo"  value="<%=DataUtility.getStringData(bean.getMobileNo())%>" placeholder="Enter Mobile Number">
                        <font color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
                </tr>
                <tr>
                    <th align="left">Gender <font style="color: red">*</font></th>
                    <td>
                        <%
                            HashMap map = new HashMap();
                            map.put("M", "Male");
                            map.put("F", "Female");

                            String htmlList = HTMLUtility.getList("gender", bean.getGender(),map);
                        %> <%=htmlList%>
   <font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font>
                    </td>
                </tr>

                <tr>
                    <th align="left">Date Of Birth  <font style="color: red">*</font></th>
                    <td><input type="text" name="dob" id="datepicker" readonly="readonly"
                                    value="<%=DataUtility.getDateString(bean.getDob())%>"  placeholder="mm/dd/yyyy">
                    <font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
                </tr>
                <tr>
                    <th></th>
                    <td> 
                        <input type="submit" name="operation" value="<%=UserRegistrationCtl.OP_SIGN_UP %>">&emsp;&emsp;
                        <input type="submit"  name="operation" value="<%=UserRegistrationCtl.OP_RESET%>" > </td>
                </tr>
            </table>
    </form>
    </center>
    <%@ include file="Footer.jsp"%>
</body>
</html>