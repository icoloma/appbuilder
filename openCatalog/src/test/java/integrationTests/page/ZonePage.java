package integrationTests.page;

import org.apache.commons.lang.math.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ZonePage extends AbstractPage {
	
	private static final String HEADER = "Zona (Admin)";
	
	
	public ZonePage(WebDriver driver){
		super(driver, HEADER); 
	}
	
	// Page input fields
	private WebElement location  = driver.findElement(By.id("gsearcher"));
	private WebElement description  = driver.findElement(By.id("description"));
	private WebElement adminArea1  = driver.findElement(By.id("adminArea1"));
	private WebElement adminArea2  = driver.findElement(By.id("adminArea2"));
	
	// Page buttons
	private WebElement saveButton = driver.findElement(By.className("saveButton"));
	private WebElement deleteButton = driver.findElement(By.className("deleteButton"));
	
	
	/**
	 * @param name
	 * @param desc
	 * @param aera1
	 * @param area2
	 */
	public void fillData( String name, String desc, String area1, String area2){
		log.trace("filling zone data");
		
		fillAutocomplete(location, name);
		
		log.trace("filling description : " + desc);
		description.sendKeys(desc);
		
		if (adminArea1 != null){
			log.trace("filling adminArea1: " + area1);
			adminArea1.sendKeys(area1);
		}
		
		if (adminArea2 != null){
			log.trace("filling adminArea2: " + area2);
			adminArea2.sendKeys(area2);
		}
	}
	
	
	/**
	 * Draw a Random Zone
	 */
	public void drawRandomZone(){
		log.trace("select polygon button");
		driver.findElement(By.cssSelector("div[title=\"Dibujar forma\"] > span > div > img")).click();
		WebElement mapCanvas = driver.findElement(By.xpath("//div[@id='map_canvas']/div/div/div/div[3]/div[2]/div"));
		
		log.trace("draw zone");
		
		// FIXME: No selecciona bien el área
		Actions actions = new Actions(driver);
		actions.moveToElement(mapCanvas).click();
		actions.moveByOffset(nextRandomSignedInt(50),nextRandomSignedInt(50)).click();
		actions.moveByOffset(nextRandomSignedInt(50),nextRandomSignedInt(50)).doubleClick();
		actions.perform();
	}
	
	private int nextRandomSignedInt(int n){
		return RandomUtils.nextInt(n) * (RandomUtils.nextBoolean() ? 1 : -1);
	}
	
	/**
	 * save
	 */
	public void save(){
		saveButton.click();
	}
	
	/**
	 * delete
	 */
	public ZoneListPage delete(){
		deleteButton.click();
		return new ZoneListPage(driver);
	}
	

}
