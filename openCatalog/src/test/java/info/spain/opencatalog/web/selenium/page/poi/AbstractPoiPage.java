package info.spain.opencatalog.web.selenium.page.poi;

import info.spain.opencatalog.domain.poi.ContactInfo;
import info.spain.opencatalog.web.selenium.page.AbstractPage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class AbstractPoiPage extends AbstractPage {
	
	public AbstractPoiPage(WebDriver driver, String header) {
		super(driver, header);
	}
	
	// Page Tabs
	private WebElement locationTab = driver.findElement(By.partialLinkText("Ubicación"));
	private WebElement flagsTab = driver.findElement(By.partialLinkText("Etiquetas"));
	private WebElement timeTableTab = driver.findElement(By.partialLinkText("Horarios"));
	private WebElement preciosTab = driver.findElement(By.partialLinkText("Precios"));
	private WebElement contactInfoTab = driver.findElement(By.partialLinkText("Información de contacto"));
	
	// Common page inputs fields
	private WebElement name_ES = driver.findElement(By.id("name.es"));
	private WebElement description_ES = driver.findElement(By.id("description.es"));
	private WebElement contactInfo_phone = driver.findElement(By.id("contactInfo.phone"));
	private WebElement contactInfo_fax = driver.findElement(By.id("contactInfo.fax"));
	private WebElement contactInfo_url = driver.findElement(By.id("contactInfo.url"));
	private WebElement contactInfo_email = driver.findElement(By.id("contactInfo.email"));
	private WebElement contactInfo_reservation = driver.findElement(By.id("contactInfo.reservation"));
	private WebElement location  = driver.findElement(By.id("gsearcher"));
	
	// Buttons
	private WebElement addTimeTableButton = driver.findElement(By.className("addTimeTableEntry"));
	private WebElement addPriceButton = driver.findElement(By.className("addPrice"));
	private WebElement saveButton = driver.findElement(By.className("saveButton"));
	private WebElement deleteButton = driver.findElement(By.className("deleteButton"));
	
	

	public void showLocationTab(){
		locationTab.click();
	}
	public void showFlagsTab(){
		flagsTab.click();
	}
	public void showTimeTableTab(){
		timeTableTab.click();
	}
	public void showPricesTab(){
		preciosTab.click();
	}
	public void showContactInfoTab(){
		contactInfoTab.click();
	}
	
	public void setName(String value){
		log.trace("name: " + value);
		fill(name_ES, value);
	}
	
	public void setDescription(String value){
		log.trace("description: " + value);
		fill(description_ES, value);
	}

	public void setData(String id, String value){
		log.trace("data[" + id + "]:"+ value);
		fillById(id, value);
	}
	
	public void searchLocation(String locationText){
		log.trace("location : " + locationText);
		fillAutocomplete(location, locationText);
	}
	
	public void setContactInfo(ContactInfo contactInfo){
		if (contactInfo != null){
			if (contactInfo.getPhone() != null){
				log.trace("phone: " + contactInfo.getPhone());
				fill(contactInfo_phone, contactInfo.getPhone());
			}
			if (contactInfo.getFax() != null){
				log.trace("fax: " + contactInfo.getFax());
				fill(contactInfo_fax, contactInfo.getFax());
			}
			if (contactInfo.getEmail() != null){
				log.trace("email: " + contactInfo.getEmail());
				fill(contactInfo_email, contactInfo.getEmail());
			}
			if (contactInfo.getUrl() != null){
				log.trace("url: " + contactInfo.getUrl());
				fill(contactInfo_url, contactInfo.getUrl());
			}
			if (contactInfo.getReservation() != null){
				log.trace("contactInfo.reserervation: " + contactInfo.getReservation());
				fill(contactInfo_reservation, contactInfo.getReservation());
			}
		}
	}
	
	/**
	 * Select a POI flag
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
	 * add Timetable Entry
	 */
	public void addTimeTableEntry(String period){
		addTimeTableButton.click();
		List<WebElement> entries = driver.findElements(By.cssSelector("table.timetable tbody tr td input"));
		WebElement lastInput = entries.get(entries.size() - 1);
		lastInput.sendKeys(period);
		log.trace("timetable: " + period);
	}
	
	public void addPrice( String price, String priceType, String period, String observations){
		addPriceButton.click();
		List<WebElement> rows = driver.findElements(By.cssSelector("table.prices tbody tr"));
		WebElement lastRow = rows.get(rows.size() - 1);
		lastRow.findElement(By.className("price")).sendKeys(price);
		selectOption(lastRow.findElement(By.className("priceType")), priceType);
		lastRow.findElement(By.className("period")).sendKeys(period);
		lastRow.findElement(By.cssSelector(".priceObservation.es")).sendKeys(observations);
		log.trace("price: " + price + ", " + priceType + ", " + period + ", " + observations);
	}
	
	
	/**
	 * save
	 */
	public AbstractPoiPage save(){
		saveButton.click();
		return this;
	}
	
	/**
	 * delete
	 */
	protected void deletePoi(){
		deleteButton.click();
	}
	

}
