package info.spain.opencatalog.domain.poi.types;

/**
 *
 */

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

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
    

    public static final Set<PoiTypeID> BASIC_TYPES = ImmutableSet.of(
    		BASIC, 
    		BEACH, 
    		NATURAL_SPACE);
    public static final Set<PoiTypeID> LODGING_TYPES = ImmutableSet.of(
    		HOTEL, 
    		CAMPING, 
    		APARTMENT);
    public static final Set<PoiTypeID> CULTURE_TYPES = Sets.immutableEnumSet(
    		PoiTypeID.MUSEUM, 
    		PoiTypeID.MONUMENT, 
    		PoiTypeID.PARK_GARDEN);
    public static final Set<PoiTypeID> BUSINESS_TYPES  = Sets.immutableEnumSet(
    		PoiTypeID.ECO_TOURISM, 
    		PoiTypeID.GOLF, 
    		PoiTypeID.NAUTICAL_STATION, 
    		PoiTypeID.SKI_STATION);
    
   
  
   
}

