package info.spain.opencatalog.domain;

import java.util.Random;

public class AbstractFactory {
	
	private static double MIN_LAT = 40.3700;
	private static double MAX_LAT = 40.4900;
	
	private static double MIN_LNG = -3.7900;
	private static double MAX_LNG = -3.5500;
	
	public static final GeoLocation GEO_CASA_CAMPO = new GeoLocation().setLat(40.4281).setLng(-3.7585);
	public static final GeoLocation GEO_RETIRO = new GeoLocation().setLat(40.4170).setLng(-3.6820);
	public static final GeoLocation GEO_SOL = new GeoLocation().setLat(40.416957).setLng(-3.703794);
	public static final GeoLocation GEO_TEIDE = new GeoLocation().setLat(28.2735).setLng(-16.6427);
	public static final GeoLocation GEO_PLAYA_TERESITAS= new GeoLocation().setLat(28.50864).setLng(-16.18679);
	public static final GeoLocation GEO_ROQUE_NUBLO= new GeoLocation().setLat(27.970201).setLng(-15.612573);
	public static final GeoLocation GEO_ALASKA = new GeoLocation().setLng(-149.9).setLat(65.9);
	
	
	private  static Random random = new Random();
	
	public static GeoLocation randomLocation(){
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
	
	protected static Random getRandom(){
		return random;
	}


}
