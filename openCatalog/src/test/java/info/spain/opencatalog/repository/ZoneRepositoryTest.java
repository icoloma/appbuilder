package info.spain.opencatalog.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.ZoneFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ZoneRepositoryTest {

	@Autowired
	private ZoneRepository zoneRepository;

	@Autowired
	private MongoOperations mongoTemplate;

	
	/**
	 * Test create 
	 */
	@Test
	public void testCreate() {
		Zone zone = ZoneFactory.newZone("testCreate");
		Zone result = zoneRepository.save(zone);
		String id = result.getId();
		assertNotNull(id);
		zoneRepository.delete(id);
		result = zoneRepository.findOne(id);
		assertNull(result);
	}

	
	
	@Test
	public void testFindByName(){
		Zone zone = ZoneFactory.newZone("findByName");
		mongoTemplate.save(zone);
		Pageable pageable = new PageRequest(0, 10);
		Page<Zone> result = zoneRepository.findByNameLike(zone.getName(), pageable);
	
		assertEquals(result.getContent().get(0).getName(), zone.getName());
		mongoTemplate.remove(zone);
	}

	/*
	@Test
	public void testGeoLocation(){
		Zone retiro = ZoneFactory.RETIRO;
		Zone sol = ZoneFactory.SOL;
		Zone teide = ZoneFactory.TEIDE;
		GeoLocation alaska = ZoneFactory.ALASKA.getLocation();
		
		
		zoneRepository.deleteAll();
		
		mongoTemplate.save(retiro);
		mongoTemplate.save(sol);
		mongoTemplate.save(teide);
		
		List<Zone> result = zoneRepository.findWithIn(retiro.getLocation().getLat(), retiro.getLocation().getLng(), 5);
		assertEquals(2, result.size());
		
		result = zoneRepository.findWithIn(teide.getLocation().getLat(), teide.getLocation().getLng(), 5);
		assertEquals(1, result.size());

		result = zoneRepository.findWithIn(alaska.getLat(), alaska.getLng(), 5);
		assertEquals(0, result.size());
		
		
		mongoTemplate.remove(retiro);
		mongoTemplate.remove(sol);
		mongoTemplate.remove(teide);
	}
	*/
}
