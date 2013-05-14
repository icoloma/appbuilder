package travel.spain.opencatalog.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import travel.spain.opencatalog.domain.GeoLocation;
import travel.spain.opencatalog.domain.Poi;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@ActiveProfiles("dev")
public class PoiRepositoryTest {

	@Autowired
	private PoiRepository poiRepository;

	@Autowired
	private MongoOperations mongoTemplate;

	/**
	 * Test JSR-303 Validations
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testJSR303Repository() {
		assertNotNull(poiRepository);
		Poi poi = new Poi().setDescription("Volcan del Teide");
		poiRepository.save(poi);
	}

	/**
	 * Test using Repository
	 */
	@Test()
	public void testRepository() {
		Poi poi = new Poi()
			.setName("Teide")
			.setDescription("Volcan del Teide")
			.setLoc(new GeoLocation().setLat(28.2716).setLng(-16.6424));
		Poi result = poiRepository.save(poi);
		String id = result.getId();
		assertNotNull(id);
		assertEquals(Double.valueOf(28.2716), result.getLoc().getLat());
		poiRepository.delete(id);
		result = poiRepository.findOne(id);
		assertNull(result);
	
	}

	/**
	 * Test using mongoTemplate
	 */
	@Test
	public void testMongoTemplate() {
		Poi poi = new Poi()
			.setName("Teide")
			.setDescription("Volcan del Teide")
			.setLoc(new GeoLocation().setLat(28.2716).setLng(-16.6424));
		mongoTemplate.save(poi);
		String id = poi.getId();
		assertNotNull(id);
		Poi result = mongoTemplate.findById(id, Poi.class);
		assertNotNull(result);
		mongoTemplate.remove(result);
		result = mongoTemplate.findById(id, Poi.class);
		assertNull(result);
	}

}
