package info.spain.opencatalog.domain.poi.types;

import info.spain.opencatalog.domain.poi.AccessibilityFlag;
import info.spain.opencatalog.domain.poi.FamilyServiceFlag;
import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.QualityCertificateFlag;
import info.spain.opencatalog.domain.poi.business.BusinessActiviyFlag;
import info.spain.opencatalog.domain.poi.business.BusinessServiceFlag;
import info.spain.opencatalog.domain.poi.business.BusinessTypeFlag;
import info.spain.opencatalog.domain.poi.lodging.RoomFlag;
import info.spain.opencatalog.domain.poi.lodging.RoomType;
import info.spain.opencatalog.domain.poi.lodging.Score;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * <pre>
 * Especifica los valores validos para cada tipo de POI.
 * Para ello se crea una instancia de cada tipo y se le especifican los valores que luego serán los posibles valores válidos que podrán almacenar.
 * </pre>
 */
public class PoiTypeRepository {
	
	/** Mantiene indexado los diferntes tipos de Poi en función de su Id */
   private static Map<PoiTypeID, BasicPoiType> types;
 
   /** Devuelve un BasicPoiType en función del PoiTypeId */ 
   public static BasicPoiType getType(PoiTypeID typeId){
		BasicPoiType result = types.get(typeId);
		if (result == null){
			throw new IllegalArgumentException("No BasicPoiType found with id " + typeId );
		}
		return result;
	}
   
   static {
	   
	    types = Maps.newHashMap();
	    
	    //Basic
	    types.put(PoiTypeID.BASIC, new BasicPoiType(PoiTypeID.BASIC));
	  
        // Lodging
        types.put(PoiTypeID.HOTEL, hotelType() );
        
        types.put(PoiTypeID.CAMPING, campingType());
        types.put(PoiTypeID.APARTMENT, apartmentType());

        // Nature 
        types.put(PoiTypeID.BEACH, beachType());
        types.put(PoiTypeID.NATURAL_SPACE, naturalSpaceType());
        
        // Culture
        types.put(PoiTypeID.MUSEUM, museumType());
        types.put(PoiTypeID.MONUMENT, monumentType());
        types.put(PoiTypeID.PARK_GARDEN, parkGardenType());
        
        // Business
        types.put(PoiTypeID.ECO_TOURISM, ecoTourismType());
        types.put(PoiTypeID.GOLF, golfType());
        types.put(PoiTypeID.NAUTICAL_STATION, nauticalStationType());
        
    }
   
  
    
   /** Hotel */
   private static LodgingType hotelType(){
	   
    	return new LodgingType(PoiTypeID.HOTEL)
        
    	.setAllowedScores(
    		Score.STAR_1, 
    		Score.STAR_2,
    		Score.STAR_3, 
    		Score.STAR_4,
    		Score.STAR_5, 
    		Score.LUXURY)
    		
        .setAllowedRoomTypes(
            RoomType.HAB1,
            RoomType.HAB2,
            RoomType.HAB3,
            RoomType.HAB4,
            RoomType.SUITE)
            
        .setAllowedRoomFlags(
        	RoomFlag.AIR_CONDITIONED,
    		RoomFlag.JACUZZI,
    		RoomFlag.WIFI,
    		RoomFlag.SAFE_BOX)
        
    	.setAllowedQualityCertificateFlags(
    		QualityCertificateFlag.CAMPSA,
    		QualityCertificateFlag.Q_CALIDAD)
    		
    	.setAllowedBusinessServiceFlags(
    		BusinessServiceFlag.AUDIOVISUAL_RENT,			// Alquiler equipos audiovisuales
    		BusinessServiceFlag.FAX,						// Fax 
    		BusinessServiceFlag.CONVENTION_ROOM,			// Sala de convenciones
    		BusinessServiceFlag.MEETING_ROOM,				// Sala(s) de reuniones
    		BusinessServiceFlag.SECRETARIAL_SERVICE,		// Servicio de secretaría
    		BusinessServiceFlag.DOCUMENT_TRANSLATION,		// Traducción de documentos 
    		BusinessServiceFlag.SIMULTANIOUS_TRANSLATION	//Traducción simultán	
    	)
    	.setAllowedFamilyServiceFlag(FamilyServiceFlag.values())
		.setAllowedAccessibilityFlags(AccessibilityFlag.values())
		.setAllowedFlags(Flag.values())
		
    	;
    }

   /** Camping */
   private static LodgingType campingType() {
    	return new LodgingType(PoiTypeID.CAMPING)
		.setAllowedScores(
			Score.OTHER,
			Score.CAT_1,
			Score.CAT_2,
			Score.CAT_3,
			Score.LUXURY)	
		.setAllowedRoomTypes(
            RoomType.ADULT,			// Facturación por Adulto
            RoomType.CHILD,			// Facturación por Niño
            RoomType.PET,			// Facturación por mascota
            RoomType.TENT,			// Tienda de campaña
            RoomType.TENT_FAM,		// Tienda de campaña familiar
            RoomType.BUS,			// Autocar
            RoomType.MOTORHOME,		// Caravana
            RoomType.CAR,			// Coche
            RoomType.MOTORBIKE)		// Moto
        .setAllowedAccessibilityFlags(AccessibilityFlag.values())
		.setAllowedFlags(Flag.values())
		.setAllowedFamilyServiceFlag(FamilyServiceFlag.values())
		
	    ;
    }
   
   	/** Apartamento */
    private static LodgingType apartmentType(){
    	return new LodgingType(PoiTypeID.APARTMENT)
    	.setAllowedScores(
			Score.KEY_1,
			Score.KEY_2,
			Score.KEY_3,
			Score.KEY_4,
			Score.KEY_5,
    		Score.LUXURY)	
        .setAllowedRoomTypes(
            RoomType.HAB1,
            RoomType.HAB2,
            RoomType.HAB3)
        .setAllowedAccessibilityFlags(AccessibilityFlag.values())
		.setAllowedFamilyServiceFlag(FamilyServiceFlag.values())
		.setAllowedFlags(Flag.values())
		;
    }
    
    /** Playa */
    private static BasicPoiType beachType(){
    	return new BasicPoiType(PoiTypeID.BEACH)
    	.setAllowedAccessibilityFlags(AccessibilityFlag.values())
		.setAllowedFlags(Flag.values())
		.setAllowedQualityCertificateFlags(
    			QualityCertificateFlag.BANDERA_AZUL,
    			QualityCertificateFlag.ACCESIBILIDAD,
    			QualityCertificateFlag.NATURISTA);
    }
    
    /** espacio natural*/
    private static BasicPoiType naturalSpaceType(){
    	return new BasicPoiType(PoiTypeID.NATURAL_SPACE)
    	.setAllowedAccessibilityFlags(AccessibilityFlag.values())
		.setAllowedFlags(Flag.values())
		.setAllowedQualityCertificateFlags(
    			QualityCertificateFlag.ECOTURISMO,
    			QualityCertificateFlag.ACCESIBILIDAD,
    			QualityCertificateFlag.PATRIMONIO_HUMANIDAD
    			);
    }
    /** Museo */
    private static BasicPoiType museumType(){
    	return new BasicPoiType(PoiTypeID.MUSEUM)
	    	.setAllowedAccessibilityFlags(AccessibilityFlag.values())
			.setAllowedFlags(Flag.values())
			.setAllowedFamilyServiceFlag(
				FamilyServiceFlag.KIDS_ENTERTAINMENT
			)
			.setAllowedQualityCertificateFlags(
    			QualityCertificateFlag.ACCESIBILIDAD,
    			QualityCertificateFlag.MICHELIN,
    			QualityCertificateFlag.CAMPSA);
    }
   
    /** Monumento */
    private static BasicPoiType monumentType(){
    	return new BasicPoiType(PoiTypeID.MONUMENT)
	    	.setAllowedAccessibilityFlags(AccessibilityFlag.values())
			.setAllowedFlags(Flag.values())
			.setAllowedQualityCertificateFlags(
    			QualityCertificateFlag.PATRIMONIO_HUMANIDAD,
    			QualityCertificateFlag.ACCESIBILIDAD,
    			QualityCertificateFlag.MICHELIN,
    			QualityCertificateFlag.RESERVA_BIOSFERA,
    			QualityCertificateFlag.CAMPSA);
    }
    
    /** Parques y jardines*/
    private static BasicPoiType parkGardenType(){
    	return new BasicPoiType(PoiTypeID.PARK_GARDEN)
	    	.setAllowedAccessibilityFlags(AccessibilityFlag.values())
			.setAllowedFlags(Flag.values())
			.setAllowedQualityCertificateFlags(
    			QualityCertificateFlag.PATRIMONIO_HUMANIDAD,
    			QualityCertificateFlag.ACCESIBILIDAD,
    			QualityCertificateFlag.MICHELIN,
    			QualityCertificateFlag.RESERVA_BIOSFERA,
    			QualityCertificateFlag.CAMPSA);
    }
    
    /** Empresas de ecoturismo */
    private static BusinessType ecoTourismType(){
    	return new BusinessType(PoiTypeID.ECO_TOURISM)
			.setAllowedFlags(Flag.values())
			.setAllowedAccessibilityFlags(AccessibilityFlag.values())
			
			.setAllowedQualityCertificateFlags(
    			QualityCertificateFlag.ACCESIBILIDAD,
    			QualityCertificateFlag.MICHELIN,
    			QualityCertificateFlag.CAMPSA,
    			QualityCertificateFlag.Q_CALIDAD)
    			
			.setAllowedActivityFlags(
				BusinessActiviyFlag.ACTIVE_TOURISM,
				BusinessActiviyFlag.EDUCATIONAL_ACTIVITIES,
				BusinessActiviyFlag.GUIDE_TOUR,
				BusinessActiviyFlag.LOCAL_PRODUCT_SALES)
				
			.setAllowedBusinessServiceFlags(
				BusinessServiceFlag.GUIDE_TOUR,				// Visitas guiadas en el espacio protegido y su entorno|
				BusinessServiceFlag.EDUCATIONAL_ACTIVITIES,	// Actividades didácticas y de educación ambiental
				BusinessServiceFlag.LOCAL_PRODUCT_SALES,	// Venta de productos locales
				BusinessServiceFlag.ACTIVE_TOURISM);		// Turismo activo
    }
    
  
    /** Empresas de  Golf */
    private static BusinessType golfType(){
    	return new BusinessType(PoiTypeID.GOLF)
			.setAllowedFlags(Flag.values())
			.setAllowedAccessibilityFlags(AccessibilityFlag.values())
			
			.setAllowedQualityCertificateFlags(
    			QualityCertificateFlag.ACCESIBILIDAD,
    			QualityCertificateFlag.MICHELIN,
    			QualityCertificateFlag.CAMPSA,
    			QualityCertificateFlag.Q_CALIDAD)
    		
    		.setAllowedActivityFlags(
				BusinessActiviyFlag.LESSONS,
				BusinessActiviyFlag.SCHOOL)
			
			.setAllowedBusinessTypeFlags(
				BusinessTypeFlag.COMERCIAL_GOLF,
				BusinessTypeFlag.MIXED_GOLF,
				BusinessTypeFlag.PARTNERS_GOLF)
			
			.setAllowedBusinessServiceFlags(
				BusinessServiceFlag.BUNKER,						// Bunker
				BusinessServiceFlag.CADDY,						// Caddy
				BusinessServiceFlag.CALL_PLAY_CARD,				// Admite tarjeta call & play
				BusinessServiceFlag.CLUB,						// Palos de golf
				BusinessServiceFlag.COVERED_DRIVING_RANGE,		// Covered driving range|
				BusinessServiceFlag.DRIVING_RANGE,				// Driving range
				BusinessServiceFlag.ELECTRIC_TROLLEY,			// Electric Trolley
				BusinessServiceFlag.HAND_TROLLEY,				//
				BusinessServiceFlag.PITCHING,
				BusinessServiceFlag.PITCHING_GREEN,
				BusinessServiceFlag.PUTTING_GREEN,
				BusinessServiceFlag.WATER_ROUTE,				// Agua en el recorrido
				BusinessServiceFlag.WC_ROUTE					// WC en el recorrido
				);		
    }
  
    /** Estación náutica */
    private static BusinessType nauticalStationType(){
    	return new BusinessType(PoiTypeID.NAUTICAL_STATION)
			.setAllowedFlags(Flag.values())
			.setAllowedAccessibilityFlags(AccessibilityFlag.values())
			.setAllowedBusinessServiceFlags(
				BusinessServiceFlag.MOORING_RENTALS ,		// Alquiler de amarres
				BusinessServiceFlag.WHALE_WATCHING,			// Avistamiento de cetáceos
				BusinessServiceFlag.CATAMARAN,				// Catamarán
				BusinessServiceFlag.CHARTERS,				// Chárters
				BusinessServiceFlag.CRUISES,				// Cruceros
				BusinessServiceFlag.SKI_BUS,				// Esquí bus
				BusinessServiceFlag.SKIING,					// Esquí náutico
				BusinessServiceFlag.BOAT_TRIPS,				// Excursiones marítimas
				BusinessServiceFlag.SUBMARINE,				// Inmersión en submarino
				BusinessServiceFlag.KAYAKING,				// Kayak
				BusinessServiceFlag.KITESURF,				// KiteSurf 
				BusinessServiceFlag.POWERBOATING,			// Motonáutica
				BusinessServiceFlag.PARASAILING,			// Parasailing
				BusinessServiceFlag.FISHING ,				// Pesca
				BusinessServiceFlag.SNORKELLING,			// Snorkel
				BusinessServiceFlag.DIVING,					// Submarinismo
				BusinessServiceFlag.SAILING,				// Vela
				BusinessServiceFlag.WAKEBOARD,				// Wakeboard
				BusinessServiceFlag.WINDSURF_SURF			// Windsurf/Surf
				);		
    }
  
   


}   
    
