package info.spain.opencatalog.repository;

import static junit.framework.Assert.assertEquals;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import info.spain.opencatalog.api.controller.SearchQuery;
import info.spain.opencatalog.domain.DummyPoiFactory;
import info.spain.opencatalog.domain.MongoDbPopulator;
import info.spain.opencatalog.domain.Zone;
import info.spain.opencatalog.domain.DummyZoneFactory;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.Flag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/root-context.xml")
@ActiveProfiles("dev")
public class CustomQueryCriteriasTest {

		
	@Autowired
	MongoOperations mongoTemplate;
	
	@Autowired
	ZoneRepository zoneRepository;
	
	PoiRepositoryImpl poiRepositoryImpl = new PoiRepositoryImpl();
	
	Zone tenerife;
	
	
	@Before
	public void init() throws Exception{
		MongoDbPopulator.main(new String[]{});
		tenerife = zoneRepository.save(DummyZoneFactory.ZONE_PROVINCIA_STA_CRUZ);
	}
	
	
	@Test
	public void testByLastModified() throws Exception {
		// All data
		SearchQuery query = new SearchQuery();
		query.setUpdatedAfter(new DateTime().minusYears(1).toString());
		
		Criteria criteria = poiRepositoryImpl.getCriteriaUpatedAfter(query);
		List<BasicPoi> result = mongoTemplate.find( query(criteria), BasicPoi.class);
		assertEquals(14, result.size());
		
		
		result = mongoTemplate.find(query(where("lastModified").gt(new DateTime().plusYears(1).toDate())), BasicPoi.class);
		assertEquals(0, result.size());
	}


	@Test
	public void testFindByFlags() throws Exception {
		
		Set<Flag> flags = DummyPoiFactory.BEACH.getFlags();
		List<String> sFlags = new ArrayList<String>();
		for (Flag flag : flags) {
			sFlags.add(flag.toString());
		}
		
		Criteria criteria = poiRepositoryImpl.getCriteriaValueInArrayCriteria("flags", sFlags);

		List<BasicPoi>  result = mongoTemplate.find(query(criteria), BasicPoi.class);
		assertEquals(2, result.size());
	}
	
	@Test
	public void testFindByZone() throws Exception {
		Criteria criteria = poiRepositoryImpl.getCriteriaByZone(tenerife);
		List<BasicPoi>  result = mongoTemplate.find(query(criteria), BasicPoi.class);
		assertEquals(3, result.size());  // Teide & Teresitas
	}

	
	
	@Test
	public void testFindByeTypeId() throws Exception {
		Query q = query(where("type").in( Lists.newArrayList("BEACH", "MUSEUM")));
		System.out.println( "\n\nQUERY: " + q);
		List<BasicPoi>  result = mongoTemplate.find(q, BasicPoi.class);
		assertEquals(3, result.size());

	}
	
	
	
	
}
