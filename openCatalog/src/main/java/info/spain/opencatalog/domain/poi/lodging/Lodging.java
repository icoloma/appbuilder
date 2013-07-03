package info.spain.opencatalog.domain.poi.lodging;

import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.AccessPrice;
import info.spain.opencatalog.domain.poi.ContactInfo;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.TimeTableEntry;
import info.spain.opencatalog.domain.poi.types.BasicPoiType;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.Objects.ToStringHelper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

/**
 * 
 * Define los campos comunes que tendrán los Poi de tipo Lodging
 * 
 */
@Document(collection="poi")
public class Lodging extends AbstractPoi {
	
    /** tipos de alojamiento: habitación doble, individual, etc */
    private Set<RoomType> roomTypes;

    /** precios del alojamiento: hab-doble en temporada alta con pensión completa */
    private Set<RoomPrice> roomPrices;

    public Lodging(BasicPoiType type) {
        super(type);
        Preconditions.checkArgument(PoiTypeID.LODGING_TYPES.contains(type.getId()));
    }

    @Override
	public Lodging setPrices(AccessPrice...prices) {
		return (Lodging) super.setPrices(prices);
	}
	
    @Override
	public Lodging setTimetable(TimeTableEntry... timetable) {
		return (Lodging) super.setTimetable(timetable);
	}

    @Override
	public Lodging setName(I18nText name) {
		return (Lodging) super.setName(name);
	}

	@Override
	public Lodging setDescription(I18nText description) {
		return (Lodging) super.setDescription(description);
	}

	@Override
	public Lodging setAddress(Address address) {
		return (Lodging) super.setAddress(address);
	}

	@Override
	public Lodging setLocation(GeoLocation location) {
		return (Lodging) super.setLocation(location);
	}

	@Override
    public Lodging setFlags(Flag... flags) {
        return (Lodging) super.setFlags(flags);
    }
	
    @Override
	public Lodging setScore(Score score) {
		return (Lodging) super.setScore(score);
	}

	@Override
 	public Lodging setContactInfo(ContactInfo contactInfo) {
		return (Lodging) super.setContactInfo(contactInfo);
	}
 	
	public Set<RoomPrice> getRoomPrices() {
    	return roomPrices;
    }
    
    public Lodging setRoomPrices(RoomPrice... roomPrices) {
    	this.roomPrices = Sets.newHashSet(roomPrices);
    	return this;
    }

    public Lodging setRoomTypes(RoomType... roomTypes) {
    	this.roomTypes = Sets.newHashSet(roomTypes);
    	return this;
    }
    
   public Set<RoomType> getRoomTypes() {
		return roomTypes;
	}

	@Override
	public Lodging validate() {
		return (Lodging) super.validate();
	}

		@Override
	protected ToStringHelper toStringHelper() {
		return super.toStringHelper()
			.add("roomTypes", roomTypes)
			.add("roomPrices", roomPrices)
		;
	}

		
	
    
}
