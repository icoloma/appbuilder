package info.spain.opencatalog.domain.poi.lodging.hotel;

import info.spain.opencatalog.domain.poi.lodging.AbstractLodging;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="poi")
public class Hotel extends AbstractLodging<HotelPrice, HotelCategory, HotelService> {
	
	private static final long serialVersionUID = -3681816043454782629L;

	private HotelCategory category;
	private List<HotelPrice> prices = new ArrayList<HotelPrice>();
	private List<HotelService> services = new ArrayList<HotelService>();
	
	public List<HotelPrice> getPrices() {
		return prices;
	}
	
	public void setPrices(List<HotelPrice> prices) {
		this.prices = prices;
	}

	@Override
	public HotelCategory getCategory() {
		return category;
	}

	@Override
	public void setCategory(HotelCategory category) {
		this.category = category;
	}

	@Override
	public List<HotelService> getServices() {
		return this.services;
	}

	@Override
	public void setServices(List<HotelService> services) {
		this.services = services;
	}
	
	

	
}
