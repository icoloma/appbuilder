package info.spain.opencatalog.domain.poi.types;

/**
 * Información de contacto: teléfono, fax, mail, ...
 */
public class ContactInfo {
	
	private String phone;
	private String fax;
	private String email;
	private String url;

	public String getPhone() {
		return phone;
	}
	public ContactInfo setPhone(String phone) {
		this.phone = phone;
		return this;
	}
	public String getFax() {
		return fax;
	}
	public ContactInfo setFax(String fax) {
		this.fax = fax;
		return this;
	}
	public String getUrl() {
		return url;
	}
	public ContactInfo setUrl(String url) {
		this.url = url;
		return this;
	}
	public String getEmail() {
		return email;
	}
	public ContactInfo setEmail(String email) {
		this.email = email;
		return this;
	}
	

	
}
