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
	BUSINESS_ACTIVITY_GUIDE_TOUR,				// Visitas guiadas en el espacio protegido y su entorno|
	BUSINESS_ACTIVITY_EDUCATIONAL_ACTIVITIES,	// Actividades didácticas y de educación ambiental
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
	BUSINESS_SERVICES_SPORTS_TENIS
    ;
	
	public static Set<Flag> ALL_ACCESSIBILITY_FLAGS = withPreffix("ACCESSIBILITY");
	public static Set<Flag> ALL_BUSINESS_ACTIVITY_FLAGS = withPreffix("BUSINESS_ACTIVITY");
	public static Set<Flag> ALL_BUSINESS_SERVICES_LODGING_FLAGS = withPreffix("BUSINESS_SERVICES_LODGING");
	public static Set<Flag> ALL_BUSINESS_SERVICES_GOLF_FLAGS = withPreffix("BUSINESS_SERVICES_GOLF");
	public static Set<Flag> ALL_BUSINESS_SERVICES_NAUTICAL_FLAGS = withPreffix("BUSINESS_SERVICES_NAUTICAL");
	public static Set<Flag> ALL_BUSINESS_SERVICES_SKI_FLAGS = withPreffix("BUSINESS_SERVICES_SKI");
	public static Set<Flag> ALL_BUSINESS_SERVICES_SPORTS_FLAGS = withPreffix("BUSINESS_SERVICES_SPORTS");
	public static Set<Flag> ALL_COMMON_FLAGS = withPreffix("COMMON");
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