package info.spain.opencatalog.domain;

import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.repository.ZoneRepository;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class MongoDbPopulator {
	
	private static final int MAX_POI = 300;
	private static final int MAX_ZONES = 30;

	
	private ZoneRepository zoneRepository;
	private PoiRepository poiRepository;
	private MongoOperations mongoTemplate;
	
	public MongoDbPopulator(ApplicationContext context) {
		poiRepository = context.getBean(PoiRepository.class);
		mongoTemplate = context.getBean(MongoOperations.class);
		zoneRepository = context.getBean(ZoneRepository.class);
	}

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("dev");
		ctx.setConfigLocation("/spring/root-context.xml");
		ctx.refresh();
		MongoDbPopulator mongoDBPopulator = new MongoDbPopulator(ctx);
		mongoDBPopulator.clearAll();
		mongoDBPopulator.populate();
		ctx.close();
	}
	
	public void clearAll(){
		mongoTemplate.dropCollection(Poi.class);
		mongoTemplate.dropCollection(Zone.class);
	}

	public void populate() {
		populatePois();
		populateZones();
	}
	
	private void populateZones(){
		// well known
		mongoTemplate.insertAll(ImmutableList.copyOf(ZoneFactory.WELL_KNOWN_ZONES));
		// random
		for (int i = 0; i < MAX_ZONES; i++) {
			Zone zone = ZoneFactory.newZone("" + i);
			zoneRepository.save(zone);
		}
	}

	private void populatePois()	{
		// well known
		mongoTemplate.insertAll(ImmutableSet.copyOf(PoiFactory.WELL_KNOWN_POIS));
		// random pois
		for (int i=0; i < MAX_POI; i++) {
			Poi poi = PoiFactory.newPoi(""+i);
			poiRepository.save(poi);
		}
	}


}
