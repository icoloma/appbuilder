package info.spain.opencatalog.domain.poi.types.nature;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.AccessibilityFlag;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.QualityCertificateFlag;
import info.spain.opencatalog.domain.poi.types.BasicPoi;
import info.spain.opencatalog.domain.poi.types.ContactInfo;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;
import info.spain.opencatalog.domain.poi.types.TimeTableEntry;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * 
 * Espacios naturales
 * 
 */
@Document(collection="poi")
public class NaturalSpacePoiType extends BasicPoi {
	
	/** Tipos de espacio natural: Parque natural, Parque nacional, ... */
	private Set<NaturalSpaceFlag> naturalSpaceFlags;
	
	public NaturalSpacePoiType() {
		super();
		setPoiType(PoiTypeID.NATURAL_SPACE);
	}
    
  
    @Override
	public NaturalSpacePoiType setName(I18nText name) {
		return (NaturalSpacePoiType) super.setName(name);
	}

	public NaturalSpacePoiType(BasicPoi other) {
		super(other);
	}

	@Override
	public NaturalSpacePoiType setDescription(I18nText description) {
		return (NaturalSpacePoiType) super.setDescription(description);
	}

	@Override
	public NaturalSpacePoiType setAddress(Address address) {
		return (NaturalSpacePoiType) super.setAddress(address);
	}

	@Override
	public NaturalSpacePoiType setLocation(GeoLocation location) {
		return (NaturalSpacePoiType) super.setLocation(location);
	}

	@Override
	public NaturalSpacePoiType setPoiType(PoiTypeID poiType) {
		return (NaturalSpacePoiType) super.setPoiType(poiType);
	}

	@Override
    public NaturalSpacePoiType setFlags(Flag... flags) {
        return (NaturalSpacePoiType) super.setFlags(flags);
    }
    
    @Override
	public NaturalSpacePoiType setQualityCertificates( QualityCertificateFlag... qualityCertificateFlags) {
		return (NaturalSpacePoiType) super.setQualityCertificates(qualityCertificateFlags);
	}

    @Override
	public NaturalSpacePoiType setAccessibilityFlags(Set<AccessibilityFlag> disabledAccessibility) {
		return (NaturalSpacePoiType) super.setAccessibilityFlags(disabledAccessibility);
	}
	@Override
	public NaturalSpacePoiType setDisabledAccessibility(AccessibilityFlag... disabledAccessibility) {
		return (NaturalSpacePoiType) super.setDisabledAccessibility(disabledAccessibility);
	}
	
	@Override
	public NaturalSpacePoiType setTimetable(Set<TimeTableEntry> timetable) {
		return (NaturalSpacePoiType) super.setTimetable(timetable);
	}

	@Override
	public NaturalSpacePoiType setTimetable(TimeTableEntry... timetable) {
		return (NaturalSpacePoiType) super.setTimetable(timetable);
	}
	
	@Override
 	public NaturalSpacePoiType setContactInfo(ContactInfo contactInfo) {
		return (NaturalSpacePoiType) super.setContactInfo(contactInfo);
	}

	public Set<NaturalSpaceFlag> getType() {
		return naturalSpaceFlags;
	}

	public NaturalSpacePoiType setNaturalSpaceFlags(Set<NaturalSpaceFlag> naturalSpaceFlags) {
		this.naturalSpaceFlags = naturalSpaceFlags;
		return this;
	}
	
	public NaturalSpacePoiType setNaturalSpaceFlags(NaturalSpaceFlag... type) {
		return setNaturalSpaceFlags(Sets.newHashSet(type));
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
			.add("flags", getFlags())
			.add("createdDate", getCreatedDate())
			.add("lastModifiedDate", getLastModifiedDate())
			.add("naturalSpaceFlags", naturalSpaceFlags)
			.toString();
	}
		
    
}
