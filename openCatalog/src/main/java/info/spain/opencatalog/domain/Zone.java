package info.spain.opencatalog.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Point of Interest
 * 
 * @author ehdez
 */
@Document
public class Zone implements Serializable {

	private static final long serialVersionUID = 5722653798168373056L;
	
	public Zone(){}
	public Zone(Zone other){
		this.id = other.id;
		this.name = other.name;
		this.description = other.description;
		this.path= other.path;
		this.address= other.address;
	}
	
	@Id
	private String id;

	@Indexed
	@NotNull
	private String name;

	@NotNull
	private String description; 
	
	private Address address = new Address();
	

	@NotEmpty(message="error.notEmpty.path")
	private List<GeoLocation> path = new ArrayList<GeoLocation>(); 	// p
	
	
	public void setId(String id){
		this.id = id;
	}
	public String getId() {
		return id;
	}

	public List<GeoLocation> getPath() {
		return path;
	}

	public String getDescription() {
		return description;
	}

	public Zone setPath(List<GeoLocation> path) {
		this.path= path;
		return this;
	}

	public Zone setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getName() {
		return name;
	}

	public Zone setName(String name) {
		this.name = name;
		return this;
	}

	public Address getAddress() {
		return address;
	}
	public Zone setAddress(Address address) {
		this.address = address;
		return this;
	}
	
	@Override
	public String toString() {
		return "Zone [id=" + id + ", name=" + name + ", description="
				+ description + ", address=" + address + ", path=" + path + "]";
	}
	

}