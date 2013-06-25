package info.spain.opencatalog.web.controller;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.PoiFactory;
import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.domain.poi.Poi;
import info.spain.opencatalog.repository.PoiRepository;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration({ "classpath:/spring/root-context.xml", "classpath:/spring/mvc-config.xml"})
@ActiveProfiles("dev")
public class PoiControllerTest {
	
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
	 * Test CRUD of a POI
	 * @throws Exception
	 */
	@Test
	public void test_POST_GET_UPDATE_DELETE() throws Exception {
		repo.deleteAll();
		Poi poi = PoiFactory.newPoi("poiTest").build();
		poi.setTags( new ArrayList<Tag>());
		poi.getTags().add(Tag.EATING_RESTAURANT);
		poi.getTags().add(Tag.LODGING_APARTAMENT);
		
		// Test POST
		MvcResult result = this.mockMvc.perform(post("/admin/poi")
				.param("name.es", poi.getName().getEs())
				.param("description.es", poi.getDescription().getEs())
				.param("address.route", poi.getAddress().getRoute())
				.param("address.adminArea1", poi.getAddress().getAdminArea1())
				.param("address.adminArea2", poi.getAddress().getAdminArea2())
				.param("address.zipCode", poi.getAddress().getZipCode())
				.param("location.lat", poi.getLocation().getLat().toString())
				.param("location.lng", poi.getLocation().getLng().toString())
			    .param("tag[" + Tag.EATING_RESTAURANT.getId() + "-a]", "whatever")
			    .param("tag[" + Tag.LODGING_APARTAMENT.getId() + "-a]", "whatever")
			    )
	    	.andExpect(status().isMovedTemporarily())
	    	.andReturn();
		
		String location = result.getResponse().getHeader("Location");
		assertTrue( location.contains("message.item.created"));
		String id = location.substring("/admin/poi/".length(), location.indexOf('?'));
		
		Poi repoPoi = repo.findOne(id);
		testEquals(poi, repoPoi);
		
		// Test GET
		result = this.mockMvc.perform( get("/admin/poi/{id}", id))
				.andExpect(status().isOk())
				.andExpect(view().name("admin/poi/poi"))
				.andReturn();
		
		// Test UPDATE 
		Poi update = new Poi();
		update.setName(new I18nText().setEs("xxx"));
		update.setDescription(new I18nText().setEs("xxx"));
		update.setAddress(new Address().setRoute("xxx").setAdminArea1("xxx").setAdminArea2("xxx").setZipCode("xxx"));
		update.setLocation(new GeoLocation().setLat(1.00).setLng(1.00));
		update.getTags().add(Tag.EATING_CAFE);
		update.getTags().add(Tag.LEISURE_BEACH);
				

		result = this.mockMvc.perform(post("/admin/poi/" + id)
				.param("name.es", update.getName().getEs())
				.param("description.es", update.getDescription().getEs())
				.param("address.route", update.getAddress().getRoute())
				.param("address.adminArea1", update.getAddress().getAdminArea1())
				.param("address.adminArea2", update.getAddress().getAdminArea2())
				.param("address.zipCode", update.getAddress().getZipCode())
				.param("location.lat", update.getLocation().getLat().toString())
				.param("location.lng", update.getLocation().getLng().toString())
			    .param("tag[" + Tag.EATING_CAFE.getId() + "-a]", "whatever")
			    .param("tag[" + Tag.LEISURE_BEACH.getId() + "-a]", "whatever")
			    )
			    .andExpect(status().isMovedTemporarily())
			    .andReturn();
		
		repoPoi = repo.findOne(id);
		testEquals(update, repoPoi);
		
		
		// TEST DELETE
		result = this.mockMvc.perform(delete("/admin/poi/" + id))
			    .andExpect(status().isMovedTemporarily())
			    .andReturn();
		
		repoPoi = repo.findOne(id);
		assertNull(repoPoi);
		
		
    }
	
	private void testEquals(Poi expected, Poi actual){
		assertEquals(expected.getName().getEs(), actual.getName().getEs());
		assertEquals(expected.getDescription().getEs(), actual.getDescription().getEs());
		assertEquals(expected.getAddress().getRoute(), actual.getAddress().getRoute());
		assertEquals(expected.getAddress().getAdminArea1(), actual.getAddress().getAdminArea1());
		assertEquals(expected.getAddress().getAdminArea2(), actual.getAddress().getAdminArea2());
		assertEquals(expected.getAddress().getZipCode(), actual.getAddress().getZipCode());
		assertEquals(expected.getLocation().getLat(), actual.getLocation().getLat());
		assertEquals(expected.getLocation().getLng(), actual.getLocation().getLng());
		assertEquals(expected.getTags().size(), actual.getTags().size());
		for (Tag tag : expected.getTags()) {
			assertTrue( actual.getTags().contains(tag));
		}
		
	}

	


}
