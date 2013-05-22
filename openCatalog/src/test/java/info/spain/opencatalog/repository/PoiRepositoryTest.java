package info.spain.opencatalog.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.domain.PoiFactory;

import java.util.ArrayList;
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
		Page<Poi> result = poiRepository.findByNameEsLike(poi.getName().getEs(), pageable);
	
		assertEquals(result.getContent().get(0).getName().getEs(), poi.getName().getEs());
		mongoTemplate.remove(poi);
	}

	@Test
	public void testGeoLocation(){
		Poi retiro = PoiFactory.newPoi("Retiro").setLocation(new GeoLocation().setLat(40.4170).setLng(-3.6820));
		Poi sol = PoiFactory.newPoi("Sol").setLocation(new GeoLocation().setLat(40.416957).setLng(-3.703794));
		Poi teide = PoiFactory.newPoi("teide").setLocation(new GeoLocation().setLat(28.2735).setLng(-16.6427));
		
		GeoLocation alaska = new GeoLocation().setLng(-149.9).setLat(65.9);
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
	
	
	/**
	 * Test DBRef
	 */
	@Test
	public void testDBRef() {

		int NUM_CHILDS = 5;

		List<Poi> related = new ArrayList<Poi>();

		for (int i = 0; i < NUM_CHILDS; i++) {
			Poi child = PoiFactory.newPoi("child-" + i);
			related.add(poiRepository.save(child));
		}

		Poi parent = PoiFactory.newPoi("parent").setRelated(related);
		Poi result = poiRepository.save(parent);

		String id = result.getId();
		assertNotNull(id);
		assertEquals(NUM_CHILDS, result.getRelated().size());

		poiRepository.delete(result);

		// no cascade delete
		for (int i = 0; i < NUM_CHILDS; i++) {
			poiRepository.delete(related.get(i));
		}

	}

}
