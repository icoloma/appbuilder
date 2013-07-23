package info.spain.opencatalog.repository;

import info.spain.opencatalog.domain.poi.BasicPoi;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;


public interface PoiRepositoryCustom {
	
	@RestResource(path = "locationWithin", rel="locationWithin")
	public List<BasicPoi> findWithIn(@Param("lat") double lat, @Param("lng") double lng, @Param("radius") double radius);
	
	@RestResource(path = "inZone", rel="inZone")
	public List<BasicPoi> findWithInZone(@Param("zoneId") String zoneId);
	

	public List<String> findAdminArea1ByName(String name);
	public List<String> findAdminArea2ByName(String name);
	
}
