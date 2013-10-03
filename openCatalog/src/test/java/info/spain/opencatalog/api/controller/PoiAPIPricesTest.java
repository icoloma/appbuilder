package info.spain.opencatalog.api.controller;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;
import info.spain.opencatalog.repository.PoiRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
       

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextConfiguration({ "classpath:APITest-config.xml"})
@ActiveProfiles("dev")
public class PoiAPIPricesTest {
	
	private static final MediaType JSON_UTF8 = MediaType.parseMediaType("application/json;charset=UTF-8");
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private PoiRepository repo;
	
	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
	}

	
	/**
	 * Test Create Lodging Price
	 * Hay que especificar en el price "type" : "ROOM"	
	 */
	@Test
	public void testLodgingPrice() throws Exception {
		String type = PoiTypeID.HOTEL.toString();
		String flags = "['GUIDED_TOUR']";
		String roomTypes = "['HAB3','HAB2','HAB1']";
		String prices = "[{" +
				"'type' : 'ROOM'," +
				"'roomType' : 'HAB1'," +
				"'meal' : 'AD', " +
				"'price' : 25, " +
				"'timeTable' : { }," +
				"'observations' : { 'es' : 'Temporada media' } " +
			"}]";

		String json =  "{" +
			"'type': '" + type + "'," +
			"'name':{" +
				"'es':'Hotel 1'," +
			"}," +
			"'address':{" +
				"'route':'route'," +
				"'adminArea1':'area1'," +
				"'adminArea2':'area2'," +
				"'zipCode':'zipCode'" +
			"}," +
			"'location':{" +
				"'lat':40.45259106740161," +
				"'lng':-3.7391396261243433" +
			"}," +
			"'flags': " + flags + "," +
			"'roomTypes': " + roomTypes + "," + 
		    "'prices': " + prices + 
			"}";
		json = json.replaceAll("'", "\"");
		
		System.out.println( "POI:" + json);
		 MvcResult result = this.mockMvc.perform(post("/poi")
	    	.param("type", type)
	    	.contentType(JSON_UTF8)
			.content(json))
			.andExpect(status().isCreated())
			.andReturn();
		 
		assertNotNull(getIdFromLocation(result));
	}
	
	
	@Test
	public void testLodgingUnkowedPriceType() throws Exception {
		String type = PoiTypeID.HOTEL.toString();
		String flags = "['GUIDED_TOUR']";
		String roomTypes = "['HAB3','HAB2','HAB1']";
		String prices = "[{" +
				"'type' : 'UNKOWED'," +
				"'roomType' : 'HAB1'," +
				"'meal' : 'AD', " +
				"'price' : 25, " +
				"'timeTable' : { }," +
				"'observations' : { 'es' : 'Temporada media' } " +
			"}]";

		String json =  "{" +
			"'type': '" + type + "'," +
			"'name':{" +
				"'es':'Hotel 1'," +
			"}," +
			"'address':{" +
				"'route':'route'," +
				"'adminArea1':'area1'," +
				"'adminArea2':'area2'," +
				"'zipCode':'zipCode'" +
			"}," +
			"'location':{" +
				"'lat':40.45259106740161," +
				"'lng':-3.7391396261243433" +
			"}," +
			"'flags': " + flags + "," +
			"'roomTypes': " + roomTypes + "," + 
		    "'prices': " + prices + 
			"}";
		json = json.replaceAll("'", "\"");
		
		System.out.println( "POI:" + json);
		 MvcResult result = this.mockMvc.perform(post("/poi")
	    	.param("type", type)
	    	.contentType(JSON_UTF8)
			.content(json))
			.andExpect(status().isInternalServerError())
			.andReturn();
		 String response = result.getResponse().getContentAsString();
		 assertTrue(response.contains("Unrecognized field \"roomType\""));
	}

	
	private String getIdFromLocation(MvcResult result){
		String location = result.getResponse().getHeader("Location");
		assertNotNull(location);
		String id = location.substring( location.lastIndexOf('/') + 1);
		assertNotNull(id);
		log.trace("id from Location:" + id);
		return id;
	}
	

}
