package info.spain.opencatalog.domain.poi.types;

import java.util.Set;

import com.google.common.collect.Sets;

import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.lodging.Lodging;

public class PoiFactory {
	
	

    /** ids que deben de ser Lodging */
    public static final Set<PoiTypeID> LODGING_TYPES = lodgingTypes();

	/**
	 * Crea una nueva isntancia de un poi en función del PoiTypeID
	 * 
	 * @param id - PoiTypeID
	 */
	public static BasicPoi newInstance(PoiTypeID id ) {
		
		if (LODGING_TYPES.contains(id)) {
			return new Lodging(id);
		} else {
			return new BasicPoi(id);
		}

	}
	
	private static Set<PoiTypeID> lodgingTypes(){
		Set<PoiTypeID> result = Sets.newHashSet();
		result.add(PoiTypeID.APARTMENT);
		result.add(PoiTypeID.HOTEL);
		result.add(PoiTypeID.CAMPING);
		return result;
		
	}
}
