package controllers;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.Images_Dao;
import models.Config_Model;
import models.Images_Model;
import utilities.Messages;
import utilities.Paginator;
import utilities.Sorting;
import checkers.ACL;
import checkers.Page_Meta;
import checkers.Parameters;

@WebServlet("/images")
@MultipartConfig

public class Images_Servlet extends HttpServlet {
	
	private static final String MODULE = "images";
	
	private static final long serialVersionUID = 1L;
	
	int BUFFER_LENGTH = 4096;
	
    public Images_Servlet() {
    	
        super();
    }

	private String getFileName(Part part) {

		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
	
	private Images_Dao setData(HttpServletRequest request, Images_Dao image) throws FileNotFoundException, IOException, ServletException {
		
	    for (Part part : request.getParts()) {
	        InputStream is = request.getPart(part.getName()).getInputStream();
	        long fileSize = request.getPart(part.getName()).getSize();
	        String fileName = getFileName(part);
	        if (fileName != null) {	        	
		        fileName = fileName.replaceAll(" ", "_");
		        image.setFile_name(fileName);
				image.setFile_size(fileSize);
	        }
	        FileOutputStream os = new FileOutputStream(System.getenv("OPENSHIFT_DATA_DIR") + fileName);
	        byte[] bytes = new byte[BUFFER_LENGTH];
	        int read = 0;
	        while ((read = is.read(bytes, 0, BUFFER_LENGTH)) != -1) {
	            os.write(bytes, 0, read);
	        }
	        os.flush();
	        is.close();
	        os.close();
	    }
	    for (Part part : request.getParts()) {
	        InputStream is = request.getPart(part.getName()).getInputStream();
	        BufferedImage img = ImageIO.read(is);
			if (img != null) { 
				
				image.setWidth(img.getWidth());
				image.setHeight(img.getHeight());
			}
	        is.close();
	    }
		image.setAuthor_id((Integer) request.getSession().getAttribute("user_id"));

		return image;
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ACL acl = new ACL();
		
		if (!acl.getAccess(request.getSession().getAttribute("user_status"), request.getSession().getAttribute("user_id"), MODULE)) {
			
			response.sendRedirect("/access_denied");
			return;
		}
		
		Parameters parameter = new Parameters(request);
		
		int id = parameter.getId();
		String action = parameter.getAction();

		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin_page.jsp");
		
		Map<String, String> attributes = new HashMap<String, String>();
		Map<String, String> preview = new HashMap<String, String>();
		
		Page_Meta pageObject = new Page_Meta();
		Config_Model config = new Config_Model();
		
		attributes = pageObject.setPageMeta(attributes);
		
		if (action.equals("new")) {
			
			attributes.put("action", action);
		}
		else if (action.equals("preview")) {
			
			Images_Dao image = null;
			
			Images_Model modelObject = new Images_Model();
			
			try {
				
				image = modelObject.getOne(id);
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", action);
			
			request.setAttribute("image", image);
		}
		else if (action.equals("delete")) {
			
			Images_Dao image = null;
			
			Images_Model modelObject = new Images_Model();
			
			try {
				
				image = modelObject.getOne(id);
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", action);
			
			request.setAttribute("image", image);
		}
		else if (action.equals("import")) {
			
			Images_Dao image = null;
			
			Images_Model modelObject = new Images_Model();
			
			try {
				
				image = modelObject.getOne(id);
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}

			String imgTag = "<!-- Import Image -->\n<div class=\"dc\">\n<img class=\"dynamic\" src=\"upload/"
					+ image.getFile_name() + "\" width=\"" + image.getWidth() + "\" height=\""
					+ image.getHeight() + "\" onload=\"showImage(this);\">\n</div>\n\n";
			
			request.getSession().setAttribute("import_image_tag", imgTag);

			Messages message = new Messages(request);

			response.sendRedirect("/pages");

			message.setMessage(action, 1);
			request = message.show();

			return;
		}
		else {
			
			List<Images_Dao> images = null;
			
			Images_Model modelObject = new Images_Model();
			
			List<String> columns = Arrays.asList("id", "preview", "file_name", "file_size", "width", "height", "login", "modified");
			List<String> widths = Arrays.asList("10%", "20%", "20%", "10%", "10%", "10%", "10%", "10%");
			List<String> aligns = Arrays.asList("left", "left", "left", "left", "left", "left", "left", "center");
			
			Paginator paginator = new Paginator(request);
			Sorting sorting = new Sorting(request);
			String filter = (String) request.getSession().getAttribute("search_text");
			
			try {
				
				modelObject.setFilter(filter);
				paginator.setRows_count(modelObject.getCount());
				request = paginator.getRequest();
				sorting.setFields_list(columns, widths, aligns);
				images = modelObject.getSegment(paginator, sorting);

				preview.put("width", config.getConfig("preview_image_list_width"));
				preview.put("height", config.getConfig("preview_image_list_height"));
			} 
			catch (SQLException e) {

				e.printStackTrace();
			} 
			catch (ParseException e) {
			
				e.printStackTrace();
			}
			
			attributes.put("action", "list");
			
			request.setAttribute("data", images);
			request.setAttribute("sorting", sorting);
			request.setAttribute("filter", filter);
		}

		attributes.put("module", MODULE);
		attributes.put("title", MODULE.toUpperCase());
		attributes.put("logged", (String) request.getSession().getAttribute("user_login"));
		request.setAttribute("page", attributes);
		request.setAttribute("preview", preview);
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
		Images_Model modelObject;
		Parameters parameter = new Parameters(request);
		Messages message = new Messages(request);
		
		int id = parameter.getId();
		String action = parameter.getAction();
		String button = parameter.getButton();
		
		if (action.equals("new")) {
			
			if (button.equals("register")) {
				
				try {
					
					Images_Dao image = new Images_Dao();
					image = setData(request, image);
					modelObject = new Images_Model(image);
					result = modelObject.save();
				} 
				catch (SQLException e) {
					
					e.printStackTrace();
				} 
				catch (FileNotFoundException e) {
					
					e.printStackTrace();
				}
				response.sendRedirect("/" + MODULE);
			}
			else {
				
				response.sendRedirect("/" + MODULE);
			}
			message.setMessage(action, result);
			request = message.show();
		}
		else if (action.equals("delete")) {
			
			modelObject = new Images_Model();

			if (button.equals("delete")) {
				
				try {
					
					Images_Dao image = modelObject.getOne(id);
					
					File aFile = new File(System.getenv("OPENSHIFT_DATA_DIR") + image.getFile_name());
					if (aFile.exists()) aFile.delete();
					
					result = modelObject.delete(id);
				} 
				catch (SQLException e) {
					
					e.printStackTrace();
				} 
				catch (ParseException e) {
					
					e.printStackTrace();
				}
			}
			response.sendRedirect("/" + MODULE);

			message.setMessage(action, result);
			request = message.show();
		}
		else {
			
			if (request.getParameter("search_button") != null) {

				request.getSession().setAttribute("search_text", request.getParameter("search_value"));
			}
			if (request.getParameter("clear_search") != null) {

				request.getSession().removeAttribute("search_text");
			}
			
			Paginator paginator = new Paginator(request);
			
			request = paginator.updatePage_index();
			
			response.sendRedirect("/" + MODULE);
		}
	}
}
