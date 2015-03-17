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

import dao.Categories_Dao;
import dao.Counts_Dao;
import dao.Pages_Dao;
import models.Categories_Model;
import models.Pages_Model;
import models.Comments_Model;
import checkers.Page_Meta;
import checkers.Parameters;

@WebServlet("/category")

public class Category_Servlet extends HttpServlet {
	
	private static final String MODULE = "category";
	
	private static final long serialVersionUID = 1L;
       
    public Category_Servlet() {

    	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Parameters parameter = new Parameters(request);
		
		int id = parameter.getId();
		List<Counts_Dao> counts = null;
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/public_page.jsp");
		
		Map<String, String> attributes = new HashMap<String, String>();
		
		Categories_Dao category = null;
		List<Categories_Dao> categories = null;
		List<Pages_Dao> sites = null;
		
		Pages_Model modelObject = new Pages_Model();
		Categories_Model importObject = new Categories_Model();
		Comments_Model commentsObject = new Comments_Model();
		Page_Meta pageObject = new Page_Meta();
		
		try {
			
			if (id == 0) {
				
				id = importObject.getFirstId();
			}
			category = importObject.getActive(id);
			categories = importObject.getActives();
			sites = modelObject.getArticleHeaders(id);
			counts = commentsObject.getCategoryCommentCounts(id);
		} 
		catch (SQLException e) {

			e.printStackTrace();
		} 
		catch (ParseException e) {
		
			e.printStackTrace();
		}
		
		pageObject.setMain_title(category.getCaption());
		attributes = pageObject.setPageMeta(attributes);
		
		attributes.put("module", MODULE);
		attributes.put("title", category != null ? category.getCaption() : "(Not found)");
		attributes.put("logged", (String) request.getSession().getAttribute("user_login"));
		
		request.setAttribute("page", attributes);
		request.setAttribute("navigator", categories);
		request.setAttribute("category", category);
		request.setAttribute("articles", sites.size());
		request.setAttribute("data", sites);
		request.setAttribute("counts", counts);
		
		dispatcher.forward(request, response);
	}
}
