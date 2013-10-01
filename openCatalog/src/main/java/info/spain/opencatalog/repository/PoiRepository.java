package info.spain.opencatalog.repository;

import info.spain.opencatalog.domain.poi.BasicPoi;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.geo.Distance;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface PoiRepository extends MongoRepository<BasicPoi, String>, PoiRepositoryCustom {
	
	@Query( value="{'name.es':{'$regex':?0, '$options': 'i'}}")
	public Page<BasicPoi> findByNameEsLikeIgnoreCase(String name, Pageable pageable);
	
	public Page<BasicPoi> findByNameEs(String name, Pageable pageable);
	
	// { 'location' : { '$nearSphere' : [location.lng, location.lat], '$maxDistance' : distance}}
	public Page<BasicPoi> findByLocationNear(Point location, Distance distance, Pageable pageable);
	
	@Query( value="{'address.adminArea1':{'$regex':?0, '$options': 'i'}}")
	public List<BasicPoi> findByAddressArea1LikeIgnoreCase(String name);
	
	@Query( value="{'address.adminArea1':?0}")
	public List<BasicPoi> findByAddressArea1(String name);

	@Query( value="{'address.adminArea2':{'$regex':?0, '$options': 'i'}}")
	public List<BasicPoi> findByAddressArea2LikeIgnoreCase(String name);
	
	@Query( value="{'address.adminArea2':?0}")
	public List<BasicPoi> findByAddressArea2(String name);
	
	@Query( value="{ '_id' : {'$in': ?0}}")
	public List<BasicPoi> findByIds(String[] ids);

}
