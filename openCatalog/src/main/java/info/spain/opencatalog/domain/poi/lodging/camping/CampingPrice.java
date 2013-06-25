package info.spain.opencatalog.domain.poi.lodging.camping;

import info.spain.opencatalog.domain.poi.lodging.LodgingPrice;
import info.spain.opencatalog.domain.poi.lodging.Regime;
import info.spain.opencatalog.domain.poi.lodging.Season;

public class CampingPrice extends LodgingPrice {

	public CampingPrice(CampingPriceType priceType, Season season, Regime regime, Double price) {
		super(priceType.toString(), season, regime, price);
	}

	public static enum CampingPriceType { 
		
		ADULT,
		CHILD,
		DOG,
		TENT,
		TENT_FAM,
		BUS,
		MOTORHOME,
		MOTORHOME_5_8,
		CAR,
		BUNGALOW,
		BUNGALOW_5_8,
		ELECTRICITY,
		MOBILEHOME,
		MOBILEHOME_5_8,
		MOTORBIKE,
		PLOT;
		
	}

}


