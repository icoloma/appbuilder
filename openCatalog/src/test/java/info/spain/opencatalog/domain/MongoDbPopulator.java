package info.spain.opencatalog.domain;

import info.spain.opencatalog.domain.poi.BasicPoi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class MongoDbPopulator {

	private static final int NUM_RANDOM_USERS = 30;

	
	private MongoOperations mongoTemplate;
	private GridFsOperations gridFsTemplate;
	
	private Zone zoneTenerife;

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
		mongoTemplate.insertAll(ImmutableList.copyOf(DummyUserFactory.WELL_KNOWN_USERS));
		
		User userTenerife = DummyUserFactory
				.newUser("userTenerife")
				.setEmail("userTenerife@example.com")
				.setPassword("1234567890")
				.setIdZones(Lists.newArrayList(zoneTenerife.getId()));
		
		
		mongoTemplate.insert(userTenerife);
		
		// random
		//mongoTemplate.insertAll(DummyUserFactory.generateUsers(NUM_RANDOM_USERS));

	}
	
	private void populateZones(){
		mongoTemplate.insert(DummyZoneFactory.ZONE_ALCALA_HENARES);
		mongoTemplate.insert(DummyZoneFactory.ZONE_MADRID_CENTRO);
		mongoTemplate.insert(DummyZoneFactory.ZONE_NORTE);
		zoneTenerife = DummyZoneFactory.ZONE_PROVINCIA_STA_CRUZ;
		mongoTemplate.insert(zoneTenerife);
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
