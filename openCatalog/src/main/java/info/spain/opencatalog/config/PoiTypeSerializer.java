package info.spain.opencatalog.config;

import info.spain.opencatalog.domain.poi.types.BasicPoiType;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class PoiTypeSerializer extends JsonSerializer<BasicPoiType> {
	
	@Override
	public Class<BasicPoiType> handledType() {
		return BasicPoiType.class;
	}

	@Override
	public void serialize(BasicPoiType value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(value.getId().toString());
	}

}
