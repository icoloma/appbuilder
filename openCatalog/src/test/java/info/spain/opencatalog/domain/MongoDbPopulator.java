package info.spain.opencatalog.domain;

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
import com.google.common.collect.ImmutableSet;

public class MongoDbPopulator {
	
	private static final int MAX_POI = 300;
	private static final int MAX_ZONES = 30;
	private static final int MAX_USERS = 30;

	
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
		mongoTemplate.dropCollection(Poi.class);
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
		mongoTemplate.insertAll(UserFactory.generateUsers(MAX_USERS));

	}
	
	private void populateZones(){
		// well known
		mongoTemplate.insertAll(ImmutableList.copyOf(ZoneFactory.WELL_KNOWN_ZONES));
		// random
		mongoTemplate.insertAll(ZoneFactory.generateZones(MAX_ZONES));
	}

	private void populatePois()	{
		// well known
		insertAllPoi(ImmutableSet.copyOf(PoiFactory.WELL_KNOWN_POIS));
		//mongoTemplate.insertAll(ImmutableSet.copyOf(PoiFactory.WELL_KNOWN_POIS));
		// random pois
		insertAllPoi(PoiFactory.generatePois(MAX_POI));
		//mongoTemplate.insertAll(PoiFactory.generatePois(MAX_POI));
	}
	
	private void insertAllPoi(Collection<Poi> pois){
		for (Poi poi : pois) {
			mongoTemplate.save(poi);
			Resource image = PoiFactory.randomImage();
			try { 
				poiImageUtils.saveImage(poi.getId(), new FileInputStream(image.getFile()), MediaType.IMAGE_JPEG_VALUE);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
		}
	}


}
