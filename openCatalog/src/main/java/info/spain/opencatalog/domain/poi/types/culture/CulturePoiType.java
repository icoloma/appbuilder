package info.spain.opencatalog.domain.poi.types.culture;

import info.spain.opencatalog.domain.poi.types.AbstractPoiType;
import info.spain.opencatalog.domain.poi.types.TimeTableEntry;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.Objects;

/**
 * 
 * Define los campos comunes que tendrán los Poi de tipo Lodging
 * 
 */
@Document(collection="poi")
public class CulturePoiType extends AbstractPoiType {
	
	/** horarios de apertura/cierre */
	private Set<TimeTableEntry> timetable;
    
    	

	@Override
	public String toString() {
		return Objects.toStringHelper(getClass())
			.add("poiType", getPoiType())
			.add("id", getId())
			.add("name", getName())
			.add("description", getDescription())
			.add("location", getLocation())
			.add("createdDate", getCreatedDate())
			.add("lastModifiedDate", getLastModifiedDate())
			.add("timeTable", timetable)
			.toString();
	}
    
}
