package utilities;

import javax.servlet.http.HttpServletRequest;

public class Messages {
	
	public enum type { ERROR, WARNING, QUESTION, INFORMATION }
	
	private String action;
	private int result;
	private HttpServletRequest request;
	private Message message;
	
	public Messages() {
	}
	
	public Messages(HttpServletRequest request) {
		
		this.request = request;
	}
	
	public Messages(HttpServletRequest request, String action, int result) {
		
		this.request = request;
		this.action = action;
		this.result = result;
	}
	
	public void setMessage(String action, int result) {
		
		this.action = action;
		this.result = result;
	}

	public HttpServletRequest show() {
		
		message = new Message();
		
		if (action.equals("new")) {
			
			if (result > 0) {
				
				message.setMessage(type.INFORMATION, "Rekord został pomyślnie dopisany.");
			}
			else {
				
				message.setMessage(type.ERROR, "Rekord nie został dopisany.");
			}
		}
		else if (action.equals("edit")) {
			
			if (result > 0) {
				
				message.setMessage(type.INFORMATION, "Rekord został pomyślnie zapisany.");
			}
			else {
				
				message.setMessage(type.ERROR, "Rekord nie został zapisany.");
			}
		}
		else if (action.equals("delete")) {
			
			if (result > 0) {
				
				message.setMessage(type.INFORMATION, "Rekord został pomyślnie usunięty.");
			}
			else {
				
				message.setMessage(type.ERROR, "Rekord nie został usunięty.");
			}
		}
		else if (action.equals("password")) {
			
			if (result > 0) {
				
				message.setMessage(type.INFORMATION, "Hasło zostało pomyślnie zmienione.");
			}
			else {
				
				message.setMessage(type.ERROR, "Hasło nie zostało zmienione.");
			}
		}
		else if (action.equals("login")) {
			
			if (result > 0) {
				
				message.setMessage(type.INFORMATION, "Zostałeś pomyślnie zalogowany do serwisu.");
			}
			else {
				
				message.setMessage(type.ERROR, "Login lub email lub hasło są nieprawidłowe.");
			}
		}
		else if (action.equals("comment")) {
			
			if (result > 0) {
				
				message.setMessage(type.INFORMATION, "Twój komentarz został pomyślnie zapisany. Po zatwierdzeniu przez moderatora pojawi się na liście komentarzy.");
			}
			else {
				
				message.setMessage(type.ERROR, "Twój komentarz nie został zapisany.");
			}
		}
		else if (action.equals("user_message")) {
			
			if (result > 0) {
				
				message.setMessage(type.INFORMATION, "Twoja wiadomość została pomyślnie wysłana i będzie niezwłocznie rozpatrzona.");
			}
			else {
				
				message.setMessage(type.ERROR, "Twoja wiadomość nie została wysłana.");
			}
		}
		else if (action.equals("import")) {
			
			if (result > 0) {
				
				message.setMessage(type.INFORMATION, "Znacznik obrazka został skopiowany do schowka.");
			}
			else {
				
				message.setMessage(type.ERROR, "Znacznik obrazka nie został skopiowany.");
			}
		}
		else {
			
			message.setMessage(type.QUESTION, "Akcja nie obsłużona. Co miałeś na myśli?");
		}
		
		request.getSession().setAttribute("message", message);
		
		return request;
	}
	
	public HttpServletRequest hide() {
		
		request.getSession().removeAttribute("message");
		
		return request;
	}
}
