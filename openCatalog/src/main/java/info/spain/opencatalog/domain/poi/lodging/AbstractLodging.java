package info.spain.opencatalog.domain.poi.lodging;

import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.Accessibility;
import info.spain.opencatalog.domain.poi.Certificate;

import java.util.ArrayList;
import java.util.List;

/**
 * Alojamiento - clase base para todos los tipos de alojamiento
 *
 * @author ehdez
 *
 */


public abstract class AbstractLodging<P extends LodgingPrice, C, S > extends AbstractPoi {
	
	private static final long serialVersionUID = -707739442931238340L;

	public abstract List<P> getPrices();
	public abstract void setPrices(List<P> prices);
	
	public abstract C getCategory(); 
	public abstract void setCategory(C category);
	
	public abstract List<S> getServices();
	public abstract void setServices(List<S> services);
	
	private List<Accessibility> accessibility = new ArrayList<Accessibility>(); 
	private List<Certificate> certificate = new ArrayList<Certificate>(); 

	
	public List<Accessibility> getAccessibility() {
		return accessibility;
	}
	public void setAccessibility(List<Accessibility> accessibility) {
		this.accessibility = accessibility;
	}
	
	
	public List<Certificate> getCertificate() {
		return certificate;
	}
	public void setCertificate(List<Certificate> certificate) {
		this.certificate = certificate;
	}
	@SuppressWarnings("rawtypes")
	public AbstractLodging(AbstractLodging other) {
		super(other);
	}

	public AbstractLodging() {
		super();
	}
	
	
	

}
