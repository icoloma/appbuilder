package info.spain.opencatalog.domain.poi.types;

import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.beach.Beach;
import info.spain.opencatalog.domain.poi.business.Business;
import info.spain.opencatalog.domain.poi.culture.Culture;
import info.spain.opencatalog.domain.poi.lodging.Lodging;
import info.spain.opencatalog.domain.poi.nature.NaturalSpace;

public class PoiFactory {

	/**
	 * Crea una nueva isntancia de un poi en función del PoiTypeID
	 * 
	 * @param id - PoiTypeID
	 */
	public static AbstractPoi newInstance(PoiTypeID id ) {
		
		// Lodging
		if (PoiTypeID.HOTEL.equals(id) || PoiTypeID.CAMPING.equals(id) || PoiTypeID.APARTMENT.equals(id)) {
			return new Lodging(PoiTypeRepository.getType(id));
		}
		// Culture
		if (PoiTypeID.MUSEUM.equals(id) || PoiTypeID.MONUMENT.equals(id) || PoiTypeID.PARK_GARDEN.equals(id)) {
			return new Culture(PoiTypeRepository.getType(id));
		}
		// Beach
		if (PoiTypeID.BEACH.equals(id)) {
			return new Beach(PoiTypeRepository.getType(id));
		}
		// Natural space
		if (PoiTypeID.NATURAL_SPACE.equals(id)) {
			return new NaturalSpace(PoiTypeRepository.getType(id));
		}
		// Business
		if (PoiTypeID.ECO_TOURISM.equals(id) || PoiTypeID.GOLF.equals(id) || PoiTypeID.NAUTICAL_STATION.equals(id) ) {
			return new Business(PoiTypeRepository.getType(id));
		}
		// Basic
		if (PoiTypeID.BASIC.equals(id)) {
			return new BasicPoi(PoiTypeRepository.getType(id));
		}
		throw new IllegalArgumentException("id Poi Type " + id + " + is not assigned to any class" );
	

	}
}
