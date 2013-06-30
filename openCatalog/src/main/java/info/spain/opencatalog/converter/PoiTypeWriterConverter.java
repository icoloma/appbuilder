package info.spain.opencatalog.converter;

import info.spain.opencatalog.domain.poi.types.BasicPoiType;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class PoiTypeWriterConverter implements Converter<BasicPoiType, DBObject> {

	@Override
	public DBObject convert(BasicPoiType source) {
		BasicDBObject result = new BasicDBObject();
		result.append("id", source.getId().toString());
		return result;
	}

}

