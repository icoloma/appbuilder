package info.spain.opencatalog.domain;

import info.spain.opencatalog.domain.Tag.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PoiFactory {
	
	private static double MIN_LAT = 40.3700;
	private static double MAX_LAT = 40.4900;
	private static double MIN_LNG = -3.7900;
	private static double MAX_LNG = -3.5500;
	
	private static Random random = new Random();
	
	public static Poi newPoi(String key){
		key = key + "-" + random.nextInt();
		Poi poi = new Poi()
//		.setName( new I18nText()                            //FIXME: uncomment when fixed #8
//			.set("ES", "es-"+key+"-name")
//			.set("EN", "en-"+key+"-name")
//			.set("DE", "en-"+key+"-name"))
//		.setDescription( new I18nText()
//			.set("ES", "es-"+key+"-description")
//			.set("EN", "en-"+key+"-description")
//			.set("DE", "en-"+key+"-description"))
		.setName( new I18nText()
			.setEs("es-"+key+"-name")
			.setEn("en-"+key+"-name")
			.setDe("en-"+key+"-name"))
		.setDescription( new I18nText()
			.setEs("es-"+key+"-description")
			.setEn("en-"+key+"-description")
			.setDe("en-"+key+"-description"))
		.setAddress(new Address()
			.setAddress( key + "-address")
			.setCity( key + "-city")
			.setZipCode(key+"-zipCode"))
		.setLocation(randomLocation())
		.setTags(randomTags(random.nextInt(4)));
		return poi;
	}
	
	private static List<Tag.Tags> randomTags(int numTags){
		List<Tag.Tags> result = new ArrayList<Tag.Tags>();
		for(int i=0; i<numTags; i++){
			Tags tag = Tags.values()[(random.nextInt(Tags.values().length))];
			result.add(tag);
		}
		return result;
	}

	private static GeoLocation randomLocation(){
		return new GeoLocation()
			.setLat(randomInRange(MIN_LAT, MAX_LAT))
			.setLng(randomInRange(MIN_LNG, MAX_LNG));
	}
	
	public static double randomInRange(double min, double max) {
		  double range = max - min;
		  double scaled = random.nextDouble() * range;
		  double shifted = scaled + min;
		  return shifted; // == (rand.nextDouble() * (max-min)) + min;
	}
	

}