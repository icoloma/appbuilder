package info.spain.opencatalog.repository;

import info.spain.opencatalog.converter.DistanceConverter;
import info.spain.opencatalog.converter.PointConverter;
import info.spain.opencatalog.domain.Poi;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.geo.Distance;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.ConvertWith;
import org.springframework.data.rest.repository.annotation.RestResource;

@RestResource(path = "poi", rel="pois")
public interface PoiRepository extends MongoRepository<Poi, String>, PoiRepositoryCustom {
	
	// API : /api/poi/?limit=1&page=5&sort=name.es&name.es.dir=DESC
	// UI  : /admin/poi/?page.page=2&page.size=5&page.sort=name.es&page.sort.dir=DESC
	@RestResource(path = "byName_es", rel="byName_es")    
	@Query( value="{'name.es':{'$regex':?0, '$options': 'i'}}")
	public Page<Poi> findByNameEsLikeIgnoreCase(@Param("name") String name, Pageable pageable);
	
	public Page<Poi> findByNameEs(@Param("name") String name, Pageable pageable);
	
	@RestResource(path = "byName_en", rel="byName_en")
	@Query( value="{'name.en':{'$regex':?0, '$options': 'i'}}")
	public Page<Poi> findByNameEnLikeIgnoreCase(@Param("name") String name, Pageable pageable);
	
	@RestResource(path = "byName_fr", rel="byName_fr")
	@Query( value="{'name.fr':{'$regex':?0, '$options': 'i'}}")
	public Page<Poi> findByNameFrLikeIgnoreCase(@Param("name") String name, Pageable pageable);
	
	@RestResource(path = "byName_de", rel="byName_de")
	@Query( value="{'name.de':{'$regex':?0, '$options': 'i'}}")
	public Page<Poi> findByNameDeLikeIgnoreCase(@Param("name") String name, Pageable pageable);
	
	@RestResource(path = "byName_it", rel="byName_it")
	@Query( value="{'name.it':{'$regex':?0, '$options': 'i'}}")
	public Page<Poi> findByNameItLikeIgnoreCase(@Param("name") String name, Pageable pageable);
	
	// { 'location' : { '$nearSphere' : [location.lng, location.lat], '$maxDistance' : distance}}
	@RestResource(path = "locationNear", rel="locationNear")
	public Page<Poi> findByLocationNear(@Param("location") @ConvertWith(value=PointConverter.class) Point location, @Param("distance") @ConvertWith(value=DistanceConverter.class) Distance distance, Pageable pageable);
	
	@Query( value="{'address.adminArea1':{'$regex':?0, '$options': 'i'}}")
	public List<Poi> findByAddressArea1LikeIgnoreCase(@Param("adminArea1") String name);
	
	@Query( value="{'address.adminArea1':?0}")
	public List<Poi> findByAddressArea1(@Param("adminArea1") String name);

	@Query( value="{'address.adminArea2':{'$regex':?0, '$options': 'i'}}")
	public List<Poi> findByAddressArea2LikeIgnoreCase(@Param("adminArea2") String name);
	
	@Query( value="{'address.adminArea2':?0}")
	public List<Poi> findByAddressArea2(@Param("adminArea2") String name);
	
	@Query( value="{ '_id' : {'$in': ?0}}")
	public List<Poi> findByIds(String[] ids);

}
