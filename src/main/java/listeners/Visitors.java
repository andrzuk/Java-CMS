package listeners;

import java.sql.SQLException;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import dao.Visitors_Dao;
import models.Visitors_Model;

@WebListener

public class Visitors implements ServletRequestListener {

    public Visitors() {
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
		
		try {
			
			if (http_referer != null) {
				
				int result = modelObject.save();
			}
		} 
		catch (SQLException e) {

			e.printStackTrace();
		}
    }
}
