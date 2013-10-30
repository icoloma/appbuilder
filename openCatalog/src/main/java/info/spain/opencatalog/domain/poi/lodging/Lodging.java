package info.spain.opencatalog.domain.poi.lodging;

import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.ContactInfo;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.Price;
import info.spain.opencatalog.domain.poi.Score;
import info.spain.opencatalog.domain.poi.TimeTableEntry;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.Sets;

/**
 * 
 * Define los campos comunes que tendrán los Poi de tipo Lodging
 * 
 */
@Document(collection="poi")
public class Lodging extends BasicPoi {
	
    /** tipos de alojamiento: habitación doble, individual, etc */
    private Set<RoomType> roomTypes;

    public Lodging() {
    	super(null); 
    }
    public Lodging(PoiTypeID type) {
        super(type);
    }

    @Override
	public Lodging setPrices(Price...prices) {
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
	
	@Override
	public Lodging setLanguages(String... languages) {
		return (Lodging) super.setLanguages(languages);
	}
	
@Override
	public Lodging setPublished(boolean published) {
		return (Lodging) super.setPublished(published);
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
	;
	}

		
	
    
}
