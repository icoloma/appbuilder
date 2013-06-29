package info.spain.opencatalog.domain;

import com.google.common.base.Objects;

public class Address {
	
	public static final String DEFAULT_ROUTE = "Plaza Puerta del Sol";
	public static final String DEFAULT_ADMIN_AREA_1 = "Comunidad de Madrid";
	public static final String DEFAULT_ADMIN_AREA_2 = "Madrid";
	public static final String DEFAULT_ZIPCODE = "28013";
	
	private String route = DEFAULT_ROUTE;
	private String adminArea1 = DEFAULT_ADMIN_AREA_1;
	private String adminArea2 = DEFAULT_ADMIN_AREA_2;
	private String zipCode = DEFAULT_ZIPCODE;
	
	public String getRoute() {
		return route;
	}
	public Address setRoute(String address) {
		this.route = address;
		return this;
	}

	public String getZipCode() {
		return zipCode;
	}
	public Address setZipCode(String zipCode) {
		this.zipCode = zipCode;
		return this;
	}
	
	public String getAdminArea1() {
		return adminArea1;
	}
	public Address setAdminArea1(String adminArea1) {
		this.adminArea1 = adminArea1;
		return this;
	}
	
	public String getAdminArea2() {
		return adminArea2;
	}
	public Address setAdminArea2(String adminArea2) {
		this.adminArea2 = adminArea2;
		return this;
	}
	public String toString(){
		return Objects.toStringHelper(getClass())
			.add("route", route)
			.add("adminArea1",adminArea1)
			.add("adminArea2",adminArea2)
			.add("zipCode",zipCode)
			.toString();
	}
	

}
