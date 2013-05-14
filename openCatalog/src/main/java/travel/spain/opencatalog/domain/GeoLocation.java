package travel.spain.opencatalog.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * Geospatial coordinates
 * 
 * @author ehdez
 */
public class GeoLocation implements Serializable{
	
	private static final long serialVersionUID = -4501256496479941090L;

	@NotNull
	private Double lng;
	
	@NotNull
	private Double lat;
	
	public Double getLng() {
		return lng;
	}

	public GeoLocation setLng(Double lng) {
		this.lng = lng;
		return this;
	}

	public Double getLat() {
		return lat;
	}

	public GeoLocation setLat(Double lat) {
		this.lat = lat;
		return this;
	}

	public String toString(){
		return "[ lng: " + lng + ", lat: " + lat + "]";
	}

}
