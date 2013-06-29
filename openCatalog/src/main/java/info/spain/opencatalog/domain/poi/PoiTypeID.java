package info.spain.opencatalog.domain.poi;

/**
 *
 */

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.Set;

/** Tipos de POI permitidos */
public enum PoiTypeID {
	
	BASIC,
	
	// Lodging
    HOTEL,
    CAMPING,
    APARTMENT,

    // Culture
    MUSEUM,
    MONUMENT,
    PARK_GARDEN,

    // Nature
    BEACH,
    NATURAL_SPACE
    
    ;


    public static final Set<PoiTypeID> LODGING_TYPES = ImmutableSet.of(HOTEL, CAMPING, APARTMENT);
    public static final Set<PoiTypeID> CULTURE_TYPES = Sets.immutableEnumSet(PoiTypeID.MUSEUM, PoiTypeID.MONUMENT, PoiTypeID.PARK_GARDEN);
   

}