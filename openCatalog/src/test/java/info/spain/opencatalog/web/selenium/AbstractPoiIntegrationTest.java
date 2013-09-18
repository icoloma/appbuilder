package info.spain.opencatalog.web.selenium;

import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.Price;
import info.spain.opencatalog.domain.poi.TimeTableEntry;
import info.spain.opencatalog.domain.poi.types.BasicPoiType;
import info.spain.opencatalog.domain.poi.types.DataValidator;
import info.spain.opencatalog.web.selenium.page.poi.AbstractPoiPage;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/properties-config.xml")
@ActiveProfiles("dev")
public abstract class AbstractPoiIntegrationTest {
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("configProps")
	protected Properties config;  
	
	protected WebDriver driver;

	@Before
	public void init(){
		driver = new FirefoxDriver();
	}
	
	@After 
	public void tearDown(){
		driver.close();
	}
	
	
	protected void fillAllInfo(AbstractPoiPage page, BasicPoi poi){
		fillNameData(page, poi);
		fillData(page, poi);
		fillLocation(page,poi);
		fillFlags(page, poi);
		fillTimeTable(page,poi);
		fillPrices(page,poi);
	}
	
	
	protected void fillNameData(AbstractPoiPage page, BasicPoi poi){
		page.setName(poi.getName().getEs());
		page.setDescription(poi.getDescription().getEs());
	}
	
	protected void fillTimeTable(AbstractPoiPage page, BasicPoi poi){
		page.showTimeTableTab();
		Set<TimeTableEntry> entries = poi.getTimetable();
		if (entries != null){
			for (TimeTableEntry entry : entries) {
				page.addTimeTableEntry(entry.getPeriod());
			}
		}
	}
	
	protected void fillPrices(AbstractPoiPage page, BasicPoi poi){
		page.showPricesTab();
		List<Price> entries = poi.getPrices();
			if (entries != null ){
			for (Price entry : entries) {
				page.addPrice(entry.getPrice().toString(), entry.getPriceType().toString(), entry.getTimeTable().getPeriod(), entry.getObservations().getEs());
			}
		}
	}
	
	protected void fillFlags(AbstractPoiPage page, BasicPoi poi){
		page.showFlagsTab();
		poi.getType().getAllowedFlags();
		for (Flag flag : poi.getFlags()) {
			log.trace("Selecting flag " + flag);
			page.selectFlag(flag.toString());
		}
	}

	protected void fillLocation(AbstractPoiPage page, BasicPoi poi){
		page.showLocationTab();
		page.searchLocation(poi.getName().getEs());
	}
	
	protected void fillData(AbstractPoiPage page, BasicPoi poi){
		BasicPoiType type = poi.getType();
		Map<String, DataValidator> dataValidators = type.getAllowedDataValidators();
		Set<String> keys = dataValidators.keySet();
		for (String key : keys) {
			String value = poi.getData().get(key);
			page.setData(key,value);
		}
	}
	
		
	


}
