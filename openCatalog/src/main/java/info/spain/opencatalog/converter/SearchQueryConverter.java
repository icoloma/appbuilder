package info.spain.opencatalog.converter;

import info.spain.opencatalog.api.controller.SearchQuery;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @author ehdez
 *
 */
public class SearchQueryConverter implements  Converter<String[],SearchQuery>{

	
	ObjectMapper objectMapper = new ObjectMapper();
	
	private Logger log = org.slf4j.LoggerFactory.getLogger(getClass());
	
	@Override
	public SearchQuery convert(String[] source) {
		try {
			return objectMapper.readValue(source[0], SearchQuery.class);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
		
	}

}
