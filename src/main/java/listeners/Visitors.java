package listeners;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import dao.Visitors_Dao;
import models.Visitors_Model;

@WebListener

public class Visitors implements ServletRequestListener {

    private List<String> excludes;
    
	public Visitors() {
		
		excludes = new ArrayList<String>();
		excludes.add(".png");
		excludes.add(".jpg");
		excludes.add(".css");
		excludes.add(".js");
    }

    public void requestDestroyed(ServletRequestEvent servletRequestEvent)  { 

    }

    public void requestInitialized(ServletRequestEvent servletRequestEvent)  { 

    	HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();
    	
    	String visitor_ip = request.getRemoteAddr();
    	String request_uri = request.getRequestURI();
    	String http_referer = request.getHeader("Referer");

		Visitors_Dao visitor = new Visitors_Dao();
		
		visitor.setHttp_referer(http_referer);
		visitor.setVisitor_ip(visitor_ip);
		visitor.setRequest_uri(request_uri);
		
		Visitors_Model modelObject = new Visitors_Model(visitor);
		
		boolean ommit;
		
		try {
			
			if (http_referer != null) {
				
				ommit = false;
				
				if (request_uri.length() > 3)
					for (String exclude : excludes)
						if (request_uri.substring(request_uri.length() - exclude.length(), request_uri.length()).equals(exclude)) 
							ommit = true;
				
				if (!ommit) {
					
					int result = modelObject.save();
				}
			}
		} 
		catch (SQLException e) {

			e.printStackTrace();
		}
    }
}
