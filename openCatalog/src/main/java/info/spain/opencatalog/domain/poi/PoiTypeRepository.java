package info.spain.opencatalog.domain.poi;

import com.google.common.collect.Maps;
import info.spain.opencatalog.domain.poi.lodging.types.AbstractPoiType;
import info.spain.opencatalog.domain.poi.lodging.types.LodgingPoiType;

import java.util.Map;

/**
 *
 */
public class PoiTypeRepository {

    private static Map<String, AbstractPoiType> types;

    static {
        types = Maps.newHashMap();
        types.put("hotel", new LodgingPoiType()
            .setLodgingTypes("suite", "hab4", "hab3", "hab2", "hab1")
            .setFlags("leisure", "pets", "pets_size", "credit_card", "no_childs", "air_conditioning", "bike_rent", "car_rent", "free_facilities", "free_facilities_req", "satellite", "lift", "roof_terrace", "bar", "bbq", "library", "bussiness_center", "exchange", "casino", "fireplace", "cloackroom")
            .setScore("star_1", "star_2", "star_3")
        );
    }

}
