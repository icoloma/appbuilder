package info.spain.opencatalog.domain;

import info.spain.opencatalog.domain.poi.BasicPoi;
import info.spain.opencatalog.domain.poi.ContactInfo;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.FlagGroup;
import info.spain.opencatalog.domain.poi.Price;
import info.spain.opencatalog.domain.poi.PriceType;
import info.spain.opencatalog.domain.poi.Score;
import info.spain.opencatalog.domain.poi.SyncInfo;
import info.spain.opencatalog.domain.poi.TimeTableEntry;
import info.spain.opencatalog.domain.poi.lodging.Lodging;
import info.spain.opencatalog.domain.poi.lodging.Meal;
import info.spain.opencatalog.domain.poi.lodging.RoomPrice;
import info.spain.opencatalog.domain.poi.lodging.RoomType;
import info.spain.opencatalog.domain.poi.types.PoiFactory;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class DummyPoiFactory extends AbstractFactory {
	
	
	public static BasicPoi newPoi(String key){
		key = key + "-" + getRandom().nextInt();
		return  PoiFactory.newInstance(PoiTypeID.BASIC)
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
			.setFlags(randomFlags());
		}
	
	private static Flag[] randomFlags(){
		List<Flag> validRandomFlags = Lists.newArrayList(FlagGroup.COMMON.getFlags());
		int size = validRandomFlags.size();
		int numFlags = getRandom().nextInt(size/2); 
		Set<Flag> result = Sets.newHashSet();
		for(int i=0; i< numFlags; i++){
			Flag flag = validRandomFlags.get((getRandom().nextInt(size)));
			result.add(flag);
		}
		return result.toArray(new Flag[] {});
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

	
	public static final BasicPoi POI_CASA_CAMPO =  newPoi("Casa de Campo").setLocation(GEO_CASA_CAMPO).setAddress(new Address().setAdminArea1("Comunidad de Madrid"));
	public static final BasicPoi POI_RETIRO = newPoi("Retiro").setLocation(GEO_RETIRO).setAddress(new Address().setAdminArea1("Comunidad de Madrid"));
	public static final BasicPoi POI_SOL = newPoi("Sol").setLocation(GEO_SOL).setAddress(new Address().setAdminArea1("Comunidad de Madrid"));
	public static final BasicPoi POI_TEIDE = newPoi("Teide").setLocation(GEO_TEIDE).setAddress(new Address().setAdminArea1("Canarias").setAdminArea2("Tenerife"));
	public static final BasicPoi POI_PLAYA_TERESITAS= newPoi("Playa de las Teresitas").setLocation(GEO_PLAYA_TERESITAS).setAddress(new Address().setAdminArea1("Canarias").setAdminArea2("Tenerife"));
	public static final BasicPoi POI_ROQUE_NUBLO= newPoi("Roque Nublo").setLocation(GEO_ROQUE_NUBLO).setAddress(new Address().setAdminArea1("Canarias").setAdminArea2("Gran Canaria"));
	public static final BasicPoi POI_ALASKA = newPoi("Alaska").setLocation(GEO_ALASKA);
	
	public static final ImmutableSet<BasicPoi> WELL_KNOWN_POIS = ImmutableSet.of(POI_CASA_CAMPO, POI_RETIRO, POI_SOL, POI_TEIDE, POI_ALASKA, POI_PLAYA_TERESITAS, POI_ROQUE_NUBLO);
	
	public static final BasicPoi BEACH = beach();
	public static final BasicPoi NATURAL_PARK = naturalPark();
	
	public static final Lodging HOTEL = hotel();
	public static final Lodging CAMPING = camping();
	public static final Lodging APARTMENT = apartment();
	
	public static final BasicPoi MUSEUM = museum();
	public static final BasicPoi MONUMENT = monument();
	public static final BasicPoi GARDEN = garden();
	
	public static final BasicPoi ECO_TOURISM = ecoTourism();
	public static final BasicPoi GOLF = golf();
	public static final BasicPoi NAUTICAL_STATION = nauticalStation();
	public static final BasicPoi SKI_STATION = skiStation();
	

	
	// Hotel
	public static Lodging hotel(){
		return ((Lodging)PoiFactory.newInstance( PoiTypeID.HOTEL))
			.setName(new I18nText().setEs("Hotel Puerta del Sol"))				// required
			.setLocation(AbstractFactory.GEO_SOL)								// required
			.setDescription(new I18nText().setEs("Descripción del hotel..."))	
			.setAddress(new Address().setRoute("Puerta del Sol").setAdminArea1("Comunidad de Madrid").setAdminArea2("Madrid"))
			.setScore(Score.STAR_3)
			.setContactInfo( new ContactInfo()
				.setEmail("info@hotelpuertadelsol.com")	
				.setUrl("http://www.hotelpuertadelsol.com")
				.setPhone("+34 000000000")
			)
			.setFlags(
				Flag.EXCHANGE,
				Flag.CREDIT_CARD, 
				Flag.CASINO, 
				Flag.JACUZZI,
				Flag.ACCESSIBILITY_ADAPTED_ROOMS,
				Flag.LIFT_ACCESSIBLE,
				Flag.ADAPTED_VEHICLE_RENT,
				Flag.PARKING_ACCESSIBLE,
				Flag.ASSISTANCE_TO_HANDICAPPED,
				Flag.GUIDE_DOG_ALLOWED,
				Flag.FAX,
				Flag.AUDIOVISUAL_RENT,
				Flag.CONVENTION_ROOM
					)
			.setRoomTypes(
                RoomType.HAB1,
                RoomType.HAB2)
			.setPrices(
				new RoomPrice(RoomType.HAB1, Meal.AD, 30d).setObservations( new I18nText().setEs("Semana santa")),
				new RoomPrice(RoomType.HAB1, Meal.AD, 25d).setObservations( new I18nText().setEs("Temporada alta"))
			)
			.validate();
	}	
	
	// Camping
	public static Lodging camping(){
		return ((Lodging) PoiFactory.newInstance(PoiTypeID.CAMPING))
			.setName(new I18nText().setEs("CAMPING Montaña Rajada"))				// required
			.setLocation(AbstractFactory.GEO_CASA_CAMPO)								// required
			.setDescription(new I18nText().setEs("Descripción del CAMPING..."))		
			.setAddress(new Address().setRoute("Casa de Campo").setAdminArea1("Comunidad de Madrid").setAdminArea2("Madrid"))
			.setScore(Score.CAT_1)
			.setFlags(
				Flag.BBQ,
				Flag.BIKE_RENT,
				Flag.LOCKER,
				Flag.CREDIT_CARD ,
				Flag.GUIDE_DOG_ALLOWED
				)
			.setRoomTypes(
                RoomType.TENT,
                RoomType.TENT_FAM)
			.setPrices(
				new RoomPrice(RoomType.TENT,  Meal.AD, 30d).setObservations( new I18nText().setEs("Temporada alta")),
				new RoomPrice(RoomType.TENT, Meal.AD, 25d).setObservations( new I18nText().setEs("Temporada media")),
				new RoomPrice(RoomType.TENT, Meal.AD, 20d).setObservations( new I18nText().setEs("Temporada baja"))
			)
			
			.validate();
	}

		
	
	// Apartmnent
	public static Lodging apartment(){
		return ((Lodging) PoiFactory.newInstance(PoiTypeID.APARTMENT))
			.setName(new I18nText().setEs("Apartamentos Bahía azul"))				// required
			.setLocation(randomLocation())											// required
			.setDescription(new I18nText().setEs("Descripción del apartamento..."))	
			.setAddress(new Address().setRoute("Casa de Campo").setAdminArea1("Comunidad de Madrid").setAdminArea2("Madrid"))
			.setScore(Score.KEY_1)
			.setFlags(
				Flag.CREDIT_CARD, 
				Flag.ACCESSIBILITY_ADAPTED_ROOMS,
				Flag.LIFT_ACCESSIBLE,
				Flag.ADAPTED_VEHICLE_RENT,
				Flag.PARKING_ACCESSIBLE,
				Flag.ASSISTANCE_TO_HANDICAPPED,
				Flag.GUIDE_DOG_ALLOWED
				)
			.setRoomTypes(
                RoomType.HAB1,
                RoomType.HAB2)
			.setPrices(
				new RoomPrice(RoomType.HAB1,  Meal.AD, 30d).setObservations( new I18nText().setEs("Temporada alta")),
				new RoomPrice(RoomType.HAB1, Meal.AD, 25d).setObservations( new I18nText().setEs("Temporada media")),
				new RoomPrice(RoomType.HAB1, Meal.AD, 20d).setObservations( new I18nText().setEs("Temporada baja"))
			)	
			.validate();
	}
		
		
		
	// Beach
	public static BasicPoi beach(){
		return ((BasicPoi) PoiFactory.newInstance(PoiTypeID.BEACH))
			.setName(new I18nText().setEs("Playa de las teresitas"))			// required
			.setLocation(AbstractFactory.GEO_PLAYA_TERESITAS)					// required
			.setDescription(new I18nText().setEs("Descripción de la playa..."))		
			.setAddress(new Address().setRoute("Las teresitas").setAdminArea1("Canarias").setAdminArea2("Tenerife"))
			.setFlags(
				Flag.BEACH_BATH_CONDITION_MODERATE_WAVES,
				Flag.BEACH_COMPOSITION_VOLCANIC_BLACK_SAND,
				Flag.BEACH_SAND_TYPE_DARK)
			.setData("longitude","100.0")
			.setData("width", "200")
			.setData("anchorZone", "true")
			.setPrices(
				new Price()
					.setPriceType(PriceType.HANDICAPPED)
					.setTimetable(new TimeTableEntry("Mon,Tue,Wed,Thu,Fri=09:00-13:00,15:00-20:00"))
					.setPrice(14d),
				new Price()
					.setPrice(0d)
					.setPriceType(PriceType.FREE)
					.setTimetable(new TimeTableEntry("Mon,Tue,Wed,Thu,Fri=18:00-20:00"))
					.setObservations(new I18nText().setEs("Desempleados, personal de los Museos Estatales del Ministerio de Cultura"))
			).setContactInfo( new ContactInfo()
				.setUrl("http://www.playadelasteresitas.com")
				.setPhone("+34922000000")
				.setReservation("Reservar llamando al teléfono de contacto"))
			.setSyncInfo(new SyncInfo()
				.setImported(true)
				.setSync(true)
				.setOriginalId("xxxx-yyyy")
				.setLastUpdate(new DateTime())
				)
			.validate();
	}
		

	// Museum
	public static BasicPoi museum() {
		return ((BasicPoi) PoiFactory.newInstance(PoiTypeID.MUSEUM))
			.setName(new I18nText().setEs("Museo del Prado"))					// required
			.setAddress(new Address().setRoute("Casa de Campo").setAdminArea1("Comunidad de Madrid").setAdminArea2("Madrid"))
			.setLocation(randomLocation())					// required
			.setDescription(new I18nText().setEs("Descripción del museo"))			
			.setContactInfo( new ContactInfo()
				.setEmail("info@mueseodelprado.com")	
				.setUrl("http://www.museodelprado.com")
				.setPhone("+34 000000000")
			)
			.setFlags(
				Flag.GUIDED_TOUR,
				Flag.CULTURE_DESIGNATION_NATIONAL_MUSEUM,
				Flag.LIFT_ACCESSIBLE,
				Flag.ASSISTANCE_TO_HANDICAPPED,
				Flag.GUIDE_DOG_ALLOWED
			)
			.setTimetable(
				new TimeTableEntry("Mon,Wed,Fri=09:00-13:00,15:00-20:00"),
				new TimeTableEntry("0601,2412=10:00-14:00"),
				new TimeTableEntry("0101,2512=")
			)
			.setPrices(
				new Price()
					.setPriceType(PriceType.GENERAL)
					.setTimetable(new TimeTableEntry("Mon,Tue,Wed,Thu,Fri=09:00-13:00,15:00-20:00"))
					.setPrice(14d),
				new Price()
					.setPriceType(PriceType.REDUCED)
					.setTimetable(new TimeTableEntry("Mon,Tue,Wed,Thu,Fri=09:00-13:00,15:00-20:00"))
					.setPrice(7d),
				new Price()
					.setPriceType(PriceType.STUDENT)
					.setTimetable(new TimeTableEntry("Mon,Tue,Wed,Thu,Fri=09:00-13:00,15:00-20:00"))
					.setPrice(10d),
				new Price()
					.setPrice(0d)
					.setPriceType(PriceType.FREE)
					.setTimetable(new TimeTableEntry("Mon,Tue,Wed,Thu,Fri=18:00-20:00"))
					.setObservations(new I18nText().setEs("Desempleados, personal de los Museos Estatales del Ministerio de Cultura"))
			)
			.validate();
	
	}
	
		

	// MONUMENTO
	public static BasicPoi monument() {
		return ((BasicPoi)PoiFactory.newInstance(PoiTypeID.MONUMENT))
			.setName(new I18nText().setEs("La Alhambra"))						// required
			.setLocation(randomLocation())										// required
			.setDescription(new I18nText().setEs("Descripción del monumento"))		
			.setAddress(new Address().setRoute("Casa de Campo").setAdminArea1("Comunidad de Madrid").setAdminArea2("Madrid"))
			.setContactInfo(new ContactInfo()
                .setEmail("info@lahalambra.com")
                .setUrl("http://www.lahalambra.com")
                .setPhone("+34 000000000")
            )
			.setEnviroment(new I18nText().setEs("El Generalife").setEn("The Generalife"))
			.setFlags(
				Flag.CULTURE_TYPE_PALACE,
				Flag.CULTURE_PERIOD_ARABIC,
				Flag.CULTURE_HISTORICAL_CENTURY_14,
				Flag.GUIDED_TOUR,
				Flag.QUALITY_PATRIMONIO_HUMANIDAD,
				Flag.PARKING_ACCESSIBLE
			)
			.setTimetable(
					new TimeTableEntry(""),
					new TimeTableEntry("0601,2412=10:00-12:00"),
					new TimeTableEntry("0101,2512=")
			)
			.setPrices(
				new Price()
					.setTimetable(new TimeTableEntry(""))
					.setPriceType(PriceType.GROUPS)
					.setPrice(14d),
				new Price()
					.setTimetable(new TimeTableEntry(""))
					.setPriceType(PriceType.REDUCED)
					.setPrice(7d),
				new Price()
					.setTimetable(new TimeTableEntry(""))
					.setPriceType(PriceType.STUDENT)
					.setPrice(10d),
				new Price()
					.setPriceType(PriceType.FREE)
					.setPrice(0d)
					.setObservations(new I18nText()
						.setEs("Desempleados, personal de los Museos Estatales del Ministerio de Cultura")
						.setEn("Unemployed, State museums staff"))
					.setTimetable(new TimeTableEntry("Mon,Tue,Wed,Thu,Fri=18:00-20:00"))
			).validate();
	}
		
	
	// GARDEN 
	public static BasicPoi garden() {
		return ((BasicPoi)PoiFactory.newInstance(PoiTypeID.PARK_GARDEN))
			.setAddress(new Address().setRoute("Casa de Campo").setAdminArea1("Comunidad de Madrid").setAdminArea2("Madrid"))
			.setName(new I18nText().setEs("Parque andalusí"))			// required
			.setLocation(AbstractFactory.GEO_TEIDE)								// required
			.setDescription(new I18nText().setEs("Descripción del parque"))			
			.setFlags(
					Flag.QUALITY_PATRIMONIO_HUMANIDAD,
					Flag.PARKING_ACCESSIBLE,
					Flag.CULTURE_PERIOD_ANDALUSI)
			.validate();
	}
		
	// Natural Park
	public static BasicPoi naturalPark(){
		return ((BasicPoi) PoiFactory.newInstance(PoiTypeID.NATURAL_SPACE))
			.setName(new I18nText().setEs("Parque nacional del Timanfaya")) 	// required
			.setLocation(AbstractFactory.GEO_TEIDE)								// required
			.setDescription(new I18nText().setEs("Descripción del parque"))			
			.setAddress(new Address()
				.setRoute("Timanfaya")
				.setAdminArea1("Canarias")
				.setAdminArea2("Lanzarote"))
			.setFlags(
				Flag.PARKING_ACCESSIBLE,
			    Flag.QUALITY_PATRIMONIO_HUMANIDAD,
			    Flag.QUALITY_ECOTURISMO,
			    Flag.QUALITY_RESERVA_BIOSFERA,
			    Flag.NATIONAL_PARK)
			.setPrices( 
				new Price()
					.setPrice(25d)
					.setPriceType(PriceType.ADULT)
					.setTimetable( 
						new TimeTableEntry("Mon,Tue,Wed,Thu,Fri,Sat,Sun=09:00-20:00")
					)
			).validate();
	}
		
	// Ecoturismo 
	public static BasicPoi ecoTourism(){
		return ((BasicPoi) PoiFactory.newInstance(PoiTypeID.ECO_TOURISM))
			.setName(new I18nText().setEs("Ecoturismo")) 	// required
			.setLocation(randomLocation())					// required
			.setAddress(new Address().setRoute("Casa de Campo").setAdminArea1("Comunidad de Madrid").setAdminArea2("Madrid"))
			.setFlags(
				Flag.GUIDED_TOUR,
				Flag.EDUCATIONAL_ACTIVITIES)
			.setLanguages( "es", "en")
			.validate();
	}
			
		
	// Golf
	public static BasicPoi golf(){
		return((BasicPoi) PoiFactory.newInstance(PoiTypeID.GOLF))
			.setName(new I18nText().setEs("Campo de golf")) 	// required
			.setLocation(randomLocation())					// required
			.setAddress(new Address().setRoute("Casa de Campo").setAdminArea1("Comunidad de Madrid").setAdminArea2("Madrid"))
			.setFlags(
				Flag.GOLF_MIXED,
				Flag.LESSONS,
				Flag.GOLF_CADDY,
				Flag.GOLF_CLUB,
				Flag.GOLF_BUNKER,
				Flag.GOLF_COVERED_DRIVING_RANGE
				)
			.setLanguages( "es" )
			.validate();
	}
		
	// Estación náutica
	public static BasicPoi nauticalStation(){
		return ((BasicPoi) PoiFactory.newInstance(PoiTypeID.NAUTICAL_STATION))
			.setName(new I18nText().setEs("Estación náutica")) 	// required
			.setLocation(randomLocation())					// required
			.setAddress(new Address().setRoute("Casa de Campo").setAdminArea1("Comunidad de Madrid").setAdminArea2("Madrid"))
			.setFlags(
					Flag.NAUTICAL_CATAMARAN,
					Flag.NAUTICAL_DIVING,
					Flag.NAUTICAL_WHALE_WATCHING)
			.setLanguages("es")
			.validate();
	}
	
	// Estación de esqui
	public static BasicPoi skiStation(){
		return ((BasicPoi) PoiFactory.newInstance(PoiTypeID.SKI_STATION))
			.setTimetable(
					new TimeTableEntry("[0112-3103]=10:00-17:00")
			 )
			.setName(new I18nText().setEs("Estación de esquí")) 	// required
			.setLocation(randomLocation())							// required
			.setAddress(new Address().setRoute("Casa de Campo").setAdminArea1("Comunidad de Madrid").setAdminArea2("Madrid"))
			.setFlags(
					Flag.SKI_RENTALS,
					Flag.SOS_SERVICE)
			.setData("total-km-esquiables", "124.2")
			.setData("cota-maxima", "800")
			.setData("cota-minima", "600")
			.setData("pistas:alpino:numero-pistas-verdes", "20")
			.setData("pistas:alpino:numero-pistas-azules", "10")
			.setData("pistas:alpino:numero-pistas-rojas", "8")
			.setData("pistas:alpino:numero-pistas-negras", "4")
			.setData("pistas:otros:numero-halfpipe", "1")
			// Not supported in version 1.0
    		//.setData("pistas:otros:otros", "Foo=1,Bar=2")
			.setData("nieve:numero-caniones", "42")
			// Not supported in version 1.0
    		//.setData("nieve:otros", "ABC=1,DEF=2")
			.setData("servicios:numero-escuelas", "4")
			.setData("servicios:numero-profesores", "14")
			.setData("servicios:estacion", "Podrá además consultar el parte de nieve y las ultimas noticias en ... ")
			.validate();
	}
	
}