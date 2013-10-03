package info.spain.opencatalog.web.controller;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.DummyPoiFactory;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.TimeTableEntry;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;
import info.spain.opencatalog.image.PoiImageUtils;
import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.web.form.PoiForm;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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
@ContextConfiguration({ "classpath:/spring/root-context.xml", "classpath:/spring/mvc-ui-config.xml"})
@ActiveProfiles("dev")
public class PoiControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private PoiRepository repo;
	
	@Autowired
	private PoiImageUtils poiImageUtils;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void testNewForm() throws Exception{
		this.mockMvc.perform( get("/admin/poi/new/BASIC"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/poi/poi"));
	}	
	
	
	@Test
	public void testPoiNotFound()  throws Exception {
		this.mockMvc.perform( get("/admin/poi/notExists"))
			.andExpect(status().isNotFound())
			.andReturn();
	}
	/**
	 * Test CRUD of a POI
	 * @throws Exception
	 * 
	 * 
	 */
	
	@Test
	public void test_CRUD_Basic() throws Exception {
		test_CRUD(PoiTypeID.BASIC);
	}

	@Test
	public void test_CRUD_Hotel() throws Exception {
		test_CRUD(PoiTypeID.HOTEL);
	}

	private void test_CRUD(PoiTypeID type) throws Exception {
		repo.deleteAll();
		BasicPoi poi = DummyPoiFactory.newPoi("poiTest");
		poi.setFlags( Flag.AIR_CONDITIONED, Flag.GUIDE_DOG_ALLOWED);
		poi.setTimetable(new TimeTableEntry("2412="));
		
		// Test POST
		MvcResult result = this.mockMvc.perform(post("/admin/poi/new/" + type)
				.param("name.es", poi.getName().getEs())
				.param("description.es", poi.getDescription().getEs())
				.param("address.route", poi.getAddress().getRoute())
				.param("address.adminArea1", poi.getAddress().getAdminArea1())
				.param("address.adminArea2", poi.getAddress().getAdminArea2())
				.param("address.zipCode", poi.getAddress().getZipCode())
				.param("location.lat", poi.getLocation().getLat().toString())
				.param("location.lng", poi.getLocation().getLng().toString())
			    .param("flags", Flag.AIR_CONDITIONED.toString())
			    .param("flags", Flag.GUIDE_DOG_ALLOWED.toString())
			    .param("timetable", "2412=")
			    )
	    	.andExpect(status().isMovedTemporarily())
	    	.andReturn();
		
		String location = result.getResponse().getHeader("Location");
		assertTrue( location.contains("message.item.created"));
		String id = location.substring("/admin/poi/".length(), location.indexOf('?'));
		
		BasicPoi repoPoi = repo.findOne(id);
		testEquals(poi, repoPoi);
		assertFalse(repoPoi.getSyncInfo().isImported());
		assertFalse(repoPoi.getSyncInfo().isSync());
		assertNull(repoPoi.getSyncInfo().getOriginalId());
		
		// Test GET
		result = this.mockMvc.perform( get("/admin/poi/{id}", id))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/poi/poi"))
			.andReturn();
		
		// Test Search all
		result = this.mockMvc.perform( get("/admin/poi"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/poi/poiList"))
			.andReturn();
				
		// Test Search by name
		result = this.mockMvc.perform( get("/admin/poi").param("q", poi.getName().getEs()))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/poi/poiList"))
			.andReturn();
				
		// Test UPDATE 
		PoiForm update = new PoiForm(PoiTypeID.BASIC);
		update.setName(new I18nText().setEs("xxx"));
		update.setDescription(new I18nText().setEs("xxx"));
		update.setAddress(new Address().setRoute("xxx").setAdminArea1("xxx").setAdminArea2("xxx").setZipCode("xxx"));
		update.setLocation(new GeoLocation().setLat(1.00).setLng(1.00));
		update.setFlags(Flag.WC, Flag.HANDICAPPED);
		update.setTimetable(new TimeTableEntry("0112="));
				
		result = this.mockMvc.perform(fileUpload("/admin/poi/" + id)
			.param("name.es", update.getName().getEs())
			.param("description.es", update.getDescription().getEs())
			.param("address.route", update.getAddress().getRoute())
			.param("address.adminArea1", update.getAddress().getAdminArea1())
			.param("address.adminArea2", update.getAddress().getAdminArea2())
			.param("address.zipCode", update.getAddress().getZipCode())
			.param("location.lat", update.getLocation().getLat().toString())
			.param("location.lng", update.getLocation().getLng().toString())
			.param("flags", Flag.WC.toString())
			.param("flags", Flag.HANDICAPPED.toString())
			.param("timetable", "0112=")
			.param("syncInfo.sync", "false")  // will be ignored
			.param("syncInfo.imported", "false")  // will be ignored
			.param("syncInfo.originalId", "Fooo")  // will be ignored
			)
		    .andExpect(status().isMovedTemporarily())
		    .andReturn();
		
		repoPoi = repo.findOne(id);
		testEquals(update, repoPoi);
		assertFalse(repoPoi.getSyncInfo().isImported());
		assertFalse(repoPoi.getSyncInfo().isSync());
		assertNull(repoPoi.getSyncInfo().getOriginalId());
		
		// TEST DELETE
		result = this.mockMvc.perform(post("/admin/poi/" + id + "/delete"))
			    .andExpect(status().isMovedTemporarily())
			    .andReturn();
		
		repoPoi = repo.findOne(id);
		assertNull(repoPoi);
    }
	
	@Test 
	public void testUpdateNonExistentPoi() throws Exception {
		this.mockMvc.perform(fileUpload("/admin/poi/-----")
			.param("name.es", "someName")
		)
	    .andExpect(status().isNotFound());
	
	}
	
	
	@Test
	public void testSyncFlags() throws Exception {
		repo.deleteAll();
		BasicPoi poi= DummyPoiFactory.monument();
		BasicPoi saved = repo.save(poi);
		
		// let's change sync flags and see how they're ignored
		this.mockMvc.perform(fileUpload("/admin/poi/" + saved.getId())
			.param("name.es", saved.getName().getEs())
			.param("location.lat", saved.getLocation().getLat().toString())
			.param("location.lng", saved.getLocation().getLng().toString())
			.param("sync", "false")  // will be ignored
			.param("imported", "true")  // will be ignored
			.param("originalId", "Fooo")  // will be ignored
			.param("syncInfo.lastUpdate", "01/01/2013 00:00:00")  // will be ignored
			)
		    .andExpect(status().isMovedTemporarily())
		    .andReturn();
		
		BasicPoi repoPoi = repo.findOne(saved.getId());
		assertFalse(repoPoi.getSyncInfo().isImported());
		assertFalse(repoPoi.getSyncInfo().isSync());
		assertNull(repoPoi.getSyncInfo().getOriginalId());
		assertNull(repoPoi.getSyncInfo().getLastUpdate());
		
		
		// change the poi in db
		DateTime lastUpdate = new DateTime();
		repoPoi.getSyncInfo()
			.setImported(true)
			.setOriginalId("original")
			.setSync(false)
			.setLastUpdate(lastUpdate);
		repo.save(repoPoi);
		
		// let's change sync flags again and see 
		this.mockMvc.perform(fileUpload("/admin/poi/" + saved.getId())
			.param("name.es", saved.getName().getEs())
			.param("location.lat", saved.getLocation().getLat().toString())
			.param("location.lng", saved.getLocation().getLng().toString())
			.param("syncInfo.sync", "true")  // will not be ignored
			.param("syncInfo.imported", "false")  // will be ignored
			.param("syncInfo.originalId", "Fooo")  // will be ignored
			.param("syncInfo.lastUpdate", "01/01/2013 00:00:00")  // will be ignored
			)
		    .andExpect(status().isMovedTemporarily())
		    .andReturn();
			
		repoPoi = repo.findOne(saved.getId());
		assertTrue(repoPoi.getSyncInfo().isImported());
		assertTrue(repoPoi.getSyncInfo().isSync());
		assertEquals("original", repoPoi.getSyncInfo().getOriginalId());
		assertEquals( lastUpdate, repoPoi.getSyncInfo().getLastUpdate());
		
			
	}
	
	
	@Test
	public void testUpdatePoiWithImages() throws Exception {
		repo.deleteAll();
		BasicPoi saved = repo.save(DummyPoiFactory.BEACH);
		MockMultipartFile image1 = new MockMultipartFile("files", "0.jpg", MediaType.IMAGE_JPEG_VALUE, new ClassPathResource("img/0.jpg").getInputStream());
		MockMultipartFile image2 = new MockMultipartFile("files", "1.jpg", MediaType.IMAGE_JPEG_VALUE, new ClassPathResource("img/1.jpg").getInputStream());
		this.mockMvc.perform(fileUpload("/admin/poi/{poiId}", saved.getId())
			.file(image1)
			.file(image2)
			.param("name.es", saved.getName().getEs()))
			.andExpect(status().isMovedTemporarily());
		
		List<String> images = poiImageUtils.getPoiImageFilenames(saved.getId());
		assertEquals(2, images.size());
		
		// Get the image
		MvcResult result = this.mockMvc.perform( get("/admin/poi/{id}/image/{idImage}", saved.getId(), images.get(0)))
			.andExpect(status().isOk())
			.andReturn();
		assertEquals( MediaType.IMAGE_JPEG_VALUE, result.getResponse().getContentType());
			
		// Get the default image
		result = this.mockMvc.perform( get("/admin/poi/{id}/image/default", saved.getId()))
			.andExpect(status().isOk())
			.andReturn();
		assertEquals( MediaType.IMAGE_JPEG_VALUE, result.getResponse().getContentType());
		
		//set Default Image
		String defaultImage = images.get(1);
		this.mockMvc.perform(fileUpload("/admin/poi/{poiId}", saved.getId())
			.param("defaultImageFilename", defaultImage)
			.param("name.es", saved.getName().getEs()))
			.andExpect(status().isMovedTemporarily());
				
		// Get the default image
		result = this.mockMvc.perform( get("/admin/poi/{id}/image/default", saved.getId()))
			.andExpect(status().isOk())
			.andReturn();
		assertEquals( MediaType.IMAGE_JPEG_VALUE, result.getResponse().getContentType());
		assertTrue(defaultImage, result.getResponse().getHeader("Content-Disposition").contains("filename=\"" + defaultImage + "\""));
		
		//Delete Default Image
		this.mockMvc.perform(fileUpload("/admin/poi/{poiId}", saved.getId())
			.param("name.es", saved.getName().getEs())
			.param("deleteFile", defaultImage))
			.andExpect(status().isMovedTemporarily());
		
		images = poiImageUtils.getPoiImageFilenames(saved.getId());
		assertEquals(1, images.size());
		
		// Get the default image
		result = this.mockMvc.perform( get("/admin/poi/{id}/image/default", saved.getId()))
			.andExpect(status().isOk())
			.andReturn();
		assertEquals( MediaType.IMAGE_JPEG_VALUE, result.getResponse().getContentType());
		assertTrue(defaultImage, result.getResponse().getHeader("Content-Disposition").contains("filename=\"" + images.get(0) + "\""));
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
		if (expected.getTimetable()!= null ) {
			assertEquals(expected.getTimetable().size(), actual.getTimetable().size());
		}
		
	}

	

}
