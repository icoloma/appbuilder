package info.spain.opencatalog.domain;

import java.awt.Polygon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
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
		copyData(this,other);
	}
	
	public static void copyData(Zone target, Zone source){
		target.id = source.id;
		target.name = source.name;
		target.description = source.description;
		target.path= source.path;
		target.address= source.address;
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
	private List<GeoLocation> path = new ArrayList<GeoLocation>(); 	
	
	@CreatedDate
	private DateTime created;
	
	@LastModifiedDate
	private DateTime lastModifiedDate;
	
	
	public DateTime getCreated() {
		return created;
	}

	public DateTime getLastModified() {
		return lastModifiedDate;
	}

	public Zone setId(String id){
		this.id = id;
		return this;
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
		updatePolygon();
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
	
	public void updatePolygon(){
		
	}
	
	/**
	 * Comprueba si un punto está dentro de la zona
	 * Delega el cálculo a través de java.awt.Polygon.contains()
	 * Datdo que Polygon.contains() funciona con int y nuestros puntos son double 
	 * multiplicamos por 1000000 para quitarnos la coma sin perder la precisión
	 * @param point
	 * @return
	 */
	public boolean contains(GeoLocation point){
		Polygon polygon = new Polygon();
		for (GeoLocation geo : path) {
			int x = scale(geo.getLng());
			int y = scale(geo.getLat());
			polygon.addPoint(x, y);
		}
		int x = scale(point.getLng());
		int y = scale(point.getLat());
		return polygon.contains(x, y);
	}

	private int scale(double val ){
		return  Long.valueOf(Math.round(val*1000000)).intValue();
	}
	

}