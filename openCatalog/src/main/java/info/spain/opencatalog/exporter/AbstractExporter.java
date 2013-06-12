package info.spain.opencatalog.exporter;

import info.spain.opencatalog.domain.GeoLocation;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

public abstract class AbstractExporter {
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	protected static final Locale LOCALE_ES = new Locale("ES");
	
	protected MessageSource messageSource;
	protected ImageExporter imageExporter;
	
	protected String translate(String text, Locale locale) {
		return messageSource.getMessage(text, null, text, locale);
	}
	

	
	@SuppressWarnings("rawtypes")
	protected String asStringArray( List list){
		StringBuffer result = new StringBuffer("[");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			result.append(asQuotedString(object.toString()));
			if (iterator.hasNext()){
				result.append(", ");
			}
		}
		result.append("]");
		return result.toString();
	}
	
	protected String asQuotedString(String str){
		if (org.apache.commons.lang.StringUtils.isBlank(str)){
			return "null";
		} else {
			return "\"" + str + "\"";
		}
	}

	
	protected String getPathAsJSON(List<GeoLocation> path){
		StringBuilder result = new StringBuilder("[");
		for (Iterator<GeoLocation> iterator = path.iterator(); iterator.hasNext();) {
			GeoLocation loc = iterator.next();
			result.append("{")
			.append("\"lat\":").append(loc.getLat())
			.append(",\"lng\":").append(loc.getLng())
			.append("}");
			if (iterator.hasNext()){
				result.append(", ");
			}
		}
		result.append("]");
		return result.toString();
	}
	
	protected Double getNormLong(GeoLocation location){
		return Math.sin( location.getLat() * Math.PI / 180 ) * location.getLng();
	}
	

}
