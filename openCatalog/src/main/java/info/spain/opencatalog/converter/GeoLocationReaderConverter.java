package info.spain.opencatalog.converter;

import info.spain.opencatalog.domain.GeoLocation;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

@Component
public class GeoLocationReaderConverter implements Converter< DBObject ,GeoLocation> {

	@Override
	public GeoLocation convert(DBObject source) {
		BasicDBList list = (BasicDBList) source;
		// Order is important [Lng,Lat]
		GeoLocation result = new GeoLocation()
		.setLng((Double)list.get(0))
		.setLat((Double)list.get(1));
		return result;
	}

}
