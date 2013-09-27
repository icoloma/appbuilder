package info.spain.opencatalog.domain;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
import org.junit.Test;

public class GeoLocationTest {
	
	@Test
	public void testEquals(){
		GeoLocation g1 = new GeoLocation().setLat(1d).setLng(1d);
		GeoLocation g2 = new GeoLocation().setLat(2d).setLng(2d);
		GeoLocation g22 = new GeoLocation().setLat(2d).setLng(2d);
		GeoLocation g3 = new GeoLocation().setLat(1d).setLng(2d);
		GeoLocation empty = new GeoLocation();
			
		assertFalse(g1.equals(new Object()));
		assertFalse(g1.equals(null));
		assertFalse(g1.equals(empty));
		assertFalse(g1.equals(g3));
		
		
		assertTrue(g1.equals(g1));
		assertTrue(g1.equals(g1));
		assertTrue(g2.equals(g22));
		
	}
	
	
	@Test
	public void testHashCode(){
		GeoLocation g1 = new GeoLocation().setLat(1d).setLng(1d);
		GeoLocation g2 = new GeoLocation().setLat(2d).setLng(2d);
		assertFalse( g1.hashCode() == g2.hashCode());
	}
	
}
