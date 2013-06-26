package info.spain.opencatalog.domain.poi.types.lodging;

import com.google.common.base.Objects;




/**
 * Precio de un servicio en función de la temporada y el régimen de alojamiento  
 *
 * Ej.: hab-doble, temporada alta, media pensión, 100
 * 
 * @author ehdez
 *
 */
public class LodgingPrice {
	
	private LodgingType lodgingType;  // tipo de alojamiento ( hotel:suite|hab-doble|... ; camping : Autocaravana|Tienda|...   
	private Season season;
	private Regime regime;
	private Double price;

	public LodgingPrice(LodgingType lodgingType, Season season, Regime regime,Double price) {
		super();
		this.lodgingType = lodgingType;
		this.season = season;
		this.regime = regime;
		this.price = price;
	}

	public LodgingType getLodgingType() {
		return lodgingType;
	}

	public void setLodgingType(LodgingType lodgingType) {
		this.lodgingType = lodgingType;
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

	@Override
	public String toString() {
		return Objects.toStringHelper(getClass())
			.add("lodgingType", lodgingType)
			.add("season", season)
			.add("regime", regime)
			.add("price", price)
			.toString();
	}
	
	
}


