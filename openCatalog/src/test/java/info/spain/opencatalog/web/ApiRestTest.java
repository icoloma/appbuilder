package info.spain.opencatalog.web;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.domain.PoiFactory;
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
@ContextConfiguration({ "ApiRestTest-config.xml"})
@ActiveProfiles("dev")
public class ApiRestTest {
	
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
    public void getPOI() throws Exception {
		repo.deleteAll();
		Poi poi= PoiFactory.getNewPoi("getPoi");
		Poi saved = repo.save(poi);
		
		// test poi
	    MvcResult result = this.mockMvc.perform(get("/poi/" + saved.getId())
	      .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
          .andExpect(status().isOk())
          .andExpect(content().contentType("application/json;charset=UTF-8"))
          .andExpect(jsonPath("$.name.ES").value(poi.getName().get("ES"))).andReturn();
	    
	    String content = result.getResponse().getContentAsString();
	    log.debug( "result /poi/" + saved.getId() + ":\n" + content);
	    
	    
	    // test discover
	    result = this.mockMvc.perform(get("/poi/" )
  	      .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
          .andExpect(status().isOk())
          .andExpect(content().contentType("application/json;charset=UTF-8"))
          .andExpect(jsonPath("$.content[0].name.ES").value(poi.getName().get("ES"))).andReturn();
	    
	    content = result.getResponse().getContentAsString();
	    log.debug("result /poi/:\n" + content);
	    
    }

	/**
	 * Test POST of POI
	 * @throws Exception
	 */
	@Test
    public void postPoi() throws Exception {
		
		repo.deleteAll();
		
		String jsonPoi = "{ 'name': {" +
						 "     'ES': 'name-español'," +
						 "     'EN': 'name-english' " +
						 "  }," +
						 "  'description': {" +
						 "     'ES': 'desc-español'," +
						 "     'EN': 'desc-english' " +
						 "  }," +
						 "  'address' : {" +
						 "     'address' : 'theaddress'," +
						 "	   'city' : 'thecity', " +
						 "	   'zipCode' : 'theZipCode' " +
						 "	  }," +
						 "  'location' : {" +
						 "     'lng' : -3.6110357166313953," +
						 "     'lat' : 40.45073418848147" +
						 "  }" +
						 "}";
		jsonPoi = jsonPoi.replaceAll("\'", "\"");
		
		System.out.println( "POI:" + jsonPoi);
	    this.mockMvc.perform(post("/poi")
	    	.contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
			.content(jsonPoi))
			.andExpect(status().isOk());
         
    }

}
