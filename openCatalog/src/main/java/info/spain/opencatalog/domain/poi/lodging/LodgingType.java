package info.spain.opencatalog.domain.poi.lodging;

import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.AccessibilityFlag;
import info.spain.opencatalog.domain.poi.FamilyServiceFlag;
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
	private Set<BusinessServiceFlag> allowedBusinessServiceFlags;
   
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

	public LodgingType setAllowedRoomFlags(RoomFlag... roomFlags) {
		this.allowedRoomFlags = Sets.immutableEnumSet(Sets.newHashSet(roomFlags));
		return this;
	}

	public Set<RoomType> getAllowedRoomTypes() {
		return allowedRoomTypes;
	}

	public LodgingType setAllowedRoomTypes(RoomType... rType) {
		this.allowedRoomTypes = Sets.immutableEnumSet(Sets.newHashSet(rType));
		return this;
	}
	
	public Set<BusinessServiceFlag> getAllowedBusinessServiceFlags() {
		return allowedBusinessServiceFlags;
	}

	public LodgingType setAllowedBusinessServiceFlags(BusinessServiceFlag... flags) {
		this.allowedBusinessServiceFlags = Sets.immutableEnumSet(Sets.newHashSet(flags));
		return this;
	}
	
	
	@Override
	public LodgingType setAllowedFamilyServiceFlag(FamilyServiceFlag... flags) {
		return (LodgingType) super.setAllowedFamilyServiceFlag(flags);
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
