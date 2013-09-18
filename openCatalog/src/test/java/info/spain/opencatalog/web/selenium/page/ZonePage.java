package info.spain.opencatalog.web.selenium.page;

import org.apache.commons.lang.math.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ZonePage extends AbstractPage {
	
	private static final String HEADER = "Zona";
	
	
	public ZonePage(WebDriver driver){
		super(driver,HEADER); 
	}
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param adminArea1
	 * @param adminArea2
	 */
	public void fillData( String name, String description, String adminArea1, String adminArea2){
		log.trace("filling zone data");
		
		fillAutocomplete("gsearcher", name);
		
		log.trace("filling description");
		driver.findElement(By.id("description")).sendKeys(description);
		
		if (adminArea1 != null){
			driver.findElement(By.id("adminArea1")).sendKeys(adminArea1);
		}
		
		if (adminArea2 != null){
			driver.findElement(By.id("adminArea2")).sendKeys(adminArea2);
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
		driver.findElement(By.className("saveButton")).click();
	}
	
	/**
	 * delete
	 */
	public ZoneListPage delete(){
		driver.findElement(By.className("deleteButton")).click();
		return new ZoneListPage(driver);
	}
	

}
