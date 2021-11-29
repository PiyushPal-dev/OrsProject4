package in.co.sunrays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.bean.TimeTableBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.exception.RecordNotFoundException;
import in.co.sunrays.proj4.model.CourseModel;
import in.co.sunrays.proj4.model.SubjectModel;
import in.co.sunrays.proj4.model.TimeTableModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * TimeTable functionality Controller. Performs operation for add, update,
 * delete and get TimeTable
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "TimeTableCtl", urlPatterns = { "/ctl/TimeTableCtl" })
public class TimeTableCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(TimeTableCtl.class);

	/**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request:
	 *            HttpServletRequest object
	 */
	protected void preload(HttpServletRequest request) {
		CourseModel courseModel = new CourseModel();
		SubjectModel subjectModel = new SubjectModel();
		try {
			List listCourse = courseModel.list();
			request.setAttribute("courseList", listCourse);

			List listSubject = subjectModel.list();
			request.setAttribute("subjectList", listSubject);
		} catch (ApplicationException e) {
			log.error(e);
		}

	}

	/**
	 * Validates the input data entered by the user
	 * 
	 * @param request:
	 *            HttpServletRequest object
	 * @return pass: a boolean variable
	 */
	protected boolean validate(HttpServletRequest request) {

		log.debug("TimeTableCtlMethod validate started");
		boolean pass = true;
		String op = DataUtility.getString(request.getParameter("operation"));

		if (DataUtility.getInt(request.getParameter("courseId")) == 0) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course"));
			pass = false;
		}

		if (DataUtility.getInt(request.getParameter("subjectId")) == 0) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("semester"))) {
			request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("examDate"))) {
			request.setAttribute("examDate", PropertyReader.getValue("error.require", "Date of exam"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("examDate"))) {
			request.setAttribute("examDate", PropertyReader.getValue("error.date", "Date of exam"));
			pass = false;
		} else if (DataUtility.getDate(request.getParameter("examDate")).getDay() == 0) {
			request.setAttribute("examDate", "Exam can't be scheduled on sunday");
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("time"))) {
			request.setAttribute("time", PropertyReader.getValue("error.require", "Time"));
			pass = false;
		}

		log.debug("TimeTableCtl Method validate Ended");
		return pass;

	}

	/**
	 * Populates the TimeTableBean object from request parameters
	 * 
	 * @param request:
	 *            HttpServletRequest object
	 * @return bean: TmeTableBean object
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("TimeTableCtl Method populatebean Started");

		TimeTableBean bean = new TimeTableBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		bean.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));
		bean.setSemester(DataUtility.getString(request.getParameter("semester")));
		bean.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
		bean.setTime(DataUtility.getString(request.getParameter("time")));

		populateDTO(bean, request);

		log.debug("TimeTableCtl Method populatebean Ended");
		return bean;

	}

	/**
	 * Display Logics inside this Method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("TimeTableCtl Method doGet Started");
		HttpSession session=request.getSession(true);
        UserBean uBean=(UserBean)session.getAttribute("user");
        if(uBean.getRoleId()!=RoleBean.ADMIN){
        	ServletUtility.redirect(ORSView.ERROR_CTL, request, response);
        	return;
        }
		
		long id = DataUtility.getLong(request.getParameter("id"));
		// get model
		TimeTableModel model = new TimeTableModel();
		if (id > 0) {
			TimeTableBean bean;
			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("TimeTableCtl Method doGet Ended");
	}

	/**
	 * Submit Logics inside this Method
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("TimeTableCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		TimeTableModel model = new TimeTableModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op)) {
			TimeTableBean bean = (TimeTableBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data is successfully updated", request);
				} else {
					long pk = model.add(bean);
					bean.setId(pk);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data is successfully added", request);
				}
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("TimeTable is already exists", request);
			} catch (RecordNotFoundException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(e.getMessage(), request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			TimeTableBean bean = (TimeTableBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.TIME_TABLE_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TIME_TABLE_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIME_TABLE_LIST_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);

		log.debug("TimeTableCtl Method doPost Ended");
	}

	/**
	 * Returns the view page of TimeTableCtl
	 * 
	 * @return TimeTableView.jsp: View page of TimeTableCtl
	 */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.TIME_TABLE_VIEW;
	}

}
