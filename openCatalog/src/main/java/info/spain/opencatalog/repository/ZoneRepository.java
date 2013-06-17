package info.spain.opencatalog.repository;

import info.spain.opencatalog.domain.Zone;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

@RestResource(path = "zone", rel="zones")
public interface ZoneRepository extends MongoRepository<Zone, String> {
	
	@RestResource(path = "byName", rel="byName")
	@Query( value="{'name':{'$regex':?0, '$options': 'i'}}")
	public Page<Zone> findByNameIgnoreCaseLike(@Param("name") String name, Pageable pageable);
	
	@Query( value="{'name':?0}")
	public Page<Zone> findByName(@Param("name") String name, Pageable pageable);
	

}
