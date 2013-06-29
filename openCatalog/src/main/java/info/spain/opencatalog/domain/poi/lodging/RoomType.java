package info.spain.opencatalog.domain.poi.lodging;

/** 
 * 	Concepto facturable dependiente del alojamiento:  
 *  Habitación doble (Hotel), 
 *  Caravana (Camping) 
 */
public enum RoomType {

	SUITE,		// Suite
	HAB4,		// Habitación cuádruple
	HAB3,		// Habitación triple
	HAB2,		// Habitación doble
	HAB1,		// Habitación individual
	
	ADULT,		// Facturación por Adulto
	CHILD,		// Facturación por Niño
	PET,		// Facturación por mascota 
	TENT,		// Tienda de campaña
	TENT_FAM,	// Tienda de campaña familiar
	BUS,		// Autocar
	MOTORHOME,	// Caravana
	CAR,		// Coche
	MOTORBIKE,	// Moto

}
