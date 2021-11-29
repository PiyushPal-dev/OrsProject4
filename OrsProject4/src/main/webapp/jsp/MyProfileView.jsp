<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.proj4.controller.MyProfileCtl"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page errorPage="ErrorView.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MyProfile View</title>

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
width:190px;
padding: 3px;
}
select{
width:200px;
padding: 3px;
}
</style>
</head>
<body>
<form action="<%=ORSView.MY_PROFILE_CTL%>" method="post">

        <%@ include file="Header.jsp"%>
        <jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.UserBean"
            scope="request"></jsp:useBean>

        <center>
            <h1>My Profile</h1>
            
            <H2>
             <font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>
            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
            

            <table  align="center"  style="margin-left:35%;">
                <tr>
                    <th align="left">LoginId<font style="color: red">*</font></th>
                    <td><input type="text" name="login"
                        value="<%=DataUtility.getStringData(bean.getLogin())%>"readonly="readonly"><font
                        color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
                </tr>

                <tr>
                    <th align="left">First Name<font style="color: red">*</font></th>
                    <td><input type="text" name="firstName"
                        value="<%=DataUtility.getStringData(bean.getFirstName())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
                </tr>
                <tr>
                    <th align="left">Last Name<font style="color: red">*</font></th>
                    <td><input type="text" name="lastName"
                        value="<%=DataUtility.getStringData(bean.getLastName())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
                </tr>
                <tr>
                    <th align="left">Gender<font style="color: red">*</font></th>
                    <td>
                        <%
                            HashMap map = new HashMap();
                            map.put("M", "Male");
                            map.put("F", "Female");

                            String htmlList = HTMLUtility.getList("gender", bean.getGender(),
                                    map);
                        %> <%=htmlList%>
                        <font style="color:red"><%=ServletUtility.getErrorMessage("gender", request) %></font>
                    </td>
                </tr>
                <tr>
                    <th align="left">Mobile No<font style="color: red">*</font></th>
                    <td><input type="text" name="mobileNo"
                        value="<%=DataUtility.getStringData(bean.getMobileNo())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
                </tr>

                <tr>
                    <th align="left">Date Of Birth <font style="color: red">*</font></th>
                    <td><input type="text" name="dob"  id="datepicker"  readonly="readonly" 
                        value="<%=DataUtility.getDateString(bean.getDob())%>"  placeholder="mm/dd/yyyy">
                    <font
                        color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
                </tr>
                
                <tr>
                    <th></th>
                    <td colspan="2"><input type="submit" name="operation"
                        value="<%=MyProfileCtl.OP_CHANGE_MY_PASSWORD %>"> &nbsp;
                         <input type="submit"  name="operation" value="<%=MyProfileCtl.OP_SAVE %>"  style="width: 67px;"> &nbsp; &emsp;</td>
                </tr>
            </table>
    </form>
    </center>
    <%@ include file="Footer.jsp"%>
</body>
</html>