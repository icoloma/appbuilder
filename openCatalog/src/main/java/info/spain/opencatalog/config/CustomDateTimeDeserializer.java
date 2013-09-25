package info.spain.opencatalog.config;

import java.io.IOException;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomDateTimeDeserializer extends JsonDeserializer<DateTime>{
	
	@Override
	public DateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return new DateTime(jp.getText()); // ISO8601
	}
	
}
