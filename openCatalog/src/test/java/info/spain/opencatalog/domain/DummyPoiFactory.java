package info.spain.opencatalog.domain;

import info.spain.opencatalog.domain.poi.AbstractPoi;
import info.spain.opencatalog.domain.poi.AccessPrice;
import info.spain.opencatalog.domain.poi.AccessibilityFlag;
import info.spain.opencatalog.domain.poi.ContactInfo;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.HourRange;
import info.spain.opencatalog.domain.poi.PoiTypes;
import info.spain.opencatalog.domain.poi.QualityCertificateFlag;
import info.spain.opencatalog.domain.poi.TimeTableDay;
import info.spain.opencatalog.domain.poi.TimeTableEntry;
import info.spain.opencatalog.domain.poi.TimeTableEntry.WeekDay;
import info.spain.opencatalog.domain.poi.beach.BathCondition;
import info.spain.opencatalog.domain.poi.beach.Beach;
import info.spain.opencatalog.domain.poi.beach.BeachComposition;
import info.spain.opencatalog.domain.poi.beach.SandType;
import info.spain.opencatalog.domain.poi.culture.ArtisticPeriod;
import info.spain.opencatalog.domain.poi.culture.ConstructionType;
import info.spain.opencatalog.domain.poi.culture.Culture;
import info.spain.opencatalog.domain.poi.culture.Designation;
import info.spain.opencatalog.domain.poi.culture.HistoricalPeriod;
import info.spain.opencatalog.domain.poi.culture.PriceType;
import info.spain.opencatalog.domain.poi.lodging.BusinessServiceFlag;
import info.spain.opencatalog.domain.poi.lodging.Lodging;
import info.spain.opencatalog.domain.poi.lodging.Regime;
import info.spain.opencatalog.domain.poi.lodging.RoomFlag;
import info.spain.opencatalog.domain.poi.lodging.RoomPrice;
import info.spain.opencatalog.domain.poi.lodging.RoomType;
import info.spain.opencatalog.domain.poi.lodging.Score;
import info.spain.opencatalog.domain.poi.lodging.Season;
import info.spain.opencatalog.domain.poi.nature.NaturalSpace;
import info.spain.opencatalog.domain.poi.nature.NaturalSpaceFlag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTimeConstants;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class DummyPoiFactory extends AbstractFactory {
	
	public static AbstractPoi newPoi(String key){
		key = key + "-" + getRandom().nextInt();
		return  new Lodging(PoiTypes.HOTEL)
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
			.setFlags(randomFlags(getRandom().nextInt(Flag.values().length)));
		}
	
	private static Flag[] randomFlags(int numFlags){
		Set<Flag> result = Sets.newHashSet();
		for(int i=0; i< numFlags; i++){
			Flag flag = Flag.values()[(getRandom().nextInt(Flag.values().length))];
			result.add(flag);
		}
		return result.toArray(new Flag[] {});
	}
	
	
	public static List<AbstractPoi> generatePois(int numPois){
		// random
		List<AbstractPoi> result = new ArrayList<AbstractPoi>();
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

	
	public static AbstractPoi POI_CASA_CAMPO =  newPoi("Casa de Campo").setLocation(GEO_CASA_CAMPO).setAddress(new Address().setAdminArea1("Comunidad de Madrid"));
	public static AbstractPoi POI_RETIRO = newPoi("Retiro").setLocation(GEO_RETIRO).setAddress(new Address().setAdminArea1("Comunidad de Madrid"));
	public static AbstractPoi POI_SOL = newPoi("Sol").setLocation(GEO_SOL).setAddress(new Address().setAdminArea1("Comunidad de Madrid"));
	public static AbstractPoi POI_TEIDE = newPoi("Teide").setLocation(GEO_TEIDE).setAddress(new Address().setAdminArea1("Canarias").setAdminArea2("Tenerife"));
	public static AbstractPoi POI_PLAYA_TERESITAS= newPoi("Playa de las Teresitas").setLocation(GEO_PLAYA_TERESITAS).setAddress(new Address().setAdminArea1("Canarias").setAdminArea2("Tenerife"));
	public static AbstractPoi POI_ROQUE_NUBLO= newPoi("Roque Nublo").setLocation(GEO_ROQUE_NUBLO).setAddress(new Address().setAdminArea1("Canarias").setAdminArea2("Gran Canaria"));
	public static AbstractPoi POI_ALASKA = newPoi("Alaska").setLocation(GEO_ALASKA);
	
	public static ImmutableSet<AbstractPoi> WELL_KNOWN_POIS = ImmutableSet.of(POI_CASA_CAMPO, POI_RETIRO, POI_SOL, POI_TEIDE, POI_ALASKA, POI_PLAYA_TERESITAS, POI_ROQUE_NUBLO);
	
	public static Lodging HOTEL;
	public static Lodging CAMPING;
	public static Lodging APARTMENT;
	
	public static Beach   BEACH;
	public static NaturalSpace NATURAL_PARK;
	
	public static Culture MUSEUM;
	public static Culture MONUMENT;
	public static Culture GARDEN;
	

	
	static {
		// Hotel
		HOTEL = new Lodging(PoiTypes.HOTEL)
			.setName(new I18nText().setEs("Hotel Puerta del Sol"))				// required
			.setDescription(new I18nText().setEs("Descripción del hotel..."))	// required	
			.setLocation(AbstractFactory.GEO_SOL)								// required
			.setAddress(new Address().setRoute("Puerta del Sol").setAdminArea1("Comunidad de Madrid").setAdminArea2("Madrid"))
			.setScore(Score.STAR_3)
			.setContactInfo( new ContactInfo()
				.setEmail("info@hotelpuertadelsol.com")	
				.setUrl("http://www.hotelpuertadelsol.com")
				.setPhone("+34 000000000")
			)
			.setFlags(
				Flag.CASINO, 
				Flag.CREDIT_CARD, 
				Flag.EXCHANGE)
			.setBusinessServiceFlags(
				BusinessServiceFlag.AUDIOVISUAL_RENT,
				BusinessServiceFlag.CONVENTION_ROOM
					)
			.setRoomTypes(
                RoomType.HAB1,
                RoomType.HAB2)
			.setRoomPrices(
				new RoomPrice(RoomType.HAB1, Season.HIGH_SEASON, Regime.AD, 30d),
				new RoomPrice(RoomType.HAB1, Season.MEDIUM_SEASON, Regime.AD, 25d),
				new RoomPrice(RoomType.HAB1, Season.LOW_SEASON, Regime.AD, 20d))
			.setRoomFlags(
				RoomFlag.JACUZZI,
				RoomFlag.SAFE_BOX)
			.setAccessibilityFlags(
				AccessibilityFlag.ADAPTED_ROOMS,
				AccessibilityFlag.LIFT_ACCESSIBLE,
				AccessibilityFlag.ADAPTED_VEHICLE_RENT,
				AccessibilityFlag.PARKING_ACCESSIBLE,
				AccessibilityFlag.ASSISTANCE_TO_DISABLED,
				AccessibilityFlag.GUIDE_DOG_ALLOWED
			);
		
		HOTEL.validate();
		
	
		// Camping
		CAMPING = new Lodging(PoiTypes.CAMPING)
			.setName(new I18nText().setEs("CAMPING Montaña Rajada"))				// required
			.setDescription(new I18nText().setEs("Descripción del CAMPING..."))	// required	
			.setLocation(AbstractFactory.GEO_CASA_CAMPO)								// required
			.setAddress(new Address().setRoute("Casa de Campo").setAdminArea1("Comunidad de Madrid").setAdminArea2("Madrid"))
			.setScore(Score.CAT_1)
			.setFlags(
				Flag.BBQ,
				Flag.BIKE_RENT,
				Flag.CLOACKROOM,
				Flag.CREDIT_CARD 
				)
			.setRoomTypes(
                RoomType.TENT,
                RoomType.TENT_FAM)
			.setRoomPrices(
				new RoomPrice(RoomType.TENT, Season.HIGH_SEASON, Regime.AD, 30d),
				new RoomPrice(RoomType.TENT, Season.MEDIUM_SEASON, Regime.AD, 25d),
				new RoomPrice(RoomType.TENT, Season.LOW_SEASON, Regime.AD, 20d))
			.setAccessibilityFlags(
				AccessibilityFlag.ADAPTED_ROOMS,
				AccessibilityFlag.LIFT_ACCESSIBLE,
				AccessibilityFlag.ADAPTED_VEHICLE_RENT,
				AccessibilityFlag.PARKING_ACCESSIBLE,
				AccessibilityFlag.ASSISTANCE_TO_DISABLED,
				AccessibilityFlag.GUIDE_DOG_ALLOWED
			);
		
		CAMPING.validate();

		

		// Apartmnent
		APARTMENT = new Lodging(PoiTypes.APARTMENT)
			.setName(new I18nText().setEs("Apartamentos Bahía azul"))				// required
			.setDescription(new I18nText().setEs("Descripción del apartamento..."))	// required	
			.setLocation(randomLocation())											// required
			.setScore(Score.KEY_1)
			.setFlags(
				Flag.CREDIT_CARD 
				)
			.setRoomTypes(
                RoomType.HAB1,
                RoomType.HAB2)
			.setRoomPrices(
				new RoomPrice(RoomType.HAB1, Season.HIGH_SEASON, Regime.AD, 30d),
				new RoomPrice(RoomType.HAB1, Season.MEDIUM_SEASON, Regime.AD, 25d),
				new RoomPrice(RoomType.HAB1, Season.LOW_SEASON, Regime.AD, 20d),
				new RoomPrice(RoomType.HAB2, Season.HIGH_SEASON, Regime.AD, 60d),
				new RoomPrice(RoomType.HAB2, Season.MEDIUM_SEASON, Regime.AD, 50d),
				new RoomPrice(RoomType.HAB2, Season.LOW_SEASON, Regime.AD, 40d))
			.setAccessibilityFlags(
				AccessibilityFlag.ADAPTED_ROOMS,
				AccessibilityFlag.LIFT_ACCESSIBLE
			);
		
		APARTMENT.validate();
		
		
		
		// Beach
		BEACH = new Beach(PoiTypes.BEACH)
			.setName(new I18nText().setEs("Playa de las teresitas"))			// required
			.setDescription(new I18nText().setEs("Descripción de la playa..."))	// required	
			.setLocation(AbstractFactory.GEO_PLAYA_TERESITAS)					// required
			.setAddress(new Address().setRoute("Las teresitas").setAdminArea1("Canarias").setAdminArea2("Tenerife"))
			.setQualityCertificates(
				QualityCertificateFlag.BANDERA_AZUL,
				QualityCertificateFlag.NATURISTA)
			.setLongitude(100d)
			.setWidth(20d)
			.setAnchorZone(Boolean.TRUE)
			.setBathCondition(BathCondition.MODERATE_WAVES)
			.setComposition(BeachComposition.VOLCANIC_BLACK_SAND)
			.setSandType(SandType.DARK)
			.setAccessibilityFlags(
				AccessibilityFlag.PARKING_ACCESSIBLE,
				AccessibilityFlag.ASSISTANCE_TO_DISABLED,
				AccessibilityFlag.GUIDE_DOG_ALLOWED
			);
		
		BEACH.validate();
		

		// Museum 
		MUSEUM = new Culture(PoiTypes.MUSEUM)
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
			.setAccessibilityFlags(
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
				new AccessPrice()
					.setPriceTypes(PriceType.GENERAL, PriceType.GROUPS)
					.setPrice(14d),
				new AccessPrice()
					.setPriceTypes(PriceType.REDUCED)
					.setPrice(7d),
				new AccessPrice()
					.setPriceTypes(PriceType.STUDENT)
					.setPrice(10d),
				new AccessPrice()
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
		
		MUSEUM.validate();
	
		

		// MONUMENTO
		MONUMENT = new Culture(PoiTypes.MONUMENT)
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
			.setAccessibilityFlags(
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
				new AccessPrice()
					.setPriceTypes(PriceType.GENERAL, PriceType.GROUPS)
					.setPrice(14d),
				new AccessPrice()
					.setPriceTypes(PriceType.REDUCED)
					.setPrice(7d),
				new AccessPrice()
					.setPriceTypes(PriceType.STUDENT)
					.setPrice(10d),
				new AccessPrice()
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
		
		MONUMENT.validate();
		
	
		// GARDEN 
		GARDEN = new Culture(PoiTypes.PARK_GARDEN)
			.setName(new I18nText().setEs("Parque andalusí"))			// required
			.setDescription(new I18nText().setEs("Descripción del parque"))		// required	
			.setLocation(AbstractFactory.GEO_TEIDE)								// required
			.setQualityCertificates(
				QualityCertificateFlag.PATRIMONIO_HUMANIDAD)
			.setAccessibilityFlags(
				AccessibilityFlag.PARKING_ACCESSIBLE,
				AccessibilityFlag.ASSISTANCE_TO_DISABLED,
				AccessibilityFlag.GUIDE_DOG_ALLOWED)
			.setArtisticPeriod(ArtisticPeriod.ANDALUSI);
		
		GARDEN.validate();
		
		// Natural Park
		NATURAL_PARK = new NaturalSpace(PoiTypes.NATURAL_SPACE)
			.setName(new I18nText().setEs("Parque nacional del Timanfaya")) 	// required
			.setDescription(new I18nText().setEs("Descripción del parque"))		// required	
			.setLocation(AbstractFactory.GEO_TEIDE)								// required
			.setAddress(new Address()
				.setRoute("Timanfaya")
				.setAdminArea1("Canarias")
				.setAdminArea2("Lanzarote"))
			.setQualityCertificates(
				QualityCertificateFlag.PATRIMONIO_HUMANIDAD)
			.setAccessibilityFlags(
				AccessibilityFlag.PARKING_ACCESSIBLE,
				AccessibilityFlag.ASSISTANCE_TO_DISABLED,
				AccessibilityFlag.GUIDE_DOG_ALLOWED)
			.setNaturalSpaceFlags(
				NaturalSpaceFlag.BIOSPHERE_RESERVE,
				NaturalSpaceFlag.NATIONAL_PARK)
			.setPrices( 
				new AccessPrice()
					.setPrice(25d)
					.setPriceTypes(PriceType.ADULT)
					.setTimetable( 
						new TimeTableEntry()
							.setHourRange(new HourRange("09:00", "20:00"))
					)
			);
		
		NATURAL_PARK.validate();
		
	
		
	}
	
	
}