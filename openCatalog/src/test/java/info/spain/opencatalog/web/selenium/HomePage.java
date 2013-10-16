package info.spain.opencatalog.web.selenium;

import info.spain.opencatalog.web.selenium.page.AbstractPage;

import org.openqa.selenium.WebDriver;

public class HomePage extends AbstractPage {
	
	public static final String HEADER ="Catálogo openData";
	
	public HomePage(WebDriver driver){
		super(driver, HEADER); 
	}
	
	

}
