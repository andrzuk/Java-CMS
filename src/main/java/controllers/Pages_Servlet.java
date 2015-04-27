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
import dao.Pages_Dao;
import models.Categories_Model;
import models.Pages_Model;
import validators.Pages_Validator;
import utilities.Messages;
import utilities.Paginator;
import utilities.Sorting;
import checkers.ACL;
import checkers.Page_Meta;
import checkers.Parameters;

@WebServlet("/pages")

public class Pages_Servlet extends HttpServlet {
	
	private static final String MODULE = "pages";
	
	private static final long serialVersionUID = 1L;
       
    public Pages_Servlet() {

    	super();
    }
    
    private Pages_Dao setData(HttpServletRequest request, Pages_Dao site) {
    	
    	site.setType(request.getParameter("type"));
    	site.setCategory_id(Integer.parseInt(request.getParameter("category_id")));
    	site.setTitle(request.getParameter("title"));
    	site.setContents(request.getParameter("contents"));
    	site.setDescription(request.getParameter("description"));
    	site.setVisible(request.getParameter("visible").equals("active"));  
    	site.setAuthor_id((Integer) request.getSession().getAttribute("user_id"));
    	
    	return site;
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
			
			Pages_Dao site = null;
			List<Categories_Dao> categories = null;
			
			Pages_Model modelObject = new Pages_Model();
			Categories_Model importObject = new Categories_Model();
			
			try {
				
				site = modelObject.getOne(id);
				categories = importObject.getAll();
				
				if (request.getSession().getAttribute("import_image_tag") != null) {
					
					site.setContents(request.getSession().getAttribute("import_image_tag") + site.getContents());					
					request.getSession().removeAttribute("import_image_tag");
				}
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", action);
			
			request.setAttribute("site", site);
			request.setAttribute("imported", categories);
		}
		else if (action.equals("delete")) {
			
			Pages_Dao site = null;
			
			Pages_Model modelObject = new Pages_Model();
			
			try {
				
				site = modelObject.getOne(id);
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", action);
			
			request.setAttribute("site", site);
		}
		else {
			
			List<Pages_Dao> pages = null;
			
			Pages_Model modelObject = new Pages_Model();
			
			List<String> columns = Arrays.asList("id", "caption", "title", "description", "login", "modified");
			List<String> widths = Arrays.asList("10%", "10%", "30%", "35%", "5%", "10%");
			List<String> aligns = Arrays.asList("left", "left", "left", "left", "left", "center");
			
			Paginator paginator = new Paginator(request);
			Sorting sorting = new Sorting(request);
			String filter = (String) request.getSession().getAttribute("search_text");
			
			try {
				
				modelObject.setFilter(filter);
				paginator.setRows_count(modelObject.getCount());
				request = paginator.getRequest();
				sorting.setFields_list(columns, widths, aligns);
				pages = modelObject.getSegment(paginator, sorting);
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", "list");
			
			request.setAttribute("data", pages);
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
		
		Pages_Model modelObject;
		Parameters parameter = new Parameters(request);
		Messages message = new Messages(request);
		
		int id = parameter.getId();
		String action = parameter.getAction();
		String button = parameter.getButton();
		
		if (action.equals("new")) {
			
			Pages_Dao site = new Pages_Dao();
			
			site = setData(request, site);
			
			modelObject = new Pages_Model(site);
			
			Pages_Validator validatorObject = new Pages_Validator();
			
			validated = validatorObject.validate(site);

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
			
			Pages_Dao site = new Pages_Dao();
			
			site = setData(request, site);
			
			modelObject = new Pages_Model(site);
			
			Pages_Validator validatorObject = new Pages_Validator();
			
			validated = validatorObject.validate(site);

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
			
			modelObject = new Pages_Model();

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
