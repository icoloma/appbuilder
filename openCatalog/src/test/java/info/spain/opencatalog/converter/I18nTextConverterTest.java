package info.spain.opencatalog.converter;

import static junit.framework.Assert.assertEquals;
import info.spain.opencatalog.domain.I18nText;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * I18nText Converter
 * @author ehdez
 */

public class I18nTextConverterTest {
	
	@Test
	/**
	 * Input 18nText().set("ES","casa").set("EN", "home").set("FR","maison") --> 
	 * { "ES": "casa", "EN": "home", "FR": "maison" }
	 * Default converter add "i18n" object
	 */
	public void testReader(){
		I18nTextReaderConverter converter = new I18nTextReaderConverter();
		BasicDBObject dbobject = new BasicDBObject("ES","casa").append("EN", "home").append("FR","maison");
		I18nText result =  converter.convert(dbobject);
		assertEquals("casa", result.get("ES"));
		assertEquals("home", result.get("EN"));
		assertEquals("maison", result.get("FR"));
	}

	@Test
	public void testWriter(){
		I18nTextWriterConverter converter = new I18nTextWriterConverter();
		I18nText i18nText = new I18nText().set("ES","casa").set("EN", "home").set("FR","maison");
		DBObject dbobject = converter.convert(i18nText);
		assertEquals("casa", dbobject.get("ES"));
		assertEquals("home", dbobject.get("EN"));
		assertEquals("maison", dbobject.get("FR"));
		
	}


}
