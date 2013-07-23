package info.spain.opencatalog.domain.poi;

import java.util.Set;

/**
 * Grupo de flags
 * @author ehdez
 *
 */
public enum FlagGroup {

	COMMON, 
	FAMILY, 

	ACCESSIBILITY,
	QUALITY, 
	
	NATURE, 
	BEACH,
	
	
	CULTURE,
	CULTURE_ARTISTIC,
	CULTURE_CONSTRUCTION,
	CULTURE_DESIGNATION,
	CULTURE_HISTORICAL,
	
	BUSINESS_ACTIVITY,
	BUSINESS_NAUTICAL,
	BUSINESS_SKI,
	BUSINESS_SPORT,
	GOLF_TYPE,
	BUSINESS_GOLF,

	LODGING, 
	LODGING_SERVICES, 
	ROOM;
	
	public Set<Flag> getFlags()  {
		return Flag.getFlags(this);
	}
	
}
