package info.spain.opencatalog.domain.poi.types.culture;

import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.types.TimeTableEntry;

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
public class CulturePrice {
	
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
	public CulturePrice setPrice(Double price) {
		this.price = price;
		return this;
	}
	public Set<PriceType> getPriceTypes() {
		return priceTypes;
	}
	public CulturePrice setPriceTypes(Set<PriceType> priceTypes) {
		this.priceTypes = priceTypes;
		return this;
	}
	public CulturePrice setPriceTypes(PriceType... culturePriceTypes) {
		return setPriceTypes(Sets.newHashSet(culturePriceTypes));
	}
	public Set<TimeTableEntry> getTimeTable() {
		return timeTable;
	}
	
	public CulturePrice setTimeTable(Set<TimeTableEntry> timeTable) {
		this.timeTable = timeTable;
		return this;
	}
	public CulturePrice setTimetable(TimeTableEntry... timeTable) {
		return setTimeTable(Sets.newHashSet(timeTable));
	}
	public I18nText getObservations() {
		return observations;
	}
	public CulturePrice setObservations(I18nText observations) {
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
