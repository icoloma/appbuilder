package info.spain.opencatalog.domain.poi.types;

import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.lodging.Lodging;
import info.spain.opencatalog.domain.poi.lodging.RoomPrice;
import info.spain.opencatalog.domain.poi.lodging.RoomType;
import info.spain.opencatalog.domain.poi.lodging.Score;

import java.util.Set;

import com.google.common.collect.Sets;

/**
 * Tipos de alojamiento
 * 
 */
public class LodgingType extends BasicPoiType {
	
	private Set<RoomType> allowedRoomTypes;
	 
	public LodgingType(PoiTypeID id) {
		super(id);
	}
	
	@Override
    public void validate(AbstractPoi poi) {
        super.validate(poi);
        Lodging lp = (Lodging) poi;
        validateSet("room type", allowedRoomTypes, lp.getRoomTypes());
        for (RoomPrice price : lp.getRoomPrices()) {
            validateSet("room type", allowedRoomTypes, Sets.immutableEnumSet(price.getRoomType()));
        }
    }

	public Set<RoomType> getAllowedRoomTypes() {
		return allowedRoomTypes;
	}

	public LodgingType setAllowedRoomTypes(RoomType... rType) {
		this.allowedRoomTypes = Sets.immutableEnumSet(Sets.newHashSet(rType));
		return this;
	}
	
	@Override
	public LodgingType setAllowedFlags(Flag... flags) {
		return (LodgingType) super.setAllowedFlags(flags);
	}


	@Override
	public LodgingType setAllowedScores(Score... scores) {
		return (LodgingType) super.setAllowedScores(scores);
	}
    
}
