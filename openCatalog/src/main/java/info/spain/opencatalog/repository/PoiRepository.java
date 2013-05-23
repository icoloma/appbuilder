package info.spain.opencatalog.repository;

import info.spain.opencatalog.converter.DistanceConverter;
import info.spain.opencatalog.converter.PointConverter;
import info.spain.opencatalog.domain.Poi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.geo.Distance;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.ConvertWith;
import org.springframework.data.rest.repository.annotation.RestResource;

@RestResource(path = "poi", rel="pois")
public interface PoiRepository extends MongoRepository<Poi, String>, PoiRepositoryCustom {
	
	// { "name.es" : { "$regex" : name}}
	@RestResource(path = "byName_es", rel="byName_es")
	public Page<Poi> findByNameEsLike(@Param("name") String name, Pageable pageable);
	
	@RestResource(path = "byName_en", rel="byName_en")
	public Page<Poi> findByNameEnLike(@Param("name") String name, Pageable pageable);
	
	@RestResource(path = "byName_fr", rel="byName_fr")
	public Page<Poi> findByNameFrLike(@Param("name") String name, Pageable pageable);
	
	@RestResource(path = "byName_de", rel="byName_de")
	public Page<Poi> findByNameDeLike(@Param("name") String name, Pageable pageable);
	
	@RestResource(path = "byName_it", rel="byName_it")
	public Page<Poi> findByNameItLike(@Param("name") String name, Pageable pageable);
	
	// { 'location' : { '$nearSphere' : [location.lng, location.lat], '$maxDistance' : distance}}
	@RestResource(path = "locationNear", rel="locationNear")
	public Page<Poi> findByLocationNear(@Param("location") @ConvertWith(value=PointConverter.class) Point location, @Param("distance") @ConvertWith(value=DistanceConverter.class) Distance distance, Pageable pageable);
	
//	@RestResource
//	public List<Poi> findByLocationWithin(@Param("circle") Circle circle);


}
