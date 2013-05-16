package info.spain.opencatalog.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.Poi;
import info.spain.opencatalog.repository.PoiRepository;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	/**
	 * Test DBRef
	 */
	@Test
	public void testDBRef(){
		
		List<Poi> related = new ArrayList<Poi>();
		
		
	
		for (int i=0; i<5; i++){
			Poi child = new Poi()
			.setName("child-" + i)
			.setLoc(new GeoLocation().setLat(28.2716).setLng(-16.6424));
			related.add(poiRepository.save(child));
		}

		Poi parent = new Poi()
			.setName("Parent")
			.setLoc(new GeoLocation().setLat(28.2716).setLng(-16.6424))
			.setRelated(related);
		
		Poi result = poiRepository.save(parent);
		
		String id = result.getId();
		assertNotNull(id);
	
		
	}

}
