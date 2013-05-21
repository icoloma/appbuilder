package info.spain.opencatalog.converter;

import static junit.framework.Assert.assertEquals;
import info.spain.opencatalog.domain.GeoLocation;

import org.junit.Test;

import com.mongodb.BasicDBList;

/**
 * GeoLocation Converter tests
 * @author ehdez
 */

public class GeoLocationConverterTest {
	
	private static final double LAT = 111.11;
	private static final double LNG = 222.22;
	@Test
	public void testReader(){
		GeoLocationReaderConverter converter = new GeoLocationReaderConverter();
		BasicDBList dbList = new BasicDBList();
		dbList.add(LNG);
		dbList.add(LAT);
		
		GeoLocation result =  converter.convert(dbList);
		
		assertEquals(LNG, result.getLng());
		assertEquals(LAT, result.getLat());
		
	}

	@Test
	public void testWriter(){
		GeoLocationWriterConverter converter = new GeoLocationWriterConverter();
		GeoLocation geo = new GeoLocation().setLat(LAT).setLng(LNG);
		BasicDBList dbList = (BasicDBList) converter.convert(geo);
		assertEquals(geo.getLng(), dbList.get(0));
		assertEquals(geo.getLat(), dbList.get(1));
	}


}
