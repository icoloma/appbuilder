package info.spain.opencatalog.web.selenium;

import static org.junit.Assert.assertEquals;
import info.spain.opencatalog.web.selenium.page.ZoneListPage;
import info.spain.opencatalog.web.selenium.page.ZonePage;

import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/properties-config.xml")
@ActiveProfiles("dev")
public class ZoneIntegrationTest {
	
	@Autowired
	@Qualifier("configProps")
	Properties config;

	WebDriver driver;

	@Before
	public void init(){
		driver = new FirefoxDriver();
	}
	
	@After 
	public void tearDown(){
		driver.close();
	}
	
	@Test
	public void CRUDZone(){
		driver.get(config.getProperty("baseUrl") + "/admin/zone/new");
		createZone(driver);
		updateZone(driver);
		deleteZone(driver);
	}

	private void fillData(ZonePage page, String name, String description, String area1, String area2){
		page.fillData( name, description, area1, area2);
		page.drawRandomZone();
	}
	
	private void createZone(WebDriver driver){
		ZonePage page = new ZonePage(driver);
		fillData(page,"Tenerife", "Isla de Tenerife", null, null);
		page.save();
		assertEquals("El elemento se creó correctamente.", page.getAlertMessage());
	}
	
	private void updateZone(WebDriver driver){
		ZonePage page = new ZonePage(driver);
		fillData(page,"Gran Canaria", "Isla de Gran Canaria", null, null);
		page.save();
		assertEquals("El elemento se actualizó correctamente.", page.getAlertMessage());
	}
	
	private void deleteZone(WebDriver driver){
		ZonePage page = new ZonePage(driver);
		ZoneListPage list = page.delete();
		assertEquals("El elemento se eliminó correctamente.", list.getAlertMessage());
	}
	
	


}
