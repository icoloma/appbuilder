package integrationTests.page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractPage {
	
	private static final int TIME_OUT = 10;

	protected Logger log = LoggerFactory.getLogger(getClass());
	protected WebDriver driver;
	protected WebDriverWait wait;
	
	
	public AbstractPage(WebDriver driver, String header){
		this.driver = driver;
		String h1Text = getH1().getText();
		if (! header.equals(h1Text)){
			throw new IllegalStateException("<h1/> tag does not match: current=" + h1Text + "; expected=" + header);
		}
		wait = new WebDriverWait(driver, TIME_OUT); 
	}
	
	
	/** 
	 * @return H1 of the page 
	 */
	private WebElement getH1(){
		return driver.findElement(By.tagName("h1"));
	}
	
	
	/**
	 * Fill a field by its id
	 * @param id
	 * @param value
	 */
	protected void fillById(String id, String value) {
		WebElement element = driver.findElement(By.id(id));
		if (element.getTagName().equals("select")){
			selectOption(driver.findElement(By.id(id)),value);
		} else {
			element.sendKeys(value);
		}
	}
	
	protected void fill(WebElement element, String value) {
		if (element.getTagName().equals("select")){
			selectOption(element,value);
		} else {
			element.sendKeys(value);
		}
	}
	
	
	/**
	 * Selects an option of a select input 
	 * @param select
	 * @param value
	 */
	protected void selectOption(WebElement select, String value){
		List<WebElement> options = select.findElements(By.tagName("option"));
		for(WebElement option : options){
		    if(option.getAttribute("value").equals(value)){
		        option.click();
		        break;
		    }
		}
	}
	
	/**
	 * @return The alert message of the page
	 */
	public String getAlertMessage(){
		return driver.findElement(By.cssSelector(".alert span")).getText();
	}
	
	/**
	 *  Fill a google autocomplete field and select the first result
	 */
	public void fillAutocomplete(WebElement input, String text){
		input.click();
		log.trace("filling zone name :" + text);
		input.sendKeys(text);
		
		selectFirstGoogleAutoCompleteResult();
	}

	private void selectFirstGoogleAutoCompleteResult(){
		String firstResultCssSelector = "ul.ui-autocomplete li";
		By selector = By.cssSelector(firstResultCssSelector);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(selector));
		log.trace("selecting first google result : " + driver.findElement(selector).getText());
		driver.findElements(selector).get(0).click();
	}
	


}
