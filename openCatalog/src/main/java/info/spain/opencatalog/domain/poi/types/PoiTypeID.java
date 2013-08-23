package info.spain.opencatalog.domain.poi.types;

import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.lodging.Lodging;

import java.util.Set;

import com.google.common.collect.Sets;

/**
 *
 */


/** Tipos de POI permitidos */
public enum PoiTypeID {
	
	// Basic
	BASIC,
	BEACH,
    NATURAL_SPACE,
	
	// Lodging
    HOTEL,
    CAMPING,
    APARTMENT,

    // Culture
    MUSEUM,
    MONUMENT,
    PARK_GARDEN,

    // Business
    ECO_TOURISM,
    GOLF,
    NAUTICAL_STATION,
	SKI_STATION;


  public static  Set<PoiTypeID> LODGING_TYPES = Sets.immutableEnumSet(
		HOTEL, 
		APARTMENT, 
		CAMPING);


   public static  Set<PoiTypeID> CULTURE_TYPES = Sets.immutableEnumSet(
		MUSEUM, 
		MONUMENT, 
		PARK_GARDEN);

    /** Agrupación lógica de Business */
    public static  Set<PoiTypeID> BUSINESS_TYPES  = Sets.immutableEnumSet(
		ECO_TOURISM, 
		GOLF, 
		NAUTICAL_STATION, 
		SKI_STATION);	
    
    public Class<? extends BasicPoi> getPoiClass(){
    	return LODGING_TYPES.contains(this) ? Lodging.class : BasicPoi.class; 
 	   
    }
   
}

