package info.spain.opencatalog.domain;

import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.validator.ValidI18nText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Point of Interest
 * 
 * @author ehdez
 */
@Document
public class Poi implements Serializable {

	private static final long serialVersionUID = 5722653798168373056L;
	
	@Id
	private String id;

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
	
	@DBRef
	private List<Poi> related = new ArrayList<Poi>();

	public List<Poi> getRelated() {
		return related;
	}

	public Poi setRelated(List<Poi> related) {
		this.related = related;
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

	public Poi setLocation(GeoLocation loc) {
		this.location = loc;
		return this;
	}

	public Poi setDescription(I18nText description) {
		this.description = description;
		return this;
	}

	public I18nText getName() {
		return name;
	}

	public Poi setName(I18nText name) {
		this.name = name;
		return this;
	}

	public Address getAddress() {
		return address;
	}

	public Poi setAddress(Address address) {
		this.address = address;
		return this;
	}
	
	
	public List<Tag> getTags() {
		return tags;
	}

	public Poi setTags(List<Tag> tag) {
		this.tags = tag;
		return this;
	}
	
	
	public Poi addTag(Tag tag) {
		this.tags.add(tag);
		return this;
	}

	

	@Override
	public String toString() {
		return "[id=" + id + 
			   ", name=" + name + 
			   ", description=" + description +
			   ", address=" + address +
			   ", location=" + location +
			   ", tags=" + tags +
			   "]";
	}

}