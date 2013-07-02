package info.spain.opencatalog.domain.poi;



/**
 * Características que pueden tener o no CUALQUIER  POI
 */
public enum Flag {

	AIR_CONDITIONING,	// Aire acondicionado en zonas comunes,
	BAR,				// Bar/Cafetería
	BBQ,				// Barbacoa
	BIKE_RENT,			// Alquiler de bicicletas
	CAR_RENT,			// Alquiler de coches
	CREDIT_CARD,		// Admite tarjetas de crédito
	EXCHANGE,			// Cambio de moneda
	GUIDED_TOUR,		// visitas guiadas
	HANDICAPPED,		// Acceso a minusválidos 
	LEISURE,			// Actividades de ocio / animación
	LOCKER,				// Consigna
	PARKING,			// Aparcamientos
	REPAIR,				// Taller de reparación
	RESTAURANT,			// Restaurante
	NO_CHILDS,			// Adultos sin niños
	PETS_ALLOWED,		// Admite animales de compañia
	PETS_ALLOWED_SIZE,	// Admite perros según tamaño
	SHOP,				// Tienda
	WC,
	
    //  Específicas de Lodging
    BUSSINESS_CENTER,	// Bussiness center
    CASINO,				// Casino
    FREE_FACILITIES,	// Amenities gratuitas
    FREE_FACILITIES_REQ,// Amenities gratuitas previa petición
    LIBRARY,			// Biblioteca
    LIFT,				// Ascensor
    ROOF_TERRACE,		// Azotea
    SATELLITE,			// Antena parabólica
    
    
    // Específicas de parque natural
	NATURAL_MONUMENT,	// Monumento Natural
	NATIONAL_PARK,		// Parque Nacional
	NATURAL_PARK,		// Parque Natural
	REGIONAL_PARK,		// Parque Regional
	BIOSPHERE_RESERVE,	// Reserva Biosfera
	NATURAL_RESERVE		// Reserva Natural
    

}