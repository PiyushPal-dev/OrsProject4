
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page errorPage="ErrorView.jsp" %>
<%@page import="in.co.sunrays.proj4.bean.RoleBean"%>
<%@page import="in.co.sunrays.proj4.controller.LoginCtl"%>
<%@page import="in.co.sunrays.proj4.bean.UserBean"%>
<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%
	UserBean userBean = (UserBean) session.getAttribute("user");
	String welcomeMsg = "Hi, ";
	if (userBean!=null) {
		 String role = (String) session.getAttribute("role");
		welcomeMsg += userBean.getFirstName() + " (" + role + ")";
	} else {
		welcomeMsg += "( Guest )";
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Header View</title>

</head>
<body>
	<table width="100%" border="0" >	
	<tr>
			<td><a href="<%=ORSView.WELCOME_CTL%>" ><b>Welcome</b></a>
				&nbsp;&nbsp; <%
				if (userBean!=null) {
			%> <a href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>"><b>Logout</b></a>
                 <%
					} else {
				%> <a href="<%=ORSView.LOGIN_CTL%>"><b>Login</b></a> <%
 	}
 %></td>
			<td rowspan="2">
				<h1 align="Right">
					<img src="<%=ORSView.APP_CONTEXT%>/img/customLogo.jpg" width="200" height="50">
				</h1>
			</td>
		</tr>

		<tr>
			<td>
				<h3><%=welcomeMsg%></h3>
			</td>
		</tr>


		<%
			if (userBean!=null) {
		%>

		<tr>
			<td colspan="2" >
			 <a href="<%=ORSView.MY_PROFILE_CTL%>"><b>MyProfile</b></a>
			  &emsp;<a href="<%=ORSView.CHANGE_PASSWORD_CTL%>"><b>ChangePassword</b></a>&emsp;
			  
						<%if(userBean.getRoleId() ==RoleBean.STUDENT){ %>
						<a href="<%=ORSView.GET_MARKSHEET_CTL%>"><b>GetMarksheet</b></a>
						<% }%>
						
						<%if(userBean.getRoleId() == RoleBean.KIOSK){ %>
						<a href="<%=ORSView.MARKSHEET_LIST_CTL%>"><b>MarksheetList</b></a>
						<%} %>
						
						<%if(userBean.getRoleId() == RoleBean.COLLEGE_SCHOOL){ %>
						<a href="<%=ORSView.STUDENT_CTL%>"><b>AddStudent</b></a> 
				        &emsp;&emsp;<a href="<%=ORSView.STUDENT_LIST_CTL%>"><b>StudentList</b></a>
				        &emsp; &nbsp;  <a href="<%=ORSView.FACULTY_CTL%>"><b>AddFaculty</b></a> 
				          &emsp; &nbsp;<a href="<%=ORSView.FACULTY_LIST_CTL%>"><b>FacultyList</b></a>
				        &emsp; &nbsp;<a href="<%=ORSView.GET_MARKSHEET_CTL%>"><b>GetMarksheet</b></a>
				        &emsp;&emsp;<a href="<%=ORSView.MARKSHEET_LIST_CTL%>"><b>MarksheetList</b></a>
				        &emsp;&emsp;<a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><b>MarksheetMerit List</b> </a>
						<%} %>
				 <%
 	               if (userBean.getRoleId() == RoleBean.ADMIN) {
                  %> 
                &nbsp;<a href="<%=ORSView.GET_MARKSHEET_CTL%>"><b>GetMarksheet</b></a>&emsp;
                &nbsp;<a href="<%=ORSView.MARKSHEET_CTL%>"><b>AddMarksheet</b></a> &emsp;
				&nbsp;<a href="<%=ORSView.MARKSHEET_LIST_CTL%>"><b>MarksheetList</b></a> &emsp;
				&nbsp;<a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><b>MarksheetMerit List</b> </a>&emsp;
			    &nbsp;<a href="<%=ORSView.USER_CTL%>"><b>AddUser</b></a>&emsp;
				&nbsp;<a href="<%=ORSView.USER_LIST_CTL%>"><b>UserList</b></a>&emsp;
				&nbsp;<a href="<%=ORSView.FACULTY_CTL%>"><b>AddFaculty</b></a> &emsp;
				&nbsp;<a href="<%=ORSView.FACULTY_LIST_CTL%>"><b>FacultyList</b></a>&emsp;
				&nbsp;<a href="<%=ORSView.COURSE_CTL%>"><b>AddCourse</b></a>&emsp;
				<br><br>
				&nbsp;<a href="<%=ORSView.COURSE_LIST_CTL%>"><b>CourseList</b></a>&emsp;
				&nbsp;<a href="<%=ORSView.SUBJECT_CTL%>"><b>AddSubject</b></a>&emsp;
				&nbsp;<a href="<%=ORSView.SUBJECT_LIST_CTL%>"><b>SubjectList</b></a>&emsp;
				&nbsp;<a href="<%=ORSView.COLLEGE_CTL%>"><b>AddCollege</b></a> &emsp;
				&nbsp;<a href="<%=ORSView.COLLEGE_LIST_CTL%>"><b>CollegeList</b></a>  &emsp;
				&nbsp;<a href="<%=ORSView.STUDENT_CTL%>"><b>AddStudent</b></a> &emsp;
				&nbsp;<a href="<%=ORSView.STUDENT_LIST_CTL%>"><b>StudentList</b></a> &emsp;
				&nbsp;<a href="<%=ORSView.ROLE_CTL%>"><b>AddRole</b></a> &emsp;
				&nbsp;<a href="<%=ORSView.ROLE_LIST_CTL%>"><b>RoleList</b></a>&emsp;
				&nbsp;<a href="<%=ORSView.TIME_TABLE_CTL%>"><b>AddTimeTable</b></a>&emsp;
				&nbsp;<a href="<%=ORSView.TIME_TABLE_LIST_CTL%>"><b>TimeTableList</b></a> &emsp;
				&nbsp;<a href="<%=ORSView.JAVA_DOC_VIEW%>" target="blanck"><b>JavaDoc</b></a>  
				<%
 	}
 %></td>

		</tr>
		<%
			}
		%>
	</table>
	<HR >
</body>
</html>