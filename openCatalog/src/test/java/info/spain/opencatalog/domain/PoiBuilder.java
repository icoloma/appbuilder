package info.spain.opencatalog.domain;

import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.domain.poi.Poi;

import java.util.List;

public class PoiBuilder {
	private Poi poi = new Poi();

	public PoiBuilder setId(String id){
		poi.setId(id);
		return this;
	}
	public PoiBuilder setDescription(I18nText description){
		poi.setDescription(description);
		return this;
	}
	
	public PoiBuilder setName(I18nText name){
		poi.setName(name);
		return this;
	}
	
	public PoiBuilder setLocation(GeoLocation loc){
		poi.setLocation(loc);
		return this;
	}
	public PoiBuilder setTags(List<Tag> tag){
		poi.setTags(tag);
		return this;
	}
	public PoiBuilder setAddress(Address address){
		poi.setAddress(address);
		return this;
	}
	
	public Poi build(){
		return poi;
	}
}

