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

	public void showLocationTab(){
		driver.findElement(By.partialLinkText("Ubicación")).click();
	}
	public void showFlagsTab(){
		driver.findElement(By.partialLinkText("Etiquetas")).click();
	}
	public void showTimeTableTab(){
		driver.findElement(By.partialLinkText("Horarios")).click();
	}
	public void showPricesTab(){
		driver.findElement(By.partialLinkText("Precios")).click();
	}
	public void showContactInfoTab(){
		driver.findElement(By.partialLinkText("Información de contacto")).click();
	}
	
	public void setName(String value){
		log.trace("name: " + value);
		fillById("name.es", value);
	}
	
	public void setDescription(String value){
		log.trace("description: " + value);
		fillById("description.es", value);
	}

	public void setData(String id, String value){
		log.trace("data[" + id + "]:"+ value);
		fillById(id, value);
	}
	
	public void searchLocation(String location){
		log.trace("location : " + location);
		fillAutocomplete("gsearcher", location);
	}
	
	public void setContactInfo(ContactInfo contactInfo){
		if (contactInfo != null){
			if (contactInfo.getPhone() != null){
				log.trace("phone: " + contactInfo.getPhone());
				fillById("contactInfo.phone", contactInfo.getPhone());
			}
			if (contactInfo.getFax() != null){
				log.trace("fax: " + contactInfo.getFax());
				fillById("contactInfo.fax", contactInfo.getFax());
			}
			if (contactInfo.getEmail() != null){
				log.trace("email: " + contactInfo.getEmail());
				fillById("contactInfo.email", contactInfo.getEmail());
			}
			if (contactInfo.getUrl() != null){
				log.trace("url: " + contactInfo.getUrl());
				fillById("contactInfo.url", contactInfo.getUrl());
			}
			if (contactInfo.getReservation() != null){
				log.trace("contactInfo.reserervation: " + contactInfo.getReservation());
				fillById("contactInfo.reservation", contactInfo.getReservation());
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
		driver.findElement(By.className("addTimeTableEntry")).click();
		List<WebElement> entries = driver.findElements(By.cssSelector("table.timetable tbody tr td input"));
		WebElement lastInput = entries.get(entries.size() - 1);
		lastInput.sendKeys(period);
		log.trace("timetable: " + period);
	}
	
	public void addPrice( String price, String priceType, String period, String observations){
		driver.findElement(By.className("addPrice")).click();
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
		driver.findElement(By.className("saveButton")).click();
		return this;
	}
	
	/**
	 * delete
	 */
	protected void deletePoi(){
		driver.findElement(By.className("deleteButton")).click();
	}
	

}
