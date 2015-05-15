package checkers;

import javax.servlet.http.HttpServletRequest;

public class Parameters {

	private int id;
	private String action;
	private HttpServletRequest request;
	
	public Parameters() {		
	}
	
	public Parameters(HttpServletRequest request) {
		
		this.request = request;
	}

	public int getId() {
		
		try {
			
			id = Integer.parseInt(request.getParameter("id"));
		}
		catch (NumberFormatException e) {
			
			id = 0;
		}
		
		return id;
	}
	
	public int getArchiveId() {
		
		try {
			
			id = Integer.parseInt(request.getParameter("archive_id"));
		}
		catch (NumberFormatException e) {
			
			id = 0;
		}
		
		return id;
	}
	
	public String getAction() {
		
		try {
			
			action = request.getParameter("action").toLowerCase();
		}
		catch (Exception e) {
			
			action = "list";
		}
		
		return action;
	}

	public String getButton() {
		
		try {
			
			action = request.getParameter("confirm").toLowerCase();
		}
		catch (Exception e) {
			
			action = "cancel";
		}
		
		return action;
	}
}
