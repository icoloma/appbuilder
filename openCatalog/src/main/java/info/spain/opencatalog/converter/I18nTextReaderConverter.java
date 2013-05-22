package info.spain.opencatalog.converter;

import info.spain.opencatalog.domain.I18nText;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;

public class I18nTextReaderConverter {
}

//FIXME: uncomment when fixed #8

//public class I18nTextReaderConverter implements Converter< DBObject,I18nText> {
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public I18nText convert(DBObject source) {
//		I18nText result = new I18nText();     
//		result.putAll(source.toMap());
//		return result;
//		return null;
//	}
//
//}
