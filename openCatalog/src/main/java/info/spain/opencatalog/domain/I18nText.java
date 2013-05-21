package info.spain.opencatalog.domain;

import java.util.HashMap;

/**
 * mantains a text in differents languages
 * 
 * @author ehdez
 */

public class I18nText extends HashMap<String, String>{
	
	private static final long serialVersionUID = -4971932571258598098L;
	public static final String DEFAULT = "ES";
	
	public I18nText set(String lang, String value){
		super.put(lang, value);
		return this;
	}
	
		
}
