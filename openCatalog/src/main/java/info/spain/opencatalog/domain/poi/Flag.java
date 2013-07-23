package info.spain.opencatalog.domain.poi;

import static info.spain.opencatalog.domain.poi.FlagGroup.ACCESSIBILITY;
import static info.spain.opencatalog.domain.poi.FlagGroup.BEACH;
import static info.spain.opencatalog.domain.poi.FlagGroup.BUSINESS_ACTIVITY;
import static info.spain.opencatalog.domain.poi.FlagGroup.BUSINESS_GOLF;
import static info.spain.opencatalog.domain.poi.FlagGroup.BUSINESS_NAUTICAL;
import static info.spain.opencatalog.domain.poi.FlagGroup.BUSINESS_SKI;
import static info.spain.opencatalog.domain.poi.FlagGroup.BUSINESS_SPORT;
import static info.spain.opencatalog.domain.poi.FlagGroup.COMMON;
import static info.spain.opencatalog.domain.poi.FlagGroup.CULTURE_ARTISTIC;
import static info.spain.opencatalog.domain.poi.FlagGroup.CULTURE_CONSTRUCTION;
import static info.spain.opencatalog.domain.poi.FlagGroup.CULTURE_DESIGNATION;
import static info.spain.opencatalog.domain.poi.FlagGroup.CULTURE_HISTORICAL;
import static info.spain.opencatalog.domain.poi.FlagGroup.FAMILY;
import static info.spain.opencatalog.domain.poi.FlagGroup.GOLF_TYPE;
import static info.spain.opencatalog.domain.poi.FlagGroup.LODGING;
import static info.spain.opencatalog.domain.poi.FlagGroup.LODGING_SERVICES;
import static info.spain.opencatalog.domain.poi.FlagGroup.NATURE;
import static info.spain.opencatalog.domain.poi.FlagGroup.QUALITY;
import static info.spain.opencatalog.domain.poi.FlagGroup.ROOM;

import java.util.Set;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.TreeMultimap;

/**
 * Características que pueden tener o no CUALQUIER  POI
 */
public enum Flag {
	
	// Common
	AIR_CONDITIONING(COMMON),	// Aire acondicionado en zonas comunes,
	BAR(COMMON),				// Bar/Cafetería
	BBQ(COMMON),				// Barbacoa
	BIKE_RENT(COMMON),			// Alquiler de bicicletas
	CAR_RENT(COMMON),			// Alquiler de coches
	CREDIT_CARD(COMMON),		// Admite tarjetas de crédito
	EDUCATIONAL_ACTIVITIES(COMMON),	// Actividades didácticas 
	EXCHANGE(COMMON),			// Cambio de moneda
	GUIDED_TOUR(COMMON),		// visitas guiadas
	HANDICAPPED(COMMON),		// Acceso a minusválidos 
	LEISURE(COMMON),			// Actividades de ocio / animación
	LOCKER(COMMON),				// Consigna
	MEDICAL_SERVICE(COMMON),
	PARKING(COMMON),			// Aparcamientos
	REPAIR(COMMON),				// Taller de reparación
	RESTAURANT(COMMON),			// Restaurante
	NO_CHILDS(COMMON),			// Adultos sin niños
	PETS_ALLOWED(COMMON),		// Admite animales de compañia
	PETS_ALLOWED_SIZE(COMMON),	// Admite perros según tamaño
	SHOP(COMMON),				// Tienda
	SOS_SERVICE(COMMON),
	WC(COMMON),
	
	
	// Accesibility
	// 
	HANDICAPPED_ACCESS(ACCESSIBILITY),
	GUIDE_DOG_ALLOWED(ACCESSIBILITY),
	ADAPTED_VEHICLE_RENT(ACCESSIBILITY),
	PARKING_ACCESSIBLE(ACCESSIBILITY),
	LIFT_ACCESSIBLE(ACCESSIBILITY),
	ASSISTANCE_TO_HANDICAPPED(ACCESSIBILITY),
	GUIDE(ACCESSIBILITY),

	
	// Quality
	QUALITY_ACCESIBILIDAD(QUALITY),
	QUALITY_BALNEARIOS(QUALITY),
	QUALITY_BANDERA_AZUL(QUALITY),
	QUALITY_CAMPSA(QUALITY),
	QUALITY_ECOTURISMO(QUALITY),
	QUALITY_EDEN(QUALITY),
	QUALITY_MICHELIN(QUALITY),
	QUALITY_NATURISTA(QUALITY),
	QUALITY_PATRIMONIO_HUMANIDAD(QUALITY),
	QUALITY_Q_CALIDAD(QUALITY),
	QUALITY_RESERVA_BIOSFERA(QUALITY),
	
	// Family
	FAMILY_KIDS_ENTERTAINMENT(FAMILY),		// Animación infantil
	FAMILY_PLAYGROUND(FAMILY),				// Área de juegos para niños
	FAMILY_KINDER(FAMILY),					// Guardería 
	FAMILY_KID_POOL(FAMILY),				// Piscina infantil
	FAMILY_BABYSITTING(FAMILY),				// Servicio de canguro
	
    //  Específicas de Lodging
	BUSSINESS_CENTER(LODGING),	// Bussiness center
	CASINO(LODGING),				// Casino
	FREE_FACILITIES(LODGING),	// Amenities gratuitas
	FREE_FACILITIES_REQ(LODGING),// Amenities gratuitas previa petición
	LIBRARY(LODGING),			// Biblioteca
	LIFT(LODGING),				// Ascensor
	ROOF_TERRACE(LODGING),		// Azotea
	SATELLITE(LODGING),			// Antena parabólica

	ACCESSIBILITY_ADAPTED_ROOMS(ROOM),
	AIR_CONDITIONED(ROOM),    // Aire acondiciondo en habitación
    BALCONY(ROOM),			// Balcón		
    FAMILY_ROOMS(ROOM),			// Habitaciones familiares
    JACUZZI(ROOM),			// Jacuzzi en habitación 
    SAFE_BOX(ROOM),			// Caja fuerte en habitación
    WIFI(ROOM),				// Wifi en habitación
    
    
    // Específicas de parque natural
	NATURAL_MONUMENT(NATURE),	// Monumento Natural
	NATURAL_RESERVE(NATURE),		// Reserva Natural
	NATIONAL_PARK(NATURE),		// Parque Nacional
	NATURAL_PARK(NATURE),		// Parque Natural
	REGIONAL_PARK(NATURE),		// Parque Regional
	
	// Golf : Tipo de campo
	GOLF_COMERCIAL(GOLF_TYPE),		// Campo comercial 
	GOLF_PARTNERS(GOLF_TYPE),		// Campo de socios|
	GOLF_MIXED(GOLF_TYPE),			// Campo mixto
	
	// Business Activity
	LESSONS(BUSINESS_ACTIVITY),					// Se imparten clases
	SCHOOL(BUSINESS_ACTIVITY),					// Escuela  ( de Golf, Ski, ... )
	LOCAL_PRODUCT_SALES(BUSINESS_ACTIVITY),		// Venta de productos locales
	ACTIVE_TOURISM(BUSINESS_ACTIVITY),			//Actividades de turismo activo
	
	
	// Lodging
	AUDIOVISUAL_RENT(LODGING_SERVICES),			// Alquiler equipos audiovisuales
	FAX(LODGING_SERVICES),						// Fax 
	CONVENTION_ROOM(LODGING_SERVICES),			// Sala de convenciones
	MEETING_ROOM(LODGING_SERVICES),				// Sala(s) de reuniones
	SECRETARIAL_SERVICE(LODGING_SERVICES),		// Servicio de secretaría
	DOCUMENT_TRANSLATION(LODGING_SERVICES),		// Traducción de documentos 
	SIMULTANIOUS_TRANSLATION(LODGING_SERVICES),	//Traducción simultánea

	// Campo de Golf
	GOLF_BUNKER(BUSINESS_GOLF),						// Bunker
	GOLF_CADDY(BUSINESS_GOLF),						// Caddy
	GOLF_CALL_PLAY_CARD(BUSINESS_GOLF),				// Admite tarjeta call & play
	GOLF_CLUB(BUSINESS_GOLF),						// Palos de golf
	GOLF_COVERED_DRIVING_RANGE(BUSINESS_GOLF),		// Covered driving range|
	GOLF_DRIVING_RANGE(BUSINESS_GOLF),				// Driving range
	GOLF_ELECTRIC_TROLLEY(BUSINESS_GOLF),			// Electric Trolley
	GOLF_HAND_TROLLEY(BUSINESS_GOLF),				//
	GOLF_PITCHING(BUSINESS_GOLF),
	GOLF_PITCHING_GREEN(BUSINESS_GOLF),
	GOLF_PUTTING_GREEN(BUSINESS_GOLF),
	GOLF_WATER_ROUTE(BUSINESS_GOLF),					// Agua en el recorrido
	GOLF_WC_ROUTE(BUSINESS_GOLF),					// WC en el recorrido
	
	
	// Estaciones náuticas
	NAUTICAL_MOORING_RENTALS(BUSINESS_NAUTICAL) ,		// Alquiler de amarres
	NAUTICAL_WHALE_WATCHING(BUSINESS_NAUTICAL),			// Avistamiento de cetáceos
	NAUTICAL_CATAMARAN(BUSINESS_NAUTICAL),				// Catamarán
	NAUTICAL_CHARTERS(BUSINESS_NAUTICAL),				// Chárters
	NAUTICAL_CRUISES(BUSINESS_NAUTICAL),				// Cruceros
	NAUTICAL_SKI_BUS(BUSINESS_NAUTICAL),				// Esquí bus
	NAUTICAL_SKIING(BUSINESS_NAUTICAL),					// Esquí náutico
	NAUTICAL_BOAT_TRIPS(BUSINESS_NAUTICAL),				// Excursiones marítimas
	NAUTICAL_SUBMARINE(BUSINESS_NAUTICAL),				// Inmersión en submarino
	NAUTICAL_KAYAKING(BUSINESS_NAUTICAL),				// Kayak
	NAUTICAL_KITESURF(BUSINESS_NAUTICAL),				// KiteSurf 
	NAUTICAL_POWERBOATING(BUSINESS_NAUTICAL),			// Motonáutica
	NAUTICAL_PARASAILING(BUSINESS_NAUTICAL),			// Parasailing
	NAUTICAL_FISHING(BUSINESS_NAUTICAL) ,				// Pesca
	NAUTICAL_SNORKELLING(BUSINESS_NAUTICAL),			// Snorkel
	NAUTICAL_DIVING(BUSINESS_NAUTICAL),					// Submarinismo
	NAUTICAL_BUSINESS_SERVICES_SAILING(BUSINESS_NAUTICAL),				// Vela
	NAUTICAL_WAKEBOARD(BUSINESS_NAUTICAL),				// Wakeboard
	NAUTICAL_WINDSURF_SURF(BUSINESS_NAUTICAL),			// Windsurf/Surf
	
	//Estación de esquí
	SKI_RENTALS(BUSINESS_SKI),			// Alquileres de material de esquí

	// Instalaciones para deportes varios
	SPORTS_FOOTBALL(BUSINESS_SPORT),
	SPORTS_GOLF(BUSINESS_SPORT),
	SPORTS_GYM(BUSINESS_SPORT),
	SPORTS(BUSINESS_SPORT),
	SPORTS_JACUZZI(BUSINESS_SPORT),
	SPORTS_LOCKER_ROOM(BUSINESS_SPORT),	
	SPORTS_PADDLE(BUSINESS_SPORT),
	SPORTS_POOL(BUSINESS_SPORT),
	SPORTS_SAUNA(BUSINESS_SPORT),
	SPORTS_SHOWERS(BUSINESS_SPORT),				
	SPORTS_TENIS(BUSINESS_SPORT),
	
	CULTURE_PERIOD_ANDALUSI(CULTURE_ARTISTIC),       // Andalusí
	CULTURE_PERIOD_ARABIC(CULTURE_ARTISTIC),         // Árabe
	CULTURE_PERIOD_ART_DECO(CULTURE_ARTISTIC),       // Art-Decó
	CULTURE_PERIOD_INDIAN_ART(CULTURE_ARTISTIC),     // Arte Indiano
	CULTURE_PERIOD_JEWISH_ART(CULTURE_ARTISTIC),     // Arte Judío
	CULTURE_PERIOD_ORIENTAL_ART(CULTURE_ARTISTIC),   // Arte Oriental
	CULTURE_PERIOD_BAROQUE(CULTURE_ARTISTIC),        // Barroco
	CULTURE_PERIOD_CELTIC(CULTURE_ARTISTIC),         // Celta
	CULTURE_PERIOD_CHURRIGUERESCO(CULTURE_ARTISTIC), // Churrigueresco
	CULTURE_PERIOD_CISTERCIAN(CULTURE_ARTISTIC),     // Cisterciense
	CULTURE_PERIOD_CONTEMPORANY(CULTURE_ARTISTIC),   // Contemporáneo
	CULTURE_PERIOD_CUSTOMS(CULTURE_ARTISTIC),        // Costumbres-Costumbrista
	CULTURE_PERIOD_VARIOUS_TIMES(CULTURE_ARTISTIC),  // Diversas épocas
	CULTURE_PERIOD_VARIOUS_STYLES(CULTURE_ARTISTIC), // Diversos estilos
	CULTURE_PERIOD_ECLECTIC(CULTURE_ARTISTIC),       // Eclecticismo
	CULTURE_PERIOD_EGYPTIAN(CULTURE_ARTISTIC),       // Egipcio
	CULTURE_PERIOD_PHOENICIAN(CULTURE_ARTISTIC),     // Fenicio
	CULTURE_PERIOD_GOTHIC(CULTURE_ARTISTIC),         // Gótico
	CULTURE_PERIOD_GRECO_ROMAN(CULTURE_ARTISTIC),    // Grecorromano
	CULTURE_PERIOD_GREEK(CULTURE_ARTISTIC),          // |Griego
	CULTURE_PERIOD_HERRERIANO(CULTURE_ARTISTIC),     // Herreriano
	CULTURE_PERIOD_HISTORICIST(CULTURE_ARTISTIC),    // Historicista
	CULTURE_PERIOD_IBERO(CULTURE_ARTISTIC),          //|Ibéro
	CULTURE_PERIOD_ELIZABETHAN(CULTURE_ARTISTIC),    // Isabelino
	CULTURE_PERIOD_MANIERIST(CULTURE_ARTISTIC),      // Manierista
	CULTURE_PERIOD_MEDIEVAL(CULTURE_ARTISTIC),       // Medieval
	CULTURE_PERIOD_MODERNISM(CULTURE_ARTISTIC),      // Modernismo
	CULTURE_PERIOD_MODERN(CULTURE_ARTISTIC),         // Moderno
	CULTURE_PERIOD_MOZARABE(CULTURE_ARTISTIC),       // Mozárabe
	CULTURE_PERIOD_MUDEJAR(CULTURE_ARTISTIC),        // Mudéjar
	CULTURE_PERIOD_MULTIDISCIPLINARY(CULTURE_ARTISTIC),      // Multidisciplinar
	CULTURE_PERIOD_NEOCLASSICAL(CULTURE_ARTISTIC),    // Neoclásico
	CULTURE_PERIOD_GOTHIC_REVIVAL(CULTURE_ARTISTIC),  // Neogótico
	CULTURE_PERIOD_NEOROMANESQUE(CULTURE_ARTISTIC),   // Neorrománico
	CULTURE_PERIOD_NEW_VANGUARD(CULTURE_ARTISTIC),    // Nuevas vanguardias
	CULTURE_PERIOD_PLATERESCO(CULTURE_ARTISTIC),      // Plateresco
	CULTURE_PERIOD_PRE_ROMANESQUE(CULTURE_ARTISTIC),  // Pre-románico
	CULTURE_PERIOD_PRE_ROMAN(CULTURE_ARTISTIC),       // Pre-romano
	CULTURE_PERIOD_PREHISTORY(CULTURE_ARTISTIC),      // Prehistoria
	CULTURE_PERIOD_RENAISSANCE(CULTURE_ARTISTIC),     // renacentista
	CULTURE_PERIOD_ROCOCO(CULTURE_ARTISTIC),          // Rococó
	CULTURE_PERIOD_ROMANESQUE(CULTURE_ARTISTIC),      // Románico
	CULTURE_PERIOD_ROMAN(CULTURE_ARTISTIC),           //Romano-clásico
	CULTURE_PERIOD_ROMANTICISM(CULTURE_ARTISTIC),     // |Romanticismo
	CULTURE_PERIOD_VANGUARD(CULTURE_ARTISTIC),        // Vanguardias
	CULTURE_PERIOD_VISIGOTH(CULTURE_ARTISTIC),        //Visigodo

	CULTURE_TYPE_OTHER(CULTURE_CONSTRUCTION),                        // Otros
	CULTURE_TYPE_PREHISTORY(CULTURE_CONSTRUCTION),           // Abrigo Prehistórico
	CULTURE_TYPE_ACROPOLYS(CULTURE_CONSTRUCTION),            // Acrópolis
	CULTURE_TYPE_AQUEDUCT(CULTURE_CONSTRUCTION),             // Acueducto
	CULTURE_TYPE_ALCAZABA(CULTURE_CONSTRUCTION),             // Alcazaba
	CULTURE_TYPE_ALCAZAR(CULTURE_CONSTRUCTION),              // Alcázar
	CULTURE_TYPE_MINARET(CULTURE_CONSTRUCTION),              // Alminar
	CULTURE_TYPE_AMPHITEATHRE(CULTURE_CONSTRUCTION),         // Anfiteatro
	CULTURE_TYPE_ARC(CULTURE_CONSTRUCTION),                  // Arco
	CULTURE_TYPE_POPULAR(CULTURE_CONSTRUCTION),              // Arquitectura popular
	CULTURE_TYPE_BASILICA(CULTURE_CONSTRUCTION),             // Basílica
	CULTURE_TYPE_ROADWAY(CULTURE_CONSTRUCTION),              // Calzada
	CULTURE_TYPE_CHAPEL(CULTURE_CONSTRUCTION),                       // Capilla
	CULTURE_TYPE_CHARTERHOUSE(CULTURE_CONSTRUCTION), // Cartuja
	CULTURE_TYPE_MANOR_HOUSE(CULTURE_CONSTRUCTION),  // Casa señorial o noble
	CULTURE_TYPE_CASTLE(CULTURE_CONSTRUCTION),                       // Castillo
	CULTURE_TYPE_CASTRO(CULTURE_CONSTRUCTION),                       // Castro
	CULTURE_TYPE_CATACOMBS(CULTURE_CONSTRUCTION),            // Catacumbas
	CULTURE_TYPE_CATHEDRAL(CULTURE_CONSTRUCTION),            // Catedral
	CULTURE_TYPE_CEMENTERY(CULTURE_CONSTRUCTION),            // Cementerio
	CULTURE_TYPE_CIRCUS(CULTURE_CONSTRUCTION),                       // Circo
	CULTURE_TYPE_EXCAVATIONS(CULTURE_CONSTRUCTION),  // Ciudad / poblado excavaciones
	CULTURE_TYPE_CLOISTER(CULTURE_CONSTRUCTION),             // Claustro
	CULTURE_TYPE_COLLEGIATE(CULTURE_CONSTRUCTION),           // Colegiata
	CULTURE_TYPE_CONCATHEDRAL(CULTURE_CONSTRUCTION), // Concatedral
	CULTURE_TYPE_CONVENT(CULTURE_CONSTRUCTION),              // Convento
	CULTURE_TYPE_PREHISTORIC_CAVE(CULTURE_CONSTRUCTION),             // Cueva prehistórica
	CULTURE_TYPE_CAVE(CULTURE_CONSTRUCTION),                 // Cuevas y minas turísticas
	CULTURE_TYPE_DOLMEN(CULTURE_CONSTRUCTION),                       // Dolmen
	CULTURE_TYPE_OFFICE_BUILDING(CULTURE_CONSTRUCTION),              // Edificio de oficinas
	CULTURE_TYPE_PUBLIC_BUILDING(CULTURE_CONSTRUCTION),              // Edificio de uso público
	CULTURE_TYPE_BUILDING(CULTURE_CONSTRUCTION),                             // Edificio de viviendas
	CULTURE_TYPE_INDUSTRIAL_BUILDING(CULTURE_CONSTRUCTION),  // Edificio industrial
	CULTURE_TYPE_MILITAR_BUILDING(CULTURE_CONSTRUCTION),             // Edificio militar
	CULTURE_TYPE_ERMITA(CULTURE_CONSTRUCTION),                       // Ermita
	CULTURE_TYPE_FORTRESS(CULTURE_CONSTRUCTION),             // Fortaleza
	CULTURE_TYPE_FORT(CULTURE_CONSTRUCTION),                 // Fuerte
	CULTURE_TYPE_SCULPTURAL_GROUP(CULTURE_CONSTRUCTION),             //Grupos escultóricos
	CULTURE_TYPE_CHURCH(CULTURE_CONSTRUCTION),                       // Iglesia
	CULTURE_TYPE_CIVIL(CULTURE_CONSTRUCTION),                        // Ingeniería civil
	CULTURE_TYPE_GARDEN(CULTURE_CONSTRUCTION),                       // Jardín
	CULTURE_TYPE_LONJA(CULTURE_CONSTRUCTION),                        // Lonja
	CULTURE_TYPE_FARMHOUSE(CULTURE_CONSTRUCTION),            // Masía
	CULTURE_TYPE_FORTIFIED_FARMHOUSE(CULTURE_CONSTRUCTION),  // Masía fortificada
	CULTURE_TYPE_MENHIR(CULTURE_CONSTRUCTION),                       // Menhir
	CULTURE_TYPE_MOSQUE(CULTURE_CONSTRUCTION),                       // Mezquita
	CULTURE_TYPE_MONASTERY(CULTURE_CONSTRUCTION),            // Monasterio
	CULTURE_TYPE_MONOLITH(CULTURE_CONSTRUCTION),             // Monolito
	CULTURE_TYPE_WALLS(CULTURE_CONSTRUCTION),                        //      Murallas
	CULTURE_TYPE_NECROPOLIS(CULTURE_CONSTRUCTION),           // Necrópolis
	CULTURE_TYPE_MANSION(CULTURE_CONSTRUCTION),              // Palacete
	CULTURE_TYPE_PALACE(CULTURE_CONSTRUCTION),                       // Palacio
	CULTURE_TYPE_PANTHEON(CULTURE_CONSTRUCTION),             // Panteón
	CULTURE_TYPE_PAZO(CULTURE_CONSTRUCTION),                 // Pazo
	CULTURE_TYPE_PYRAMID(CULTURE_CONSTRUCTION),              // Pirámide
	CULTURE_TYPE_SQUAERE(CULTURE_CONSTRUCTION),              // Plaza
	CULTURE_TYPE_BULLRING(CULTURE_CONSTRUCTION),             // Plaza de toros
	CULTURE_TYPE_PORTICO(CULTURE_CONSTRUCTION),              // Pórtico
	CULTURE_TYPE_BRIDGE(CULTURE_CONSTRUCTION),                       // Puente
	CULTURE_TYPE_DOOR(CULTURE_CONSTRUCTION),                 // Puerta
	CULTURE_TYPE_WALLED(CULTURE_CONSTRUCTION),                       // Recinto amurallado
	CULTURE_TYPE_RESIDENCE(CULTURE_CONSTRUCTION),            // Residencia
	CULTURE_TYPE_RUINS(CULTURE_CONSTRUCTION),                        // Ruinas
	CULTURE_TYPE_SANCTUARY(CULTURE_CONSTRUCTION),            // Santuario
	CULTURE_TYPE_SEMINAR(CULTURE_CONSTRUCTION),              // Seminario
	CULTURE_TYPE_GRAVE(CULTURE_CONSTRUCTION),                        // Sepulcro
	CULTURE_TYPE_SYNAGOGUE(CULTURE_CONSTRUCTION),            // Sinagoga
	CULTURE_TYPE_TAULAS(CULTURE_CONSTRUCTION),                       // Taulas(CULTURE_CONSTRUCTION), talayots y navetas
	CULTURE_TYPE_THEATRE(CULTURE_CONSTRUCTION),              // Teatro
	CULTURE_TYPE_TEMPLE(CULTURE_CONSTRUCTION),                       // Templo
	CULTURE_TYPE_TEMPLE_FORT(CULTURE_CONSTRUCTION),  // Templo-fortaleza
	CULTURE_TYPE_THERMAL_BATHS(CULTURE_CONSTRUCTION),        // Termas
	CULTURE_TYPE_TOWER(CULTURE_CONSTRUCTION),                        // Torre
	CULTURE_TYPE_TOMB(CULTURE_CONSTRUCTION),                 // Túmulo funerario
	CULTURE_TYPE_UNIVERSITY(CULTURE_CONSTRUCTION),           // Universidad
	CULTURE_TYPE_VILLA(CULTURE_CONSTRUCTION),                        // Villa
	CULTURE_TYPE_ARCHAEOLOGICAL(CULTURE_CONSTRUCTION),        // Yacimiento arqueológic

	CULTURE_DESIGNATION_COLLECTION_MUSEUM(CULTURE_DESIGNATION),	// Colección museográfica
	CULTURE_DESIGNATION_REGIONAL_MUSEUM(CULTURE_DESIGNATION),	// Museo Autonómico
	CULTURE_DESIGNATION_CATHEDRAL_MUSEUM(CULTURE_DESIGNATION),	// Museo Catedralicio
	CULTURE_DESIGNATION_DIOCESAN_MUSEUM(CULTURE_DESIGNATION),	// Museo Diocesano
	CULTURE_DESIGNATION_ISLAND_MUSEUM(CULTURE_DESIGNATION),		// Museo Insular
	CULTURE_DESIGNATION_LOCAL_MUSEUM(CULTURE_DESIGNATION),		// Museo local
	CULTURE_DESIGNATION_MUNICIPAL_MUSEUM(CULTURE_DESIGNATION),	// Museo Municipal
	CULTURE_DESIGNATION_NATIONAL_MUSEUM(CULTURE_DESIGNATION),	// Museo Nacional
	CULTURE_DESIGNATION_PRIVATE_MUSEUM(CULTURE_DESIGNATION),		// Museo particular
	CULTURE_DESIGNATION_PROVINCIAL_MUSEUM(CULTURE_DESIGNATION),	// Museo Provincial
	CULTURE_DESIGNATION_NATIONAL_HERITAGE(CULTURE_DESIGNATION),	// Patrimonio Nacional
	
	CULTURE_HISTORICAL_VARIOUS_TIMES(CULTURE_HISTORICAL),         // Diversas épocas
	CULTURE_HISTORICAL_VARIOUS_STYLES(CULTURE_HISTORICAL),                // Diversos estilos
	CULTURE_HISTORICAL_BRONZE_AGE(CULTURE_HISTORICAL),                    // Edad del Bronce
	CULTURE_HISTORICAL_IRON_COPPER_AGE(CULTURE_HISTORICAL),       // Edad del Hierro y Cobre
	CULTURE_HISTORICAL_IBERIAN(CULTURE_HISTORICAL),                       // Ibérico
	CULTURE_HISTORICAL_ROMANIZATION_BC(CULTURE_HISTORICAL),       // Romanización (A.C.)
	CULTURE_HISTORICAL_ROMANIZATION_AC(CULTURE_HISTORICAL),       // Romanización (d.C.)
	CULTURE_HISTORICAL_CENTURY_1(CULTURE_HISTORICAL),                     // Siglo 1
	CULTURE_HISTORICAL_CENTURY_1_BC(CULTURE_HISTORICAL),          // Siglo 1 (a.C.)
	CULTURE_HISTORICAL_CENTURY_2(CULTURE_HISTORICAL),                     // Siglo 2
	CULTURE_HISTORICAL_CENTURY_3(CULTURE_HISTORICAL),                     // Siglo 3
	CULTURE_HISTORICAL_CENTURY_4(CULTURE_HISTORICAL),                     // Siglo 4
	CULTURE_HISTORICAL_CENTURY_5(CULTURE_HISTORICAL),                     // Siglo 5
	CULTURE_HISTORICAL_CENTURY_6(CULTURE_HISTORICAL),                     // Siglo 6
	CULTURE_HISTORICAL_CENTURY_7(CULTURE_HISTORICAL),                     // Siglo 7
	CULTURE_HISTORICAL_CENTURY_8(CULTURE_HISTORICAL),                     // Siglo 8
	CULTURE_HISTORICAL_CENTURY_9(CULTURE_HISTORICAL),                     // Siglo 9
	CULTURE_HISTORICAL_CENTURY_10(CULTURE_HISTORICAL),                    // Siglo 10
	CULTURE_HISTORICAL_CENTURY_11(CULTURE_HISTORICAL),                    // Siglo 11
	CULTURE_HISTORICAL_CENTURY_12(CULTURE_HISTORICAL),                    // Siglo 12
	CULTURE_HISTORICAL_CENTURY_13(CULTURE_HISTORICAL),                    // Siglo 13
	CULTURE_HISTORICAL_CENTURY_14(CULTURE_HISTORICAL),                    // Siglo 14
	CULTURE_HISTORICAL_CENTURY_15(CULTURE_HISTORICAL),                    // Siglo 15
	CULTURE_HISTORICAL_CENTURY_16(CULTURE_HISTORICAL),                    // Siglo 16
	CULTURE_HISTORICAL_CENTURY_17(CULTURE_HISTORICAL),                    // Siglo 17
	CULTURE_HISTORICAL_CENTURY_18(CULTURE_HISTORICAL),                    // Siglo 18
	CULTURE_HISTORICAL_CENTURY_19(CULTURE_HISTORICAL),                    // Siglo 19
	CULTURE_HISTORICAL_CENTURY_20(CULTURE_HISTORICAL),                    // Siglo 20
	CULTURE_HISTORICAL_CENTURY_21(CULTURE_HISTORICAL),                     // Siglo 21
	
	// Beach
	BEACH_BATH_CONDITION_CALM(BEACH),
	BEACH_BATH_CONDITION_STRONG_WAVES(BEACH),
	BEACH_BATH_CONDITION_MODERATE_WAVES(BEACH),
	BEACH_BATH_CONDITION_SOFT_WAVES(BEACH),
	
	BEACH_COMPOSITION_SAND(BEACH),
	BEACH_COMPOSITION_VOLCANIC_BLACK_SAND(BEACH),
	BEACH_COMPOSITION_GRAVEL(BEACH),
	BEACH_COMPOSITION_STONE(BEACH),
	BEACH_COMPOSITION_ROCK(BEACH),	
	
	BEACH_SAND_TYPE_WHITE(BEACH),
	BEACH_SAND_TYPE_GOLDEN(BEACH),
	BEACH_SAND_TYPE_DARK(BEACH),

	;
	
	private FlagGroup group;
	
	private Flag(FlagGroup group) {
		this.group = group;
	}

	private static final SetMultimap<FlagGroup, Flag> flagsByGroup;
	
	public FlagGroup getGroup() {
		return group;
	}

	static Set<Flag> getFlags(FlagGroup group) {
		return flagsByGroup.get(group);
	}

	static {
		SetMultimap<FlagGroup, Flag> flags = TreeMultimap.create();
		for (Flag flag : Flag.values()) {
			flags.put(flag.getGroup(), flag);
		}
		flagsByGroup = ImmutableSetMultimap.copyOf(flags);
	}
	
	
			
}