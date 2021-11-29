package in.co.sunrays.proj4.controller;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.bean.SubjectBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.CourseModel;
import in.co.sunrays.proj4.model.SubjectModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Subject functionality Controller. Performs operation for add, update, delete
 * and get Subject
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet( urlPatterns = { "/ctl/SubjectCtl" })
public class SubjectCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(SubjectCtl.class);

	/**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request:
	 * 					HttpServletRequest object
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		CourseModel model = new CourseModel();
		try {
			List l = model.list();
			request.setAttribute("courseList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}

	}

	/**
	 * Validates Input data entered by user
	 * 
	 * @param request:
	 * 					HttpServletRequest object
	 * @return pass:
	 * 				a boolean variable
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("SubjectCtl Method validate Started");

		boolean pass = true;

		String op = DataUtility.getString(request.getParameter("operation"));

		
		
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Subject"));
			pass = false;
		}
		

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		if(DataUtility.getInt(request.getParameter("courseId"))==0) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course"));
			pass = false;
		}

		log.debug("SubjectCtl Method validate Ended");

		return pass;
	}

	/**
	 * Populates SubjectBean object from request parameters
	 * 
	 * @param request:
	 * 					HttpServletRequest object
	 * @return bean:
	 * 				SubjectBean object
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("SubjectCtl Method populatebean Started");

		SubjectBean bean = new SubjectBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setName(DataUtility.getString(request.getParameter("name")));

		bean.setDescription(DataUtility.getString(request.getParameter("description")));

		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));

		populateDTO(bean, request);

		log.debug("SubjectCtl Method populatebean Ended");

		return bean;
	}
/**
 * Display Logic inside this Method
 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("SubjectCtl Method doGet Started");

		HttpSession session=request.getSession(true);
        UserBean uBean=(UserBean)session.getAttribute("user");
        if(uBean.getRoleId()!=RoleBean.ADMIN){
        	ServletUtility.redirect(ORSView.ERROR_CTL, request, response);
        	return;
        }
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		// get model

		SubjectModel model = new SubjectModel();
		if (id > 0 || op != null) {
			SubjectBean bean;
			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("SubjectCtl Method doGett Ended");
	}
/**
 * Submit Logics inside this Method
 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("SubjectCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model

		SubjectModel model = new SubjectModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) ) {

			SubjectBean bean = (SubjectBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data is successfully updated", request);
				} else {
					long pk = model.add(bean);
					bean.setId(pk);
					 ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				}

			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Subject Name already exists", request);
			}
		}

		else if (OP_DELETE.equalsIgnoreCase(op)) {

			SubjectBean bean = (SubjectBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		}

		else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;

		}
		else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);

		log.debug("SubjectCtl Method doPost Ended");
	}

	/**
	 * Returns the view page of SubjectCtl
	 * 
	 * @return SubjectView.jsp:
	 * 							View page of SubjectCtl
	 */
	@Override
	protected String getView() {
		return ORSView.SUBJECT_VIEW;
	}

}

