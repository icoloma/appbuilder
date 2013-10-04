package info.spain.opencatalog.repository;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import info.spain.opencatalog.domain.DummyZoneFactory;
import info.spain.opencatalog.domain.Zone;

import java.util.List;

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

import com.google.common.collect.Lists;


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
		Zone zone = DummyZoneFactory.newZone("testCreate");
		Zone result = zoneRepository.save(zone);
		String id = result.getId();
		assertNotNull(id);
		zoneRepository.delete(id);
		result = zoneRepository.findOne(id);
		assertNull(result);
	}

	@Test
	public void testFindByName(){
		Zone zone = DummyZoneFactory.newZone("findByName");
		mongoTemplate.save(zone);
		Pageable pageable = new PageRequest(0, 10);
		Page<Zone> result = zoneRepository.findByNameIgnoreCaseLike(zone.getName(), pageable);
	
		assertEquals(result.getContent().get(0).getName(), zone.getName());
		mongoTemplate.remove(zone);
	}
	
	
	@Test
	public void testFindByIds(){
		Zone zone1 = zoneRepository.save(DummyZoneFactory.newZone("zone1"));
		Zone zone2 = zoneRepository.save(DummyZoneFactory.newZone("zone2"));
		Zone zone3 = zoneRepository.save(DummyZoneFactory.newZone("zone3"));
		
		String[] ids = new String[] { zone1.getId(), zone2.getId() };
		
		List<Zone> dbZones = zoneRepository.findByIds(ids);
		assertEquals( 2, dbZones.size());

		List<String> dbIds = Lists.newArrayList(dbZones.get(0).getId(), dbZones.get(1).getId());
		
		assertTrue( dbIds.contains(zone1.getId()));
		assertTrue( dbIds.contains(zone2.getId()));
		assertFalse( dbIds.contains(zone3.getId()));
		
	}


		
}
