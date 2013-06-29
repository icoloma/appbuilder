package info.spain.opencatalog.domain.poi.lodging;

import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.AccessibilityFlag;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.PoiType;
import info.spain.opencatalog.domain.poi.PoiTypeID;
import info.spain.opencatalog.domain.poi.QualityCertificateFlag;

import java.util.Set;

import com.google.common.collect.Sets;

/**
 * Tipos de alojamiento
 * 
 */
public class LodgingType extends PoiType {
	

	private Set<RoomFlag> allowedRoomFlags;
	private Set<RoomType> allowedRoomTypes;
   
	public LodgingType(PoiTypeID id) {
		super(id);
	}
	
	@Override
    public void validate(AbstractPoi poi) {
        super.validate(poi);
        Lodging lp = (Lodging) poi;
        validateSet("room flag", allowedRoomFlags, lp.getRoomFlags());
        validateSet("room type", allowedRoomTypes, lp.getRoomTypes());
        for (RoomPrice price : lp.getRoomPrices()) {
            validateSet("room type", allowedRoomTypes, Sets.immutableEnumSet(price.getRoomType()));
        }
    }

	public Set<RoomFlag> getAllowedRoomFlags() {
		return allowedRoomFlags;
	}

	public LodgingType setAllowedRoomFlags(RoomFlag roomFlag, RoomFlag... others) {
		this.allowedRoomFlags = Sets.immutableEnumSet(roomFlag, others);
		return this;
	}

	public Set<RoomType> getAllowedRoomTypes() {
		return allowedRoomTypes;
	}

	public LodgingType setAllowedRoomTypes(RoomType rType, RoomType... others) {
		this.allowedRoomTypes = Sets.immutableEnumSet(rType, others);
		return this;
	}

	@Override
	public LodgingType setAllowedFlags(Flag... flags) {
		return (LodgingType) super.setAllowedFlags(flags);
	}

	@Override
	public LodgingType setAllowedAccessibilityFlags( AccessibilityFlag... flags) {
		return (LodgingType) super.setAllowedAccessibilityFlags(flags);
	}

	@Override
	public LodgingType setAllowedScores(Score... scores) {
		return (LodgingType) super.setAllowedScores(scores);
	}

	@Override
	public LodgingType setAllowedQualityCertificateFlags( QualityCertificateFlag... flags) {
		return (LodgingType) super.setAllowedQualityCertificateFlags(flags);
	}
	
	
	
	
    
    
}
