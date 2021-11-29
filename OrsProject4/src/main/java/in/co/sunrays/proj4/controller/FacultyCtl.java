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
import in.co.sunrays.proj4.bean.FacultyBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.CollegeModel;
import in.co.sunrays.proj4.model.CourseModel;
import in.co.sunrays.proj4.model.FacultyModel;
import in.co.sunrays.proj4.model.RoleModel;
import in.co.sunrays.proj4.model.SubjectModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * Faculty  functionality Controller. Performs operation for add,update and get Faculty
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(urlPatterns = "/ctl/FacultyCtl")
public class FacultyCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(FacultyCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {

		CollegeModel collegeModel = new CollegeModel();
		SubjectModel subjectModel = new SubjectModel();
		 CourseModel courseModel = new CourseModel();
		try {
			List collegeList = collegeModel.list();
			 List subjectList = subjectModel.list();
			 List courseList = courseModel.list();
			request.setAttribute("collegeList", collegeList);
			 request.setAttribute("courseList", courseList);
			 request.setAttribute("subjectList", subjectList);
		} catch (ApplicationException e) {
			log.error(e);
		}
	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;
		String dob = request.getParameter("dateOfBirth");
		String email = request.getParameter("emailId");
		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (!DataValidator.isFname(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.fname", "First Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isLname(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.lname", "Last Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
		}
		if (DataValidator.isNull(request.getParameter("collegeList"))) {
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("courseList"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("subjectList"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "SubjectName"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("qualification"))) {
			request.setAttribute("qualification", PropertyReader.getValue("error.require", "Qualification"));
			pass = false;
		}
		if (DataValidator.isNull(email)) {
			request.setAttribute("emailId", PropertyReader.getValue("error.require", "Email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(email)) {
			request.setAttribute("emailId", PropertyReader.getValue("error.email", "Email Id"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "MobileNo"));
			pass = false;
		} else if (!DataValidator.isMobile(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.mono", "Mobile No"));
			pass = false;
		}
		if (DataValidator.isNull(dob)) {
			request.setAttribute("dateOfBirth", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(dob)) {
			request.setAttribute("dateOfBirth", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}

		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		FacultyBean bean = new FacultyBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeList")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseList")));
		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectList")));
		bean.setQualification(DataUtility.getString(request.getParameter("qualification")));
		bean.setEmailId(DataUtility.getString(request.getParameter("emailId")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setDob(DataUtility.getDate(request.getParameter("dateOfBirth")));
		populateDTO(bean, request);

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
	   
		long id=DataUtility.getInt(request.getParameter("id"));
		FacultyModel fmodel=new FacultyModel();
		if(id>0){ 
		              try {
					FacultyBean	bean=(FacultyBean)fmodel.findByPK(id);
					ServletUtility.setBean(bean, request);
					} catch (ApplicationException e) {
						e.printStackTrace();
					}
		              }
		         ServletUtility.forward(getView(), request, response);
	   }
/**
 * Summit Logics inside this Method
 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String op = DataUtility.getString(request.getParameter("operation"));
		
		FacultyModel model = new FacultyModel();
		
		long id = DataUtility.getLong(request.getParameter("id"));
		
		if (OP_SAVE.equalsIgnoreCase(op)) {
			FacultyBean bean = (FacultyBean) populateBean(request);
			
			try {
			
				if (id > 0) {
					model.update(bean);
					 ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data successfully updated", request);
				} else {
			       long pk = model.add(bean);
					bean.setId(pk);
					 ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data successfully saved", request);	
				}
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
				
			}catch(DuplicateRecordException e){
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login is Already Exist", request);
			}
		}else if(OP_DELETE.equalsIgnoreCase(op)){
                     FacultyBean    bean=(FacultyBean) populateBean(request);
			try{
				model.delete(bean);
				return;
			}catch(ApplicationException e){
				ServletUtility.handleException(e, request, response);
				return;
			}
		
	}else if(OP_CANCEL.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			return;
		}else if(OP_RESET.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
			return;
		}
		
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FACULTY_VIEW;
	}

}
