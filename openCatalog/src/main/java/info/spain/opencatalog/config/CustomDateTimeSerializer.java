package info.spain.opencatalog.config;

import java.io.IOException;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateTimeSerializer extends JsonSerializer<DateTime> {

	@Override
	public Class<DateTime> handledType() {
		return DateTime.class;
	}

	@Override
	public void serialize(DateTime dateTime, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(dateTime.toString()); // ISO8601
	}
}