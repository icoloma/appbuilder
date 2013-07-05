package info.spain.opencatalog.converter;

import info.spain.opencatalog.domain.poi.types.BasicPoiType;
import info.spain.opencatalog.domain.poi.types.PoiTypeRepository;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;

@Component
public class PoiTypeReaderConverter implements Converter< DBObject ,BasicPoiType> {

	@Override
	public BasicPoiType convert(DBObject source) {
		BasicPoiType result = PoiTypeRepository.getType((String)source.get("id"));
		return result;
	}

}
