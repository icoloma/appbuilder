package info.spain.opencatalog.domain.poi.lodging.types;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Tipos de POI
 */
public class LodgingPoiType extends AbstractPoiType {

    /** tipos de alojamiento: habitación doble, individual, etc */
    private Set<String> lodgingTypes;

    private Set<String> roomFlags;

    public LodgingPoiType setLodgingTypes(String... lodgingTypes) {
        return setLodgingTypes(Arrays.asList(lodgingTypes));
    }

    public LodgingPoiType setLodgingTypes(Set<String> lodgingTypes) {
        this.lodgingTypes = lodgingTypes;
        return this;
    }

    public LodgingPoiType setFlags(String... flags) {
        return (LodgingPoiType) super.setFlags(flags);
    }


}
