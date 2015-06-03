package utilities;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import models.Config_Model;

public class Dates {
	
	private int offset;
	private boolean year_first;
	
	public Dates() throws SQLException, ParseException {
		
		setOffset();
		setFormat();
	}
	
	private void setOffset() throws SQLException, ParseException {
		
		Config_Model config = new Config_Model();
		
		Boolean time_offset_active = Boolean.parseBoolean(config.getConfig("time_offset_active"));
		int time_offset_hours = Integer.parseInt(config.getConfig("time_offset_hours"));
		
		offset = time_offset_active ? time_offset_hours : 0;
	}
	
	private void setFormat() throws SQLException, ParseException {
		
		Config_Model config = new Config_Model();
		
		year_first = Boolean.parseBoolean(config.getConfig("date_year_first"));
	}
	
	public String getShortDate(Date longDate) {
		
		String custom = "";
		
		if (longDate == null) return custom;

		Calendar cal = Calendar.getInstance();
		cal.setTime(longDate);
		cal.add(Calendar.HOUR_OF_DAY, offset); 
		
		if (year_first) {
			
			custom += String.format("%04d", cal.get(Calendar.YEAR)) + "-";
			custom += String.format("%02d", cal.get(Calendar.MONTH) + 1) + "-";
			custom += String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)) + " ";
		}
		else {
			
			custom += String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)) + "-";
			custom += String.format("%02d", cal.get(Calendar.MONTH) + 1) + "-";
			custom += String.format("%04d", cal.get(Calendar.YEAR)) + " ";
		}
		custom += String.format("%02d", cal.get(Calendar.HOUR_OF_DAY)) + ":";
		custom += String.format("%02d", cal.get(Calendar.MINUTE));

		return custom;
	}
}
