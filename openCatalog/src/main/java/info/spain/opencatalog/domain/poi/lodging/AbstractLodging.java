package info.spain.opencatalog.domain.poi.lodging;

import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.DisabledAccessibility;
import info.spain.opencatalog.domain.poi.QualityCertificate;
import info.spain.opencatalog.domain.poi.types.lodging.LodgingPrice;

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
	
	private List<DisabledAccessibility> accessibility = new ArrayList<DisabledAccessibility>(); 
	private List<QualityCertificate> certificate = new ArrayList<QualityCertificate>(); 

	
	public List<DisabledAccessibility> getAccessibility() {
		return accessibility;
	}
	public void setAccessibility(List<DisabledAccessibility> accessibility) {
		this.accessibility = accessibility;
	}
	
	
	public List<QualityCertificate> getCertificate() {
		return certificate;
	}
	public void setCertificate(List<QualityCertificate> certificate) {
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
