package info.spain.opencatalog.domain;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import info.spain.opencatalog.domain.poi.AccessibilityFlag;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.QualityCertificateFlag;
import info.spain.opencatalog.domain.poi.types.*;
import info.spain.opencatalog.domain.poi.types.TimeTableEntry.WeekDay;
import info.spain.opencatalog.domain.poi.types.beach.BathCondition;
import info.spain.opencatalog.domain.poi.types.beach.BeachComposition;
import info.spain.opencatalog.domain.poi.types.beach.BeachPoiType;
import info.spain.opencatalog.domain.poi.types.beach.SandType;
import info.spain.opencatalog.domain.poi.types.culture.*;
import info.spain.opencatalog.domain.poi.types.lodging.*;
import info.spain.opencatalog.domain.poi.types.nature.NaturalSpaceFlag;
import info.spain.opencatalog.domain.poi.types.nature.NaturalSpacePoiType;
import org.joda.time.DateTimeConstants;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PoiFactory extends AbstractFactory {
	
	public static BasicPoi newPoi(String key){
		key = key + "-" + getRandom().nextInt();
		return  new BasicPoi()
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
			.setFlags(randomFlags(getRandom().nextInt(4)));
		}
	
	public static LodgingPoi newLodging(String key, PoiTypeID poiType){
		key = key + "-" + getRandom().nextInt();
		
		LodgingPoi result = new LodgingPoi().setPoiType(poiType);
		return result;
		
	}

	
	private static Set<Flag> randomFlags(int numFlags){
		Set<Flag> result = Sets.newHashSet();
		for(int i=0; i< numFlags; i++){
			Flag flag = Flag.values()[(getRandom().nextInt(Flag.values().length))];
			result.add(flag);
		}
		return result;
	}
	
	
	public static List<BasicPoi> generatePois(int numPois){
		// random
		List<BasicPoi> result = new ArrayList<BasicPoi>();
		for (int i = 0; i < numPois; i++) {
			result.add(newPoi("" + i));
		}
		return result;
	}
	
	public static Resource randomImage(){
		String imageFilename = "/img/" + getRandom().nextInt(9) + ".jpg";
		Resource image = new ClassPathResource(imageFilename);
		Assert.isTrue(image.exists());
		return image;
	}

	
	public static BasicPoi POI_CASA_CAMPO =  newPoi("Casa de Campo").setLocation(GEO_CASA_CAMPO).setAddress(new Address().setAdminArea1("Comunidad de Madrid"));
	public static BasicPoi POI_RETIRO = newPoi("Retiro").setLocation(GEO_RETIRO).setAddress(new Address().setAdminArea1("Comunidad de Madrid"));
	public static BasicPoi POI_SOL = newPoi("Sol").setLocation(GEO_SOL).setAddress(new Address().setAdminArea1("Comunidad de Madrid"));
	public static BasicPoi POI_TEIDE = newPoi("Teide").setLocation(GEO_TEIDE).setAddress(new Address().setAdminArea1("Canarias").setAdminArea2("Tenerife"));
	public static BasicPoi POI_PLAYA_TERESITAS= newPoi("Playa de las Teresitas").setLocation(GEO_PLAYA_TERESITAS).setAddress(new Address().setAdminArea1("Canarias").setAdminArea2("Tenerife"));
	public static BasicPoi POI_ROQUE_NUBLO= newPoi("Roque Nublo").setLocation(GEO_ROQUE_NUBLO).setAddress(new Address().setAdminArea1("Canarias").setAdminArea2("Gran Canaria"));
	public static BasicPoi POI_ALASKA = newPoi("Alaska").setLocation(GEO_ALASKA);
	
	public static ImmutableSet<BasicPoi> WELL_KNOWN_POIS = ImmutableSet.of(POI_CASA_CAMPO, POI_RETIRO, POI_SOL, POI_TEIDE, POI_ALASKA, POI_PLAYA_TERESITAS, POI_ROQUE_NUBLO);
	
	public static LodgingPoi HOTEL;
	public static LodgingPoi CAMPING;
	public static LodgingPoi APARTMENT;
	public static BeachPoiType BEACH;
	public static CulturePoiType MUSEUM;
	public static CulturePoiType MONUMENT;
	public static NaturalSpacePoiType NATURAL_PARK;
	

	
	static {
		// Hotel
		HOTEL = new LodgingPoi()
			.setPoiType(PoiTypeID.HOTEL)
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
			.setRoomTypes(
                RoomType.HAB1,
                RoomType.HAB2)
			.setLodgingPrices(
				new RoomPrice(RoomType.HAB1, Season.HIGH_SEASON, Regime.AD, 30d),
				new RoomPrice(RoomType.HAB1, Season.MEDIUM_SEASON, Regime.AD, 25d),
				new RoomPrice(RoomType.HAB1, Season.LOW_SEASON, Regime.AD, 20d))
			.setLodgingTypeFlags(
				RoomFlag.JACUZZI,
				RoomFlag.SAFE_BOX)
			.setDisabledAccessibility(
				AccessibilityFlag.ADAPTED_ROOMS,
				AccessibilityFlag.LIFT_ACCESSIBLE,
				AccessibilityFlag.ADAPTED_VEHICLE_RENT,
				AccessibilityFlag.PARKING_ACCESSIBLE,
				AccessibilityFlag.ASSISTANCE_TO_DISABLED,
				AccessibilityFlag.GUIDE_DOG_ALLOWED
			);
		
		HOTEL.validateTypeAllowedValues();
		
	
		// Camping
		CAMPING = new LodgingPoi()
			.setPoiType(PoiTypeID.CAMPING)
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
			.setRoomTypes(
                RoomType.TENT,
                RoomType.TENT_FAM)
			.setLodgingPrices(
				new RoomPrice(RoomType.TENT, Season.HIGH_SEASON, Regime.AD, 30d),
				new RoomPrice(RoomType.TENT, Season.MEDIUM_SEASON, Regime.AD, 25d),
				new RoomPrice(RoomType.TENT, Season.LOW_SEASON, Regime.AD, 20d))
			.setDisabledAccessibility(
				AccessibilityFlag.ADAPTED_ROOMS,
				AccessibilityFlag.LIFT_ACCESSIBLE,
				AccessibilityFlag.ADAPTED_VEHICLE_RENT,
				AccessibilityFlag.PARKING_ACCESSIBLE,
				AccessibilityFlag.ASSISTANCE_TO_DISABLED,
				AccessibilityFlag.GUIDE_DOG_ALLOWED
			);
		
		CAMPING.validateTypeAllowedValues();

		

		// Apartmnent
		APARTMENT = new LodgingPoi()
			.setPoiType(PoiTypeID.APARTMENT)
			.setName(new I18nText().setEs("Apartamentos Bahía azul"))				// required
			.setDescription(new I18nText().setEs("Descripción del apartamento..."))	// required	
			.setLocation(randomLocation())											// required
			.setScores(Score.KEY_1)
			.setLodgingFlags(
				LodgingFlag.CREDIT_CARD 
				)
			.setRoomTypes(
                RoomType.HAB1,
                RoomType.HAB2)
			.setLodgingPrices(
				new RoomPrice(RoomType.HAB1, Season.HIGH_SEASON, Regime.AD, 30d),
				new RoomPrice(RoomType.HAB1, Season.MEDIUM_SEASON, Regime.AD, 25d),
				new RoomPrice(RoomType.HAB1, Season.LOW_SEASON, Regime.AD, 20d),
				new RoomPrice(RoomType.HAB2, Season.HIGH_SEASON, Regime.AD, 60d),
				new RoomPrice(RoomType.HAB2, Season.MEDIUM_SEASON, Regime.AD, 50d),
				new RoomPrice(RoomType.HAB2, Season.LOW_SEASON, Regime.AD, 40d))
			.setDisabledAccessibility(
				AccessibilityFlag.ADAPTED_ROOMS,
				AccessibilityFlag.LIFT_ACCESSIBLE
			);
		
		APARTMENT.validateTypeAllowedValues();
		
		
		
		// Beach
		BEACH = new BeachPoiType()
			.setName(new I18nText().setEs("Playa de las teresitas"))			// required
			.setDescription(new I18nText().setEs("Descripción de la playa..."))	// required	
			.setLocation(AbstractFactory.GEO_PLAYA_TERESITAS)					// required
			.setAddress(new Address().setRoute("Las teresitas").setAdminArea1("Canarias").setAdminArea2("Tenerife"))
			.setQualityCertificates(
					QualityCertificateFlag.BANDERA_AZUL,
					QualityCertificateFlag.NATURISTA)
			.setLarge(100d)
			.setWidth(20d)
			.setAnchorZone(Boolean.TRUE)
			.setBathCondition(BathCondition.MODERATE_WAVES)
			.setComposition(BeachComposition.VOLCANIC_BLACK_SAND)
			.setSandType(SandType.DARK)
			.setDisabledAccessibility(
					AccessibilityFlag.PARKING_ACCESSIBLE,
					AccessibilityFlag.ASSISTANCE_TO_DISABLED,
					AccessibilityFlag.GUIDE_DOG_ALLOWED
			);
		
		BEACH.validateTypeAllowedValues();
		

		// Museum
		MUSEUM = new CulturePoiType()
			.setPoiType(PoiTypeID.MUSEUM)
			.setName(new I18nText().setEs("Museo del Prado"))					// required
			.setDescription(new I18nText().setEs("Descripción del museo"))		// required	
			.setLocation(randomLocation())					// required
			.setContactInfo( new ContactInfo()
				.setEmail("info@mueseodelprado.com")	
				.setUrl("http://www.museodelprado.com")
				.setPhone("+34 000000000")
			)
			.setDesignation(Designation.NATIONAL_MUSEUM)
			.setFlags(Flag.GUIDED_TOUR)
			.setDisabledAccessibility(
					AccessibilityFlag.PARKING_ACCESSIBLE,
					AccessibilityFlag.ASSISTANCE_TO_DISABLED,
					AccessibilityFlag.GUIDE_DOG_ALLOWED
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
					.setPriceTypes(PriceType.GENERAL, PriceType.GROUPS)
					.setPrice(14d),
				new CulturePrice()
					.setPriceTypes(PriceType.REDUCED)
					.setPrice(7d),
				new CulturePrice()
					.setPriceTypes(PriceType.STUDENT)
					.setPrice(10d),
				new CulturePrice()
					.setPriceTypes(PriceType.FREE)
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
			.setPoiType(PoiTypeID.MONUMENT)
			.setName(new I18nText().setEs("La Alhambra"))						// required
			.setDescription(new I18nText().setEs("Descripción del monumento"))	// required	
			.setLocation(randomLocation())										// required
			.setContactInfo(new ContactInfo()
                .setEmail("info@lahalambra.com")
                .setUrl("http://www.lahalambra.com")
                .setPhone("+34 000000000")
            )
			.setConstructionType(ConstructionType.PALACE)
			.setArtisticPeriod( ArtisticPeriod.ARABIC)
			.setHistoricalPeriod( HistoricalPeriod.CENTURY_14)
			.setEnviroment(new I18nText().setEs("El Generalife").setEn("The Generalife"))
			.setQualityCertificates( QualityCertificateFlag.PATRIMONIO_HUMANIDAD )
			.setFlags(Flag.GUIDED_TOUR)
			.setDisabledAccessibility(
					AccessibilityFlag.PARKING_ACCESSIBLE,
					AccessibilityFlag.ASSISTANCE_TO_DISABLED,
					AccessibilityFlag.GUIDE_DOG_ALLOWED
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
					.setPriceTypes(PriceType.GENERAL, PriceType.GROUPS)
					.setPrice(14d),
				new CulturePrice()
					.setPriceTypes(PriceType.REDUCED)
					.setPrice(7d),
				new CulturePrice()
					.setPriceTypes(PriceType.STUDENT)
					.setPrice(10d),
				new CulturePrice()
					.setPriceTypes(PriceType.FREE)
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
		
		
		
		// Natural Park
		NATURAL_PARK = new NaturalSpacePoiType()
			.setName(new I18nText().setEs("Parque nacional del Teide"))			// required
			.setDescription(new I18nText().setEs("Descripción del parque"))		// required	
			.setLocation(AbstractFactory.GEO_TEIDE)								// required
			.setAddress(new Address().setRoute("Las cañadas").setAdminArea1("Canarias").setAdminArea2("Tenerife"))
			.setQualityCertificates(
					QualityCertificateFlag.PATRIMONIO_HUMANIDAD)
			.setDisabledAccessibility(
					AccessibilityFlag.PARKING_ACCESSIBLE,
					AccessibilityFlag.ASSISTANCE_TO_DISABLED,
					AccessibilityFlag.GUIDE_DOG_ALLOWED)
			.setNaturalSpaceFlags(
					NaturalSpaceFlag.BIOSPHERE_RESERVE,
					NaturalSpaceFlag.NATIONAL_PARK
					);
		
		NATURAL_PARK.validateTypeAllowedValues();
		
	}
	
	
}