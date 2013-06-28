package info.spain.opencatalog.domain.poi.types;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.AccessibilityFlag;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.QualityCertificateFlag;
import info.spain.opencatalog.domain.poi.types.lodging.Score;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Clase base para cualquier POI
 */
@Document(collection="poi")
public class BasicPoi {
	
	@Id
	private String id;

	/** Tipo de POI específico: Hotel, Monumento, Playa, ... */
	@NotNull
	private PoiType type;
	
	@Indexed
	private I18nText name;

	private I18nText description;
	
	private Address address;
	
	/** Geospatial location */
	@NotNull
	@GeoSpatialIndexed
	private GeoLocation location; 	
	
    /** características que un PoiType puede tener o no: visitas guiadas, tiendas, etc.. */
    private Set<Flag> flags;

    /** valoración oficial (no de los usuarios): 3 Estrellas, 2 tenedores, etc */
    private Score score;

    /** certificados otorgados a este poi: ISO9001, Patrimonio de la humanidad... */
    private Set<QualityCertificateFlag> qualityCertificateFlags;

    /** Accesibilidad para personas con discapacidad */
	private Set<AccessibilityFlag> accessibilityFlags;

	/** horarios de apertura/cierre */
	private Set<TimeTableEntry> timetable;
	
	/** Información de contacto */
    private ContactInfo contactInfo;
    
    @CreatedDate
	private DateTime createdDate;
	
	@LastModifiedDate
	private DateTime lastModified;
	
	public BasicPoi(PoiType type){
		this.type = type;
        //this.createdDate = new DateTime();
        //this.lastModified = new DateTime();
    }

	/** Permite definir las validaciones en función del tipo */
	public void validate(){
        type.validate(this);
	}
	
	public void copyData(BasicPoi source){
        this.id = source.id;
        this.name = source.name;
        this.description = source.description;
        this.address = source.address;
        this.location = source.location;
        this.contactInfo = source.contactInfo;
        this.accessibilityFlags = source.accessibilityFlags;
        this.flags = source.flags;
        this.qualityCertificateFlags = source.qualityCertificateFlags;
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

	public Set<AccessibilityFlag> getAccessibilityFlags() {
		return accessibilityFlags;
	}

	public BasicPoi setAccessibilityFlags(AccessibilityFlag... disabledAccessibility) {
		this.accessibilityFlags = Sets.newHashSet(disabledAccessibility);
        return this;
	}
	
    public BasicPoi setFlags(Flag... flags) {
        return this.setFlags(Sets.newHashSet(flags));
    }

    public BasicPoi setFlags(Set <Flag> flags) {
        this.flags = flags;
        return this;
    }

	public BasicPoi setQualityCertificateFlags(Set<QualityCertificateFlag> qualityCertificateFlags) {
		this.qualityCertificateFlags = qualityCertificateFlags;
		return this;
	}
	
	public BasicPoi setQualityCertificates(QualityCertificateFlag... qualityCertificateFlags) {
		return setQualityCertificateFlags(Sets.newHashSet(qualityCertificateFlags));
	}

	public Set<Flag> getFlags() {
		return flags;
	}

	public Set<QualityCertificateFlag> getQualityCertificateFlags() {
		return qualityCertificateFlags;
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

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    @Override
	public String toString() {
		return Objects.toStringHelper(getClass())
			.add("type", type)
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
