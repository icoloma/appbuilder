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
import java.util.Set;

public abstract class AbstractPoiIntegrationTest extends AbstractIntegrationTest {
	
	protected void fillAllInfo(AbstractPoiPage page, BasicPoi poi){
		fillNameData(page, poi);
		fillData(page, poi);
		fillLocation(page,poi);
		fillFlags(page, poi);
		fillTimeTable(page,poi);
		fillPrices(page,poi);
		fillContactInfo(page,poi);
		fillSyncInfo(page,poi);
	}
	
	protected void fillSyncInfo(AbstractPoiPage page, BasicPoi poi){
		if (page.showSyncInfoTab()) {
			page.checkSyncCheckBox();
		}
	}
	
	protected void fillContactInfo(AbstractPoiPage page, BasicPoi poi){
		page.showContactInfoTab();
		if (poi.getContactInfo() != null){
			page.setContactInfo(poi.getContactInfo());
		}
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
