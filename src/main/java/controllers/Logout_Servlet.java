package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logout")

public class Logout_Servlet extends HttpServlet {
	
	private static final String MODULE = "logout";

	private static final long serialVersionUID = 1L;
       
    public Logout_Servlet() {

    	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getSession().removeAttribute("user_id");
		request.getSession().removeAttribute("user_status");
		request.getSession().removeAttribute("user_login");
		
		response.sendRedirect("/");
	}
}
