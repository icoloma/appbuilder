package info.spain.opencatalog.exporter;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import info.spain.opencatalog.domain.PoiFactory;
import info.spain.opencatalog.domain.Tags;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.ZoneFactory;
import info.spain.opencatalog.domain.poi.Poi;
import info.spain.opencatalog.image.PoiImageUtils;
import info.spain.opencatalog.image.PoiImageUtilsMock;
import info.spain.opencatalog.repository.PoiRepository;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.io.Files;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/root-context.xml")
@ActiveProfiles("dev")
public class CatalogExporterTest {
	
	private static Integer NUM_TAGS = Tags.Tag.values().length;
	private static Integer NUM_POIS = 10;
	private static Integer NUM_ZONES =2;
	
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private PoiRepository poiRepository;
	
	@Autowired
	private MongoOperations mongoTemplate;
	
	private List<Zone> zones;
	private List<Poi> pois;
	private PoiImageUtils poiImageUtils = new PoiImageUtilsMock();
	
	@Before
	public void init(){
		// Como no se almacenan en la base de datos, asignamos id manualmente
		pois = PoiFactory.generatePois(NUM_POIS);
		for (Poi poi : pois) {
			poi.setId(UUID.randomUUID().toString());
		}
		
		zones = ZoneFactory.generateZones(NUM_ZONES);
		for(Zone zone: zones){
			zone.setId(UUID.randomUUID().toString());
		}
	}
	
	
	@Test
	public void testSQLiteExporter() throws Exception {
		File outputDir = Files.createTempDir();
		SQLiteExporter exporter = new SQLiteExporter(messageSource,  new ImageExporterImpl(poiImageUtils));
		export(exporter, outputDir);
		assertTrue(outputDir.exists());
		DataSource ds = SQLiteExporter.getDataSource(outputDir);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		assertEquals(NUM_POIS, jdbcTemplate.queryForObject("select count(*) from Poi", Integer.class));		
		assertEquals(NUM_ZONES, jdbcTemplate.queryForObject("select count(*) from Zone", Integer.class));
		assertEquals(NUM_TAGS, jdbcTemplate.queryForObject("select count(*) from Tag", Integer.class));
		checkImageFilesExists(new File(outputDir, ImageExporterImpl.DIR_NAME));
	}
	
	@Test
	public void testJSONExporter() throws Exception {
		File outputDir = Files.createTempDir();
		JSONExporter exporter = new JSONExporter(messageSource,  new ImageExporterImpl(poiImageUtils));
		export(exporter, outputDir);
		checkImageFilesExists(new File(outputDir, ImageExporterImpl.DIR_NAME));
		// TODO: Test json content
	}
	
	
	private void export(CatalogExporter exporter, File  outputDir){
		exporter.export(pois,zones,Tags.Tag.values(), outputDir);
		
	}
	
	private void checkImageFilesExists(File outputDir){
		String[] filesNames = outputDir.list();
		assertEquals(pois.size(), filesNames.length);
	}
	
	
		

}
