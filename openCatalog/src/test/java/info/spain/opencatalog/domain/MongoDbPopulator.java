package info.spain.opencatalog.domain;

import info.spain.opencatalog.repository.PoiRepository;
import info.spain.opencatalog.repository.ZoneRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.google.common.collect.ImmutableSet;

public class MongoDbPopulator {
	
	private static final int MAX_POI = 300;
	private static final int MAX_POI_CHILDS = 0;  // no related
	private static final int MAX_ZONES = 30;

	
	private ZoneRepository zoneRepository;
	private PoiRepository poiRepository;
	private MongoOperations mongoTemplate;
	
	private static Random random = new Random();
	
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
		mongoDBPopulator.populate();
		ctx.close();
	}

	public void populate() {
		PopulateWellKonwnPois();
		populateRandomPoi(MAX_POI, MAX_POI_CHILDS);
		populateRandomZones(MAX_ZONES);
	}

	private void PopulateWellKonwnPois(){
		mongoTemplate.insertAll(ImmutableSet.copyOf(PoiFactory.WELL_KNOWN_POIS));
		
	}

	private void populateRandomPoi(int numParents, int maxChilds) {
		int numChilds = maxChilds > 0 ? random.nextInt(maxChilds) : 0;
		
		for (int p=0; p < numParents; p++) {
			List<Poi> related = new ArrayList<Poi>();
			for (int i = 0; i < numChilds; i++) {
				Poi child = PoiFactory.newPoi("c-" + i);
				related.add(poiRepository.save(child));
			}
			Poi parent = PoiFactory.newPoi("p-"+p).setRelated(related);
			poiRepository.save(parent);
		}
	}
	
	private void populateRandomZones(int maxZones) {
		for (int i = 0; i < maxZones; i++) {
			Zone zone = ZoneFactory.newZone("" + i);
			zoneRepository.save(zone);
		}
	}

}
