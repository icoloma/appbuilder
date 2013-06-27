package info.spain.opencatalog.domain.poi.types;

import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.DisabledAccessibility;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.PoiTypeRepository;
import info.spain.opencatalog.domain.poi.PoiTypeRepository.PoiType;
import info.spain.opencatalog.domain.poi.QualityCertificate;
import info.spain.opencatalog.validator.ValidI18nText;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;

/**
 * Clase base para cualquier POI
 */
@Document(collection="poi")
public class BasicPoi {
	
	@Id
	private String id;

	/** Tipo de POI específico: Hotel, Monumento, Playa, ... */
	@NotNull
	private PoiType poiType;
	
	@ValidI18nText(message="poi.name.validation.message")   
	@Indexed
	private I18nText name;

	@ValidI18nText(message="poi.description.validation.message") 
	private I18nText description; 
	
	private Address address;
	
	/** Geospatial location */
	@NotNull
	@GeoSpatialIndexed
	private GeoLocation location; 	
	
    /** características que un PoiType puede tener o no: visitas guiadas, tiendas, etc.. */
    private Set<Flag> flags;

    /** certificados que pueden asignarse a este poi */
    private Set<QualityCertificate> qualityCertificates;

    /** Accesibilidad para personas con discapacidad */
	private Set<DisabledAccessibility> disabledAccessibility;     

	/** horarios de apertura/cierre */
	private Set<TimeTableEntry> timetable;
	
	/** Información de contacto */
    private ContactInfo contactInfo;
    
    @CreatedDate
	private DateTime createdDate;
	
	@LastModifiedDate
	private DateTime lastModifiedDate;
	
	public BasicPoi(){
		this.poiType = PoiType.POI;
        this.createdDate = new DateTime();
        this.lastModifiedDate = new DateTime();
    }
	public BasicPoi(BasicPoi other){
		this.poiType = PoiType.POI;
	    copyData(other);
	}
	
	/** Permite definir las validaciones en función del tipo */
	public void validateTypeAllowedValues(){
		Assert.notNull(getPoiType());
		BasicPoi type = PoiTypeRepository.getPoiType(getPoiType());
		validateFlags(type.getFlags(), getFlags());
		validateFlags(type.getDisabledAccessibility(), getDisabledAccessibility());
		validateFlags(type.getQualityCertificates(), getQualityCertificates());
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void validateFlags(Set allowed, Set current){
		if (allowed == null || allowed.size() == 0 ||
			current == null || current.size() == 0){
			return;
		}
		
		if (!allowed.containsAll(current)) {
			throw new IllegalArgumentException("Asignación de flag no permitida. Flags permitidos : " + allowed + ", Flags asignados :" + current);
		}
	}
	
	public void copyData(BasicPoi source){
        this.id = source.id;
        this.name = source.name;
        this.description = source.description;
        this.address = source.address;
        this.location = source.location;
        this.contactInfo = source.contactInfo;
        this.disabledAccessibility = source.disabledAccessibility;
        this.flags = source.flags;
        this.qualityCertificates = source.qualityCertificates;
        this.timetable = source.timetable;
    }	
	
	public Set<TimeTableEntry> getTimetable() {
		return timetable;
	}

	public BasicPoi setTimetable(Set<TimeTableEntry> timetable) {
		this.timetable = timetable;
		return this;
	}

	public BasicPoi setTimetable(TimeTableEntry... timetable) {
		return setTimetable(Sets.newHashSet(timetable));
	}


	public Set<DisabledAccessibility> getDisabledAccessibility() {
		return disabledAccessibility;
	}


	public BasicPoi setDisabledAccessibility(Set<DisabledAccessibility> disabledAccessibility) {
		this.disabledAccessibility = disabledAccessibility;
		return this;
	}
	public BasicPoi setDisabledAccessibility(DisabledAccessibility... disabledAccessibility) {
		return setDisabledAccessibility(Sets.newHashSet(disabledAccessibility));
	}
	
    public BasicPoi setFlags(Flag... flags) {
        return this.setFlags(Sets.newHashSet(flags));
    }

    public BasicPoi setFlags(Set <Flag> flags) {
        this.flags = flags;
        return this;
    }

	public BasicPoi setQualityCertificates(Set<QualityCertificate> qualityCertificates) {
		this.qualityCertificates = qualityCertificates;
		return this;
	}
	
	public BasicPoi setQualityCertificates(QualityCertificate... qualityCertificates) {
		return setQualityCertificates(Sets.newHashSet(qualityCertificates));
	}

	public Set<Flag> getFlags() {
		return flags;
	}

	public Set<QualityCertificate> getQualityCertificates() {
		return qualityCertificates;
	}
	
	public PoiType getPoiType() {
		return poiType;
	}

	public BasicPoi setPoiType(PoiType poiType) {
		this.poiType = poiType;
		return this;
	}

	public String getId() {
		return id;
	}

	public BasicPoi setId(String id) {
		this.id = id;
		return this;
	}

	public I18nText getName() {
		return name;
	}

	public BasicPoi setName(I18nText name) {
		this.name = name;
		return this;
	}

	public I18nText getDescription() {
		return description;
	}

	public BasicPoi setDescription(I18nText description) {
		this.description = description;
		return this;
	}

	public Address getAddress() {
		return address;
	}

	public BasicPoi setAddress(Address address) {
		this.address = address;
		return this;
	}

	public GeoLocation getLocation() {
		return location;
	}

	public BasicPoi setLocation(GeoLocation location) {
		this.location = location;
		return this;
	}
	
	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public BasicPoi setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
		return this;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	
	public DateTime getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(getClass())
			.add("poiType", poiType)
			.add("id", id)
			.add("name", name)
			.add("description", description)
			.add("location", location)
			.add("timeTable", timetable)
			.add("contactInfo", contactInfo)
			.add("createdDate", createdDate)
			.add("flags", flags)
			.add("lastModifiedDate", lastModifiedDate).toString();
	}
    
}
