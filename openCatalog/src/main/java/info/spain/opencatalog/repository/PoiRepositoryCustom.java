package info.spain.opencatalog.repository;

import info.spain.opencatalog.domain.Poi;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;


public interface PoiRepositoryCustom {
	
	@RestResource(path = "within", rel="within")
	public List<Poi> findWithIn(@Param("lat") double lat, @Param("lng") double lng, @Param("radius") double radius);

}
