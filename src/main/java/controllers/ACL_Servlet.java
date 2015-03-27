package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utilities.Messages;
import utilities.Paginator;
import utilities.Sorting;
import models.ACL_Model;
import models.Users_Model;
import checkers.ACL;
import checkers.Page_Meta;
import checkers.Parameters;
import dao.ACL_Dao;
import dao.Users_Dao;

@WebServlet("/acl")

public class ACL_Servlet extends HttpServlet {
	
	private static final String MODULE = "acl";
	
	private static final long serialVersionUID = 1L;
       
    public ACL_Servlet() {
    	
        super();
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
			
			List<ACL_Dao> dacl = null;
			Users_Dao user = null;
			
			ACL_Model modelObject = new ACL_Model();
			Users_Model userObject = new Users_Model();
			
			try {
				
				dacl = modelObject.getOne(id);
				user = userObject.getOne(id);
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", action);
			
			request.setAttribute("user_id", id);
			request.setAttribute("data", dacl);
			request.setAttribute("user", user);
		}
		else {
			
			List<Users_Dao> users = null;
			
			ACL_Model modelObject = new ACL_Model();
			
			List<String> columns = Arrays.asList("id", "login", "email", "status", "functions");
			List<String> widths = Arrays.asList("10%", "20%", "25%", "15%", "30%");
			List<String> aligns = Arrays.asList("left", "left", "left", "center", "left");
			
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
		
		Parameters parameter = new Parameters(request);
		Messages message = new Messages(request);
		
		String action = parameter.getAction();
		String button = parameter.getButton();

		if (action.equals("edit")) {
			
			int result = 0;
			int user_id = 0;
			int function_id = 0;
			Boolean access = false;
			
			ACL_Dao dacl = null;
			List<ACL_Dao> dacls = new ArrayList<ACL_Dao>();
			ACL_Model modelObject = new ACL_Model(dacls);
			
			String param = "";
			Enumeration<String> params = request.getParameterNames(); 
			
			Boolean[] complete = {false, false, false}; 
			
			if (button.equals("update")) {
				
				while (params.hasMoreElements()) {
					
					param = params.nextElement();

					if (param.substring(0, 5).equals("user_")) {
						
						user_id = Integer.parseInt(request.getParameter(param));
						complete[0] = true;
					}
					if (param.substring(0, 5).equals("func_")) {
						
						function_id = Integer.parseInt(request.getParameter(param));
						complete[1] = true;
					}
					if (param.substring(0, 5).equals("accs_")) {
						
						access = Integer.parseInt(request.getParameter(param)) == 1 ? true : false;
						complete[2] = true;
					}

					if (complete[0] & complete[1] & complete[2]) {
						
						dacl = new ACL_Dao();
						
						dacl.setUser_id(user_id);
						dacl.setFunction_id(function_id);
						dacl.setAccess(access);
						
						dacls.add(dacl);						

						complete[0] = false;
						complete[1] = false;
						complete[2] = false;
					}
				}
				
				try {
					
					result = modelObject.saveAll(user_id);
				} 
				catch (SQLException e) {

					e.printStackTrace();
				}
				
				response.sendRedirect("/" + MODULE);
			}
			else {
				
				response.sendRedirect("/" + MODULE);
			}
			
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
