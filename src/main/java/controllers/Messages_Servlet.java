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

import models.Messages_Model;
import utilities.Messages;
import utilities.Paginator;
import utilities.Sorting;
import validators.Messages_Validator;
import checkers.ACL;
import checkers.Page_Meta;
import checkers.Parameters;
import dao.Messages_Dao;

@WebServlet("/messages")

public class Messages_Servlet extends HttpServlet {
	
	private static final String MODULE = "messages";
	
	private static final long serialVersionUID = 1L;
       
    public Messages_Servlet() {

    	super();
    }

    private Messages_Dao setData(HttpServletRequest request, Messages_Dao user_message) {
    	
    	user_message.setNick(request.getParameter("nick"));
    	user_message.setEmail(request.getParameter("email"));
    	user_message.setIp(request.getParameter("ip"));
    	user_message.setMessage(request.getParameter("message"));
    	user_message.setVisible(request.getParameter("visible").equals("active"));  
    	
    	return user_message;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ACL acl = new ACL();
		
		if (!acl.getAccess(request.getSession().getAttribute("user_status"), request.getSession().getAttribute("user_id"), MODULE)) {
			
			response.sendRedirect("/access_denied");
			return;
		}
		
		Parameters parameter = new Parameters(request);
		
		int id = parameter.getId();
		String action = parameter.getAction();

		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin_page.jsp");
		
		Map<String, String> attributes = new HashMap<String, String>();
		
		Page_Meta pageObject = new Page_Meta();
		
		attributes = pageObject.setPageMeta(attributes);

		if (action.equals("edit")) {
			
			Messages_Dao user_message = null;
			
			Messages_Model modelObject = new Messages_Model();
			
			try {
				
				user_message = modelObject.getOne(id);
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", action);
			
			request.setAttribute("user_message", user_message);
		}
		else if (action.equals("delete")) {
			
			Messages_Dao user_message = null;
			
			Messages_Model modelObject = new Messages_Model();
			
			try {
				
				user_message = modelObject.getOne(id);
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", action);
			
			request.setAttribute("user_message", user_message);
		}
		else {
			
			List<Messages_Dao> user_messages = null;
			
			Messages_Model modelObject = new Messages_Model();
			
			List<String> columns = Arrays.asList("id", "nick", "email", "ip", "message", "modified");
			List<String> widths = Arrays.asList("10%", "15%", "20%", "15%", "30%", "10%");
			List<String> aligns = Arrays.asList("left", "left", "left", "left", "left", "center");
			
			Paginator paginator = new Paginator(request);
			Sorting sorting = new Sorting(request);
			String filter = (String) request.getSession().getAttribute("search_text");
			
			try {
				
				modelObject.setFilter(filter);
				paginator.setRows_count(modelObject.getCount());
				request = paginator.getRequest();
				sorting.setFields_list(columns, widths, aligns);
				user_messages = modelObject.getSegment(paginator, sorting);
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", "list");
			
			request.setAttribute("data", user_messages);
			request.setAttribute("sorting", sorting);
			request.setAttribute("filter", filter);
		}

		attributes.put("module", MODULE);
		attributes.put("title", MODULE.toUpperCase());
		attributes.put("logged", (String) request.getSession().getAttribute("user_login"));
		request.setAttribute("page", attributes);
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
		
		int result = 0;
		boolean validated = false;
		
		Messages_Model modelObject;
		Parameters parameter = new Parameters(request);
		Messages message = new Messages(request);
		
		int id = parameter.getId();
		String action = parameter.getAction();
		String button = parameter.getButton();
		
		if (action.equals("edit")) {
			
			Messages_Dao user_message = new Messages_Dao();
			
			user_message = setData(request, user_message);
			
			modelObject = new Messages_Model(user_message);
			
			Messages_Validator validatorObject = new Messages_Validator();
			
			validated = validatorObject.validate(user_message);

			if (button.equals("update")) {
				
				if (validated) {
					
					try {
						
						result = modelObject.update(id);
					} 
					catch (SQLException e) {
						
						e.printStackTrace();
					}
					response.sendRedirect("/" + MODULE);
				}
				else {
					
					response.sendRedirect("/" + MODULE + "?action=" + action + "&id=" + id);
				}		
			}
			else {
				
				response.sendRedirect("/" + MODULE);
			}
			message.setMessage(action, result);
			request = message.show();
		}
		else if (action.equals("delete")) {
			
			modelObject = new Messages_Model();

			if (button.equals("delete")) {
				
				try {
					
					result = modelObject.delete(id);
				} 
				catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
			response.sendRedirect("/" + MODULE);

			message.setMessage(action, result);
			request = message.show();
		}
		else {
			
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
}
