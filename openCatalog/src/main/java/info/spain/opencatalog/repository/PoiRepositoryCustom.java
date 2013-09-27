package info.spain.opencatalog.repository;

import info.spain.opencatalog.api.controller.SearchQuery;
import info.spain.opencatalog.converter.SearchQueryConverter;
import info.spain.opencatalog.domain.poi.BasicPoi;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.ConvertWith;
import org.springframework.data.rest.repository.annotation.RestResource;
import org.springframework.data.web.PageableDefaults;


public interface PoiRepositoryCustom {
	
	@RestResource(path = "locationWithin", rel="locationWithin")
	public List<BasicPoi> findWithIn(@Param("lat") double lat, @Param("lng") double lng, @Param("radius") double radius);
	
	@RestResource(path = "inZone", rel="inZone")
	public List<BasicPoi> findWithInZone(@Param("zoneId") String zoneId);
	

	public List<String> findAdminArea1ByName(String name);
	public List<String> findAdminArea2ByName(String name);
	
	
	@RestResource(path = "custom", rel="custom")
	public Page<BasicPoi> findCustom(  
			@Param("q") @ConvertWith(value=SearchQueryConverter.class) SearchQuery query, 
			@PageableDefaults(sort="lastModified", sortDir=Direction.DESC) Pageable pageable);
	
	
}
