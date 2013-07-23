package info.spain.opencatalog.repository;

import info.spain.opencatalog.converter.DistanceConverter;
import info.spain.opencatalog.converter.PointConverter;
import info.spain.opencatalog.domain.poi.BasicPoi;

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
public interface PoiRepository extends MongoRepository<BasicPoi, String>, PoiRepositoryCustom {
	
	// API : /api/poi/?limit=1&page=5&sort=name.es&name.es.dir=DESC
	// UI  : /admin/poi/?page.page=2&page.size=5&page.sort=name.es&page.sort.dir=DESC
	@RestResource(path = "byNameLike", rel="byNameLike")    
	@Query( value="{'name.es':{'$regex':?0, '$options': 'i'}}")
	public Page<BasicPoi> findByNameEsLikeIgnoreCase(@Param("name") String name, Pageable pageable);
	
	@RestResource(path = "byName", rel="byName")    
	public Page<BasicPoi> findByNameEs(@Param("name") String name, Pageable pageable);
	
	// { 'location' : { '$nearSphere' : [location.lng, location.lat], '$maxDistance' : distance}}
	@RestResource(path = "byLocationNear", rel="byLocationNear")
	public Page<BasicPoi> findByLocationNear(@Param("location") @ConvertWith(value=PointConverter.class) Point location, @Param("distance") @ConvertWith(value=DistanceConverter.class) Distance distance, Pageable pageable);
	
	@RestResource( path="byArea1Like", rel="byArea1Like")
	@Query( value="{'address.adminArea1':{'$regex':?0, '$options': 'i'}}")
	public List<BasicPoi> findByAddressArea1LikeIgnoreCase(@Param("adminArea1") String name);
	
	@RestResource( path="byArea1", rel="byArea1")
	@Query( value="{'address.adminArea1':?0}")
	public List<BasicPoi> findByAddressArea1(@Param("adminArea1") String name);

	@RestResource( path="byArea2Like", rel="byArea2Like")
	@Query( value="{'address.adminArea2':{'$regex':?0, '$options': 'i'}}")
	public List<BasicPoi> findByAddressArea2LikeIgnoreCase(@Param("adminArea2") String name);
	
	@RestResource( path="byArea2", rel="byArea2")
	@Query( value="{'address.adminArea2':?0}")
	public List<BasicPoi> findByAddressArea2(@Param("adminArea2") String name);
	
	@RestResource( path="byId", rel="byId")
	@Query( value="{ '_id' : {'$in': ?0}}")
	public List<BasicPoi> findByIds(@Param("id") String[] ids);
	
	

}
