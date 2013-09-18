package info.spain.opencatalog.web.selenium;

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
		FirefoxProfile profile = new FirefoxProfile();  
		profile.setPreference("intl.accept_languages","es");
		driver = new FirefoxDriver(profile);  
	}
	
	@After 
	public void tearDown(){
		driver.close();
	}

}
