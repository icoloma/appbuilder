package info.spain.opencatalog.domain;

import junit.framework.Assert;

import org.junit.Test;

public class ZoneTest {
	
	
	@Test
	public void testPointInsideZone(){
		Zone madrid = ZoneFactory.ZONE_MADRID_CENTRO;
		Assert.assertTrue(madrid.contains(DummyPoiFactory.GEO_RETIRO));
		Assert.assertTrue(madrid.contains(DummyPoiFactory.GEO_SOL));
		Assert.assertTrue(madrid.contains(DummyPoiFactory.GEO_CASA_CAMPO));
		Assert.assertFalse(madrid.contains(DummyPoiFactory.GEO_TEIDE));
		Assert.assertFalse(madrid.contains(DummyPoiFactory.GEO_ALASKA));
	}

	@Test
	/**
	 * Formamos una zona en forma de pajarita que deja el retiro dentro de los límites (NW,NE,SE,SO) pero fuera del polígono
	 * Puerta del Sol cae dentro
	 */
	public void testPointInsideComplexPolygon(){
		
		Zone zone= ZoneFactory.ZONE_COMPLEX;
		
		Assert.assertTrue(zone.contains(DummyPoiFactory.GEO_SOL));
		
		Assert.assertFalse(zone.contains(DummyPoiFactory.GEO_RETIRO));
		Assert.assertFalse(zone.contains(DummyPoiFactory.GEO_CASA_CAMPO));
		Assert.assertFalse(zone.contains(DummyPoiFactory.GEO_TEIDE));
		Assert.assertFalse(zone.contains(DummyPoiFactory.GEO_ALASKA));
	}
	
	
}
