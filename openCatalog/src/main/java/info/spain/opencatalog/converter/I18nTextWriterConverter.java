package info.spain.opencatalog.converter;

import info.spain.opencatalog.domain.I18nText;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

//@Component
public class I18nTextWriterConverter implements Converter<I18nText, DBObject> {

	@Override
	public DBObject convert(I18nText source) {
		BasicDBObject result = new BasicDBObject();
		result.putAll(source);
		return result;
	}

}
