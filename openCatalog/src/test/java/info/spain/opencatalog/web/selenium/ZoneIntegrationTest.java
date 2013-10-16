package info.spain.opencatalog.web.selenium;

import static org.junit.Assert.assertEquals;
import info.spain.opencatalog.domain.DummyUserFactory;
import info.spain.opencatalog.domain.User;
import info.spain.opencatalog.web.selenium.page.ZoneListPage;
import info.spain.opencatalog.web.selenium.page.ZonePage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class ZoneIntegrationTest extends AbstractIntegrationTest {
	
	@Before
	@Override
	public void init(){
		super.init();
		User root = DummyUserFactory.ROOT;
		loginAs(root);
	}
	
	@After
	@Override
	public void tearDown(){
		logout();
		super.tearDown();
	}
	
	@Test
	public void CRUDZone(){
		String url = config.getProperty("baseUrl") + "/admin/zone/new";
		log.trace("url:" + url);
		driver.get(url);
		createZone(driver);
		updateZone(driver);
		deleteZone(driver);
	}

	private void fillData(ZonePage page, String name, String description, String area1, String area2){
		page.fillData( name, description, area1, area2);
		page.drawRandomZone();
	}
	
	private void createZone(WebDriver driver){
		log.trace("create ZONE");
		ZonePage page = new ZonePage(driver);
		fillData(page,"Tenerife", "Isla de Tenerife", null, null);
		page.save();
		assertEquals("El elemento se creó correctamente.", page.getAlertMessage());
	}
	
	private void updateZone(WebDriver driver){
		log.trace("update ZONE");
		ZonePage page = new ZonePage(driver);
		fillData(page,"Gran Canaria", "Isla de Gran Canaria", null, null);
		page.save();
		assertEquals("El elemento se actualizó correctamente.", page.getAlertMessage());
	}
	
	private void deleteZone(WebDriver driver){
		log.trace("delete ZONE");
		ZonePage page = new ZonePage(driver);
		ZoneListPage list = page.delete();
		assertEquals("El elemento se eliminó correctamente.", list.getAlertMessage());
	}
	
	


}
