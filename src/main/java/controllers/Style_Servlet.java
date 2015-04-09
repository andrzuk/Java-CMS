package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;

import utilities.Messages;
import models.Config_Model;
import checkers.ACL;
import checkers.Page_Meta;
import checkers.Parameters;

@WebServlet("/styles")

public class Style_Servlet extends HttpServlet {
	
	private static final String MODULE = "styles";
	
	private static final long serialVersionUID = 1L;
       
    public Style_Servlet() {
    	
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ACL acl = new ACL();
		
		if (!acl.getAccess(request.getSession().getAttribute("user_status"), request.getSession().getAttribute("user_id"), MODULE)) {
			
			response.sendRedirect("/access_denied");
			return;
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin_page.jsp");
		
		Map<String, String> attributes = new HashMap<String, String>();
		
		Page_Meta pageObject = new Page_Meta();
		
		attributes = pageObject.setPageMeta(attributes);
		
		Config_Model config = new Config_Model();
		
		String shortname = "";
		String filename = "";
		String style = "";
		
		try {
			
			shortname = config.getConfig("custom_style");
			if (shortname == null) shortname = "custom.css";
			filename = System.getenv("OPENSHIFT_DATA_DIR") + shortname;
			
			if (Files.exists(Paths.get(filename))) {
				
				style = FileUtils.readFileToString(new File(filename));
			}
		}
		catch (SQLException e) {
			
			e.printStackTrace();
		} 
		catch (ParseException e) {
			
			e.printStackTrace();
		} 
		
		attributes.put("module", MODULE);
		attributes.put("title", MODULE.toUpperCase());
		attributes.put("action", "edit");
		
		attributes.put("logged", (String) request.getSession().getAttribute("user_login"));
		request.setAttribute("page", attributes);
		request.setAttribute("shortname", shortname);
		request.setAttribute("filename", filename);
		request.setAttribute("style", style);
		
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
		Parameters parameter = new Parameters(request);
		Messages message = new Messages(request);
		String button = parameter.getButton();
		String action = "edit";
		
		if (button.equals("update")) {
			
			FileUtils.writeStringToFile(new File(request.getParameter("filename")), request.getParameter("contents"));
			result = 1;
		}
		
		response.sendRedirect("/admin");
		
		message.setMessage(action, result);
		request = message.show();
	}
}
