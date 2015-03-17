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
import models.Comments_Model;
import models.Pages_Model;
import checkers.Page_Meta;
import dao.Categories_Dao;
import dao.Counts_Dao;
import dao.Pages_Dao;

@WebServlet("/search")

public class Search_Servlet extends HttpServlet {
	
	private static final String MODULE = "search";
	
	private static final long serialVersionUID = 1L;
       
    public Search_Servlet() {
    	
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.sendRedirect("/");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Counts_Dao> counts = null;
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/public_page.jsp");
		
		Map<String, String> attributes = new HashMap<String, String>();
		
		List<Categories_Dao> categories = null;
		List<Pages_Dao> sites = null;
		
		Pages_Model modelObject = new Pages_Model();
		Categories_Model importObject = new Categories_Model();
		Comments_Model commentsObject = new Comments_Model();
		Page_Meta pageObject = new Page_Meta();
		
		try {
			
			categories = importObject.getActives();
			sites = modelObject.getFoundHeaders(request.getParameter("search_value"));
			counts = commentsObject.getFoundCommentCounts(request.getParameter("search_value"));
		} 
		catch (SQLException e) {

			e.printStackTrace();
		} 
		catch (ParseException e) {
		
			e.printStackTrace();
		}
		
		pageObject.setMain_title(MODULE.toUpperCase());
		attributes = pageObject.setPageMeta(attributes);
		
		attributes.put("module", MODULE);
		attributes.put("title", "Wyszukiwanie: \"" + request.getParameter("search_value") + "\" - znaleziono: " + (sites != null ? sites.size() + "." : "(Nie znaleziono)"));
		attributes.put("logged", (String) request.getSession().getAttribute("user_login"));
		
		request.setAttribute("page", attributes);
		request.setAttribute("navigator", categories);
		request.setAttribute("data", sites);
		request.setAttribute("counts", counts);
		
		dispatcher.forward(request, response);
	}
}
