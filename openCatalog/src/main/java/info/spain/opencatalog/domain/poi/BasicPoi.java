package info.spain.opencatalog.domain.poi;

import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.types.BasicPoiType;

import java.util.HashMap;
import java.util.HashSet;
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
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Clase base para cualquier POI
 */
@Document(collection="poi")
public class BasicPoi {
	
	@Id
	protected String id;

	/** Tipo de POI específico: Hotel, Monumento, Playa, ... */
	@NotNull
	protected BasicPoiType type;
	
	@Indexed
	protected I18nText name;

	protected I18nText description;
	
	protected Address address;
	
	/** Geospatial location */
	@NotNull
	@GeoSpatialIndexed
	protected GeoLocation location; 	
	
    /** características que un BasicPoiType puede tener o no: visitas guiadas, tiendas, etc.. */
	protected Set<Flag> flags;

    /** valoración oficial (no de los usuarios): 3 Estrellas, 2 tenedores, etc */
    protected Score score;
    
    /** Entorno: El generalife, ... */
    protected I18nText enviroment;
    
    /** Datos específicos del poi: longitud(playa), nº pistas verdes(estación esquí) ... */
    protected Map<String,String> data;

   	/** horarios de apertura/cierre */
    protected Set<TimeTableEntry> timetable;
	
	/** precios de acceso */
    protected Set<Price> prices;
   
	/** Información de contacto */
    protected ContactInfo contactInfo;
    
	/** Idiomas soportados en formato ISO*/
    protected Set<String> languages;
	
    
    @CreatedDate
	private DateTime created;
	
	@LastModifiedDate
	private DateTime lastModified;
	
	public BasicPoi(){
	}
	public BasicPoi(BasicPoiType type){
		this.type = type;
    }

	/** Permite definir las validaciones en función del tipo */
	public BasicPoi validate(){
        type.validate(this);
        return this;
	}
	
	/** Initialize all Collections */ 
	protected void initCollections(){
		this.flags= new HashSet<Flag>();
		this.data = new HashMap<String,String>();
		this.timetable = new HashSet<TimeTableEntry>();
		this.prices = new HashSet<Price>();
		this.languages=new HashSet<String>();
		if (this.address == null){
			setAddress(new Address());
		}
	}
	
	public BasicPoi copyData(BasicPoi source){
        this.id = source.id;
        this.name = new I18nText().copyNotEmpty(source.name);
        this.description = new I18nText().copyNotEmpty(source.description);
        this.address = source.address;
        this.location = source.location;
        this.contactInfo = source.contactInfo;
        this.flags = source.flags;
        this.timetable = source.timetable;
        this.data= deleteEmptyEntries(source.data);
        return this;
    }	
	
	/**
	 * Elimina los elementos vacíos
	 * @param source
	 * @return
	 */
	private Map<String,String> deleteEmptyEntries(Map<String,String> source){
		Map<String,String> result = Maps.newLinkedHashMap();
		if (source != null) {
			for (String key : source.keySet()) {
				String value = source.get(key);
				if (!Strings.isNullOrEmpty(value)){
					result.put(key, value);
				}
			}
		}
		return result;
	}
	
	public Set<TimeTableEntry> getTimetable() {
		return timetable;
	}

	public BasicPoi setTimetable(TimeTableEntry... timetable) {
		this.timetable = Sets.newHashSet(timetable);
		return this;
	}

	
    public BasicPoi setFlags(Flag... flags) {
        this.flags = Sets.newHashSet(flags);
        return this;
    }

	public Set<Flag> getFlags() {
		return flags;
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

    public Score getScore() {
        return score;
    }

    public BasicPoi setScore(Score score) {
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

	public Set<Price> getPrices() {
		return prices;
	}

	public BasicPoi setPrices(Price...prices) {
		this.prices = Sets.newHashSet(prices);
		return this;
	}
	
	public Map<String, String> getData() {
		return data;
	}

	public BasicPoi setData(String key, String data) {
		if (this.data == null){
			this.data = new HashMap<String, String>();
		}
		this.data.put(key,data);
		return this;
	}
	
	public Set<String> getLanguages() {
		return languages;
	}

	public BasicPoi setLanguages(String... languages) {
		this.languages = Sets.newHashSet(languages);
		return this;
	}

	
	public I18nText getEnviroment() {
		return enviroment;
	}

	public BasicPoi setEnviroment(I18nText enviroment) {
		this.enviroment = enviroment;
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
				.add("languages", languages)
				.add("enviroment",enviroment)
				.add("created", created)
				.add("lastModified", lastModified);
	}
    
}
