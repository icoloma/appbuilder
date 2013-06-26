package info.spain.opencatalog.domain;

import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.domain.poi.DisabledAccessibility;
import info.spain.opencatalog.domain.poi.Poi;
import info.spain.opencatalog.domain.poi.PoiTypeRepository.PoiType;
import info.spain.opencatalog.domain.poi.QualityCertificate;
import info.spain.opencatalog.domain.poi.types.beach.BeachBathCondition;
import info.spain.opencatalog.domain.poi.types.beach.BeachComposition;
import info.spain.opencatalog.domain.poi.types.beach.BeachPoiType;
import info.spain.opencatalog.domain.poi.types.beach.BeachSandType;
import info.spain.opencatalog.domain.poi.types.lodging.LodgingFlag;
import info.spain.opencatalog.domain.poi.types.lodging.LodgingPoiType;
import info.spain.opencatalog.domain.poi.types.lodging.LodgingPrice;
import info.spain.opencatalog.domain.poi.types.lodging.LodgingType;
import info.spain.opencatalog.domain.poi.types.lodging.LodgingTypeFlag;
import info.spain.opencatalog.domain.poi.types.lodging.Regime;
import info.spain.opencatalog.domain.poi.types.lodging.Score;
import info.spain.opencatalog.domain.poi.types.lodging.Season;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import com.google.common.collect.ImmutableSet;

public class PoiFactory extends AbstractFactory {
	
	public static PoiBuilder newPoi(String key){
		key = key + "-" + getRandom().nextInt();
		return  new PoiBuilder()
			.setName( new I18nText()
				.setEs("es-"+key+"-name")
				.setEn("en-"+key+"-name")
				.setDe("en-"+key+"-name"))
			.setDescription( new I18nText()
				.setEs("es-"+key+"-description")
				.setEn("en-"+key+"-description")
				.setDe("en-"+key+"-description"))
			.setAddress(new Address()
				.setRoute( key + "-address")
				.setAdminArea1( "adminArea1-" + getRandom().nextInt(10))
				.setAdminArea2( "adminArea2-" + getRandom().nextInt(10))
				.setZipCode(key+"-zipCode"))
			.setLocation(randomLocation())
			.setTags(randomTags(getRandom().nextInt(4)));
		}
	
	public static LodgingPoiType newLodging(String key, PoiType poiType){
		key = key + "-" + getRandom().nextInt();
		
		LodgingPoiType result = new LodgingPoiType().setPoiType(poiType);
		return result;
		
	}

	
	private static List<Tag> randomTags(int numTags){
		List<Tag> result = new ArrayList<Tag>();
		for(int i=0; i<numTags; i++){
			Tag tag = Tag.values()[(getRandom().nextInt(Tag.values().length))];
			result.add(tag);
		}
		return result;
	}
	
	
	
	
	public static List<Poi> generatePois(int numPois){
		// random
		List<Poi> result = new ArrayList<Poi>();
		for (int i = 0; i < numPois; i++) {
			result.add(newPoi("" + i).build());
		}
		return result;
	}
	
	public static Resource randomImage(){
		String imageFilename = "/img/" + getRandom().nextInt(9) + ".jpg";
		Resource image = new ClassPathResource(imageFilename);
		Assert.isTrue(image.exists());
		return image;
	}

	
	public static Poi POI_CASA_CAMPO =  newPoi("Casa de Campo").setLocation(GEO_CASA_CAMPO).setAddress(new Address().setAdminArea1("Comunidad de Madrid")).build();
	public static Poi POI_RETIRO = newPoi("Retiro").setLocation(GEO_RETIRO).setAddress(new Address().setAdminArea1("Comunidad de Madrid")).build();
	public static Poi POI_SOL = newPoi("Sol").setLocation(GEO_SOL).setAddress(new Address().setAdminArea1("Comunidad de Madrid")).build();
	public static Poi POI_TEIDE = newPoi("Teide").setLocation(GEO_TEIDE).setAddress(new Address().setAdminArea1("Canarias").setAdminArea2("Tenerife")).build();
	public static Poi POI_PLAYA_TERESITAS= newPoi("Playa de las Teresitas").setLocation(GEO_PLAYA_TERESITAS).setAddress(new Address().setAdminArea1("Canarias").setAdminArea2("Tenerife")).build();
	public static Poi POI_ROQUE_NUBLO= newPoi("Roque Nublo").setLocation(GEO_ROQUE_NUBLO).setAddress(new Address().setAdminArea1("Canarias").setAdminArea2("Gran Canaria")).build();
	public static Poi POI_ALASKA = newPoi("Alaska").setLocation(GEO_ALASKA).build();
	
	public static ImmutableSet<Poi> WELL_KNOWN_POIS = ImmutableSet.of(POI_CASA_CAMPO, POI_RETIRO, POI_SOL, POI_TEIDE, POI_ALASKA, POI_PLAYA_TERESITAS, POI_ROQUE_NUBLO);
	
	public static LodgingPoiType HOTEL;
	public static LodgingPoiType CAMPING;
	public static LodgingPoiType APARTMENT;
	public static BeachPoiType BEACH;

	
	static {
		// Hotel
		HOTEL = new LodgingPoiType()
			.setPoiType(PoiType.HOTEL)
			.setName(new I18nText().setEs("Hotel Puerta del Sol"))				// required
			.setDescription(new I18nText().setEs("Descripción del hotel..."))	// required	
			.setLocation(AbstractFactory.GEO_SOL)								// required
			.setAddress(new Address().setRoute("Puerta del Sol").setAdminArea1("Comunidad de Madrid").setAdminArea2("Madrid"))
			.setScores(Score.STAR_3)
			.setLodgingFlags(
				LodgingFlag.CASINO, 
				LodgingFlag.CREDIT_CARD, 
				LodgingFlag.EXCHANGE)
			.setLodgingTypes(
				LodgingType.HAB1,
				LodgingType.HAB2)
			.setLodgingPrices(
				new LodgingPrice(LodgingType.HAB1, Season.HIGH_SEASON, Regime.AD, Double.valueOf(30)),
				new LodgingPrice(LodgingType.HAB1, Season.MEDIUM_SEASON, Regime.AD, Double.valueOf(25)),
				new LodgingPrice(LodgingType.HAB1, Season.LOW_SEASON, Regime.AD, Double.valueOf(20)))
			.setLodgingTypeFlags(
				LodgingTypeFlag.JACUZZI,
				LodgingTypeFlag.SAFE_BOX)
			.setDisabledAccessibility(
				DisabledAccessibility.ADAPTED_ROOMS,
				DisabledAccessibility.LIFT_ACCESSIBLE,
				DisabledAccessibility.ADAPTED_VEHICLE_RENT,
				DisabledAccessibility.PARKING_ACCESSIBLE,
				DisabledAccessibility.ASSISTANCE_TO_DISABLED,
				DisabledAccessibility.GUIDE_DOG_ALLOWED
			);
		
		HOTEL.validateTypeAllowedValues();
		
	
		// Camping
		CAMPING = new LodgingPoiType()
			.setPoiType(PoiType.CAMPING)
			.setName(new I18nText().setEs("CAMPING Montaña Rajada"))				// required
			.setDescription(new I18nText().setEs("Descripción del CAMPING..."))	// required	
			.setLocation(AbstractFactory.GEO_CASA_CAMPO)								// required
			.setAddress(new Address().setRoute("Casa de Campo").setAdminArea1("Comunidad de Madrid").setAdminArea2("Madrid"))
			.setScores(Score.CAT_1)
			.setLodgingFlags(
				LodgingFlag.BBQ,
				LodgingFlag.BIKE_RENT,
				LodgingFlag.CLOACKROOM,
				LodgingFlag.CREDIT_CARD 
				)
			.setLodgingTypes(
				LodgingType.TENT,
				LodgingType.TENT_FAM)
			.setLodgingPrices(
				new LodgingPrice(LodgingType.TENT, Season.HIGH_SEASON, Regime.AD, Double.valueOf(30)),
				new LodgingPrice(LodgingType.TENT, Season.MEDIUM_SEASON, Regime.AD, Double.valueOf(25)),
				new LodgingPrice(LodgingType.TENT, Season.LOW_SEASON, Regime.AD, Double.valueOf(20)))
			.setDisabledAccessibility(
				DisabledAccessibility.ADAPTED_ROOMS,
				DisabledAccessibility.LIFT_ACCESSIBLE,
				DisabledAccessibility.ADAPTED_VEHICLE_RENT,
				DisabledAccessibility.PARKING_ACCESSIBLE,
				DisabledAccessibility.ASSISTANCE_TO_DISABLED,
				DisabledAccessibility.GUIDE_DOG_ALLOWED
			);
		
		CAMPING.validateTypeAllowedValues();

		

		// Apartmnent
		APARTMENT = new LodgingPoiType()
			.setPoiType(PoiType.APARTMENT)
			.setName(new I18nText().setEs("Apartamentos Bahía azul"))				// required
			.setDescription(new I18nText().setEs("Descripción del apartamento..."))	// required	
			.setLocation(randomLocation())											// required
			.setScores(Score.KEY_1)
			.setLodgingFlags(
				LodgingFlag.CREDIT_CARD 
				)
			.setLodgingTypes(
				LodgingType.HAB1,
				LodgingType.HAB2)
			.setLodgingPrices(
				new LodgingPrice(LodgingType.HAB1, Season.HIGH_SEASON, Regime.AD, Double.valueOf(30)),
				new LodgingPrice(LodgingType.HAB1, Season.MEDIUM_SEASON, Regime.AD, Double.valueOf(25)),
				new LodgingPrice(LodgingType.HAB1, Season.LOW_SEASON, Regime.AD, Double.valueOf(20)),
				new LodgingPrice(LodgingType.HAB2, Season.HIGH_SEASON, Regime.AD, Double.valueOf(60)),
				new LodgingPrice(LodgingType.HAB2, Season.MEDIUM_SEASON, Regime.AD, Double.valueOf(50)),
				new LodgingPrice(LodgingType.HAB2, Season.LOW_SEASON, Regime.AD, Double.valueOf(40)))
			.setDisabledAccessibility(
				DisabledAccessibility.ADAPTED_ROOMS,
				DisabledAccessibility.LIFT_ACCESSIBLE
			);
		
		APARTMENT.validateTypeAllowedValues();
		
		
		
		// Beach
		BEACH = new BeachPoiType()
			.setName(new I18nText().setEs("Playa de las teresitas"))			// required
			.setDescription(new I18nText().setEs("Descripción de la playa..."))	// required	
			.setLocation(AbstractFactory.GEO_PLAYA_TERESITAS)					// required
			.setAddress(new Address().setRoute("Las teresitas").setAdminArea1("Canarias").setAdminArea2("Tenerife"))
			.setQualityCertificates(
					QualityCertificate.BANDERA_AZUL,
					QualityCertificate.NATURISTA)
			.setLarge(Double.valueOf(100))
			.setWidth(Double.valueOf(20))
			.setAnchorZone(Boolean.TRUE)
			.setBathCondition(BeachBathCondition.MODERATE_WAVES)
			.setComposition(BeachComposition.VOLCANIC_BLACK_SAND)
			.setSandType(BeachSandType.DARK)
			.setDisabledAccessibility(
					DisabledAccessibility.PARKING_ACCESSIBLE,
					DisabledAccessibility.ASSISTANCE_TO_DISABLED,
					DisabledAccessibility.GUIDE_DOG_ALLOWED
			);
		
		BEACH.validateTypeAllowedValues();
		
		
	}
	
	
}