import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Categories_Model;
import models.Pages_Model;
import dao.Categories_Dao;
import dao.Pages_Dao;

@WebServlet("/sitemap.xml")

public class SitemapGenerator extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public SitemapGenerator() {
    	
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/site_map.jsp");
		
		List<Categories_Dao> categories = null;
		List<Pages_Dao> sites = null;
		
		Categories_Model categoriesObject = new Categories_Model();
		Pages_Model pagesObject = new Pages_Model();
		
		try {
			
			categories = categoriesObject.getActives();
			sites = pagesObject.getFoundHeaders("%");
		} 
		catch (SQLException e) {

			e.printStackTrace();
		} 
		catch (ParseException e) {
		
			e.printStackTrace();
		}
		
		request.setAttribute("base_url", System.getenv("OPENSHIFT_APP_DNS"));
		request.setAttribute("categories", categories);
		request.setAttribute("sites", sites);

		dispatcher.forward(request, response);
	}
}
