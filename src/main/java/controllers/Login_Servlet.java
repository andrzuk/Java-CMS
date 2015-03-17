package controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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

import checkers.Page_Meta;
import models.Categories_Model;
import models.Users_Model;
import utilities.Messages;
import validators.Users_Validator;
import dao.Categories_Dao;
import dao.Users_Dao;

@WebServlet("/login")

public class Login_Servlet extends HttpServlet {
	
	private static final String MODULE = "login";

	private static final long serialVersionUID = 1L;
       
    public Login_Servlet() {
    	
        super();
    }

    private Users_Dao setData(HttpServletRequest request, Users_Dao user) {
		
		user.setLogin(request.getParameter("login"));
		user.setPassword(request.getParameter("password"));
		
		return user;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getSession().getAttribute("user_status") != null) {
			
			response.sendRedirect("/admin");
			return;
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/public_page.jsp");
		
		Map<String, String> attributes = new HashMap<String, String>();
		
		List<Categories_Dao> categories = null;
		
		Categories_Model importObject = new Categories_Model();
		Page_Meta pageObject = new Page_Meta();
		
		pageObject.setMain_title(MODULE.toUpperCase());
		attributes = pageObject.setPageMeta(attributes);
		
		try {
			
			categories = importObject.getActives();
		} 
		catch (SQLException e) {

			e.printStackTrace();
		} 
		catch (ParseException e) {
		
			e.printStackTrace();
		}
		
		attributes.put("module", MODULE);
		attributes.put("title", MODULE.toUpperCase() + " - Authorization");
		attributes.put("logged", (String) request.getSession().getAttribute("user_login"));

		request.setAttribute("page", attributes);
		request.setAttribute("navigator", categories);
		
		request.setAttribute("message", request.getSession().getAttribute("message"));
		request.getSession().removeAttribute("message");
		
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int result = 0;
		boolean validated = false;
		
		Messages message = new Messages(request);
		
		if (request.getParameter("login") != null) {
			
			Users_Dao user = new Users_Dao();
			
			user = setData(request, user);
			
			Users_Model modelObject = new Users_Model(user);
			
			Users_Validator validatorObject = new Users_Validator();
			
			validated = validatorObject.check(user);

			if (validated) {
				
				try {
					
					user = modelObject.authorize(user);
				} 
				catch (SQLException e) {
					
					e.printStackTrace();
				} 
				catch (NoSuchAlgorithmException e) {
				
					e.printStackTrace();
				} 
				catch (ParseException e) {
					
					e.printStackTrace();
				}
				if (user.getId() > 0) {
					
					result = user.getId();
					
					request.getSession().setAttribute("user_id", user.getId());
					request.getSession().setAttribute("user_status", user.getStatus());
					request.getSession().setAttribute("user_login", user.getLogin());
					
					response.sendRedirect("/admin");
				}
				else {
					
					response.sendRedirect("/" + MODULE);
				}
			}
			else {
				
				response.sendRedirect("/" + MODULE);
			}		
			message.setMessage(MODULE, result);
			request = message.show();
		}
	}
}
