package info.spain.opencatalog.domain;

import java.util.UUID;

import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.repository.UserRepository;
import info.spain.opencatalog.repository.ZoneRepository;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class MongoDbPopulator {
	
	private static final int MAX_POI = 300;
	private static final int MAX_ZONES = 30;
	private static final int MAX_USERS = 30;

	
	private ZoneRepository zoneRepository;
	private PoiRepository poiRepository;
	private MongoOperations mongoTemplate;
	private UserRepository userRepository;
	
	public MongoDbPopulator(ApplicationContext context) {
		poiRepository = context.getBean(PoiRepository.class);
		mongoTemplate = context.getBean(MongoOperations.class);
		zoneRepository = context.getBean(ZoneRepository.class);
		userRepository = context.getBean(UserRepository.class);
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
		mongoTemplate.dropCollection(User.class);
	}

	public void populate() {
		populatePois();
		populateZones();
		populateUsers();
	}
	
	private void populateUsers(){
		// well known
		mongoTemplate.insertAll(ImmutableList.copyOf(UserFactory.WELL_KNOWN_USERS));
		// random
		for (int i = 0; i < MAX_USERS; i++) {
			User user = UserFactory.newUser("" + i);
			userRepository.save(user);
		}
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
			Poi poi = PoiFactory.newPoi(UUID.randomUUID().toString()+i);
			poiRepository.save(poi);
		}
	}


}
