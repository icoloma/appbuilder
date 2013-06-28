package info.spain.opencatalog.domain.poi.types.lodging;

import com.google.common.collect.Sets;
import info.spain.opencatalog.domain.poi.types.BasicPoi;
import info.spain.opencatalog.domain.poi.types.PoiType;

/**
 *
 */
public class LodgingPoiType extends PoiType {

    @Override
    public void validate(BasicPoi poi) {
        super.validate(poi);
        LodgingPoi lp = (LodgingPoi) poi;
        validateSet("room flag", lp.getRoomFlags(), allowedRoomFlags);
        validateSet("room type", lp.getRoomTypes(), allowedRoomTypes);
        for (RoomPrice price : lp.getRoomPrices()) {
            validateSet(allowedRoomTypes, Sets.immutableEnumSet(price.getRoomType()));
        }
    }
}
