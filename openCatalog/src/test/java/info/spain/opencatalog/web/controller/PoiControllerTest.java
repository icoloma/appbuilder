package info.spain.opencatalog.web.controller;

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
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.types.BasicPoi;
import info.spain.opencatalog.repository.PoiRepository;

import static junit.framework.Assert.*;

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
		BasicPoi poi = PoiFactory.newPoi("poiTest");
		poi.setFlags( Flag.SHOP, Flag.GUIDED_TOUR);
		
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
			    .param("flag[" + Flag.GUIDED_TOUR + "-a]", "whatever")
			    .param("flag[" + Flag.SHOP + "-a]", "whatever")
			    )
	    	.andExpect(status().isMovedTemporarily())
	    	.andReturn();
		
		String location = result.getResponse().getHeader("Location");
		assertTrue( location.contains("message.item.created"));
		String id = location.substring("/admin/poi/".length(), location.indexOf('?'));
		
		BasicPoi repoPoi = repo.findOne(id);
		testEquals(poi, repoPoi);
		
		// Test GET
		result = this.mockMvc.perform( get("/admin/poi/{id}", id))
				.andExpect(status().isOk())
				.andExpect(view().name("admin/poi/poi"))
				.andReturn();
		
		// Test UPDATE 
		BasicPoi update = new BasicPoi();
		update.setName(new I18nText().setEs("xxx"));
		update.setDescription(new I18nText().setEs("xxx"));
		update.setAddress(new Address().setRoute("xxx").setAdminArea1("xxx").setAdminArea2("xxx").setZipCode("xxx"));
		update.setLocation(new GeoLocation().setLat(1.00).setLng(1.00));
		update.setFlags(Flag.WC, Flag.DISABLED);
				

		result = this.mockMvc.perform(post("/admin/poi/" + id)
				.param("name.es", update.getName().getEs())
				.param("description.es", update.getDescription().getEs())
				.param("address.route", update.getAddress().getRoute())
				.param("address.adminArea1", update.getAddress().getAdminArea1())
				.param("address.adminArea2", update.getAddress().getAdminArea2())
				.param("address.zipCode", update.getAddress().getZipCode())
				.param("location.lat", update.getLocation().getLat().toString())
				.param("location.lng", update.getLocation().getLng().toString())
			    .param("flag[" + Flag.WC + "-a]", "whatever")
			    .param("flag[" + Flag.DISABLED + "-a]", "whatever")
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
	
	private void testEquals(BasicPoi expected, BasicPoi actual){
		assertEquals(expected.getName().getEs(), actual.getName().getEs());
		assertEquals(expected.getDescription().getEs(), actual.getDescription().getEs());
		assertEquals(expected.getAddress().getRoute(), actual.getAddress().getRoute());
		assertEquals(expected.getAddress().getAdminArea1(), actual.getAddress().getAdminArea1());
		assertEquals(expected.getAddress().getAdminArea2(), actual.getAddress().getAdminArea2());
		assertEquals(expected.getAddress().getZipCode(), actual.getAddress().getZipCode());
		assertEquals(expected.getLocation().getLat(), actual.getLocation().getLat());
		assertEquals(expected.getLocation().getLng(), actual.getLocation().getLng());
		if (expected.getFlags() != null ) {
			assertEquals(expected.getFlags().size(), actual.getFlags().size());
			for (Flag flag : expected.getFlags()) {
				assertTrue( actual.getFlags().contains(flag));
			}
		}
		if (expected.getAccessibilityFlags()!= null ) {
			assertEquals(expected.getAccessibilityFlags().size(), actual.getAccessibilityFlags().size());
		}
		if (expected.getQualityCertificateFlags() != null ) {
			assertEquals(expected.getQualityCertificateFlags().size(), actual.getQualityCertificateFlags().size());
		}
		if (expected.getTimetable()!= null ) {
			assertEquals(expected.getTimetable().size(), actual.getTimetable().size());
		}
		
	}

	


}
