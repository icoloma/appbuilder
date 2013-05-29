package info.spain.opencatalog.domain;

public class Address {
	
	public static final String DEFAULT_ROUTE   = "Plaza Puerta del Sol";
	public static final String DEFAULT_CITY    = "Madrid";
	public static final String DEFAULT_ZIPCODE = "28013";
	
	private String route = DEFAULT_ROUTE;
	private String city = DEFAULT_CITY;
	private String zipCode = DEFAULT_ZIPCODE;
	
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
