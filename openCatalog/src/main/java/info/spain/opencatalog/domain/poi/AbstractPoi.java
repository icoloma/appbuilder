package info.spain.opencatalog.domain.poi;

import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.domain.poi.lodging.types.AbstractPoiType;
import info.spain.opencatalog.validator.ValidI18nText;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AbstractPoi implements Serializable {
	
	private static final long serialVersionUID = 5722653798168373056L;

	@Id
	private String id;

    private AbstractPoiType type;

	@ValidI18nText(message="poi.name.validation.message")   
	@Indexed
	private I18nText name;

	@ValidI18nText(message="poi.description.validation.message") 
	private I18nText description; 
	
	private Address address = new Address();
	
	@NotNull
	@GeoSpatialIndexed
	private GeoLocation location; 	// Geospatial location
	
	private List<Tag> tags = new ArrayList<Tag>();
	
	@CreatedDate
	private DateTime createdDate;
	
	@LastModifiedDate
	private DateTime lastModifiedDate;

    /** certificados que se han otorgado a este poi: patrominio de la humanidad, bandera azul, etc */
    private Set<QualityCertificate> certificates;

    public AbstractPoi(){
        this.createdDate = new DateTime();
        this.lastModifiedDate = new DateTime();
    }

    public AbstractPoi(AbstractPoi other){
        copyData(this, other);
    }

    public static void copyData(AbstractPoi target, AbstractPoi source){
        target.id = source.id;
        target.name = source.name;
        target.description = source.description;
        target.address = source.address;
        target.location = source.location;
        target.tags = source.tags;
    }

    protected AbstractPoi(AbstractPoiType type) {
        this.type = type;
    }

    public AbstractPoi setId(String id){
		this.id = id;
		return this;
	}
	public String getId() {
		return id;
	}

	public GeoLocation getLocation() {
		return location;
	}

	public I18nText getDescription() {
		return description;
	}

	public void setLocation(GeoLocation loc) {
		this.location = loc;
		
	}

	public void setDescription(I18nText description) {
		this.description = description;
	}

	public I18nText getName() {
		return name;
	}

	public void setName(I18nText name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tag) {
		this.tags = tag;
	}
	
	
	public void addTag(Tag tag) {
		this.tags.add(tag);
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}
	public DateTime getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	
	
	@Override
	public String toString() {
		return "[id=" + id + 
			   ", name=" + name + 
			   ", description=" + description +
			   ", address=" + address +
			   ", location=" + location +
			   ", tags=" + tags +
			   ", createdDate=" + createdDate +
			   ", lastModifiedDate=" + lastModifiedDate +
			   "]";
	}

}
