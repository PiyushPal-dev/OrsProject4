package in.co.sunrays.proj4.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.bean.TimeTableBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.RecordNotFoundException;
import in.co.sunrays.proj4.model.CourseModel;
import in.co.sunrays.proj4.model.TimeTableModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;
/**
 * Timetable List functionality Controller. Performs operation for list, search
 * and delete operations of Timetables
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "TimeTableListCtl", urlPatterns = { "/ctl/TimeTableListCtl" })

public class TimeTableListCtl extends BaseCtl {
	
	/**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request:
	 * 					HttpServletRequest object
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		CourseModel cmodel = new CourseModel();
		try {
			List cList = cmodel.list();
			request.setAttribute("courseList", cList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Populates TimeTableBean object from request parameters
	 * 
	 * @param request:
	 * 					HttpServletRequest object
	 * @return bean:
	 * 				TimeTableBean object
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		TimeTableBean bean = new TimeTableBean();
		bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));
		bean.setSemester(DataUtility.getString(request.getParameter("semester")));
		bean.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
		return bean;
	}

	
	/**
	 * Display Logics inside this Method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session=request.getSession(true);
        UserBean uBean=(UserBean)session.getAttribute("user");
        if(uBean.getRoleId()!=RoleBean.ADMIN){
        	ServletUtility.redirect(ORSView.ERROR_CTL, request, response);
        	return;
        }
		
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		TimeTableBean bean = (TimeTableBean) populateBean(request);
		TimeTableModel model = new TimeTableModel();
		List list = null;
		try {
			try {
				list = model.search(bean, pageNo, pageSize);
			} catch (RecordNotFoundException e) {
					ServletUtility.setErrorMessage("No Record Found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}
	
	
	/**
	 * Submit Logics inside this Method
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		int count=0;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
        TimeTableBean bean = (TimeTableBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("chk_1");
		TimeTableModel model = new TimeTableModel();
		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}
			}else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.TIME_TABLE_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.TIME_TABLE_LIST_CTL, request, response);
				return;
			}else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo=1;
				if (ids != null && ids.length > 0) {
					TimeTableBean deleteBean = new TimeTableBean();
					for (String id : ids) {
						deleteBean.setId(DataUtility.getInt(id));
						model.delete(deleteBean);
					}
					ServletUtility.setSuccessMessage("Record deleted successfully", request);
					} else {
					ServletUtility.setErrorMessage("Please select at least one record ", request);
				}
			}
			
			try {
				list = model.search(bean, pageNo, pageSize);
			} catch (RecordNotFoundException e) {
				if ( !OP_DELETE.equalsIgnoreCase(op)) {
					ServletUtility.setErrorMessage("No Record Found", request);
				}
			}
			ServletUtility.setBean(bean, request);
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}
	
	/**
	 * Returns the view page of TimeTableListCtl
	 * 
	 * @return TimeTableListView.jsp:
	 * 									View page of TimeTableListCtl
	 */
	protected String getView() {
		return ORSView.TIME_TABLE_LIST_VIEW;
	}
}

