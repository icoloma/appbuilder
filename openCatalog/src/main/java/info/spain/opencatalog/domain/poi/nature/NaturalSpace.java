package info.spain.opencatalog.domain.poi.nature;

import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.AccessPrice;
import info.spain.opencatalog.domain.poi.AccessibilityFlag;
import info.spain.opencatalog.domain.poi.ContactInfo;
import info.spain.opencatalog.domain.poi.FamilyServiceFlag;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.QualityCertificateFlag;
import info.spain.opencatalog.domain.poi.TimeTableEntry;
import info.spain.opencatalog.domain.poi.types.BasicPoiType;

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
	
	public NaturalSpace(BasicPoiType type) {
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
	public NaturalSpace setQualityCertificates( QualityCertificateFlag... flags) {
		return (NaturalSpace) super.setQualityCertificates(flags);
	}

	@Override
	public NaturalSpace setAccessibilityFlags(AccessibilityFlag... flags) {
		return (NaturalSpace) super.setAccessibilityFlags(flags);
	}
	
 	@Override
	public NaturalSpace setFamilyServiceFlags( FamilyServiceFlag... flags) {
		return (NaturalSpace) super.setFamilyServiceFlags(flags);
	}

	
	public NaturalSpace setNaturalSpaceFlags(NaturalSpaceFlag... flags) {
		this.naturalSpaceFlags = Sets.newHashSet(flags);
		return this;
	}

	@Override
	public NaturalSpace setTimetable(TimeTableEntry... timetable) {
		return (NaturalSpace) super.setTimetable(timetable);
	}
		
	@Override
	public NaturalSpace setContactInfo(ContactInfo contactInfo) {
		return (NaturalSpace) super.setContactInfo(contactInfo);
	}

	@Override
	protected ToStringHelper toStringHelper() {
		return super.toStringHelper()
			.add("naturalSpaceFlags", naturalSpaceFlags);
	}

}
