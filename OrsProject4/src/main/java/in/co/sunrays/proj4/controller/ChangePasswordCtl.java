package in.co.sunrays.proj4.controller;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.RecordNotFoundException;
import in.co.sunrays.proj4.model.UserModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Change Password functionality Controller. Performs operation for Change
 * Password
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "ChangePasswordCtl", urlPatterns = { "/ctl/ChangePasswordCtl" })
public class ChangePasswordCtl extends BaseCtl {

	public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";

	private static Logger log = Logger.getLogger(ChangePasswordCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("ChangePasswordCtl Method validate Started");

		boolean pass = true;
		String op = DataUtility.getString(request.getParameter("operation"));
		if (OP_CHANGE_MY_PROFILE.equals(op)) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("oldPassword"))) {
			request.setAttribute("oldPassword", PropertyReader.getValue("error.require", "Old Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", PropertyReader.getValue("error.require", "New Password"));
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", PropertyReader.getValue("error.pass", "New Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.pass", "Confirm Password"));
			pass = false;
		} else if (!request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "New and Confirm Password Not Matched");
			pass = false;
		}
		log.debug("ChangePasswordCtl Method validate Ended");
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("ChangePasswordCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setPassword(DataUtility.getString(request.getParameter("oldPassword")));

		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

		populateDTO(bean, request);

		log.debug("ChangePasswordCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * Display Logics inside this method
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("ChangePasswordCtl Method doGet Started");

		ServletUtility.forward(getView(), request, response);

		log.debug("ChangePasswordCtl Method doGet Ended");
	}

	/**
	 * Submit logic inside this Method
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("ChangePasswordCtl Method doPost Started");

		HttpSession session = request.getSession(true);
		UserBean UserBean = (UserBean) session.getAttribute("user");
		long id = UserBean.getId();
		String newPassword = (String) request.getParameter("newPassword");
		String op = DataUtility.getString(request.getParameter("operation"));
		// UserBean bean = (UserBean) populateBean(request);
		// get model
		UserModel model = new UserModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);
			try {
				boolean flag = model.changePassword(id, bean.getPassword(), newPassword);
				if (flag == true) {
					bean = model.findByLogin(UserBean.getLogin());
					session.setAttribute("user", bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Password has been changed successfully.", request);
				}
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;

			} catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage("Old password is invalid", request);
			}
		} else if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
			return;
		}
		ServletUtility.forward(ORSView.CHANGE_PASSWORD_VIEW, request, response);
		log.debug("ChangePasswordCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.CHANGE_PASSWORD_VIEW;
	}

}