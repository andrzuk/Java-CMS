package controllers;

import java.io.IOException;
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

import models.Categories_Model;
import checkers.Page_Meta;
import dao.Categories_Dao;

@WebServlet("/resource_not_found")

public class Not_Found_Servlet extends HttpServlet {
	
	private static final String MODULE = "not_found";
	
	private static final long serialVersionUID = 1L;
       
    public Not_Found_Servlet() {
    
    	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/public_page.jsp");
		
		Map<String, String> attributes = new HashMap<String, String>();
		
		List<Categories_Dao> categories = null;
		
		Categories_Model importObject = new Categories_Model();
		Page_Meta pageObject = new Page_Meta();
		
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
		attributes.put("title", "RESOURCE NOT FOUND");
		attributes.put("logged", (String) request.getSession().getAttribute("user_login"));
		
		request.setAttribute("page", attributes);
		request.setAttribute("navigator", categories);
		
		request.setAttribute("message", request.getSession().getAttribute("message"));
		request.getSession().removeAttribute("message");

		dispatcher.forward(request, response);
	}
}
