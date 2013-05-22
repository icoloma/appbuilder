package info.spain.opencatalog.domain;

import info.spain.opencatalog.repository.PoiRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MongoDbPopulator {

	private ApplicationContext context;

	public MongoDbPopulator(ApplicationContext context) {
		this.context = context;
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
		populatePOI();
	}

	

	private void populatePOI() {
		int NUM_CHILDS = 5;
		PoiRepository poiRepository = context.getBean(PoiRepository.class);
		
		poiRepository.deleteAll();
		
		List<Poi> related = new ArrayList<Poi>();
		for (int i = 0; i < NUM_CHILDS; i++) {
			Poi child = PoiFactory.newPoi("child-" + i);
			related.add(poiRepository.save(child));
		}
		Poi parent = PoiFactory.newPoi("parent").setRelated(related);
		poiRepository.save(parent);
	}

}
