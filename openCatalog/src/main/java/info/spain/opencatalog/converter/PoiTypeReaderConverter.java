package info.spain.opencatalog.converter;

import info.spain.opencatalog.domain.poi.PoiType;
import info.spain.opencatalog.domain.poi.PoiTypeID;
import info.spain.opencatalog.domain.poi.PoiTypes;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;

@Component
public class PoiTypeReaderConverter implements Converter< DBObject ,PoiType> {

	@Override
	public PoiType convert(DBObject source) {
		PoiType result = PoiTypes.valueOf(PoiTypeID.valueOf((String)source.get("id")));
		return result;
	}

}
