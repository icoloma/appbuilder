package info.spain.opencatalog.domain.poi.types;

import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.business.Business;
import info.spain.opencatalog.domain.poi.culture.Culture;
import info.spain.opencatalog.domain.poi.lodging.Lodging;

public class PoiFactory {

	/**
	 * Crea una nueva isntancia de un poi en función del PoiTypeID
	 * 
	 * @param id - PoiTypeID
	 */
	public static AbstractPoi newInstance(PoiTypeID id ) {
		
		// Basic
		if (PoiTypeID.BASIC_TYPES.contains(id)) {
			return new BasicPoi(PoiTypeRepository.getType(id));
		}
		// Lodging
		if (PoiTypeID.LODGING_TYPES.contains(id)) {
			return new Lodging(PoiTypeRepository.getType(id));
		}
		// Culture
		if (PoiTypeID.CULTURE_TYPES.contains(id)) {
			return new Culture(PoiTypeRepository.getType(id));
		}
		// Business
		if (PoiTypeID.BUSINESS_TYPES.contains(id)) {
			return new Business(PoiTypeRepository.getType(id));
		}
		throw new IllegalArgumentException("id Poi Type " + id + " + is not assigned to any class" );
	

	}
}
