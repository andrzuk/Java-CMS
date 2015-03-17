package utilities;

public class Numbers {

	private String number;
	
	public Numbers(String number) {
		
		this.number = number;
	}
	
	public boolean isNumeric() {
		
		if (number.length() == 0) return false;
		
	    for (char c : number.toCharArray()) {
	    	
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}
	
	public String toString() {
		
		return number;
	}
	
	public int toInteger() {
		
		return Integer.parseInt(number);
	}
}
