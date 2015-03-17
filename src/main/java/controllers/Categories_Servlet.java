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

import dao.Categories_Dao;
import models.Categories_Model;
import validators.Categories_Validator;
import checkers.ACL;
import checkers.Page_Meta;
import checkers.Parameters;
import utilities.Messages;
import utilities.Paginator;
import utilities.Sorting;

@WebServlet("/categories")

public class Categories_Servlet extends HttpServlet {
	
	private static final String MODULE = "categories";
	
	private static final long serialVersionUID = 1L;
       
    public Categories_Servlet() {
    	
        super();
    }

    private Categories_Dao setData(HttpServletRequest request, Categories_Dao category) {
		
    	category.setParent_id(Integer.parseInt(request.getParameter("parent_id")));
		category.setItem_order(Integer.parseInt(request.getParameter("item_order")));
		category.setCaption(request.getParameter("caption"));
		category.setLink(request.getParameter("link"));
		category.setVisible(request.getParameter("visible").equals("active"));    	
		category.setAuthor_id((Integer) request.getSession().getAttribute("user_id"));
		
		return category;
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
			
			List<Categories_Dao> categories = null;
			
			Categories_Model importObject = new Categories_Model();
			
			try {
				
				categories = importObject.getAll();
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", action);

			request.setAttribute("imported", categories);
		}
		else if (action.equals("edit")) {
			
			Categories_Dao category = null;
			List<Categories_Dao> categories = null;
			
			Categories_Model modelObject = new Categories_Model();
			
			try {
				
				category = modelObject.getOne(id);
				categories = modelObject.getAll();
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", action);
			
			request.setAttribute("category", category);
			request.setAttribute("imported", categories);
		}
		else if (action.equals("up")) {
			
			Categories_Dao category = null;
			
			Categories_Model modelObject = new Categories_Model();
			
			try {
				
				category = modelObject.getOne(id);
				
				if (category != null) {
					
					int result = modelObject.moveUp(category);
				}
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			response.sendRedirect("/" + MODULE);

			return;
		}
		else if (action.equals("down")) {
			
			Categories_Dao category = null;
			
			Categories_Model modelObject = new Categories_Model();
			
			try {
				
				category = modelObject.getOne(id);
				
				if (category != null) {
					
					int result = modelObject.moveDown(category);
				}
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
					
			response.sendRedirect("/" + MODULE);
			
			return;
		}
		else if (action.equals("delete")) {
			
			Categories_Dao category = null;
			
			Categories_Model modelObject = new Categories_Model();
			
			try {
				
				category = modelObject.getOne(id);
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", action);
			
			request.setAttribute("category", category);
		}
		else {
			
			List<Categories_Dao> categories = null;
			
			Categories_Model modelObject = new Categories_Model();
			
			List<String> columns = Arrays.asList("id", "parent_id", "item_order", "caption", "link", "login", "modified");
			List<String> widths = Arrays.asList("10%", "10%", "10%", "25%", "25%", "10%", "10%");
			List<String> aligns = Arrays.asList("left", "left", "left", "left", "left", "left", "center");
			
			Paginator paginator = new Paginator(request);
			Sorting sorting = new Sorting(request);
			String filter = (String) request.getSession().getAttribute("search_text");
			
			try {
				
				modelObject.setFilter(filter);
				paginator.setRows_count(modelObject.getCount());
				request = paginator.getRequest();
				sorting.setFields_list(columns, widths, aligns);
				categories = modelObject.getSegment(paginator, sorting);
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", "list");
			
			request.setAttribute("data", categories);
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
		
		Categories_Model modelObject;
		Parameters parameter = new Parameters(request);
		Messages message = new Messages(request);
		
		int id = parameter.getId();
		String action = parameter.getAction();
		String button = parameter.getButton();
		
		if (action.equals("new")) {
			
			Categories_Dao category = new Categories_Dao();
			
			category = setData(request, category);
			
			modelObject = new Categories_Model(category);
			
			Categories_Validator validatorObject = new Categories_Validator();
			
			validated = validatorObject.validate(category);

			if (button.equals("register")) {
				
				if (validated) {
					
					try {
						
						result = modelObject.save();
						
						if (result > 0) {
							
							if (category.getLink().equals("/category?id=(auto)")) {
								
								category = modelObject.getLast();
								category.setLink("/category?id=" + Integer.toString(category.getId()));
								modelObject = new Categories_Model(category);
								result = modelObject.update(category.getId());
							}
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
			
			Categories_Dao category = new Categories_Dao();
			
			category = setData(request, category);
			
			modelObject = new Categories_Model(category);
			
			Categories_Validator validatorObject = new Categories_Validator();
			
			validated = validatorObject.validate(category);

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
			
			modelObject = new Categories_Model();

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
