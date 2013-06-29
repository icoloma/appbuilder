package info.spain.opencatalog.domain.poi;

import info.spain.opencatalog.domain.poi.lodging.BusinessServiceFlag;
import info.spain.opencatalog.domain.poi.lodging.LodgingType;
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
public class PoiTypes {
	
  /** Mantiene indexado los diferntes tipos de Poi en función de su Id */
   private static Map<PoiTypeID, PoiType> types;
  
   public static final PoiType BASIC = new PoiType(PoiTypeID.BASIC);
   
   public static final LodgingType HOTEL = hotelType();
   public static final LodgingType CAMPING = campingType();
   public static final LodgingType APARTMENT = apartmentType();
  
   public static final PoiType BEACH = beachType();
   
   public static final PoiType NATURAL_SPACE = naturalSpaceType();
   
   public static final PoiType MUSEUM = museumType();
   public static final PoiType MONUMENT = monumentType();
   public static final PoiType PARK_GARDEN  = parkGardenType();
     
   
   static {
	   
	    types = Maps.newHashMap();
        
        // Lodging
        types.put(PoiTypeID.HOTEL, HOTEL );
        types.put(PoiTypeID.CAMPING, CAMPING);
        types.put(PoiTypeID.APARTMENT, APARTMENT);

        // Nature 
        types.put(PoiTypeID.BEACH, BEACH);
        types.put(PoiTypeID.NATURAL_SPACE, NATURAL_SPACE);

        // Culture
        types.put(PoiTypeID.MUSEUM, MUSEUM);
        types.put(PoiTypeID.MONUMENT, MONUMENT);
        types.put(PoiTypeID.PARK_GARDEN, PARK_GARDEN);
        
        
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
    		
    	.setAllowedBusinessServiceFlags(BusinessServiceFlag.values())
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
		.setAllowedFlags(Flag.values())
		.setAllowedFamilyServiceFlag(FamilyServiceFlag.values())
		;
    }
    
    /** Playa */
    private static PoiType beachType(){
    	return new PoiType(PoiTypeID.BEACH)
    	.setAllowedAccessibilityFlags(AccessibilityFlag.values())
		.setAllowedFlags(Flag.values())
		.setAllowedQualityCertificateFlags(
    			QualityCertificateFlag.BANDERA_AZUL,
    			QualityCertificateFlag.ACCESIBILIDAD,
    			QualityCertificateFlag.NATURISTA);
    }
    
    /** espacio natural*/
    private static PoiType naturalSpaceType(){
    	return new PoiType(PoiTypeID.NATURAL_SPACE)
    	.setAllowedAccessibilityFlags(AccessibilityFlag.values())
		.setAllowedFlags(Flag.values())
		.setAllowedQualityCertificateFlags(
    			QualityCertificateFlag.ECOTURISMO,
    			QualityCertificateFlag.ACCESIBILIDAD,
    			QualityCertificateFlag.PATRIMONIO_HUMANIDAD
    			);
    }
    /** Museo */
    private static PoiType museumType(){
    	return new PoiType(PoiTypeID.MUSEUM)
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
    private static PoiType monumentType(){
    	return new PoiType(PoiTypeID.MONUMENT)
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
    private static PoiType parkGardenType(){
    	return new PoiType(PoiTypeID.PARK_GARDEN)
	    	.setAllowedAccessibilityFlags(AccessibilityFlag.values())
			.setAllowedFlags(Flag.values())
			.setAllowedQualityCertificateFlags(
    			QualityCertificateFlag.PATRIMONIO_HUMANIDAD,
    			QualityCertificateFlag.ACCESIBILIDAD,
    			QualityCertificateFlag.MICHELIN,
    			QualityCertificateFlag.RESERVA_BIOSFERA,
    			QualityCertificateFlag.CAMPSA);
    }
    
  
  
   /** Devuelve un PoiType en función del PoiTypeId */ 
   public static PoiType valueOf(PoiTypeID typeId){
		PoiType result =  types.get(typeId);
		if (result == null){
			throw new IllegalArgumentException("No PoiType found with id " + typeId );
		}
		return result;
	}


}   
    
