package info.spain.opencatalog.domain.poi.types;

import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.lodging.Lodging;

public class PoiFactory {

	/**
	 * Crea una nueva isntancia de un poi en función del PoiTypeID
	 * 
	 * @param id - PoiTypeID
	 */
	public static AbstractPoi newInstance(PoiTypeID id ) {
		
		// Basic
		if (PoiTypeRepository.BASIC_TYPES.contains(id)) {
			return new BasicPoi(PoiTypeRepository.getType(id));
		}
		// Lodging
		if (PoiTypeRepository.LODGING_TYPES.contains(id)) {
			return new Lodging(PoiTypeRepository.getType(id));
		}
			
		throw new IllegalArgumentException("id Poi Type " + id + " + is not assigned to any class" );
	

	}
}
