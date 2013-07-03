package info.spain.opencatalog.domain.poi;

import com.google.common.base.Objects;

/**
 * Informaci&oacute;n de contacto: tel&eacute;fono, fax, mail, ...
 */
public class ContactInfo {
	
	private String phone;
	private String fax;
	private String email;
	private String url;
	
	/** URL, mail o teléfono para reservas */
	private String reservation;

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
	
	
	public String getReservation() {
		return reservation;
	}
	public void setReservation(String reservation) {
		this.reservation = reservation;
	}
	@Override
	public String toString() {
		return Objects.toStringHelper(getClass())
			.add("phone",phone)
			.add("fax",fax)
			.add("email", email)
			.add("url",url)
			.add("reservation", reservation)
			.toString();
	}
	

	
}
