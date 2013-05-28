package info.spain.opencatalog.domain;

import info.spain.opencatalog.domain.Tags.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableSet;

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
	
	private static List<Tag> randomTags(int numTags){
		List<Tag> result = new ArrayList<Tag>();
		for(int i=0; i<numTags; i++){
			Tag tag = Tag.values()[(random.nextInt(Tag.values().length))];
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

	public static Poi CASA_CAMPO = PoiFactory.newPoi("Casa de Campo").setLocation(new GeoLocation().setLat(40.4281).setLng(-3.7585));
	public static Poi RETIRO = PoiFactory.newPoi("Retiro").setLocation(new GeoLocation().setLat(40.4170).setLng(-3.6820));
	public static Poi SOL = PoiFactory.newPoi("Sol").setLocation(new GeoLocation().setLat(40.416957).setLng(-3.703794));
	public static Poi TEIDE = PoiFactory.newPoi("Teide").setLocation(new GeoLocation().setLat(28.2735).setLng(-16.6427));
	public static Poi ALASKA = PoiFactory.newPoi("Alaska").setLocation(new GeoLocation().setLng(-149.9).setLat(65.9));
	
	public static ImmutableSet<Poi> WELL_KNOWN_POIS = ImmutableSet.of(CASA_CAMPO, RETIRO, SOL, TEIDE, ALASKA);
	
	
	

}