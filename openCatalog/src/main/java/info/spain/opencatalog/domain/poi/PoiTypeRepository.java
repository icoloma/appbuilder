package info.spain.opencatalog.domain.poi;

import info.spain.opencatalog.domain.poi.types.AbstractPoiType;
import info.spain.opencatalog.domain.poi.types.beach.BeachPoiType;
import info.spain.opencatalog.domain.poi.types.lodging.LodgingFlag;
import info.spain.opencatalog.domain.poi.types.lodging.LodgingPoiType;
import info.spain.opencatalog.domain.poi.types.lodging.LodgingType;
import info.spain.opencatalog.domain.poi.types.lodging.LodgingTypeFlag;
import info.spain.opencatalog.domain.poi.types.lodging.Score;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Especifica los valores válidos para cada tipo de POI.
 * Para ello se crea una instancia de cada tipo y se le especifican los valores que luego serán los posibles valores válidos que podrán almacenar.
 */
public class PoiTypeRepository {
	
	/** Tipos de POI permitidos */
	public static enum PoiType {
		
		// Lodging
		HOTEL,
		CAMPING,
		APARTMENT,
		
		BEACH,
		
		
	}
	
	/** Referencias a las agrupaciones lógicas de los diferentes tipos de POI */
	public static final Set<PoiType> LODGING_TYPES = Sets.newHashSet(PoiType.HOTEL, PoiType.CAMPING, PoiType.APARTMENT);
	
	
	/** Almacena la información permitida para cada tipo */
   private static Map<PoiType, AbstractPoiType> types;
    
    
   static {
	   
        types = Maps.newHashMap();
        
        // Lodging
        types.put(PoiType.HOTEL, hotel());
        types.put(PoiType.CAMPING, camping());
        types.put(PoiType.APARTMENT, apartment());
        types.put(PoiType.BEACH, beach());
        
    
    }
    
    private static LodgingPoiType hotel(){
    	return new LodgingPoiType()
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
        .setLodgingTypes(
    		LodgingType.HAB1,
    		LodgingType.HAB2,
    		LodgingType.HAB3,
    		LodgingType.HAB4,
    		LodgingType.SUITE)
        .setLodgingTypeFlags(
    		LodgingTypeFlag.JACUZZI, 
    		LodgingTypeFlag.WIFI, 
    		LodgingTypeFlag.SAFE_BOX)
        .setQualityCertificates(
    		QualityCertificate.CAMPSA,
    		QualityCertificate.Q_CALIDAD)
    	//.setFlags() // común a cualquier poi
        ;
    }

    private static LodgingPoiType camping() {
    	return new LodgingPoiType()
		.setScores(
			Score.OTHER,
			Score.CAT_1,
			Score.CAT_2,
			Score.CAT_3,
			Score.LUXURY)	
		.setLodgingTypeFlags(
			LodgingTypeFlag.NONE)
	    .setLodgingFlags(
	    	LodgingFlag.BBQ,
	    	LodgingFlag.CLOACKROOM,
    		LodgingFlag.BAR, 
    		LodgingFlag.BIKE_RENT, 
    		LodgingFlag.CREDIT_CARD, 
    		LodgingFlag.PETS_ALLOWED)
	    .setLodgingTypes(
    		LodgingType.ADULT,		// Facturación por Adulto
    		LodgingType.CHILD,		// Facturación por Niño
    		LodgingType.PET,		// Facturación por mascota 
    		LodgingType.TENT,		// Tienda de campaña
    		LodgingType.TENT_FAM,	// Tienda de campaña familiar
    		LodgingType.BUS,		// Autocar
    		LodgingType.MOTORHOME,	// Caravana
    		LodgingType.CAR,		// Coche
    		LodgingType.MOTORBIKE	// Moto
    		)
	    //.setFlags()   // común a cualquier poi
	    ;
    }
    
    private static LodgingPoiType apartment(){
    	return new LodgingPoiType()
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
        .setLodgingTypes(
			LodgingType.HAB1,
			LodgingType.HAB2,
			LodgingType.HAB3 )
        //.setFlags()   // común a cualquier poi
        ;
    }
    
    
    private static BeachPoiType beach(){
    	return new BeachPoiType()
    	.setDisabledAccessibility(
    			DisabledAccessibility.ASSISTANCE_TO_DISABLED,
    			DisabledAccessibility.DISABLED_ACCESS,
    			DisabledAccessibility.GUIDE_DOG_ALLOWED,
    			DisabledAccessibility.PARKING_ACCESSIBLE)
    			
    	.setQualityCertificates(
    			QualityCertificate.BANDERA_AZUL,
    			QualityCertificate.ACCESIBILIDAD,
    			QualityCertificate.NATURISTA);
    }
    
    public static AbstractPoiType getPoiType(PoiType type){
		AbstractPoiType result =  types.get(type);
		if (result == null){
			throw new IllegalArgumentException("No PoiType found with name " + type );
		}
		return result;
	}


}   
    
