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

import dao.Config_Dao;
import models.Config_Model;
import utilities.Messages;
import utilities.Paginator;
import utilities.Sorting;
import validators.Config_Validator;
import checkers.ACL;
import checkers.Page_Meta;
import checkers.Parameters;

@WebServlet("/config")

public class Config_Servlet extends HttpServlet {
	
	private static final String MODULE = "config";
	
	private static final long serialVersionUID = 1L;
       
    public Config_Servlet() {
    	
        super();
    }

    private Config_Dao setData(HttpServletRequest request, Config_Dao config) {
		
		config.setKey_name(request.getParameter("key_name"));
		config.setKey_value(request.getParameter("key_value"));
		config.setMeaning(request.getParameter("meaning"));
		config.setField_type(Integer.parseInt(request.getParameter("field_type")));
		config.setActive(request.getParameter("active").equals("active"));
		
		return config;
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

		if (action.equals("new")) {
			
			attributes.put("action", action);
		}
		else if (action.equals("edit")) {
			
			Config_Dao config = null;
			
			Config_Model modelObject = new Config_Model();
			
			try {
				
				config = modelObject.getOne(id);
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", action);
			
			request.setAttribute("config", config);
		}
		else if (action.equals("delete")) {
			
			Config_Dao config = null;
			
			Config_Model modelObject = new Config_Model();
			
			try {
				
				config = modelObject.getOne(id);
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", action);
			
			request.setAttribute("config", config);
		}
		else {
			
			List<Config_Dao> configs = null;
			
			Config_Model modelObject = new Config_Model();
			
			List<String> columns = Arrays.asList("id", "key_name", "key_value", "meaning", "field_type", "modified");
			List<String> widths = Arrays.asList("10%", "20%", "25%", "25%", "10%", "10%");
			List<String> aligns = Arrays.asList("left", "left", "left", "left", "left", "center");
			
			Paginator paginator = new Paginator(request);
			Sorting sorting = new Sorting(request);
			String filter = (String) request.getSession().getAttribute("search_text");
			
			try {
				
				modelObject.setFilter(filter);
				paginator.setRows_count(modelObject.getCount());
				request = paginator.getRequest();
				sorting.setFields_list(columns, widths, aligns);
				configs = modelObject.getSegment(paginator, sorting);
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", "list");
			
			request.setAttribute("data", configs);
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
		
		Config_Model modelObject;
		Parameters parameter = new Parameters(request);
		Messages message = new Messages(request);
		
		int id = parameter.getId();
		String action = parameter.getAction();
		String button = parameter.getButton();
		
		if (action.equals("new")) {
			
			Config_Dao config = new Config_Dao();
			
			config = setData(request, config);
			
			modelObject = new Config_Model(config);
			
			Config_Validator validatorObject = new Config_Validator();
			
			validated = validatorObject.validate(config);

			if (button.equals("register")) {
				
				if (validated) {
					
					try {
						
						result = modelObject.save();
					} 
					catch (SQLException e) {
						
						e.printStackTrace();
					} 
					response.sendRedirect("/" + MODULE);
				}
				else {
					
					response.sendRedirect("/" + MODULE + "?action=" + action);
				}
			}
			else {
				
				response.sendRedirect("/" + MODULE);
			}
			message.setMessage(action, result);
			request = message.show();
		}
		else if (action.equals("edit")) {
			
			Config_Dao config = new Config_Dao();
			
			config = setData(request, config);
			
			modelObject = new Config_Model(config);
			
			Config_Validator validatorObject = new Config_Validator();
			
			validated = validatorObject.validate(config);

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
			
			modelObject = new Config_Model();

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
