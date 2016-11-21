package checkers;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;

import models.Config_Model;

public class Page_Meta {
	
	private String main_title;
	private String main_description;
	private String main_keywords;
	private String main_favicon;
	private String main_footer;
	private String main_author;
	private String logo_image;
	private String logo_caption;
	private String main_style;
	private String custom_style;
	private String custom_script;
	private String site_verification;
	private String bing_verification;
	
	public Page_Meta() {
		
		Config_Model configObject = new Config_Model();
		
		try {
			
			setMain_title(configObject.getConfig("main_title"));
			setMain_description(configObject.getConfig("main_description"));
			setMain_keywords(configObject.getConfig("main_keywords"));
			setMain_favicon(configObject.getConfig("main_favicon"));
			setMain_footer(configObject.getConfig("main_footer"));
			setMain_author(configObject.getConfig("main_author"));
			setLogo_image(configObject.getConfig("logo_image"));
			setLogo_caption(configObject.getConfig("logo_caption"));
			setMain_style(configObject.getConfig("main_style"));
			setCustom_style(configObject.getConfig("custom_style"));
			setCustom_script(configObject.getConfig("custom_script"));
			setSite_verification(configObject.getConfig("site_verification"));
			setBing_verification(configObject.getConfig("bing_verification"));
		}
		catch (SQLException e) {
		
			e.printStackTrace();
		} 
		catch (ParseException e) {
		
			e.printStackTrace();
		}		
	}
	
	public Map<String, String> setPageMeta(Map<String, String> attributes) {
		
		attributes.put("main_title", getMain_title());
		attributes.put("main_description", getMain_description());
		attributes.put("main_keywords", getMain_keywords());
		attributes.put("main_favicon", getMain_favicon());
		attributes.put("main_footer", getMain_footer());
		attributes.put("main_author", getMain_author());
		attributes.put("logo_image", getLogo_image());
		attributes.put("logo_caption", getLogo_caption());
		attributes.put("main_style", getMain_style());
		attributes.put("custom_style", getCustom_style());
		attributes.put("custom_script", getCustom_script());
		attributes.put("site_verification", getSite_verification());
		attributes.put("bing_verification", getBing_verification());
		
		return attributes;
	}
	
	public String getMain_title() {
	
		return main_title;
	}
	
	public void setMain_title(String main_title) {
	
		this.main_title = main_title;
	}
	
	public String getMain_description() {
	
		return main_description;
	}
	
	public void setMain_description(String main_description) {
	
		this.main_description = main_description;
	}
	
	public String getMain_keywords() {
	
		return main_keywords;
	}
	
	public void setMain_keywords(String main_keywords) {
	
		this.main_keywords = main_keywords;
	}

	public String getMain_favicon() {
		
		return main_favicon;
	}

	public void setMain_favicon(String main_favicon) {
		
		this.main_favicon = main_favicon;
	}

	public String getMain_footer() {
		
		return main_footer;
	}

	public void setMain_footer(String main_footer) {
		
		this.main_footer = main_footer;
	}

	public String getMain_author() {
	
		return main_author;
	}

	public void setMain_author(String main_author) {
	
		this.main_author = main_author;
	}

	public String getLogo_image() {
		
		return logo_image;
	}

	public void setLogo_image(String logo_image) {
		
		this.logo_image = logo_image;
	}

	public String getLogo_caption() {
		
		return logo_caption;
	}

	public void setLogo_caption(String logo_caption) {
		
		this.logo_caption = logo_caption;
	}

	public String getMain_style() {
		
		return main_style;
	}

	public void setMain_style(String main_style) {
		
		this.main_style = main_style;
	}
	
	public String getSite_verification() {
	
		return site_verification;
	}
	
	public void setSite_verification(String site_verification) {
	
		this.site_verification = site_verification;
	}

	public String getBing_verification() {
	
		return bing_verification;
	}
	
	public void setBing_verification(String bing_verification) {
	
		this.bing_verification = bing_verification;
	}

	public String getCustom_style() {
		
		return custom_style;
	}

	public void setCustom_style(String custom_style) {
		
		this.custom_style = custom_style;
	}

	public String getCustom_script() {
		
		return custom_script;
	}

	public void setCustom_script(String custom_script) {
		
		this.custom_script = custom_script;
	}
}
