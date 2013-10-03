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
	

}
