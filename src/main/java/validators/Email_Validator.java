package validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email_Validator {

	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public Email_Validator() {
		
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	public boolean validate(final String email) {

		matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
