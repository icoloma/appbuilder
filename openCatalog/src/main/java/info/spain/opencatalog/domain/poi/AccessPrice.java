package info.spain.opencatalog.domain.poi;

import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.culture.PriceType;

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
public class AccessPrice {
	
	private Double price;
	
	/** Tipo de tarifa:  General, Adulto, Niño, Estudiante, ... */
	private Set<PriceType> priceTypes;
	
	/** Horarios aplicados a este precio: Lunes-Viernes de 18:00 a 20:00 */
	private Set<TimeTableEntry> timeTable;
	
	/** Texto adicional: "La Entrada es gratuíta para miembros de ... " */
	private I18nText observations;
	
	
	public Double getPrice() {
		return price;
	}
	public AccessPrice setPrice(Double price) {
		this.price = price;
		return this;
	}
	public Set<PriceType> getPriceTypes() {
		return priceTypes;
	}
	public AccessPrice setPriceTypes(Set<PriceType> priceTypes) {
		this.priceTypes = priceTypes;
		return this;
	}
	public AccessPrice setPriceTypes(PriceType... culturePriceTypes) {
		return setPriceTypes(Sets.newHashSet(culturePriceTypes));
	}
	public Set<TimeTableEntry> getTimeTable() {
		return timeTable;
	}
	
	public AccessPrice setTimetable(TimeTableEntry... timeTable) {
		this.timeTable = Sets.newHashSet(timeTable);
		return this;
	}
	
	public I18nText getObservations() {
		return observations;
	}
	public AccessPrice setObservations(I18nText observations) {
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
