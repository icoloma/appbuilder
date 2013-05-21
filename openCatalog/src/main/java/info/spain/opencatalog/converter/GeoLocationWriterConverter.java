package info.spain.opencatalog.converter;

import info.spain.opencatalog.domain.GeoLocation;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

@Component
public class GeoLocationWriterConverter implements Converter<GeoLocation, DBObject> {

	@Override
	public DBObject convert(GeoLocation source) {
		BasicDBList result = new BasicDBList();
		// Order is important [Lng,Lat]
		result.add(source.getLng());
		result.add(source.getLat());
	 	return result;
	}

}

