package info.spain.opencatalog.domain.poi.types;



/**
 * Representa un día en formato día/mes
 */
public class TimeTableDay {
	
	private static final int[] MAX_DAY_MONTHS  = {31,29,31,30,31,30,31,31,30,31,30,31};

	/** day of month 1..31 */
	private Integer day;
	
	/** 1..12  */
	private Integer month;
	
	public TimeTableDay(){}
	public TimeTableDay(Integer day, Integer month){
		if (day == null || day < 0 || month == null || month < 0 || day > MAX_DAY_MONTHS[month-1] ){
			throw new IllegalArgumentException("Invalid day/month values : " + day + "/" + month);
		}
		this.day = day;
		this.month = month;
	}
	
	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}
	
	@Override
	public String toString() {
		return day + "/" + month;
				
			
	}
	
	
	
}
