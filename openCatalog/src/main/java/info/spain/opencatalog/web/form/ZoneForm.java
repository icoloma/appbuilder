package info.spain.opencatalog.web.form;

import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.Zone;

import java.util.Iterator;

public class ZoneForm extends Zone {

	/** 
	 * Obtenemos el Zone del ZoneForm y le
	 * añadimos los Tags correspondientes
	 * @return
	 */
	public Zone getZone(){
		Zone result = new Zone(this);
		return result;
	}
	
	
	public ZoneForm(){
		super();
	}
	
	public ZoneForm(Zone zone){
		super(zone);
	}
	
	public  String getPathArray(){
		StringBuffer result = new StringBuffer("[");
		for (Iterator<GeoLocation> iterator = getPath().iterator(); iterator.hasNext();) {
			GeoLocation geoLocation = iterator.next();
			result.append("[").append(geoLocation.getLat()).append(",").append(geoLocation.getLng()).append("]");
			if (iterator.hasNext()){
				result.append(",");
			}
		}
		result.append("]");
		return result.toString();
	}

}
