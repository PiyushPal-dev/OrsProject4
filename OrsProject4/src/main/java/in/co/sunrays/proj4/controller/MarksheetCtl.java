package in.co.sunrays.proj4.controller;


import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.MarksheetBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.MarksheetModel;
import in.co.sunrays.proj4.model.StudentModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Marksheet functionality Controller. Performs operation for add, update,
 * delete and get Marksheet
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@ WebServlet(name="MarksheetCtl",urlPatterns={"/ctl/MarksheetCtl"})
public class MarksheetCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(MarksheetCtl.class);

    @Override
    protected void preload(HttpServletRequest request) {
        StudentModel model = new StudentModel();
        try {
            List l = model.list();
            request.setAttribute("studentList", l);
        } catch (ApplicationException e) {
            log.error(e);
        }

    }

    @Override
    protected boolean validate(HttpServletRequest request) {
        log.debug("MarksheetCtl Method validate Started");
        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("rollNo"))) {
            request.setAttribute("rollNo",
                    PropertyReader.getValue("error.require", "Roll Number"));
            pass = false;
        }else if(!DataValidator.isRollNo(request.getParameter("rollNo"))){
        	request.setAttribute("rollNo", PropertyReader.getValue("error.roll","Roll Number"));
        	pass=false;
        }
        
     if(DataValidator.isNull(request.getParameter("physics"))){
    	request.setAttribute("physics", PropertyReader.getValue("error.require", "Physics No")); 
    	 pass=false;
     }
        if (DataValidator.isNotNull(request.getParameter("physics"))
                && !DataValidator.isInteger(request.getParameter("physics"))) {
            request.setAttribute("physics",
                    PropertyReader.getValue("error.integer", "Marks"));
            pass = false;
        }

        if (DataUtility.getInt(request.getParameter("physics")) > 100) {
            request.setAttribute("physics", "Marks can not be greater than 100");
            pass = false;
        }
        
        if(DataUtility.getInt(request.getParameter("physics")) < 0){
        	request.setAttribute("physics", "Marks can't be negative");
        	pass = false;
        }
        
    if(DataValidator.isNull(request.getParameter("chemistry"))){
	request.setAttribute("chemistry",PropertyReader.getValue("error.require", "Chemistry No"));
	pass=false;
}
        if (DataValidator.isNotNull(request.getParameter("chemistry"))
                && !DataValidator.isInteger(request.getParameter("chemistry"))) {
            request.setAttribute("chemistry",
                    PropertyReader.getValue("error.integer", "Marks"));
            pass = false;
        }

        if (DataUtility.getInt(request.getParameter("chemistry")) > 100) {
            request.setAttribute("chemistry",
                    "Marks can not be greater than 100");
            pass = false;
        }
        
        if(DataUtility.getInt(request.getParameter("chemistry")) < 0){
        	request.setAttribute("chemistry", "Marks can't be negative");
        	pass = false;
        }
        
    if(DataValidator.isNull(request.getParameter("maths"))){
    	request.setAttribute("maths", PropertyReader.getValue("error.require", "Math No "));
    	pass=false;
    }
        if (DataValidator.isNotNull(request.getParameter("maths"))
                && !DataValidator.isInteger(request.getParameter("maths"))) {
            request.setAttribute("maths",
                    PropertyReader.getValue("error.integer", "Marks"));
            pass = false;
        }

        if (DataUtility.getInt(request.getParameter("maths")) > 100) {
            request.setAttribute("maths", "Marks can not be greater than 100");
            pass = false;
        }
        
        if(DataUtility.getInt(request.getParameter("maths")) < 0){
        	request.setAttribute("maths", "Marks can't be negative");
        	pass = false;
        }

        if (DataValidator.isNull(request.getParameter("studentId"))) {
            request.setAttribute("studentId",
                    PropertyReader.getValue("error.require", "Student Name"));
            pass = false;
        }

        log.debug("MarksheetCtl Method validate Ended");

        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("MarksheetCtl Method populatebean Started");

        MarksheetBean bean = new MarksheetBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));

        bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

        bean.setName(DataUtility.getString(request.getParameter("name")));

        bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));

        bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));

        bean.setMaths(DataUtility.getInt(request.getParameter("maths")));

        bean.setStudentId(DataUtility.getLong(request.getParameter("studentId")));

        populateDTO(bean, request);


        log.debug("MarksheetCtl Method populatebean Ended");

        return bean;
    }

    /**
     *  Display logics inside this Method
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.debug("MarksheetCtl Method doGet Started");
        HttpSession session=request.getSession(true);
        UserBean uBean=(UserBean)session.getAttribute("user");
        if(uBean.getRoleId()!=RoleBean.ADMIN){
        	ServletUtility.redirect(ORSView.ERROR_CTL, request, response);
        	return;
        }
        //String op = DataUtility.getString(request.getParameter("operation"));
        long id = DataUtility.getLong(request.getParameter("id"));
        MarksheetModel model = new MarksheetModel();
        if (id > 0) {
            MarksheetBean bean;
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
        log.debug("MarksheetCtl Method doGet Ended");
    }

    /**
     * Submit logics inside this Method
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        log.debug("MarksheetCtl Method doPost Started");
        String op = DataUtility.getString(request.getParameter("operation"));
        long id = DataUtility.getLong(request.getParameter("id"));
        // get model
        MarksheetModel model = new MarksheetModel();
        if (OP_SAVE.equalsIgnoreCase(op)) {
            MarksheetBean bean = (MarksheetBean) populateBean(request);
            try {
                if (id > 0) {
                    model.update(bean);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage("Data is successfully update", request);
                } else {
                    long pk = model.add(bean);
                    bean.setId(pk);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage("Data is successfully saved", request);
                }
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Roll no already exists",
                        request);
            }

        } else if (OP_DELETE.equalsIgnoreCase(op)) {

            MarksheetBean bean = (MarksheetBean) populateBean(request);
            try {
                model.delete(bean);
                ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request,
                        response);
                return;
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request,
                    response);
            return;

        }else if(OP_RESET.equalsIgnoreCase(op)){
        	ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
        	return;
        }
        ServletUtility.forward(getView(), request, response);

        log.debug("MarksheetCtl Method doPost Ended");
    }

    @Override
    protected String getView() {
        return ORSView.MARKSHEET_VIEW;
    }

}

 