package info.spain.opencatalog.web.selenium;


import static org.junit.Assert.assertEquals;
import info.spain.opencatalog.domain.DummyPoiFactory;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.web.selenium.page.poi.PoiListPage;
import info.spain.opencatalog.web.selenium.page.poi.BeachPage;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class BeachIntegrationTest extends AbstractPoiIntegrationTest{
	
	@Test
	public void CRUDBeach(){
		driver.get(config.getProperty("baseUrl") + "/admin/poi/new/BEACH");
		createBeach(driver);
		updateBeach(driver);
		deleteBeach(driver);
	}

	private void createBeach(WebDriver driver){
		BeachPage page = new BeachPage(driver);
		BasicPoi beach = DummyPoiFactory.BEACH;
		fillAllInfo(page, beach);
		page.save();
		assertEquals("El elemento se creó correctamente.", page.getAlertMessage());
	}
	
	
	private void updateBeach(WebDriver driver){
		BeachPage page = new BeachPage(driver);
		BasicPoi beach = new BasicPoi();
		beach.setName(new I18nText());
		beach.setDescription(new I18nText());
		beach.getName().setEs("updated beach name");
		beach.getDescription().setEs("updated beach description");
		fillNameData(page, beach);
		page.save();
		assertEquals("El elemento se actualizó correctamente.", page.getAlertMessage());
	}
	
	
	private void deleteBeach(WebDriver driver){
		BeachPage page = new BeachPage(driver);
		PoiListPage list = page.delete();
		assertEquals("El elemento se eliminó correctamente.", list.getAlertMessage());
	}


}
