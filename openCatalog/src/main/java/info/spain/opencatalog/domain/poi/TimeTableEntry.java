package info.spain.opencatalog.domain.poi;

import java.util.regex.Pattern;

import org.springframework.data.annotation.Transient;

import com.google.common.base.Objects;



/**
* <pre> 
*    Horario de un establecimiento (museo, restaurante, etc)
*    
*   (intervalo_validez)?(dia,?)+=(hora,?)*
*    
*   intervalo_validez: 	\[fecha-fecha\] 					Fecha de inicio y fecha de fin de validez.
*	dia:				Mon|Tue|Wed|Thu|Fri|Sat|Sun|fecha 	Un día de la semana (lunes a domingo) o una fecha específica.
*	fecha:				\d\d\d\d 							Una fecha específica en formato ddMM, dos dígitos para el día (01-31) seguidos de dos dígitos para el mes (01-12)
*	hora:				\d\d:\d\d							Hora de apertura en formato HH:mm, dos dígitos para la hora (00-23) seguidos de dos puntos y dos dígitos para los minutos (00-59)
*
*	Ejemplos:
*	De Lunes a Viernes de 9 a 2: Mon,Tue,Wed,Thu,Fri=09:00-14:00
*	Domingos cerrado: Sun=
*	Cerrado el 24 de Diciembre y 6 de Enero: 2412,0601=
*	De Enero a Julio, Lunes a Viernes de 9 a 1 y de 5 a 7: [0101-0107]Mon,Tue,Wed,Thu,Fri=09:00-13:00,15:00-19:00
* </pre>
*/
public class TimeTableEntry {

	static final String HOUR= "([01][0-9]|2[0-3]):[0-5][0-9]"; 				// hh:mm
	static final String HOUR_RANGE = "(" + HOUR + "-" + HOUR + ")";			// hh:mm-hh:mm
	static final String DAYMONTH = "(0[1-9]|[12][0-9]|3[01])";  			// dd
	static final String MONTH = "(0[1-9]|1[012])";        					// MM
	static final String DATE = "(" + DAYMONTH + MONTH + ")";				// ddMM
	static final String DATE_RANGE =  "(\\[" + DATE + "-" + DATE +"\\])"; 	// [ddMM-ddMM]
	static final String WDAY = "(Mon|Tue|Wed|Thu|Fri|Sat|Sun)";	
	static final String DAY = "(" + WDAY + "|" + DATE + ")";				// día de semana o día concreto (con mes)
	static final String DAYS = "(" + DAY + "(," + DAY + ")*)";				// (dia,?) 
	
	public static final String PERIOD_REGEX = "(" + DATE_RANGE + "|" + DAYS + "|" + DATE_RANGE + DAYS +")=" + "(" + HOUR_RANGE + "(," + HOUR_RANGE + ")*?)?";
	
	@Transient
	private final Pattern pattern = Pattern.compile(TimeTableEntry.PERIOD_REGEX);
	
	/** Expresión regular que indica el periodo que aplica */
	private String period;
	
	public String getPeriod() {
		return period;
	}
	
	public TimeTableEntry(String period) {
		super();
		this.period = period;
		if (!pattern.matcher(period).matches()) {
			throw new IllegalArgumentException("period expression doesn't match " + PERIOD_REGEX);
		}
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(getClass())
			.add("period",period)
			.toString();
	}

}
