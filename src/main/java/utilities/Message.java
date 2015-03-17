package utilities;

import utilities.Messages.type;

public class Message {

	public type type;	
	public String text;
	
	public void setType(type type) {
		
		this.type = type;
	}
	
	public void setText(String text) {
		
		this.text = text;
	}
	
	public type getType() {
		
		return this.type;
	}
	
	public String getText() {
		
		return this.text;
	}
	
	public void setMessage(type type, String text) {
		
		this.type = type;
		this.text = text;
	}
}
