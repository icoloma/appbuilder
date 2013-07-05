package info.spain.opencatalog.domain.poi;

import info.spain.opencatalog.domain.I18nText;

import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;

/**
 * Precios de acceso 
 * 
 * Permite especificar:
 * 	 - El tipo de tarifa :  General, Adulto, Niño, Estudiante, ...
 *   - Horarios aplicados a este precio: Lunes-Viernes de 18:00 a 20:00
 *   - Texto adicional: "La Entrada es gratuíta para miembros de ... " 
 */
public class Price {
	
	protected Double price;
	
	/** Tipo de tarifa:  General, Adulto, Niño, Estudiante, ... */
	protected Set<PriceType> priceTypes;
	
	/** Horarios aplicados a este precio: Lunes-Viernes de 18:00 a 20:00 */
	protected Set<TimeTableEntry> timeTable;
	
	/** Texto adicional: "La Entrada es gratuíta para miembros de ... " */
	protected I18nText observations;
	
	
	public Double getPrice() {
		return price;
	}
	public Price setPrice(Double price) {
		this.price = price;
		return this;
	}
	public Set<PriceType> getPriceTypes() {
		return priceTypes;
	}
	
	public Price setPriceTypes(PriceType... culturePriceTypes) {
		this.priceTypes = Sets.newHashSet(culturePriceTypes);
		return this;
	}
	public Set<TimeTableEntry> getTimeTable() {
		return timeTable;
	}
	
	public Price setTimetable(TimeTableEntry... timeTable) {
		this.timeTable = Sets.newHashSet(timeTable);
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
			.add("priceTypes", priceTypes)
			.add("timeTable", timeTable)
			.add("observations", observations)
			.toString();
	}
	
}
