package travel.spain.opencatalog.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
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

	@NotNull
	private String name;

	private String description;

	@NotNull
	private GeoLocation loc; 	// Geospatial location

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public GeoLocation getLoc() {
		return loc;
	}

	public String getDescription() {
		return description;
	}

	public Poi setLoc(GeoLocation loc) {
		this.loc = loc;
		return this;
	}

	public Poi setDescription(String description) {
		this.description = description;
		return this;
	}

	public Poi setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", name=" + name + ", description=" + description
				+ " loc=" + loc +"]";
	}

}