package info.spain.opencatalog.domain.poi.nature;

import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.AccessPrice;
import info.spain.opencatalog.domain.poi.AccessibilityFlag;
import info.spain.opencatalog.domain.poi.ContactInfo;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.PoiType;
import info.spain.opencatalog.domain.poi.QualityCertificateFlag;
import info.spain.opencatalog.domain.poi.TimeTableEntry;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.Sets;

/**
 * 
 * Espacios naturales
 * 
 */
@Document(collection="poi")
public class NaturalSpace extends AbstractPoi {
	
	/** Tipos de espacio natural: Parque natural, Parque nacional, ... */
	private Set<NaturalSpaceFlag> naturalSpaceFlags;
	
	public NaturalSpace(PoiType type) {
		super(type);
	}

	@Override
	public NaturalSpace setPrices(AccessPrice... prices) {
		return (NaturalSpace) super.setPrices(prices);
	}
  
    @Override
	public NaturalSpace setName(I18nText name) {
		return (NaturalSpace) super.setName(name);
	}

	@Override
	public NaturalSpace setDescription(I18nText description) {
		return (NaturalSpace) super.setDescription(description);
	}

	@Override
	public NaturalSpace setAddress(Address address) {
		return (NaturalSpace) super.setAddress(address);
	}

	@Override
	public NaturalSpace setLocation(GeoLocation location) {
		return (NaturalSpace) super.setLocation(location);
	}

	@Override
    public NaturalSpace setFlags(Flag... flags) {
        return (NaturalSpace) super.setFlags(flags);
    }
    
    @Override
	public NaturalSpace setQualityCertificates( QualityCertificateFlag... qualityCertificateFlags) {
		return (NaturalSpace) super.setQualityCertificates(qualityCertificateFlags);
	}

	@Override
	public NaturalSpace setAccessibilityFlags(AccessibilityFlag... disabledAccessibility) {
		return (NaturalSpace) super.setAccessibilityFlags(disabledAccessibility);
	}
	
	@Override
	public NaturalSpace setTimetable(TimeTableEntry... timetable) {
		return (NaturalSpace) super.setTimetable(timetable);
	}
	
	@Override
 	public NaturalSpace setContactInfo(ContactInfo contactInfo) {
		return (NaturalSpace) super.setContactInfo(contactInfo);
	}

	public NaturalSpace setNaturalSpaceFlags(NaturalSpaceFlag... type) {
		this.naturalSpaceFlags = Sets.newHashSet(type);
		return this;
	}
		
	@Override
	protected ToStringHelper toStringHelper() {
		return super.toStringHelper()
			.add("naturalSpaceFlags", naturalSpaceFlags);
	}

}
