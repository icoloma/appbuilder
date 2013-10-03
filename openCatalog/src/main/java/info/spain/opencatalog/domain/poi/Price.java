package info.spain.opencatalog.domain.poi;

import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.lodging.RoomPrice;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.Objects;

/**
 * Precios de acceso 
 * 
 * Permite especificar:
 * 	 - El tipo de tarifa :  General, Adulto, Niño, Estudiante, ...
 *   - Horarios aplicados a este precio: Lunes-Viernes de 18:00 a 20:00
 *   - Texto adicional: "La Entrada es gratuíta para miembros de ... " 
 */

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type", defaultImpl=Price.class)
@JsonSubTypes({
	@JsonSubTypes.Type(value=Price.class, name="BASE"),
    @JsonSubTypes.Type(value=RoomPrice.class, name="ROOM")
})
public class Price {
	
	protected Double price;
	
	/** Tipo de tarifa:  General, Adulto, Niño, Estudiante, ... */
	protected PriceType priceType;
	
	/** Horario aplicado a este precio: Lunes-Viernes de 18:00 a 20:00 */
	protected TimeTableEntry timeTable = new TimeTableEntry();
	
	/** Texto adicional: "La Entrada es gratuíta para miembros de ... " */
	protected I18nText observations = new I18nText();
	
	
	public Double getPrice() {
		return price;
	}
	public Price setPrice(Double price) {
		this.price = price;
		return this;
	}
	public PriceType getPriceType() {
		return priceType;
	}
	
	public Price setPriceType(PriceType priceType) {
		this.priceType = priceType;
		return this;
	}
	public TimeTableEntry getTimeTable() {
		return timeTable;
	}
	
	public Price setTimetable(TimeTableEntry timeTable) {
		this.timeTable = timeTable;
		return this;
	}
	
	public I18nText getObservations() {
		return observations;
	}
	public Price setObservations(I18nText observations) {
		this.observations = observations;
		return this;
	}
	
	
	@Override
	public String toString() {
		return Objects.toStringHelper(getClass())
			.add("price", price)
			.add("priceType", priceType)
			.add("timeTable", timeTable)
			.add("observations", observations)
			.toString();
	}
	
}
