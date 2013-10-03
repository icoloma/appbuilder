package info.spain.opencatalog.api;

import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.Zone;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.ResourceSupport;

@XmlRootElement
public class ZoneResource extends ResourceSupport {
	
	private String name;
	private String description; 
	private Address address = new Address();
	private List<GeoLocation> path = new ArrayList<GeoLocation>(); 	
	private String created;
	private String lastModifiedDate;

	
	public void copyFrom(Zone zone){
		BeanUtils.copyProperties(zone, this, new String[] {"created", "lastModified"});
		this.created = zone.getCreated().toString();
		this.lastModifiedDate = zone.getLastModified().toString();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
		public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<GeoLocation> getPath() {
		return path;
	}
	public void setPath(List<GeoLocation> path) {
		this.path = path;
	}
	public String getCreated() {
		return created;
	}
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}


}
