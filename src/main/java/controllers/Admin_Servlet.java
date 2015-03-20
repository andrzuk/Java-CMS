package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Admin_Model;
import checkers.Page_Meta;
import dao.Admin_Dao;

@WebServlet("/admin")

public class Admin_Servlet extends HttpServlet {
	
	private static final String MODULE = "admin";
	
	private static final long serialVersionUID = 1L;
       
    public Admin_Servlet() {
    	
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getSession().getAttribute("user_status") == null) {
			
			response.sendRedirect("/login");
			return;
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin_page.jsp");
		
		Map<String, String> attributes = new HashMap<String, String>();
		
		List<Admin_Dao> statistics = null;
		List<Admin_Dao> modules = null;
		
		Admin_Model modelObject = new Admin_Model();
		Page_Meta pageObject = new Page_Meta();
		
		attributes = pageObject.setPageMeta(attributes);
		
		try {
			
			statistics = modelObject.getStatistics();
			modules = modelObject.getModules();
		} 
		catch (SQLException e) {

			e.printStackTrace();
		} 
		catch (ParseException e) {
		
			e.printStackTrace();
		}
		
		attributes.put("module", MODULE);
		attributes.put("title", "Panel administratora");
		
		attributes.put("logged", (String) request.getSession().getAttribute("user_login"));
		request.setAttribute("page", attributes);
		request.setAttribute("statistics", statistics);
		request.setAttribute("modules", modules);
		
		request.setAttribute("message", request.getSession().getAttribute("message"));
		request.getSession().removeAttribute("message");

		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.sendRedirect("/admin");
	}
}
