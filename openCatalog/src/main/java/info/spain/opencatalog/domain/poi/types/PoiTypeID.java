package info.spain.opencatalog.domain.poi.types;

/**
 *
 */

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/** Tipos de POI permitidos */
public enum PoiTypeID {

    // Basic poi
    POI,

    // Lodging
    HOTEL,
    CAMPING,
    APARTMENT,

    // Nature
    BEACH,
    NATURAL_SPACE,

    // Culture
    MUSEUM,
    MONUMENT,
    PARK_GARDEN;

    public static final Set<PoiTypeID> LODGING_TYPES = ImmutableSet.of(HOTEL, CAMPING, APARTMENT);

}