package info.spain.opencatalog.repository;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.DummyPoiFactory;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.ZoneFactory;
import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.lodging.Lodging;
import info.spain.opencatalog.domain.poi.lodging.RoomFlag;
import info.spain.opencatalog.domain.poi.types.PoiFactory;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;
import info.spain.opencatalog.domain.poi.types.PoiTypeRepository;

import java.util.List;

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

//	/**
//	 * Test Validations
//	 */
//	@Test(expected = ConstraintViolationException.class)
//	public void testPoiValidation() {
//		AbstractPoi poi = DummyPoiFactory.newPoi("testJSR303")
//			.setName(new I18nText().setEn("home")) // no default;
//			;
//		
//		// Test validator directly
//		Set<ConstraintViolation<AbstractPoi>> constraintViolations = validator.validate(poi);
//		assertTrue(constraintViolations.size() > 0);
//
//		// Test that repository use the validator 
//		poiRepository.save(poi);
//	}
//
	/**
	 * Test create 
	 */
	@Test
	public void testCreate() {
		Lodging poi = ((Lodging) PoiFactory.newInstance(PoiTypeID.HOTEL)).setName(new I18nText().setEs("My hotel")).setLocation(DummyPoiFactory.randomLocation());
		poi.setRoomFlags(RoomFlag.AIR_CONDITIONED);
		AbstractPoi result = poiRepository.save(poi);
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
		AbstractPoi poi = DummyPoiFactory.newPoi("testMongoTemplate");
		mongoTemplate.save(poi);
		String id = poi.getId();
		assertNotNull(id);
		AbstractPoi result = mongoTemplate.findById(id, AbstractPoi.class);
		assertNotNull(result);
		mongoTemplate.remove(result);
		result = mongoTemplate.findById(id, AbstractPoi.class);
		assertNull(result);
	}
	
	@Test
	public void testFindByName(){
		AbstractPoi poi = DummyPoiFactory.newPoi("findByName");
		mongoTemplate.save(poi);
		Pageable pageable = new PageRequest(0, 10);
		Page<AbstractPoi> result = poiRepository.findByNameEsLikeIgnoreCase(poi.getName().getEs(), pageable);
	
		assertEquals(result.getContent().get(0).getName().getEs(), poi.getName().getEs());
		mongoTemplate.remove(poi);
	}

	@Test
	public void testPoiInNormalZone(){

		poiRepository.deleteAll();
		zoneRepository.deleteAll();
		
		mongoTemplate.save(DummyPoiFactory.POI_RETIRO);
		mongoTemplate.save(DummyPoiFactory.POI_SOL);
		mongoTemplate.save(DummyPoiFactory.POI_CASA_CAMPO);
		mongoTemplate.save(DummyPoiFactory.POI_TEIDE);
		mongoTemplate.save(DummyPoiFactory.POI_ALASKA);
		
		Zone madrid = ZoneFactory.ZONE_MADRID_CENTRO;
		mongoTemplate.save(madrid);
		
		List<AbstractPoi> pois = poiRepository.findWithInZone(madrid.getId());
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
		
		mongoTemplate.save(DummyPoiFactory.POI_RETIRO);
		mongoTemplate.save(DummyPoiFactory.POI_SOL);
		mongoTemplate.save(DummyPoiFactory.POI_CASA_CAMPO);
		
		Zone complex = ZoneFactory.ZONE_COMPLEX;
		mongoTemplate.save(complex);
		
		List<AbstractPoi> pois = poiRepository.findWithInZone(complex.getId());
		assertEquals(1, pois.size());
		
	}

	@Test
	public void testWithIn(){
		AbstractPoi retiro = DummyPoiFactory.POI_RETIRO;
		AbstractPoi sol = DummyPoiFactory.POI_SOL;
		AbstractPoi teide = DummyPoiFactory.POI_TEIDE;
		GeoLocation alaska = DummyPoiFactory.POI_ALASKA.getLocation();
		
		poiRepository.deleteAll();
		
		mongoTemplate.save(retiro);
		mongoTemplate.save(sol);
		mongoTemplate.save(teide);
		
		List<AbstractPoi> result = poiRepository.findWithIn(retiro.getLocation().getLat(), retiro.getLocation().getLng(), 5);
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
		
		mongoTemplate.save(DummyPoiFactory.POI_RETIRO);
		mongoTemplate.save(DummyPoiFactory.POI_SOL);
		mongoTemplate.save(DummyPoiFactory.POI_CASA_CAMPO);
		mongoTemplate.save(DummyPoiFactory.POI_TEIDE);
		mongoTemplate.save(DummyPoiFactory.POI_PLAYA_TERESITAS);
		
		// Admin Area 1
		List<String> areas = poiRepository.findAdminArea1ByName("adrid");
		assertEquals(1, areas.size());
		
		List<AbstractPoi> pois = poiRepository.findByAddressArea1LikeIgnoreCase("adrid");
		assertEquals(3, pois.size());
		
		
		// Admin Area 2
		areas = poiRepository.findAdminArea2ByName("Tenerife");
		assertEquals(1, areas.size());
		
		pois = poiRepository.findByAddressArea2LikeIgnoreCase("Tenerife");
		assertEquals(2, pois.size());

		
	}
	
	
	
}
