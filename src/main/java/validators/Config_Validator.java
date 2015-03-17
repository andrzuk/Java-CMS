package validators;

import dao.Config_Dao;

public class Config_Validator {

	public boolean validate(Config_Dao config) {
		
		if (config == null) return false;
		
		if (config.getKey_name().isEmpty() || config.getKey_value().isEmpty() || config.getMeaning().isEmpty()) {
			
			return false;
		}
		
		return true;
	}
}

