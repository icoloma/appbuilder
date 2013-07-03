package info.spain.opencatalog.domain.poi;

import info.spain.opencatalog.domain.Address;
import info.spain.opencatalog.domain.GeoLocation;
import info.spain.opencatalog.domain.I18nText;
import info.spain.opencatalog.domain.poi.types.BasicPoiType;
import info.spain.opencatalog.domain.poi.types.PoiTypeRepository;

import com.google.common.base.Preconditions;

/** 
 * Clase base para cualquier POI genérico 
 */
public class BasicPoi extends AbstractPoi {
	
	public BasicPoi(BasicPoiType type){
		super(type);
		Preconditions.checkArgument(PoiTypeRepository.BASIC_TYPES.contains(type.getId()));
    }
	
	@Override
	public BasicPoi setLanguages(String... languages) {
		return (BasicPoi) super.setLanguages(languages);
	}

	@Override
	public BasicPoi setData(String key, String data) {
		return (BasicPoi) (BasicPoi) super.setData(key, data);
	}

	@Override
	public BasicPoi setTimetable(TimeTableEntry... timetable) {
		return (BasicPoi) super.setTimetable(timetable);
	}

	@Override
	public BasicPoi setFlags(Flag... flags) {
		return (BasicPoi) super.setFlags(flags);
	}

	@Override
	public BasicPoi setId(String id) {
		return (BasicPoi) super.setId(id);
	}

	@Override
	public BasicPoi setName(I18nText name) {
		return (BasicPoi) super.setName(name);
	}

	@Override
	public BasicPoi setDescription(I18nText description) {
		return (BasicPoi) super.setDescription(description);
	}

	@Override
	public BasicPoi setAddress(Address address) {
		return (BasicPoi) super.setAddress(address);
	}

	@Override
	public BasicPoi setLocation(GeoLocation location) {
		return (BasicPoi) super.setLocation(location);
	}

	@Override
	public BasicPoi setContactInfo(ContactInfo contactInfo) {
		return (BasicPoi) super.setContactInfo(contactInfo);
	}

	@Override
	public BasicPoi setScore(Score score) {
		return (BasicPoi) super.setScore(score);
	}

	@Override
	public BasicPoi setPrices(Price... prices) {
		return (BasicPoi) super.setPrices(prices);
	}

	@Override
	public BasicPoi validate() {
		return (BasicPoi) super.validate();
	}

	@Override
	public BasicPoi setEnviroment(I18nText enviroment) {
		return (BasicPoi) super.setEnviroment(enviroment);
	}
	
	
	

}
