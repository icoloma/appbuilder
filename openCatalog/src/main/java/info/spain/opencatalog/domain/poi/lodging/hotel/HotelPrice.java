package info.spain.opencatalog.domain.poi.lodging.hotel;

import info.spain.opencatalog.domain.poi.lodging.LodgingPrice;
import info.spain.opencatalog.domain.poi.lodging.Regime;
import info.spain.opencatalog.domain.poi.lodging.Season;

public class HotelPrice extends LodgingPrice {

	public HotelPrice(HotelPriceType priceType, Season season, Regime regime, Double price) {
		super(priceType.toString(), season, regime, price);
	}

	public static enum HotelPriceType { 
		SUITE,
		HAB4, 
		HAB3,
		HAB2, 
		HAB1;
		
	}

}

