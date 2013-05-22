package info.spain.opencatalog.domain;

import java.io.Serializable;

import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

/**
 * Geospatial coordinates
 * 
 * @author ehdez
 */
public class GeoLocation implements Serializable{
	
	private static final long serialVersionUID = 435272423089458051L;
	private static final int LNG = 0; // index in array
	private static final int LAT = 1; // index in array 
	
	private static final double[] DEFAULT_COORDS = {-3.703794, 40.416957}; // Madrid, Puerta del Sol
	
	@GeoSpatialIndexed
	private double[] loc = {DEFAULT_COORDS[0],DEFAULT_COORDS[1]};
	
	public Double getLng() {
		return loc[LNG];
	}

	public GeoLocation setLng(Double lng) {
		loc[LNG]= lng;
		return this;
	}

	public Double getLat() {
		return loc[LAT];
	}

	public GeoLocation setLat(Double lat) {
		loc[LAT]= lat;
		return this;
	}

	public String toString(){
		return "[ lng: " + getLng() + ", lat: " + getLat()+ "]";
	}

}
