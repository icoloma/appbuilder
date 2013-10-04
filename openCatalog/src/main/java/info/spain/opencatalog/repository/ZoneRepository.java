package info.spain.opencatalog.repository;

import info.spain.opencatalog.domain.Zone;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ZoneRepository extends MongoRepository<Zone, String> {
	
	@Query( value="{'name':{'$regex':?0, '$options': 'i'}}")
	public Page<Zone> findByNameIgnoreCaseLike(@Param("name") String name, Pageable pageable);
	
	@Query( value="{'name':?0}")
	public Page<Zone> findByName(@Param("name") String name, Pageable pageable);
	
	
	@Query( value="{ '_id' : {'$in': ?0}}")
	public List<Zone> findByIds(String[] ids);

	

}
