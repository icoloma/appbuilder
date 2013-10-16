package integrationTests.page.poi;

import org.openqa.selenium.WebDriver;

public class BeachPage extends AbstractPoiPage {
	
	private static final String HEADER = "Playa";
	
	public BeachPage(WebDriver driver){
		super(driver,HEADER); 
	}
	
	/**
	 * delete
	 */
	public PoiListPage delete(){
		super.deletePoi();
		return new PoiListPage(driver);
	}
	

}
