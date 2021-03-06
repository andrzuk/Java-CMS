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
import models.Config_Model;
import models.Pages_Model;
import models.Comments_Model;
import dao.Categories_Dao;
import dao.Pages_Dao;
import dao.Comments_Dao;
import dao.Counts_Dao;
import checkers.Page_Meta;
import checkers.Parameters;

@WebServlet("/page")

public class Page_Servlet extends HttpServlet {
	
	private static final String MODULE = "page";
		
	private static final long serialVersionUID = 1L;
       
    public Page_Servlet() {
    	
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Parameters parameter = new Parameters(request);
		
		int id = parameter.getId();
		Counts_Dao count = null;
		int previews = 0; 
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/public_page.jsp");
		
		Map<String, String> attributes = new HashMap<String, String>();
		
		List<Categories_Dao> categories = null;
		List<Comments_Dao> comments = null;
		Pages_Dao site = null;
		List<Pages_Dao> articles = null;
		
		Pages_Model modelObject = new Pages_Model();
		Categories_Model importObject = new Categories_Model();
		Comments_Model commentsObject = new Comments_Model();
		Page_Meta pageObject = new Page_Meta();
		Config_Model config = new Config_Model();
		String code_highlight_theme = null;
		
		try {
			
			categories = importObject.getActives();
			site = modelObject.getActive(id);
			comments = commentsObject.getActives(id);
			count = commentsObject.getArticleCommentCount(id);
			articles = modelObject.getArticleHeaders(site.getCategory_id());
			previews = modelObject.getArticleViewsCount(id);
			code_highlight_theme = config.getConfig("code_highlight_theme");
		} 
		catch (SQLException e) {

			e.printStackTrace();
		} 
		catch (ParseException e) {
		
			e.printStackTrace();
		}
		
		if (site == null) {
			
			response.sendRedirect("/resource_not_found");
			return;
		}
		
		pageObject.setMain_title(site.getTitle());
		pageObject.setMain_description(site.getDescription());
		attributes = pageObject.setPageMeta(attributes);
		
		attributes.put("module", MODULE);
		attributes.put("title", MODULE.toUpperCase() + ": " + (site != null ? site.getCaption() : "(Not found)"));
		attributes.put("logged", (String) request.getSession().getAttribute("user_login"));
		attributes.put("highlight_theme", code_highlight_theme);
		
		request.setAttribute("page", attributes);
		request.setAttribute("navigator", categories);
		request.setAttribute("current_category_id", site.getCategory_id());
		request.setAttribute("site", site);
		request.setAttribute("comments", comments);
		request.setAttribute("count", count);
		request.setAttribute("articles", articles);
		request.setAttribute("previews", previews);
		
		request.setAttribute("message", request.getSession().getAttribute("message"));
		request.getSession().removeAttribute("message");
				
		dispatcher.forward(request, response);
	}
}
