package info.spain.opencatalog.domain.poi.lodging;

import com.google.common.base.Objects;


/**
 * Precio de un servicio en función de la temporada y el régimen de alojamiento  
 *
 * Ej.: hab-doble, temporada alta, media pensión, 100
 *
 */
public class RoomPrice {
	
	private RoomType roomType;  // tipo de alojamiento ( hotel:suite|hab-doble|... ; camping : Autocaravana|Tienda|...
	private Season season;
	private Regime regime;
	private Double price;

	public RoomPrice(RoomType roomType, Season season, Regime regime, Double price) {
		super();
		this.roomType = roomType;
		this.season = season;
		this.regime = regime;
		this.price = price;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
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
			.add("lodgingType", roomType)
			.add("season", season)
			.add("regime", regime)
			.add("price", price)
			.toString();
	}
	
	
}


