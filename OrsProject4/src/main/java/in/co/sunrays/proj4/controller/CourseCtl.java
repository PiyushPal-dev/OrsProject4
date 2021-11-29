package in.co.sunrays.proj4.controller;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.CourseBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.CourseModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * CourseCtl functionality Controller. Performs operation for add, update, delete
 * and get Course
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */

@WebServlet(name = "CourseCtl", urlPatterns = { "/ctl/CourseCtl" })
public class CourseCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(CourseCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("CourseCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("duration"))) {
			request.setAttribute("duration", PropertyReader.getValue("error.require", "Duration"));
			pass = false;
		}

		log.debug("CourseCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("CourseCtl Method populatebean Started");

		CourseBean bean = new CourseBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setName(DataUtility.getString(request.getParameter("name")));

		bean.setDescription(DataUtility.getString(request.getParameter("description")));

		bean.setDuration(DataUtility.getString(request.getParameter("duration")));

		populateDTO(bean, request);

		log.debug("CourseCtl Method populatebean Ended");

		return bean;
	}
	 /**
	   *  Display Logics inside this method
	   */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        HttpSession      session=request.getSession(true);
        UserBean	uBean=(UserBean)session.getAttribute("user");
      if(uBean.getRoleId()!=RoleBean.ADMIN){
      ServletUtility.redirect(ORSView.ERROR_CTL, request, response);
      return;
  }
		log.debug("doGet started");
		// get model
		CourseModel model = new CourseModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			CourseBean bean;
			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		log.debug("doGet Ended");
		ServletUtility.forward(getView(), request, response);
	}
    /**
     *  Submit logics inside this Method
     */
  @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("CourseCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		CourseModel model = new CourseModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) ) {

			CourseBean bean = (CourseBean) populateBean(request);
               
			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data successfully updated", request);
				} else {
					long pk = model.add(bean);
					bean.setId(pk);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data successfully Saved", request);
				}
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Course Name already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			CourseBean bean = (CourseBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;

		} else if(OP_RESET.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("CourseCtl Method doGet Ended");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COURSE_VIEW;
	}



}
