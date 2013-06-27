package info.spain.opencatalog.domain.poi.types.beach;

import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.DisabledAccessibility;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.PoiTypeRepository.PoiType;
import info.spain.opencatalog.domain.poi.QualityCertificate;
import info.spain.opencatalog.domain.poi.types.AbstractPoiType;
import info.spain.opencatalog.domain.poi.types.ContactInfo;
import info.spain.opencatalog.domain.poi.types.TimeTableEntry;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.Objects;

/**
 * 
 * Define los campos comunes que tendrán los Poi de tipo Lodging
 * 
 */
@Document(collection="poi")
public class BeachPoiType extends AbstractPoiType {
	
	private Double large;
	private Double width;
	private BeachComposition composition;
	private BeachSandType sandType;
	private BeachBathCondition bathCondition;
	private Boolean	anchorZone = Boolean.FALSE;	 // zona de fondeo
	private Boolean	promenade = Boolean.FALSE;	 // paseo marítimo


	
//    @Override
//    public void validateTypeAllowedValues(){
//    	super.validateTypeAllowedValues();
//    }
	
	
	public BeachPoiType() {
		super();
		setPoiType(PoiType.BEACH);
	}
    
  
    @Override
	public BeachPoiType setName(I18nText name) {
		return (BeachPoiType) super.setName(name);
	}


	public BeachPoiType(AbstractPoiType other) {
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
	public BeachPoiType setPoiType(PoiType poiType) {
		return (BeachPoiType) super.setPoiType(poiType);
	}

	@Override
    public BeachPoiType setFlags(Flag... flags) {
        return (BeachPoiType) super.setFlags(flags);
    }
    
    @Override
	public BeachPoiType setQualityCertificates( QualityCertificate... qualityCertificates) {
		return (BeachPoiType) super.setQualityCertificates(qualityCertificates);
	}

    @Override
	public BeachPoiType setDisabledAccessibility(Set<DisabledAccessibility> disabledAccessibility) {
		return (BeachPoiType) super.setDisabledAccessibility(disabledAccessibility);
	}
	@Override
	public BeachPoiType setDisabledAccessibility(DisabledAccessibility... disabledAccessibility) {
		return (BeachPoiType) super.setDisabledAccessibility(disabledAccessibility);
	}
	
	@Override
	public BeachPoiType setTimetable(Set<TimeTableEntry> timetable) {
		return (BeachPoiType) super.setTimetable(timetable);
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

	public BeachSandType getSandType() {
		return sandType;
	}

	public BeachPoiType setSandType(BeachSandType sandType) {
		this.sandType = sandType;
		return this;
	}

	public BeachBathCondition getBathCondition() {
		return bathCondition;
	}

	public BeachPoiType setBathCondition(BeachBathCondition bathCondition) {
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
