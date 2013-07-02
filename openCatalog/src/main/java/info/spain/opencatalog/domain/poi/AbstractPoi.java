package info.spain.opencatalog.domain.poi;

import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.lodging.Score;
import info.spain.opencatalog.domain.poi.types.BasicPoiType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;

/**
 * Clase base para cualquier POI
 */
@Document(collection="poi")
public abstract class AbstractPoi {
	
	@Id
	private String id;

	/** Tipo de POI específico: Hotel, Monumento, Playa, ... */
	@NotNull
	private BasicPoiType type;
	
	@Indexed
	private I18nText name;

	private I18nText description;
	
	private Address address;
	
	/** Geospatial location */
	@NotNull
	@GeoSpatialIndexed
	private GeoLocation location; 	
	
    /** características que un BasicPoiType puede tener o no: visitas guiadas, tiendas, etc.. */
    private Set<Flag> flags;

    /** valoración oficial (no de los usuarios): 3 Estrellas, 2 tenedores, etc */
    private Score score;
    
    /** Datos específicos del poi: longitud(playa), nº pistas verdes(estación esquí) ... */
    private Map<String,String> data;

    /** certificados otorgados a este poi: ISO9001, Patrimonio de la humanidad... */
    private Set<QualityCertificateFlag> qualityCertificateFlags;

    /** Accesibilidad para personas con discapacidad */
   	private Set<AccessibilityFlag> accessibilityFlags;

    /** Servicios para las familias: Guardería, parque infantil, ... */
   	private Set<FamilyServiceFlag> familyServiceFlags;

   	/** horarios de apertura/cierre */
	private Set<TimeTableEntry> timetable;
	
	/** precios de acceso */
    private Set<AccessPrice> prices;
   
	/** Información de contacto */
    private ContactInfo contactInfo;
    
    @CreatedDate
	private DateTime created;
	
	@LastModifiedDate
	private DateTime lastModified;
	
	public AbstractPoi(BasicPoiType type){
		this.type = type;
    }

	/** Permite definir las validaciones en función del tipo */
	public AbstractPoi validate(){
        type.validate(this);
        return this;
	}
	
	public void copyData(AbstractPoi source){
        this.id = source.id;
        this.name = source.name;
        this.description = source.description;
        this.address = source.address;
        this.location = source.location;
        this.contactInfo = source.contactInfo;
        this.flags = source.flags;
        this.timetable = source.timetable;
        this.accessibilityFlags = source.accessibilityFlags;
        this.qualityCertificateFlags = source.qualityCertificateFlags;
        this.familyServiceFlags = source.familyServiceFlags;
    }	
	
	public Set<TimeTableEntry> getTimetable() {
		return timetable;
	}

	public AbstractPoi setTimetable(TimeTableEntry... timetable) {
		this.timetable = Sets.newHashSet(timetable);
		return this;
	}

	public Set<AccessibilityFlag> getAccessibilityFlags() {
		return accessibilityFlags;
	}

	public AbstractPoi setAccessibilityFlags(AccessibilityFlag... flags) {
		this.accessibilityFlags = Sets.newHashSet(flags);
        return this;
	}
	
    public AbstractPoi setFlags(Flag... flags) {
        this.flags = Sets.newHashSet(flags);
        return this;
    }

	public AbstractPoi setQualityCertificates(QualityCertificateFlag... flags) {
		this.qualityCertificateFlags =  Sets.newHashSet(flags);
		return this;
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

	public AbstractPoi setId(String id) {
		this.id = id;
		return this;
	}

	public I18nText getName() {
		return name;
	}

	public AbstractPoi setName(I18nText name) {
		this.name = name;
		return this;
	}

	public I18nText getDescription() {
		return description;
	}

	public AbstractPoi setDescription(I18nText description) {
		this.description = description;
		return this;
	}

	public Address getAddress() {
		return address;
	}

	public AbstractPoi setAddress(Address address) {
		this.address = address;
		return this;
	}

	public GeoLocation getLocation() {
		return location;
	}

	public AbstractPoi setLocation(GeoLocation location) {
		this.location = location;
		return this;
	}
	
	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public AbstractPoi setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
		return this;
	}

    public Score getScore() {
        return score;
    }

    public AbstractPoi setScore(Score score) {
        this.score = score;
        return this;
    }

    
    public DateTime getCreated() {
		return created;
	}

	public DateTime getLastModified() {
		return lastModified;
	}

	
	public BasicPoiType getType() {
		return type;
	}

	public Set<AccessPrice> getPrices() {
		return prices;
	}

	public AbstractPoi setPrices(AccessPrice...prices) {
		this.prices = Sets.newHashSet(prices);
		return this;
	}
	
	public Set<FamilyServiceFlag> getFamilyServiceFlags() {
		return familyServiceFlags;
	}

	public AbstractPoi setFamilyServiceFlags(FamilyServiceFlag... flags) {
		this.familyServiceFlags = Sets.newHashSet(flags);
		return this;
	}
	

	public Map<String, String> getData() {
		return data;
	}

	public AbstractPoi setData(String key, String data) {
		if (this.data == null){
			this.data = new HashMap<String, String>();
		}
		this.data.put(key,data);
		return this;
	}

	@Override
	public String toString() {
		return toStringHelper().toString();
	}
	
	protected com.google.common.base.Objects.ToStringHelper toStringHelper(){
		return Objects.toStringHelper(getClass())
				.add("id", id)
				.add("type", type.getId())
				.add("name", name)
				.add("description", description)
				.add("location", location)
				.add("data,", "data")
				.add("contactInfo", contactInfo)
				.add("timeTable", timetable)
				.add("flags", flags)
				.add("prices", prices)
				.add("accessibilityFlags",accessibilityFlags)
				.add("qualityCertificateFlags", qualityCertificateFlags)
				.add("familyServiceFlags",familyServiceFlags)
				.add("created", created)
				.add("lastModified", lastModified);
	}
    
}
