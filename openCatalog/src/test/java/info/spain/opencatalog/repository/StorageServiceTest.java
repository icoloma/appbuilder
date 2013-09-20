package info.spain.opencatalog.repository;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.net.MediaType;
import com.mongodb.gridfs.GridFSFile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/root-context.xml")
@ActiveProfiles("dev")
public class StorageServiceTest {
	
	private  static Random random = new Random();
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private GridFsOperations gridFsTemplate;
	
	@Test
	public void testGetNull(){
		assertNull(storageService.getByFilename(null));
	}
	
	@Test
	public void testSaveGetDelete() throws Exception {
		
		Resource res = new ClassPathResource("log4j.properties");  // sample file
		assertTrue(res.exists());
		
		String filename =  random.nextInt() + "-" + res.getFilename();
		
		assertFalse(storageService.existsFile(filename));

		GridFSFile saved = storageService.saveFile(res.getInputStream(), filename, MediaType.PLAIN_TEXT_UTF_8.toString());
		assertNotNull(saved);
		
		// get by filename
		GridFsResource file = storageService.getByFilename(filename);
		assertNotNull(file);
		assertNotNull(file.exists());
		assertTrue(file.contentLength() > 0);
		assertTrue(storageService.existsFile(filename));

		
		// delete
		storageService.deleteFile(filename);
		file = storageService.getByFilename(filename);
		assertNull(file);
	}

}
