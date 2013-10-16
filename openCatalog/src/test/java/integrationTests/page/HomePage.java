package integrationTests.page;

import org.openqa.selenium.WebDriver;

public class HomePage extends AbstractPage {
	
	public static final String HEADER ="Catálogo openData";
	
	public HomePage(WebDriver driver){
		super(driver, HEADER); 
	}
	
	

}
