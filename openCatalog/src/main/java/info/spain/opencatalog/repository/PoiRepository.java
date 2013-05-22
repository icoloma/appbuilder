package info.spain.opencatalog.repository;

import info.spain.opencatalog.domain.Poi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

@RestResource(path = "poi", rel="pois")
public interface PoiRepository extends MongoRepository<Poi, String>, PoiRepositoryCustom {
	
	@RestResource(path = "name_es", rel="name_es")
	public Page<Poi> findByNameEsLike(@Param("name") String name, Pageable pageable);
	
	@RestResource(path = "name_en", rel="name_en")
	public Page<Poi> findByNameEnLike(@Param("name") String name, Pageable pageable);
	
	@RestResource(path = "name_fr", rel="name_fr")
	public Page<Poi> findByNameFrLike(@Param("name") String name, Pageable pageable);
	
	@RestResource(path = "name_de", rel="name_de")
	public Page<Poi> findByNameDeLike(@Param("name") String name, Pageable pageable);
	
	@RestResource(path = "name_it", rel="name_it")
	public Page<Poi> findByNameItLike(@Param("name") String name, Pageable pageable);
	
//	@RestResource
//	public List<Poi> findByLocationWithin(@Param("circle") Circle circle);
//	

	// { 'location' : { '$near' : [point.x, point.y], '$maxDistance' : distance}}
//	@RestResource
//	public List<Poi> findByLocationNear(@Param("loc") Point location, @Param("dist") Distance distance);

}
