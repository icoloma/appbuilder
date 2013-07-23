package info.spain.opencatalog.domain.poi.types;

import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.FlagGroup;
import info.spain.opencatalog.domain.poi.FlagSetBuilder;
import info.spain.opencatalog.domain.poi.Score;
import info.spain.opencatalog.domain.poi.lodging.RoomType;

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
   public static BasicPoiType getType(String typeId){
	   return getType(PoiTypeID.valueOf(typeId));
   }

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
	    types.put(PoiTypeID.BASIC, basicType());
	    types.put(PoiTypeID.BEACH, beachType());
	    types.put(PoiTypeID.NATURAL_SPACE, naturalSpaceType());
	  
        // Lodging
        types.put(PoiTypeID.HOTEL, hotelType() );
        types.put(PoiTypeID.CAMPING, campingType());
        types.put(PoiTypeID.APARTMENT, apartmentType());

        
        // Culture
        types.put(PoiTypeID.MUSEUM, museumType());
        types.put(PoiTypeID.MONUMENT, monumentType());
        types.put(PoiTypeID.PARK_GARDEN, parkGardenType());
        
        // Business
        types.put(PoiTypeID.ECO_TOURISM, ecoTourismType());
        types.put(PoiTypeID.GOLF, golfType());
        types.put(PoiTypeID.NAUTICAL_STATION, nauticalStationType());
        types.put(PoiTypeID.SKI_STATION, skiStationType());
        
        
    }
   
   /** Basic Type */
   private static BasicPoiType basicType(){
	   return new BasicPoiType(PoiTypeID.BASIC)
   		.setAllowedFlags(new FlagSetBuilder()
		   	.add(FlagGroup.COMMON)
		   	.add(FlagGroup.FAMILY)
		   	.add(FlagGroup.ACCESSIBILITY)
		   	.build());
   }
    
   /** Hotel */
   private static LodgingType hotelType(){
	   
    	return new LodgingType(PoiTypeID.HOTEL)
    	
    	.setAllowedFlags(new FlagSetBuilder()
		   	.add(FlagGroup.COMMON)
		   	.add(FlagGroup.FAMILY)
		   	.add(FlagGroup.ACCESSIBILITY)
		   	.add(FlagGroup.LODGING)
		   	.add(FlagGroup.ROOM)
		   	.add(FlagGroup.LODGING_SERVICES)
		   	.add(Flag.QUALITY_CAMPSA, Flag.QUALITY_ACCESIBILIDAD, Flag.QUALITY_Q_CALIDAD)
		   	.build())
        
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
     	;
		
    }

   /** Camping */
   private static LodgingType campingType() {
	   return new LodgingType(PoiTypeID.CAMPING)
	   	.setAllowedFlags(
    		new FlagSetBuilder()
    		.add(FlagGroup.COMMON)
    		.add(FlagGroup.FAMILY)
		   	.add(FlagGroup.ACCESSIBILITY)
		   	.add(FlagGroup.LODGING)
		   	.add(FlagGroup.LODGING_SERVICES)
		   	.add(Flag.QUALITY_CAMPSA, Flag.QUALITY_ACCESIBILIDAD, Flag.QUALITY_Q_CALIDAD)
		   	.build())
    	
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
       	;
	}
   
   	/** Apartamento */
    private static LodgingType apartmentType(){
    	return new LodgingType(PoiTypeID.CAMPING)
    	.setAllowedFlags(
       		new FlagSetBuilder()
       		.add(FlagGroup.COMMON)
    		.add(FlagGroup.FAMILY)
   		   	.add(FlagGroup.ACCESSIBILITY)
   		   	.add(FlagGroup.LODGING)
   		   	.add(FlagGroup.LODGING_SERVICES)
   		   	.add(FlagGroup.ROOM)
   		   	.add(Flag.QUALITY_CAMPSA, Flag.QUALITY_ACCESIBILIDAD, Flag.QUALITY_Q_CALIDAD)
   		   	.build())
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
        ;
    }
    
    /** Playa */
    private static BasicPoiType beachType(){
    	return new BasicPoiType(PoiTypeID.BEACH)
    		.setAllowedFlags( new FlagSetBuilder()
	    		.add(FlagGroup.COMMON)
				.add(FlagGroup.FAMILY)
    			.add(FlagGroup.ACCESSIBILITY)
    			.add(FlagGroup.BEACH)
    			.add(Flag.QUALITY_BANDERA_AZUL)
    			.add(Flag.QUALITY_ACCESIBILIDAD)
    			.add(Flag.QUALITY_NATURISTA)
    			.build())
	 		.setAllowedDataValidator("longitude", DataValidator.DOUBLE_VALIDATOR)
			.setAllowedDataValidator("width", DataValidator.DOUBLE_VALIDATOR)
			.setAllowedDataValidator("promenade", DataValidator.BOOLEAN_VALIDATOR)
			.setAllowedDataValidator("anchorZone", DataValidator.BOOLEAN_VALIDATOR)
		;
    }
    
    /** espacio natural*/
    private static BasicPoiType naturalSpaceType(){
    	return new BasicPoiType(PoiTypeID.NATURAL_SPACE)
    	.setAllowedFlags(
    			new FlagSetBuilder()
    			.add(FlagGroup.COMMON)
    			.add(FlagGroup.FAMILY)
    			.add(FlagGroup.ACCESSIBILITY)
    			.add(FlagGroup.NATURE)
    			.add(Flag.QUALITY_PATRIMONIO_HUMANIDAD)
    			.add(Flag.QUALITY_ACCESIBILIDAD)
    			.add(Flag.QUALITY_ECOTURISMO)
    			.add(Flag.QUALITY_EDEN)
    			.add(Flag.QUALITY_RESERVA_BIOSFERA)
    			.build()
    	);
    }
    /** Museo */
    private static BasicPoiType museumType(){
    	return new BasicPoiType(PoiTypeID.MUSEUM)
		.setAllowedFlags(
	    	new FlagSetBuilder()
			.add(FlagGroup.COMMON)
			.add(FlagGroup.FAMILY)
			.add(FlagGroup.ACCESSIBILITY)
			.add(FlagGroup.CULTURE_ARTISTIC)
			.add(FlagGroup.CULTURE_CONSTRUCTION)
			.add(FlagGroup.CULTURE_DESIGNATION)
			.add(FlagGroup.CULTURE_HISTORICAL)
			.add(Flag.QUALITY_ACCESIBILIDAD)
			.add(Flag.QUALITY_MICHELIN)
			.add(Flag.QUALITY_CAMPSA)
			.build()
		);
    }
   
    /** Monumento */
    private static BasicPoiType monumentType(){
    	return new BasicPoiType(PoiTypeID.MONUMENT)
		.setAllowedFlags(
	    	new FlagSetBuilder()
			.add(FlagGroup.COMMON)
			.add(FlagGroup.FAMILY)
			.add(FlagGroup.ACCESSIBILITY)
			.add(FlagGroup.CULTURE_ARTISTIC)
			.add(FlagGroup.CULTURE_CONSTRUCTION)
			.add(FlagGroup.CULTURE_HISTORICAL)
			.add(Flag.QUALITY_ACCESIBILIDAD)
			.add(Flag.QUALITY_MICHELIN)
			.add(Flag.QUALITY_CAMPSA)
			.add(Flag.QUALITY_PATRIMONIO_HUMANIDAD)
			.add(Flag.QUALITY_RESERVA_BIOSFERA)
			.build()
		);
    }
    
    /** Parques y jardines*/
    private static BasicPoiType parkGardenType(){
    	return new BasicPoiType(PoiTypeID.PARK_GARDEN)
		.setAllowedFlags(
	    	new FlagSetBuilder()
			.add(FlagGroup.COMMON)
			.add(FlagGroup.FAMILY)
			.add(FlagGroup.ACCESSIBILITY)
			.add(FlagGroup.CULTURE_ARTISTIC)
			.add(FlagGroup.CULTURE_CONSTRUCTION)
			.add(FlagGroup.CULTURE_HISTORICAL)
			.add(Flag.QUALITY_ACCESIBILIDAD)
			.add(Flag.QUALITY_MICHELIN)
			.add(Flag.QUALITY_CAMPSA)
			.add(Flag.QUALITY_PATRIMONIO_HUMANIDAD)
			.build()
		);
    }
    
    /** Empresas de ecoturismo */
    private static BasicPoiType ecoTourismType(){
    	return new BasicPoiType(PoiTypeID.ECO_TOURISM)
		.setAllowedFlags(
	    	new FlagSetBuilder()
			.add(FlagGroup.COMMON)
			.add(FlagGroup.FAMILY)
			.add(FlagGroup.ACCESSIBILITY)
			.add(Flag.QUALITY_ACCESIBILIDAD)
			.add(Flag.QUALITY_MICHELIN)
			.add(Flag.QUALITY_Q_CALIDAD)
			.add(Flag.QUALITY_CAMPSA)
			.add(Flag.QUALITY_CAMPSA)
			.add(Flag.QUALITY_MICHELIN)
			.add(Flag.ACTIVE_TOURISM)
			.build()
		);
    }
    
  
    /** Empresas de  Golf */
    private static BasicPoiType golfType(){
    	return new BasicPoiType(PoiTypeID.GOLF)
 	   	.setAllowedFlags(
 	   			new FlagSetBuilder()
 	   			.add(FlagGroup.COMMON)
 	   			.add(FlagGroup.FAMILY)
 	   			.add(FlagGroup.ACCESSIBILITY)
 	   			.add(FlagGroup.GOLF_TYPE)
 	   			.add(FlagGroup.BUSINESS_GOLF)
 	   			.add(Flag.QUALITY_Q_CALIDAD)
 	   			.add(Flag.QUALITY_ACCESIBILIDAD)
 	   			.add(Flag.QUALITY_CAMPSA)
 	   			.add(Flag.QUALITY_MICHELIN)
 	   			.add(Flag.SCHOOL)
 	   			.add(Flag.LESSONS)
 	   			.build()
 	   			);
    }
  
    /** Estación náutica */
    private static BasicPoiType nauticalStationType(){
    	return new BasicPoiType(PoiTypeID.NAUTICAL_STATION)
 	   	.setAllowedFlags(
 	   			new FlagSetBuilder()
 	   			.add(FlagGroup.COMMON)
 	   			.add(FlagGroup.FAMILY)
 	   			.add(FlagGroup.ACCESSIBILITY)
 	   			.add(FlagGroup.BUSINESS_NAUTICAL)
 	   			.add(Flag.QUALITY_Q_CALIDAD)
 	   			.add(Flag.QUALITY_ACCESIBILIDAD)
 	   			.add(Flag.QUALITY_CAMPSA)
 	   			.add(Flag.QUALITY_MICHELIN)
 	   			.add(Flag.SCHOOL)
 	   			.add(Flag.LESSONS)
 	   			.build()
 	   			);
    }
    
    /** Estación de esquí */
    private static BasicPoiType skiStationType(){
    	return new BasicPoiType(PoiTypeID.SKI_STATION)
 	   		.setAllowedFlags(
 	   			new FlagSetBuilder()
 	   			.add(FlagGroup.COMMON)
 	   			.add(FlagGroup.FAMILY)
 	   			.add(FlagGroup.ACCESSIBILITY)
 	   			.add(FlagGroup.BUSINESS_SKI)
 	   			.add(Flag.QUALITY_Q_CALIDAD)
 	   			.add(Flag.QUALITY_ACCESIBILIDAD)
 	   			.add(Flag.QUALITY_CAMPSA)
 	   			.add(Flag.QUALITY_MICHELIN)
 	   			.add(Flag.SCHOOL)
 	   			.add(Flag.LESSONS)
 	   			.build()
 	   			)
    		.setAllowedDataValidator("total-km-esquiables", DataValidator.DOUBLE_VALIDATOR)
			.setAllowedDataValidator("cota-maxima", DataValidator.DOUBLE_VALIDATOR)
			.setAllowedDataValidator("cota-minima", DataValidator.DOUBLE_VALIDATOR)
    		
			.setAllowedDataValidator("pistas:alpino:numero-pistas-verdes", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:alpino:numero-pistas-azules", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:alpino:numero-pistas-rojas", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:alpino:numero-pistas-negras", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:alpino:total-pistas", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:alpino:total-kms", DataValidator.DOUBLE_VALIDATOR)
    		
    		.setAllowedDataValidator("pistas:otros:km-esqui-fondo", DataValidator.DOUBLE_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-pistas-fondo", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-pistas-trineos", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-pistas-raquetas", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-pistas-travesia", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-pistas-iluminadas", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-estadios-competicion", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-trampolines", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-show-park", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-halfpipe", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-snowboard", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:otros", DataValidator.KEY_VALUE_VALIDATOR)  // Múltiple 
    		
    		.setAllowedDataValidator("nieve:numero-caniones", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("nieve:total-km-innovados", DataValidator.DOUBLE_VALIDATOR)
    		.setAllowedDataValidator("nieve:total-pistas-inhibidas", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("nieve:otros", DataValidator.KEY_VALUE_VALIDATOR)  // Cualquier texto
    		
    		.setAllowedDataValidator("servicios:numero-escuelas", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("servicios:numero-profesores", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("servicios:estacion", DataValidator.BIGTEXT_VALIDATOR)
    		.setAllowedDataValidator("servicios:area-influencia", DataValidator.BIGTEXT_VALIDATOR)
    		;
    }
   
    
   


}   
    
