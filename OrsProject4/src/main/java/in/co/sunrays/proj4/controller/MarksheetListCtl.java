package in.co.sunrays.proj4.controller;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.MarksheetBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.MarksheetModel;
import in.co.sunrays.proj4.model.StudentModel;
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
 * Marksheet List functionality Controller. Performs operation for list, search
 * and delete operations of Marksheet
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */

/**
 * Servlet implementation class MarksheetlistCtl
 */
@ WebServlet(name="MarksheetListCtl",urlPatterns={"/ctl/MarksheetListCtl"})
public class MarksheetListCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(MarksheetListCtl.class);

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        MarksheetBean bean = new MarksheetBean();
         bean.setStudentId(DataUtility.getLong(request.getParameter("studentName")));
        bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

        bean.setName(DataUtility.getString(request.getParameter("name")));

        return bean;
    }
@Override
protected void preload(HttpServletRequest request) {

StudentModel model=new StudentModel();
List list=null;
try {
       list=	model.list();
      request.setAttribute("sList", list);
} catch (ApplicationException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}
    /**
     * Display logics inside this Method
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession session=request.getSession(true);
        UserBean uBean=(UserBean)session.getAttribute("user");
        if(uBean.getRoleId()==RoleBean.STUDENT||uBean.getRoleId()==RoleBean.KIOSK){
        	ServletUtility.redirect(ORSView.ERROR_CTL, request, response);
        	return;
        }
        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
        MarksheetBean bean = (MarksheetBean) populateBean(request);
        MarksheetModel model = new MarksheetModel();
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
        log.debug("MarksheetListCtl doGet End");

    }

    /**
     * Submit logics inside this Method
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.debug("MarksheetListCtl doPost Start");
        List list = null;
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        MarksheetBean bean = (MarksheetBean) populateBean(request);

        String op = DataUtility.getString(request.getParameter("operation"));

        // get the selected checkbox ids array for delete list
        String[] ids = request.getParameterValues("chk_1");

        MarksheetModel model = new MarksheetModel();

        try {

            if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op)
                    || OP_PREVIOUS.equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.MARKSHEET_CTL, request,
                        response);
                return;
            } else if(OP_RESET.equalsIgnoreCase(op)||OP_BACK.equalsIgnoreCase(op)){
            	ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
            	return;
            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;
                if (ids != null && ids.length !=0) {
                    MarksheetBean deletebean = new MarksheetBean();
                    for (String id : ids) {
                        deletebean.setId(DataUtility.getInt(id));
                        model.delete(deletebean);
                    }
                    ServletUtility.setSuccessMessage("Data successfully deleted", request);
                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }
            }
            list = model.search(bean, pageNo, pageSize);
            ServletUtility.setList(list, request);
            if (list == null || list.size() == 0 &&!OP_DELETE.equalsIgnoreCase(op)) {
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

        log.debug("MarksheetListCtl doPost End");
    }

    @Override
    protected String getView() {
        return ORSView.MARKSHEET_LIST_VIEW;
    }

}
