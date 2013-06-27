package info.spain.opencatalog.domain.poi.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Rango de horas
 */
public class HourRange {
	
	private static final String PATTERN = "(\\d\\d):(\\d\\d)";
	private static Pattern pattern = Pattern.compile(PATTERN);
	
	private String start;
	private String end;
	
		public HourRange(){}
	
	/**
	 * @param start hh:mm
	 * @param end	hh:mm
	 */
	public HourRange(String start, String end) {
		this.start = checkHour(start);
		this.end = checkHour(end);
	}

	private String checkHour(String hour){
		Matcher matcher = pattern.matcher(hour);
		if (!matcher.find()){
			throw new IllegalArgumentException("Valor " + hour + " con formato incorrecto; debe de ser " + PATTERN);
		}
		Integer shour = Integer.valueOf(matcher.group(1));
		Integer smin = Integer.valueOf(matcher.group(2));
		if (shour <0 || shour > 23 || smin < 0 || smin > 59){
			throw new IllegalArgumentException("Invalid hour :" + hour);
		}
		return hour;
	}
	public String getStart() {
		return start;
	}
	public String getEnd() {
		return end;
	}
	
	
	
	
	
		
}
