package info.spain.opencatalog.domain.poi.types.beach;

import com.google.common.base.Objects;
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
 * Playas 
 */
@Document(collection="poi")
public class BeachPoiType extends BasicPoi {
	
	private Double large;
	private Double width;
	private BeachComposition composition;
	private SandType sandType;
	private BathCondition bathCondition;
	private Boolean	anchorZone = Boolean.FALSE;	 // zona de fondeo
	private Boolean	promenade = Boolean.FALSE;	 // paseo marítimo


	
//    @Override
//    public void validateTypeAllowedValues(){
//    	super.validateTypeAllowedValues();
//    }
	
	
	public BeachPoiType() {
		super();
		setPoiType(PoiTypeID.BEACH);
	}
    
  
    @Override
	public BeachPoiType setName(I18nText name) {
		return (BeachPoiType) super.setName(name);
	}


	public BeachPoiType(BasicPoi other) {
		super(other);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BeachPoiType setDescription(I18nText description) {
		return (BeachPoiType) super.setDescription(description);
	}

	@Override
	public BeachPoiType setAddress(Address address) {
		return (BeachPoiType) super.setAddress(address);
	}

	@Override
	public BeachPoiType setLocation(GeoLocation location) {
		return (BeachPoiType) super.setLocation(location);
	}

	@Override
	public BeachPoiType setPoiType(PoiTypeID poiType) {
		return (BeachPoiType) super.setPoiType(poiType);
	}

	@Override
    public BeachPoiType setFlags(Flag... flags) {
        return (BeachPoiType) super.setFlags(flags);
    }
    
    @Override
	public BeachPoiType setQualityCertificates( QualityCertificateFlag... qualityCertificateFlags) {
		return (BeachPoiType) super.setQualityCertificates(qualityCertificateFlags);
	}

    @Override
	public BeachPoiType setAccessibilityFlags(Set<AccessibilityFlag> disabledAccessibility) {
		return (BeachPoiType) super.setAccessibilityFlags(disabledAccessibility);
	}
	@Override
	public BeachPoiType setDisabledAccessibility(AccessibilityFlag... disabledAccessibility) {
		return (BeachPoiType) super.setDisabledAccessibility(disabledAccessibility);
	}
	@Override
	public BeachPoiType setTimetable(TimeTableEntry... timetable) {
		return (BeachPoiType) super.setTimetable(timetable);
	}
	
	@Override
 	public BeachPoiType setContactInfo(ContactInfo contactInfo) {
		return (BeachPoiType) super.setContactInfo(contactInfo);
	}

	
	public Double getLarge() {
		return large;
	}

	public BeachPoiType setLarge(Double large) {
		this.large = large;
		return this;
	}

	public Double getWidth() {
		return width;
	}

	public BeachPoiType setWidth(Double width) {
		this.width = width;
		return this;
	}

	public BeachComposition getComposition() {
		return composition;
	}

	public BeachPoiType setComposition(BeachComposition composition) {
		this.composition = composition;
		return this;
	}

	public SandType getSandType() {
		return sandType;
	}

	public BeachPoiType setSandType(SandType sandType) {
		this.sandType = sandType;
		return this;
	}

	public BathCondition getBathCondition() {
		return bathCondition;
	}

	public BeachPoiType setBathCondition(BathCondition bathCondition) {
		this.bathCondition = bathCondition;
		return this;
	}

	public Boolean getAnchorZone() {
		return anchorZone;
	}

	public BeachPoiType setAnchorZone(Boolean anchorZone) {
		this.anchorZone = anchorZone;
		return this;
	}

	public Boolean getPromenade() {
		return promenade;
	}

	public BeachPoiType setPromenade(Boolean promenade) {
		this.promenade = promenade;
		return this;
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
			.add("large",large)
			.add("width",width)
			.add("composition",composition)
			.add("sandType",sandType)
			.add("bathCondition",bathCondition)
			.add("anchorZone",anchorZone)
			.add("promenade",promenade)
			.toString();
	}
		
    
}
