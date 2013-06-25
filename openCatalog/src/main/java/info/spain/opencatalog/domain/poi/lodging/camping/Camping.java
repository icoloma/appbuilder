package info.spain.opencatalog.domain.poi.lodging.camping;

import info.spain.opencatalog.domain.poi.lodging.AbstractLodging;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="poi")
public class Camping extends AbstractLodging<CampingPrice, CampingCategory, CampingService> {
	
	private static final long serialVersionUID = -3681816043454782629L;

	private CampingCategory category;
	private List<CampingPrice> prices = new ArrayList<CampingPrice>();
	private List<CampingService> services = new ArrayList<CampingService>();
	
	public List<CampingPrice> getPrices() {
		return prices;
	}
	
	public void setPrices(List<CampingPrice> prices) {
		this.prices = prices;
	}

	@Override
	public CampingCategory getCategory() {
		return category;
	}

	@Override
	public void setCategory(CampingCategory category) {
		this.category = category;
	}

	@Override
	public List<CampingService> getServices() {
		return this.services;
	}

	@Override
	public void setServices(List<CampingService> services) {
		this.services = services;
	}
	
	

	
}
