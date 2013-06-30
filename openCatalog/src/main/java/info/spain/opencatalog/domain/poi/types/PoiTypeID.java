package info.spain.opencatalog.domain.poi.types;

/**
 *
 */

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/** Tipos de POI permitidos */
public enum PoiTypeID {
	
	BASIC,

	// Beach
	BEACH,
	
	// Lodging
    HOTEL,
    CAMPING,
    APARTMENT,

    // Culture
    MUSEUM,
    MONUMENT,
    PARK_GARDEN,

    // Nature
    NATURAL_SPACE,

    // BUSINESS
    ECO_TOURISM,
    GOLF,
    NAUTICAL_STATION;
    


    public static final Set<PoiTypeID> LODGING_TYPES = ImmutableSet.of(HOTEL, CAMPING, APARTMENT);
    public static final Set<PoiTypeID> CULTURE_TYPES = Sets.immutableEnumSet(PoiTypeID.MUSEUM, PoiTypeID.MONUMENT, PoiTypeID.PARK_GARDEN);
    public static final Set<PoiTypeID> NATURE_TYPES  = Sets.immutableEnumSet(PoiTypeID.NATURAL_SPACE, PoiTypeID.ECO_TOURISM);
    
   
  
   
}

