package info.spain.opencatalog.domain;

public class Address {
	
	private String route;
	private String city;
	private String zipCode;
	public String getRoute() {
		return route;
	}
	public Address setRoute(String address) {
		this.route = address;
		return this;
	}
	public String getCity() {
		return city;
	}
	public Address setCity(String city) {
		this.city = city;
		return this;
	}
	public String getZipCode() {
		return zipCode;
	}
	public Address setZipCode(String zipCode) {
		this.zipCode = zipCode;
		return this;
	}
	
	public String toString(){
		return "[route=" + route +
			   ", city=" + city +
			   ", zipCode=" + zipCode +
			   "]";
	}
	

}
