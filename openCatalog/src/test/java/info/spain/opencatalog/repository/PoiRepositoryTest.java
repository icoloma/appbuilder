package info.spain.opencatalog.repository;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import info.spain.opencatalog.domain.DummyPoiFactory;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.DummyZoneFactory;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.lodging.Lodging;
import info.spain.opencatalog.domain.poi.types.PoiFactory;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;

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

	/**
	 * Test create 
	 */
	@Test
	public void testCreate() {
		Lodging poi = ((Lodging) PoiFactory.newInstance(PoiTypeID.HOTEL)).setName(new I18nText().setEs("My hotel")).setLocation(DummyPoiFactory.randomLocation());
		poi.setFlags(Flag.AIR_CONDITIONED);
		BasicPoi result = poiRepository.save(poi);
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
		BasicPoi poi = DummyPoiFactory.newPoi("testMongoTemplate");
		mongoTemplate.save(poi);
		String id = poi.getId();
		assertNotNull(id);
		BasicPoi result = mongoTemplate.findById(id, BasicPoi.class);
		assertNotNull(result);
		mongoTemplate.remove(result);
		result = mongoTemplate.findById(id, BasicPoi.class);
		assertNull(result);
	}
	
	@Test
	public void testFindByName(){
		BasicPoi poi = DummyPoiFactory.newPoi("findByName");
		mongoTemplate.save(poi);
		Pageable pageable = new PageRequest(0, 10);
		Page<BasicPoi> result = poiRepository.findByNameEsLikeIgnoreCase(poi.getName().getEs(), pageable);
	
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
		
		Zone madrid = DummyZoneFactory.ZONE_MADRID_CENTRO;
		mongoTemplate.save(madrid);
		
		List<BasicPoi> pois = poiRepository.findWithInZone(madrid.getId());
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
		
		Zone complex = DummyZoneFactory.ZONE_COMPLEX;
		mongoTemplate.save(complex);
		
		List<BasicPoi> pois = poiRepository.findWithInZone(complex.getId());
		assertEquals(1, pois.size());
		
	}

	@Test
	public void testWithIn(){
		BasicPoi retiro = DummyPoiFactory.POI_RETIRO;
		BasicPoi sol = DummyPoiFactory.POI_SOL;
		BasicPoi teide = DummyPoiFactory.POI_TEIDE;
		GeoLocation alaska = DummyPoiFactory.POI_ALASKA.getLocation();
		
		poiRepository.deleteAll();
		
		mongoTemplate.save(retiro);
		mongoTemplate.save(sol);
		mongoTemplate.save(teide);
		
		List<BasicPoi> result = poiRepository.findWithIn(retiro.getLocation().getLat(), retiro.getLocation().getLng(), 5);
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
		
		List<BasicPoi> pois = poiRepository.findByAddressArea1LikeIgnoreCase("adrid");
		assertEquals(3, pois.size());
		
		
		// Admin Area 2
		areas = poiRepository.findAdminArea2ByName("Tenerife");
		assertEquals(1, areas.size());
		
		pois = poiRepository.findByAddressArea2LikeIgnoreCase("Tenerife");
		assertEquals(2, pois.size());

		
	}
	
	
	
}
