package info.spain.opencatalog.api.controller;

import static junit.framework.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import info.spain.opencatalog.domain.DummyPoiFactory;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.ZoneFactory;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.repository.ZoneRepository;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.jayway.jsonpath.JsonPath;

       

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextConfiguration({ "classpath:APITest-config.xml"})
@ActiveProfiles("dev")
public class PoiAPICustomSearchTest {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private PoiRepository poiRepo;
	
	@Autowired
	private ZoneRepository zoneRepo;
	
	
	private MockMvc mockMvc;
	
	// used as saved POI sample
	private BasicPoi playaTeresitas;
	private BasicPoi playaLaConcha;
	private Zone zonaNorte;
	private Zone zonaTenerife;
	private Zone zonaVacia;
	
	
	private static final String DATE_FUTURE= new DateTime().plusYears(1).toString();   //  after DB Populator 
	private static final String DATE_PAST  = new DateTime().minusYears(1).toString();  //  before DB Populator 
	
	@Autowired
	private ObjectMapper objectMapper;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		poiRepo.deleteAll();
		
		playaTeresitas = poiRepo.save(DummyPoiFactory.PLAYA_TERESITAS);
		playaLaConcha = poiRepo.save(DummyPoiFactory.PLAYA_LA_CONCHA);
		
		zonaNorte = zoneRepo.save(ZoneFactory.ZONE_NORTE);
		zonaTenerife = zoneRepo.save(ZoneFactory.ZONE_PROVINCIA_STA_CRUZ);
		zonaVacia = zoneRepo.save(ZoneFactory.ZONE_EMPTY);
	}
	
	

	
	@Test
	public void test_match_AND_criterias_OK_with_LasTeresitas() throws Exception {
		SearchQuery query = new SearchQuery()
			//.setUpdatedAfter(DATE_PAST)
			.setFlagList(Lists.newArrayList(playaTeresitas.getFlags().iterator().next().toString()))
			.setPoiTypeIdList(Lists.newArrayList(playaTeresitas.getType().toString()))
			.setIdZone(zonaTenerife.getId())  
			;
	
		String jsonQuery = objectMapper.writeValueAsString(query);
		log.trace("jsonQuery:" + jsonQuery);
		MvcResult mvcResult = sendRequest(jsonQuery);
		
		String response = mvcResult.getResponse().getContentAsString();
		log.trace("Response:" + response);

		JSONObject json = new JSONObject(response);
		JSONArray result = (JSONArray) json.get("content");
		
		assertEquals(1, result.length());
		assertEquals(playaTeresitas.getName().getEs(), JsonPath.read(response, "$.content[0].name.es"));
		
	}
	

	@Test
	public void test_match_AND_criterias_FAIL_with_LasTeresitas() throws Exception {
		SearchQuery query = new SearchQuery()
			.setUpdatedAfter(DATE_FUTURE)
			.setFlagList(Lists.newArrayList(playaTeresitas.getFlags().iterator().next().toString()))
			.setPoiTypeIdList(Lists.newArrayList(playaTeresitas.getType().toString()))
			.setIdZone(zonaTenerife.getId())  
			;
	
		String jsonQuery = objectMapper.writeValueAsString(query);
		log.trace("jsonQuery:" + jsonQuery);
		
		MvcResult mvcResult = sendRequest(jsonQuery);
		String response = mvcResult.getResponse().getContentAsString();
		log.trace("Response:" + response);

		JSONObject json = new JSONObject(response);
		JSONArray result = (JSONArray) json.get("content");
		
		assertEquals(0, result.length());
		
	}
	
	@Test
	public void test_invalid_LastUpdate() throws Exception {
		doTestLastUpdate("");
		doTestLastUpdate("null");
		doTestLastUpdate(null);
		doTestLastUpdate("---");
	}
	
	private void doTestLastUpdate(String date) throws Exception{
		SearchQuery query = new SearchQuery().setUpdatedAfter(date);
		String jsonQuery = objectMapper.writeValueAsString(query);
		log.trace("query:" + query);
		
		MvcResult mvcResult = sendRequest(jsonQuery);
		String response = mvcResult.getResponse().getContentAsString();
		log.trace("Response:" + response);

		JSONObject json = new JSONObject(response);
		JSONArray result = (JSONArray) json.get("content");
		assertEquals(0, result.length());   // No criteria (invalid date), no results
	}

	
	@Test
	public void test_match_by_LastUpdate_All() throws Exception {
		SearchQuery query = new SearchQuery()
			.setUpdatedAfter( DATE_PAST);

		String jsonQuery = objectMapper.writeValueAsString(query);
		log.trace("jsonQuery:" + jsonQuery);
		
		MvcResult mvcResult = sendRequest(jsonQuery);
		String response = mvcResult.getResponse().getContentAsString();
		log.trace("Response:" + response);

		JSONObject json = new JSONObject(response);
		JSONArray result = (JSONArray) json.get("content");
		assertEquals(2, result.length());
	}
	
	@Test
	public void test_match_by_LastUpdate_None() throws Exception {
		SearchQuery query = new SearchQuery()
			.setUpdatedAfter( DATE_FUTURE);  
		
		String jsonQuery = objectMapper.writeValueAsString(query);
		log.trace("jsonQuery:" + jsonQuery);
		
		MvcResult mvcResult = sendRequest(jsonQuery);
		String response = mvcResult.getResponse().getContentAsString();
		log.trace("Response:" + response);

		JSONObject json = new JSONObject(response);
		JSONArray result = (JSONArray) json.get("content");
		assertEquals(0, result.length());
		
	}
	
	@Test
	public void test_match_by_zone_Ok() throws Exception {
		SearchQuery query = new SearchQuery().setIdZone(zonaNorte.getId());

		String jsonQuery = objectMapper.writeValueAsString(query);
		log.trace("jsonQuery:" + jsonQuery);
		
		MvcResult mvcResult = sendRequest(jsonQuery);
		String response = mvcResult.getResponse().getContentAsString();
		log.trace("Response:" + response);

		JSONObject json = new JSONObject(response);
		JSONArray result = (JSONArray) json.get("content");
		assertEquals(1, result.length());
		assertEquals(playaLaConcha.getName().getEs(), JsonPath.read(response, "$.content[0].name.es"));
	}

	
	@Test
	public void test_match_by_zone_nonExistingZone() throws Exception {
		SearchQuery query = new SearchQuery().setIdZone(zonaVacia.getId());

		String jsonQuery = objectMapper.writeValueAsString(query);
		log.trace("jsonQuery:" + jsonQuery);
		
		MvcResult mvcResult = sendRequest(jsonQuery);
		String response = mvcResult.getResponse().getContentAsString();
		log.trace("Response:" + response);

		JSONObject json = new JSONObject(response);
		JSONArray result = (JSONArray) json.get("content");
		assertEquals(0, result.length());
	}

	@Test
	public void test_match_by_zone_withoutPOis() throws Exception {
		SearchQuery query = new SearchQuery().setIdZone("unknowed");

		String jsonQuery = objectMapper.writeValueAsString(query);
		log.trace("jsonQuery:" + jsonQuery);
		
		MvcResult mvcResult = sendRequest(jsonQuery);
		String response = mvcResult.getResponse().getContentAsString();
		log.trace("Response:" + response);

		JSONObject json = new JSONObject(response);
		JSONArray result = (JSONArray) json.get("content");
		assertEquals(0, result.length());
	}

	
	/**
	 * Sends Mock Request
	 * @param jsonQuery
	 * @return
	 */
	private MvcResult sendRequest(String jsonQuery) throws Exception {
		log.trace("Query: " + jsonQuery);
		MvcResult mvcResult = this.mockMvc.perform(
			get("/poi/search/custom")
				.accept(MediaType.valueOf("application/json;charset=UTF-8"))
				.param("q", jsonQuery)
				.param("limit", "10")
			)
			.andExpect(status().isOk())
			.andReturn();
		return mvcResult;
	}
	

}
