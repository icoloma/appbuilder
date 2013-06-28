package info.spain.opencatalog.domain.poi.types.lodging;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.AccessibilityFlag;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.PoiTypeRepository;
import info.spain.opencatalog.domain.poi.QualityCertificateFlag;
import info.spain.opencatalog.domain.poi.types.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * 
 * Define los campos comunes que tendrán los Poi de tipo Lodging
 * 
 */
@Document(collection="poi")
public class LodgingPoi extends BasicPoi {
	
    /** tipos de alojamiento: habitación doble, individual, etc */
    private Set<RoomType> roomTypes;

    /** servicios que se ofrece en el tipo de alojamiento: Jacuzzi, Caja fuerte, Wifi */
    private Set<RoomFlag> roomFlags;


    /** precios del alojamiento: hab-doble en temporada alta con pensión completa */
    private Set<RoomPrice> roomPrices;

    public LodgingPoi(PoiType type) {
        super(type);
        Preconditions.checkArgument(PoiTypeID.LODGING_TYPES.contains(type.getId()));
    }

	@Override
	public LodgingPoi setTimetable(Set<TimeTableEntry> timetable) {
		return (LodgingPoi) super.setTimetable(timetable);
	}

	@Override
	public LodgingPoi setTimetable(TimeTableEntry... timetable) {
		return (LodgingPoi) super.setTimetable(timetable);
	}

  

    @Override
	public LodgingPoi setName(I18nText name) {
		return (LodgingPoi) super.setName(name);
	}

	@Override
	public LodgingPoi setDescription(I18nText description) {
		return (LodgingPoi) super.setDescription(description);
	}

	@Override
	public LodgingPoi setAddress(Address address) {
		return (LodgingPoi) super.setAddress(address);
	}

	@Override
	public LodgingPoi setLocation(GeoLocation location) {
		return (LodgingPoi) super.setLocation(location);
	}

	@Override
	public LodgingPoi setPoiType(PoiTypeID poiType) {
		return (LodgingPoi) super.setPoiType(poiType);
	}

	@Override
    public LodgingPoi setFlags(Flag... flags) {
        return (LodgingPoi) super.setFlags(flags);
    }
    
    @Override
	public LodgingPoi setQualityCertificates( QualityCertificateFlag... qualityCertificateFlags) {
		return (LodgingPoi) super.setQualityCertificates(qualityCertificateFlags);
	}
    
    @Override
 	public LodgingPoi setAccessibilityFlags(Set<AccessibilityFlag> disabledAccessibility) {
 		return (LodgingPoi) super.setAccessibilityFlags(disabledAccessibility);
 	}
 	@Override
 	public LodgingPoi setDisabledAccessibility(AccessibilityFlag... disabledAccessibility) {
 		return (LodgingPoi) super.setDisabledAccessibility(disabledAccessibility);
 	}
 
 	@Override
 	public LodgingPoi setContactInfo(ContactInfo contactInfo) {
		return (LodgingPoi) super.setContactInfo(contactInfo);
	}

	public Set<RoomPrice> getRoomPrices() {
    	return roomPrices;
    }
    
    public LodgingPoi setRoomPrices(Set<RoomPrice> roomPrices) {
    	this.roomPrices = roomPrices;
    	return this;
    }
    
    public LodgingPoi setLodgingPrices(RoomPrice... roomPrices) {
    	return setRoomPrices(Sets.newHashSet(roomPrices));
    }

    public LodgingPoi setRoomTypes(RoomType... roomTypes) {
    	return setLodgingTypes(Sets.newHashSet(roomTypes));
    }
    
    public LodgingPoi setLodgingTypes(Set<RoomType> roomTypes) {
    	this.roomTypes = roomTypes;
    	return this;
    }

    public LodgingPoi setScores(Score... scores){
    	return setScores(Sets.newHashSet(scores));
    }
    
    public LodgingPoi setScores(Set<Score> scores){
    	this.scores = scores;
    	return this;
    }

    public LodgingPoi setLodgingFlags(Set<LodgingFlag> lodgingFlags) {
		this.lodgingFlags = lodgingFlags;
		return this;
	}
    public LodgingPoi setLodgingFlags(LodgingFlag... lodgingFlags) {
		return setLodgingFlags(Sets.newHashSet(lodgingFlags));
	}

	public LodgingPoi setRoomFlags(Set<RoomFlag> roomFlags) {
		this.roomFlags = roomFlags;
		return this;
	
	}
	public LodgingPoi setLodgingTypeFlags(RoomFlag... roomFlags) {
		return 	setRoomFlags(Sets.newHashSet(roomFlags));

	}

	public Set<LodgingFlag> getLodgingFlags() {
		return lodgingFlags;
	}

	public Set<RoomType> getRoomTypes() {
		return roomTypes;
	}

	public Set<RoomFlag> getRoomFlags() {
		return roomFlags;
	}

	public Set<Score> getScores() {
		return scores;
	}
	

	@Override
	public String toString() {
		return Objects.toStringHelper(getClass())
			.add("poiType", getPoiType())
			.add("id", getId())
			.add("name", getName())
			.add("description", getDescription())
			.add("location", getLocation())
			.add("contactInfo", getContactInfo())
			.add("timeTable", getTimetable())
			.add("flags", getFlags())
			.add("createdDate", getCreatedDate())
			.add("lastModifiedDate", getLastModifiedDate())
			.add("lodgingFlags",lodgingFlags)
			.add("lodgingTypes", roomTypes)
			.add("lodgingTypeFlags", roomFlags)
			.add("scores", scores)
			.add("lodgingPrices", roomPrices)
			.toString();
	}
    
}
