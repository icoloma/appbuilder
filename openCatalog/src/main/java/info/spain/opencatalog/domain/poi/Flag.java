package info.spain.opencatalog.domain.poi;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;



/**
 * Características que pueden tener o no CUALQUIER  POI
 */
public enum Flag {
	
	// Common
	COMMON_AIR_CONDITIONING,	// Aire acondicionado en zonas comunes,
	COMMON_BAR,				// Bar/Cafetería
	COMMON_BBQ,				// Barbacoa
	COMMON_BIKE_RENT,			// Alquiler de bicicletas
	COMMON_CAR_RENT,			// Alquiler de coches
	COMMON_CREDIT_CARD,		// Admite tarjetas de crédito
	COMMON_EDUCATIONAL_ACTIVITIES,	// Actividades didácticas 
	COMMON_EXCHANGE,			// Cambio de moneda
	COMMON_GUIDED_TOUR,		// visitas guiadas
	COMMON_HANDICAPPED,		// Acceso a minusválidos 
	COMMON_LEISURE,			// Actividades de ocio / animación
	COMMON_LOCAL_PRODUCT_SALES,	// Venta de productos locales
	COMMON_LOCKER,				// Consigna
	COMMON_MEDICAL_SERVICE,
	COMMON_PARKING,			// Aparcamientos
	COMMON_REPAIR,				// Taller de reparación
	COMMON_RESTAURANT,			// Restaurante
	COMMON_NO_CHILDS,			// Adultos sin niños
	COMMON_PETS_ALLOWED,		// Admite animales de compañia
	COMMON_PETS_ALLOWED_SIZE,	// Admite perros según tamaño
	COMMON_SHOP,				// Tienda
	COMMON_SOS_SERVICE,
	COMMON_WC,
	
	
	// Accesibility
	ACCESSIBILITY_HANDICAPPED_ACCESS,
	ACCESSIBILITY_GUIDE_DOG_ALLOWED,
	ACCESSIBILITY_ADAPTED_VEHICLE_RENT,
	ACCESSIBILITY_PARKING_ACCESSIBLE,
	ACCESSIBILITY_LIFT_ACCESSIBLE,
	ACCESSIBILITY_ASSISTANCE_TO_HANDICAPPED,
	ACCESSIBILITY_GUIDE,
	ACCESSIBILITY_ADAPTED_ROOMS,
	
	// Quality
	QUALITY_ACCESIBILIDAD,
	QUALITY_BALNEARIOS,
	QUALITY_BANDERA_AZUL,
	QUALITY_CAMPSA,
	QUALITY_ECOTURISMO,
	QUALITY_EDEN,
	QUALITY_MICHELIN,
	QUALITY_NATURISTA,
	QUALITY_PATRIMONIO_HUMANIDAD,
	QUALITY_Q_CALIDAD,
	QUALITY_RESERVA_BIOSFERA,
	
	// Family
	FAMILY_KIDS_ENTERTAINMENT,		// Animación infantil
	FAMILY_PLAYGROUND,				// Área de juegos para niños
	FAMILY_KINDER,					// Guardería 
	FAMILY_FAMILY_ROOMS,			// Habitaciones familiares
	FAMILY_KID_POOL,				// Piscina infantil
	FAMILY_BABYSITTING,				// Servicio de canguro
	
    //  Específicas de Lodging
	LODGING_BUSSINESS_CENTER,	// Bussiness center
	LODGING_CASINO,				// Casino
	LODGING_FREE_FACILITIES,	// Amenities gratuitas
	LODGING_FREE_FACILITIES_REQ,// Amenities gratuitas previa petición
	LODGING_LIBRARY,			// Biblioteca
	LODGING_LIFT,				// Ascensor
	LODGING_ROOF_TERRACE,		// Azotea
	LODGING_SATELLITE,			// Antena parabólica

	LODGING_ROOM_AIR_CONDITIONED,    // Aire acondiciondo en habitación
    LODGING_ROOM_BALCONY,			// Balcón		
    LODGING_ROOM_JACUZZI,			// Jacuzzi en habitación 
    LODGING_ROOM_SAFE_BOX,			// Caja fuerte en habitación
    LODGING_ROOM_WIFI,				// Wifi en habitación
    
    
    // Específicas de parque natural
	NATURE_NATURAL_MONUMENT,	// Monumento Natural
	NATURE_NATURAL_RESERVE,		// Reserva Natural
	NATURE_NATIONAL_PARK,		// Parque Nacional
	NATURE_NATURAL_PARK,		// Parque Natural
	NATURE_REGIONAL_PARK,		// Parque Regional
	
	// Golf : Tipo de campo
	BUSINESS_TYPE_GOLF_COMERCIAL,		// Campo comercial 
	BUSINESS_TYPE_GOLF_PARTNERS,		// Campo de socios|
	BUSINESS_TYPE_GOLF_MIXED,			// Campo mixto
	
	// Business Activity
	BUSINESS_ACTIVITY_LESSONS,					// Se imparten clases
	BUSINESS_ACTIVITY_SCHOOL,					// Escuela  ( de Golf, Ski, ... )
	BUSINESS_ACTIVITY_LOCAL_PRODUCT_SALES,		// Venta de productos locales
	BUSINESS_ACTIVITY_ACTIVE_TOURISM,			//Actividades de turismo activo
	
	
	// Lodging
	BUSINESS_SERVICES_LODGING_AUDIOVISUAL_RENT,			// Alquiler equipos audiovisuales
	BUSINESS_SERVICES_LODGING_FAX,						// Fax 
	BUSINESS_SERVICES_LODGING_CONVENTION_ROOM,			// Sala de convenciones
	BUSINESS_SERVICES_LODGING_MEETING_ROOM,				// Sala(s) de reuniones
	BUSINESS_SERVICES_LODGING_SECRETARIAL_SERVICE,		// Servicio de secretaría
	BUSINESS_SERVICES_LODGING_DOCUMENT_TRANSLATION,		// Traducción de documentos 
	BUSINESS_SERVICES_LODGING_SIMULTANIOUS_TRANSLATION,	//Traducción simultánea

	// Campo de Golf
	BUSINESS_SERVICES_GOLF_BUNKER,						// Bunker
	BUSINESS_SERVICES_GOLF_CADDY,						// Caddy
	BUSINESS_SERVICES_GOLF_CALL_PLAY_CARD,				// Admite tarjeta call & play
	BUSINESS_SERVICES_GOLF_CLUB,						// Palos de golf
	BUSINESS_SERVICES_GOLF_COVERED_DRIVING_RANGE,		// Covered driving range|
	BUSINESS_SERVICES_GOLF_DRIVING_RANGE,				// Driving range
	BUSINESS_SERVICES_GOLF_ELECTRIC_TROLLEY,			// Electric Trolley
	BUSINESS_SERVICES_GOLF_HAND_TROLLEY,				//
	BUSINESS_SERVICES_GOLF_PITCHING,
	BUSINESS_SERVICES_GOLF_PITCHING_GREEN,
	BUSINESS_SERVICES_GOLF_PUTTING_GREEN,
	BUSINESS_SERVICES_GOLF_WATER_ROUTE,				// Agua en el recorrido
	BUSINESS_SERVICES_GOLF_WC_ROUTE,					// WC en el recorrido
	
	
	// Estaciones náuticas
	BUSINESS_SERVICES_NAUTICAL_MOORING_RENTALS ,		// Alquiler de amarres
	BUSINESS_SERVICES_NAUTICAL_WHALE_WATCHING,			// Avistamiento de cetáceos
	BUSINESS_SERVICES_NAUTICAL_CATAMARAN,				// Catamarán
	BUSINESS_SERVICES_NAUTICAL_CHARTERS,				// Chárters
	BUSINESS_SERVICES_NAUTICAL_CRUISES,				// Cruceros
	BUSINESS_SERVICES_NAUTICAL_SKI_BUS,				// Esquí bus
	BUSINESS_SERVICES_NAUTICAL_SKIING,					// Esquí náutico
	BUSINESS_SERVICES_NAUTICAL_BOAT_TRIPS,				// Excursiones marítimas
	BUSINESS_SERVICES_NAUTICAL_SUBMARINE,				// Inmersión en submarino
	BUSINESS_SERVICES_NAUTICAL_KAYAKING,				// Kayak
	BUSINESS_SERVICES_NAUTICAL_KITESURF,				// KiteSurf 
	BUSINESS_SERVICES_NAUTICAL_POWERBOATING,			// Motonáutica
	BUSINESS_SERVICES_NAUTICAL_PARASAILING,			// Parasailing
	BUSINESS_SERVICES_NAUTICAL_FISHING ,				// Pesca
	BUSINESS_SERVICES_NAUTICAL_SNORKELLING,			// Snorkel
	BUSINESS_SERVICES_NAUTICAL_DIVING,					// Submarinismo
	BUSINESS_SERVICES_NAUTICAL_BUSINESS_SERVICES_SAILING,				// Vela
	BUSINESS_SERVICES_NAUTICAL_WAKEBOARD,				// Wakeboard
	BUSINESS_SERVICES_NAUTICAL_WINDSURF_SURF,			// Windsurf/Surf
	
	//Estación de esquí
	BUSINESS_SERVICES_SKI_RENTALS,			// Alquileres de material de esquí
	BUSINESS_SERVICES_SKI_SCHOOL,				

	// Instalaciones para deportes varios
	BUSINESS_SERVICES_SPORTS_FOOTBALL,
	BUSINESS_SERVICES_SPORTS_GOLF,
	BUSINESS_SERVICES_SPORTS_GYM,
	BUSINESS_SERVICES_SPORTS,
	BUSINESS_SERVICES_SPORTS_JACUZZI,
	BUSINESS_SERVICES_SPORTS_LOCKER_ROOM,	
	BUSINESS_SERVICES_SPORTS_PADDLE,
	BUSINESS_SERVICES_SPORTS_POOL,
	BUSINESS_SERVICES_SPORTS_SAUNA,
	BUSINESS_SERVICES_SPORTS_SHOWERS,				
	BUSINESS_SERVICES_SPORTS_TENIS,
	
	
	CULTURE_ARTISTIC_PERIOD_ANDALUSI,               // Andalusí
	CULTURE_ARTISTIC_PERIOD_ARABIC,                 // Árabe
	CULTURE_ARTISTIC_PERIOD_ART_DECO,               // Art-Decó
	CULTURE_ARTISTIC_PERIOD_INDIAN_ART,             // Arte Indiano
	CULTURE_ARTISTIC_PERIOD_JEWISH_ART,             // Arte Judío
	CULTURE_ARTISTIC_PERIOD_ORIENTAL_ART,   // Arte Oriental
	CULTURE_ARTISTIC_PERIOD_BAROQUE,                // Barroco
	CULTURE_ARTISTIC_PERIOD_CELTIC,                 // Celta
	CULTURE_ARTISTIC_PERIOD_CHURRIGUERESCO, // Churrigueresco
	CULTURE_ARTISTIC_PERIOD_CISTERCIAN,             // Cisterciense
	CULTURE_ARTISTIC_PERIOD_CONTEMPORANY,   // Contemporáneo
	CULTURE_ARTISTIC_PERIOD_CUSTOMS,                // Costumbres-Costumbrista
	CULTURE_ARTISTIC_PERIOD_VARIOUS_TIMES,  // Diversas épocas
	CULTURE_ARTISTIC_PERIOD_VARIOUS_STYLES, // Diversos estilos
	CULTURE_ARTISTIC_PERIOD_ECLECTIC,               // Eclecticismo
	CULTURE_ARTISTIC_PERIOD_EGYPTIAN,               // Egipcio
	CULTURE_ARTISTIC_PERIOD_PHOENICIAN,             // Fenicio
	CULTURE_ARTISTIC_PERIOD_GOTHIC,                 // Gótico
	CULTURE_ARTISTIC_PERIOD_GRECO_ROMAN,    // Grecorromano
	CULTURE_ARTISTIC_PERIOD_GREEK,                  // |Griego
	CULTURE_ARTISTIC_PERIOD_HERRERIANO,             // Herreriano
	CULTURE_ARTISTIC_PERIOD_HISTORICIST,    // Historicista
	CULTURE_ARTISTIC_PERIOD_IBERO,                  //|Ibéro
	CULTURE_ARTISTIC_PERIOD_ELIZABETHAN,    // Isabelino
	CULTURE_ARTISTIC_PERIOD_MANIERIST,              // Manierista
	CULTURE_ARTISTIC_PERIOD_MEDIEVAL,               // Medieval
	CULTURE_ARTISTIC_PERIOD_MODERNISM,              // Modernismo
	CULTURE_ARTISTIC_PERIOD_MODERN,                 // Moderno
	CULTURE_ARTISTIC_PERIOD_MOZARABE,               // Mozárabe
	CULTURE_ARTISTIC_PERIOD_MUDEJAR,                // Mudéjar
	CULTURE_ARTISTIC_PERIOD_MULTIDISCIPLINARY,      // Multidisciplinar
	CULTURE_ARTISTIC_PERIOD_NEOCLASSICAL,           // Neoclásico
	CULTURE_ARTISTIC_PERIOD_GOTHIC_REVIVAL, // Neogótico
	CULTURE_ARTISTIC_PERIOD_NEOROMANESQUE,          // Neorrománico
	CULTURE_ARTISTIC_PERIOD_NEW_VANGUARD,   // Nuevas vanguardias
	CULTURE_ARTISTIC_PERIOD_PLATERESCO,             // Plateresco
	CULTURE_ARTISTIC_PERIOD_PRE_ROMANESQUE, // Pre-románico
	CULTURE_ARTISTIC_PERIOD_PRE_ROMAN,              // Pre-romano
	CULTURE_ARTISTIC_PERIOD_PREHISTORY,             // Prehistoria
	CULTURE_ARTISTIC_PERIOD_RENAISSANCE,    // renacentista
	CULTURE_ARTISTIC_PERIOD_ROCOCO,                 // Rococó
	CULTURE_ARTISTIC_PERIOD_ROMANESQUE,             // Románico
	CULTURE_ARTISTIC_PERIOD_ROMAN,                  //Romano-clásico
	CULTURE_ARTISTIC_PERIOD_ROMANTICISM,    // |Romanticismo
	CULTURE_ARTISTIC_PERIOD_VANGUARD,               // Vanguardias
	CULTURE_ARTISTIC_PERIOD_VISIGOTH,                //Visigodo

	CULTURE_CONSTRUCTION_TYPE_OTHER,                        // Otros
	CULTURE_CONSTRUCTION_TYPE_PREHISTORY,           // Abrigo Prehistórico
	CULTURE_CONSTRUCTION_TYPE_ACROPOLYS,            // Acrópolis
	CULTURE_CONSTRUCTION_TYPE_AQUEDUCT,             // Acueducto
	CULTURE_CONSTRUCTION_TYPE_ALCAZABA,             // Alcazaba
	CULTURE_CONSTRUCTION_TYPE_ALCAZAR,              // Alcázar
	CULTURE_CONSTRUCTION_TYPE_MINARET,              // Alminar
	CULTURE_CONSTRUCTION_TYPE_Amphitheatre,         // Anfiteatro
	CULTURE_CONSTRUCTION_TYPE_ARC,                  // Arco
	CULTURE_CONSTRUCTION_TYPE_POPULAR,              // Arquitectura popular
	CULTURE_CONSTRUCTION_TYPE_BASILICA,             // Basílica
	CULTURE_CONSTRUCTION_TYPE_ROADWAY,              // Calzada
	CULTURE_CONSTRUCTION_TYPE_CHAPEL,                       // Capilla
	CULTURE_CONSTRUCTION_TYPE_CHARTERHOUSE, // Cartuja
	CULTURE_CONSTRUCTION_TYPE_MANOR_HOUSE,  // Casa señorial o noble
	CULTURE_CONSTRUCTION_TYPE_CASTLE,                       // Castillo
	CULTURE_CONSTRUCTION_TYPE_CASTRO,                       // Castro
	CULTURE_CONSTRUCTION_TYPE_CATACOMBS,            // Catacumbas
	CULTURE_CONSTRUCTION_TYPE_CATHEDRAL,            // Catedral
	CULTURE_CONSTRUCTION_TYPE_CEMENTERY,            // Cementerio
	CULTURE_CONSTRUCTION_TYPE_CIRCUS,                       // Circo
	CULTURE_CONSTRUCTION_TYPE_EXCAVATIONS,  // Ciudad / poblado excavaciones
	CULTURE_CONSTRUCTION_TYPE_CLOISTER,             // Claustro
	CULTURE_CONSTRUCTION_TYPE_COLLEGIATE,           // Colegiata
	CULTURE_CONSTRUCTION_TYPE_CONCATHEDRAL, // Concatedral
	CULTURE_CONSTRUCTION_TYPE_CONVENT,              // Convento
	CULTURE_CONSTRUCTION_TYPE_PREHISTORIC_CAVE,             // Cueva prehistórica
	CULTURE_CONSTRUCTION_TYPE_CAVE,                 // Cuevas y minas turísticas
	CULTURE_CONSTRUCTION_TYPE_DOLMEN,                       // Dolmen
	CULTURE_CONSTRUCTION_TYPE_OFFICE_BUILDING,              // Edificio de oficinas
	CULTURE_CONSTRUCTION_TYPE_PUBLIC_BUILDING,              // Edificio de uso público
	CULTURE_CONSTRUCTION_TYPE_BUILDING,                             // Edificio de viviendas
	CULTURE_CONSTRUCTION_TYPE_INDUSTRIAL_BUILDING,  // Edificio industrial
	CULTURE_CONSTRUCTION_TYPE_MILITAR_BUILDING,             // Edificio militar
	CULTURE_CONSTRUCTION_TYPE_ERMITA,                       // Ermita
	CULTURE_CONSTRUCTION_TYPE_FORTRESS,             // Fortaleza
	CULTURE_CONSTRUCTION_TYPE_FORT,                 // Fuerte
	CULTURE_CONSTRUCTION_TYPE_SCULPTURAL_GROUP,             //Grupos escultóricos
	CULTURE_CONSTRUCTION_TYPE_CHURCH,                       // Iglesia
	CULTURE_CONSTRUCTION_TYPE_CIVIL,                        // Ingeniería civil
	CULTURE_CONSTRUCTION_TYPE_GARDEN,                       // Jardín
	CULTURE_CONSTRUCTION_TYPE_LONJA,                        // Lonja
	CULTURE_CONSTRUCTION_TYPE_FARMHOUSE,            // Masía
	CULTURE_CONSTRUCTION_TYPE_FORTIFIED_FARMHOUSE,  // Masía fortificada
	CULTURE_CONSTRUCTION_TYPE_MENHIR,                       // Menhir
	CULTURE_CONSTRUCTION_TYPE_MOSQUE,                       // Mezquita
	CULTURE_CONSTRUCTION_TYPE_MONASTERY,            // Monasterio
	CULTURE_CONSTRUCTION_TYPE_MONOLITH,             // Monolito
	CULTURE_CONSTRUCTION_TYPE_WALLS,                        //      Murallas
	CULTURE_CONSTRUCTION_TYPE_NECROPOLIS,           // Necrópolis
	CULTURE_CONSTRUCTION_TYPE_MANSION,              // Palacete
	CULTURE_CONSTRUCTION_TYPE_PALACE,                       // Palacio
	CULTURE_CONSTRUCTION_TYPE_PANTHEON,             // Panteón
	CULTURE_CONSTRUCTION_TYPE_PAZO,                 // Pazo
	CULTURE_CONSTRUCTION_TYPE_PYRAMID,              // Pirámide
	CULTURE_CONSTRUCTION_TYPE_SQUAERE,              // Plaza
	CULTURE_CONSTRUCTION_TYPE_BULLRING,             // Plaza de toros
	CULTURE_CONSTRUCTION_TYPE_PORTICO,              // Pórtico
	CULTURE_CONSTRUCTION_TYPE_BRIDGE,                       // Puente
	CULTURE_CONSTRUCTION_TYPE_DOOR,                 // Puerta
	CULTURE_CONSTRUCTION_TYPE_WALLED,                       // Recinto amurallado
	CULTURE_CONSTRUCTION_TYPE_RESIDENCE,            // Residencia
	CULTURE_CONSTRUCTION_TYPE_RUINS,                        // Ruinas
	CULTURE_CONSTRUCTION_TYPE_SANCTUARY,            // Santuario
	CULTURE_CONSTRUCTION_TYPE_SEMINAR,              // Seminario
	CULTURE_CONSTRUCTION_TYPE_GRAVE,                        // Sepulcro
	CULTURE_CONSTRUCTION_TYPE_SYNAGOGUE,            // Sinagoga
	CULTURE_CONSTRUCTION_TYPE_TAULAS,                       // Taulas, talayots y navetas
	CULTURE_CONSTRUCTION_TYPE_THEATRE,              // Teatro
	CULTURE_CONSTRUCTION_TYPE_TEMPLE,                       // Templo
	CULTURE_CONSTRUCTION_TYPE_TEMPLE_FORT,  // Templo-fortaleza
	CULTURE_CONSTRUCTION_TYPE_THERMAL_BATHS,        // Termas
	CULTURE_CONSTRUCTION_TYPE_TOWER,                        // Torre
	CULTURE_CONSTRUCTION_TYPE_TOMB,                 // Túmulo funerario
	CULTURE_CONSTRUCTION_TYPE_UNIVERSITY,           // Universidad
	CULTURE_CONSTRUCTION_TYPE_VILLA,                        // Villa
	CULTURE_CONSTRUCTION_TYPE_ARCHAEOLOGICAL,        // Yacimiento arqueológic

	CULTURE_DESIGNATION_COLLECTION_MUSEUM,	// Colección museográfica
	CULTURE_DESIGNATION_REGIONAL_MUSEUM,	// Museo Autonómico
	CULTURE_DESIGNATION_CATHEDRAL_MUSEUM,	// Museo Catedralicio
	CULTURE_DESIGNATION_DIOCESAN_MUSEUM,	// Museo Diocesano
	CULTURE_DESIGNATION_ISLAND_MUSEUM,		// Museo Insular
	CULTURE_DESIGNATION_LOCAL_MUSEUM,		// Museo local
	CULTURE_DESIGNATION_MUNICIPAL_MUSEUM,	// Museo Municipal
	CULTURE_DESIGNATION_NATIONAL_MUSEUM,	// Museo Nacional
	CULTURE_DESIGNATION_PRIVATE_MUSEUM,		// Museo particular
	CULTURE_DESIGNATION_PROVINCIAL_MUSEUM,	// Museo Provincial
	CULTURE_DESIGNATION_NATIONAL_HERITAGE,	// Patrimonio Nacional
	
	CULTURE_HISTORICAL_PERIOD_VARIOUS_TIMES,         // Diversas épocas
	CULTURE_HISTORICAL_PERIOD_VARIOUS_STYLES,                // Diversos estilos
	CULTURE_HISTORICAL_PERIOD_BRONZE_AGE,                    // Edad del Bronce
	CULTURE_HISTORICAL_PERIOD_IRON_COPPER_AGE,       // Edad del Hierro y Cobre
	CULTURE_HISTORICAL_PERIOD_IBERIAN,                       // Ibérico
	CULTURE_HISTORICAL_PERIOD_ROMANIZATION_BC,       // Romanización (A.C.)
	CULTURE_HISTORICAL_PERIOD_ROMANIZATION_AC,       // Romanización (d.C.)
	CULTURE_HISTORICAL_PERIOD_CENTURY_1,                     // Siglo 1
	CULTURE_HISTORICAL_PERIOD_CENTURY_1_BC,          // Siglo 1 (a.C.)
	CULTURE_HISTORICAL_PERIOD_CENTURY_2,                     // Siglo 2
	CULTURE_HISTORICAL_PERIOD_CENTURY_3,                     // Siglo 3
	CULTURE_HISTORICAL_PERIOD_CENTURY_4,                     // Siglo 4
	CULTURE_HISTORICAL_PERIOD_CENTURY_5,                     // Siglo 5
	CULTURE_HISTORICAL_PERIOD_CENTURY_6,                     // Siglo 6
	CULTURE_HISTORICAL_PERIOD_CENTURY_7,                     // Siglo 7
	CULTURE_HISTORICAL_PERIOD_CENTURY_8,                     // Siglo 8
	CULTURE_HISTORICAL_PERIOD_CENTURY_9,                     // Siglo 9
	CULTURE_HISTORICAL_PERIOD_CENTURY_10,                    // Siglo 10
	CULTURE_HISTORICAL_PERIOD_CENTURY_11,                    // Siglo 11
	CULTURE_HISTORICAL_PERIOD_CENTURY_12,                    // Siglo 12
	CULTURE_HISTORICAL_PERIOD_CENTURY_13,                    // Siglo 13
	CULTURE_HISTORICAL_PERIOD_CENTURY_14,                    // Siglo 14
	CULTURE_HISTORICAL_PERIOD_CENTURY_15,                    // Siglo 15
	CULTURE_HISTORICAL_PERIOD_CENTURY_16,                    // Siglo 16
	CULTURE_HISTORICAL_PERIOD_CENTURY_17,                    // Siglo 17
	CULTURE_HISTORICAL_PERIOD_CENTURY_18,                    // Siglo 18
	CULTURE_HISTORICAL_PERIOD_CENTURY_19,                    // Siglo 19
	CULTURE_HISTORICAL_PERIOD_CENTURY_20,                    // Siglo 20
	CULTURE_HISTORICAL_PERIOD_CENTURY_21,                     // Siglo 21
	
	// Beach
	BEACH_BATH_CONDITION_CALM,
	BEACH_BATH_CONDITION_STRONG_WAVES,
	BEACH_BATH_CONDITION_MODERATE_WAVES,
	BEACH_BATH_CONDITION_SOFT_WAVES,
	
	BEACH_COMPOSITION_SAND,
	BEACH_COMPOSITION_VOLCANIC_BLACK_SAND,
	BEACH_COMPOSITION_GRAVEL,
	BEACH_COMPOSITION_STONE,
	BEACH_COMPOSITION_ROCK,	
	
	BEACH_SAND_TYPE_WHITE,
	BEACH_SAND_TYPE_GOLDEN,
	BEACH_SAND_TYPE_DARK,

	;
	
	public static Set<Flag> ALL_ACCESSIBILITY_FLAGS = withPreffix("ACCESSIBILITY");
	
	public static Set<Flag> ALL_BEACH_FLAGS = withPreffix("BEACH");
	
	public static Set<Flag> ALL_BUSINESS_ACTIVITY_FLAGS = withPreffix("BUSINESS_ACTIVITY");
	public static Set<Flag> ALL_BUSINESS_SERVICES_LODGING_FLAGS = withPreffix("BUSINESS_SERVICES_LODGING");
	public static Set<Flag> ALL_BUSINESS_SERVICES_GOLF_FLAGS = withPreffix("BUSINESS_SERVICES_GOLF");
	public static Set<Flag> ALL_BUSINESS_SERVICES_NAUTICAL_FLAGS = withPreffix("BUSINESS_SERVICES_NAUTICAL");
	public static Set<Flag> ALL_BUSINESS_SERVICES_SKI_FLAGS = withPreffix("BUSINESS_SERVICES_SKI");
	public static Set<Flag> ALL_BUSINESS_SERVICES_SPORTS_FLAGS = withPreffix("BUSINESS_SERVICES_SPORTS");
	
	public static Set<Flag> ALL_COMMON_FLAGS = withPreffix("COMMON");
	
	public static Set<Flag> ALL_CULTURE_ARTISTIC_PERIOD_FLAGS= withPreffix("CULTURE_ARTISTIC_PERIOD");
	public static Set<Flag> ALL_CULTURE_CONSTRUCTION_TYPE_FLAGS= withPreffix("CULTURE_CONSTRUCTION_TYPE");
	public static Set<Flag> ALL_CULTURE_DESIGNATION_FLAGS= withPreffix("CULTURE_DESIGNATION");
	public static Set<Flag> ALL_CULTURE_HISTORICAL_PERIOD_FLAGS= withPreffix("CULTURE_HISTORICAL_PERIOD");
	
	public static Set<Flag> ALL_FAMILY_FLAGS = withPreffix("FAMILY");
	
	public static Set<Flag> ALL_LODGING_FLAGS =  withPreffix("LODGING");
	
	public static Set<Flag> ALL_NATURE_FLAGS = withPreffix("NATURE");
	
	public static Set<Flag> ALL_QUALITY_FLAGS = withPreffix("QUALITY");

	
	
	private static Set<Flag> withPreffix(String preffix){
		Set<Flag> result = new HashSet<Flag>();
		for (Flag flag : Flag.values()) {
			if (flag.toString().startsWith(preffix)){
				result.add(flag);
			}
		}
		return Sets.immutableEnumSet(result);
	}
			
}