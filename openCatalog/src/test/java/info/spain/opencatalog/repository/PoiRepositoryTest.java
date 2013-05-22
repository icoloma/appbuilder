package info.spain.opencatalog.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
	 * Test POI Validations
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testPoiValidation() {
		Poi poi = PoiFactory.newPoi("testJSR303").setName(new I18nText().setEn("home")); // no default;
		// Poi poi = PoiFactory.newPoi("testJSR303").setName(new I18nText().set("EN", "home")); // no default;  FIXME: uncomment when fixed #8
		
		// Test validator directly
		Set<ConstraintViolation<Poi>> constraintViolations = validator.validate(poi);
		assertTrue(constraintViolations.size() > 0);

		// Test that repository use the validator 
		poiRepository.save(poi);
	}

	/**
	 * Test using Repository
	 */
	@Test
	public void testRepository() {
		Poi poi = PoiFactory.newPoi("testRepository");
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
