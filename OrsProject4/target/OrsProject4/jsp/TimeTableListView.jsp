<%@page import="in.co.sunrays.proj4.util.DataValidator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.model.TimeTableModel"%>
<%@page import="in.co.sunrays.proj4.controller.TimeTableCtl"%>
<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@page import="in.co.sunrays.proj4.controller.TimeTableListCtl"%>
<%@page import="in.co.sunrays.proj4.bean.TimeTableBean"%>
<%@page import="in.co.sunrays.proj4.bean.CourseBean"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="javax.servlet.ServletContext"%>
<%@page errorPage="ErrorView.jsp" %>
<html>
<head>
<style type="text/css">

</style>
<link rel="stylesheet" type="text/css" href="../js/jquery-ui.css"></link>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TimeTableList View</title>
<link rel="icon" type="image/Ico" href="img/logo.ico"  />
<script type="text/javascript">

function checkedAll()
{
	var totalElement=document.forms[0].elements.length;
	for(var i=0;i<totalElement;i++){
		var en=document.forms[0].elements[i].name;
		
		if(en!=undefined & en.indexOf("chk_")!=-1)
		{	
			document.forms[0].elements[i].checked=document.frm.chk_all.checked;
		 
		}		
	}
}
function check(){
	var en=document.forms[0].elements[7].name;
	if(en!=undefined & en.indexOf("chk_1")!=-1)
	{	
		document.forms[0].elements[6].checked=document.frm.chk_all.unchecked;
	}	
}
</script>

<script type="text/javascript">
/*  function sunday(date) {
var day=date.getDay();
if(day==0){
	return [false]
}else{
	return[true]
}
} */
 function noSunday(date){ 
    return [date.getDay() != 0, false];
 };

	  $(function() {
		
		$("#datepicker2").datepicker({
			//dateFormat : 'yyyy/MM/dd',
			minDate: "01/01/2019",
			//maxDate: "12/31/2020",
			changeMonth : true,
			changeYear : true,
			beforeShowDay: noSunday,
			yearRange : "2019:2020" //"-4:+6"
		});
	}); 
</script>
<style type="text/css">
input[type=text]{
width:170px;
padding:2px;
}
select{
width:160px;
padding:2px;
}
input[type=submit]{

width:80px;
padding:2px;
}
</style>
</head>
<body>
<%@ include file="Header.jsp"%>


<jsp:useBean id="cBean" class="in.co.sunrays.proj4.bean.CourseBean" scope="request"></jsp:useBean>
<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.TimeTableBean" scope="request"></jsp:useBean>
	
	<form action="<%=ORSView.TIME_TABLE_LIST_CTL%>" name="frm" method="post">
	
		<h1 align="center">Timetable List</h1>
		<center>
		<h2>
			<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
		</h2>
		
		<%List list = ServletUtility.getList(request);
		Integer i = (Integer)request.getAttribute("var");%>
	<%if(list!=null && list.size()!=0){%>
	<%List courseList = (List)request.getAttribute("courseList"); %>
		<table width="100%">

			<tr>
				<td align="center"><label><b>Course Name : </b></label>
					<%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), courseList) %>
					&emsp;
					<label><b>Semester : </b></label><%
							HashMap map = new HashMap();
							map.put("I", "I");
							map.put("II", "II");
							map.put("III", "III");
							map.put("IV", "IV");
							map.put("V", "V");
							map.put("VI", "VI");
							map.put("VII", "VII");
							map.put("VIII", "VIII");
							String htmlList= HTMLUtility.getSemList("semester", bean.getSemester(), map);
						%> <%=htmlList%>
					&emsp;<label><b>Subject Name : </b></label>
					<input type="text" name="subjectName" placeholder="Enter Subject Name"
					value="<%=ServletUtility.getParameter("subjectName", request)%>">
					&emsp;<label><b>Exam Date :</b> </label>
					<input type="text" name="examDate"	 id="datepicker2" placeholder="Date Of Exam(Click Here)" readonly="readonly"
						value="<%=ServletUtility.getParameter("examDate", request)%>">
					&emsp;
					<input  type="submit" name="operation" value="<%=TimeTableListCtl.OP_SEARCH%>"> 
					&emsp;<input  type="submit" name="operation"  value="<%=TimeTableCtl.OP_RESET%>"></td>
			</tr>
		</table>
		<br>
		<table border="1" width="100%" >

			<tr>
				<th align="left" style="width: 100px"><input  type="checkbox" name="chk_all" onclick="checkedAll()"/> Select All</th>
				<th>S.No</th>
				<th>Course Name</th>
				<th>Subject Name</th>
				<th>Semester</th>
				<th>Exam Date</th>
				<th>Exam Time</th>
				<th>Edit</th>
			</tr>
			<%
				TimeTableModel model = new TimeTableModel();
				    int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;
					
					Iterator<TimeTableBean> it = list.iterator();
					TimeTableBean tbean= null;
					while (it.hasNext()) {
					 tbean = it.next();
			%>
			<tr align="center">
				<td align="left"><input type="checkbox" id="sid" name="chk_1" onchange="check()" value="<%=tbean.getId()%>"></td>
                <td><%=index++ %></td>
				<td><%=tbean.getSubjectName()%></td>
				<td><%=tbean.getCourseName()%></td>
				<td><%=tbean.getSemester()%></td>
				<td><%=new SimpleDateFormat("dd-MM-yyyy").format(tbean.getExamDate())%>
				<td><%= tbean.getTime()%>
				<td><a href="TimeTableCtl?id=<%=tbean.getId()%>">Edit</a></td>
			</tr>
			<%
				}
			%>
		</table>
		<br>
		<table width="100%" align="center">
			<tr>
				<td><input  type="submit" name="operation" 
					value="<%=TimeTableCtl.OP_PREVIOUS%>" <%if (pageNo == 1) {%>
					disabled="disabled" <%}%>></td>
				<td ><input  type="submit" name="operation"
					value="<%=TimeTableCtl.OP_NEW%>"></td>
				<td style="padding-left: 13%"><input type="submit" name="operation"
					value="<%=TimeTableCtl.OP_DELETE%>" <%if(list.size()==0){ %>disabled="disabled"<%} %>></td><td></td>
				<td align="right"><input  type="submit" name="operation"
					value="<%=TimeTableCtl.OP_NEXT%>" <%if (list.size() < pageSize || model.nextPK()-1 == tbean.getId()) {%>
					disabled="disabled" <%}%>></td>
			</tr>
		</table>
		<input type="hidden" name="pageNo" value="<%=pageNo%>"> 
		<input type="hidden" name="pageSize" value="<%=pageSize%>">
			<%} else{ %>
			<input type="submit" name="operation" value="<%=TimeTableListCtl.OP_BACK%>">
			<%} %>
			</center>
	</form>
	<br><br><br><br>
	<%@ include file="Footer.jsp"%>
</body>
</html>