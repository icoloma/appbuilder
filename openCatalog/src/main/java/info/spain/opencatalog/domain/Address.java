package info.spain.opencatalog.domain;

public class Address {
	
	private String address;
	private String city;
	private String zipCode;
	public String getAddress() {
		return address;
	}
	public Address setAddress(String address) {
		this.address = address;
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
		return "[address=" + address +
			   ", city=" + city +
			   ", zipCode=" + zipCode +
			   "]";
	}
	

}
