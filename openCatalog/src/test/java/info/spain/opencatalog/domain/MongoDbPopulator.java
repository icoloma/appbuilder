package info.spain.opencatalog.domain;

import info.spain.opencatalog.domain.poi.BasicPoi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import com.google.common.collect.ImmutableList;

public class MongoDbPopulator {

	private static final int NUM_RANDOM_USERS = 30;

	
	private MongoOperations mongoTemplate;
	private GridFsOperations gridFsTemplate;

	public MongoDbPopulator(ApplicationContext context) {
		mongoTemplate = context.getBean(MongoOperations.class);
		gridFsTemplate = context.getBean(GridFsOperations.class);
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
		mongoTemplate.dropCollection(BasicPoi.class);
		mongoTemplate.dropCollection(Zone.class);
		mongoTemplate.dropCollection(User.class);
		gridFsTemplate.delete(new Query());
		
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
		mongoTemplate.insertAll(UserFactory.generateUsers(NUM_RANDOM_USERS));

	}
	
	private void populateZones(){
		mongoTemplate.insert(DummyZoneFactory.ZONE_ALCALA_HENARES);
		mongoTemplate.insert(DummyZoneFactory.ZONE_MADRID_CENTRO);
		mongoTemplate.insert(DummyZoneFactory.ZONE_NORTE);
		mongoTemplate.insert(DummyZoneFactory.ZONE_PROVINCIA_STA_CRUZ);
	}

	private void populatePois()	{
		// Basic
		mongoTemplate.insert(DummyPoiFactory.NATURAL_PARK);
		mongoTemplate.insert(DummyPoiFactory.BEACH);

		// Lodging
		mongoTemplate.insert(DummyPoiFactory.HOTEL);
		mongoTemplate.insert(DummyPoiFactory.CAMPING);
		mongoTemplate.insert(DummyPoiFactory.APARTMENT);


		// Business
		mongoTemplate.insert(DummyPoiFactory.ECO_TOURISM);
		mongoTemplate.insert(DummyPoiFactory.GOLF);
		mongoTemplate.insert(DummyPoiFactory.NAUTICAL_STATION);
		mongoTemplate.insert(DummyPoiFactory.SKI_STATION);
		
		// Culture
		mongoTemplate.insert(DummyPoiFactory.MUSEUM);
		mongoTemplate.insert(DummyPoiFactory.GARDEN);
		mongoTemplate.insert(DummyPoiFactory.MONUMENT);
		
	}
	


}
