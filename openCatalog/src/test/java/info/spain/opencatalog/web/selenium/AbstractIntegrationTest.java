package info.spain.opencatalog.web.selenium;

import info.spain.opencatalog.domain.MongoDbPopulator;
import info.spain.opencatalog.domain.User;

import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/properties-config.xml")
public abstract class AbstractIntegrationTest {
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("configProps")
	protected Properties config;  
	
	protected WebDriver driver;

	@Before
	public void init(){
		populateDB();
		FirefoxProfile profile = new FirefoxProfile();  
		profile.setPreference("intl.accept_languages","es");
		driver = new FirefoxDriver(profile);  
	}
	
	@After 
	public void tearDown(){
		driver.close();
	}
	
	public HomePage loginAs(User user) {
		String url = config.getProperty("baseUrl") + "/login";
		log.trace("url:" + url);
		driver.get(url);
		
		LoginPage page = new LoginPage(driver);
		return page.login(user.getEmail(), user.getPassword(), false);
	}
	
	public void logout(){
		String url = config.getProperty("baseUrl") + "/logout";
		log.trace("url:" + url);
		driver.get(url);
	}
	
	public void populateDB(){
		MongoDbPopulator.main(new String[]{});
	}


}
