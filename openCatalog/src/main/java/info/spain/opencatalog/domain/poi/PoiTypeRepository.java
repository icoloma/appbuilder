package info.spain.opencatalog.domain.poi;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import info.spain.opencatalog.domain.poi.types.BasicPoi;
import info.spain.opencatalog.domain.poi.types.PoiTypeID;
import info.spain.opencatalog.domain.poi.types.beach.BeachPoiType;
import info.spain.opencatalog.domain.poi.types.culture.CulturePoiType;
import info.spain.opencatalog.domain.poi.types.lodging.*;
import info.spain.opencatalog.domain.poi.types.nature.NaturalSpacePoiType;

import java.util.Map;
import java.util.Set;

/**
 * <pre>
 * Especifica los valores v&aacute;lidos para cada tipo de POI.
 * Para ello se crea una instancia de cada tipo y se le especifican los valores que luego serán los posibles valores válidos que podrán almacenar.
 * </pre>
 */
public class PoiTypeRepository {
	
	/** Referencias a las agrupaciones lógicas de los diferentes tipos de POI */
	public static final Set<PoiTypeID> LODGING_TYPES = Sets.newHashSet(PoiTypeID.HOTEL, PoiTypeID.CAMPING, PoiTypeID.APARTMENT);
	public static final Set<PoiTypeID> CULTURE_TYPES = Sets.newHashSet(PoiTypeID.MUSEUM, PoiTypeID.MONUMENT, PoiTypeID.PARK_GARDEN);
	
	
	/** Almacena la información permitida para cada tipo */
   private static Map<PoiTypeID, BasicPoi> types;
    
    
   static {
	   
        types = Maps.newHashMap();
        
        // POI
        types.put(PoiTypeID.POI, basicPoi());
        
        
        // Lodging
        types.put(PoiTypeID.HOTEL, hotel());
        types.put(PoiTypeID.CAMPING, camping());
        types.put(PoiTypeID.APARTMENT, apartment());
        
        // Nature 
        types.put(PoiTypeID.BEACH, beach());
        types.put(PoiTypeID.NATURAL_SPACE, nature());
        
        // Culture
        types.put(PoiTypeID.MUSEUM, museum());
        types.put(PoiTypeID.MONUMENT, monument());
        types.put(PoiTypeID.PARK_GARDEN, parkGarden());
        
        
        
    }
   
   private static BasicPoi basicPoi(){
	   return new BasicPoi();
   }
    
   /** Hotel */
   private static LodgingPoi hotel(){
    	return new LodgingPoi()
        .setScores(
    		Score.STAR_1, 
    		Score.STAR_2,
    		Score.STAR_3, 
    		Score.STAR_4,
    		Score.STAR_5, 
    		Score.LUXURY)
        .setLodgingFlags(
        	LodgingFlag.CASINO, 
        	LodgingFlag.EXCHANGE,
    		LodgingFlag.BAR, 
    		LodgingFlag.BIKE_RENT, 
    		LodgingFlag.CREDIT_CARD)
        .setRoomTypes(
            RoomType.HAB1,
            RoomType.HAB2,
            RoomType.HAB3,
            RoomType.HAB4,
            RoomType.SUITE)
        .setLodgingTypeFlags(
    		RoomFlag.JACUZZI,
    		RoomFlag.WIFI,
    		RoomFlag.SAFE_BOX)
        .setQualityCertificates(
    		QualityCertificateFlag.CAMPSA,
    		QualityCertificateFlag.Q_CALIDAD)
    	//.setFlags() // común a cualquier poi
        ;
    }

   /** Camping */
   private static LodgingPoi camping() {
    	return new LodgingPoi()
		.setScores(
			Score.OTHER,
			Score.CAT_1,
			Score.CAT_2,
			Score.CAT_3,
			Score.LUXURY)	
		.setLodgingTypeFlags(
			RoomFlag.NONE)
	    .setLodgingFlags(
	    	LodgingFlag.BBQ,
	    	LodgingFlag.CLOACKROOM,
    		LodgingFlag.BAR, 
    		LodgingFlag.BIKE_RENT, 
    		LodgingFlag.CREDIT_CARD, 
    		LodgingFlag.PETS_ALLOWED)
	    .setRoomTypes(
            RoomType.ADULT,        // Facturación por Adulto
            RoomType.CHILD,        // Facturación por Niño
            RoomType.PET,        // Facturación por mascota
            RoomType.TENT,        // Tienda de campaña
            RoomType.TENT_FAM,    // Tienda de campaña familiar
            RoomType.BUS,        // Autocar
            RoomType.MOTORHOME,    // Caravana
            RoomType.CAR,        // Coche
            RoomType.MOTORBIKE    // Moto
        )
	    //.setFlags()   // común a cualquier poi
	    ;
    }
   
   	/** Apartamento */
    private static LodgingPoi apartment(){
    	return new LodgingPoi()
    	.setScores(
			Score.KEY_1,
			Score.KEY_2,
			Score.KEY_3,
			Score.KEY_4,
			Score.KEY_5,
    		Score.LUXURY)	
        .setLodgingFlags(
    		LodgingFlag.BAR, 
    		LodgingFlag.BIKE_RENT, 
    		LodgingFlag.CREDIT_CARD, 
    		LodgingFlag.PETS_ALLOWED)
        .setRoomTypes(
            RoomType.HAB1,
            RoomType.HAB2,
            RoomType.HAB3)
        //.setFlags()   // común a cualquier poi
        ;
    }
    
    /** Playa */
    private static BeachPoiType beach(){
    	return new BeachPoiType()
    	.setDisabledAccessibility(
    			AccessibilityFlag.ASSISTANCE_TO_DISABLED,
    			AccessibilityFlag.DISABLED_ACCESS,
    			AccessibilityFlag.GUIDE_DOG_ALLOWED,
    			AccessibilityFlag.PARKING_ACCESSIBLE)
    	.setQualityCertificates(
    			QualityCertificateFlag.BANDERA_AZUL,
    			QualityCertificateFlag.ACCESIBILIDAD,
    			QualityCertificateFlag.NATURISTA);
    }
    
    /** espacio natural*/
    private static NaturalSpacePoiType nature(){
    	return new NaturalSpacePoiType()
    	.setDisabledAccessibility(
    			AccessibilityFlag.ASSISTANCE_TO_DISABLED,
    			AccessibilityFlag.DISABLED_ACCESS,
    			AccessibilityFlag.GUIDE_DOG_ALLOWED,
    			AccessibilityFlag.PARKING_ACCESSIBLE)
    	.setQualityCertificates(
    			QualityCertificateFlag.ECOTURISMO,
    			QualityCertificateFlag.ACCESIBILIDAD,
    			QualityCertificateFlag.PATRIMONIO_HUMANIDAD
    			);
    }
    /** Museo */
    private static CulturePoiType museum(){
    	return new CulturePoiType()
    		.setQualityCertificates(
    			QualityCertificateFlag.ACCESIBILIDAD,
    			QualityCertificateFlag.MICHELIN,
    			QualityCertificateFlag.CAMPSA);
    }
   
    /** Monumento */
    private static CulturePoiType monument(){
    	return new CulturePoiType()
    	.setQualityCertificates(
    			QualityCertificateFlag.PATRIMONIO_HUMANIDAD,
    			QualityCertificateFlag.ACCESIBILIDAD,
    			QualityCertificateFlag.MICHELIN,
    			QualityCertificateFlag.RESERVA_BIOSFERA,
    			QualityCertificateFlag.CAMPSA);
    }
    
    /** Parques y jardines*/
    private static CulturePoiType parkGarden(){
    	return new CulturePoiType()
    	.setQualityCertificates(
    			QualityCertificateFlag.PATRIMONIO_HUMANIDAD,
    			QualityCertificateFlag.ACCESIBILIDAD,
    			QualityCertificateFlag.MICHELIN,
    			QualityCertificateFlag.RESERVA_BIOSFERA,
    			QualityCertificateFlag.CAMPSA);
    }
    
  
  
    
    public static BasicPoi getPoiType(PoiTypeID type){
		BasicPoi result =  types.get(type);
		if (result == null){
			throw new IllegalArgumentException("No PoiType found with name " + type );
		}
		return result;
	}


}   
    
