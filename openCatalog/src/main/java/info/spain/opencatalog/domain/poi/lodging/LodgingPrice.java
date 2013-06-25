package info.spain.opencatalog.domain.poi.lodging;



/**
 * Precio de un servicio en función de la temporada y el régimen de alojamiento  
 *
 * Ej.: hab-doble, temporada alta, media pensión, 100
 * 
 * @author ehdez
 *
 */
public abstract class LodgingPrice {
	
	private String priceType;  // depende del tipo de alojamiento ( hotel:suite|hab-doble|... ; camping : Autocaravana|Tienda|...   
	private Season season;
	private Regime regime;
	private Double price;

	protected LodgingPrice(String priceType, Season season, Regime regime,Double price) {
		super();
		this.priceType = priceType;
		this.season = season;
		this.regime = regime;
		this.price = price;
	}

	
	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Regime getRegime() {
		return regime;
	}

	public void setRegime(Regime regime) {
		this.regime = regime;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}
}


