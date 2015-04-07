package controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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

import dao.Users_Dao;
import models.ACL_Model;
import models.Users_Model;
import validators.Users_Validator;
import checkers.ACL;
import checkers.Page_Meta;
import checkers.Parameters;
import utilities.Messages;
import utilities.Paginator;
import utilities.Sorting;

@WebServlet("/users")

public class Users_Servlet extends HttpServlet {
	
	private static final String MODULE = "users";

	private static final long serialVersionUID = 1L;
       
    public Users_Servlet() {
    	
        super();
    }
    
    private Users_Dao setData(HttpServletRequest request, Users_Dao user) {
		
		user.setFirst_name(request.getParameter("first_name"));
		user.setLast_name(request.getParameter("last_name"));
		user.setLogin(request.getParameter("login"));
		user.setEmail(request.getParameter("email"));
		user.setStatus(Integer.parseInt(request.getParameter("status")));
		user.setActive(request.getParameter("active").equals("active"));
		
		return user;
    }

    private Users_Dao setPassword(HttpServletRequest request, Users_Dao user) {
		
		user.setPassword(request.getParameter("password"));
		user.setRepeat(request.getParameter("repeat"));
		
		return user;
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
		
		if ((Integer) request.getSession().getAttribute("user_id") > 1) {
			
			id = (Integer) request.getSession().getAttribute("user_id");
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin_page.jsp");
		
		Map<String, String> attributes = new HashMap<String, String>();
		
		Page_Meta pageObject = new Page_Meta();
		
		attributes = pageObject.setPageMeta(attributes);

		if (action.equals("new")) {
			
			attributes.put("action", action);
		}
		else if (action.equals("edit")) {
			
			Users_Dao user = null;
			
			Users_Model modelObject = new Users_Model();
			
			try {
				
				user = modelObject.getOne(id);
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", action);
			
			request.setAttribute("user", user);
		}
		else if (action.equals("password")) {
			
			Users_Dao user = null;
			
			Users_Model modelObject = new Users_Model();
			
			try {
				
				user = modelObject.getOne(id);
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
					
			attributes.put("action", action);
			
			request.setAttribute("user", user);
		}
		else if (action.equals("delete")) {
			
			Users_Dao user = null;
			
			Users_Model modelObject = new Users_Model();
			
			try {
				
				user = modelObject.getOne(id);
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", action);
			
			request.setAttribute("user", user);
		}
		else {
			
			List<Users_Dao> users = null;
			
			Users_Model modelObject = new Users_Model();
			
			List<String> columns = Arrays.asList("id", "first_name", "last_name", "email", "login", "status", "modified");
			List<String> widths = Arrays.asList("10%", "15%", "15%", "25%", "15%", "10%", "10%");
			List<String> aligns = Arrays.asList("left", "left", "left", "left", "left", "left", "center");
			
			Paginator paginator = new Paginator(request);
			Sorting sorting = new Sorting(request);
			String filter = (String) request.getSession().getAttribute("search_text");
			
			try {
				
				modelObject.setFilter(filter);
				paginator.setRows_count(modelObject.getCount());
				request = paginator.getRequest();
				sorting.setFields_list(columns, widths, aligns);
				users = modelObject.getSegment(paginator, sorting);
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", "list");
			
			request.setAttribute("data", users);
			request.setAttribute("sorting", sorting);
			request.setAttribute("filter", filter);
		}

		attributes.put("module", MODULE);
		attributes.put("title", MODULE.toUpperCase());
		attributes.put("logged", (String) request.getSession().getAttribute("user_login"));
		request.setAttribute("operator", request.getSession().getAttribute("user_id"));
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
		
		Users_Model modelObject;
		Parameters parameter = new Parameters(request);
		Messages message = new Messages(request);
		
		int id = parameter.getId();
		String action = parameter.getAction();
		String button = parameter.getButton();
		
		if (action.equals("new")) {
			
			Users_Dao user = new Users_Dao();
			
			user = setData(request, user);
			
			modelObject = new Users_Model(user);
			
			ACL_Model dacl = new ACL_Model();
			
			Users_Validator validatorObject = new Users_Validator();
			
			validated = validatorObject.validate(user);

			if (button.equals("register")) {
				
				if (validated) {
					
					try {
						
						result = modelObject.save();
						
						if (result > 0) {
							
							user = modelObject.getLast();
							result = dacl.makeAccess(user.getId());
						}
					} 
					catch (SQLException e) {
						
						e.printStackTrace();
					} 
					catch (ParseException e) {
					
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
			
			Users_Dao user = new Users_Dao();
			
			user = setData(request, user);
			
			modelObject = new Users_Model(user);
			
			Users_Validator validatorObject = new Users_Validator();
			
			validated = validatorObject.validate(user);

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
		else if (action.equals("password")) {
			
			Users_Dao user = new Users_Dao();
			
			user = setPassword(request, user);
			
			modelObject = new Users_Model(user);
			
			Users_Validator validatorObject = new Users_Validator();
			
			validated = validatorObject.compare(user);

			if (button.equals("update")) {
				
				if (validated) {
					
					try {
						
						result = modelObject.setPassword(id);
					} 
					catch (SQLException e) {
						
						e.printStackTrace();
					} 
					catch (NoSuchAlgorithmException e) {
					
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
			
			modelObject = new Users_Model();

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
