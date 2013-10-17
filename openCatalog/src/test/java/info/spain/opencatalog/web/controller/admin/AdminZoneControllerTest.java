package info.spain.opencatalog.web.controller.admin;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.DummyZoneFactory;
import info.spain.opencatalog.repository.ZoneRepository;

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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextConfiguration({ "classpath:/spring/root-context.xml", "classpath:/spring/mvc-ui-config.xml"})
@ActiveProfiles("dev")
public class AdminZoneControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private ZoneRepository repo;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	

	@Test
	public void testNewForm() throws Exception{
		this.mockMvc.perform( get("/admin/zone/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/zone/zone"));
	}	
	
	@Test
	public void testSearchWithQuery() throws Exception {
		// With query
		this.mockMvc.perform( get("/admin/zone").param("q", "some criteria"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/zone/zoneList"));
	}
	
	@Test
	public void testSearchWithoutQuery() throws Exception {
		// without query
		this.mockMvc.perform( get("/admin/zone"))
		.andExpect(status().isOk())
		.andExpect(view().name("admin/zone/zoneList"));
	}
	

	@Test
	public void notFound() throws Exception {
		this.mockMvc.perform( get("/admin/zone/notFound"))
		.andExpect(status().isNotFound());
	}
	

		
	/**
	 * Test CRUD of a Zone
	 * @throws Exception
	 */
	@Test
	public void zoneCRUD() throws Exception {
		repo.deleteAll();
		Zone zone = DummyZoneFactory.newZone("zoneTest");
		zone.setPath(DummyZoneFactory.ZONE_MADRID_CENTRO.getPath());
		
		// Test POST
		MockHttpServletRequestBuilder thePost = post("/admin/zone")
			.param("name", zone.getName())
			.param("description", zone.getDescription())
			.param("address.adminArea1", zone.getAddress().getAdminArea1())
			.param("address.adminArea2", zone.getAddress().getAdminArea2())
			;
		
		setPathParams(zone, thePost);	
		
		MvcResult result = this.mockMvc.perform( thePost ).andExpect(status().isMovedTemporarily()).andReturn();
		
		String location = result.getResponse().getHeader("Location");
		assertTrue( location.contains("message.item.created"));
		String id = location.substring("/admin/zone/".length(), location.indexOf('?'));
		
		Zone repoZone = repo.findOne(id);
		testEquals(zone, repoZone);
		
		// Test GET
		result = this.mockMvc.perform( get("/admin/zone/{id}", id))
				.andExpect(status().isOk())
				.andExpect(view().name("admin/zone/zone"))
				.andReturn();
		
		
		// Show Pois 
		result = this.mockMvc.perform( get("/admin/zone/{id}/poi", id))
				.andExpect(status().isOk())
				.andExpect(view().name("admin/zone/zonePoi"))
				.andReturn();
		
		// Test UPDATE 
		Zone update = new Zone()
			.setName("xxx")
			.setDescription("xxx")
			.setAddress(new Address().setAdminArea1("xxx").setAdminArea2("xxx"))
			.setPath(DummyZoneFactory.ZONE_ALCALA_HENARES.getPath());

		
		MockHttpServletRequestBuilder thePut = put("/admin/zone/" + id)
				.param("name", update.getName())
				.param("description", update.getDescription())
				.param("address.adminArea1", update.getAddress().getAdminArea1())
				.param("address.adminArea2", update.getAddress().getAdminArea2())
				;
		setPathParams(update, thePut);		

		result = this.mockMvc.perform(thePut)
			    .andExpect(status().isMovedTemporarily())
			    .andReturn();
		
		repoZone = repo.findOne(id);
		testEquals(update, repoZone);
		
		
		// TEST DELETE
		result = this.mockMvc.perform(delete("/admin/zone/" + id))
			    .andExpect(status().isMovedTemporarily())
			    .andReturn();
		
		repoZone = repo.findOne(id);
		assertNull(repoZone);
		
		
    }
	
	
	@Test
	public void testErrors(){
		AdminZoneController controller = new AdminZoneController();
		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(new Zone(), "zone");
		errors.reject("sampleError");
		
		assertEquals("admin/zone/zone", controller.create(null , errors,  null));
		assertEquals("admin/zone/zone", controller.update(null, errors, null, null));
		
		
	}
	
	private void testEquals(Zone expected, Zone actual){
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getAddress().getAdminArea1(), actual.getAddress().getAdminArea1());
		assertEquals(expected.getAddress().getAdminArea2(), actual.getAddress().getAdminArea2());
		assertEquals(expected.getPath().size(), actual.getPath().size());
		for (GeoLocation geo: expected.getPath()) {
			assertTrue( actual.getPath().contains(geo));
		}
		
	}
	
	private void setPathParams(Zone zone, MockHttpServletRequestBuilder builder){
		for( int i=0; i< zone.getPath().size(); i ++){
			GeoLocation point = zone.getPath().get(i);
			builder.param("path[" + i +"].lat", point.getLat().toString());
			builder.param("path[" + i +"].lng", point.getLng().toString());
		}		
	}

	


}
