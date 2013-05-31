package info.spain.opencatalog.domain;

import java.util.Random;

public class AbstractFactory {
	
	private static double MIN_LAT = 40.3700;
	private static double MAX_LAT = 40.4900;
	
	private static double MIN_LNG = -3.7900;
	private static double MAX_LNG = -3.5500;
	
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
