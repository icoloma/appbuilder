package info.spain.opencatalog.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.domain.PoiFactory;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.ZoneFactory;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/root-context.xml")
@ActiveProfiles("dev")
public class PoiRepositoryTest {

	@Autowired
	private PoiRepository poiRepository;
	
	@Autowired
	private ZoneRepository zoneRepository;
	

	@Autowired
	private MongoOperations mongoTemplate;

	@Autowired()
	@Qualifier("localValidatorFactoryBean")
	private  Validator validator;

	/**
	 * Test Validations
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testPoiValidation() {
		Poi poi = PoiFactory.newPoi("testJSR303").setName(new I18nText().setEn("home")); // no default;
		
		// Test validator directly
		Set<ConstraintViolation<Poi>> constraintViolations = validator.validate(poi);
		assertTrue(constraintViolations.size() > 0);

		// Test that repository use the validator 
		poiRepository.save(poi);
	}

	/**
	 * Test create 
	 */
	@Test
	public void testCreate() {
		Poi poi = PoiFactory.newPoi("testCreate");
		Poi result = poiRepository.save(poi);
		String id = result.getId();
		assertNotNull(id);
		poiRepository.delete(id);
		result = poiRepository.findOne(id);
		assertNull(result);
	}

	/**
	 * Test using mongoTemplate
	 */
	@Test
	public void testMongoTemplate() {
		Poi poi = PoiFactory.newPoi("testMongoTemplate");
		mongoTemplate.save(poi);
		String id = poi.getId();
		assertNotNull(id);
		Poi result = mongoTemplate.findById(id, Poi.class);
		assertNotNull(result);
		mongoTemplate.remove(result);
		result = mongoTemplate.findById(id, Poi.class);
		assertNull(result);
	}
	
	@Test
	public void testFindByName(){
		Poi poi = PoiFactory.newPoi("findByName");
		mongoTemplate.save(poi);
		Pageable pageable = new PageRequest(0, 10);
		Page<Poi> result = poiRepository.findByNameEsLikeIgnoreCase(poi.getName().getEs(), pageable);
	
		assertEquals(result.getContent().get(0).getName().getEs(), poi.getName().getEs());
		mongoTemplate.remove(poi);
	}

	@Test
	public void testPoiInNormalZone(){

		poiRepository.deleteAll();
		zoneRepository.deleteAll();
		
		mongoTemplate.save(PoiFactory.POI_RETIRO);
		mongoTemplate.save(PoiFactory.POI_SOL);
		mongoTemplate.save(PoiFactory.POI_CASA_CAMPO);
		mongoTemplate.save(PoiFactory.POI_TEIDE);
		mongoTemplate.save(PoiFactory.POI_ALASKA);
		
		Zone madrid = ZoneFactory.ZONE_MADRID_CENTRO;
		mongoTemplate.save(madrid);
		
		List<Poi> pois = poiRepository.findWithInZone(madrid.getId());
		assertEquals(3, pois.size());
	}
	
	
	/**
	 * Formamos una zona en forma de pajarita que deja el retiro dentro de los límites (NW,NE,SE,SO) pero fuera del polígono
	 * Puerta del Sol también cae dentro
	 */
	@Test
	public void testPoiInComplexZone(){

		poiRepository.deleteAll();
		zoneRepository.deleteAll();
		
		mongoTemplate.save(PoiFactory.POI_RETIRO);
		mongoTemplate.save(PoiFactory.POI_SOL);
		mongoTemplate.save(PoiFactory.POI_CASA_CAMPO);
		
		Zone complex = ZoneFactory.ZONE_COMPLEX;
		mongoTemplate.save(complex);
		
		List<Poi> pois = poiRepository.findWithInZone(complex.getId());
		assertEquals(1, pois.size());
		
	}

	@Test
	public void testWithIn(){
		Poi retiro = PoiFactory.POI_RETIRO;
		Poi sol = PoiFactory.POI_SOL;
		Poi teide = PoiFactory.POI_TEIDE;
		GeoLocation alaska = PoiFactory.POI_ALASKA.getLocation();
		
		poiRepository.deleteAll();
		
		mongoTemplate.save(retiro);
		mongoTemplate.save(sol);
		mongoTemplate.save(teide);
		
		List<Poi> result = poiRepository.findWithIn(retiro.getLocation().getLat(), retiro.getLocation().getLng(), 5);
		assertEquals(2, result.size());
		
		result = poiRepository.findWithIn(teide.getLocation().getLat(), teide.getLocation().getLng(), 5);
		assertEquals(1, result.size());

		result = poiRepository.findWithIn(alaska.getLat(), alaska.getLng(), 5);
		assertEquals(0, result.size());
		
		
		mongoTemplate.remove(retiro);
		mongoTemplate.remove(sol);
		mongoTemplate.remove(teide);
	}
	
	@Test
	public void testPoisInAdminArea(){

		poiRepository.deleteAll();
		
		mongoTemplate.save(PoiFactory.POI_RETIRO);
		mongoTemplate.save(PoiFactory.POI_SOL);
		mongoTemplate.save(PoiFactory.POI_CASA_CAMPO);
		mongoTemplate.save(PoiFactory.POI_TEIDE);
		mongoTemplate.save(PoiFactory.POI_PLAYA_TERESITAS);
		
		// Admin Area 1
		List<String> areas = poiRepository.findAdminArea1ByName("adrid");
		assertEquals(1, areas.size());
		
		List<Poi> pois = poiRepository.findByAddressArea1LikeIgnoreCase("adrid");
		assertEquals(3, pois.size());
		
		
		// Admin Area 2
		areas = poiRepository.findAdminArea2ByName("Tenerife");
		assertEquals(1, areas.size());
		
		pois = poiRepository.findByAddressArea2LikeIgnoreCase("Tenerife");
		assertEquals(2, pois.size());

		
	}
	
	
	
}
