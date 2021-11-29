package in.co.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.chainsaw.Main;
@WebServlet(urlPatterns="/AAA")
public class TestCookies extends HttpServlet {
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	Cookie c1= new Cookie("name", "Rajkumar");
	Cookie c2=new Cookie("Village","Bapcha");
	resp.addCookie(c1);
	resp.addCookie(c2);
	PrintWriter pw=resp.getWriter();
	pw.print("Cookies Are Add");
	
	
	Cookie [] cArray = req.getCookies();
	for(Cookie c:cArray){
	String	name=c.getName();
	String village=c.getValue();
	System.out.println(name+"="+village);
	//pw.println();
	}
	
}

}
