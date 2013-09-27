package info.spain.opencatalog.domain.poi;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;

public class TimeTableEntryTest {
	
//	assertTrue(p.matcher("12/05,06").matches());		// Dias de un mes concreto
//	assertTrue(p.matcher("12/Mon").matches());			// Dia de la semana de un mes concreto
//	assertTrue(p.matcher("12/Mon,Tue").matches());		// Dias de la semana de un mes concreto
//	assertTrue(p.matcher("01-12/02").matches());		// Dia de mes de un rango
//	assertTrue(p.matcher("01-12/02,31").matches());		// Dias de mes de un rango
//	assertTrue(p.matcher("01-12/Mon").matches());		// Dia de semana de un rango
//	assertTrue(p.matcher("01-12/Mon,Tue").matches());   // Dias de semana de un rango
	

	@Test
	public void testTimeTable() {
		Pattern p = Pattern.compile(TimeTableEntry.PERIOD_REGEX);
		assertTrue( p.matcher("").matches());						// abierto 24/365
		assertTrue( p.matcher("2412=").matches());					// cerrado día concreto
		assertTrue( p.matcher("[0101-0107]=").matches());			// cerrado rango
		assertTrue( p.matcher("Mon,Tue=").matches());				// cerrado lunes y martes
		assertTrue( p.matcher("Mon,Tue=09:00-14:00").matches());	// abierto lunes y martes
		assertTrue( p.matcher("[0101-0107]Mon,Tue=").matches());	// cerrado lunes y martes en rango			
		assertTrue( p.matcher("[0101-0107]Mon,Tue,Wed,Thu,Fri=09:00-13:00").matches());
		assertTrue( p.matcher("[0101-0107]Mon,Tue,Wed,Thu,Fri=09:00-13:00,14:00-20:00").matches());
		assertTrue( p.matcher("2412=09:00-13:00,14:00-20:00").matches());	// horario para día en concreto
		assertFalse(p.matcher("MonTue").matches());
		
		// Valores no validos
		assertFalse(p.matcher("13/01").matches());					// Mes inválido
		assertFalse(p.matcher("01-12/Mon,Tue,01,02").matches());	// Mezcla días 
		assertFalse(p.matcher("13m").matches());	// Mes 13
		
	}

	@Test
	public void testRange() {
		Pattern p = Pattern.compile(TimeTableEntry.DATE_RANGE);
		assertTrue(p.matcher("[0112-0112]").matches());	    // Día 12 de mes
		assertTrue(p.matcher("[0101-3101]").matches());	    // Enero
		
	}
	

	@Test
	public void testDays() {
		Pattern p = Pattern.compile(TimeTableEntry.DAYS);
		assertTrue( p.matcher("Mon").matches());
		assertTrue( p.matcher("3101").matches());
		assertTrue( p.matcher("Mon,Tue,Wed").matches());
		assertFalse(p.matcher("").matches());
		assertFalse(p.matcher("MonTue").matches());
		assertFalse(p.matcher("01,02,03").matches());		// solo días del mes - formato incorrecto
		assertFalse(p.matcher("12/05").matches());			// Formato incorrecto
	}
	
	@Test
	public void testDate() {
		Pattern p = Pattern.compile(TimeTableEntry.DATE);
		assertTrue( p.matcher("0101").matches());
		assertTrue( p.matcher("3112").matches()); 	
		assertFalse(p.matcher("3214").matches());
		assertFalse(p.matcher("400").matches());
	}
	
	@Test
	public void testHour() {
		Pattern p = Pattern.compile(TimeTableEntry.HOUR);
		assertTrue( p.matcher("00:00").matches());
		assertTrue( p.matcher("23:59").matches()); 	
		assertFalse(p.matcher("24:00").matches());
		assertFalse(p.matcher("4:00").matches());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testValidateInvalidPeriod(){
		TimeTableEntry.validate("INVALID");
	}
		

	/**
	 * only for cobertura
	 */
	@Test 
	public void testEquals(){
		TimeTableEntry t1 = new TimeTableEntry().setPeriod("2412=");
		TimeTableEntry t2 = new TimeTableEntry().setPeriod("2412=");
		TimeTableEntry t3 = new TimeTableEntry().setPeriod("0101=");
		TimeTableEntry t4 = new TimeTableEntry();
		TimeTableEntry t5 = new TimeTableEntry();
		assertTrue(t1.equals(t1));
		assertTrue(t1.equals(t2));
		assertTrue(t4.equals(t5));
		assertFalse(t4.equals(t1));
		assertFalse(t1.equals(t3));
		assertFalse(t1.equals(null));
		assertFalse(t1.equals(new Object()));
	}
	
	/**
	 * only for cobertura
	 */
	@Test 
	public void testHashCode(){
		assertTrue(new TimeTableEntry().setPeriod("2412=").hashCode() != new TimeTableEntry().hashCode());
	}

}
