package info.spain.opencatalog.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static junit.framework.Assert.*;

import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.DummyPoiFactory;
import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.Flag;
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
@ContextConfiguration({ "PoiAPITest-config.xml"})
@ActiveProfiles("dev")
public class PoiAPITest {
	
	public static final String NO_RESULTS = "{\n  \"links\" : [ ],\n  \"content\" : [ ]\n}";
	
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

	
	// TODO: Test POI images
	
	/**
	 * Test discover and GET of POI
	 * @throws Exception
	 */
	@Test
    public void testDiscoverAndGET() throws Exception {
		repo.deleteAll();
		AbstractPoi poi= DummyPoiFactory.newPoi("getPoi");
		AbstractPoi saved = repo.save(poi);
		
		// test poi
	    MvcResult result = this.mockMvc.perform(get("/poi/" + saved.getId())
			.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json;charset=UTF-8"))
			.andExpect(jsonPath("$.name.es").value(poi.getName().getEs()))
			.andReturn();     //FIXME: delete when fixed #8 
	    
	    String content = result.getResponse().getContentAsString();
	    log.debug( "result /poi/" + saved.getId() + ":\n" + content);
	    
	    // test discover
	    result = this.mockMvc.perform(get("/poi/" )
			.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json;charset=UTF-8"))
			.andExpect(jsonPath("$.content[0].name.es").value(poi.getName().getEs()))
			.andReturn(); //FIXME: delete when fixed #8
	    
	    content = result.getResponse().getContentAsString();
	    log.debug("result /poi/:\n" + content);
	    
    }

	/**
	 * Test POST of POI
	 * @throws Exception
	 */
	@Test
	public void testPOST() throws Exception {
		repo.deleteAll();
		String json = "{" +
				"'name':{" +
					"'es':'es-name'," +
					"'en':'en-name'," +
					"'de':'en-name'" +
				"}," +
				"'description':{" +
					"'es':'es-description'," +
					"'en':'en-description'," +
					"'de':'en-description'" +
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
				"'flags':['" + Flag.COMMON_GUIDED_TOUR+ "']" +
				"}";
		json = json.replaceAll("'", "\"");
		
		System.out.println( "POI:" + json);
	    this.mockMvc.perform(post("/poi")
	    	.contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
			.content(json))
			.andExpect(status().isCreated());
    }
	

	@Test
	public void tesFindByName() throws Exception {
		repo.deleteAll();
		AbstractPoi poi = DummyPoiFactory.newPoi("tesFindByName");
		AbstractPoi saved = repo.save(poi);
		MvcResult result = this.mockMvc.perform(get("/poi/search/byName").param("name", poi.getName().getEs()))
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$content[*].name.es").value(poi.getName().getEs()))
			.andReturn();
		String content = result.getResponse().getContentAsString();
	    log.debug("result /poi/search/byName_es:\n" + content);
	    repo.delete(saved);
	}
	
	@Test
	public void tesFindByLocationWithIn() throws Exception {
		repo.deleteAll();
		GeoLocation alaska = DummyPoiFactory.POI_ALASKA.getLocation();
		AbstractPoi poi = DummyPoiFactory.POI_TEIDE;
		AbstractPoi saved = repo.save(poi); 
		
		MvcResult result = this.mockMvc.perform(get("/poi/search/locationWithin")
				.param("lat", poi.getLocation().getLat().toString())
				.param("lng", poi.getLocation().getLng().toString())
				.param("radius", "5"))
	    	.andExpect(status().isOk())
	    	.andExpect(content().contentType("application/json"))
	    	.andExpect(jsonPath("$content[*].name.es").value(poi.getName().getEs()))
	    	.andReturn();
		String content = result.getResponse().getContentAsString();
	    log.debug("result /poi/search/name/locationWithin:\n" + content);
	    
	    // Test not found
	    result = this.mockMvc.perform(get("/poi/search/locationWithin")
				.param("lat", alaska.getLat().toString())
				.param("lng", alaska.getLng().toString())
				.param("radius", "5"))
	    	.andExpect(status().isOk())
	    	.andReturn();
	 	content = result.getResponse().getContentAsString();	 	 
	 	assertEquals(NO_RESULTS, content);
	    repo.delete(saved);
	}
	
	@Test
	public void tesFindByLocationNear() throws Exception {
		repo.deleteAll();
		AbstractPoi poi = DummyPoiFactory.POI_TEIDE;
		GeoLocation alaska = DummyPoiFactory.POI_ALASKA.getLocation();
		AbstractPoi saved = repo.save(poi); 
		
		// Test found
		MvcResult result = this.mockMvc.perform(get("/poi/search/byLocationNear")
				.param("location", "{\"lat\":" + poi.getLocation().getLat() + ", \"lng\":" + poi.getLocation().getLng() + "}")
				.param("distance", "5"))
	    	.andExpect(status().isOk())
	    	.andExpect(content().contentType("application/json"))
	    	.andExpect(jsonPath("$content[*].name.es").value(poi.getName().getEs()))
	    	.andReturn();
		String content = result.getResponse().getContentAsString();
	    log.debug("result /poi/search/locationNear:\n" + content);
	    
	    // Test Not found
	 	result = this.mockMvc.perform(get("/poi/search/byLocationNear")
				.param("location", "{\"lat\":" + alaska.getLat() + ", \"lng\":" + alaska.getLng() + "}")
				.param("distance", "5"))
	    	.andExpect(status().isOk())
	    	.andReturn();
	 	content = result.getResponse().getContentAsString();	 	 
	 	assertEquals(NO_RESULTS, content);
	    repo.delete(saved);
	}
	
	

}
