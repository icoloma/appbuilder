package info.spain.opencatalog.domain.poi;

import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;



/**
* <pre> 
*    Horario de un establecimiento (museo, restaurante, etc)
*
*	Permite especificar d&iacute;as concretos de la semana y/o del mes as&iacute; como
*	los días de cierre. 
*		
*		Lunes, Mi&eacute;rcoles y Viernes
*			De 10:00 a 20:00 
*			De 10:00 a 19:00 
*	
*		06 ene De 10:00 a 14:00 
*		24 dic De 10:00 a 14:00 
*		31 dic De 10:00 a 14:00 
*
*	Cerrado: 
*		1 de enero, 
*		1 de mayo, 
*		25 de diciembre.
*	
* </pre>
*/
public class TimeTableEntry {
	  
	public static enum WeekDay {MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY}
	
	
	/** Días de la semana en concreto: Lunes,Miércoles,Viernes */
	private Set<WeekDay> weekDays;
	
	/** Días concretos (no tiene en cuenta el año):  6 enero, 24 diciembre, 25 diciembre */
	private Set<TimeTableDay> days;
	
	/** Días que permanece cerrrado (no tiene en cuenta el año)*/
	private Set<TimeTableDay> closedDays;
	
	/** Permite especificar restricciones, por ejemplo rango de horas: 08:00-1:00 y 14:00-20:00 */
	// TODO: Modificar para especificar rango de horas
    private Set<HourRange> hourRange;
    
    
    public Set<HourRange> getHourRange() {
		return hourRange;
	}

	public TimeTableEntry setHourRange(Set<HourRange> hourRange) {
		this.hourRange = hourRange;
		return this;
	}
	public TimeTableEntry setHourRange(HourRange... hourRange) {
		return setHourRange(Sets.newHashSet(hourRange));
	}

	public Set<TimeTableDay> getDays() {
		return days;
	}

    public TimeTableEntry setDays(Set<TimeTableDay> days) {
		this.days = days;
		return this;
	}
    public TimeTableEntry setDays(TimeTableDay... days) {
		return setDays(Sets.newHashSet(days));
	}

	public Set<TimeTableDay> getClosedDays() {
		return closedDays;
	}

	
	public TimeTableEntry setClosedDays(Set<TimeTableDay> closedDays) {
		this.closedDays = closedDays;
		return this;
	}
	
			
	public TimeTableEntry setClosedDays(TimeTableDay... closedDays) {
		return setClosedDays(Sets.newHashSet(closedDays));
	}
	
	public Set<WeekDay> getWeekDays() {
		return weekDays;
	}

	public TimeTableEntry setWeekDays(Set<WeekDay> weekDays) {
		this.weekDays = weekDays;
		return this;
	}
	
	public TimeTableEntry setWeekDays(WeekDay... weekDays) {
		return  setWeekDays(Sets.newHashSet(weekDays));
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(getClass())
			.add("weekDays",weekDays)
			.add("days",days)
			.add("closedDays",closedDays)
			.add("hourRange",hourRange)
			.toString();
	}

	
	
	
	
	

}
