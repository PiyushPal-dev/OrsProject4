package in.co.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/ABC")
public class FirstServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		System.out.println();
		session.getCreationTime();
		session.getLastAccessedTime();
		session.getId();
		session.isNew();
		String name1 = null;
		String value = null;
		Enumeration<String> s = session.getAttributeNames();
		while (s.hasMoreElements()) {
			name1 = s.nextElement();
			value = (String) session.getAttribute(name1);
		}
		String name = "Rajkumar meena";
		PrintWriter pw = resp.getWriter();
		pw.print(name);
	}
}
