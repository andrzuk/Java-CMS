package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import models.Excludes_Model;
import checkers.ACL;
import checkers.Page_Meta;
import checkers.Parameters;
import dao.Excludes_Dao;

@WebServlet("/excludes")

public class Excludes_Servlet extends HttpServlet {
	
	private static final String MODULE = "excludes";
	
	private static final long serialVersionUID = 1L;
       
    public Excludes_Servlet() {

    	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ACL acl = new ACL();
		
		if (!acl.getAccess(request.getSession().getAttribute("user_status"), request.getSession().getAttribute("user_id"), MODULE)) {
			
			response.sendRedirect("/access_denied");
			return;
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/admin_page.jsp");
		
		Map<String, String> attributes = new HashMap<String, String>();
		
		Page_Meta pageObject = new Page_Meta();
		
		attributes = pageObject.setPageMeta(attributes);

		attributes.put("module", MODULE);
		attributes.put("title", MODULE.toUpperCase());
		attributes.put("action", "list");

		List<Excludes_Dao> excludes = null;
		
		Excludes_Model modelObject = new Excludes_Model();
		
		try {
			
			excludes = modelObject.getAll();
		} 
		catch (SQLException e) {

			e.printStackTrace();
		}
		
		attributes.put("logged", (String) request.getSession().getAttribute("user_login"));
		request.setAttribute("page", attributes);
		request.setAttribute("data", excludes);
		
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
		String visitor_ip = "";
		Boolean active = true;
		
		Excludes_Dao exclude = null;
		List<Excludes_Dao> excludes = new ArrayList<Excludes_Dao>();
		Parameters parameter = new Parameters(request);
		Messages message = new Messages(request);
		
		String param = "";
		Enumeration<String> params = request.getParameterNames(); 
		
		String action = "edit";
		String button = parameter.getButton();

		Boolean[] complete = {false, false}; 

		if (button.equals("update")) {
			
			while (params.hasMoreElements()) {
				
				param = params.nextElement();
				
				if (param.substring(0, 3).equals("ip_")) {
					
					visitor_ip = request.getParameter(param);
					complete[0] = true;
				}
				if (param.substring(0, 3).equals("ac_")) {
					
					active = Integer.parseInt(request.getParameter(param)) == 1 ? true : false;
					complete[1] = true;
				}

				if (complete[0] & complete[1]) {
					
					exclude = new Excludes_Dao();
					
					exclude.setVisitor_ip(visitor_ip);
					exclude.setActive(active);
						
					if (!exclude.getVisitor_ip().isEmpty()) {
						
						excludes.add(exclude);
					}

					complete[0] = false;
					complete[1] = false;
				}
			}
			
			Excludes_Model modelObject = new Excludes_Model(excludes);
			
			try {
				
				result = modelObject.saveAll();
			} 
			catch (SQLException e) {

				e.printStackTrace();
			}
		}
		
		response.sendRedirect("/admin");
		
		message.setMessage(action, result);
		request = message.show();
	}
}
