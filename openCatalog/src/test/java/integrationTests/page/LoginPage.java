package integrationTests.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends AbstractPage {
	
	public static final String HEADER ="Login";
	
	private WebElement email  = driver.findElement(By.id("j_username"));
	private WebElement password = driver.findElement(By.id("j_password"));
	private WebElement rememberMe  = driver.findElement(By.name("_spring_security_remember_me"));
	private WebElement submit  = driver.findElement(By.id("loginButton"));
	
	public LoginPage(WebDriver driver){
		super(driver, HEADER); 
	}
	
	public HomePage login(String email, String password, Boolean rememberMe){
		this.email.sendKeys(email);
		this.password.sendKeys(password);
		if (rememberMe){
			this.rememberMe.click();
		}
		submit.click();
		return new HomePage(driver);
	}
	
	
	

}
