package info.spain.opencatalog.domain.poi;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import info.spain.opencatalog.domain.Address;

import org.junit.Test;

public class BasicPoiTest {
	
	@Test
	public void testDeleteEmptyEntries(){
		
		BasicPoi source = new BasicPoi()
			.setData("a", "A")
			.setData("empty1", "")
			.setData("empty2", null);
		
		BasicPoi copy = new BasicPoi()
			.copyData(source);

		assertEquals(1, copy.getData().size());
	}
	
	@Test
	public void testInitCollectionsNotNull() {
		BasicPoi poi = new BasicPoi();
		poi.initCollections();
		assertNotNull(poi.address);
		assertNotNull(poi.flags);
		assertNotNull(poi.timetable);
		assertNotNull(poi.prices);
		assertNotNull(poi.languages);
		assertNotNull(poi.address);
		assertNotNull(poi.location);
	}
	
	@Test
	public void testInitCollectionsWithAddressNotNull() {
		BasicPoi poi = new BasicPoi();
		poi.setAddress(new Address().setAdminArea1("foo"));
		poi.initCollections();
		assertNotNull(poi.address);
		assertNotNull(poi.flags);
		assertNotNull(poi.timetable);
		assertNotNull(poi.prices);
		assertNotNull(poi.languages);
		assertNotNull(poi.address);
		assertNotNull(poi.location);
	}

}
