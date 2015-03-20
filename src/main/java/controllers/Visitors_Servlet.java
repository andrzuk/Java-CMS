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

import checkers.ACL;
import checkers.Page_Meta;
import utilities.Paginator;
import utilities.Sorting;
import models.Visitors_Model;
import dao.Visitors_Dao;

@WebServlet("/visitors")

public class Visitors_Servlet extends HttpServlet {
	
	private static final String MODULE = "visitors";
	
	private static final long serialVersionUID = 1L;
       
    public Visitors_Servlet() {
    	
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
		
		List<String> columns = Arrays.asList("id", "visitor_ip", "host_name", "http_referer", "request_uri", "visited");
		List<String> widths = Arrays.asList("10%", "15%", "20%", "30%", "15%", "10%");
		List<String> aligns = Arrays.asList("left", "left", "left", "left", "left", "center");
		
		List<Visitors_Dao> visitors = null;
		
		Visitors_Model modelObject = new Visitors_Model();
		
		Paginator paginator = new Paginator(request);
		Sorting sorting = new Sorting(request);
		String filter = (String) request.getSession().getAttribute("search_text");
		
		try {
			
			modelObject.setFilter(filter);
			paginator.setRows_count(modelObject.getCount());
			request = paginator.getRequest();
			sorting.setFields_list(columns, widths, aligns);
			visitors = modelObject.getSegment(paginator, sorting);
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
		request.setAttribute("data", visitors);
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
