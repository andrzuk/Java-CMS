package utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Descriptions {

	private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

	public String removeTags(String string) {
		
	    if (string == null || string.length() == 0) {
	    	
	        return string;
	    }

	    Matcher m = REMOVE_TAGS.matcher(string);
	    
	    return m.replaceAll("");
	}
	
	public String getIntroduce(String input, int words) {
		
		String result = "";
		String temp = input;
		boolean whole = false;
		
		for (int i = 0; i < words; i++) {
			
			if (temp.contains(" ")) {
				
				result += temp.substring(0, temp.indexOf(" ")) + " ";
				temp = temp.substring(temp.indexOf(" ") + 1, temp.length());
			}
			else {
				
				result += temp;
				whole = true;
				break;
			}
		}
		
		if (!whole) result += " [...]";
		
		return result;
	}
}
