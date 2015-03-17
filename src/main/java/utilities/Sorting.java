package utilities;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Sorting {
	
	private String sort_field;
	private String sort_order;
	private List<String> fields_names;
	private List<String> fields_widths;
	private List<String> fields_aligns;
	
	private HttpServletRequest request;
	
	public Sorting(HttpServletRequest request) {
		
		sort_field = "id";
		sort_order = "ASC";

		this.request = request;
		
		if (request.getParameter("sort") != null) {
			
			sort_field = request.getParameter("sort");
			request.getSession().setAttribute("sort_field", sort_field);
		}
		if (request.getParameter("order") != null) {
			
			sort_order = request.getParameter("order");
			request.getSession().setAttribute("sort_order", sort_order);
		}
		
		if (request.getSession().getAttribute("sort_field") != null) {
			
			sort_field = (String) request.getSession().getAttribute("sort_field"); 
		}
		else {
			
			request.getSession().setAttribute("sort_field", sort_field);
		}

		if (request.getSession().getAttribute("sort_order") != null) {
			
			sort_order = (String) request.getSession().getAttribute("sort_order"); 
		}
		else {
			
			request.getSession().setAttribute("sort_order", sort_order);
		}
	}
	
	public HttpServletRequest getRequest() {
	
		return request;
	}

	public String getSort_field() {
		
		return sort_field;
	}

	public void setSort_field(String sort_field) {
		
		this.sort_field = sort_field;
	}

	public String getSort_order() {
		
		return sort_order;
	}

	public void setSort_order(String sort_order) {
		
		this.sort_order = sort_order;
	}

	public List<String> getFields_names() {
		
		return fields_names;
	}

	public List<String> getFields_widths() {
		
		return fields_widths;
	}

	public List<String> getFields_aligns() {
		
		return fields_aligns;
	}

	public void setFields_list(List<String> fields_names, List<String> fields_widths, List<String> fields_aligns) {
		
		this.fields_names = fields_names;
		this.fields_widths = fields_widths;
		this.fields_aligns = fields_aligns;
		
		if (!fields_names.contains((String) sort_field)) {
			
			sort_field = "id";
			sort_order = "ASC";
			request.getSession().setAttribute("sort_field", sort_field);
			request.getSession().setAttribute("sort_order", sort_order);
		}
	}
}
