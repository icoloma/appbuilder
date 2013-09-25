package info.spain.opencatalog.domain;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

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
		
		for (int i=0; i< getRandom().nextInt(3)+3; i++){
			result.add( randomLocation());
		}
		
		return result;
	}
	
	public static List<Zone> generateZones(int maxZones){
		List<Zone> result = new ArrayList<Zone>();
		for (int i = 0; i < maxZones; i++) {
			result.add(newZone("" + i));
		}
		return result;
	}
	
	public static final Zone ZONE_MADRID_CENTRO  = ZoneFactory.newZone("Madrid").setPath( ImmutableList.of(
			new GeoLocation().setLat(40.5021).setLng(-3.797),
			new GeoLocation().setLat(40.4752).setLng(-3.591),
			new GeoLocation().setLat(40.3761).setLng(-3.634),
			new GeoLocation().setLat(40.3677).setLng(-3.788)
			));
	public static final Zone ZONE_ALCALA_HENARES = ZoneFactory.newZone("Alcala de Henares").setPath( ImmutableList.of(
			new GeoLocation().setLat(40.5138).setLng(-3.4185),
			new GeoLocation().setLat(40.5168).setLng(-3.3198),
			new GeoLocation().setLat(40.4702).setLng(-3.3263),
			new GeoLocation().setLat(40.4652).setLng(-3.4202)
			));
	
	public static final Zone ZONE_PROVINCIA_STA_CRUZ = ZoneFactory.newZone("Santa Cruz de Tenerife").setPath( ImmutableList.of(
			new GeoLocation().setLat(29.084976575985912).setLng(-18.3636474609375),
			new GeoLocation().setLat(29.036960648558267).setLng(-15.3094482421875),
			new GeoLocation().setLat(27.362010549240278).setLng(-17.0672607421875),
			new GeoLocation().setLat(27.6543381066919).setLng(-18.3636474609375)
			));
		
	public static final Zone ZONE_COMPLEX = ZoneFactory.newZone("Complex Zone").setPath( ImmutableList.of(
			new GeoLocation().setLat(40.42988).setLng(-3.70568),  // Metro San Bernardo  ( NW )
			new GeoLocation().setLat(40.38190).setLng(-3.66829),  // Renfe Asamblea Madrid - Entrevias ( SE )
			new GeoLocation().setLat(40.43001).setLng(-3.64116),  // Metro Ascao (NE)
			new GeoLocation().setLat(40.38524).setLng(-3.71905)   // Metro Intercambiador de Plaza Elíptica (SW)
			));  
	
	public static ImmutableSet<Zone> WELL_KNOWN_ZONES= ImmutableSet.of(ZONE_MADRID_CENTRO,ZONE_ALCALA_HENARES, ZONE_COMPLEX, ZONE_PROVINCIA_STA_CRUZ);

	

}