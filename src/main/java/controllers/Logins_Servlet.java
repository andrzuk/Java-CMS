package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Logins_Model;
import utilities.Paginator;
import utilities.Sorting;
import checkers.ACL;
import checkers.Page_Meta;
import dao.Logins_Dao;

@WebServlet("/logins")

public class Logins_Servlet extends HttpServlet {
	
	private static final String MODULE = "logins";
	
	private static final long serialVersionUID = 1L;
       
    public Logins_Servlet() {
    
    	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ACL acl = new ACL();
		
		if (!acl.getAccess(request.getSession().getAttribute("user_status"), request.getSession().getAttribute("user_id"), MODULE)) {
			
			response.sendRedirect("/access_denied");
			return;
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin_page.jsp");
		
		Map<String, String> attributes = new HashMap<String, String>();
		
		Page_Meta pageObject = new Page_Meta();
		
		attributes = pageObject.setPageMeta(attributes);
		
		List<String> columns = Arrays.asList("id", "agent", "user_ip", "user_id", "login", "password", "login_time");
		List<String> widths = Arrays.asList("10%", "30%", "10%", "10%", "15%", "15%", "10%");
		List<String> aligns = Arrays.asList("left", "left", "left", "left", "left", "left", "center");
		
		List<Logins_Dao> logins = null;
		
		Logins_Model modelObject = new Logins_Model();
		
		Paginator paginator = new Paginator(request);
		Sorting sorting = new Sorting(request);
		String filter = (String) request.getSession().getAttribute("search_text");
		
		try {
			
			modelObject.setFilter(filter);
			paginator.setRows_count(modelObject.getCount());
			request = paginator.getRequest();
			sorting.setFields_list(columns, widths, aligns);
			logins = modelObject.getSegment(paginator, sorting);
		} 
		catch (SQLException e) {

			e.printStackTrace();
		} 
		catch (ParseException e) {
		
			e.printStackTrace();
		}
		
		attributes.put("module", MODULE);
		attributes.put("title", MODULE.toUpperCase());
		attributes.put("action", "list");
		attributes.put("logged", (String) request.getSession().getAttribute("user_login"));
		request.setAttribute("page", attributes);
		request.setAttribute("data", logins);
		request.setAttribute("sorting", sorting);
		request.setAttribute("filter", filter);

		request.setAttribute("message", request.getSession().getAttribute("message"));
		request.getSession().removeAttribute("message");
		
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ACL acl = new ACL();
		
		if (!acl.getAccess(request.getSession().getAttribute("user_status"), request.getSession().getAttribute("user_id"), MODULE)) {
			
			response.sendRedirect("/access_denied");
			return;
		}
		
		if (request.getParameter("search_button") != null) {

			request.getSession().setAttribute("search_text", request.getParameter("search_value"));
		}
		if (request.getParameter("clear_search") != null) {

			request.getSession().removeAttribute("search_text");
		}
		
		Paginator paginator = new Paginator(request);
		
		request = paginator.updatePage_index();
		
		response.sendRedirect("/" + MODULE);
	}
}
