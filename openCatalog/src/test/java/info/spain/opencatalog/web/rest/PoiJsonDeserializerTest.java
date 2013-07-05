package info.spain.opencatalog.web.rest;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.types.BasicPoiType;
import info.spain.opencatalog.domain.poi.types.PoiTypeRepository;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PoiJsonDeserializerTest {

	@Test
	public void testDeserializeBasicPoi() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = "{'type':'BEACH','name':{'es':'foo'}}".replaceAll("'", "\"");
		BasicPoi poi = mapper.readValue(json , BasicPoi.class);
		assertPoi(poi,PoiTypeRepository.getType("BEACH"));
	}

	@Test
	public void testDeserializeLodgingPoi() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = "{'type':'HOTEL','name':{'es':'foo'}}".replaceAll("'", "\"");
		BasicPoi poi = mapper.readValue(json , BasicPoi.class);
		assertPoi(poi,PoiTypeRepository.getType("HOTEL"));
	}
	
	private void assertPoi(AbstractPoi poi, BasicPoiType type ){
		assertNotNull(poi);
		assertNotNull(poi.getType());
		assertEquals( type.getId() , poi.getType().getId());
	}
}
