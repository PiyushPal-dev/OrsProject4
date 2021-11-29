package in.co.sunrays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.ServletUtility;

/**
 * Error Controller
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(urlPatterns = "/ErrorCtl")
public class ErrorCtl extends BaseCtl {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
     // String e= (String)request.getAttribute("exception");
      
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

      String  op=DataUtility.getString(request.getParameter("operation"));
      if(OP_BACK.equalsIgnoreCase(op)){
    	  ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
    	 return; 
      }
      
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {

		return ORSView.ERROR_VIEW;
	}

}
