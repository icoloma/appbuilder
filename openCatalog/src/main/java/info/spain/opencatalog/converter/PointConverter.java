package info.spain.opencatalog.converter;

import info.spain.opencatalog.domain.GeoLocation;

import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.geo.Point;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Convert {"lng":x, "lat":y} to Point(x,y)
 *   
 * @author ehdez
 *
 */
public class PointConverter implements  Converter<String[],Point>{
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
		
	@Override
	public Point convert(String[] source) {
		String json = source[0];
		GeoLocation geoLocation = new GeoLocation();
		try {
			json = URLDecoder.decode(json, "UTF-8").replaceFirst("lat=", "\"lat\":").replaceFirst("lng=", "\"lng\":");
			
			geoLocation =  new ObjectMapper().readValue(json,GeoLocation.class);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new IllegalArgumentException(e.getMessage());
		}
		return new Point( geoLocation.getLng(), geoLocation.getLat());
		
	}

}
