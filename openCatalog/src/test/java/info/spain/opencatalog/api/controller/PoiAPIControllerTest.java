package info.spain.opencatalog.api.controller;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import info.spain.opencatalog.domain.DummyPoiFactory;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.Price;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;
import info.spain.opencatalog.repository.PoiRepository;
import net.minidev.json.JSONArray;

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
public class PoiAPIControllerTest {
	
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
	 * Test discover and GET of POI
	 * @throws Exception
	 */
	@Test
    public void testDiscoverAndGET() throws Exception {
		repo.deleteAll();
		BasicPoi poi= DummyPoiFactory.newPoi("getPoi");
		BasicPoi saved = repo.save(poi);
		
		// test poi
	    MvcResult result = this.mockMvc.perform(get("/poi/" + saved.getId())
			.accept(JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(content().contentType(JSON_UTF8.toString()))
			.andExpect(jsonPath("$.name.es").value(poi.getName().getEs()))
			.andReturn();
	    
	    String content = result.getResponse().getContentAsString();
	    log.debug( "result /poi/" + saved.getId() + ":\n" + content);
	    
	    
	    JSONArray links = new JSONArray();
	    links.add("byName");
	    links.add("byLocationNear");
	    links.add("custom");
	    
	    // test discover
	    this.mockMvc.perform(get("/poi/" )
			.accept(JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(content().contentType(JSON_UTF8.toString()))
			.andExpect(jsonPath("$links[*].rel").value( links))
			;
	    
	    // check Poi Images
	    this.mockMvc.perform(get("/poi/" + saved.getId() + "/image")
				.accept(JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(JSON_UTF8.toString()))
				.andExpect(jsonPath("$.links").isArray())
				;    
    }
	
	@Test
	public void testUpdateNonExistingPoi() throws Exception {
		String json = "{" +
				"'type': 'BEACH'," +
				"'name':{" +
					"'es':'some other value'" +
				"}," +
				"'location':{" +
					"'lat':40.45259106740161," +
					"'lng':-3.7391396261243433" +
				"}," +
				"'syncInfo' : {" +
					"'sync' : 'true'," +
					"'imported' : 'true'," +
					"'originalId' : 'XXX'," +
					"'lastUpdate' : '20130101'" +
				"}," +
				"'flags':['" + Flag.GUIDED_TOUR+ "']" +
				"}";
		
		this.mockMvc.perform(put("/poi/NON_EXISTING_POI")
			.contentType(JSON_UTF8)
			.content(json))
			.andExpect(status().isNotFound())
		    .andReturn();
	}
	
	@Test
	public void testDeleteNonExistingPoi() throws Exception {
		this.mockMvc.perform(delete("/poi/NON_EXISTING_POI"))
			.andExpect(status().isNoContent());
		  
	}
	
	
	/**
	 * Test POST of POI
	 * @throws Exception
	 */
	@Test
	public void testPOST() throws Exception {
		String type = PoiTypeID.MUSEUM.toString();
		repo.deleteAll();
		String json = "{" +
				"'type': '" + type + "'," +
				"'name':{" +
					"'es':'es-name'," +
					"'en':'en-name'" +
				"}," +
				"'description':{" +
					"'es':'es-description'," +
					"'en':'en-description'" +
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
				"'flags':['" + Flag.GUIDED_TOUR+ "']," +
				"'prices': [{" +
						"'price' : 25, " +
						"'timeTable' : { }," +
						"'observations' : { 'es' : 'Temporada media' } " +
					"}]" +
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
	public void testSyncFlags() throws Exception{
		repo.deleteAll();
		BasicPoi poi= DummyPoiFactory.monument();
		BasicPoi saved = repo.save(poi);
		
		// let's change 
		//    sync = true        ---> will be ignored
		//    imported = true    ---> will be ignored
		//    originalId = "aaa" ---> will be ignored
		String json = "{" +
				"'type': '" + poi.getType() + "'," +
				"'name':{" +
					"'es':'some other value'" +
				"}," +
				"'location':{" +
					"'lat':40.45259106740161," +
					"'lng':-3.7391396261243433" +
				"}," +
				"'syncInfo' : {" +
					"'sync' : 'true'," +
					"'imported' : 'true'," +
					"'originalId' : 'XXX'," +
					"'lastUpdate' : '20130101'" +
				"}," +
				"'flags':['" + Flag.GUIDED_TOUR+ "']" +
				"}";
		
		this.mockMvc.perform(put("/poi/" + saved.getId())
			.contentType(JSON_UTF8)
			.content(json))
			.andExpect(status().isNoContent())
		    .andReturn();
		
		BasicPoi repoPoi = repo.findOne(saved.getId());
		
		// No se puede cambiar si no es un poi importado
		assertFalse(repoPoi.getSyncInfo().isSync());
		assertFalse(repoPoi.getSyncInfo().isImported());
		assertNull(repoPoi.getSyncInfo().getOriginalId());

		// Modificamos directamente la base de datos
		repoPoi.getSyncInfo()
			.setImported(true)
			.setSync(true)
			.setOriginalId("original");
		
		saved = repo.save(repoPoi);
		
		// let's change 
		//    sync = false
		//    imported = false     ----> will be ignored
		//    originalId = "changed" ---> will be ignored
		json = "{" +
				"'type': '" + poi.getType() + "'," +
				"'name':{" +
					"'es':'changing the name again'" +
				"}," +
				"'location':{" +
					"'lat':40.45259106740161," +
					"'lng':-3.7391396261243433" +
				"}," +
				"'syncInfo' : {" +	
					"'sync' : 'false'," +
					"'imported' : 'false'," + // será ignorado
					"'originalId' : 'changed'," + // será ignorado
					"'lastUpdate' : '20130101'" + // será ignorado
				"}," + 
				"'flags':['" + Flag.GUIDED_TOUR+ "']" +
				"}";
		
		this.mockMvc.perform(put("/poi/" + saved.getId())
			.contentType(JSON_UTF8)
			.content(json))
			.andExpect(status().isNoContent())
		    .andReturn();

		
		// Comprobamos los casos en que se puede modificar
		repoPoi = repo.findOne(saved.getId());
		assertTrue(repoPoi.getSyncInfo().isImported());
		assertEquals("original", repoPoi.getSyncInfo().getOriginalId());
		assertFalse(repoPoi.getSyncInfo().isSync());  
	}
	
	

	@Test
	public void testFindByName() throws Exception {
		repo.deleteAll();
		BasicPoi poi = DummyPoiFactory.newPoi("tesFindByName");
		BasicPoi saved = repo.save(poi);
		MvcResult result = this.mockMvc.perform(get("/poi/search/byName").param("name", poi.getName().getEs()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(JSON_UTF8.toString()))
			.andExpect(jsonPath("$content[*].name.es").value(poi.getName().getEs()))
			.andReturn();
		String content = result.getResponse().getContentAsString();
	    log.debug("result /poi/search/byName_es:\n" + content);
	    repo.delete(saved);
	}
	
	@Test
	public void testFindByLocationNear() throws Exception {
		repo.deleteAll();
		BasicPoi poi = DummyPoiFactory.POI_TEIDE;
		GeoLocation alaska = DummyPoiFactory.POI_ALASKA.getLocation();
		BasicPoi saved = repo.save(poi); 
		
		// Test found
		MvcResult result = this.mockMvc.perform(get("/poi/search/byLocationNear")
				.param("lat",  poi.getLocation().getLat().toString())
				.param("lng",  poi.getLocation().getLng().toString())
				.param("radius", "5"))
	    	.andExpect(status().isOk())
	    	.andExpect(content().contentType(JSON_UTF8.toString()))
	    	.andExpect(jsonPath("$content[*].name.es").value(poi.getName().getEs()))
	    	.andReturn();
		String content = result.getResponse().getContentAsString();
	    log.debug("result /poi/search/locationNear:\n" + content);
	    
	    // Test Not found
	 	this.mockMvc.perform(get("/poi/search/byLocationNear")
				.param("lat", alaska.getLat().toString())
				.param("lng", alaska.getLng().toString())
				.param("radius", "5"))
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$totalElements").value(0));
	    repo.delete(saved);
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
