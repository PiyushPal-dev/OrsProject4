package in.co.sunrays.proj4.controller;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.bean.SubjectBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.CourseModel;
import in.co.sunrays.proj4.model.SubjectModel;
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
 * College List functionality Controller. Performs operation for list, search
 * and delete operations of College
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "SubjectListCtl", urlPatterns = { "/ctl/SubjectListCtl" })
public class SubjectListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(SubjectListCtl.class);

	/**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request:
	 * 					HttpServletRequest object
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		SubjectModel model = new SubjectModel();
		CourseModel cmodel = new CourseModel();
		try {
			List subjectList = model.list();
			List courseList = cmodel.list();
			request.setAttribute("subjectList", subjectList);
			request.setAttribute("courseList", courseList);
			
		} catch (ApplicationException e) {
			log.error(e);
		}
	}

	/**
	 * Populates the SubjectBean object from request parameters
	 * 
	 * @param request:
	 * 					HttpServletRequest object
	 * @return bean:
	 * 				SubjectBean object
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		SubjectBean bean = new SubjectBean();

		bean.setId(DataUtility.getLong(request.getParameter("subjectName")));
		//bean.setDescription(DataUtility.getString(request.getParameter("description")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));

		return bean;
	}
/**
 * Display Logics inside this Method
 */
	@Override
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

		SubjectBean bean = (SubjectBean) populateBean(request);
		SubjectModel model = new SubjectModel();

		List list = null;

		try {
			list = model.search(bean, pageNo, pageSize);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

		if (list == null || list.size() == 0) {
			ServletUtility.setErrorMessage("No record found ", request);
		}

		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Submit Logics inside this Method
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("SubjectListCtl doPost Start");

		List list = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		SubjectBean bean = (SubjectBean) populateBean(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("chk_1");

		SubjectModel model = new SubjectModel();

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
				return;
			}else if(OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)){
				ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
				return;
			}else if (OP_DELETE.equalsIgnoreCase(op)) {
			     pageNo = 1;
				if (ids != null && ids.length > 0) {
					SubjectBean deletebean = new SubjectBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
						}
					ServletUtility.setSuccessMessage("Record deleted Successfully", request);
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
			
			list = model.search(bean, pageNo, pageSize);
			//ServletUtility.setList(list, request);
			if ((list == null || list.size() == 0) && !OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setBean(bean, request);
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

		log.debug("SubjectListCtl doPost End");
	}

	/**
	 * Returns the view page of SubjectListCtl
	 * 
	 * @return SubjectListView.jsp:
	 * 								View page of SubjectListCtl
	 */
	@Override
	protected String getView() {
		return ORSView.SUBJECT_LIST_VIEW;
	}
}

