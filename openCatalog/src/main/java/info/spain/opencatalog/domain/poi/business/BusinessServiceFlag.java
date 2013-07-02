package info.spain.opencatalog.domain.poi.business;
/**
 * Recursos que el negocio ofrece:
 * - Alquiler de audiovisual
 * - Sala convenciones
 * - ...
 */
public enum BusinessServiceFlag {
	
	// Common
	SOS_SERVICE,
	MEDICAL_SERVICE,
	
	
	
	// EcoTourism
	GUIDE_TOUR,				// Visitas guiadas en el espacio protegido y su entorno|
	EDUCATIONAL_ACTIVITIES,	// Actividades didácticas y de educación ambiental
	LOCAL_PRODUCT_SALES,	// Venta de productos locales
	ACTIVE_TOURISM,			//Actividades de turismo activo

	// Lodging
	AUDIOVISUAL_RENT,			// Alquiler equipos audiovisuales
	FAX,						// Fax 
	CONVENTION_ROOM,			// Sala de convenciones
	MEETING_ROOM,				// Sala(s) de reuniones
	SECRETARIAL_SERVICE,		// Servicio de secretaría
	DOCUMENT_TRANSLATION,		// Traducción de documentos 
	SIMULTANIOUS_TRANSLATION,	//Traducción simultánea

	// Campo de Golf
	BUNKER,						// Bunker
	CADDY,						// Caddy
	CALL_PLAY_CARD,				// Admite tarjeta call & play
	CLUB,						// Palos de golf
	COVERED_DRIVING_RANGE,		// Covered driving range|
	DRIVING_RANGE,				// Driving range
	ELECTRIC_TROLLEY,			// Electric Trolley
	HAND_TROLLEY,				//
	PITCHING,
	PITCHING_GREEN,
	PUTTING_GREEN,
	WATER_ROUTE,				// Agua en el recorrido
	WC_ROUTE,					// WC en el recorrido
	
	
	// Estaciones náuticas
	MOORING_RENTALS ,		// Alquiler de amarres
	WHALE_WATCHING,			// Avistamiento de cetáceos
	CATAMARAN,				// Catamarán
	CHARTERS,				// Chárters
	CRUISES,				// Cruceros
	SKI_BUS,				// Esquí bus
	SKIING,					// Esquí náutico
	BOAT_TRIPS,				// Excursiones marítimas
	SUBMARINE,				// Inmersión en submarino
	KAYAKING,				// Kayak
	KITESURF,				// KiteSurf 
	POWERBOATING,			// Motonáutica
	PARASAILING,			// Parasailing
	FISHING ,				// Pesca
	SNORKELLING,			// Snorkel
	DIVING,					// Submarinismo
	SAILING,				// Vela
	WAKEBOARD,				// Wakeboard
	WINDSURF_SURF,			// Windsurf/Surf
	
	//Estación de esquí
	SKI_RENTALS,			// Alquileres de material de esquí
	SKI_SCHOOL,				

	// Instalaciones para deportes varios
	FOOTBALL,
	GOLF,
	GYM,
	HORSE,
	JACUZZI,
	LOCKER_ROOM,	
	PADDLE,
	POOL,
	SAUNA,
	SHOWERS,				
	TENIS
	
	
}
