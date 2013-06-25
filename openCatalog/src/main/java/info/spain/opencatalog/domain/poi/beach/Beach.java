package info.spain.opencatalog.domain.poi.beach;

import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.Accessibility;
import info.spain.opencatalog.domain.poi.Certificate;

import java.util.ArrayList;
import java.util.List;

/**
 * Playa 
 *
 * @author ehdez
 *
 */


public class Beach extends AbstractPoi {
	
	private static final long serialVersionUID = -707739442931238340L;
	
	public static enum BeachComposition {
		SAND,
		VOLCANIC_BLACK_SAND,
		GRAVEL,
		STONE,
		ROCK;		
	}
	
	public static enum BeachSandType {
		WHITE,
		GOLDEN,
		DARK
	}
	
	public static enum BeachBathCondition {
		CALM,
		STRONG_WAVES,
		MODERATE_WAVES,
		SOFT_WAVES
	}
	
	

	private List<Accessibility> accessibility = new ArrayList<Accessibility>(); 
	private List<Certificate> certificate = new ArrayList<Certificate>(); 
	
	private Double large;
	private Double width;
	
	private BeachComposition composition;
	private BeachSandType sandType;
	private BeachBathCondition bathCondition;
	private Boolean	anchorZone;
	private Boolean	promenade;

	
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
	
	
	public Double getLarge() {
		return large;
	}
	public void setLarge(Double large) {
		this.large = large;
	}
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	public BeachComposition getComposition() {
		return composition;
	}
	public void setComposition(BeachComposition composition) {
		this.composition = composition;
	}
	public BeachSandType getSandType() {
		return sandType;
	}
	public void setSandType(BeachSandType sandType) {
		this.sandType = sandType;
	}
	public BeachBathCondition getBathCondition() {
		return bathCondition;
	}
	public void setBathCondition(BeachBathCondition bathCondition) {
		this.bathCondition = bathCondition;
	}
	public Boolean getAnchorZone() {
		return anchorZone;
	}
	public void setAnchorZone(Boolean anchorZone) {
		this.anchorZone = anchorZone;
	}
	public Boolean getPromenade() {
		return promenade;
	}
	public void setPromenade(Boolean promenade) {
		this.promenade = promenade;
	}
	public Beach(Beach other) {
		super(other);
	}

	public Beach() {
		super();
	}
	
	
	

}
