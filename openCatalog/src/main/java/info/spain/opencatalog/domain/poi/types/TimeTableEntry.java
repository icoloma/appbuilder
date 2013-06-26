package info.spain.opencatalog.domain.poi.types;

import java.util.Set;

import com.google.common.collect.Sets;



/**
 * 
 *  Horario de un establecimiento (museo, restaurante, etc)
	
	De Lunes a Sábado 
	De 10:00 a 20:00 
	De 10:00 a 19:00 
	Cerrado: 1 de enero, 1 de mayo y 25 de diciembre.
	
	06 ene De 10:00 a 14:00 
	24 dic De 10:00 a 14:00 
	31 dic De 10:00 a 14:00 
 *
 */
public class TimeTableEntry {
	  
	public static enum Day {MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY}
	
	
	/** Permite especificar días de la semana: Lunes,Miércoles,Viernes */
	private Set<Day> weekDays;

	/** Permite especificar restricciones, por ejemplo rango de horas: 08:00-1:00 y 14:00-20:00 */
	// TODO: Modificar para especificar rango de horas
    private Set<String> restriction;
	
	public Set<Day> getWeekDays() {
		return weekDays;
	}

	public TimeTableEntry setWeekDays(Set<Day> weekDays) {
		this.weekDays = weekDays;
		return this;
	}
	
	public TimeTableEntry setWeekDays(Day... weekDays) {
		return  setWeekDays(Sets.newHashSet(weekDays));
	}
	
	
	
	
	
	
	
	

}
