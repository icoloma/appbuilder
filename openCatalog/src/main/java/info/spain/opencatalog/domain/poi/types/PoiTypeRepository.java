package info.spain.opencatalog.domain.poi.types;

import info.spain.opencatalog.domain.poi.Flag;
import info.spain.opencatalog.domain.poi.beach.BathCondition;
import info.spain.opencatalog.domain.poi.beach.BeachComposition;
import info.spain.opencatalog.domain.poi.beach.SandType;
import info.spain.opencatalog.domain.poi.lodging.RoomType;
import info.spain.opencatalog.domain.poi.lodging.Score;

import java.util.HashSet;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

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
   
  
    
   /** Hotel */
   private static LodgingType hotelType(){
	   
    	HashSet<Flag> flags = Sets.newHashSet();
    	flags.addAll(Flag.ALL_COMMON_FLAGS);
    	flags.addAll(Flag.ALL_FAMILY_FLAGS);
    	flags.addAll(Flag.ALL_ACCESSIBILITY_FLAGS);
    	flags.addAll(Flag.ALL_LODGING_FLAGS);
    	flags.addAll(Flag.ALL_BUSINESS_SERVICES_LODGING_FLAGS);
    	flags.add(Flag.QUALITY_CAMPSA);
    	flags.add(Flag.QUALITY_ACCESIBILIDAD);
    	flags.add(Flag.QUALITY_Q_CALIDAD);
	   
    	return new LodgingType(PoiTypeID.HOTEL)
    	
    	.setAllowedFlags(flags.toArray(new Flag[]{}))
        
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

	   HashSet<Flag> flags = Sets.newHashSet();
    	flags.addAll(Flag.ALL_COMMON_FLAGS);
    	flags.addAll(Flag.ALL_FAMILY_FLAGS);
    	flags.addAll(Flag.ALL_ACCESSIBILITY_FLAGS);
    	flags.addAll(Flag.ALL_LODGING_FLAGS);
    	flags.add(Flag.QUALITY_CAMPSA);
    	flags.add(Flag.QUALITY_ACCESIBILIDAD);
    	flags.add(Flag.QUALITY_Q_CALIDAD);

    	return new LodgingType(PoiTypeID.CAMPING)
    	.setAllowedFlags(flags.toArray(new Flag[]{}))
    	
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
       	HashSet<Flag> flags = Sets.newHashSet();
    	flags.addAll(Flag.ALL_COMMON_FLAGS);
    	flags.addAll(Flag.ALL_FAMILY_FLAGS);
    	flags.addAll(Flag.ALL_ACCESSIBILITY_FLAGS);
    	flags.addAll(Flag.ALL_LODGING_FLAGS);
    	flags.add(Flag.QUALITY_CAMPSA);
    	flags.add(Flag.QUALITY_ACCESIBILIDAD);
    	flags.add(Flag.QUALITY_Q_CALIDAD);

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
        .setAllowedFlags(flags.toArray(new Flag[]{}))
        ;
    }
    
    /** Playa */
    private static BasicPoiType beachType(){
    	HashSet<Flag> flags = Sets.newHashSet();
    	flags.addAll(Flag.ALL_COMMON_FLAGS);
    	flags.addAll(Flag.ALL_FAMILY_FLAGS);
    	flags.addAll(Flag.ALL_ACCESSIBILITY_FLAGS);
    	flags.add(Flag.QUALITY_BANDERA_AZUL);
    	flags.add(Flag.QUALITY_ACCESIBILIDAD);
    	flags.add(Flag.QUALITY_NATURISTA);
    	
    	return new BasicPoiType(PoiTypeID.BEACH)
	 		.setAllowedDataValidator("longitude", DataValidator.DOUBLE_VALIDATOR)
			.setAllowedDataValidator("width", DataValidator.DOUBLE_VALIDATOR)
			.setAllowedDataValidator("sandType", new DataValidator().setValidValues(Sets.newHashSet(SandType.values())))
			.setAllowedDataValidator("bathCondition", new DataValidator().setValidValues(Sets.newHashSet(BathCondition.values())))
			.setAllowedDataValidator("composition", new DataValidator().setValidValues(Sets.newHashSet(BeachComposition.values())))
			.setAllowedDataValidator("promenade", DataValidator.BOOLEAN_VALIDATOR)
			.setAllowedDataValidator("anchorZone", DataValidator.BOOLEAN_VALIDATOR)
			.setAllowedFlags(flags.toArray(new Flag[]{}));
    }
    
    /** espacio natural*/
    private static BasicPoiType naturalSpaceType(){
       	HashSet<Flag> flags = Sets.newHashSet();
    	flags.addAll(Flag.ALL_COMMON_FLAGS);
    	flags.addAll(Flag.ALL_FAMILY_FLAGS);
    	flags.addAll(Flag.ALL_ACCESSIBILITY_FLAGS);
    	flags.addAll(Flag.ALL_NATURE_FLAGS);
    	flags.add(Flag.QUALITY_PATRIMONIO_HUMANIDAD);
    	flags.add(Flag.QUALITY_ACCESIBILIDAD);
    	flags.add(Flag.QUALITY_ECOTURISMO);
    	flags.add(Flag.QUALITY_EDEN);
    	flags.add(Flag.QUALITY_RESERVA_BIOSFERA);
    	
    	return new BasicPoiType(PoiTypeID.NATURAL_SPACE)
    		.setAllowedFlags(flags.toArray(new Flag[]{}));
    		
    }
    /** Museo */
    private static BasicPoiType museumType(){
      	HashSet<Flag> flags = Sets.newHashSet();
    	flags.addAll(Flag.ALL_COMMON_FLAGS);
    	flags.addAll(Flag.ALL_FAMILY_FLAGS);
    	flags.addAll(Flag.ALL_ACCESSIBILITY_FLAGS);
    	flags.add(Flag.QUALITY_MICHELIN);
    	flags.add(Flag.QUALITY_ACCESIBILIDAD);
    	flags.add(Flag.QUALITY_CAMPSA);
    	
    	return new BasicPoiType(PoiTypeID.MUSEUM)
    		.setAllowedFlags(flags.toArray(new Flag[]{}));
    }
   
    /** Monumento */
    private static BasicPoiType monumentType(){
    	HashSet<Flag> flags = Sets.newHashSet();
    	flags.addAll(Flag.ALL_COMMON_FLAGS);
    	flags.addAll(Flag.ALL_FAMILY_FLAGS);
    	flags.addAll(Flag.ALL_ACCESSIBILITY_FLAGS);
    	flags.add(Flag.QUALITY_PATRIMONIO_HUMANIDAD);
    	flags.add(Flag.QUALITY_MICHELIN);
    	flags.add(Flag.QUALITY_ACCESIBILIDAD);
    	flags.add(Flag.QUALITY_CAMPSA);
    	flags.add(Flag.QUALITY_MICHELIN);
    	flags.add(Flag.QUALITY_RESERVA_BIOSFERA);
    	 
    	return new BasicPoiType(PoiTypeID.MONUMENT)
    		.setAllowedFlags(flags.toArray(new Flag[]{}));
    }
    
    /** Parques y jardines*/
    private static BasicPoiType parkGardenType(){
     	HashSet<Flag> flags = Sets.newHashSet();
    	flags.addAll(Flag.ALL_COMMON_FLAGS);
    	flags.addAll(Flag.ALL_FAMILY_FLAGS);
    	flags.addAll(Flag.ALL_ACCESSIBILITY_FLAGS);
    	flags.add(Flag.QUALITY_PATRIMONIO_HUMANIDAD);
    	flags.add(Flag.QUALITY_ACCESIBILIDAD);
    	flags.add(Flag.QUALITY_CAMPSA);
    	flags.add(Flag.QUALITY_MICHELIN);
   
    	return new BasicPoiType(PoiTypeID.PARK_GARDEN)
    		.setAllowedFlags(flags.toArray(new Flag[]{}));
    }
    
    /** Empresas de ecoturismo */
    private static BasicPoiType ecoTourismType(){
    	HashSet<Flag> flags = Sets.newHashSet();
    	flags.addAll(Flag.ALL_COMMON_FLAGS);
    	flags.addAll(Flag.ALL_FAMILY_FLAGS);
    	flags.addAll(Flag.ALL_ACCESSIBILITY_FLAGS);
    	flags.add(Flag.QUALITY_Q_CALIDAD);
    	flags.add(Flag.QUALITY_ACCESIBILIDAD);
    	flags.add(Flag.QUALITY_CAMPSA);
    	flags.add(Flag.QUALITY_MICHELIN);
    	flags.add(Flag.BUSINESS_ACTIVITY_GUIDE_TOUR);
    	flags.add(Flag.BUSINESS_ACTIVITY_EDUCATIONAL_ACTIVITIES);
    	flags.add(Flag.BUSINESS_ACTIVITY_LOCAL_PRODUCT_SALES);
    	flags.add(Flag.BUSINESS_ACTIVITY_ACTIVE_TOURISM);
    	flags.add(Flag.BUSINESS_ACTIVITY_ACTIVE_TOURISM);
    	  
    	return new BasicPoiType(PoiTypeID.ECO_TOURISM)
    		.setAllowedFlags(flags.toArray(new Flag[]{}));
    }
    
  
    /** Empresas de  Golf */
    private static BasicPoiType golfType(){
    	HashSet<Flag> flags = Sets.newHashSet();
    	flags.addAll(Flag.ALL_COMMON_FLAGS);
    	flags.addAll(Flag.ALL_FAMILY_FLAGS);
    	flags.addAll(Flag.ALL_ACCESSIBILITY_FLAGS);
    	flags.addAll(Flag.ALL_BUSINESS_SERVICES_GOLF_FLAGS);
    	flags.add(Flag.QUALITY_Q_CALIDAD);
    	flags.add(Flag.QUALITY_ACCESIBILIDAD);
    	flags.add(Flag.QUALITY_CAMPSA);
    	flags.add(Flag.QUALITY_MICHELIN);
    	flags.add(Flag.BUSINESS_ACTIVITY_LESSONS);
    	flags.add(Flag.BUSINESS_ACTIVITY_SCHOOL);
    	flags.add(Flag.BUSINESS_TYPE_GOLF_COMERCIAL);		// Campo comercial 
		flags.add(Flag.BUSINESS_TYPE_GOLF_PARTNERS);		// Campo de socios|
		flags.add(Flag.BUSINESS_TYPE_GOLF_MIXED);			// Campo mixto
    	  
    	return new BasicPoiType(PoiTypeID.GOLF)
    		.setAllowedFlags(flags.toArray(new Flag[]{}));
	    }
  
    /** Estación náutica */
    private static BasicPoiType nauticalStationType(){
    	HashSet<Flag> flags = Sets.newHashSet();
    	flags.addAll(Flag.ALL_COMMON_FLAGS);
    	flags.addAll(Flag.ALL_FAMILY_FLAGS);
    	flags.addAll(Flag.ALL_ACCESSIBILITY_FLAGS);
    	flags.addAll(Flag.ALL_BUSINESS_SERVICES_NAUTICAL_FLAGS);
    	
    	flags.add(Flag.QUALITY_Q_CALIDAD);
    	flags.add(Flag.QUALITY_ACCESIBILIDAD);
    	flags.add(Flag.QUALITY_CAMPSA);
    	flags.add(Flag.QUALITY_MICHELIN);
    	flags.add(Flag.BUSINESS_ACTIVITY_LESSONS);
    	flags.add(Flag.BUSINESS_ACTIVITY_SCHOOL);

    	return new BasicPoiType(PoiTypeID.NAUTICAL_STATION)
    		.setAllowedFlags(flags.toArray(new Flag[]{}));

    }
    
    /** Estación de esquí */
    private static BasicPoiType skiStationType(){

    	HashSet<Flag> flags = Sets.newHashSet();
    	flags.addAll(Flag.ALL_COMMON_FLAGS);
    	flags.addAll(Flag.ALL_FAMILY_FLAGS);
    	flags.addAll(Flag.ALL_ACCESSIBILITY_FLAGS);
    	flags.addAll(Flag.ALL_BUSINESS_SERVICES_SKI_FLAGS);
    	flags.add(Flag.QUALITY_Q_CALIDAD);
    	flags.add(Flag.QUALITY_ACCESIBILIDAD);
    	flags.add(Flag.QUALITY_CAMPSA);
    	flags.add(Flag.QUALITY_MICHELIN);
    	flags.add(Flag.BUSINESS_ACTIVITY_LESSONS);
    	flags.add(Flag.BUSINESS_ACTIVITY_SCHOOL);
    	
    	return new BasicPoiType(PoiTypeID.SKI_STATION)
    		.setAllowedFlags(flags.toArray(new Flag[]{}))
			.setAllowedDataValidator("total-km-esquiables", DataValidator.DOUBLE_VALIDATOR)
			.setAllowedDataValidator("cota-maxima", DataValidator.DOUBLE_VALIDATOR)
			.setAllowedDataValidator("cota-minima", DataValidator.DOUBLE_VALIDATOR)
    		.setAllowedDataValidator("pistas:alpino:numero-pistas-verdes", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:alpino:numero-pistas-rojas", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:alpino:numero-pistas-azules", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:alpino:numero-pistas-negras", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:alpino:total-pistas", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:alpino:total-kms", DataValidator.DOUBLE_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-pistas:fondo", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:km-esqui-fondo", DataValidator.DOUBLE_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-pistas:trineos", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-pistas:raquetas", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-show-park", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-trampolines", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-estadios-competicion", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-pista-iluminada", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-pistas:travesia", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-halfpipe", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:numero-snowboard", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("pistas:otros:otros", DataValidator.ANY_VALUE)  // Cualquier texto
    		.setAllowedDataValidator("nieve:numero-caniones", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("nieve:total-km-innovadps", DataValidator.DOUBLE_VALIDATOR)
    		.setAllowedDataValidator("nieve:total-pistas-inhibidas", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("nieve:otros", DataValidator.ANY_VALUE)  // Cualquier texto
    		.setAllowedDataValidator("servicios:numero-escuelas", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("servicios:numero-profesores", DataValidator.INTEGER_VALIDATOR)
    		.setAllowedDataValidator("servicios:estacion", DataValidator.ANY_VALUE)
    		.setAllowedDataValidator("servicios:area-influencia", DataValidator.ANY_VALUE)
    		;
    }
   


}   
    
