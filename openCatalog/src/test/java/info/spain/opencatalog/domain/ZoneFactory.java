package info.spain.opencatalog.domain;

import java.util.ArrayList;
import java.util.List;

public class ZoneFactory extends AbstractFactory {
	
	public static Zone newZone(String key){
		key = key + "-" + getRandom().nextInt();
		Zone zone= new Zone()
		.setName(key+"-name")
		.setDescription(key+"-description")
		.setAddress(new Address()
			.setAdminArea1( key + "-area1")
			.setAdminArea2( key + "-area2"))
		.setPath(randomPolygon());
		return zone;
	}
	

	private static List<GeoLocation> randomPolygon(){
		List<GeoLocation> result = new ArrayList<GeoLocation>();
		
		
		return result;
	}
	
	public static double randomInRange(double min, double max) {
		  double range = max - min;
		  double scaled = getRandom().nextDouble() * range;
		  double shifted = scaled + min;
		  return shifted; // == (rand.nextDouble() * (max-min)) + min;
	}
/*
	public static Zone CASA_CAMPO = ZoneFactory.newZone("Casa de Campo").setLocation(new GeoLocation().setLat(40.4281).setLng(-3.7585));
	public static Zone RETIRO = ZoneFactory.newZone("Retiro").setLocation(new GeoLocation().setLat(40.4170).setLng(-3.6820));
	public static Zone SOL = ZoneFactory.newZone("Sol").setLocation(new GeoLocation().setLat(40.416957).setLng(-3.703794));
	public static Zone TEIDE = ZoneFactory.newZone("Teide").setLocation(new GeoLocation().setLat(28.2735).setLng(-16.6427));
	public static Zone ALASKA = ZoneFactory.newZone("Alaska").setLocation(new GeoLocation().setLng(-149.9).setLat(65.9));
	
	public static ImmutableSet<Zone> WELL_KNOWN_POIS = ImmutableSet.of(CASA_CAMPO, RETIRO, SOL, TEIDE, ALASKA);

*/
	
	

}