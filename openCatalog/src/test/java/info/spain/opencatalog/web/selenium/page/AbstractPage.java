package info.spain.opencatalog.web.selenium.page;

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
	public void fillAutocomplete(String id, String text){
		WebElement input = driver.findElement(By.id(id));
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
	
	
	/**
	 * Select poi flag
	 * @param flag
	 */
	public void selectFlag(String flag){
		List<WebElement> groups = driver.findElements(By.cssSelector("a.accordion-toggle"));
		for (WebElement group : groups) {
			group.click();
			WebElement accordionBody = driver.findElement(By.cssSelector(".accordion-body.in.collapse"));
			wait.until(ExpectedConditions.visibilityOf(accordionBody));
			List<WebElement> inputs = accordionBody.findElements(By.name("flags"));
			for (WebElement input : inputs) {
				if (input.getAttribute("value").equals(flag)){
					input.click();
				}
			}
			group.click();
			
		}
	}
	
	/**
	 * Add a POI timetable entry
	 * @param period
	 */
	public void addTimeTableEntry(String period){
		driver.findElement(By.className("addTimeTableEntry")).click();
		List<WebElement> entries = driver.findElements(By.cssSelector("table.timetable tbody tr td input"));
		WebElement lastInput = entries.get(entries.size() - 1);
		lastInput.sendKeys(period);
	}
	
	/**
	 * Add a POI price
	 * @param price
	 * @param priceType
	 * @param period
	 * @param observations
	 */
	public void addPrice( String price, String priceType, String period, String observations){
		driver.findElement(By.className("addPrice")).click();
		List<WebElement> rows = driver.findElements(By.cssSelector("table.prices tbody tr"));
		WebElement lastRow = rows.get(rows.size() - 1);
		lastRow.findElement(By.className("price")).sendKeys(price);
		selectOption(lastRow.findElement(By.className("priceType")), priceType);
		lastRow.findElement(By.className("period")).sendKeys(period);
		lastRow.findElement(By.cssSelector(".priceObservation.es")).sendKeys(observations);
	
		
	}
	
	

}
