package info.spain.opencatalog.domain.poi.lodging;

import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.Price;

import com.google.common.base.Objects;


/**
 * Precio de un servicio en función de la temporada y el régimen de alojamiento  
 *
 * Ej.: hab-doble, temporada alta, media pensión, 100
 *
 */
public class RoomPrice extends Price {
	
	private RoomType roomType;  // tipo de alojamiento ( hotel:suite|hab-doble|... ; camping : Autocaravana|Tienda|...
	private Meal meal;
	

	public RoomPrice() {
		super();
	}
	
	public RoomPrice(RoomType roomType,  Meal meal, Double price) {
		super();
		this.roomType = roomType;
		this.meal = meal;
		this.price = price;
		
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal regime) {
		this.meal = regime;
	}
	
	
	@Override
	public RoomPrice setObservations(I18nText observations) {
		return (RoomPrice) super.setObservations(observations);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(getClass())
			.add("lodgingType", roomType)
			.add("regime", meal)
			.add("price", price)
			.toString();
	}
	
	
}


