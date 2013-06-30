package info.spain.opencatalog.domain.poi.beach;

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

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.Objects.ToStringHelper;

/**
 * Playas 
 */
@Document(collection="poi")
public class Beach extends AbstractPoi {
	
	private Double longitude;
	private Double width;
	private BeachComposition composition;
	private SandType sandType;
	private BathCondition bathCondition;
	private Boolean	anchorZone = Boolean.FALSE;	 // zona de fondeo
	private Boolean	promenade = Boolean.FALSE;	 // paseo marítimo

	
	public Beach(BasicPoiType type) {
		super(type);
	}
    
  
    @Override
	public Beach setName(I18nText name) {
		return (Beach) super.setName(name);
	}

	@Override
	public Beach setDescription(I18nText description) {
		return (Beach) super.setDescription(description);
	}

	@Override
	public Beach setAddress(Address address) {
		return (Beach) super.setAddress(address);
	}

	@Override
	public Beach setLocation(GeoLocation location) {
		return (Beach) super.setLocation(location);
	}

	@Override
    public Beach setFlags(Flag... flags) {
        return (Beach) super.setFlags(flags);
    }
    
    @Override
	public Beach setQualityCertificates( QualityCertificateFlag... flags) {
		return (Beach) super.setQualityCertificates(flags);
	}

   	@Override
	public Beach setAccessibilityFlags(AccessibilityFlag... flags) {
		return (Beach) super.setAccessibilityFlags(flags);
	}
	@Override
	public Beach setTimetable(TimeTableEntry... timetable) {
		return (Beach) super.setTimetable(timetable);
	}
	
	@Override
 	public Beach setContactInfo(ContactInfo contactInfo) {
		return (Beach) super.setContactInfo(contactInfo);
	}

	@Override
	public Beach setPrices(AccessPrice... prices) {
		return (Beach) super.setPrices(prices);
	}
	
 	@Override
	public Beach setFamilyServiceFlags( FamilyServiceFlag... flags) {
		return (Beach) super.setFamilyServiceFlags(flags);
	}

	
	public Double getLongitude() {
		return longitude;
	}

	public Beach setLongitude(Double longitude) {
		this.longitude = longitude;
		return this;
	}

	public Double getWidth() {
		return width;
	}

	public Beach setWidth(Double width) {
		this.width = width;
		return this;
	}

	public BeachComposition getComposition() {
		return composition;
	}

	public Beach setComposition(BeachComposition composition) {
		this.composition = composition;
		return this;
	}

	public SandType getSandType() {
		return sandType;
	}

	public Beach setSandType(SandType sandType) {
		this.sandType = sandType;
		return this;
	}

	public BathCondition getBathCondition() {
		return bathCondition;
	}

	public Beach setBathCondition(BathCondition bathCondition) {
		this.bathCondition = bathCondition;
		return this;
	}

	public Boolean getAnchorZone() {
		return anchorZone;
	}

	public Beach setAnchorZone(Boolean anchorZone) {
		this.anchorZone = anchorZone;
		return this;
	}

	public Boolean getPromenade() {
		return promenade;
	}

	public Beach setPromenade(Boolean promenade) {
		this.promenade = promenade;
		return this;
	}

	
		@Override
	protected ToStringHelper toStringHelper() {
		return super.toStringHelper()
			.add("long", longitude)
			.add("width", width)
			.add("composition", composition)
			.add("sandType", sandType)
			.add("bathCondition", bathCondition)
			.add("anchorZone", anchorZone)
			.add("promenade", promenade)
			;
	}


}
