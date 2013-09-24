package info.spain.opencatalog.web.api;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import info.spain.opencatalog.domain.DummyPoiFactory;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.repository.PoiRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@ContextConfiguration({ "classpath:APITest-config.xml"})
@ActiveProfiles("dev")
public class PoiImageAPIControllerTest {
	
	public static final String NO_RESULTS = "{\n  \"links\" : [ ],\n  \"content\" : [ ]\n}";
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private PoiRepository repo;
	
	private MockMvc mockMvc;
	
	private MockMultipartFile sampleImage;
	
	
	
	
	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		sampleImage = new MockMultipartFile("file", "0.jpg", MediaType.IMAGE_JPEG_VALUE, new ClassPathResource("img/0.jpg").getInputStream());
	}

	
	@Test
    public void testGetImagesIfPoiDoesNotExists() throws Exception {
		repo.deleteAll();
		this.mockMvc.perform(get("/poi/xxxxxx/image")
			.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
			.andExpect(status().isNotFound());
    }
	
	@Test
    public void testPostImageIfPoiDoesNotExists() throws Exception {
		repo.deleteAll();
		this.mockMvc.perform(fileUpload("/poi/xxxxxxx/image").file(sampleImage))
			.andExpect(status().isNotFound())
			.andReturn();
    }
	
	/**
	 * Create image 
	 * @throws Exception
	 */
	@Test
	public void testCRDImageIfPoiExists() throws Exception {
		BasicPoi saved = repo.save(DummyPoiFactory.POI_TEIDE);
		// Add Image
	    MvcResult result = this.mockMvc.perform(fileUpload("/poi/{id}/image", saved.getId()).file(sampleImage))
			.andExpect(status().isCreated())
			.andReturn();
	    log.trace("image created");   
	    
	   // Retrieve image
	   String filename = getIdFromLocation(result);
	   result = this.mockMvc.perform(get("/poi/{idPoi}/image/{idImage}", saved.getId(), filename))
			.andExpect(status().isOk())
			.andReturn();
	   assertEquals( MediaType.IMAGE_JPEG_VALUE, result.getResponse().getContentType());
	   log.trace("image retrieved");   
		
	   
	   // Retrieve poi Images
	   result = this.mockMvc.perform(get("/poi/{idPoi}/image", saved.getId()))
			.andExpect(status().isOk())
			//.andExpect(jsonPath("$.links[?(@.rel == 'image[0]')].href").value(new StringContains(filename)))    // no reconoce bien
			.andExpect(jsonPath("$.links[?(@.rel == 'image[0]')].href").exists())    // no reconoce bien
			.andReturn();
	   log.trace("image list retrieved");   
		   
	   // Delete image
	   result = this.mockMvc.perform(delete("/poi/{idPoi}/image/{idImage}", saved.getId(), filename))
		   .andExpect(status().isNoContent())
		   .andReturn();
	   log.trace("image deleted");
	}
	
	
	@Test
	public void testInvalidPoiAndImageURL() throws Exception {
		BasicPoi saved = repo.save(DummyPoiFactory.POI_TEIDE);
		this.mockMvc.perform(get("/poi/{idPoi}/image/{idImage}", saved.getId(), "bbb"))    // idImage must starts with idPoi
			.andExpect(status().isNotFound())
			.andReturn();
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
