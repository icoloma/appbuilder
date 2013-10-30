package integrationTests.page.poi;

import integrationTests.page.AbstractPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BeachReadOnlyPage extends AbstractPage {
	
	private static final String HEADER = "Playa";
	
	private WebElement crearRevisionButton = driver.findElement(By.partialLinkText("Crear revisión"));
	
	
	public BeachReadOnlyPage(WebDriver driver){
		super(driver, HEADER); 
	}
	
	public BeachPage crearRevision(){
		crearRevisionButton.click();
		return new BeachPage(driver);
	}
	
	

}
