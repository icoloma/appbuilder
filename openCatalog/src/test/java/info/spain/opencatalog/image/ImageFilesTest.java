package info.spain.opencatalog.image;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;
import info.spain.opencatalog.repository.StorageService;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/root-context.xml")
@ActiveProfiles("dev")
public class ImageFilesTest {
	
	@Autowired
	StorageService storageService;
	
	@Autowired
	PoiImageUtils poiImageUtils;

	@Autowired
	private GridFsOperations gridFSTemplate;
	
	@Test
	public void testImageFilenames() throws Exception {

		/* sample idPoi */
		String idPoi = UUID.randomUUID().toString();
		
		/* Num images to save */
		int numImages = 3;
		
		// No previous image files
		List<String> filenames = storageService.getFilenamesLike(idPoi);
		assertEquals(0, filenames.size());
		
		// Save image files
		for (int i=0; i<numImages; i++){
			Resource img = new ClassPathResource("img/" + i + ".jpg");
			assertTrue(img.exists());
			poiImageUtils.saveImage(idPoi, img.getInputStream(), "image/jpg");
		}
		
		// Assert has images
		assertTrue( poiImageUtils.hasImages(idPoi));

		filenames = storageService.getFilenamesLike(idPoi);
		assertEquals(numImages, filenames.size());
		
		List<String> images = poiImageUtils.getPoiImageFilenames(idPoi);
		assertEquals(numImages, images.size());
		
		// Retrieve image files
		for (String filename: images) {
			assertTrue(filename.startsWith(idPoi + "::"));
			ImageResource resource = poiImageUtils.getPoiImageResource(filename);
			assertNotNull(resource);
		}
		
		
		// Delete images
		for (String filename: images) {
			storageService.deleteFile(filename);
		}
		
		filenames = storageService.getFilenamesLike(idPoi);
		assertEquals(0, filenames.size());
		
	}
	
	@Before
	public void before(){
		gridFSTemplate.delete(query(whereFilename().regex("")));
	}
	
	

}
