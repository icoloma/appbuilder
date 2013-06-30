package info.spain.opencatalog.domain;

import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.image.PoiImageUtils;
import info.spain.opencatalog.image.PoiImageUtilsImpl;
import info.spain.opencatalog.repository.StorageService;

import java.io.FileInputStream;
import java.util.Collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.MediaType;

import com.google.common.collect.ImmutableList;

public class MongoDbPopulator {
	
	private static final int NUM_RANDOM_POIS = 1;
	private static final int NUM_RANDOM_ZONES = 2;
	private static final int NUM_RANDOM_USERS = 30;

	
	private MongoOperations mongoTemplate;
	private GridFsOperations gridFsTemplate;
	private PoiImageUtils poiImageUtils;
	
	public MongoDbPopulator(ApplicationContext context) {
		mongoTemplate = context.getBean(MongoOperations.class);
		gridFsTemplate = context.getBean(GridFsOperations.class);
		StorageService storageService = context.getBean(StorageService.class);
		poiImageUtils = new PoiImageUtilsImpl(storageService);
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
		mongoTemplate.dropCollection(AbstractPoi.class);
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
		// well known
		mongoTemplate.insertAll(ImmutableList.copyOf(ZoneFactory.WELL_KNOWN_ZONES));
		// random
		mongoTemplate.insertAll(ZoneFactory.generateZones(NUM_RANDOM_ZONES));
	}

	private void populatePois()	{
		// well known
		//insertAllPoi(ImmutableSet.copyOf(DummyPoiFactory.WELL_KNOWN_POIS));
		//mongoTemplate.insertAll(ImmutableSet.copyOf(DummyPoiFactory.WELL_KNOWN_POIS));
		// random pois
		//insertAllPoi(DummyPoiFactory.generatePois(NUM_RANDOM_POIS));
		//mongoTemplate.insertAll(DummyPoiFactory.generatePois(NUM_RANDOM_POIS));
		
//		mongoTemplate.insert(DummyPoiFactory.HOTEL);
//		mongoTemplate.insert(DummyPoiFactory.CAMPING);
//		mongoTemplate.insert(DummyPoiFactory.APARTMENT);
//
//		mongoTemplate.insert(DummyPoiFactory.MUSEUM);
//		mongoTemplate.insert(DummyPoiFactory.MONUMENT);
//		mongoTemplate.insert(DummyPoiFactory.GARDEN);
//		
//		mongoTemplate.insert(DummyPoiFactory.BEACH);
//		mongoTemplate.insert(DummyPoiFactory.NATURAL_PARK);
//
//		mongoTemplate.insert(DummyPoiFactory.ECO_TOURISM);
		mongoTemplate.insert(DummyPoiFactory.GOLF);
		mongoTemplate.insert(DummyPoiFactory.NAUTICAL_STATION);
		
		
	}
	
	private void insertAllPoi(Collection<AbstractPoi> pois){
		for (AbstractPoi poi : pois) {
			mongoTemplate.save(poi);
			Resource image = DummyPoiFactory.randomImage();
			try { 
				poiImageUtils.saveImage(poi.getId(), new FileInputStream(image.getFile()), MediaType.IMAGE_JPEG_VALUE);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
		}
	}


}
