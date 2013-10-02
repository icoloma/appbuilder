package info.spain.opencatalog.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import info.spain.opencatalog.domain.DummyZoneFactory;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.repository.ZoneRepository;
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
public class ZoneAPIControllerTest {
	
	private static final MediaType JSON_UTF8 = MediaType.parseMediaType("application/json;charset=UTF-8");
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private ZoneRepository repo;
	
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
		Zone zone= DummyZoneFactory.newZone("getZone");
		Zone saved = repo.save(zone);
		
		// test zone
	    MvcResult result = this.mockMvc.perform(get("/zone/{0}",  saved.getId())
			.accept(JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(content().contentType(JSON_UTF8.toString()))
			.andExpect(jsonPath("$name").value(zone.getName()))
			.andReturn();     
	    
	    String content = result.getResponse().getContentAsString();
	    log.debug( "result /zone/" + saved.getId() + ":\n" + content);
	    
	    
	    JSONArray links = new JSONArray();
	    links.add("byName");
	    
	    // test discover
	    this.mockMvc.perform(get("/zone/" )
			.accept(JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(content().contentType(JSON_UTF8.toString()))
			.andExpect(jsonPath("$links[*].rel").value( links))
			; 
    }
	
	@Test
	public void testUpdateNonExistingZone() throws Exception {
		String json = "{" +
				"'name': 'some other value'," +
				"'description': 'some other description'" +
				"}";
		json = json.replaceAll("'", "\"");
		
		this.mockMvc.perform(put("/zone/NON_EXISTING")
			.contentType(JSON_UTF8)
			.content(json))
			.andExpect(status().isNotFound())
		    .andReturn();
	}
	
	@Test
	public void testDeleteNonExistingZone() throws Exception {
		this.mockMvc.perform(delete("/zone/NON_EXISTING"))
			.andExpect(status().isNoContent());
		  
	}
	
	
//	/**
//	 * Test POST of POI
//	 * @throws Exception
//	 */
//	@Test
//	public void testPOST() throws Exception {
//		String type = ZoneTypeID.HOTEL.toString();
//		repo.deleteAll();
//		String json = "{" +
//				"'type': '" + type + "'," +
//				"'name':{" +
//					"'es':'es-name'," +
//					"'en':'en-name'" +
//				"}," +
//				"'description':{" +
//					"'es':'es-description'," +
//					"'en':'en-description'" +
//				"}," +
//				"'address':{" +
//					"'route':'route'," +
//					"'adminArea1':'area1'," +
//					"'adminArea2':'area2'," +
//					"'zipCode':'zipCode'" +
//				"}," +
//				"'location':{" +
//					"'lat':40.45259106740161," +
//					"'lng':-3.7391396261243433" +
//				"}," +
//				"'flags':['" + Flag.GUIDED_TOUR+ "']" +
//				"}";
//		json = json.replaceAll("'", "\"");
//		
//		System.out.println( "POI:" + json);
//		 MvcResult result = this.mockMvc.perform(post("/zone")
//	    	.param("type", type)
//	    	.contentType(JSON_UTF8)
//			.content(json))
//			.andExpect(status().isCreated())
//			.andReturn();
//		 
//		assertNotNull(getIdFromLocation(result));
//	}
//	
//	@Test
//	public void testSyncFlags() throws Exception{
//		repo.deleteAll();
//		Zone zone= DummyZoneFactory.monument();
//		Zone saved = repo.save(zone);
//		
//		// let's change 
//		//    sync = true        ---> will be ignored
//		//    imported = true    ---> will be ignored
//		//    originalId = "aaa" ---> will be ignored
//		String json = "{" +
//				"'type': '" + zone.getType() + "'," +
//				"'name':{" +
//					"'es':'some other value'" +
//				"}," +
//				"'location':{" +
//					"'lat':40.45259106740161," +
//					"'lng':-3.7391396261243433" +
//				"}," +
//				"'syncInfo' : {" +
//					"'sync' : 'true'," +
//					"'imported' : 'true'," +
//					"'originalId' : 'aaa'," +
//					"'lastUpdate' : '20130101'" +
//				"}," +
//				"'flags':['" + Flag.GUIDED_TOUR+ "']" +
//				"}";
//		
//		this.mockMvc.perform(put("/zone/" + saved.getId())
//			.contentType(JSON_UTF8)
//			.content(json))
//			.andExpect(status().isNoContent())
//		    .andReturn();
//		
//		Zone repoZone = repo.findOne(saved.getId());
//		
//		// No se puede cambiar si no es un zone importado
//		assertFalse(repoZone.getSyncInfo().isSync());
//		assertFalse(repoZone.getSyncInfo().isImported());
//		assertNull(repoZone.getSyncInfo().getOriginalId());
//
//		// Modificamos directamente la base de datos
//		repoZone.getSyncInfo()
//			.setImported(true)
//			.setSync(true)
//			.setOriginalId("original");
//		
//		saved = repo.save(repoZone);
//		
//		// let's change 
//		//    sync = false
//		//    imported = false     ----> will be ignored
//		//    originalId = "changed" ---> will be ignored
//		json = "{" +
//				"'type': '" + zone.getType() + "'," +
//				"'name':{" +
//					"'es':'changing the name again'" +
//				"}," +
//				"'location':{" +
//					"'lat':40.45259106740161," +
//					"'lng':-3.7391396261243433" +
//				"}," +
//				"'syncInfo' : {" +	
//					"'sync' : 'false'," +
//					"'imported' : 'false'," + // será ignorado
//					"'originalId' : 'changed'," + // será ignorado
//					"'lastUpdate' : '20130101'" + // será ignorado
//				"}," + 
//				"'flags':['" + Flag.GUIDED_TOUR+ "']" +
//				"}";
//		
//		this.mockMvc.perform(put("/zone/" + saved.getId())
//			.contentType(JSON_UTF8)
//			.content(json))
//			.andExpect(status().isNoContent())
//		    .andReturn();
//
//		
//		// Comprobamos los casos en que se puede modificar
//		repoZone = repo.findOne(saved.getId());
//		assertTrue(repoZone.getSyncInfo().isImported());
//		assertEquals("original", repoZone.getSyncInfo().getOriginalId());
//		assertFalse(repoZone.getSyncInfo().isSync());  
//	}
//	
//	
//
//	@Test
//	public void testFindByName() throws Exception {
//		repo.deleteAll();
//		Zone zone = DummyZoneFactory.newZone("tesFindByName");
//		Zone saved = repo.save(zone);
//		MvcResult result = this.mockMvc.perform(get("/zone/search/byName").param("name", zone.getName().getEs()))
//			.andExpect(status().isOk())
//			.andExpect(content().contentType(JSON_UTF8.toString()))
//			.andExpect(jsonPath("$content[*].name.es").value(zone.getName().getEs()))
//			.andReturn();
//		String content = result.getResponse().getContentAsString();
//	    log.debug("result /zone/search/byName_es:\n" + content);
//	    repo.delete(saved);
//	}
//	
//	@Test
//	public void testFindByLocationNear() throws Exception {
//		repo.deleteAll();
//		Zone zone = DummyZoneFactory.POI_TEIDE;
//		GeoLocation alaska = DummyZoneFactory.POI_ALASKA.getLocation();
//		Zone saved = repo.save(zone); 
//		
//		// Test found
//		MvcResult result = this.mockMvc.perform(get("/zone/search/byLocationNear")
//				.param("lat",  zone.getLocation().getLat().toString())
//				.param("lng",  zone.getLocation().getLng().toString())
//				.param("radius", "5"))
//	    	.andExpect(status().isOk())
//	    	.andExpect(content().contentType(JSON_UTF8.toString()))
//	    	.andExpect(jsonPath("$content[*].name.es").value(zone.getName().getEs()))
//	    	.andReturn();
//		String content = result.getResponse().getContentAsString();
//	    log.debug("result /zone/search/locationNear:\n" + content);
//	    
//	    // Test Not found
//	 	result = this.mockMvc.perform(get("/zone/search/byLocationNear")
//				.param("lat", alaska.getLat().toString())
//				.param("lng", alaska.getLng().toString())
//				.param("radius", "5"))
//	    	.andExpect(status().isOk())
//	    	.andExpect(jsonPath("$totalElements").value(0))
//	    	.andReturn();
//	 	content = result.getResponse().getContentAsString();	 	 
//	 
//	    repo.delete(saved);
//	}
//	
//	private String getIdFromLocation(MvcResult result){
//		String location = result.getResponse().getHeader("Location");
//		assertNotNull(location);
//		String id = location.substring( location.lastIndexOf('/') + 1);
//		assertNotNull(id);
//		log.trace("id from Location:" + id);
//		return id;
//	}
//	

}
