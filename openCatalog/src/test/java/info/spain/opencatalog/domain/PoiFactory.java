package info.spain.opencatalog.domain;

import info.spain.opencatalog.domain.Tags.Tag;
import info.spain.opencatalog.domain.poi.DisabledAccessibility;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.Poi;
import info.spain.opencatalog.domain.poi.PoiTypeRepository.PoiType;
import info.spain.opencatalog.domain.poi.QualityCertificate;
import info.spain.opencatalog.domain.poi.types.ContactInfo;
import info.spain.opencatalog.domain.poi.types.HourRange;
import info.spain.opencatalog.domain.poi.types.TimeTableDay;
import info.spain.opencatalog.domain.poi.types.TimeTableEntry;
import info.spain.opencatalog.domain.poi.types.TimeTableEntry.WeekDay;
import info.spain.opencatalog.domain.poi.types.beach.BeachBathCondition;
import info.spain.opencatalog.domain.poi.types.beach.BeachComposition;
import info.spain.opencatalog.domain.poi.types.beach.BeachPoiType;
import info.spain.opencatalog.domain.poi.types.beach.BeachSandType;
import info.spain.opencatalog.domain.poi.types.culture.CultureArtisticPeriod;
import info.spain.opencatalog.domain.poi.types.culture.CultureConstructionType;
import info.spain.opencatalog.domain.poi.types.culture.CultureDesignation;
import info.spain.opencatalog.domain.poi.types.culture.CultureHistoricalPeriod;
import info.spain.opencatalog.domain.poi.types.culture.CulturePoiType;
import info.spain.opencatalog.domain.poi.types.culture.CulturePrice;
import info.spain.opencatalog.domain.poi.types.culture.CulturePriceType;
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

import org.joda.time.DateTimeConstants;
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
	public static CulturePoiType MUSEUM;
	public static CulturePoiType MONUMENT;
	

	
	static {
		// Hotel
		HOTEL = new LodgingPoiType()
			.setPoiType(PoiType.HOTEL)
			.setName(new I18nText().setEs("Hotel Puerta del Sol"))				// required
			.setDescription(new I18nText().setEs("Descripción del hotel..."))	// required	
			.setLocation(AbstractFactory.GEO_SOL)								// required
			.setAddress(new Address().setRoute("Puerta del Sol").setAdminArea1("Comunidad de Madrid").setAdminArea2("Madrid"))
			.setScores(Score.STAR_3)
			.setContactInfo( new ContactInfo()
				.setEmail("info@hotelpuertadelsol.com")	
				.setUrl("http://www.hotelpuertadelsol.com")
				.setPhone("+34 000000000")
			)
			.setLodgingFlags(
				LodgingFlag.CASINO, 
				LodgingFlag.CREDIT_CARD, 
				LodgingFlag.EXCHANGE)
			.setLodgingTypes(
				LodgingType.HAB1,
				LodgingType.HAB2)
			.setLodgingPrices(
				new LodgingPrice(LodgingType.HAB1, Season.HIGH_SEASON, Regime.AD, 30d),
				new LodgingPrice(LodgingType.HAB1, Season.MEDIUM_SEASON, Regime.AD, 25d),
				new LodgingPrice(LodgingType.HAB1, Season.LOW_SEASON, Regime.AD, 20d))
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
				new LodgingPrice(LodgingType.TENT, Season.HIGH_SEASON, Regime.AD, 30d),
				new LodgingPrice(LodgingType.TENT, Season.MEDIUM_SEASON, Regime.AD, 25d),
				new LodgingPrice(LodgingType.TENT, Season.LOW_SEASON, Regime.AD, 20d))
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
				new LodgingPrice(LodgingType.HAB1, Season.HIGH_SEASON, Regime.AD, 30d),
				new LodgingPrice(LodgingType.HAB1, Season.MEDIUM_SEASON, Regime.AD, 25d),
				new LodgingPrice(LodgingType.HAB1, Season.LOW_SEASON, Regime.AD, 20d),
				new LodgingPrice(LodgingType.HAB2, Season.HIGH_SEASON, Regime.AD, 60d),
				new LodgingPrice(LodgingType.HAB2, Season.MEDIUM_SEASON, Regime.AD, 50d),
				new LodgingPrice(LodgingType.HAB2, Season.LOW_SEASON, Regime.AD, 40d))
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
			.setLarge(100d)
			.setWidth(20d)
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
		

		// Museum
		MUSEUM = new CulturePoiType()
			.setPoiType(PoiType.MUSEUM)
			.setName(new I18nText().setEs("Museo del Prado"))					// required
			.setDescription(new I18nText().setEs("Descripción del museo"))		// required	
			.setLocation(randomLocation())					// required
			.setContactInfo( new ContactInfo()
				.setEmail("info@mueseodelprado.com")	
				.setUrl("http://www.museodelprado.com")
				.setPhone("+34 000000000")
			)
			.setDesignation(CultureDesignation.NATIONAL_MUSEUM)
			.setFlags(Flag.GUIDED_TOUR)
			.setDisabledAccessibility(
					DisabledAccessibility.PARKING_ACCESSIBLE,
					DisabledAccessibility.ASSISTANCE_TO_DISABLED,
					DisabledAccessibility.GUIDE_DOG_ALLOWED
			)
			.setTimetable(
				new TimeTableEntry()
					.setWeekDays(
							WeekDay.MONDAY, 
							WeekDay.WEDNESDAY, 
							WeekDay.FRIDAY)
					.setHourRange( 
						new HourRange("09:00", "13:00"),
						new HourRange("15:00", "20:00")),
				new TimeTableEntry()
					.setDays( new TimeTableDay(6,DateTimeConstants.JANUARY))
					.setDays( new TimeTableDay(24,DateTimeConstants.DECEMBER))
					.setHourRange(
						new HourRange("10:00","14:00")),
				new TimeTableEntry()
					.setClosedDays( 
							new TimeTableDay(1,DateTimeConstants.JANUARY),
							new TimeTableDay(1,DateTimeConstants.MAY),
							new TimeTableDay(25,DateTimeConstants.DECEMBER)
					)
			)
			.setPrices(
				new CulturePrice()
					.setPriceTypes(CulturePriceType.GENERAL, CulturePriceType.GROUPS)
					.setPrice(14d),
				new CulturePrice()
					.setPriceTypes(CulturePriceType.REDUCED)
					.setPrice(7d),
				new CulturePrice()
					.setPriceTypes(CulturePriceType.STUDENT)
					.setPrice(10d),
				new CulturePrice()
					.setPriceTypes(CulturePriceType.FREE)
					.setObservations(new I18nText().setEs("Desempleados, personal de los Museos Estatales del Ministerio de Cultura"))
					.setTimetable(
						new TimeTableEntry()
							.setWeekDays(WeekDay.MONDAY,WeekDay.TUESDAY,WeekDay.WEDNESDAY,WeekDay.THURSDAY,WeekDay.FRIDAY)
							.setHourRange(new HourRange("18:00","20:00")),
						new TimeTableEntry()
							.setWeekDays(WeekDay.SATURDAY, WeekDay.SUNDAY)
							.setHourRange(new HourRange("17:00","19:00"))
					)
			);
		
		MUSEUM.validateTypeAllowedValues();
	
		

		// MONUMENTO
		MONUMENT = new CulturePoiType()
			.setPoiType(PoiType.MONUMENT)
			.setName(new I18nText().setEs("La Alhambra"))					// required
			.setDescription(new I18nText().setEs("Descripción del monumento"))		// required	
			.setLocation(randomLocation())					// required
			.setContactInfo( new ContactInfo()
				.setEmail("info@lahalambra.com")	
				.setUrl("http://www.lahalambra.com")
				.setPhone("+34 000000000")
			)
			.setConstructionType(CultureConstructionType.PALACE)
			.setArtisticPeriod( CultureArtisticPeriod.ARABIC)
			.setHistoricalPeriod( CultureHistoricalPeriod.CENTURY_14)
			.setEnviroment(new I18nText().setEs("El Generalife").setEn("The Generalife"))
			.setQualityCertificates( QualityCertificate.PATRIMONIO_HUMANIDAD )
			.setFlags(Flag.GUIDED_TOUR)
			.setDisabledAccessibility(
					DisabledAccessibility.PARKING_ACCESSIBLE,
					DisabledAccessibility.ASSISTANCE_TO_DISABLED,
					DisabledAccessibility.GUIDE_DOG_ALLOWED
			)
			.setTimetable(
				new TimeTableEntry()
					.setWeekDays(
							WeekDay.MONDAY, 
							WeekDay.WEDNESDAY, 
							WeekDay.FRIDAY)
					.setHourRange( 
						new HourRange("09:00", "13:00"),
						new HourRange("15:00", "20:00")),
				new TimeTableEntry()
					.setDays( new TimeTableDay(6,DateTimeConstants.JANUARY))
					.setDays( new TimeTableDay(24,DateTimeConstants.DECEMBER))
					.setHourRange(
						new HourRange("10:00","14:00")),
				new TimeTableEntry()
					.setClosedDays( 
							new TimeTableDay(1,DateTimeConstants.JANUARY),
							new TimeTableDay(1,DateTimeConstants.MAY),
							new TimeTableDay(25,DateTimeConstants.DECEMBER)
					)
			)
			.setPrices(
				new CulturePrice()
					.setPriceTypes(CulturePriceType.GENERAL, CulturePriceType.GROUPS)
					.setPrice(14d),
				new CulturePrice()
					.setPriceTypes(CulturePriceType.REDUCED)
					.setPrice(7d),
				new CulturePrice()
					.setPriceTypes(CulturePriceType.STUDENT)
					.setPrice(10d),
				new CulturePrice()
					.setPriceTypes(CulturePriceType.FREE)
					.setObservations(new I18nText().setEs("Desempleados, personal de los Museos Estatales del Ministerio de Cultura"))
					.setTimetable(
						new TimeTableEntry()
							.setWeekDays(WeekDay.MONDAY,WeekDay.TUESDAY,WeekDay.WEDNESDAY,WeekDay.THURSDAY,WeekDay.FRIDAY)
							.setHourRange(new HourRange("18:00","20:00")),
						new TimeTableEntry()
							.setWeekDays(WeekDay.SATURDAY, WeekDay.SUNDAY)
							.setHourRange(new HourRange("17:00","19:00"))
					)
			);
		
		MONUMENT.validateTypeAllowedValues();
	}
	
	
}