package utilities;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

public class Paginator {
	
	private int rows_count;
	private int rows_per_page;
	private int pages_count;
	private int page_index;
	private int starting_from;
	private int page_indexes;
	
	private ArrayList<String> page_buttons;
	
	private HttpServletRequest request;
	
	public void init() {
		
		page_index = 0;
		rows_per_page = 10;
		page_indexes = 5;		
	}
	
	public Paginator(HttpServletRequest request) {
		
		init();
		
		this.request = request;
		
		if (request.getSession().getAttribute("page_index") != null) {
			
			page_index = Integer.parseInt((String) request.getSession().getAttribute("page_index")); 
		}
		else {
			
			request.getSession().setAttribute("page_index", new Integer(page_index).toString());
		}

		if (request.getSession().getAttribute("rows_per_page") != null) {
			
			rows_per_page = Integer.parseInt((String) request.getSession().getAttribute("rows_per_page")); 
		}
		else {
			
			request.getSession().setAttribute("rows_per_page", new Integer(rows_per_page).toString());
		}

		if (request.getSession().getAttribute("rows_count") != null) {
			
			rows_count = Integer.parseInt((String) request.getSession().getAttribute("rows_count")); 
		}
		else {
			
			request.getSession().setAttribute("rows_count", new Integer(rows_count).toString());
		}
		
		pages_count = (int) Math.ceil((double) rows_count / (double) rows_per_page);		
	}
	
	public HttpServletRequest getRequest() {

		request.setAttribute("rows_count", rows_count);
		request.setAttribute("rows_count_str", NumberFormat.getNumberInstance(Locale.GERMAN).format(rows_count));
		request.setAttribute("rows_per_page", rows_per_page);
		request.setAttribute("page_index", page_index);
		request.setAttribute("pages_count", pages_count);
		request.setAttribute("pages_count_str", NumberFormat.getNumberInstance(Locale.GERMAN).format(pages_count));
		request.setAttribute("page_buttons", page_buttons);
		
		return request;
	}
	
	public int getRows_count() {
		
		return rows_count;
	}
	
	public void setRows_count(int rows_count) {
		
		this.rows_count = rows_count;
		
		request.getSession().setAttribute("rows_count", new Integer(rows_count).toString());
		
		pages_count = (int) Math.ceil((double) rows_count / (double) rows_per_page);		
		
		if (page_index >= pages_count) {
			
			page_index = 0;
			request.getSession().setAttribute("page_index", new Integer(page_index).toString());
		}

		setPage_buttons();
	}
	
	public int getRows_per_page() {
		
		return rows_per_page;
	}
	
	public void setRows_per_page(int rows_per_page) {
		
		this.rows_per_page = rows_per_page;
	}
	
	public int getPages_count() {
		
		return pages_count;
	}
	
	public void setPages_count(int pages_count) {
		
		this.pages_count = pages_count;
	}
	
	public int getPage_index() {
		
		return page_index;
	}
	
	public void setPage_index(int page_index) {
		
		this.page_index = page_index;
	}
	
	public HttpServletRequest updatePage_index() {
		
		String button_first = request.getParameter("first");
		String button_prev = request.getParameter("prev");
		String button_next = request.getParameter("next");
		String button_last = request.getParameter("last");
		String goto_page = request.getParameter("goto_page");
		String goto_button = request.getParameter("goto_button");
		String lines_on_page = request.getParameter("lines_on_page");
		
		Enumeration<String> params = request.getParameterNames();
		
		if (button_first != null) {
			
			page_index = 0;
		}
		if (button_prev != null) {
			
			if (page_index > 0) page_index--;
		}
		if (button_next != null) {
			
			if (page_index < pages_count - 1) page_index++;
		}
		if (button_last != null) {
			
			page_index = pages_count - 1;
		}
	
		while (params.hasMoreElements()) {
			
			String param = params.nextElement();

			if (param.length() > 5) {
				
				if (param.substring(0, 5).equals("page_")) {
					
					page_index = Integer.parseInt(request.getParameter(param)) - 1;
				}
			}
		}

		if (lines_on_page != null) {
			
			rows_per_page= Integer.parseInt(lines_on_page);
			request.getSession().setAttribute("rows_per_page", new Integer(rows_per_page).toString());
			page_index = 0;
		}
		if (goto_button != null) {
			
			if (goto_page != null) {
				
				Numbers number = new Numbers(goto_page);
				
				if (number.isNumeric()) {
					
					int index = number.toInteger() - 1;
					
					if (index >= 0 && index < pages_count) {
						
						page_index = index;
					}
				}
			}
		}
		
		request.getSession().setAttribute("page_index", new Integer(page_index).toString());
		
		return request;
	}
	
	public int getStarting_from() {
		
		return starting_from;
	}
	
	public void setStarting_from(int starting_from) {
		
		this.starting_from = starting_from;
	}
	
	public int getPage_indexes() {
		
		return page_indexes;
	}
	
	public void setPage_indexes(int page_indexes) {
		
		this.page_indexes = page_indexes;
	}
	
	public void setPage_buttons() {
		
		page_buttons = new ArrayList<String>();
		
		for (int i = page_indexes; i > 0 ; i--) {
			
			int idx = page_index - i;
			
			if (idx >= 0) page_buttons.add(new Integer(idx + 1).toString());
		}
		
		for (int i = 0; i <= page_indexes; i++) {
			
			int idx = page_index + i;
			
			if (idx < pages_count) page_buttons.add(new Integer(idx + 1).toString());
		}
	}
}
