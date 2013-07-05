package info.spain.opencatalog.config;

import info.spain.opencatalog.domain.poi.types.BasicPoiType;
import info.spain.opencatalog.domain.poi.types.PoiTypeRepository;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class PoiTypeDeserializer extends JsonDeserializer<BasicPoiType>{
	
	@Override
	public BasicPoiType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return PoiTypeRepository.getType(jp.getText());
	}
	
}
