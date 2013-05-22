package info.spain.opencatalog.domain;

import info.spain.opencatalog.repository.PoiRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MongoDbPopulator {

	private ApplicationContext context;
	
	private static Random random = new Random();
	
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
		populatePOI(4, 3);
	}

	

	private void populatePOI(int numParents, int maxChilds) {
		
		int numChilds = random.nextInt(maxChilds);
		
		PoiRepository poiRepository = context.getBean(PoiRepository.class);
		
		poiRepository.deleteAll();
		
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

}
