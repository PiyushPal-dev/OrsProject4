package in.co.sunrays.proj4.controller;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.MarksheetBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.MarksheetModel;
import in.co.sunrays.proj4.util.DataUtility;
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
 * Marksheet Merit List functionality Controller. Performance operation of
 * Marksheet Merit List
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "MarksheetMeritListCtl", urlPatterns = { "/ctl/MarksheetMeritListCtl" })
public class MarksheetMeritListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(MarksheetMeritListCtl.class);

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		MarksheetBean bean = new MarksheetBean();

		return bean;
	}

	/**
	 * Display logics inside this Method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("MarksheetMeritListCtl doGet Start");
		// MarksheetBean bean = (MarksheetBean) populateBean(request);
		// String op = DataUtility.getString(request.getParameter("operation"));
		HttpSession session=request.getSession(true);
	    UserBean uBean=(UserBean)session.getAttribute("user");
	    if(uBean.getRoleId()==RoleBean.STUDENT){
	    	ServletUtility.redirect(ORSView.ERROR_CTL, request, response);
	    	return;
	    }
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		MarksheetModel model = new MarksheetModel();
		List list = null;
		try {
			list = model.getMeritList(pageNo, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("MarksheetMeritListCtl doGet End");
	}

	/**
	 * Submit logics inside this Method
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("MarksheetMeritListCtl doGet Start");
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		MarksheetBean bean = (MarksheetBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		MarksheetModel model = new MarksheetModel();
		List list = null;
		try {
			if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
				return;
			}
			list = model.getMeritList(pageNo, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("MarksheetMeritListCtl doPost End");
	}

	@Override
	protected String getView() {
		return ORSView.MARKSHEET_MERIT_LIST_VIEW;
	}
}
