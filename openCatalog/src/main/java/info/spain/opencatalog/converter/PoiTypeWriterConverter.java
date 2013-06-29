package info.spain.opencatalog.converter;

import info.spain.opencatalog.domain.poi.PoiType;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class PoiTypeWriterConverter implements Converter<PoiType, DBObject> {

	@Override
	public DBObject convert(PoiType source) {
		BasicDBObject result = new BasicDBObject();
		result.append("id", source.getId().toString());
		return result;
	}

}

